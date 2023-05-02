## VC2
[//]: # (MAIN: vc.Class)
Tests a virtually dispatched method call when a simple type hierarchy is present.
```java
// vc/Class.java
package vc;

import lib.annotations.callgraph.DirectCall;

class Class {

    public void method(){ }

    @DirectCall(name = "method", line = 11, resolvedTargets = "Lvc/SubClass;", prohibitedTargets = "Lvc/Class;")
    public static void callMethod(Class cls) {
        cls.method();
    }

    public static void main(String[] args){
        callMethod(new SubClass());
    }
}

class SubClass extends Class {

    public void method() { }
}
```
[//]: # (END)

## VC4
[//]: # (MAIN: vc.Class)
Tests a virtually dispatched method call when the receiver is loaded from an array.
```java
// vc/Class.java
package vc;

import lib.annotations.callgraph.DirectCall;

interface Interface {
    void method();
}

class Class implements Interface {

    public static Interface[] types = new Interface[]{new Class(), new ClassImpl()};

    public void method(){ }

    @DirectCall(name = "method", line = 18, resolvedTargets = "Lvc/Class;", prohibitedTargets = "Lvc/ClassImpl;")
    public static void main(String[] args){
        Interface i = types[0];
        i.method();
    }
}

class ClassImpl implements Interface {
    public void method(){ }
}
```
[//]: # (END)