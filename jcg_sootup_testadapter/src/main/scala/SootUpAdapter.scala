import sootup.callgraph.{ClassHierarchyAnalysisAlgorithm, RapidTypeAnalysisAlgorithm}
import sootup.core.signatures.MethodSignature
import sootup.core.typehierarchy.ViewTypeHierarchy
import sootup.core.types.PrimitiveType._
import sootup.core.types.{ArrayType, ClassType, Type, VoidType}
import sootup.java.bytecode.inputlocation.JavaClassPathAnalysisInputLocation
import sootup.java.core.{JavaIdentifierFactory, JavaProject}
import sootup.java.core.language.JavaLanguage
import play.api.libs.json.Json
import sootup.java.core.views.JavaView

import java.io.FileWriter
import java.util.Collections
import scala.collection.JavaConverters._
import scala.collection.mutable

object SootUpAdapter extends JCGTestAdapter {

    private val CHA = "CHA"
    private val RTA = "RTA"
    private val VTA = "VTA"
    private val SPARK = "SPARK"

    override def possibleAlgorithms(): Array[String] = Array(CHA, RTA)

    override def frameworkName(): String = "SootUp"

    override def serializeCG(
        algorithm: String,
        target: String,
        mainClass: String,
        classPath: Array[String],
        JDKPath: String,
        analyzeJDK: Boolean,
        outputFile: String
    ): Long = {

        val identifierFactory = JavaIdentifierFactory.getInstance()
        val inputLocation = new JavaClassPathAnalysisInputLocation(target)
        val rtJar = new JavaClassPathAnalysisInputLocation(System.getProperty("java.home") + "/lib/rt.jar")
        val javaVersion = new JavaLanguage(8);
        val javaProject = JavaProject.builder(javaVersion)
                                     .addInputLocation(inputLocation)
                                     .addInputLocation(rtJar)
                                     .build()

        val view = javaProject.createFullView()
        val typeHierarchy = new ViewTypeHierarchy(view)
        val entryMethods = if (mainClass != null) {
            val classType = identifierFactory.getClassType(mainClass)
            val entryMethod = identifierFactory.getMethodSignature(classType,
                                                                   "main",
                                                                   "void",
                                                                   Collections.singletonList("java.lang.String[]"))
            Collections.singletonList(entryMethod)

        } else {
            view.getClasses.asScala.toList
                .flatMap(className => className.getMethods.asScala.toList)
                .map(_.getSignature).asJava
        }

        val cgAlgorithm = algorithm match {
            case CHA => new ClassHierarchyAnalysisAlgorithm(view, typeHierarchy)
//            case VTA => new ClassHierarchyAnalysisAlgorithm(view, typeHierarchy)
//            case SPARK => new ClassHierarchyAnalysisAlgorithm(view, typeHierarchy)
            case RTA => new RapidTypeAnalysisAlgorithm(view, typeHierarchy)
            case _ => throw new IllegalArgumentException(s"unknown algorithm $algorithm")
        }

        var after: Long = 0L;
        val before = System.nanoTime
        val cg = cgAlgorithm.initialize(entryMethods)
//        val cg = if (algorithm.contains(VTA)) {
//            val spark = new Spark.Builder(view, tempCG).vta(true).build
//            spark.analyze()
//            after = System.nanoTime
//            spark.getCallGraph
//        } else if (algorithm.contains(SPARK)) {
//            val spark = new Spark.Builder(view, tempCG).build
//            spark.analyze()
//            after = System.nanoTime
//            spark.getCallGraph
//        } else {
//            after = System.nanoTime
//            tempCG
//        }
        after = System.nanoTime
        val worklist = mutable.Queue(entryMethods.asScala: _*)
        val processed = mutable.Set(worklist: _*)

        var reachableMethods = Set.empty[ReachableMethod]

        while (worklist.nonEmpty) {
            val entryMethod = worklist.dequeue()
            var callSitesMap = Map.empty[(MethodSignature, Int), Set[MethodSignature]]
            for (target <- cg.callsFrom(entryMethod).asScala) {
                val key = getCallSite(view, entryMethod, target)
                val targets = callSitesMap.getOrElse(key, Set.empty)
                callSitesMap = callSitesMap.updated(key, targets + target)
                if (!processed.contains(target)) {
                    worklist += target
                    processed += target
                }
            }

            val callSites = callSitesMap.map {
                case ((declaredTarget, lineNo), targets) => CallSite(createMethodObject(declaredTarget), lineNo,
                                                                     None, targets.map(createMethodObject))
            }.toSet

            val method = createMethodObject(entryMethod)
            reachableMethods += ReachableMethod(method, callSites)
        }

        val file: FileWriter = new FileWriter(outputFile)
        file.write(Json.prettyPrint(Json.toJson(ReachableMethods(reachableMethods))))
        file.flush()
        file.close()

        after - before

    }

    private def createMethodObject(method: MethodSignature): Method = {
        val name = method.getName
        val declaringClass = getJVMType(method.getDeclClassType)
        val returnType = getJVMType(method.getType)
        val paramTypes = method.getParameterTypes.asScala.map(getJVMType).toList

        Method(name, declaringClass, returnType, paramTypes)
    }

    private def getJVMType(nonJVMType: Type): String = {
        if (nonJVMType.isInstanceOf[VoidType]) "V"
        else if (nonJVMType.isInstanceOf[DoubleType]) "D"
        else if (nonJVMType.isInstanceOf[FloatType]) "F"
        else if (nonJVMType.isInstanceOf[IntType]) "I"
        else if (nonJVMType.isInstanceOf[ByteType]) "B"
        else if (nonJVMType.isInstanceOf[ShortType]) "S"
        else if (nonJVMType.isInstanceOf[CharType]) "C"
        else if (nonJVMType.isInstanceOf[BooleanType]) "Z"
        else if (nonJVMType.isInstanceOf[LongType]) "J"
        else if (nonJVMType.isInstanceOf[ArrayType]) "[" + getJVMType(nonJVMType.asInstanceOf[ArrayType].getBaseType)
        else if (nonJVMType.isInstanceOf[ClassType]) "L" + nonJVMType.toString.replace(".", "/") + ";"
        else throw new RuntimeException("Invalid type: " + nonJVMType.getClass.toString)
    }

    /**
     * Returns true if the static analysis framework supports
     * finding the line number of a program statement */
    override def locationSupport(): java.lang.Boolean = true

    private def getCallSite(
        view: JavaView,
        entryMethod: MethodSignature,
        target: MethodSignature
    ): (MethodSignature, Int) = {
        val stmts = view.getClass(entryMethod.getDeclClassType).get().
                        getMethod(entryMethod.getSubSignature).get().
                        getBody.getStmts.asScala
        val invokeStmts = stmts.filter(stmt => stmt.containsInvokeExpr())
        for(invokeStmt <- invokeStmts) {
            val methodSignature = invokeStmt.getInvokeExpr.getMethodSignature
            if(methodSignature.getSubSignature.toString.equals(target.getSubSignature.toString)) {
                return invokeStmt.getInvokeExpr.getMethodSignature -> invokeStmt.getPositionInfo.getStmtPosition.getFirstLine
            }
        }
        target -> -1
    }
}