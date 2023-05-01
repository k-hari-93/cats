## NVC1
[//]: # (MAIN: nvc.Class)
Tests the resolution of a static method call when another static method with the same name but
different signature is presence.
```java
// nvc/Class.java
package nvc;

import lib.annotations.callgraph.DirectCall;

class Class {

    public static void method(){ /* do something*/}
    public static void method(int param){ /* do something*/}

    @DirectCall(name = "method", line = 12, resolvedTargets = "Lnvc/Class;", prohibitedTargets = "Lnvc/Class;", ptParameterTypes = { int.class })
    public static void main(String[] args){
        Class.method();
    }
}
```
[//]: # (END)

## NVC3
[//]: # (MAIN: nvc.Class)
Tests the resolution of a private method call when another method with the same name but
different signature is presence.
```java
// nvc/Class.java
package nvc;

import lib.annotations.callgraph.DirectCall;

class Class {

    private void method(){ /* do something*/}
    private void method(int num){ /* do something*/}

    @DirectCall(name = "method", line = 13, resolvedTargets = "Lnvc/Class;", prohibitedTargets = "Lnvc/Class;", ptParameterTypes = { int.class })
    public static void main(String[] args){
        Class cls = new Class();
        cls.method();
    }
}
```
[//]: # (END)

## NVC5
[//]: # (MAIN: nvc.Demo)
Tests the resolution of a super call in a larger type hierarchy. In a class hierarchy like below,
with ```nvc.Sub <: nvc.Middle <: nvc.Super```, the super call in ```nvc.Sub.method``` will always invoke
```nvc.Middle.method``` even if ```Sub``` was compiled when ```nvc.Middle``` did not yet have an
implementation of ```method``` and thus the ```invokespecial``` references ```nvc.Super```.
```java
// nvc/Demo.java
package nvc;

import lib.annotations.callgraph.DirectCall;

public class Demo {
    
    public static void main(String[] args){
      new Sub().method();
    }
}

class Super { 
    
    void method() { /* doSomething */ } 
}

class Middle extends Super {
    
    void method() { /* doSomething */ }
}

class Sub extends Middle {
    
    @DirectCall(name="method", line=26, resolvedTargets = "Lnvc/Middle;", prohibitedTargets = "Lnvc/Super;")
    void method() { 
        super.method(); 
    }
}
```
[//]: # (END)
