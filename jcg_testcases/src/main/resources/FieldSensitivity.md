# Field Sensitivity Tests
Tests to check if objects assigned to fields are resolved correctly.

## Field1
[//]: # (MAIN: field.Class)
Test to check if a chain of field accesses can be resolved correctly.
```java
// field/Class.java
package field;

import lib.annotations.callgraph.DirectCall;
class A {
    void method() {
        //do something
    }
}
class B {
    A a = new A();
    void method() {
        //do something
    }
}
class C {
    B b = new B();
    void method() {
        //do something
    }
}
class Class {

    @DirectCall(name = "method", line = 28, resolvedTargets = "Lfield/A;" , prohibitedTargets = {"Lfield/B;", "Lfield/C;"})
    public static void main(String[] args){
        A a = new A();
        B b = new B();
        C c = new C();
        c.b.a.method();
    }
}
```
[//]: # (END)

## Field2
[//]: # (MAIN: field.Class)
Test to check if field stores performed in called methods are handled correctly.
```java
// field/Class.java
package field;

import lib.annotations.callgraph.DirectCall;
class A {
    void method() {
        //do something
    }
}
class B {
    A a;
    void method() {
        //do something
    }
}

class Class {

    @DirectCall(name = "method", line = 23, resolvedTargets = "Lfield/A;", prohibitedTargets = {"Lfield/B;"})
    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        setField(b);
        b.a.method();
    }
    private static void setField(B b) {
        b.a = new A();
    }
}
```
[//]: # (END)

## Field3
[//]: # (MAIN: field.Class)
Test whether aliasing is handled correctly for field access.
```java
// field/Class.java
package field;

import lib.annotations.callgraph.DirectCall;

class Superclass {
    void method() {
        //do something
    }
}
class A extends Superclass {
    void method() {
        //do something
    }
}
class B extends Superclass {
    void method() {
        //do something
    }
}
class Test {
    Superclass a;
}
class Class {

    @DirectCall(name = "method", line = 33, resolvedTargets = "Lfield/A;", prohibitedTargets = {"Lfield/B;", "Lfield/Superclass;"})
    public static void main(String[] args){
        Test x  = new Test();
        Test y = new Test();
        x.a = new Superclass();
        y.a = new B();
        x = y;
        y.a = new A();
        x.a.method();
    }
}
```
[//]: # (END)

