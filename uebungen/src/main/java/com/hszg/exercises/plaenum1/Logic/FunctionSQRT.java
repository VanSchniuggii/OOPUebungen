package uebungen.src.main.java.com.hszg.exercises.plaenum1.Logic;

public class FunctionSQRT implements Function {

    @Override
    public int execute(int x) {
        if (x < 0) {
            throw new IllegalArgumentException("Input must be non-negative");
        }
        return (int) Math.sqrt(x);
    }

}
