/*
 * BSD 2-Clause License:
 * Copyright (c) 2009 - 2016
 * Software Technology Group
 * Department of Computer Science
 * Technische Universität Darmstadt
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */
package lib;

import lib.annotations.callgraph.IndirectCall;
import lib.annotations.documentation.CGNote;
import lib.annotations.properties.EntryPoint;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static lib.annotations.callgraph.AnalysisMode.CPA;
import static lib.annotations.callgraph.AnalysisMode.OPA;
import static lib.annotations.documentation.CGCategory.REFLECTION;

/**
 * This abstract class models a binary arithmetic expression. It contains factory methods
 * for creating new binary expressions.
 * <p>
 * This class uses reflection in both of its methods. It uses the newInstance() method of
 * the Constructor class and invoke() method of the Method class.
 * <p>
 * <!--
 * <b>NOTE</b><br>
 * This class is not meant to be (automatically) recompiled; it just serves documentation
 * purposes.
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * INTENTIONALLY LEFT EMPTY TO MAKE SURE THAT THE SPECIFIED LINE NUMBERS ARE STABLE IF THE
 * CODE (E.G. IMPORTS) CHANGE.
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * -->
 *
 * @author Michael Eichberg
 * @author Michael Reif
 * @author Florian Kuebler
 */
public abstract class BinaryExpression implements Expression {

    public static final String FQN = "Llib/BinaryExpression;";

    abstract protected Expression left();

    abstract protected Expression right();

    abstract protected Operator operator();

    @EntryPoint(value = {OPA, CPA})
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @CGNote(value = REFLECTION, description = "a new instance is created by Java Reflection")
    @IndirectCall(name = "<init>", declaringClass = PlusOperator.FQN)
    @EntryPoint(value = {OPA, CPA})
    public static BinaryExpression createBasicBinaryExpression(
            String operator,
            final Expression left,
            final Expression right) throws Exception {
        Class<?> operatorClass = Class.forName(operator);
        final Operator op = (Operator) operatorClass.newInstance();

        return new BinaryExpression() {

            @Override
            @EntryPoint(value = {OPA, CPA})
            public Constant eval(Map<String, Constant> values) {
                throw new UnsupportedOperationException();
            }

            @Override
            @EntryPoint(value = {OPA, CPA})
            protected Expression left() {
                return left;
            }

            @Override
            @EntryPoint(value = {OPA, CPA})
            protected Expression right() {
                return right;
            }

            @Override
            @EntryPoint(value = {OPA, CPA})
            protected Operator operator() {
                return op;
            }
        };
    }


    @IndirectCall(name = "<init>", declaringClass = MultOperator.FQN)
    public static BinaryExpression createBasicMultExpression(
            final Expression left,
            final Expression right) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Class<?> operatorClass = Class.forName("lib.MultOperator");
        final Operator operator = (Operator) operatorClass.newInstance();
        return new BinaryExpression() {
            @Override
            protected Expression left() {
                return left;
            }

            @Override
            protected Expression right() {
                return right;
            }

            @Override
            protected Operator operator() {
                return operator;
            }

            @Override
            public Constant eval(Map<String, Constant> values) {
                throw new UnsupportedOperationException();
            }
        };
    }


    @CGNote(value = REFLECTION, description = "a (static) method is invoked by Java's reflection mechanism; the call graph has to handle reflection")
    @EntryPoint(value = {OPA, CPA})
    @IndirectCall(name = "createBinaryExpression", parameterTypes = {Expression.class, Expression.class}, returnType = BinaryExpression.class, declaringClass = PlusOperator.FQN)
    public static BinaryExpression createBinaryExpression(
            String operator,
            final Expression left,
            final Expression right) throws Exception {
        Class<?> operatorClass = null;
        try {
            operatorClass = Class.forName(operator);
            Method m = operatorClass.getDeclaredMethod("createBinaryExpression", Expression.class, Expression.class);
            return (BinaryExpression) m.invoke(null, left, right);
        } catch (ClassNotFoundException cnfe) {
            throw cnfe;
        }
    }

    @CGNote(value = REFLECTION, description = "a (static) method is invoked by Java's reflection mechanism; the call graph has to handle reflection")
    @EntryPoint(value = {OPA, CPA})
    @IndirectCall(name = "createBinaryExpression", parameterTypes = {Expression.class, Expression.class}, returnType = BinaryExpression.class, declaringClass = MinusOperator.FQN)
    @IndirectCall(name = "createBinaryExpression", parameterTypes = {Expression.class, Expression.class}, returnType = BinaryExpression.class, declaringClass = DivOperator.FQN)
    public static BinaryExpression createRandomBinaryExpression(
            final Expression left,
            final Expression right
    ) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String operator;
        if (System.currentTimeMillis() % 2 == 0)
            operator = "lib.MinusOperator";
        else
            operator = "lib.DivOperator";


        Class<?> operatorClass = Class.forName(operator);
        Method m = operatorClass.getDeclaredMethod("createBinaryExpression", Expression.class, Expression.class);
        return (BinaryExpression) m.invoke(null, left, right);
    }


    @IndirectCall(name = "createBinaryExpression", parameterTypes = {Expression.class, Expression.class}, returnType = BinaryExpression.class, declaringClass = MultOperator.FQN)
    public static BinaryExpression createMultExpression(
            final Expression left,
            final Expression right
    ) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> operatorClass = Class.forName("lib.MultOperator");

        Method m = operatorClass.getDeclaredMethod("createBinaryExpression", Expression.class, Expression.class);
        return (BinaryExpression) m.invoke(null, left, right);
    }
}

