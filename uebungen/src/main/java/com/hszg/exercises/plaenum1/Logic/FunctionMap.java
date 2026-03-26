package uebungen.src.main.java.com.hszg.exercises.plaenum1.Logic;

public class FunctionMap {
           
    int[] map(int[] input, Function f) {
        int[] result = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = f.execute(input[i]);
        }
        return result;
    }
    
}
