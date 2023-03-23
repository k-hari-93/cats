# Flow Sensitivity Tests
Tests to evaluate whether CG construction algorithms are flow sensitive.


## Flow1
[//]: # (MAIN: flow.Class)
Test to check if the object assignment after method call is ignored.
```java
// flow/Class.java
package flow;

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

class Someclass {
    void method() {
        //do something
    }
}
class Class {
    @DirectCall(name = "method", line = 30, resolvedTargets = "Lflow/Subclass1;" , 
            prohibitedTargets = {"Lflow/Superclass;", "Lflow/Subclass2;", "Lflow/Someclass;"})
    public static void main(String[] args){
        Superclass clz = new Subclass1();
        clz.method();
        clz = new Subclass2();
    }
}
```
[//]: # (END)

## Flow2
[//]: # (MAIN: flow.Class)
Test to check if the object assignment in the exception block is correctly ignored.
```java
// flow/Class.java
package flow;

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

class Someclass {
    void method() {
        //do something
    }
}
class Class {
    @DirectCall(name = "method", line = 34, resolvedTargets = "Lflow/Subclass1;" , 
            prohibitedTargets = {"Lflow/Superclass;", "Lflow/Subclass2;", "Lflow/Someclass;"})
    public static void main(String[] args){
        Superclass clz;
        try {
            clz = new Subclass1();
        } catch (Exception e) {
            clz = new Subclass2();
            }
        clz.method();
    }
}
```
[//]: # (END)

## Flow3
[//]: # (MAIN: flow.Class)
Test to check if the object assignment in the exception block is correctly evaluated.
```java
// flow/Class.java
package flow;

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

class Someclass {
    void method() {
        //do something
    }
}
class Class {
    @DirectCall(name = "method", line = 36, resolvedTargets = "Lflow/Subclass1;" , 
            prohibitedTargets = {"Lflow/Superclass;", "Lflow/Subclass2;", "Lflow/Someclass;"})
    public static void main(String[] args){
        Superclass clz;
        try {
            clz = new Subclass2();
            throw new Exception("error");
        } catch (Exception e) {
            clz = new Subclass1();
        }
        clz.method();
    }
}
```
[//]: # (END)

## Flow4
[//]: # (MAIN: flow.Class)
Test to check if the object assignment in the cleanup block is correctly evaluated.
```java
// flow/Class.java
package flow;

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

class Someclass {
    void method() {
        //do something
    }
}
class Class {
    @DirectCall(name = "method", line = 39, resolvedTargets = "Lflow/Subclass1;" , 
            prohibitedTargets = {"Lflow/Superclass;", "Lflow/Subclass2;", "Lflow/Someclass;"})
    public static void main(String[] args){
        Superclass clz;
        try {
            clz = new Subclass2();
        } catch (Exception e) {
            
        } finally {
            clz = new Subclass1();
        }
        clz.method();
    }
}
```
[//]: # (END)