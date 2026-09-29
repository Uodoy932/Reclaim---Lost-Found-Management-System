package  model;

import java.time.LocalDate;
import java.time.LocalTime;


public class Other extends Item {
    private String customNote;

    //Constructors
    public Other(String description, String location, LocalDate date, LocalTime time, ItemStatus status,
                 String uniqueMark, String ownerId, String ownerName, String ownerContact,String customNote) {
    	
        super(description, location, date, time, status, uniqueMark, ownerId, ownerName, ownerContact);
        this.customNote = customNote;
    }

    public Other(String itemId, String description, String location, LocalDate date, LocalTime time, ItemStatus status,
                 String uniqueMark, String ownerId, String ownerName, String ownerContact,
                 String customNote) {
        super(itemId, description, location, date, time, status, uniqueMark, ownerId, ownerName, ownerContact);
        this.customNote = customNote;
    }
    //-------------------------
    
    
    
    //GET SET
    public String getCustomNote() { return customNote; }
    @Override
    public String getCategory() { return "Other"; }
    @Override
    public String getCategoryDetails() { return "Item Details: " + customNote; }
    //--------------------------
    
    
    
    //Percentage
    @Override
    public double calculateMatchPercentage(Item other) {
        double score = 0;

        score += locationMatch(other) * 18;
        score += dateMatch(other) * 18;
        score += descriptionMatch(other) * 18;
        score += uniqueMarkMatch(other) * 18;
        score += categoryMatch(other) * 8;

        if (other instanceof Other) {
            Other o = (Other) other;
            score += txtMatch(customNote, o.customNote) * 20;
        }

        return Math.min(100, score);
    }
    //-----------------------------
    
    
    @Override
    public String toFileString() {
        return CommonAtrributesString() + "," + nullSafe(customNote);
    }
}
