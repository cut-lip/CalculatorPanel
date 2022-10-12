import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Stack;

public class CalculatorPanel extends JPanel {

    final static private String DIGITS = "1234567890";      // IS IT OKAY to include ZERO?
    // Code above replaced concept below
    // final static ArrayList<String> UNARY_OPS = new ArrayList<>();

    private int parenCount;
    private int curlyCount;

    //private boolean insideCurly, insideParen;     //PROBABLY not necessary

    private enum mostRecentControlSymbol
    {
        openParen, closePren, openCurly, closeCurly;
    }

    private String currInput;

    private final JTextArea display = new JTextArea("0");

    // Keep track of whether other digits are allowed after a zero,
    // i.e. after a decimal point or within the digits of a larger number
    private boolean zeroOK;
    final private JButton a1Button, a2Button, a3Button, addButton, subtractButton;
    final private JButton a4Button, a5Button, a6Button, multiplyButton, divideButton;
    final private JButton a7Button, a8Button, a9Button, sinButton, cosButton;
    final private JButton a0Button, openParenButton, closeParenButton, tanButton, cotButton;
    final private JButton equalButton, openCurlyButton, closeCurlyButton, lnButton, logButton;
    final private JButton decimalButton, expButton, clearButton;

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
        panel.setLayout(new GridLayout(6,5));

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
        enableUnaryOps();
        enableDigits();

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
            enableUnaryOps();       // are these ever disabled??
        });

        a2Button.addActionListener(e -> {
            String tempInput = "2";
            currInput += tempInput;
            display.setText(currInput);
        });

        a3Button.addActionListener(e -> {
            String tempInput = "3";
            currInput += tempInput;
            display.setText(currInput);
        });

        a4Button.addActionListener(e -> {
            String tempInput = "4";
            currInput += tempInput;
            display.setText(currInput);
        });

        a5Button.addActionListener(e -> {
            String tempInput = "5";
            currInput += tempInput;
            display.setText(currInput);
        });

        a6Button.addActionListener(e -> {
            String tempInput = "6";
            currInput += tempInput;
            display.setText(currInput);
        });

        a7Button.addActionListener(e -> {
            String tempInput = "7";
            currInput += tempInput;
            display.setText(currInput);
        });

        a8Button.addActionListener(e -> {
            String tempInput = "8";
            currInput += tempInput;
            display.setText(currInput);
        });

        a9Button.addActionListener(e -> {
            String tempInput = "9";
            currInput += tempInput;
            display.setText(currInput);
        });

        a0Button.addActionListener(e -> {
            String tempInput = "0";
            currInput += tempInput;
            display.setText(currInput);
        });

        // Bind setEnabled function to same boolean
        addButton.addActionListener(e -> {
            // Disable necessary buttons
            disableBinaryOps();
            closeParenButton.setEnabled(false);
            closeCurlyButton.setEnabled(false);
        });

        subtractButton.addActionListener(e -> {
            // Disable necessary buttons
            disableBinaryOps();
            closeParenButton.setEnabled(false);
            closeCurlyButton.setEnabled(false);
        });

        multiplyButton.addActionListener(e -> {
            // Disable necessary buttons
            disableBinaryOps();
            closeParenButton.setEnabled(false);
            closeCurlyButton.setEnabled(false);
        });

        divideButton.addActionListener(e -> {
            // Disable necessary buttons
            disableBinaryOps();
            a0Button.setEnabled(false);
            closeParenButton.setEnabled(false);
            closeCurlyButton.setEnabled(false);

            // Enable necessary buttons
        });

        sinButton.addActionListener(e -> {
            // Disable necessary buttons
            disableBinaryOps();
            closeParenButton.setEnabled(false);
            closeCurlyButton.setEnabled(false);
        });

        cosButton.addActionListener(e -> {
            // Disable necessary buttons
            disableBinaryOps();
            closeParenButton.setEnabled(false);
            closeCurlyButton.setEnabled(false);
        });

        tanButton.addActionListener(e -> {
            // Disable necessary buttons
            disableBinaryOps();
            closeParenButton.setEnabled(false);
            closeCurlyButton.setEnabled(false);
        });

        cotButton.addActionListener(e -> {
            // Disable necessary buttons
            disableBinaryOps();
            a0Button.setEnabled(false);
            closeParenButton.setEnabled(false);
            closeCurlyButton.setEnabled(false);
        });

        lnButton.addActionListener(e -> {
            // Disable necessary buttons
            disableBinaryOps();
            a0Button.setEnabled(false);
            closeParenButton.setEnabled(false);
            closeCurlyButton.setEnabled(false);
        });

        logButton.addActionListener(e -> {
            // Disable necessary buttons
            disableBinaryOps();
            a0Button.setEnabled(false);
            enableClose(false);
        });

        expButton.addActionListener(e -> {
            // Disable necessary buttons
            disableBinaryOps();
            enableClose(false);
        });

        decimalButton.addActionListener(e -> {
            String tempInput;
            if ( currInput.length() == 0 ||
                    !DIGITS.contains(currInput.substring(currInput.length() - 1)))
            {
                tempInput = "0.";
            }
            else
            {
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
            if (parenCount > 0)
            {
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
            disableBinaryOps();
            disableZero();
        });

        closeCurlyButton.addActionListener(e -> {
            if (curlyCount > 0)
            {
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
            display.setText(evaluate(currInput));
            currInput = "";
            // Disable necessary buttons
        });

        add(panel, "Center");
    }

    // Evaluate
    private String evaluate(String currInput)
    {
        String evaluated = "";
        Stack<String> operands = new Stack<>();
        Stack<String> operators = new Stack<>();

        // Shunting yard?? Lol
        return evaluated;
    }

    private void enableDecimal()
    {
        decimalButton.setEnabled(true);
    }
    private void disableDecimal()
    {
        decimalButton.setEnabled(false);
    }

    private void enableZero()
    {
        a0Button.setEnabled(true);
    }
    private void disableZero()
    {
        a0Button.setEnabled(false);
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
    private void disableDigits()        // May never be needed?
    {
        a1Button.setEnabled(false);
        a2Button.setEnabled(false);
        a3Button.setEnabled(false);
        a4Button.setEnabled(false);
        a5Button.setEnabled(false);
        a6Button.setEnabled(false);
        a7Button.setEnabled(false);
        a8Button.setEnabled(false);
        a9Button.setEnabled(false);
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
    private void disableUnaryOps()
    {
        sinButton.setEnabled(false);
        cosButton.setEnabled(false);
        tanButton.setEnabled(false);
        cotButton.setEnabled(false);
        lnButton.setEnabled(false);
        logButton.setEnabled(false);
        subtractButton.setEnabled(false);
    }

    private void enableBinaryOps()
    {
        addButton.setEnabled(true);
        subtractButton.setEnabled(true);
        multiplyButton.setEnabled(true);
        divideButton.setEnabled(true);
        equalButton.setEnabled(true);
        expButton.setEnabled(true);
    }
    private void disableBinaryOps()
    {
        addButton.setEnabled(false);
        //subtractButton.setEnabled(false);  REMAINS enabled, since (-) is also unary operator
        multiplyButton.setEnabled(false);
        divideButton.setEnabled(false);
        equalButton.setEnabled(false);     //IDK if this counts as a binary op??
        expButton.setEnabled(false);
    }

    private void enableClose(boolean enabled)       // Open are always enabled??
    {
        closeParenButton.setEnabled(enabled);
        closeCurlyButton.setEnabled(enabled);
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(false);
        JFrame frame = new JFrame();
        frame.setTitle("Calculator");
        frame.setSize(800,400);
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
