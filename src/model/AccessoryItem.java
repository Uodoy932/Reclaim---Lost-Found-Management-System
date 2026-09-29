package  model;

import java.time.LocalDate;
import java.time.LocalTime;

public class AccessoryItem extends Item {
	
    private String material;
    private String color;

    //Constructor
    public AccessoryItem(String description, String location, LocalDate date, LocalTime time, ItemStatus status,
                         String uniqueMark, String ownerId, String ownerName, String ownerContact,String material,
                         String color) {
    	
        super(description, location, date, time, status, uniqueMark, ownerId, ownerName, ownerContact);
        this.material = material;
        this.color = color;
    }

    public AccessoryItem(String itemId, String description, String location, LocalDate date, LocalTime time, ItemStatus status,
                         String uniqueMark, String ownerId, String ownerName, String ownerContact,
                         String material, String color) {
    	
        super(itemId, description, location, date, time, status, uniqueMark, ownerId, ownerName, ownerContact);
        this.material = material;
        this.color = color;
    }
    //------------------------------
    
    
    //SET GET
    public String getMaterial() { return material; }
    public String getColor() { return color; }
    @Override
    public String getCategory() { return "Accessory"; }
    @Override
    public String getCategoryDetails() {
        return "Material: " + material + "\nColor: " + color;
    }
    //------------------------------
    
    
    
    //Calculate Percentage
    @Override
    public double calculateMatchPercentage(Item other) {
    	
        double score = 0;

        score += locationMatch(other) * 15;
        score += dateMatch(other) * 15;
        score += descriptionMatch(other) * 15;
        score += uniqueMarkMatch(other) * 15;
        score += categoryMatch(other) * 10;

        if (other instanceof AccessoryItem) 
        {
            AccessoryItem a = (AccessoryItem) other;
            if (material != null && material.equalsIgnoreCase(a.material)) {score += 15;}
            if (color != null && color.equalsIgnoreCase(a.color)) 		   {score += 15;}
        }

        return Math.min(100, score);
    }
    //-------------------------
    
    
    @Override
    public String toFileString() {
        return CommonAtrributesString() + "," + nullSafe(material) + "," + nullSafe(color)+"\n";
    }
}
