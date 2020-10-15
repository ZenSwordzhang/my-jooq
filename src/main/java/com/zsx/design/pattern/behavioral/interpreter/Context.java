package com.zsx.design.pattern.behavioral.interpreter;

public class Context {

    public static Expression getMaleExpression(String name1, String name2){
        Expression robert = new TerminalExpression(name1);
        Expression john = new TerminalExpression(name2);
        return new OrExpression(robert, john);
    }

    public static Expression getMarriedWomanExpression(String name, String maritalStatus){
        Expression julie = new TerminalExpression(name);
        Expression married = new TerminalExpression(maritalStatus);
        return new AndExpression(julie, married);
    }
}
