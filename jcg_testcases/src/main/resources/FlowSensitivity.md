# Flow Sensitivity Tests
Tests to evaluate whether CG construction algorithms are flow sensitive.


## Flow1
[//]: # (MAIN: flow.Class)
Test to check if object re-assignments are tracked.
```java
// flow/Class.java
package flow;

import lib.annotations.callgraph.DirectCall;

class Class {
    @DirectCall(name = "method", line = 11, resolvedTargets = "Lflow/Subclass1;" ,
            prohibitedTargets = {"Lflow/Superclass;"})
    public static void main(String[] args){
        Superclass clz = new Superclass();
        clz = new Subclass1();
        clz.method();
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

## Flow2
[//]: # (MAIN: flow.Class)
Test to check if the object assignment after method call is ignored.
```java
// flow/Class.java
package flow;

import lib.annotations.callgraph.DirectCall;

class Class {
    @DirectCall(name = "method", line = 10, resolvedTargets = "Lflow/Subclass1;" ,
            prohibitedTargets = {"Lflow/Superclass;"})
    public static void main(String[] args){
        Superclass clz = new Subclass1();
        clz.method();
        clz = new Superclass();
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

## Flow3
[//]: # (MAIN: flow.Class)
Test to check if object re-assignments are tracked.
```java
// flow/Class.java
package flow;

import lib.annotations.callgraph.DirectCall;

class Class {
    @DirectCall(name = "method", line = 12, resolvedTargets = "Lflow/Subclass1;" ,
            prohibitedTargets = {"Lflow/Superclass;"})
    public static void main(String[] args){
        Superclass clz = new Superclass();
        Superclass clz1 = new Subclass1();
        clz = clz1;
        clz.method();
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

## Flow4
[//]: # (MAIN: flow.Class)
Test to check if the object assignment after method call is ignored.
```java
// flow/Class.java
package flow;

import lib.annotations.callgraph.DirectCall;

class Class {
    @DirectCall(name = "method", line = 11, resolvedTargets = "Lflow/Subclass1;" ,
            prohibitedTargets = {"Lflow/Superclass;"})
    public static void main(String[] args){
        Superclass clz = new Subclass1();
        Superclass clz1 = new Superclass();
        clz.method();
        clz = clz1;
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

## Flow5
[//]: # (MAIN: flow.Class)
Test to check if the object assignment in the cleanup block is correctly evaluated.
```java
// flow/Class.java
package flow;

import lib.annotations.callgraph.DirectCall;

class Class {
    @DirectCall(name = "method", line = 17, resolvedTargets = "Lflow/Subclass1;" ,
            prohibitedTargets = {"Lflow/Superclass;"})
    public static void main(String[] args){
        Superclass clz;
        try {
            clz = new Superclass();
        } catch (Exception e) {

        } finally {
            clz = new Subclass1();
        }
        clz.method();
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

## Flow6
[//]: # (MAIN: flow.Class)
Test to check if the object assignment in the exception block is correctly evaluated.
```java
// flow/Class.java
package flow;

import lib.annotations.callgraph.DirectCall;

class Class {
    @DirectCall(name = "method", line = 16, resolvedTargets = "Lflow/Subclass1;" ,
            prohibitedTargets = {"Lflow/Superclass;"})
    public static void main(String[] args){
        Superclass clz;
        try {
            clz = new Superclass();
            throw new Exception("error");
        } catch (Exception e) {
            clz = new Subclass1();
        }
        clz.method();
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

## Flow7
[//]: # (MAIN: flow.Class)
Test to check if branches are correctly evaluated.
```java
// flow/Class.java
package flow;

import lib.annotations.callgraph.DirectCall;

class Class {
    private static boolean isEven(int n) {
        return n%2 == 0;
    }
    @DirectCall(name = "method", line = 14, resolvedTargets = "Lflow/Subclass1;" ,
            prohibitedTargets = {"Lflow/Superclass;", "Lflow/Subclass2;"})
    public static void main(String[] args){
        Superclass clz = new Subclass2();
        if(isEven(3)){
            clz = new Superclass();
        }
        else{
            clz = new Subclass1();
        }
        clz.method();
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




