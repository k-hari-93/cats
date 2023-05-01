# Records
Records were introduced in Java 14 to ease the burden of writing getters, setters etc. for 
data classes. With records, the JVM provides these methods.

## Record1
[//]: # (MAIN: record.Class)
Test whether a call to the record constructor is correctly resolved
```java
package rc;

import lib.annotations.callgraph.DirectCall;

record Thing (String attr1) {
    public Thing(String attr1) {
        this.attr1 = attr1;
        }
    }

class Class {
    public static void main(String[] args) {
        @DirectCall(name = "Thing", line = 14, resolvedTargets = "Lrc/Thing;")
        Thing thing = new Thing("attribute");
    }
}

```