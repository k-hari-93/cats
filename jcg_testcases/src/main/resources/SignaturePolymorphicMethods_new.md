
## SPM1
[//]: # (MAIN: spm1.Class)
This tests checks whether a static method call to a method with polymorphic signature is correctly performed.
The ```MethodHandle``` is first retrieved via ```MethodHandles.lookup().findStatic(..)``` and then
invoked via the MethodHandle's ```invoke``` method. The passed parameter must be casted to
match the called method's signature.
```java
// spm1/Class.java
package spm1;

import lib.annotations.callgraph.IndirectCall;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

class Class {
    
       public static void method(int i){
           /* do Something */
       }
   
       public static void method(byte i){
           /* do Something */
       }
       
       @IndirectCall(
            name = "method", returnType = void.class, rtParameterTypes = {int.class}, 
            line = 26, resolvedTargets = "Lspm1/Class;", prohibitedTargets = "Lspm1/Class;", ptParameterTypes = { byte.class })
       public static void main(String[] args) throws Throwable {
           MethodType descriptor = MethodType.methodType(void.class, int.class);
           MethodHandle mh = MethodHandles.lookup().findStatic(Class.class, "method", descriptor);
           byte castMeToInt = 42;
           mh.invoke(castMeToInt);
       }
}
```
[//]: # (END)

## SPM2
[//]: # (MAIN: spm2.Class)
This tests checks whether a static method call to a method with polymorphic signature is correctly performed.
The ```MethodHandle``` is first retrieved via ```MethodHandles.lookup().findStatic(..)``` and then
invoked via the MethodHandle's ```invoke``` method. The passed parameter must be unboxed to
match the called method's signature.
```java
// spm2/Class.java
package spm2;

import lib.annotations.callgraph.IndirectCall;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

class Class {
    
       public static void method(int i){
           /* do Something */
       }
   
       public static void method(Integer i){
           /* do Something */
       }
       
       @IndirectCall(
            name = "method", returnType = void.class, rtParameterTypes = {int.class}, line = 26,
            resolvedTargets = "Lspm2/Class;", prohibitedTargets = "Lspm2/Class;", ptParameterTypes = {Integer.class})
       public static void main(String[] args) throws Throwable {
           MethodType descriptor = MethodType.methodType(void.class, int.class);
           MethodHandle mh = MethodHandles.lookup().findStatic(Class.class, "method", descriptor);
           Integer unboxMeToInt = 42;
           mh.invoke(unboxMeToInt);
       }
}
```
[//]: # (END)

## SPM3
[//]: # (MAIN: spm3.Class)
This tests checks whether a static method call to a method with polymorphic signature is correctly performed.
The ```MethodHandle``` is first retrieved via ```MethodHandles.lookup().findStatic(..)``` and then
invoked via the MethodHandle's ```invoke``` method. The passed parameter must be widened to
match the called method's signature.
```java
// spm3/Class.java
package spm3;

import lib.annotations.callgraph.IndirectCall;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

class Class {
    
       public static void method(MyObject mo){
           /* do Something */
       }
   
       public static void method(MyString ms){
           /* do Something */
       }
       
       @IndirectCall(
            name = "method", returnType = void.class, rtParameterTypes = {MyObject.class}, line = 26,
            resolvedTargets = "Lspm3/Class;", prohibitedTargets = "Lspm3/Class;", ptParameterTypes = {MyString.class})
       public static void main(String[] args) throws Throwable {
           MethodType descriptor = MethodType.methodType(void.class, MyObject.class);
           MethodHandle mh = MethodHandles.lookup().findStatic(Class.class, "method", descriptor);
           MyString widenMe = new MyString();
           mh.invoke(widenMe);
       }
}

class MyObject {}
final class MyString extends MyObject {}
```
[//]: # (END)
