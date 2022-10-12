import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Scanner;
public class CalculatorPanel extends JPanel {

    final static private String DIGITS = "1234567890";      // IS IT OKAY to include ZERO?
    // Code above replaced concept below
    // final static ArrayList<String> UNARY_OPS = new ArrayList<>();
    private int parenCount;
    private int curlyCount;
    private enum mostRecentControlSymbol {
        openParen, closePren, openCurly, closeCurly;
    }
    private String currInput;
    // Keep track of whether other digits are allowed after a zero,
    // i.e. after a decimal point or within the digits of a larger number
    private boolean zeroOK;
    final private JButton a1Button, a2Button, a3Button, addButton, subtractButton;
    final private JButton a4Button, a5Button, a6Button, multiplyButton, divideButton;
    final private JButton a7Button, a8Button, a9Button, sinButton, cosButton;
    final private JButton a0Button, openParenButton, closeParenButton, tanButton, cotButton;
    final private JButton equalButton, openCurlyButton, closeCurlyButton, lnButton, logButton;
    final private JButton decimalButton, expButton, clearButton;
    private final JTextArea display = new JTextArea("0");

    public CalculatorPanel() {
        setLayout(new BorderLayout(2, 2));
        //this.setBackground(Color.green);

        display.setEditable(false);
        currInput = "";
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
        expButton = new JButton("^");
        panel.add(expButton);
        decimalButton = new JButton(".");
        panel.add(decimalButton);
        clearButton = new JButton("clear");
        panel.add(clearButton);

        // Initial button states
        enableDecimal();
        enableUnaryOps(true);
        enableDigits(true);

        disableZero();
        disableBinaryOps();

        a1Button.addActionListener(e -> {
            String tempInput = "1";
            currInput += tempInput;
            display.setText(currInput);

            //Enable necessary buttons
            enableZero();
            enableDecimal();        // IS THIS ever disabled?
            enableBinaryOps();
            enableUnaryOps(true);       // are these ever disabled??
            enableClose(true);
        });

        a2Button.addActionListener(e -> {
            String tempInput = "2";
            currInput += tempInput;
            display.setText(currInput);

            // Enable necessary buttons
            enableClose(true);
        });

        a3Button.addActionListener(e -> {
            String tempInput = "3";
            currInput += tempInput;
            display.setText(currInput);

            // Enable necessary buttons
            enableClose(true);
        });

        a4Button.addActionListener(e -> {
            String tempInput = "4";
            currInput += tempInput;
            display.setText(currInput);

            // Enable necessary buttons
            enableClose(true);
        });

        a5Button.addActionListener(e -> {
            String tempInput = "5";
            currInput += tempInput;
            display.setText(currInput);

            // Enable necessary buttons
            enableClose(true);
        });

        a6Button.addActionListener(e -> {
            String tempInput = "6";
            currInput += tempInput;
            display.setText(currInput);

            // Enable necessary buttons
            enableClose(true);
        });

        a7Button.addActionListener(e -> {
            String tempInput = "7";
            currInput += tempInput;
            display.setText(currInput);

            // Enable necessary buttons
            enableClose(true);
        });

        a8Button.addActionListener(e -> {
            String tempInput = "8";
            currInput += tempInput;
            display.setText(currInput);

            // Enable necessary buttons
            enableClose(true);
        });

        a9Button.addActionListener(e -> {
            String tempInput = "9";
            currInput += tempInput;
            display.setText(currInput);

            // Enable necessary buttons
            enableClose(true);
        });

        a0Button.addActionListener(e -> {
            String tempInput = "0";
            currInput += tempInput;
            display.setText(currInput);

            // Enable necessary buttons
            enableClose(true);
        });

        // Bind setEnabled function to same boolean
        addButton.addActionListener(e -> {
            // Disable necessary buttons
            disableBinaryOps();
            closeParenButton.setEnabled(false);
            closeCurlyButton.setEnabled(false);
        });

        subtractButton.addActionListener(e -> {
            String tempInput = "-";
            currInput += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            disableBinaryOps();
            closeParenButton.setEnabled(false);
            closeCurlyButton.setEnabled(false);
        });

        multiplyButton.addActionListener(e -> {
            String tempInput = "*";
            currInput += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            disableBinaryOps();
            closeParenButton.setEnabled(false);
            closeCurlyButton.setEnabled(false);
        });

        divideButton.addActionListener(e -> {
            String tempInput = "/";
            currInput += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            disableBinaryOps();
            a0Button.setEnabled(false);
            closeParenButton.setEnabled(false);
            closeCurlyButton.setEnabled(false);

            // Enable necessary buttons
        });

        sinButton.addActionListener(e -> {
            String tempInput = "sin(";
            parenCount++;
            currInput += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            disableBinaryOps();
            enableClose(true);
        });

        cosButton.addActionListener(e -> {
            String tempInput = "cos(";
            parenCount++;
            currInput += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            disableBinaryOps();
            enableClose(true);
        });

        tanButton.addActionListener(e -> {
            String tempInput = "tan";
            parenCount++;
            currInput += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            disableBinaryOps();
            closeParenButton.setEnabled(false);
            closeCurlyButton.setEnabled(false);
        });

        cotButton.addActionListener(e -> {
            String tempInput = "cot(";
            parenCount++;
            currInput += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            disableBinaryOps();
            a0Button.setEnabled(false);
            enableClose(true);
        });

        lnButton.addActionListener(e -> {
            String tempInput = "ln(";
            parenCount++;
            currInput += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            disableBinaryOps();
            a0Button.setEnabled(false);
            enableClose(true);
        });

        logButton.addActionListener(e -> {
            String tempInput = "log(";
            parenCount++;
            currInput += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            disableBinaryOps();
            a0Button.setEnabled(false);
            enableClose(false);
        });

        expButton.addActionListener(e -> {
            String tempInput = "^(";
            parenCount++;
            currInput += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            disableBinaryOps();
            enableClose(false);
        });

        decimalButton.addActionListener(e -> {
            String tempInput;
            if (currInput.length() == 0 ||
                    !DIGITS.contains(currInput.substring(currInput.length() - 1))) {
                tempInput = "0.";
            } else {
                tempInput = ".";
            }
            currInput += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            disableDecimal();
            disableBinaryOps();
            enableClose(false);
        });

        openParenButton.addActionListener(e -> {
            parenCount++;
            String tempInput = "(";
            currInput += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            disableBinaryOps();
            disableZero();
        });

        closeParenButton.addActionListener(e -> {
            if (parenCount > 0) {
                String tempInput = ")";
                currInput += tempInput;
                display.setText(currInput);
                parenCount--;
            }
        });

        openCurlyButton.addActionListener(e -> {
            curlyCount++;
            String tempInput = "{";
            currInput += tempInput;
            display.setText(currInput);

            // Disable necessary buttons
            //enable
            disableBinaryOps();
            disableZero();
        });

        closeCurlyButton.addActionListener(e -> {
            if (curlyCount > 0) {
                String tempInput = "}";
                currInput += tempInput;
                display.setText(currInput);
                curlyCount--;
            }
        });

        clearButton.addActionListener(e -> {
            currInput = "";
            display.setText(currInput);
        });

        equalButton.addActionListener(e -> {
            display.setText(evaluate());
            currInput = "";
            // Disable necessary buttons
        });

        add(panel, "Center");
    }

    // Evaluate
    private String evaluate() {
        Scanner s = new Scanner(currInput);
        String evaluated = "";
        Stack<String> operands = new Stack<>();
        Stack<String> operators = new Stack<>();

        // for (the love of the goddess)

        // Shunting yard?? Lol
        return evaluated;
    }

    private void enableDecimal() {
        decimalButton.setEnabled(true);
    }

    private void disableDecimal() {
        decimalButton.setEnabled(false);
    }

    private void enableZero() {
        a0Button.setEnabled(true);
    }

    private void disableZero() {
        a0Button.setEnabled(false);
    }

    private void enableDigits(boolean enabled) {
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

    private void enableUnaryOps(boolean enabled) {
        sinButton.setEnabled(enabled);
        cosButton.setEnabled(enabled);
        tanButton.setEnabled(enabled);
        cotButton.setEnabled(enabled);
        lnButton.setEnabled(enabled);
        logButton.setEnabled(enabled);
        subtractButton.setEnabled(enabled);
    }

    private void enableBinaryOps() {
        addButton.setEnabled(true);
        subtractButton.setEnabled(true);
        multiplyButton.setEnabled(true);
        divideButton.setEnabled(true);
        equalButton.setEnabled(true);
        expButton.setEnabled(true);
    }

    // Kept separate for now because of difference with (-)
    private void disableBinaryOps() {
        addButton.setEnabled(false);
        //subtractButton.setEnabled(false);  REMAINS enabled, since (-) is also unary operator
        multiplyButton.setEnabled(false);
        divideButton.setEnabled(false);
        equalButton.setEnabled(false);     //IDK if this counts as a binary op??
        expButton.setEnabled(false);
    }

    private void enableClose(boolean enabled)       // Open are always enabled??
    {
        if (curlyCount > 0) {
            closeParenButton.setEnabled(enabled);
            closeCurlyButton.setEnabled(enabled);
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