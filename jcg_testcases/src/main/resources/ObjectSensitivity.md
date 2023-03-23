# Object Sensitivity Tests
Tests to check whether different objects can be distinguished by the CG construction algorithm.

## Object1
[//]: # (MAIN: obj.Class)
Test to check if objects of sibling classes can be correctly distinguished.
```java
// obj/Class.java
package obj;

import lib.annotations.callgraph.DirectCall;
class Superclass {
    void method() {
        //do something
    }
}
class Subclass1 extends Superclass {
    void method() {
        //do something
    }
}
class Subclass2 extends Superclass {
    void method() {
        //do something
    }
}
class Class {

    @DirectCall(name = "method", line = 25, resolvedTargets = "Lobj/Subclass1;" , prohibitedTargets = {"Lobj/Superclass;", "Lobj/Subclass2;"})
    public static void main(String[] args) {
        Superclass clz1 = new Subclass1();
        Superclass clz2 = new Subclass2();
        clz1.method();
    }
}
```
[//]: # (END)


