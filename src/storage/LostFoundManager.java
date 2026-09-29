package storage;
import exceptions.InvalidStatusTransitionException;

import model.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LostFoundManager {

    private ArrayList<Item> items;
    private FileManager fileManager;

    // Constructor
    public LostFoundManager(FileManager fileManager) 
    {
        this.fileManager = fileManager;
        this.items = fileManager.loadItems();
    }



    //ADDITEM Code
    public void addItem(Item item) {

        items.add(item);//Array list
        fileManager.saveItems(items);//Sends ArrayList to File manager saveItems method
    }


   
    //DELETEITEM Code
    public boolean deleteItem(String itemId, String requesterId) {

        for (Item item : items) 
        {

	            if (item.getItemId().equals(itemId)&& item.getOwnerId().equals(requesterId)) 
	            {
	
	                items.remove(item);
	                fileManager.saveItems(items);
	                return true;
	            }
        }

        return false;
    }


    
    // UPDATESTATUS Code

    public void updateStatus(String itemId, ItemStatus newStatus)throws InvalidStatusTransitionException {

        for (Item item : items) 
        {

            if (item.getItemId().equals(itemId)) 
            {

                item.setStatus(newStatus);
                fileManager.saveItems(items);
                return;
            }
        }

        
        throw new InvalidStatusTransitionException("Item was not found.");
    }


    
    // My Listings Loading 

    public ArrayList<Item> getItemsByOwner(String ownerId) 
    {

        ArrayList<Item> myItems = new ArrayList<>();

        for (Item item : items) 
        {

            if (item.getOwnerId().equals(ownerId)) 
            {
                myItems.add(item);
            }
        }

        return myItems;
    }


    
    // SEARCH Code
    public ArrayList<Item> search(String category,ItemStatus status,String location) {

        ArrayList<Item> results = new ArrayList<>();

        for (Item item : items) {

            boolean categoryMatch = true;
            boolean statusMatch = true;
            boolean locationMatch = true;


            //Category
            if (category != null&&!category.equalsIgnoreCase("All"))
            {
                if (!item.getCategory().equalsIgnoreCase(category)) {categoryMatch = false;}
            }


            //Status
            if (status != null) 
            {
                if (item.getStatus() != status) {statusMatch = false;}
            }


            //Location
            if (location != null&& !location.trim().isEmpty())
            {
                if (!item.getLocation().toLowerCase().contains(location.toLowerCase())) {locationMatch = false;}
            }

            
            if (categoryMatch&& statusMatch&& locationMatch)
            {
                results.add(item);
            }
        }

        return results;
    }


    
    // FINDMATCHES
    public ArrayList<MatchResult> findMatches(Item searchItem) 
    {

        ArrayList<MatchResult> results = new ArrayList<>();//Array List for Match Results


        // Find OpStatus
        ItemStatus oppositeStatus;
        if (searchItem.getStatus() == ItemStatus.LOST) {oppositeStatus = ItemStatus.FOUND;}
        else										   {oppositeStatus = ItemStatus.LOST;}


        // Matching Category and OPStatus
        ArrayList<Item> candidates = search(searchItem.getCategory(),oppositeStatus,searchItem.getLocation());


        //Percentage
        for (Item item : candidates) 
        {
        	double score =searchItem.calculateMatchPercentage(item);
            MatchResult result = new MatchResult(item, score);
            results.add(result);//Storing Results in Array list
        }


        // Result form HIGH TO LOW
        for (int i = 0; i < results.size(); i++) {

            for (int j = i + 1; j < results.size(); j++) {

                if (results.get(j).getMatchPercentage() > results.get(i).getMatchPercentage()) 
                {

                    MatchResult temp = results.get(i);
                    results.set(i, results.get(j));
                    results.set(j, temp);
                    
                }
            }
        }

        return results;
    }



    // Create ITEM 

    public Item createItem(String category, String description, String location, LocalDate date, LocalTime time,
            ItemStatus status, String uniqueMark, String ownerId, String ownerName, String ownerContact,
            String[] extraFields) {

        if (category.equals("Document")) {

            return new DocumentItem(description, location, date, time, status, uniqueMark, ownerId, ownerName,
                    ownerContact, extraFields[0], extraFields[1]);
        }

        else if (category.equals("Electronic")) {

            return new ElectronicItem(description, location, date, time, status, uniqueMark, ownerId, ownerName,
                    ownerContact, extraFields[0], extraFields[1]);
        }

        else if (category.equals("Accessory")) {

            return new AccessoryItem(description, location, date, time, status, uniqueMark, ownerId, ownerName,
                    ownerContact, extraFields[0], extraFields[1]);
        }

        else if (category.equals("Book")) {

            return new BookItem(description, location, date, time, status, uniqueMark, ownerId, ownerName,
                    ownerContact, extraFields[0], extraFields[1], extraFields[2]);
        }

        else {

            return new Other(description, location, date, time, status, uniqueMark, ownerId, ownerName,
                    ownerContact, extraFields[0]);
        }
    }
    //------------------------------
    
    //Date And Time
    public LocalDate parseDate(String text) {
        return LocalDate.parse(text);
    }

    public LocalDate parseDate(String text, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(text, formatter);
    }

    public LocalTime parseTime(String text) {
        return LocalTime.parse(text);
    }

    public LocalTime parseTime(String text, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalTime.parse(text, formatter);
    }

    //--------------------------------
}