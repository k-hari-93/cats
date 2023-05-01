# Private Interface Method Calls
Java 9 introduced private interface methods. Interface methods are public by default. 
Class implementing an interface cannot access its private methods directly though the 
objects the class. Access to private interface methods is restricted to the interface 
methods.


## PIMC1
[//]: # (MAIN: pimc.Class)
Test whether a call to the private interface method is resolved correctly 
```java
package pimc;

import lib.annotations.callgraph.DirectCall;
public interface Interface {
    void method() {
        @DirectCall(name = "privateMethod", line = 7, resolvedTargets = "Lpimc/Interface;")
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
## PIMC2
[//]: # (MAIN: pimc.Class)
Test whether a call to the private interface method is resolved correctly in presence of overloading
```java
package pimc;

import lib.annotations.callgraph.DirectCall;
public interface Interface {
    void method() {
        @DirectCall(name = "privateMethod", line = 8, resolvedTargets = "Lpimc/Interface;", 
                prohibitedTargets = "Lpimc/Interface", ptParameterTypes = { int.class })
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
