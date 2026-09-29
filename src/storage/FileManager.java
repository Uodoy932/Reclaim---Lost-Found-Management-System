package storage;

import model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {


	//Create USer and Item Files
	public FileManager() {

		try {

			File users = new File("users.txt");
			if (!users.exists()) {
				FileWriter writer = new FileWriter("users.txt");
				writer.close();
			}

			
			
			File items = new File("items.txt");
			if (!items.exists()) {
				FileWriter writer = new FileWriter("items.txt");
				writer.close();
			}

		}

		catch (IOException e) {
			System.out.println("An error occurred.");
		}
	}

	
	///USERFILE READ WRITE
	
	// Read User file
	public ArrayList<User> loadUsers() {

		ArrayList<User> users = new ArrayList<User>();//ArrayList

		try(Scanner myReader = new Scanner(new File("users.txt"))) 
		{	
			//Scanner myReader = new Scanner(new File("users.txt"))
			while (myReader.hasNextLine()) {

				String data = myReader.nextLine();

				if (!data.trim().isEmpty()) {
					User user = User.fromFileString(data);
					users.add(user);
				}
			}

			myReader.close();
		}

		catch (IOException e) {
			System.out.println("An error occurred.");
		}

		return users;
	}

	
	//Write
	public void saveUsers(ArrayList<User> users) {

		try(FileWriter myWriter = new FileWriter("users.txt")) {

			//FileWriter myWriter = new FileWriter("users.txt");

			for (User user : users) {
				myWriter.write(user.toFileString());
				myWriter.write("\n");
			}

			myWriter.close();
		}

		catch (IOException e) {
			System.out.println("An error occurred.");
		}
	}

	
	
	// Add User
	public void appendUser(User user) {

		try (FileWriter myWriter = new FileWriter("users.txt", true)){

			//FileWriter myWriter = new FileWriter("users.txt", true);

			myWriter.write(user.toFileString());
			myWriter.write("\n");

			myWriter.close();
		}

		catch (IOException e) {
			System.out.println("An error occurred.");
		}
	}

	

	
	
	
	///ITEMFILE READ WRITE
	
	
	
	// Read Item
	public ArrayList<Item> loadItems() {

		ArrayList<Item> items = new ArrayList<Item>();//ArrayList

		try (Scanner myReader = new Scanner(new File("Items.txt")))
		{

			/*File myObj = new File("Items.txt");
			Scanner myReader = new Scanner(new File("Items.txt"));*/

			while (myReader.hasNextLine()) {

				String data = myReader.nextLine();

				if (!data.trim().isEmpty()) {

					Item item = createItemFromFile(data);

					items.add(item);
					
				}
			}

			myReader.close();
		}

		catch (IOException e) {
			
			System.out.println("An error occurred.");
		}

		return items;
	}

	
	
	
	// Save Item
	public void saveItems(ArrayList<Item> items) {

		try(FileWriter myWriter = new FileWriter("Items.txt")) {


			for (Item item : items) {
				myWriter.write(item.toFileString());
				myWriter.write("\n");
			}

			myWriter.close();
		}

		catch (IOException e) {
			System.out.println("An error occurred.");
		}
	}

	
	
	
	// Add item
	public void appendItem(Item item) {

		try(FileWriter myWriter = new FileWriter("Items.txt", true)) {

			//FileWriter myWriter = new FileWriter("Items.txt", true);

			myWriter.write(item.toFileString());
			myWriter.write("\n");

			myWriter.close();
		}

		catch (IOException e) {
			System.out.println("An error occurred.");
		}
	}

	
	
	// CREATE ITEM FROM FILE LINE
	

	private Item createItemFromFile(String line) {

		try {

			String[] data = line.split("\\,");

			String category = data[0];
			String itemId = data[1];
			String description = data[2];
			String location = data[3];
			LocalDate date = LocalDate.parse(data[4]);
			LocalTime time = LocalTime.parse(data[5]);
			ItemStatus status = ItemStatus.valueOf(data[6]);
			String uniqueMark = data[7];
			String ownerId = data[8];
			String ownerName = data[9];
			String ownerContact = data[10];

			if (category.equals("Document")) {
				return new DocumentItem(itemId, description, location, date, time, status, uniqueMark, ownerId,
						ownerName, ownerContact, data[11], data[12]);
			}

			else if (category.equals("Electronic")) {
				return new ElectronicItem(itemId, description, location, date, time, status, uniqueMark, ownerId,
						ownerName, ownerContact, data[11], data[12]);
			}

			else if (category.equals("Accessory")) {
				return new AccessoryItem(itemId, description, location, date, time, status, uniqueMark, ownerId,
						ownerName, ownerContact, data[11], data[12]);
			}

			else if (category.equals("Book")) {
				return new BookItem(itemId, description, location, date, time, status, uniqueMark, ownerId, ownerName,
						ownerContact, data[11], data[12], data[13]);
			}

			else if (category.equals("Other")) {
				return new Other(itemId, description, location, date, time, status, uniqueMark, ownerId, ownerName,
						ownerContact, data[11]);
			}

		}

		catch (Exception e) {
			System.out.println("Could not read item from file.");
		}

		return null;
	}
}