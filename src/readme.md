# Welcome to CalculatorPanel

### Have you ever wanted a pretty basic GUI based calculator that you can run from the command prompt with naught but a simple Java file? Look no further.

##### Compilation instructions:

Navigate to the directory containing CalculatorPanel.java, then compile the java file and run the executable:
1. On Windows Command Prompt, this can be accomplished by navigating to the necessary directory with **cd**.
2. Next, compiling the file with the command **javac CalculatorPanel.java**.
3. Now you can launch the application with the command **java CalculatorPanel**.

##### Contract of use is as follows:
If you wish to use a decimal point to represent a value less than 1,
the preceding zero will be automatically provided for you. ( . -> 0. ).

For functions and exponent (i.e. sin, cos, tan, cot, ln, log, ^) an opening parenthesis will be provided
for the user. It is the users responsibility to close the set: i.e. "cos (".

Empty matching sets of parenthesis will be automatically removed upon input: i.e. ( ), { }. Mismatched parenthesis, empty or otherwise, will result in an error message displaying the issue upon evaluation: i.e. ( }, or { ).

Trigonometric functions are evaluated in radians.

A multiplication operator (*) will be automatically placed after a closing parenthesis and before a digit or an opening parenthesis, if omitted by user.

Undefined operations include cot ( 0 ), ln ( 0 ), and log ( 0 ). Negative numbers are also outside the domain of ln and log, and will result in an error message identifying the issue upon evaluation.