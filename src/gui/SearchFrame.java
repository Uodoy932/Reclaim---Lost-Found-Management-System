package gui;

import model.*;
import storage.LostFoundManager;
import storage.MatchResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SearchFrame extends JFrame implements ActionListener {

    User currentUser;
    LostFoundManager manager;
    JPanel panel = new JPanel();
    
    
    JLabel statusLabel = new JLabel("I am searching for a:");
    JComboBox<String> statusBox = new JComboBox<String>(new String[] { "LOST", "FOUND" });
    JLabel categoryLabel = new JLabel("item, Category:");
    JComboBox<String> categoryBox = new JComboBox<String>(new String[] { "Document", "Electronic", "Accessory", "Book", "Other" });
    
    
    JLabel descriptionLabel = new JLabel("Description:");
    JTextField descriptionField = new JTextField(15);
    JLabel locationLabel = new JLabel("Location:");
    JTextField locationField = new JTextField(15);
    JLabel dateLabel = new JLabel("Date (DD-MM-YYYY):");
    JTextField dateField = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), 10);
    JLabel timeLabel = new JLabel("Time (HH:MM):");
    JTextField timeField = new JTextField(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")), 10);
    JLabel uniqueMarkLabel = new JLabel("Unique Mark:");
    JTextField uniqueMarkField = new JTextField(15);
    JLabel docTypeLabel = new JLabel("Document Type:");
    JTextField docTypeField = new JTextField(15);
    JLabel docNumLabel = new JLabel("Document No:");
    JTextField docNumField = new JTextField(15);
    JLabel brandLabel = new JLabel("Brand:");
    JTextField brandField = new JTextField(15);
    JLabel serialLabel = new JLabel("Serial Number:");
    JTextField serialField = new JTextField(15);
    JLabel materialLabel = new JLabel("Material:");
    JTextField materialField = new JTextField(15);
    JLabel colorLabel = new JLabel("Color:");
    JTextField colorField = new JTextField(15);
    JLabel titleLabel = new JLabel("Book Title:");
    JTextField titleField = new JTextField(15);
    JLabel isbnLabel = new JLabel("ISBN:");
    JTextField isbnField = new JTextField(15);
    JLabel authorLabel = new JLabel("Author:");
    JTextField authorField = new JTextField(15);
    JLabel noteLabel = new JLabel("Note:");
    JTextField noteField = new JTextField(15);
    JTextArea resultsArea = new JTextArea(30, 100);
    JScrollPane scrollPane = new JScrollPane(resultsArea);
    JLabel messageLabel = new JLabel(" ");
    
    
    //Buttons
    JButton searchBtn = new JButton("Search");
    JButton backButton = new JButton("Back");
    

    // Constructor
    public SearchFrame(User currentUser, LostFoundManager manager) {

        this.currentUser = currentUser;
        this.manager = manager;

        setTitle("ReClaim - Search for a Match");
        setSize(1165, 650);
        setLocationRelativeTo(null);
        setBackground(Color.lightGray);
        setResizable(true); 
        resultsArea.setEditable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        
        Container con = this.getContentPane();
        con.add(panel);

        //panel.setLayout(new FlowLayout());
        //panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        //panel.setLayout(new GridBagLayout());
        panel.add(statusLabel);
        panel.add(statusBox);
        panel.add(categoryLabel);
        panel.add(categoryBox);
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
        panel.add(docTypeLabel);
        panel.add(docTypeField);
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
        panel.add(titleLabel);
        panel.add(titleField);
        panel.add(isbnLabel);
        panel.add(isbnField);
        panel.add(authorLabel);
        panel.add(authorField);
        panel.add(noteLabel);
        panel.add(noteField);
        panel.add(searchBtn);
        //panel.add(resultsArea);
        panel.add(backButton);
        panel.add(messageLabel);
        panel.add(scrollPane);
        
       // scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        
        // Actions
        categoryBox.addActionListener(this);
        searchBtn.addActionListener(this);
        backButton.addActionListener(this);

        updateVisibleFields();
        setVisible(true);
    }

    //Actions
    public void actionPerformed(ActionEvent e) {

        String s = e.getActionCommand();

        if (e.getSource() == categoryBox) {updateVisibleFields();}
        else if (s.equals("Search"))	  {runSearch();}
        else if (s.equals("Back")) 		  {dispose();}
    }
    
    
    
    //Visible Fields
    public void updateVisibleFields() {

        String category = (String) categoryBox.getSelectedItem();

        docTypeLabel.setVisible(false);
        docTypeField.setVisible(false);
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

        titleLabel.setVisible(false);
        titleField.setVisible(false);
        isbnLabel.setVisible(false);
        isbnField.setVisible(false);
        authorLabel.setVisible(false);
        authorField.setVisible(false);

        noteLabel.setVisible(false);
        noteField.setVisible(false);

        if (category.equals("Document")) {
            docTypeLabel.setVisible(true);
            docTypeField.setVisible(true);
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
            titleLabel.setVisible(true);
            titleField.setVisible(true);
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
    //--------------------------
    
    
    // Search
    public void runSearch() {

		/*
		 * if (locationField.getText().trim().equals("")){
		 * messageLabel.setText("Location is required."); return; }
		 */

        
        Item searchItem = buildSearchItem();
       
        
        //Search Result ArrayList
        List<MatchResult> results = manager.findMatches(searchItem);//Method from Lost and Found Managers
        if (results.isEmpty()) {
            resultsArea.setText("No matching items found.Please Enter more Information.");
            messageLabel.setText(" ");
            return;
        }

        
        //Result AREA
        String resultText = "";
        for (int i = 0; i < results.size(); i++) 
        {
            MatchResult r = results.get(i);//GETs percentage from MatchResult class         
            Item item = r.getItem();//GETs item object from MatchResult class  
            
            resultText = resultText + "Item ID: " + item.getItemId() + "\n";
            resultText = resultText + "Match: " + String.format("%.1f", r.getMatchPercentage()) + "%\n";
            resultText = resultText + "Category: " + item.getCategory() + "\n";
            resultText = resultText + item.getCategoryDetails() + "\n";
            resultText = resultText + "Description: " + item.getDescription() + "\n";
            resultText = resultText + "Location: " + item.getLocation() + "\n";
            resultText = resultText + "Date: " + item.getDate() + "\n";
            resultText = resultText + "Time: " + item.getTime() + "\n";
            resultText = resultText + "Status: " + item.getStatus() + "\n";
            resultText = resultText + "Reported by: " + item.getOwnerName() + "\n";
            resultText = resultText + "Contact: " + item.getOwnerContact() + "\n";
            resultText = resultText + "<------------------------->\n";
        }

        resultsArea.setText(resultText);
        messageLabel.setText("Found " + results.size() + " match(es).");
    }

    //-----------------------
    
    
    
    public Item buildSearchItem() {

        String category = (String) categoryBox.getSelectedItem();
        ItemStatus status = ItemStatus.valueOf((String) statusBox.getSelectedItem());
        String description = descriptionField.getText();
        String location = locationField.getText();
        String uniqueMark = uniqueMarkField.getText();
        LocalDate date = manager.parseDate(dateField.getText(), "dd-MM-yyyy");
        LocalTime time = manager.parseTime(timeField.getText(), "HH:mm");

        
        String[] extraFields = new String[3];

        if (category.equals("Document")) {
            extraFields[0] = docTypeField.getText();
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
        	extraFields[0] = titleField.getText();
            extraFields[1] =isbnField.getText();
            extraFields[2] =authorField.getText();
            
        }
        else {
        	extraFields[0] = noteField.getText();
        }

        return manager.createItem(category, description, location, date, time, status, uniqueMark,
                currentUser.getUserId(), currentUser.getName(), currentUser.getPhoneNumber(), extraFields);
    }
    //----------------------------------
    
}
