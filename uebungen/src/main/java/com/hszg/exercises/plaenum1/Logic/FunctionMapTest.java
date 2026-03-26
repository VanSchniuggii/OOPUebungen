package uebungen.src.main.java.com.hszg.exercises.plaenum1.Logic;

public class FunctionMapTest {

    public static void main(String[] args) {
        Function func = new FunctionPlusFive();
        FunctionMap fTest = new FunctionMap();
        int[] input = {1, 2, 3, 4, 5};
        int[] expectedOutput = {6, 7, 8, 9, 10};
        int[] output = fTest.map(input, func);
        boolean success = true;
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
    }

}
