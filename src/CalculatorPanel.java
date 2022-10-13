import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CalculatorPanel extends JPanel {

    final static private String DIGITS = "1234567890";      // IS IT OKAY to include ZERO?
    final static private String CLOSING_PARENS = ")}";
    final static private String OPENING_PARENS = "({";
    final static private String BINARY_OPS = "-+*/^=";
    final static private String[] UNARY_OPS = {"sin(", "cos(", "tan(" , "cot(", "ln(", "log("};
    final static private String[] OPS_PRECEDENCE = { "-", "+", "*", "/", "^", "^",
            "sin", "cos", "tan" , "cot", "ln", "log"};

    // Code above replaced concept below
    // final static ArrayList<String> UNARY_OPS = new ArrayList<>();

    private String lastOpenParen = "";
    private boolean insideDecimal = false;
    private int parenCount;
    private int curlyCount;
    private enum mostRecentControlSymbol {
        openParen, closePren, openCurly, closeCurly
    }
    // STACK IMPLEMENTATION TO REMEMBER WHAT COMES BEFORE
    // PUT LEFTS ON STACK, RIGHTS POP THEM
    private String currInput;
    private String currExpression;

    // Keep track of whether other digits are allowed after a zero,
    // i.e. after a decimal point or within the digits of a larger number
    private boolean zeroOK;

    // Row 1 buttons
    final private JButton a1Button, a2Button, a3Button, addButton, subtractButton;
    // Row 2 buttons
    final private JButton a4Button, a5Button, a6Button, multiplyButton, divideButton;
    // Row 3 buttons
    final private JButton a7Button, a8Button, a9Button, sinButton, cosButton;
    // Row 4 buttons
    final private JButton a0Button, openParenButton, closeParenButton, tanButton, cotButton;
    // Row 5 Buttons
    final private JButton equalButton, openCurlyButton, closeCurlyButton, lnButton, logButton;
    // Row 6 buttons
    final private JButton unaryNegativeButton, decimalButton, expButton, clearButton;
    // Text I/O area
    private final JTextArea display = new JTextArea("0");

    public CalculatorPanel() {
        setLayout(new BorderLayout(10, 10));
        //this.setBackground(Color.green);

        display.setEditable(false);
        currInput = "";
        currExpression = "";
        display.setText(currInput);
        Font bigFont = display.getFont().deriveFont(Font.PLAIN, 25f);
        display.setFont(bigFont);
        add(display, "North");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 5));

        // Row 1
        a1Button = new JButton("1");
        panel.add(a1Button);
        a2Button = new JButton("2");
        panel.add(a2Button);
        a3Button = new JButton("3");
        panel.add(a3Button);
        addButton = new JButton("+");
        panel.add(addButton);
        subtractButton = new JButton("-");
        panel.add(subtractButton);

        // Row 2
        a4Button = new JButton("4");
        panel.add(a4Button);
        a5Button = new JButton("5");
        panel.add(a5Button);
        a6Button = new JButton("6");
        panel.add(a6Button);
        multiplyButton = new JButton("*");
        panel.add(multiplyButton);
        divideButton = new JButton("/");
        panel.add(divideButton);


        // Row 3
        a7Button = new JButton("7");
        panel.add(a7Button);
        a8Button = new JButton("8");
        panel.add(a8Button);
        a9Button = new JButton("9");
        panel.add(a9Button);
        sinButton = new JButton("sin");
        panel.add(sinButton);
        cosButton = new JButton("cos");
        panel.add(cosButton);

        // Row 4
        a0Button = new JButton("0");
        panel.add(a0Button);
        openParenButton = new JButton("(");
        panel.add(openParenButton);
        closeParenButton = new JButton(")");
        panel.add(closeParenButton);
        tanButton = new JButton("tan");
        panel.add(tanButton);
        cotButton = new JButton("cot");
        panel.add(cotButton);

        // Row 5
        equalButton = new JButton("=");
        panel.add(equalButton);
        openCurlyButton = new JButton("{");
        panel.add(openCurlyButton);
        closeCurlyButton = new JButton("}");
        panel.add(closeCurlyButton);
        lnButton = new JButton("ln");
        panel.add(lnButton);
        logButton = new JButton("log");
        panel.add(logButton);

        // Row 6
        unaryNegativeButton = new JButton("+/-");
        panel.add(unaryNegativeButton);
        expButton = new JButton("^");
        panel.add(expButton);
        decimalButton = new JButton(".");
        panel.add(decimalButton);
        clearButton = new JButton("clear");
        panel.add(clearButton);

        // Initial button states
        decimalButton.setEnabled(true);
        enableUnaryOps(true);
        enableDigits(true);
        equalButton.setEnabled(false);
        enableZero(false);
        enableBinaryOps(false);

        a1Button.addActionListener(e -> {
            digitButtonAction("1 ");
            digitEnables();
        });

        a2Button.addActionListener(e -> {
            digitButtonAction("2 ");
            digitEnables();
        });

        a3Button.addActionListener(e -> {
            digitButtonAction("3 ");
            digitEnables();
        });

        a4Button.addActionListener(e -> {
            digitButtonAction("4 ");
            digitEnables();
        });

        a5Button.addActionListener(e -> {
            digitButtonAction("5 ");
            digitEnables();
        });

        a6Button.addActionListener(e -> {
            digitButtonAction("6 ");
            digitEnables();
        });

        a7Button.addActionListener(e -> {
            digitButtonAction("7 ");
            digitEnables();
        });

        a8Button.addActionListener(e -> {
            digitButtonAction("8 ");
            digitEnables();
        });

        a9Button.addActionListener(e -> {
            digitButtonAction("9 ");
            digitEnables();
        });

        a0Button.addActionListener(e -> {
            digitButtonAction("0 ");

            // Enable necessary buttons
            if (!insideDecimal) decimalButton.setEnabled(true);
            enableBinaryOps(true);
            enableUnaryOps(true);
            checkLastOpenParen();
            equalButton.setEnabled(parenCount == 0 && curlyCount == 0);
        });

        // Bind setEnabled function to same boolean
        addButton.addActionListener(e -> {
            String tempInput = "+ ";
            currInput += tempInput;
            currExpression += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            decimalButton.setEnabled(true);
            enableBinaryOps(false);
            equalButton.setEnabled(false);
            enableClose(false);
        });

        subtractButton.addActionListener(e -> {
            String tempInput = "- ";
            currInput += tempInput;
            currExpression += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            decimalButton.setEnabled(true);
            enableBinaryOps(false);
            equalButton.setEnabled(false);
            enableClose(false);
        });

        multiplyButton.addActionListener(e -> {
            String tempInput = "* ";
            currInput += tempInput;
            currExpression += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            decimalButton.setEnabled(true);
            enableBinaryOps(false);
            equalButton.setEnabled(false);
            enableClose(false);
        });

        divideButton.addActionListener(e -> {
            String tempInput = "/ ";
            currInput += tempInput;
            currExpression += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            decimalButton.setEnabled(true);
            enableBinaryOps(false);
            a0Button.setEnabled(false);
            equalButton.setEnabled(false);
            enableClose(false);

            // Enable necessary buttons
        });

        sinButton.addActionListener(e -> {
            lastOpenParen = "(";
            insertPostCloseMult();
            insertPostDigitMult();
            String tempInput = "sin ( ";
            parenCount++;
            currInput += tempInput;
            currExpression += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            decimalButton.setEnabled(true);
            closeParenButton.setEnabled(false);
            enableBinaryOps(false);
            equalButton.setEnabled(false);
            a0Button.setEnabled(true);
        });

        cosButton.addActionListener(e -> {
            lastOpenParen = "(";
            insertPostCloseMult();
            insertPostDigitMult();
            String tempInput = "cos ( ";
            parenCount++;
            currInput += tempInput;
            currExpression += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            decimalButton.setEnabled(true);
            closeParenButton.setEnabled(false);
            enableBinaryOps(false);
            equalButton.setEnabled(false);
            a0Button.setEnabled(true);
        });

        tanButton.addActionListener(e -> {
            lastOpenParen = "(";
            insertPostCloseMult();
            insertPostDigitMult();
            String tempInput = "tan ( ";
            parenCount++;
            currInput += tempInput;
            currExpression += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            decimalButton.setEnabled(true);
            closeParenButton.setEnabled(false);
            enableBinaryOps(false);
            equalButton.setEnabled(false);
            a0Button.setEnabled(true);
        });

        cotButton.addActionListener(e -> {
            lastOpenParen = "(";
            insertPostCloseMult();
            insertPostDigitMult();
            String tempInput = "cot ( ";
            parenCount++;
            currInput += tempInput;
            currExpression += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            decimalButton.setEnabled(true);
            closeParenButton.setEnabled(false);
            enableBinaryOps(false);
            a0Button.setEnabled(false);
            equalButton.setEnabled(false);
        });

        lnButton.addActionListener(e -> {
            lastOpenParen = "(";
            insertPostCloseMult();
            insertPostDigitMult();
            String tempInput = "ln ( ";
            parenCount++;
            currInput += tempInput;
            currExpression += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            decimalButton.setEnabled(true);
            closeParenButton.setEnabled(false);
            enableBinaryOps(false);
            a0Button.setEnabled(false);
            equalButton.setEnabled(false);
        });

        logButton.addActionListener(e -> {
            lastOpenParen = "(";
            insertPostCloseMult();
            insertPostDigitMult();
            String tempInput = "log ( ";
            parenCount++;
            currInput += tempInput;
            currExpression += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            decimalButton.setEnabled(true);
            closeParenButton.setEnabled(false);
            enableBinaryOps(false);
            a0Button.setEnabled(false);
            equalButton.setEnabled(false);
        });

        unaryNegativeButton.addActionListener(e -> {
            if (!currInput.equals("") &&
                    CLOSING_PARENS.contains(currInput.substring(currInput.length() - 2, currInput.length() - 1))) {
                // Insert multiplication operator
                currExpression += "* ";
            }
            String tempInput = "- ";
            currInput += tempInput;
            currExpression += "( 0 - 1 ) * ";
            display.setText(currInput);

            // Disable necessary buttons
            decimalButton.setEnabled(true);
            enableBinaryOps(false);
            equalButton.setEnabled(false);
        });

        expButton.addActionListener(e -> {
            lastOpenParen = "(";
            String tempInput = "^ ( ";
            parenCount++;
            currInput += tempInput;
            currExpression += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            decimalButton.setEnabled(true);
            closeParenButton.setEnabled(false);
            enableBinaryOps(false);
            equalButton.setEnabled(false);
        });

        decimalButton.addActionListener(e -> {
            String tempInput;
            if (currInput.length() == 0 ||
                    !deletePostDigitWS()) {
                tempInput = "0.";
            } else {
                tempInput = ".";
            }
            insertPostCloseMult();
            currInput += tempInput;
            currExpression += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            decimalButton.setEnabled(false);
            insideDecimal = true;
            enableBinaryOps(false);
            equalButton.setEnabled(false);
            enableClose(false);
        });

        openParenButton.addActionListener(e -> {
            lastOpenParen = "(";
            insertPostCloseMult();
            insertPostDigitMult();
            parenCount++;
            String tempInput = "( ";
            currInput += tempInput;
            currExpression += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            //closeCurlyButton.setEnabled(false);
            enableBinaryOps(false);
            equalButton.setEnabled(false);
            enableZero(false);
            enableClose(true);
        });

        closeParenButton.addActionListener(e -> {
            if (!deletePostOpenClose() && parenCount > 0) {
                String tempInput = ") ";
                currInput += tempInput;
                currExpression += tempInput;
                display.setText(currInput);
                parenCount--;
            }
            //closeCurlyButton.setEnabled(true);
            equalButton.setEnabled(parenCount == 0 && curlyCount == 0);
        });

        openCurlyButton.addActionListener(e -> {
            lastOpenParen = "{";
            insertPostCloseMult();
            insertPostDigitMult();
            curlyCount++;
            String tempInput = "{ ";
            currInput += tempInput;
            currExpression += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            //closeParenButton.setEnabled(false);
            enableBinaryOps(false);
            equalButton.setEnabled(false);
            enableZero(false);
            enableClose(true);
        });

        closeCurlyButton.addActionListener(e -> {
            if  (!deletePostOpenClose() && curlyCount > 0) {
                String tempInput = "} ";
                currInput += tempInput;
                currExpression += tempInput;
                display.setText(currInput);
                curlyCount--;
            }
            //closeParenButton.setEnabled(true);
            equalButton.setEnabled(curlyCount == 0 && parenCount == 0);
        });

        clearButton.addActionListener(e -> {
            currInput = "";
            currExpression = "";
            display.setText(currInput);

            // Initial button states
            if (!insideDecimal) decimalButton.setEnabled(true);
            enableUnaryOps(true);
            enableDigits(true);
            equalButton.setEnabled(false);
            enableZero(false);
            enableBinaryOps(false);
        });

        equalButton.addActionListener(e -> {
            display.setText(evaluate());
            currInput = "";
            currExpression = "";

            // Disable necessary buttons
            decimalButton.setEnabled(true);
            enableUnaryOps(true);
            enableDigits(true);
            equalButton.setEnabled(false);
            enableZero(false);
            enableBinaryOps(false);
        });

        add(panel, "Center");
    }

    // Evaluate
    private String evaluate() {
        return evalRPN(infixToPostfix(currExpression));
    }
    private static String evalRPN(String expr){
        // Function adapted from: https://rosettacode.org/wiki/Parsing/RPN_calculator_algorithm#Java_2
        LinkedList<Double> stack = new LinkedList<Double>();
        System.out.println("Input\tOperation\tStack after");
        for (String token : expr.split("\\s")){
            System.out.print(token + "\t");
            switch (token) {
                // Binary Operators
                case "*" -> {
                    System.out.print("Operate\t\t");
                    double secondOperand = stack.pop();
                    if (stack.isEmpty()) return "Error: Incorrect expression";
                    double firstOperand = stack.pop();
                    stack.push(firstOperand * secondOperand);
                    break;
                }
                case "/" -> {
                    System.out.print("Operate\t\t");
                    double secondOperand = stack.pop();
                    if (stack.isEmpty()) return "Error: Incorrect expression";
                    double firstOperand = stack.pop();
                    if (secondOperand == 0.0) {
                        return "Error: Division by zero is undefined";
                    }
                    stack.push(firstOperand / secondOperand);
                    break;
                }
                case "-" -> {
                    System.out.print("Operate\t\t");
                    double secondOperand = stack.pop();
                    if (stack.isEmpty()) return "Error: Incorrect expression";
                    double firstOperand = stack.pop();
                    stack.push(firstOperand - secondOperand);
                    break;
                }
                case "+" -> {
                    System.out.print("Operate\t\t");
                    double secondOperand = stack.pop();
                    if (stack.isEmpty()) return "Error: Incorrect expression";
                    double firstOperand = stack.pop();
                    stack.push(firstOperand + secondOperand);
                    break;
                }
                case "^" -> {
                    System.out.print("Operate\t\t");
                    double secondOperand = stack.pop();
                    if (stack.isEmpty()) return "Error: Incorrect expression";
                    double firstOperand = stack.pop();
                    stack.push(Math.pow(firstOperand, secondOperand));
                    break;
                }
                //Unary Operators
                case "sin" -> {
                    if (stack.isEmpty()) return "Error: Incorrect sin expression";
                    double operand = stack.pop();
                    stack.push(Math.sin(operand));
                }
                case "cos" -> {
                    if (stack.isEmpty()) return "Error: Incorrect cos expression";
                    double operand = stack.pop();
                    stack.push(Math.cos(operand));
                }
                case "tan" -> {
                    if (stack.isEmpty()) return "Error: Incorrect tan expression";
                    double operand = stack.pop();
                    stack.push(Math.tan(operand));
                }
                case "cot" -> {
                    if (stack.isEmpty()) return "Error: Incorrect cot expression";
                    double operand = stack.pop();
                    if (operand == 0.0) {
                        return "Error: cot ( 0 ) is undefined";
                    }
                    stack.push(1.0 / Math.tan(operand));
                }
                case "ln" -> {
                    if (stack.isEmpty()) return "Error: Incorrect ln expression";
                    double operand = stack.pop();
                    if (operand <= 0.0) {
                        return "Error: ln ( n <= 0 ) is undefined";
                    }
                    stack.push(Math.log(operand));
                }
                case "log" -> {
                    if (stack.isEmpty()) return "Error: Incorrect log expression";
                    double operand = stack.pop();
                    if (operand <= 0.0) {
                        return "Error: log ( n <= 0 ) is undefined";
                    }
                    stack.push(Math.log(operand));
                }
                // Digits
                default -> {
                    System.out.print("Push\t\t");
                    try {
                        stack.push(Double.parseDouble(token + ""));
                    } catch (NumberFormatException e) {
                        return "\nError: invalid token " + token;
                    }
                }
            }
            System.out.println(stack);
        }
        if (stack.size() > 1) {
            return "Error, too many operands.";
        }
        return "" + stack.pop();
    }

    private void digitButtonAction(String s) {
        insertPostCloseMult();
        deletePostDigitWS();
        currInput += s;
        currExpression += s;
        display.setText(currInput);
        digitEnables();
    }
    private void digitEnables()
    {
        enableZero(true);
        equalButton.setEnabled(parenCount == 0 && curlyCount == 0);
        if (!insideDecimal) decimalButton.setEnabled(true);
        enableBinaryOps(true);
        enableUnaryOps(true);
        checkLastOpenParen();
    }

    static String infixToPostfix(String infix)
    {
        // Function adapted from: https://rosettacode.org/wiki/Parsing/Shunting-yard_algorithm#Java
        /* To find out the precedence, we take the index of the
           token in the ops string and divide by 2 (rounding down).
           This will give us: 0, 0, 1, 1, 2 */

        ArrayList<String> opsPrecedence = new ArrayList<>(Arrays.asList(OPS_PRECEDENCE));
        StringBuilder postfix = new StringBuilder();
        Stack<Integer> s = new Stack<>();

        for (String token : infix.split("\\s")) {
            if (token.isEmpty())
                continue;
            int idx = opsPrecedence.indexOf(token);

            // check for operator
            if (idx != -1) {
                if (s.isEmpty())
                    s.push(idx);

                else {
                    while (!s.isEmpty()) {
                        int prec2 = s.peek() / 2;
                        int prec1 = idx / 2;
                        if (prec2 > prec1 || (prec2 == prec1 && !token.equals("^"))) {
                            postfix.append(opsPrecedence.get(s.pop())).append(" ");
                        } else {
                            break;
                        }
                    }
                    s.push(idx);
                }
            }
            else if (token.equals("(")) {
                s.push(-2); // -2 stands for '('
            }
            else if (token.equals("{")) {
                s.push(-4); // -4 stands for '{'
            }
            else if (token.equals(")")) {
                // until '(' on stack, pop operators.
                while (s.peek() != -2)
                    postfix.append(opsPrecedence.get(s.pop())).append(" ");
                s.pop();
            }
            else if (token.equals("}")) {
                // until '(' on stack, pop operators.
                while (s.peek() != -4)
                    postfix.append(opsPrecedence.get(s.pop())).append(" ");
                s.pop();
            }
            else {
                postfix.append(token).append(' ');
            }
        }
        while (!s.isEmpty())
            postfix.append(opsPrecedence.get(s.pop())).append(' ');
        return postfix.toString();
    }

    private void checkLastOpenParen()
    {
        if (lastOpenParen.equals("(")) {
            closeParenButton.setEnabled(true);
        }
        else if (lastOpenParen.equals("{")) {
            closeCurlyButton.setEnabled(true);
        }
    }

    private boolean deletePostDigitWS()
    {
        if (!currInput.equals("") &&
                DIGITS.contains(currInput.substring(currInput.length() - 2, currInput.length() - 1)) &&
                currInput.substring(currInput.length() - 1).equals(" "))
        {
            // Delete trailing whitespace
            currInput = currInput.substring(0, currInput.length() - 1);
            currExpression = currExpression.substring(0, currExpression.length() - 1);
            return true;
        }
        return false;
    }

    private boolean deletePostOpenClose()
    {
        if (!currInput.equals("") &&
                OPENING_PARENS.contains(currInput.substring(currInput.length() - 2, currInput.length() - 1)))
        {
            // Adjust paren counts
            if (currInput.charAt(currInput.length() - 2) == '(') parenCount--;
            else curlyCount--;

            // Delete preceding opening parenthesis
            currInput = currInput.substring(0, currInput.length() - 2);
            currExpression = currExpression.substring(0, currExpression.length() - 2);
            display.setText(currInput);
            return true;
        }
        return false;
    }

    private void insertPostDigitMult()
    {
        if (!currInput.equals("") &&
                DIGITS.contains(currInput.substring(currInput.length() - 2, currInput.length() - 1)))
        {
            // Insert multiplication operator
            currInput += "* ";
            currExpression += "* ";
        }
    }

    private void insertPostCloseMult()
    {
        if (!currInput.equals("") &&
                CLOSING_PARENS.contains(currInput.substring(currInput.length() - 2, currInput.length() - 1)))
        {
            // Insert multiplication operator
            currInput += "* ";
            currExpression += "* ";
        }
    }

    private void enableZero(Boolean enabled)
    {
        a0Button.setEnabled(enabled);
    }

    private void enableDigits(boolean enabled)
    {
        a1Button.setEnabled(enabled);
        a2Button.setEnabled(enabled);
        a3Button.setEnabled(enabled);
        a4Button.setEnabled(enabled);
        a5Button.setEnabled(enabled);
        a6Button.setEnabled(enabled);
        a7Button.setEnabled(enabled);
        a8Button.setEnabled(enabled);
        a9Button.setEnabled(enabled);
    }

    private void enableUnaryOps(boolean enabled)
    {
        sinButton.setEnabled(enabled);
        cosButton.setEnabled(enabled);
        tanButton.setEnabled(enabled);
        cotButton.setEnabled(enabled);
        lnButton.setEnabled(enabled);
        logButton.setEnabled(enabled);
        subtractButton.setEnabled(enabled);
    }

    private void enableBinaryOps(Boolean enabled)
    {
        addButton.setEnabled(enabled);
        subtractButton.setEnabled(enabled);
        multiplyButton.setEnabled(enabled);
        divideButton.setEnabled(enabled);
        expButton.setEnabled(enabled);
    }

    private void enableClose(boolean enabled)       // Open are always enabled??
    {
        if (curlyCount > 0) {
            closeCurlyButton.setEnabled(enabled);
        }
        if (parenCount > 0) {
            closeParenButton.setEnabled(enabled);
        }
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(false);
        JFrame frame = new JFrame();
        frame.setTitle("Calculator");
        frame.setSize(800,500);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        Container contentPane = frame.getContentPane();
        contentPane.add(new CalculatorPanel());
        frame.setVisible(true); // frame.show() is deprecated
    }
}