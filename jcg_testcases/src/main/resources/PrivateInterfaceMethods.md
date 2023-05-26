# Private Interface Method Calls
Java 9 introduced private interface methods. Interface methods are public by default. 
Class implementing an interface cannot access its private methods directly though the 
objects the class. Access to private interface methods is restricted to the interface 
methods.


## PIMC1
[//]: # (MAIN: pimc.Class)
Test whether a call to the private interface method is resolved correctly 
```java
// pimc/Class.java
package pimc;

import lib.annotations.callgraph.DirectCall;
interface Interface {
    @DirectCall(name = "privateMethod", line = 7, resolvedTargets = "Lpimc/Interface;")
    default void method() {
        privateMethod();
    }
    private void privateMethod () {
        //do something
    }
}

class Class implements Interface {
    public static void main(String[] args) {
        Class obj = new Class();
        obj.method();
    }
}
```
[//]: # (END)

## PIMC2
[//]: # (MAIN: pimc.Class)
Test whether a call to the private interface method is resolved correctly in presence of overloading
```java
// pimc/Class.java
package pimc;

import lib.annotations.callgraph.DirectCall;
interface Interface {
    @DirectCall(name = "privateMethod", line = 8, resolvedTargets = "Lpimc/Interface;",
            prohibitedTargets = "Lpimc/Interface", ptParameterTypes = { int.class })
    default void method() {
        privateMethod();
    }
    private void privateMethod () {
        //do something
    }
    private void privateMethod (int i) {
        //do something
    }
}

class Class implements Interface {
    public static void main(String[] args) {
        Class obj = new Class();
        obj.method();
    }
}
```
[//]: # (END)
