package com.hszg.exercises.plaenum1.logic;

public class FunctionMapTest {

    public static void main(String[] args) {
        Function func = new FunctionPlusFive();
        Function func2 = new FunctionModulo2();
        FunctionMap fTest = new FunctionMap();
        int[] input = {1, 2, 3, 4, 5};
        int[] expectedOutput = {6, 7, 8, 9, 10};
        int[] output = fTest.map(input, func);
        boolean success = true;

        System.out.println("Testing FunctionPlusFive...");
        for (int i = 0; i < input.length; i++) {
            if (output[i] != expectedOutput[i]) {
                success = false;
                System.out.println("Test failed at index " + i + ": expected " + (expectedOutput[i]) + " but got " + output[i]);
                break;
            }
        }
        if (success) {
            System.out.println("Test passed! Output: " + java.util.Arrays.toString(output));
        } else {
            System.out.println("Test failed!");
        }

        System.out.println("Testing FunctionModulo2...");
        int[] expectedOutput2 = {1, 0, 1, 0, 1};
        int[] output2 = fTest.map(input, func2);
        success = true;
        for (int i = 0; i < input.length; i++) {
            if (output2[i] != expectedOutput2[i]) {
                success = false;
                System.out.println("Test failed at index " + i + ": expected " + (expectedOutput2[i]) + " but got " + output2[i]);
                break;
            }
        }
        if (success) {
            System.out.println("Test passed! Output: " + java.util.Arrays.toString(output2));
        } else {
            System.out.println("Test failed!");
        }

    }

}

