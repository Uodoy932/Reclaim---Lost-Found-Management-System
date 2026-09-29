package gui;

import model.User;
import exceptions.InvalidLoginException;
import storage.Authentication;
import storage.LostFoundManager;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class AuthenticationFrame extends JFrame implements ActionListener {

	Authentication auth;
    LostFoundManager manager;
    
    JPanel panel = new JPanel();
   
  
    //Labels
    JLabel idLabel = new JLabel("Student ID:");
    JLabel passwordLabel = new JLabel("Password:");
    JLabel nameLabel = new JLabel("Name:");
    JLabel emailLabel = new JLabel("Email:");
    JLabel phoneLabel = new JLabel("Phone:");
    JLabel statusLabel = new JLabel(" ");
    
    
    //Text Fields
    JTextField idField = new JTextField(15);
    JTextField passwordField = new JPasswordField(15);
    JTextField nameField = new JTextField(15);
    JTextField emailField = new JTextField(15);
    JTextField phoneField = new JTextField(15);

    //Buttons
    JButton loginButton = new JButton("Login");
    JButton registerButton = new JButton("Register");
    JButton exitButton = new JButton("Exit");
    JButton submitButton = new JButton("Submit");
    JButton cancelButton = new JButton("Cancel");

    
 
    // Constructor
    public AuthenticationFrame(Authentication auth, LostFoundManager manager) {

        this.auth = auth;
        this.manager = manager;
        
        
        setTitle("ReClaim - Login");
        setResizable(false);
        setSize(1350,600);    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        
        
        Container con = this.getContentPane();
        con.add(panel); 

        
        //Panels
        //panel.setLayout(new GridBagLayout());
        //panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        //panel.setLayout(new GridLayout());
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.lightGray);
        panel.add(idLabel);
        panel.add(idField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(exitButton);
        panel.add(submitButton);
        panel.add(cancelButton);
        panel.add(statusLabel);

        
        //ActionListener
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);
        exitButton.addActionListener(this);
        submitButton.addActionListener(this);
        cancelButton.addActionListener(this);

        showLoginMode();
        setVisible(true);
    }

    
    //Login
    public void showLoginMode() {

        nameLabel.setVisible(false);
        nameField.setVisible(false);
        emailLabel.setVisible(false);
        emailField.setVisible(false);
        phoneLabel.setVisible(false);
        phoneField.setVisible(false);
        submitButton.setVisible(false);
        cancelButton.setVisible(false);

        idLabel.setVisible(true);
        idField.setVisible(true);
        passwordLabel.setVisible(true);
        passwordField.setVisible(true);
        loginButton.setVisible(true);
        registerButton.setVisible(true);
        exitButton.setVisible(true);

        idField.setText("");
        passwordField.setText("");
        statusLabel.setText(" ");
    }

    
    
    //Register
    public void showRegisterMode() {

        loginButton.setVisible(false);
        registerButton.setVisible(false);
        exitButton.setVisible(false);

        idLabel.setVisible(true);
        idField.setVisible(true);
        passwordLabel.setVisible(true);
        passwordField.setVisible(true);
        nameLabel.setVisible(true);
        nameField.setVisible(true);
        emailLabel.setVisible(true);
        emailField.setVisible(true);
        phoneLabel.setVisible(true);
        phoneField.setVisible(true);
        submitButton.setVisible(true);
        cancelButton.setVisible(true);

        idField.setText("");
        passwordField.setText("");
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        statusLabel.setText(" ");
    }

    
    // Button Clicks
    public void actionPerformed(ActionEvent e) 
    {
        String command = e.getActionCommand();

        if (command.equals("Login"))         {login();}
        else if (command.equals("Register")) {showRegisterMode();}
        else if (command.equals("Exit"))     {System.exit(0);}
        else if (command.equals("Submit")) 	 {register();}
        else if (command.equals("Cancel"))   {showLoginMode();}
    }

    
    
    
    // Login method
    public void login() {

        String id = idField.getText();
        String password = passwordField.getText();

        try {
            User user = auth.login(id, password);//User object
            statusLabel.setText("Welcome " + user.getName());
            new StudentDashboard(user, auth, manager);//Dashboard
            dispose();
        }
        catch (InvalidLoginException e) {
            statusLabel.setText(e.getMessage());
        }
    }

    
    // Register method
    public void register() {

        String id = idField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();

        try {
        	User user = auth.register(id, name, email, phone, password);
            statusLabel.setText("Welcome "+user.getName()+". Registration successful! You can now log in.");
            showLoginMode();
        }
        catch (InvalidLoginException e) {
            statusLabel.setText(e.getMessage());
        }
    }
}
