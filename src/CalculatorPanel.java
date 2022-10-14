/*
// Nicholas Cutlip
// CS480 Lab #2
// Prof. Vajda
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// A class to create a basic GUI based calculator.
public class CalculatorPanel extends JPanel {

    final static private String DIGITS = "1234567890";      // IS IT OKAY to include ZERO?

    final static private String CLOSING_PARENS = ")}";
    final static private String OPENING_PARENS = "({";

    // Higher indices indicate higher precedence (precedence = index / 2)
    final static private String[] OPS_PRECEDENCE = { "-", "+", "*", "/", "^", "^",
            "sin", "cos", "tan" , "cot", "ln", "log"};

    private String lastOpenParen = "";          // Tracks which type of paren was last opened, ( or {
    private boolean insideDecimal = false;      // Prevents use of decimal point inside another decimal point
    private int parenCount;                     // Prevents incorrect parenthesis input
    private int curlyCount;                     // Prevents incorrect curly bracket input
    private String currInput;                   // Holds state of current expression as displayed to user
    private String currExpression;              // Holds state of current expression as it will be evaluated

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
    final private JButton unaryNegativeButton, decimalButton, expButton, clearButton, backButton;
    // Text I/O area
    private final JTextArea display = new JTextArea("0");

    public CalculatorPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.orange);

        // Configure I/O display area
        display.setEditable(false);         // Input field cannot be edited by user
        display.setPreferredSize(new Dimension(800,100));
        currInput = "";
        currExpression = "";
        display.setText(currInput);
        Font bigFont = display.getFont().deriveFont(Font.PLAIN, 25f);
        display.setFont(bigFont);
        add(display, "North");

        // Configure grid of buttons
        JPanel panel = new JPanel();
        panel.setBackground(Color.pink);
        GridLayout layout = new GridLayout(6, 5);
        layout.setVgap(5);
        layout.setHgap(5);
        panel.setLayout(layout);


        // Row 1
        a1Button = new JButton("1");
        a1Button.setFont(bigFont);
        panel.add(a1Button);
        a2Button = new JButton("2");
        a2Button.setFont(bigFont);
        panel.add(a2Button);
        a3Button = new JButton("3");
        a3Button.setFont(bigFont);
        panel.add(a3Button);
        addButton = new JButton("+");
        addButton.setFont(bigFont);
        panel.add(addButton);
        subtractButton = new JButton("-");
        subtractButton.setFont(bigFont);
        panel.add(subtractButton);

        // Row 2
        a4Button = new JButton("4");
        a4Button.setFont(bigFont);
        panel.add(a4Button);
        a5Button = new JButton("5");
        a5Button.setFont(bigFont);
        panel.add(a5Button);
        a6Button = new JButton("6");
        a6Button.setFont(bigFont);
        panel.add(a6Button);
        multiplyButton = new JButton("*");
        multiplyButton.setFont(bigFont);
        panel.add(multiplyButton);
        divideButton = new JButton("/");
        divideButton.setFont(bigFont);
        panel.add(divideButton);


        // Row 3
        a7Button = new JButton("7");
        a7Button.setFont(bigFont);
        panel.add(a7Button);
        a8Button = new JButton("8");
        a8Button.setFont(bigFont);
        panel.add(a8Button);
        a9Button = new JButton("9");
        a9Button.setFont(bigFont);
        panel.add(a9Button);
        sinButton = new JButton("sin");
        sinButton.setFont(bigFont);
        panel.add(sinButton);
        cosButton = new JButton("cos");
        cosButton.setFont(bigFont);
        panel.add(cosButton);

        // Row 4
        a0Button = new JButton("0");
        a0Button.setFont(bigFont);
        panel.add(a0Button);
        openParenButton = new JButton("(");
        openParenButton.setFont(bigFont);
        panel.add(openParenButton);
        closeParenButton = new JButton(")");
        closeParenButton.setFont(bigFont);
        panel.add(closeParenButton);
        tanButton = new JButton("tan");
        tanButton.setFont(bigFont);
        panel.add(tanButton);
        cotButton = new JButton("cot");
        cotButton.setFont(bigFont);
        panel.add(cotButton);

        // Row 5
        equalButton = new JButton("=");
        equalButton.setFont(bigFont);
        panel.add(equalButton);
        openCurlyButton = new JButton("{");
        openCurlyButton.setFont(bigFont);
        panel.add(openCurlyButton);
        closeCurlyButton = new JButton("}");
        closeCurlyButton.setFont(bigFont);
        panel.add(closeCurlyButton);
        lnButton = new JButton("ln");
        lnButton.setFont(bigFont);
        panel.add(lnButton);
        logButton = new JButton("log");
        logButton.setFont(bigFont);
        panel.add(logButton);

        // Row 6
        unaryNegativeButton = new JButton("+/-");
        unaryNegativeButton.setFont(bigFont);
        panel.add(unaryNegativeButton);
        expButton = new JButton("^");
        expButton.setFont(bigFont);
        panel.add(expButton);
        decimalButton = new JButton(".");
        decimalButton.setFont(bigFont);
        panel.add(decimalButton);
        clearButton = new JButton("clear");
        clearButton.setFont(bigFont);
        panel.add(clearButton);
        backButton = new JButton("back");
        backButton.setFont(bigFont);
        panel.add(backButton);

        // Initial button states
        decimalButton.setEnabled(true);
        enableUnaryOps();
        enableDigits();
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
            enableUnaryOps();
            checkLastOpenParen();
            equalButton.setEnabled(parenCount == 0 && curlyCount == 0);
        });

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
            enableUnaryOps();
            enableDigits();
            equalButton.setEnabled(false);
            enableZero(false);
            enableBinaryOps(false);
        });

        backButton.addActionListener(e -> {
            // ADD ACTION LISTSENER CODE HERE
            // IF LAST char IS WS, DELETE LAST TWO
            // ELSE DELETE ONE

        });

        equalButton.addActionListener(e -> {
            display.setText(evaluate());
            currInput = "";
            currExpression = "";

            // Initial button states
            decimalButton.setEnabled(true);
            enableUnaryOps();
            enableDigits();
            equalButton.setEnabled(false);
            enableZero(false);
            enableBinaryOps(false);
        });

        // Constrain buttons to center of CalculatorPanel
        add(panel, "Center");
    }

    // Evaluate
    private String evaluate() {
        return evalRPN(infixToRPN(currExpression));
    }
    private static String evalRPN(String expr)
    {
        // Function adapted from: https://rosettacode.org/wiki/Parsing/RPN_calculator_algorithm#Java_2
        // Evaluate a mathematical expression in Reverse Polish Notation (postfix)

        if (expr.equals("Error: Mismatched parenthesis")) return expr;
        LinkedList<Double> stack = new LinkedList<>();

        // Split postfix expression into tokens based on delimiting whitespace
        for (String token : expr.split("\\s")){
            switch (token) {

                // Binary Operators
                case "*" -> {
                    double secondOperand = stack.pop();
                    if (stack.isEmpty()) return "Error: Missing multiplication operand";
                    double firstOperand = stack.pop();
                    stack.push(firstOperand * secondOperand);
                    break;
                }
                case "/" -> {
                    double secondOperand = stack.pop();
                    if (stack.isEmpty()) return "Error: Missing division operand";
                    double firstOperand = stack.pop();
                    if (secondOperand == 0.0) {
                        return "Error: Division by zero is undefined";
                    }
                    stack.push(firstOperand / secondOperand);
                    break;
                }
                case "-" -> {
                    double secondOperand = stack.pop();
                    if (stack.isEmpty()) return "Error: Missing subtraction operand";
                    double firstOperand = stack.pop();
                    stack.push(firstOperand - secondOperand);
                    break;
                }
                case "+" -> {
                    double secondOperand = stack.pop();
                    if (stack.isEmpty()) return "Error: Missing addition operand";
                    double firstOperand = stack.pop();
                    stack.push(firstOperand + secondOperand);
                    break;
                }
                case "^" -> {
                    double secondOperand = stack.pop();
                    if (stack.isEmpty()) return "Error: Missing exponent";
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
                    try {
                        stack.push(Double.parseDouble(token + ""));
                    } catch (NumberFormatException e) {
                        return "\nError: invalid token " + token;
                    }
                }
            }
        }
        if (stack.size() > 1) {
            return "Error: too many operands.";
        }

        double result = stack.pop();
        // Check for double overflow
        if (result == Double.POSITIVE_INFINITY || result == Double.NEGATIVE_INFINITY)
            return result + "\nError: Loss of precision due to double overflow.";
        return "" + result;
    }
    static String infixToRPN(String infix)
    {
        // Function adapted from: https://rosettacode.org/wiki/Parsing/Shunting-yard_algorithm#Java
        // Convert infix mathematical expression to Reverse Polish Notation (postfix)

        ArrayList<String> opsPrecedence = new ArrayList<>(Arrays.asList(OPS_PRECEDENCE));
        StringBuilder postfix = new StringBuilder();
        Stack<Integer> s = new Stack<>();

        // Split infix expression into tokens based on delimiting whitespace
        for (String token : infix.split("\\s")) {
            if (token.isEmpty())
                continue;
            int idx = opsPrecedence.indexOf(token);

            // Check if the current token is an operator
            if (idx != -1) {
                if (s.isEmpty()) s.push(idx);
                else {
                    while (!s.isEmpty()) {
                        // If current token's precedence is lower than the operator on top of the stack,
                        // pop the top of the stack and append to result
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
                // Until '(' on stack, pop operators.
                while (s.peek() != -2) {
                    if (s.peek() == -4) return "Error: Mismatched parenthesis";
                    postfix.append(opsPrecedence.get(s.pop())).append(" ");
                }
                s.pop();
            }
            else if (token.equals("}")) {
                // Until '(' on stack, pop operators.
                while (s.peek() != -4) {
                    if (s.peek() == -2) return "Error: Mismatched parenthesis";
                    postfix.append(opsPrecedence.get(s.pop())).append(" ");
                }
                s.pop();
            }
            else {
                postfix.append(token).append(' ');
            }
        }
        while (!s.isEmpty())        // Empty out the stack
            postfix.append(opsPrecedence.get(s.pop())).append(' ');
        return postfix.toString();
    }

    private void digitButtonAction(String s)
    {
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
        enableUnaryOps();
        checkLastOpenParen();
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

    private void enableDigits()
    {
        a1Button.setEnabled(true);
        a2Button.setEnabled(true);
        a3Button.setEnabled(true);
        a4Button.setEnabled(true);
        a5Button.setEnabled(true);
        a6Button.setEnabled(true);
        a7Button.setEnabled(true);
        a8Button.setEnabled(true);
        a9Button.setEnabled(true);
    }

    private void enableUnaryOps()
    {
        sinButton.setEnabled(true);
        cosButton.setEnabled(true);
        tanButton.setEnabled(true);
        cotButton.setEnabled(true);
        lnButton.setEnabled(true);
        logButton.setEnabled(true);
        subtractButton.setEnabled(true);
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
        frame.setTitle("My Calculator!");
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