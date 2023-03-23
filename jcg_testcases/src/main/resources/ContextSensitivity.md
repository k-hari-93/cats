# Context Sensitivity Tests
Tests to evaluate whether the calling contexts can be distinguished under different conditions

## Context1
[//]: # (MAIN: ctx.Class)
Test to check whether return parameters are assigned correctly.
```java
// ctx/Class.java
package ctx;

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
    
    @DirectCall(name = "method", line = 26, resolvedTargets = "Lctx/Subclass1;" , prohibitedTargets = {"Lctx/Superclass;", "Lctx/Subclass2;"})
    @DirectCall(name = "method", line = 28, resolvedTargets = "Lctx/Subclass1;" , prohibitedTargets = {"Lctx/Superclass;", "Lctx/Subclass2;"})
    public static void main(String[] args) {
        Superclass clz;
        clz = assignObj(new Subclass1());
        clz.method();
        clz = assignObj(new Subclass2());
        clz.method();
    }
    
    private static Superclass assignObj(Superclass c) {
        return c;
    }
}
```
[//]: # (END)

## Context2
[//]: # (MAIN: ctx.Class)
Test to check whether return parameters are assigned correctly when calls are deeply nested.
```java
// ctx/Class.java
package ctx;

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
    
    @DirectCall(name = "method", line = 25, resolvedTargets = "Lctx/Subclass1;" , prohibitedTargets = {"Lctx/Superclass;", "Lctx/Subclass2;"})
    public static void main(String[] args) {
        Superclass clz1 = assignObj(new Subclass1());
        Superclass clz2 = assignObj(new Subclass2());
        clz1.method();
    }
    
    private static Superclass assignObj(Superclass c) {
        return m1(c);
    }
    private static Superclass m1(Superclass c) {
        return m2(c);
    }private static Superclass m2(Superclass c) {
        return m3(c);
    }private static Superclass m3(Superclass c) {
        return m4(c);
    }private static Superclass m4(Superclass c) {
        return m5(c);
    }private static Superclass m5(Superclass c) {
        return c;
    }
}
```
[//]: # (END)

## Context3
[//]: # (MAIN: ctx.Class)
Test to check whether overloaded methods( by parameters ) can be identified correctly.
```java
// ctx/Class.java
package ctx;

import lib.annotations.callgraph.DirectCall;
class A {
    void method(String a) {
        //do something
    }
    void method(int a) {
        //do something
    }
}

class Class {
    
    @DirectCall(name = "method", line = 19, resolvedTargets = "Lctx/A;", rtParameterTypes = { Integer.class },
            prohibitedTargets = "Lctx/A;", ptParameterTypes = { String.class })
    public static void main(String[] args) {
        A a = new A();
        a.method(2);
    }
}
```
[//]: # (END)
