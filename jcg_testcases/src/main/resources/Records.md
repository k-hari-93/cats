# Records
Records were introduced in Java 14 to ease the burden of writing getters, setters etc. 
for data classes. With records, the JVM provides these methods.

## Record1
[//]: # (MAIN: rc.Class)
Test whether a call to the record constructor is correctly resolved
```java
// rc/Class.java
package rc;

import lib.annotations.callgraph.DirectCall;

record Thing (String attr1) {
    public Thing(String attr1) {
        this.attr1 = attr1;
        }
    }

class Class {
    @DirectCall(name = "Thing", line = 14, resolvedTargets = "Lrc/Thing;")
    public static void main(String[] args) {
        Thing thing = new Thing("attribute");
    }
}
```
[//]: # (END)