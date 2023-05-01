# Field Sensitivity Tests
Tests to check if objects assigned to fields are resolved correctly.

## Field1
[//]: # (MAIN: field.Class)
Test to check if a field accesses can be resolved correctly.
```java
// field/Class.java
package field;

import lib.annotations.callgraph.DirectCall;

class Class {

    @DirectCall(name = "method", line = 10, resolvedTargets = "Lfield/A;" , prohibitedTargets = {"Lfield/B;"})
    public static void main(String[] args){
        B b = new B();
        b.a.method();
    }
}
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
```
[//]: # (END)

## Field2
[//]: # (MAIN: field.Class)
Test to check if a chain of field accesses can be resolved correctly.
```java
// field/Class.java
package field;

import lib.annotations.callgraph.DirectCall;

class Class {

    @DirectCall(name = "method", line = 10, resolvedTargets = "Lfield/A;" , prohibitedTargets = {"Lfield/B;", "Lfield/C;"})
    public static void main(String[] args){
        C c = new C();
        c.b.a.method();
    }
}
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
```
[//]: # (END)

## Field3
[//]: # (MAIN: field.Class)
Test to check if field stores performed in called methods are handled correctly.
```java
// field/Class.java
package field;

import lib.annotations.callgraph.DirectCall;

class Class {

    @DirectCall(name = "method", line = 11, resolvedTargets = "Lfield/A;", prohibitedTargets = {"Lfield/B;"})
    public static void main(String[] args) {
        B b = new B();
        setField(b);
        b.a.method();
    }
    private static void setField(B b) {
        b.a = new A();
    }
}

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
```
[//]: # (END)

## Field4
[//]: # (MAIN: field.Class)
Test whether aliasing is handled correctly for field access.
```java
// field/Class.java
package field;

import lib.annotations.callgraph.DirectCall;

class Class {

    @DirectCall(name = "method", line = 12, resolvedTargets = "Lfield/A;", prohibitedTargets = {"Lfield/B;", "Lfield/Superclass;"})
    public static void main(String[] args){
        Test x  = new Test();
        Test y = x;
        x.a = new A();
        y.a.method();
    }
}

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

class Test {
    Superclass a;
}
```
[//]: # (END)

## Field5
[//]: # (MAIN: field.Class)
Test whether aliasing is handled correctly for field access.
```java
// field/Class.java
package field;

import lib.annotations.callgraph.DirectCall;

class Class {

    @DirectCall(name = "method", line = 15, resolvedTargets = "Lfield/A;", prohibitedTargets = {"Lfield/B;", "Lfield/Superclass;"})
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
```
[//]: # (END)