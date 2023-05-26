# Object Sensitivity Tests
Tests to check whether different objects can be distinguished by the CG construction algorithm.

## Object1
[//]: # (MAIN: obj.Class)
Test to check if objects of sibling classes can be correctly distinguished.
```java
// obj/Class.java
package obj;

import lib.annotations.callgraph.DirectCall;

class Class {

    @DirectCall(name = "method", line = 11, resolvedTargets = "Lobj/Subclass1;" , prohibitedTargets = {"Lobj/Superclass;", "Lobj/Subclass2;"})
    public static void main(String[] args) {
        Superclass clz1 = new Subclass1();
        Superclass clz2 = new Subclass2();
        clz1.method();
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
class Subclass2 extends Superclass {
    void method() {
        //do something
    }
}
```
[//]: # (END)

## Object2
[//]: # (MAIN: obj.Class)
Test to check if the correct object and the resulting value is considered.
```java
// obj/Class.java
package obj;

import lib.annotations.callgraph.DirectCall;

class Class {

    @DirectCall(name = "method", line = 11, resolvedTargets = "Lobj/Subclass1;" , prohibitedTargets = {"Lobj/Superclass;", "Lobj/Subclass2;"})
    public static void main(String[] args) {
        Test clz = new Test(new Subclass2());
        Test clz1 = new Test(new Subclass1());
        clz1.a.method();
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

class Subclass2 extends Superclass {
    void method() {
        //do something
    }
}

class Test {
    Superclass a;
    
    public Test(Superclass a) {
        this.a = a;
    }
}
```
[//]: # (END)

## Object3
[//]: # (MAIN: obj.Class)
Test to check if aliases are considered
```java
// obj/Class.java
package obj;

import lib.annotations.callgraph.DirectCall;

class Class {

    @DirectCall(name = "method", line = 11, resolvedTargets = "Lobj/Subclass1;" , prohibitedTargets = {"Lobj/Superclass;"})
    public static void main(String[] args) {
        Superclass clz = new Subclass1();
        Superclass clz1 = clz;
        clz1.method();
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

## Object4
[//]: # (MAIN: obj.Class)
Test to check if aliases are considered
```java
// obj/Class.java
package obj;

import lib.annotations.callgraph.DirectCall;

class Class {

    @DirectCall(name = "method", line = 12, resolvedTargets = "Lobj/Subclass1;" , prohibitedTargets = {"Lobj/Superclass;"})
    public static void main(String[] args) {
        Test clz = new Test(new Subclass1());
        Test clz1 = clz;
        Superclass clz2 = clz1.getA();
        clz2.method();
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

class Test {
    Superclass a;

    public Test(Superclass a) {
        this.a = a;
    }
    
    Superclass getA() {
        return this.a;
    }
}
```
[//]: # (END)
