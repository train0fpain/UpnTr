import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

public class UpnTr {

    private Stack<BigDecimal> stack;
    private BigDecimal lastPushed;
    private final String[] CMDS = {"c", "print", "cmd", "end", "switch", "switchall"};

    public UpnTr() {
        stack = new Stack<>();
        while(true){
            takeInput();
        }
    }

    public void takeInput() {

        try {
            String stackString = readInput("Enter value: ");

            parseSign(stackString);
            outLastValue();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EmptyStackException e){
            System.err.println(e);
            System.out.println("Make sure that there are enough numbers in the stack to calculate." +
                    " \nCheck your stack with \"print\"");
        } catch (NumberFormatException e){
            System.err.println(e);
            System.out.println("Your input was not valid." +
                    " \nEither enter a number or one of the commands that can be found with \"cmd\". ");
        }catch (Exception e){
            System.out.println("Fatal error");
            System.err.println(e);
            System.exit(-1);
        }
    }

    private void parseSign(String in){
        switch (in){
            case "+":
                stack.push(stack.pop().add(stack.pop()));
                break;
            case "-":
                lastPushed = stack.pop();
                stack.push(stack.pop().subtract(lastPushed));
                break;
            case "/":
                lastPushed = stack.pop();
                stack.push(stack.pop().divide(lastPushed));
                break;
            case "*":
                stack.push(stack.pop().multiply(stack.pop()));
                break;
            case "**":
                lastPushed = stack.pop();
                stack.push(stack.pop().pow(lastPushed.intValue()));
                break;
            case "c":
                stack.clear();
                break;
            case "end":
                System.exit(-1);
                break;
            case "cmd":
                showCmds();
                break;
            case "print":
                printStack();
                break;
            case "switch":
                switchNumbers();
                break;
            case "switchall":
                switchAll();
                break;
            default:
                stack.push(new BigDecimal(in));
                break;
        }
    }

    private void outLastValue(){
        if (!stack.empty()) {
            System.out.println("Last value: " + stack.peek());
        }
    }

    private String readInput(String message) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print(message);
        return br.readLine();
    }

    private void showCmds(){
        System.out.println("List of commands: ");
        for (String cmd : CMDS){
            System.out.println(cmd);
        }
        System.out.println();
    }

    private void printStack(){
        System.out.println("Stack from bottom up: ");
        Object[] tmpArray = stack.toArray();
        for (Object num : tmpArray){
            System.out.println(num);
        }
    }

    private void switchNumbers(){
        BigDecimal firstNumber = stack.pop();
        BigDecimal secondNumber = stack.pop();
        stack.push(firstNumber);
        stack.push(secondNumber);
    }

    private void switchAll(){
        Collections.reverse(stack);
    }
}
