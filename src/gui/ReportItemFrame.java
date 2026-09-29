package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.*;
import storage.LostFoundManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReportItemFrame extends JFrame implements ActionListener {
	
	User currentUser;
    LostFoundManager manager;
    JPanel panel = new JPanel();
    
    JLabel titleLabel = new JLabel("Fill in the informations for your LOST or FOUND item\n");
    JLabel categoryLabel = new JLabel("\nCategory:");  
    JComboBox<String> categoryBox = new JComboBox<String>(new String[] { "Document", "Electronic", "Accessory", "Book", "Other" });
    JLabel statusLabel = new JLabel("Status:");
    JComboBox<String> statusBox = new JComboBox<String>(new String[] { "LOST", "FOUND" });
    JLabel descriptionLabel = new JLabel("Description:");
    JTextField descriptionField = new JTextField(20);
    JLabel locationLabel = new JLabel("Location:");
    JTextField locationField = new JTextField(20);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    JLabel dateLabel = new JLabel("Date (DD-MM-YYYY):");
    JTextField dateField = new JTextField(LocalDate.now().format(formatter), 15);
    JLabel timeLabel = new JLabel("Time (HH:MM):");
    JTextField timeField = new JTextField(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")), 15);
    JLabel uniqueMarkLabel = new JLabel("Unique Mark:");
    JTextField uniqueMarkField = new JTextField(20);
    JLabel typeLabel = new JLabel("Document Type:");
    JTextField typeField = new JTextField(20);
    JLabel docNumLabel = new JLabel("Document Number(e.g. NID No.):");
    JTextField docNumField = new JTextField(20);
    JLabel brandLabel = new JLabel("Brand:");
    JTextField brandField = new JTextField(20);
    JLabel serialLabel = new JLabel("Serial Number:");
    JTextField serialField = new JTextField(20);
    JLabel materialLabel = new JLabel("Material:");
    JTextField materialField = new JTextField(20);
    JLabel colorLabel = new JLabel("Color:");
    JTextField colorField = new JTextField(20);
    JLabel bookTitleLabel = new JLabel("Book Title:");
    JTextField bookTitleField = new JTextField(20);
    JLabel isbnLabel = new JLabel("ISBN:");
    JTextField isbnField = new JTextField(20);
    JLabel authorLabel = new JLabel("Author:");
    JTextField authorField = new JTextField(20);
    JLabel noteLabel = new JLabel("Additional Note:");
    JTextField noteField = new JTextField(20);
    JLabel messageLabel = new JLabel(" ");
    
    //Buttons
    JButton reportButton = new JButton("Report Item");
    JButton backButton = new JButton("Back");
    

    
    
    

    // Constructor
    ReportItemFrame(User currentUser, LostFoundManager manager) {

    	this.currentUser = currentUser;
        this.manager = manager;
        
        
        setTitle("ReClaim - Report Item");
        setSize(1280, 380);
        setLocationRelativeTo(null);
        setResizable(true);
        setBackground(Color.LIGHT_GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container con = this.getContentPane();
        con.add(panel);

        //Panel
       // panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setLayout(new FlowLayout());
        panel.add(titleLabel);
        panel.add(categoryLabel);
        panel.add(categoryBox);
        panel.add(statusLabel);
        panel.add(statusBox);
        panel.add(descriptionLabel);
        panel.add(descriptionField);
        panel.add(locationLabel);
        panel.add(locationField);
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(timeLabel);
        panel.add(timeField);
        panel.add(uniqueMarkLabel);
        panel.add(uniqueMarkField);
        panel.add(typeLabel);
        panel.add(typeField);
        panel.add(docNumLabel);
        panel.add(docNumField);
        panel.add(brandLabel);
        panel.add(brandField);
        panel.add(serialLabel);
        panel.add(serialField);
        panel.add(materialLabel);
        panel.add(materialField);
        panel.add(colorLabel);
        panel.add(colorField);
        panel.add(bookTitleLabel);
        panel.add(bookTitleField);
        panel.add(isbnLabel);
        panel.add(isbnField);
        panel.add(authorLabel);
        panel.add(authorField);
        panel.add(noteLabel);
        panel.add(noteField);
        panel.add(reportButton);
        panel.add(backButton);
        panel.add(messageLabel);


        // Action listener
        categoryBox.addActionListener(this);
        reportButton.addActionListener(this);
        backButton.addActionListener(this);

        updateVisibleFields();
        setVisible(true);
    }

    
    // Button actions
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        if (e.getSource() == categoryBox)       {updateVisibleFields();}
        else if (command.equals("Report Item")) {reportItem();}
        else if (command.equals("Back"))        {dispose();}
    }
    
    
    
    
    // Selected category options
    public void updateVisibleFields() {

        String category = (String) categoryBox.getSelectedItem();

        typeLabel.setVisible(false);
        typeField.setVisible(false);
        docNumLabel.setVisible(false);
        docNumField.setVisible(false); 
        brandLabel.setVisible(false);
        brandField.setVisible(false);
        serialLabel.setVisible(false);
        serialField.setVisible(false);
        materialLabel.setVisible(false);
        materialField.setVisible(false);
        colorLabel.setVisible(false);
        colorField.setVisible(false);
        bookTitleLabel.setVisible(false);
        bookTitleField.setVisible(false);
        isbnLabel.setVisible(false);
        isbnField.setVisible(false);
        authorLabel.setVisible(false);
        authorField.setVisible(false);
        noteLabel.setVisible(false);
        noteField.setVisible(false);

        if (category.equals("Document")) {
            typeLabel.setVisible(true);
            typeField.setVisible(true);
            docNumLabel.setVisible(true);
            docNumField.setVisible(true);
        }
        else if (category.equals("Electronic")) {
            brandLabel.setVisible(true);
            brandField.setVisible(true);
            serialLabel.setVisible(true);
            serialField.setVisible(true);
        }
        else if (category.equals("Accessory")) {
            materialLabel.setVisible(true);
            materialField.setVisible(true);
            colorLabel.setVisible(true);
            colorField.setVisible(true);
        }
        else if (category.equals("Book")) {
            bookTitleLabel.setVisible(true);
            bookTitleField.setVisible(true);
            isbnLabel.setVisible(true);
            isbnField.setVisible(true);
            authorLabel.setVisible(true);
            authorField.setVisible(true);
        }
        else {
            noteLabel.setVisible(true);
            noteField.setVisible(true);
        }
    }

    
    // Report 
    public void reportItem() {

        
        //GET Attributes
    	String category = (String) categoryBox.getSelectedItem();
        ItemStatus status = ItemStatus.valueOf((String) statusBox.getSelectedItem());
        String description = descriptionField.getText();
        String location = locationField.getText();
        String uniqueMark = uniqueMarkField.getText();
        LocalDate date= manager.parseDate(dateField.getText(), "dd-MM-yyyy");;
        LocalTime time= manager.parseTime(timeField.getText(), "HH:mm");;
        //---------
  

        
        //ADDING ITEM TO FILE
        String[] extraFields = new String[3];

        if (category.equals("Document")) {
            extraFields[0] = typeField.getText();
            extraFields[1] =docNumField.getText();
        }
        else if (category.equals("Electronic")) {
        	extraFields[0] = brandField.getText();
            extraFields[1] =serialField.getText();
           
        }
        else if (category.equals("Accessory")) {
        	extraFields[0] = materialField.getText();
            extraFields[1] =colorField.getText();
            
        }
        else if (category.equals("Book")) {
        	extraFields[0] = bookTitleField.getText();
            extraFields[1] =isbnField.getText();
            extraFields[2] =authorField.getText();
            
        }
        else {
        	extraFields[0] = noteField.getText();
        }

        Item item = manager.createItem(category, description, location, date, time, status, uniqueMark,
                currentUser.getUserId(), currentUser.getName(), currentUser.getPhoneNumber(), extraFields);

        manager.addItem(item);//addItem method from LostFoundManager
        messageLabel.setText("Item reported successfully. Item ID: " + item.getItemId());
        //----------------
        

        //Emptying Fields
        descriptionField.setText("");
        locationField.setText("");
        uniqueMarkField.setText("");
        typeField.setText("");
        docNumField.setText("");
        brandField.setText("");
        serialField.setText("");
        materialField.setText("");
        colorField.setText("");
        bookTitleField.setText("");
        isbnField.setText("");
        authorField.setText("");
        noteField.setText("");
    }
}
