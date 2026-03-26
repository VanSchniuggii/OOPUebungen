package uebungen.src.main.java.com.hszg.exercises.plaenum1.Logic;

public class FunctionPlay {

    static Function func;
    public static void main(String[] args) {
        switch (args[0]) {
            case "plus5":
                func = new FunctionPlusFive();
                break;
            case "modulo2":
                func = new FunctionModulo2();
                break;
            case "sqrt":
                func = new FunctionSQRT();
                break;        
            default:
                break;
        }

        int result = func.execute(10);
        System.out.println("Result: " + result);
    }

}
