package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import exceptions.InvalidStatusTransitionException;
import model.Item;
import model.ItemStatus;
import model.User;
import storage.LostFoundManager;

public class MyListingsFrame extends JFrame implements ActionListener {

	User currentUser;
    LostFoundManager manager;
    JPanel panel = new JPanel();
    
    JLabel idLabel = new JLabel("Item ID:");
    JLabel messageLabel = new JLabel(" ");
    JTextArea display = new JTextArea(25,60);
    JScrollPane scrollPane = new JScrollPane(display);
    JTextField idField = new JTextField(15);

    //Buttons
    JButton returnedButton = new JButton("Mark as Returned");
    JButton deleteButton = new JButton("Delete Listing");
    JButton refreshButton = new JButton("Refresh");
    JButton backButton = new JButton("Back");

    
    // Constructor
    MyListingsFrame(User currentUser, LostFoundManager manager) {

        this.currentUser = currentUser;
        this.manager = manager;
        
        setTitle("ReClaim - My Listings");
        setSize(720, 550);
        setLocationRelativeTo(null);
        setBackground(Color.lightGray);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Container
        Container con = this.getContentPane();
        con.add(panel);
        display.setEditable(false);

        //Panel    
        panel.add(scrollPane);
        panel.add(idLabel);
        panel.add(idField);
        panel.add(returnedButton);
        panel.add(deleteButton);
        panel.add(refreshButton);
        panel.add(backButton);
        panel.add(messageLabel);

        //Action listener
        returnedButton.addActionListener(this);
        deleteButton.addActionListener(this);
        refreshButton.addActionListener(this);
        backButton.addActionListener(this);

        refresh();
        setVisible(true);
    }

    
    
    // Button Actions
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        if (command.equals("Mark as Returned"))    {markReturned();}
        else if (command.equals("Delete Listing")) {deleteListing();}
        else if (command.equals("Refresh"))        {refresh();}
        else if (command.equals("Back"))           {dispose();}
    }

    
    

    // Return Status change of an Item
    public void markReturned() {

        String id = idField.getText().trim();

        if (id.equals("")) {
            messageLabel.setText("Type an Item ID first.");
            return;
        }

        try {
            manager.updateStatus(id, ItemStatus.RETURNED);
            messageLabel.setText("Item marked as RETURNED.");
            idField.setText("");
            refresh();
        }
        catch (InvalidStatusTransitionException e) {
            messageLabel.setText(e.getMessage());
        }
    }

    //-------------------------------
    
    
    
    // Delete listing
    public void deleteListing() {

        String id = idField.getText().trim();

        if (id.equals("")) {
            messageLabel.setText("Type an Item ID first.");
            return;
        }

        boolean deleted = manager.deleteItem(id, currentUser.getUserId());

        if (deleted) {
            messageLabel.setText("Listing deleted.");
            idField.setText("");
            refresh();
        }
        
        else {
            messageLabel.setText("Could not delete listing.");
        }
    }
    
    //-------------------------------
    
    
    
    // Show listings
    public void refresh() {

        display.setText("");

        ArrayList<Item> myItems = manager.getItemsByOwner(currentUser.getUserId());

        if (myItems.size() == 0) {
            display.setText("You have no listings.");
            return;
        }

        for (int i = 0; i < myItems.size(); i++) {

            Item item = myItems.get(i);

            display.append("ID: "+item.getItemId()+ "\n"+"Category: "+item.getCategory()+"\n"+item.getCategoryDetails()+"\n"+"Description: " +item.getDescription() 
            				+"\n" + "Location: "+item.getLocation()+ "\n"+ "Date: "+item.getDate()+"\n"+ "Status: " +item.getStatus() 
            				+ "\n<----------------------------->\n");
        }
    }
    
    
}
