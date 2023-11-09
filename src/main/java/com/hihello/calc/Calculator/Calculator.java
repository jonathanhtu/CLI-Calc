package com.hihello.calc.Calculator;

import java.util.Arrays;
import java.util.Scanner;

public class Calculator {

    Scanner ins;
    private Double total = 0.0;
    private String[] currentCache = new String[0];

    public Calculator() {
        ins = new Scanner(System.in);
    }

    /*
     * Runs CLI calculator application.
     */
    public void start() {
        String input;

        System.out.print("> ");

        input = ins.next();

        // TODO BUG: Sometimes, these do not trigger and the illegal argument exception for letters gets triggered instead.
        if (CalcUtils.ACTION_CLEAR.equals(input)) {
            total = 0.0;
            currentCache = new String[0];
            System.out.println(total);
        } else if (CalcUtils.ACTION_EXIT.equals(input)) {
            return;
        } else if (CalcUtils.ACTION_HELP.equals(input)) {
            CalcUtils.printInstructionLines();
        } else if (CalcUtils.OPERATOR_EQUALS.equals(input.substring(input.length() - 1))) {
            if (input.length() == 1) {
                calculateResult(currentCache);
                start();
            } else {
                CalcUtils.validateNoLetters(input);
                String[] inputArray = input.substring(0, input.length() - 1)
                        .split("((?<=[^0-9'])|(?=[^0-9']))");
                currentCache = CalcUtils.concatenateArrays(currentCache, inputArray);
                calculateResult(currentCache);
            }
        } else {
            // TODO: This should print the last element of the array to match doc specifications.
            // TODO BUG: This may not work if the first element is not an operator.
            CalcUtils.validateNoLetters(input);
            String[] inputArray = input.split("((?<=[^0-9'])|(?=[^0-9']))");
            currentCache = CalcUtils.concatenateArrays(currentCache, inputArray);
        }


        // Keep running the calculator if the user has not exited.
        start();
    }

    private void calculateResult(String[] inputArray) {

        // First input must be an operator, ignore first input if it is not an operator.
        // TODO: Account for first input being a negative number.
        if (!CalcUtils.isOperator(inputArray[0])) {
            total = Double.valueOf(inputArray[0]);
            inputArray = Arrays.copyOfRange(inputArray, 1, inputArray.length);
        }

        // PEMDAS - Handle mult/div first, then add/sub
        inputArray = CalcUtils.performMultiplicationAndDivision(inputArray, total);

        // TODO: Account for first input being a negative number.
        if (!CalcUtils.isOperator(inputArray[0])) {
            total = Double.valueOf(inputArray[0]);
            inputArray = Arrays.copyOfRange(inputArray, 1, inputArray.length);
        }
        total = CalcUtils.performAdditionAndSubtraction(inputArray, total);

        System.out.println(total);
    }
}
