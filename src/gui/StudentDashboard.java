package gui;

import model.User;
import storage.Authentication;
import storage.LostFoundManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentDashboard extends JFrame implements ActionListener {
	
	User currentUser;
	Authentication auth;
	LostFoundManager manager;
	
	//Panel
	JPanel panel = new JPanel();
	JLabel welcome = new JLabel();
	
	//Buttons
	JButton reportBtn = new JButton("Report Lost / Found Item");
	JButton searchBtn = new JButton("Search & Find Matches");
	JButton listingsBtn = new JButton("My Listings");
	JButton logoutBtn = new JButton("Logout");
	
	
	//Constructor
	StudentDashboard(User currentUser, Authentication auth, LostFoundManager manager) {
		
		this.currentUser = currentUser;
		this.auth = auth;
		this.manager = manager;
		
		
		setTitle("ReClaim - Dashboard");
		setSize(1000,600);
		setResizable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		Container con = this.getContentPane();
		con.add(panel);
		
		//Panel
		welcome.setText("Welcome, " + currentUser.getName()+"    ");
		panel.setLayout(new GridBagLayout());
		//panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.setBackground(Color.lightGray);
		panel.add(welcome);
		panel.add(reportBtn);
		panel.add(searchBtn);
		panel.add(listingsBtn);
		panel.add(logoutBtn);
		
		
		// Action listener
		reportBtn.addActionListener(this);
		searchBtn.addActionListener(this);
		listingsBtn.addActionListener(this);
		logoutBtn.addActionListener(this);
		setVisible(true);
	}
	
	
	//Button Action
	public void actionPerformed(ActionEvent e) {
		
		String s = e.getActionCommand();
		
		if (s.equals("Report Lost / Found Item")) {
			new ReportItemFrame(currentUser, manager).setVisible(true);
		}
		else if (s.equals("Search & Find Matches")) {
			new SearchFrame(currentUser, manager).setVisible(true);
		}
		else if (s.equals("My Listings")) {
			new MyListingsFrame(currentUser, manager).setVisible(true);
		}
		else if (s.equals("Logout")) {
			new AuthenticationFrame(auth, manager).setVisible(true);
			dispose();
		}
	}
}
