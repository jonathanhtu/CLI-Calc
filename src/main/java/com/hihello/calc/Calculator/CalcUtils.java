package com.hihello.calc.Calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalcUtils {

    protected static String OPERATOR_PLUS = "+";
    protected static String OPERATOR_MINUS = "-";
    protected static String OPERATOR_MULTIPLY = "*";
    protected static String OPERATOR_DIVIDE = "/";
    protected static String OPERATOR_EQUALS = "=";
    protected static String OPERATOR_NEGATE = "!";
    protected static String ACTION_CLEAR = "c";
    protected static String ACTION_EXIT = "exit";
    protected static String ACTION_HELP = "help";


    /*
     * Used to output instructions about how to use Simple Calc.
     */
    public static void printInstructionLines() {
        // TODO: Make these messages constants
        // TODO: Replace symbols with their variables
        System.out.println("Supported operations are addition (+), subtraction (-), multiplication (*), and division (/).");
        System.out.println("To add a negative sign, enter \"!\". (FEATURE COMING SOON)");
        System.out.println("To clear results, enter \"c\".");
        System.out.println("To close Simple Calc, enter \"exit\".");
        System.out.println("To repeat these instructions, enter \"help\".");
        System.out.println("Enter value...");
    }

    /*
     * Validate that calculator inputs do not include any letters.
     */
    protected static void validateNoLetters(String input) {
        boolean containsLetters = input.matches(".*[a-zA-Z]+.*");
        if (containsLetters) {
            throw new IllegalArgumentException("Letters are not allowed in inputs.");
        }
    }

    /*
     * Scan through the inputs and complete all multiplication and division operations.
     * Expects the first element to always be an operator
     */
    protected static String[] performMultiplicationAndDivision(String[] inputArray, Double total) {
        List<String> postOperationArray = new ArrayList<>();

        Double previous = total;
        String operator = "";
        boolean negate = false;
        for (int i = 0; i < inputArray.length; i++) {
            if (isOperator(inputArray[i])) {
                if (OPERATOR_PLUS.equals(inputArray[i]) || OPERATOR_MINUS.equals(inputArray[i])) {
                    // Note: An actual calculator functions by allowing users to press multiple operations, whereas this
                    // disallows entering multiple operators in a row.
                    if (!operator.isEmpty()) {
                        throw new IllegalArgumentException("Cannot have multiple operators in a row.");
                    } else {
                        if (!previous.equals(total)) {
                            postOperationArray.add(previous.toString());
                        }
                        postOperationArray.add(inputArray[i]);
                        previous = null;
                        negate = false;
                        operator = "";
                    }
                } else {
                    // Note: An actual calculator functions by allowing users to press multiple operations, whereas this
                    // disallows entering multiple operators in a row.
                    if (!operator.isEmpty()) {
                        throw new IllegalArgumentException("Cannot have multiple operators in a row.");
                    } else {
                        negate = false;
                        operator = inputArray[i];
                    }
                }
            } else if (OPERATOR_NEGATE.equals(inputArray[i])) {
                negate = true;
            } else {
                Double value = Double.valueOf(inputArray[i]);
                if (negate) {
                    value *= -1;
                    negate = false;
                }
                if (operator.isEmpty()) {
                    previous = value;
                } else {
                    previous = performOperation(operator, previous, value);
                    operator = "";
                }
            }
        }
        postOperationArray.add(previous.toString());
        return arrayToStringArray(postOperationArray);
    }

    /*
     * Scan through the inputs and complete all addition and subtraction operations.
     * Expects the first element to always be an operator.
     */
    protected static Double performAdditionAndSubtraction(String[] inputArray, Double total) {
        String operator = "";
        boolean negate = false;
        // TODO: Account for negative numbers.
        for (int i = 0; i < inputArray.length; i++) {
            // Assumes that all operators we see here are addition or subtraction, since multiplication and division
            // were done in a previous step. Make more robust by disallowing non-addition and subtraction operators.
            if (isOperator(inputArray[i])) {
                // Note: An actual calculator functions by allowing users to press multiple operations, whereas this
                // disallows entering multiple operators in a row.
                if (!operator.isEmpty()) {
                    throw new IllegalArgumentException("Cannot have multiple operators in a row.");
                } else {
                    // TODO: Operator following negation does not make sense and should throw an error.
                    negate = false;
                    operator = inputArray[i];
                }
            } else if (OPERATOR_NEGATE.equals(inputArray[i])) {
                negate = true;
            } else {
                Double value = Double.valueOf(inputArray[i]);
                if (negate) {
                    value *= -1;
                    negate = false;
                }
                if (operator.isEmpty()) {
                    throw new IllegalArgumentException("No operator found.");
                }
                total = performOperation(operator, total, value);
                operator = "";
            }
        }
        return total;
    }

    protected static boolean isOperator(String val) {
        return (OPERATOR_PLUS.equals(val) || OPERATOR_MINUS.equals(val)
                || OPERATOR_MULTIPLY.equals(val) || OPERATOR_DIVIDE.equals(val));
    }

    protected static String[] skipToFirstOperator(String[] values) {
        for (int i = 0; i < values.length; i++) {
            if (isOperator(values[i])) {
                return Arrays.copyOfRange(values, i, values.length);
            }
        }
        return new String[0];
    }

    protected static String[] concatenateArrays(String[] a, String[] b) {
        int aLen = a.length;
        int bLen = b.length;

        String[] concatenatedArray = new String[aLen + bLen];
        System.arraycopy(a, 0, concatenatedArray, 0, aLen);
        System.arraycopy(b, 0, concatenatedArray, aLen, bLen);

        return concatenatedArray;
    }

    private static Double performOperation(String operator, Double val1, Double val2) {
        if (OPERATOR_PLUS.equals(operator)) {
            return val1 + val2;
        } else if (OPERATOR_MINUS.equals(operator)) {
            return val1 - val2;
        } else if (OPERATOR_MULTIPLY.equals(operator)) {
            return val1 * val2;
        } else if (OPERATOR_DIVIDE.equals(operator)) {
            return val1 / val2;
        } else {
            throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private static String[] arrayToStringArray(List<String> array) {
        String[] stringArray = new String[array.size()];// ArrayList to String Array conversion
        for(int j =0;j<array.size();j++){
            stringArray[j] = array.get(j);
        }
        return stringArray;
    }
}
