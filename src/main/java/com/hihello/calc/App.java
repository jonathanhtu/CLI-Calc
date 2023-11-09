package com.hihello.calc;

import com.hihello.calc.Calculator.CalcUtils;
import com.hihello.calc.Calculator.Calculator;


/**
 * Simple CLI Calculator.
 *
 */
public class App {
    public static void main( String[] args ) {
        Calculator myCalc = new Calculator();

        System.out.println("Welcome to Simple Calc!");
        CalcUtils.printInstructionLines();

        myCalc.start();
    }
}
