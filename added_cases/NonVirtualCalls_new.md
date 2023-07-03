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