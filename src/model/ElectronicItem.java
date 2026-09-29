package  model;

import java.time.LocalDate;
import java.time.LocalTime;

public class ElectronicItem extends Item {
    private String brand;
    private String serialNumber;

    //Constructors
    public ElectronicItem(String description, String location, LocalDate date, LocalTime time, ItemStatus status,
                          String uniqueMark, String ownerId, String ownerName, String ownerContact,
                          String brand, String serialNumber) {
        super(description, location, date, time, status, uniqueMark, ownerId, ownerName, ownerContact);
        this.brand = brand;
        this.serialNumber = serialNumber;
    }

    public ElectronicItem(String itemId, String description, String location, LocalDate date, LocalTime time, ItemStatus status,
                           String uniqueMark, String ownerId, String ownerName, String ownerContact,
                           String brand, String serialNumber) {
        super(itemId, description, location, date, time, status, uniqueMark, ownerId, ownerName, ownerContact);
        this.brand = brand;
        this.serialNumber = serialNumber;
    }
    //------------------------------
    
    
    
    //GET SET
    public String getBrand() { return brand; }
    public String getSerialNumber() { return serialNumber; }
    @Override
    public String getCategory() { return "Electronic"; }
    @Override
    public String getCategoryDetails() {return "Brand: " + brand + "\nProduct Serial: " + serialNumber;}
    //--------------------
    
    
    
    //Calculate Percentage
    @Override
    public double calculateMatchPercentage(Item other) {
        double score = 0;

        score += locationMatch(other) * 15;
        score += dateMatch(other) * 15;
        score += descriptionMatch(other) * 15;
        score += uniqueMarkMatch(other) * 10;
        score += categoryMatch(other) * 10;

        if (other instanceof ElectronicItem) {
            ElectronicItem e = (ElectronicItem) other;
            if (brand != null && brand.equalsIgnoreCase(e.brand)) 														{score += 15;}
            if (serialNumber != null && !serialNumber.trim().isEmpty()&& serialNumber.equalsIgnoreCase(e.serialNumber)) {score += 20;}
        }

        return Math.min(100, score);
    }
    //---------------------
    
    
    
    @Override
    public String toFileString() {
        return CommonAtrributesString() + "," + nullSafe(brand) + "," + nullSafe(serialNumber);
    }
}
