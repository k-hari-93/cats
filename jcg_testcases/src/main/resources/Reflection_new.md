## TR7
[//]: # (MAIN: tr.Demo)
Tests a reflective method invocation that is performed on a class' private field that is retrieved via the
reflection API. In ```tr.Demo```'s main method a new ```tr.Demo``` object is created and an object
of type ```tr.CallTarget``` is assigned to its field. This field is then retrieved via the reflection
using ```java.lang.Class.getDeclaredField(<fieldName>)``` and the field's name, namely ```"field"```.
```java.lang.reflect.Field.get``` is then used to get the object stored within the field of the Demo
instance that has been created previously. Afterwards, the returned instance is used to call
the ```target``` method.
```java
// tr/Demo.java
package tr;

import java.lang.reflect.Field;
import lib.annotations.callgraph.IndirectCall;

public class Demo {
    private Target field;

    @IndirectCall(
        name = "target", line = 18, resolvedTargets = "Ltr/CallTarget;", prohibitedTargets = "Ltr/NeverInstantiated;"
    )
    public static void main(String[] args) throws Exception {
        Demo demo = new Demo();
        demo.field = new CallTarget();

        Field field = Demo.class.getDeclaredField("field");
        Target target = (Target) field.get(demo);
        target.target();
    }
}

interface Target {
    void target();
}

class CallTarget implements Target {
    public void target(){ /* do something */ }
}

class NeverInstantiated implements Target {
    public void target(){ /* do something */ }
}
```
[//]: # (END)

## TR8
[//]: # (MAIN: tr.Demo)
Tests a reflective method invocation that is performed on a class' public field that is retrieved via the
reflection API. In ```tr.Demo```'s main method a new ```tr.Demo``` object is created and an object
of type ```tr.CallTarget``` is assigned to its field. This field is then retrieved via the reflection
using ```java.lang.Class.getField(<fieldName>)``` and the field's name, namely ```"field"```.
```java.lang.reflect.Field.get``` is then used to get the object stored within the field of the Demo
instance that has been created previously. Afterwards, the returned instance is used to call
the ```target``` method.

```java
// tr/Demo.java
package tr;

import java.lang.reflect.Field;
import lib.annotations.callgraph.IndirectCall;

public class Demo {
    public Target field;

    @IndirectCall(
        name = "target", line = 18, resolvedTargets = "Ltr/CallTarget;", prohibitedTargets = "Ltr/NeverInstantiated;"
    )
    public static void main(String[] args) throws Exception {
        Demo demo = new Demo();
        demo.field = new CallTarget();

        Field field = Demo.class.getField("field");
        Target t = (Target) field.get(demo);
        t.target();
    }
}

interface Target {
    void target();
}

class CallTarget implements Target {
    public void target(){ /* do something */ }
}

class NeverInstantiated implements Target {
    public void target(){ /* do something */ }
}
```
[//]: # (END)
