# Context Sensitivity Tests
Tests to evaluate whether the calling contexts can be distinguished under different conditions

## Context1
[//]: # (MAIN: ctx.Class)
Test to check whether return parameters are assigned correctly.

```java
// ctx/Class.java
package ctx;

import lib.annotations.callgraph.DirectCall;
import lib.annotations.callgraph.DirectCalls;

class Class {
    @DirectCalls({
            @DirectCall(name = "method", line = 13, resolvedTargets = "Lctx/Subclass1;", prohibitedTargets = {"Lctx/Superclass;"}),
            @DirectCall(name = "method", line = 15, resolvedTargets = "Lctx/Superclass;", prohibitedTargets = {"Lctx/Subclass1;"})})
    public static void main(String[] args) {
        Superclass clz, clz1;
        clz = assignObj(new Subclass1());
        clz.method();
        clz1 = assignObj(new Superclass());
        clz1.method();
    }

    private static Superclass assignObj(Superclass c) {
        return c;
    }
}

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

```
[//]: # (END)

## Context2
[//]: # (MAIN: ctx.Class)
Test to check whether return parameters are assigned correctly when calls are deeply nested.
```java
// ctx/Class.java
package ctx;

import lib.annotations.callgraph.DirectCall;

class Class {

    @DirectCall(name = "method", line = 11, resolvedTargets = "Lctx/Subclass1;" , prohibitedTargets = {"Lctx/Superclass;"})
    public static void main(String[] args) {
        Superclass clz1 = assignObj(new Subclass1());
        Superclass clz2 = assignObj(new Superclass());
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

```
[//]: # (END)

## Context3
[//]: # (MAIN: ctx.Class)
Test to check whether overloaded methods( by parameters ) can be identified correctly.
```java
// ctx/Class.java
package ctx;

import lib.annotations.callgraph.DirectCall;

class Class {

    @DirectCall(name = "method", line = 11, resolvedTargets = "Lctx/A;", rtParameterTypes = { int.class },
            prohibitedTargets = "Lctx/A;", ptParameterTypes = { String.class })
    public static void main(String[] args) {
        A a = new A();
        a.method(2);
    }
}
class A {
    void method(String a) {
        //do something
    }
    void method(int a) {
        //do something
    }
}
```
[//]: # (END)

## Context4
[//]: # (MAIN: ctx.Class)
Test to check whether overloaded methods( by number of parameters ) can be identified correctly.
```java
// ctx/Class.java
package ctx;

import lib.annotations.callgraph.DirectCall;

class Class {

    @DirectCall(name = "method", line = 11, resolvedTargets = "Lctx/A;", rtParameterTypes = { String.class },
            prohibitedTargets = "Lctx/A;", ptParameterTypes = { String.class, String.class })
    public static void main(String[] args) {
        A a = new A();
        a.method("Hi!!");
    }
}
class A {
    void method(String a, String b) {
        //do something
    }
    void method(String a) {
        //do something
    }
}
```
[//]: # (END)


## Context5
[//]: # (MAIN: ctx.Class)
Test to check whether parameters are considered.
```java
// ctx/Class.java
package ctx;

import lib.annotations.callgraph.DirectCall;

class Class {

    public static void main(String[] args) {
        assignObj(new Subclass1());
    }

    @DirectCall(name = "method", line = 13, resolvedTargets = "Lctx/Subclass1;" , prohibitedTargets = {"Lctx/Superclass;"})
    private static void assignObj(Superclass c) {
        c.method();
    }
}
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
```
[//]: # (END)
