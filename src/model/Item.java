package model;

import interfaces.Match;
import exceptions.InvalidStatusTransitionException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public abstract class Item implements Match {

	private String itemId;
	private String description;
	private String location;
	private LocalDate date;
	private LocalTime time;
	private ItemStatus status;
	private String uniqueMark;

	private String ownerId;
	private String ownerName;
	private String ownerContact;

	private static int counter = 1;

	// Constructors
	public Item(String description, String location, LocalDate date, LocalTime time, ItemStatus status,
			String uniqueMark, String ownerId, String ownerName, String ownerContact) {
		this.itemId = generateId();
		this.description = description;
		this.location = location;
		this.date = date;
		this.time = time;
		this.status = status;
		this.uniqueMark = uniqueMark;
		this.ownerId = ownerId;
		this.ownerName = ownerName;
		this.ownerContact = ownerContact;
	}

	public Item(String itemId, String description, String location, LocalDate date, LocalTime time, ItemStatus status,
			String uniqueMark, String ownerId, String ownerName, String ownerContact) {
		this.itemId = itemId;
		this.description = description;
		this.location = location;
		this.date = date;
		this.time = time;
		this.status = status;
		this.uniqueMark = uniqueMark;
		this.ownerId = ownerId;
		this.ownerName = ownerName;
		this.ownerContact = ownerContact;
		HighestIdNumCheck(itemId);
	}
	//------------------------------
	
	
	
	//ITEM ID Generate
	
	private static synchronized String generateId() {
		String id = "ITM" + String.format("%04d", counter);
		counter++;
		return id;
	}

	private static synchronized void HighestIdNumCheck(String itemId) {
		
		try {
			int num = Integer.parseInt(itemId.replaceAll("[^0-9]", ""));
			if (num >= counter)
				counter = num + 1;
			} 
		
		catch (NumberFormatException e) {}
		
	}
	
	//------------------------------
	
	
	
	//Match interface Methods
	@Override
	public double calculateMatchPercentage(Item other) {
		double score = 0;

		score += locationMatch(other) * 20;
		score += dateMatch(other) * 20;
		score += descriptionMatch(other) * 20;
		score += uniqueMarkMatch(other) * 20;
		score += categoryMatch(other) * 20;

		return Math.min(100, score);
	}

	@Override
	public double calculateMatchPercentage(Item other, double locationWeight, double dateWeight,
			double descriptionWeight) {
		
		double score = 0;
		score += locationMatch(other) * locationWeight * 100;
		score += dateMatch(other) * dateWeight * 100;
		score += descriptionMatch(other) * descriptionWeight * 100;
		
		return Math.min(100, score);
	}

	
	@Override
	public double calculateMatchPercentage(Item other, double categoryWeight,double descriptionWeight) {

	    double score = 0;
	    score += categoryMatch(other) * categoryWeight * 100;
	    score += descriptionMatch(other) * descriptionWeight * 100;

	    return Math.min(100, score);
	}
	
	@Override
	public double calculateMatchPercentage(Item other, boolean includeUniqueMark) {

	    double score = 0;
	    score += locationMatch(other) * 25;
	    score += dateMatch(other) * 25;
	    score += descriptionMatch(other) * 25;
	    
	    if (includeUniqueMark) {
	        score += uniqueMarkMatch(other) * 25;
	    }

	    return Math.min(100, score);
	}
	
	//------------------------------
	
	
	
	//Useful Functions-->
	
	//For Two String Comparing word by word
	double txtMatch(String a, String b) {
		
		int common = 0;
		
		if (a == null || b == null) return 0.0;
			
		String[] wordsA = a.toLowerCase().split("\\s+");
		String[] wordsB = b.toLowerCase().split("\\s+");
		if (wordsA.length == 0) return 0.0;
			
		
		for (String wa : wordsA) {
			for (String wb : wordsB) {
				if (wa.equals(wb)) {
					common++;
					break;
				}
			}
		}
		
		return Math.min(1.0, (common / (double) wordsA.length));
	}

	//For Location
	double locationMatch(Item other) {
		if (this.location != null && other.location != null && this.location.equalsIgnoreCase(other.location.trim())) {
			return 1.0;
		}
		return 0.0;
	}

	//For Date
	double dateMatch(Item other) {
		
		if (this.date == null || other.date == null)  return 0.0;
			
		long d = Math.abs(ChronoUnit.DAYS.between(this.date, other.date));
		
		if (d <= 2) return 1.0;
			
		if (d <= 5) return 0.75;
			
		if (d <= 10) return 0.5;
			
		if (d <= 15) return 0.25;
			
		return 0.0;
	}

	//For Description
	double descriptionMatch(Item other) {
		return txtMatch(this.description, other.description);
	}

	//For Unique Mark
	double uniqueMarkMatch(Item other) {
		return txtMatch(this.uniqueMark, other.uniqueMark);
	}
	
	//For Category
	double categoryMatch(Item other) {
		if (this.getCategory() != null && other.getCategory() != null && this.getCategory().equalsIgnoreCase(other.getCategory())) {
			return 1.0;
		}
		
		return 0.0;
	}

	//------------------------------
	
	
	
	
	// GET SET
	public String getItemId() {return itemId;}
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	public String getLocation() {return location;}
	public void setLocation(String location) {this.location = location;}
	public LocalDate getDate() {return date;}
	public void setDate(LocalDate date) {this.date = date;}
	public LocalTime getTime() {return time;}
	public void setTime(LocalTime time) {this.time = time;}
	public ItemStatus getStatus() {return status;}
	public String getUniqueMark() {return uniqueMark;}
	public void setUniqueMark(String uniqueMark) {this.uniqueMark = uniqueMark;}
	public String getOwnerId() {return ownerId;}
	public String getOwnerName() {return ownerName;}
	public String getOwnerContact() {return ownerContact;}
	
	//Setter and Exception
	public void setStatus(ItemStatus newStatus) throws InvalidStatusTransitionException {
		
		if (this.status == ItemStatus.RETURNED) {
			
			throw new InvalidStatusTransitionException("Item " + itemId + " is already RETURNED and cannot change status again.");
		}
		
		if (newStatus == ItemStatus.RETURNED && this.status != ItemStatus.LOST && this.status != ItemStatus.FOUND) {
			
			throw new InvalidStatusTransitionException("Item " + itemId + " cannot be marked RETURNED from its current state.");
		}
		
		this.status = newStatus;
	}

	//------------------------------
	
	

	
	// Necessary Methods 
	
		public abstract String getCategory();
		public abstract String getCategoryDetails();
		public abstract String toFileString();
		
		protected static String nullSafe(String s) {
		    if (s == null) {return "";}
		    return s;
			}
		
		protected String CommonAtrributesString() {
		    return getCategory() + "," +itemId + "," +description + "," +location + "," +date.toString() + "," +time.toString() + "," +
		           status.name() + "," +nullSafe(uniqueMark) + "," +ownerId + "," +ownerName + "," + ownerContact;
		}

		public String getDisplaySummary() {
			return "[" + getCategory() + "] " + description + " - " + location + " (" + date + " " + time + ") - Status: "+ status;
		}

		@Override
		public String toString() {
			return itemId + " - " + getDisplaySummary();
		}
		
		//------------------------------
}
