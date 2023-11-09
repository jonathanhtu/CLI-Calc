package com.hihello.calc.Calculator;

import java.util.Scanner;

public class Calculator {

    Scanner ins;

    public Calculator() {
        ins = new Scanner(System.in);
    }

    public void start() {
        String input = "";

        while (input.equals("")) {
            System.out.println("Enter value...");
            input = ins.next();
        }
    }
}
