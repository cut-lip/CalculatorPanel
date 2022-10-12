import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorPanel extends JPanel {

    private int openParen;
    private int closeParen;
    private int openCurly;
    private int closeCurly;

    private String currInput;

    private String valuated;

    private JTextArea display = new JTextArea("0");

    // Keep track of whether other digits are allowed after a zero,
    // i.e. after a decimal point or within the digits of a larger number
    private boolean zeroOK;
    private JButton a1Button, a2Button, a3Button, addButton, subtractButton;
    private JButton a4Button, a5Button, a6Button, multiplyButton, divideButton;
    private JButton a7Button, a8Button, a9Button, sinButton, cosButton;
    private JButton a0Button, openParenButton, closeParenButton, tanButton, cotButton;
    private JButton equalButton, openCurlyButton, closeCurlyButton, lnButton, logButton;
    private JButton decimalButton, expButton;

    public CalculatorPanel() {
        setLayout(new BorderLayout(2, 2));
        //this.setBackground(Color.green);

        display.setEditable(false);
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


        a1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tempInput = "1";
                display.setText(currInput + tempInput);
            }
        });
        a2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        a3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        a4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        a5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        a6Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        a7Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        a8Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        a9Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        a0Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!zeroOK) {
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
            }
        });
        a1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        a2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        a3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        a4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        a5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        a6Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        a7Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        a8Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        a9Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        display.addComponentListener(new ComponentAdapter() {
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            // Bind setEnabled function to same boolean
            public void actionPerformed(ActionEvent e) {
                // Disable necessary buttons
                addButton.setEnabled(false);
                //subtract.setEnabled(false);
                multiplyButton.setEnabled(false);
                divideButton.setEnabled(false);
                equalButton.setEnabled(false);
                expButton.setEnabled(false);
                closeParenButton.setEnabled(false);
                closeCurlyButton.setEnabled(false);
            }
        });
        subtractButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Disable necessary buttons
                addButton.setEnabled(false);
                //subtract.setEnabled(false);
                multiplyButton.setEnabled(false);
                divideButton.setEnabled(false);
                equalButton.setEnabled(false);
                expButton.setEnabled(false);
                closeParenButton.setEnabled(false);
                closeCurlyButton.setEnabled(false);
            }
        });
        multiplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Disable necessary buttons
                addButton.setEnabled(false);
                //subtract.setEnabled(false);
                multiplyButton.setEnabled(false);
                divideButton.setEnabled(false);
                equalButton.setEnabled(false);
                expButton.setEnabled(false);
                closeParenButton.setEnabled(false);
                closeCurlyButton.setEnabled(false);
            }
        });
        divideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Disable necessary buttons
                addButton.setEnabled(false);
                //subtract.setEnabled(false);
                multiplyButton.setEnabled(false);
                divideButton.setEnabled(false);
                equalButton.setEnabled(false);
                expButton.setEnabled(false);
                a0Button.setEnabled(false);
                closeParenButton.setEnabled(false);
                closeCurlyButton.setEnabled(false);

                // Enable necessary buttons
            }
        });
        sinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Disable necessary buttons
                addButton.setEnabled(false);
                //subtract.setEnabled(false);
                multiplyButton.setEnabled(false);
                divideButton.setEnabled(false);
                equalButton.setEnabled(false);
                expButton.setEnabled(false);
                closeParenButton.setEnabled(false);
                closeCurlyButton.setEnabled(false);
            }
        });
        cosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Disable necessary buttons
                addButton.setEnabled(false);
                //subtract.setEnabled(false);
                multiplyButton.setEnabled(false);
                divideButton.setEnabled(false);
                equalButton.setEnabled(false);
                expButton.setEnabled(false);
                closeParenButton.setEnabled(false);
                closeCurlyButton.setEnabled(false);
            }
        });
        tanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Disable necessary buttons
                addButton.setEnabled(false);
                //subtract.setEnabled(false);
                multiplyButton.setEnabled(false);
                divideButton.setEnabled(false);
                equalButton.setEnabled(false);
                expButton.setEnabled(false);
                closeParenButton.setEnabled(false);
                closeCurlyButton.setEnabled(false);
            }
        });
        cotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Disable necessary buttons
                addButton.setEnabled(false);
                //subtract.setEnabled(false);
                multiplyButton.setEnabled(false);
                divideButton.setEnabled(false);
                equalButton.setEnabled(false);
                expButton.setEnabled(false);
                a0Button.setEnabled(false);
                closeParenButton.setEnabled(false);
                closeCurlyButton.setEnabled(false);
            }
        });
        lnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Disable necessary buttons
                addButton.setEnabled(false);
                //subtract.setEnabled(false);
                multiplyButton.setEnabled(false);
                divideButton.setEnabled(false);
                equalButton.setEnabled(false);
                expButton.setEnabled(false);
                a0Button.setEnabled(false);
                closeParenButton.setEnabled(false);
                closeCurlyButton.setEnabled(false);
            }
        });
        logButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Disable necessary buttons
                addButton.setEnabled(false);
                //subtract.setEnabled(false);
                multiplyButton.setEnabled(false);
                divideButton.setEnabled(false);
                equalButton.setEnabled(false);
                expButton.setEnabled(false);
                a0Button.setEnabled(false);
                closeParenButton.setEnabled(false);
                closeCurlyButton.setEnabled(false);
            }
        });
        expButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Disable necessary buttons
                addButton.setEnabled(false);
                //subtract.setEnabled(false);
                multiplyButton.setEnabled(false);
                divideButton.setEnabled(false);
                equalButton.setEnabled(false);
                expButton.setEnabled(false);
                closeParenButton.setEnabled(false);
                closeCurlyButton.setEnabled(false);
            }
        });
        decimalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Disable necessary buttons
                addButton.setEnabled(false);
                //subtract.setEnabled(false);
                multiplyButton.setEnabled(false);
                divideButton.setEnabled(false);
                equalButton.setEnabled(false);
                expButton.setEnabled(false);
                closeParenButton.setEnabled(false);
                closeCurlyButton.setEnabled(false);
            }
        });
        openParenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Disable necessary buttons

            }
        });
        closeParenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        openCurlyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        closeCurlyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(panel, "Center");
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(false);
        JFrame frame = new JFrame();
        frame.setTitle("Calculator");
        frame.setSize(300,400);
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
