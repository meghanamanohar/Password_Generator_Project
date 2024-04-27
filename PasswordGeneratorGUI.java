import javax.swing.*;   // you want to use or import the packages which has inbuild classes so that you can build you GUI project

import java.awt.event.ActionEvent; //notices event is occuring
import java.awt.event.ActionListener;  //ActionListener interface is responsible for processing events
import javax.swing.border.EmptyBorder;  //A class which provides an empty, transparent border which takes up space but does no drawing. 
import java.awt.datatransfer.StringSelection;   //A class which implements the capability required to transfer a simple java String in plain text format
import java.awt.datatransfer.Clipboard;  // class that implements a mechanism to transfer data using cut/copy/paste operations.
import java.io.*;
//render the gui components(frontend)
//this class will inherit from the JFrame class
import java.awt.*;  //(Abstract Window Toolkit) is an API to develop GUI or window-based applications in java.
import java.util.*;

public class PasswordGeneratorGUI extends JFrame {
    private PasswordGenerator passwordGenerator;
    private JLabel passwordStrengthLabel;
    private JTextArea passwordLengthInputArea;
    
     //private JTextArea passwordOutput;

    public PasswordGeneratorGUI() {

        //render frame and add a title
        super("Password Generator");

        //set the size of the gui
        setSize(540, 630);

        //prevent gui from being able to resize whenevr we run it
        setResizable(false);

        //we will set the layout to be null to have control over the position and size of our components in our app
        setLayout(null);

        //terminates the program when gui is closed(ends the process)
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //center the gui to the screen
        setLocationRelativeTo(null);

        //init password generator
        passwordGenerator= new PasswordGenerator();

        //render the gui components 
        addGuiComponents();

        setVisible(true);
    }

    public void addGuiComponents() {

        //create title text
        JLabel titleLabel = new JLabel("Password Generator");

        //increase the font size and make it bold 
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 32));

        //center the text to the screen
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //set x,y coordinates and width/height values
        titleLabel.setBounds(0, 10, 540, 39);

        //add to gui
        add(titleLabel);
        
        //create result text area
        JTextArea passwordOutput = new JTextArea();

        //prevent editing the text area to define length of password
        passwordOutput.setEditable(false); //user doesnt wanna change length of password at first but acc to him it can change
        passwordOutput.setFont(new Font("Dialog", Font.BOLD, 32));

        //add scrollability incase output becomes too big
        JScrollPane passwordOutputPane = new JScrollPane(passwordOutput);
        passwordOutputPane.setBounds(25, 97, 479, 70);

        //creating black border around text area
        passwordOutputPane.setBorder(BorderFactory.createLineBorder(Color.black));
        add(passwordOutputPane);

        //creating password length label(heading)
        JLabel passwordLengthLabel = new JLabel("Password Length:");
        passwordLengthLabel.setFont(new Font("Dialog",Font.PLAIN,32));
        passwordLengthLabel.setBounds(25,215,272,39);
        add(passwordLengthLabel);

        //creating password length input(that box)
        JTextArea passwordLengthInputArea=new JTextArea();
        passwordLengthInputArea.setFont(new Font("Dialog",Font.PLAIN,32));
        passwordLengthInputArea.setBorder(BorderFactory.createLineBorder(Color.black));
        passwordLengthInputArea.setBounds(310,215,192,39);
        add(passwordLengthInputArea);

        //create toggle buttons
        //uppercase toggle
        JToggleButton uppercaseToggle=new JToggleButton("Uppercase");
        uppercaseToggle.setFont(new Font("Dialog",Font.PLAIN,26));
        uppercaseToggle.setBounds(25,302,225,56);
        add(uppercaseToggle);
         
        //lowercase toggle
        JToggleButton lowercaseToggle=new JToggleButton("Lowercase");
        lowercaseToggle.setFont(new Font("Dialog",Font.PLAIN,26));
        lowercaseToggle.setBounds(282,302,225,56);
        add(lowercaseToggle);

        //numbers toggle
        JToggleButton numbersToggle=new JToggleButton("Numbers");
        numbersToggle.setFont(new Font("Dialog",Font.PLAIN,26));
        numbersToggle.setBounds(25,373,225,56);
        add(numbersToggle);

        //symbols toggle
        JToggleButton symbolsToggle=new JToggleButton("Symbols");
        symbolsToggle.setFont(new Font("Dialog",Font.PLAIN,26));
        symbolsToggle.setBounds(282,373,225,56);
        add(symbolsToggle);

        //create generate button
        JButton generateButton=new JButton("Generate");
        generateButton.setFont(new Font("Dialog",Font.PLAIN,32));
        generateButton.setBounds(155,477,222,41);

        generateButton.addActionListener(new ActionListener()
         {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                //validation:generate a password only when length>0 and one of the toggled buttons is pressed
                if(passwordLengthInputArea.getText().length()<=0) return;
                boolean anyToggleSelected=lowercaseToggle.isSelected() ||
                uppercaseToggle.isSelected() ||
                numbersToggle.isSelected() ||
                symbolsToggle.isSelected();
                 
                //generate password
                //converts the text to an integer value
                int passwordLength=Integer.parseInt(passwordLengthInputArea.getText());
                if(anyToggleSelected)
                {
                    String generatePassword=passwordGenerator.generatePassword(passwordLength,
                uppercaseToggle.isSelected(),
                lowercaseToggle.isSelected(),
                numbersToggle.isSelected(),
                symbolsToggle.isSelected());

                //display password back to user
                passwordOutput.setText(generatePassword);

                // Calculate and display password strength
                    PasswordStrength strength = passwordGenerator.calculatePasswordStrength(generatePassword);
                    String strengthMessage;
                    switch (strength) {
                        case WEAK:
                            strengthMessage = "Password Strength: Weak";
                            break;
                        case MEDIUM:
                            strengthMessage = "Password Strength: Medium";
                            break;
                        case STRONG:
                            strengthMessage = "Password Strength: Strong";
                            break;
                        default:
                            strengthMessage = "Password Strength: Unknown";
                            break;
                    }
                    passwordStrengthLabel.setText(strengthMessage);

                }
            }
        });
        add(generateButton);

        passwordStrengthLabel = new JLabel();
        passwordStrengthLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        passwordStrengthLabel.setBounds(25, 520, 479, 30);
        passwordStrengthLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordStrengthLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        add(passwordStrengthLabel);


        //COPY TO CLIPBOARD
        JButton copyButton = new JButton("Copy to Clipboard");
        copyButton.setFont(new Font("Dialog", Font.PLAIN, 20));
        copyButton.setBounds(25, 560, 220, 26);
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = passwordOutput.getText();
                if (!password.isEmpty()) {
                    StringSelection selection = new StringSelection(password);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(selection, null);
                    JOptionPane.showMessageDialog(null, "Password copied to clipboard!");
                }
            }
        });
        add(copyButton);


        //SAVE PASSWORD
        JButton saveButton = new JButton("Save Password");
        saveButton.setFont(new Font("Dialog", Font.PLAIN, 18));
        saveButton.setBounds(260, 560, 230, 26);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String generatedPassword = passwordOutput.getText();
                if (!generatedPassword.isEmpty()) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Save Password");
                    int userSelection = fileChooser.showSaveDialog(PasswordGeneratorGUI.this);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        FileUtil.saveStringToFile(generatedPassword, fileToSave);
                        JOptionPane.showMessageDialog(PasswordGeneratorGUI.this,
                                "Password saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(PasswordGeneratorGUI.this,
                            "No password generated yet!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(saveButton);

        // Create the custom character set text field


    }

    static class FileUtil {
        public static void saveStringToFile(String content, File file) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                writer.write(content);
                writer.newLine();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
             