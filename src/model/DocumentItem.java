package  model;

import java.time.LocalDate;
import java.time.LocalTime;

public class DocumentItem extends Item {
    private String documentType; // e.g. "National ID", "Certificate"
    private String docNum;

    //Constructors
    public DocumentItem(String description, String location, LocalDate date, LocalTime time, ItemStatus status,
                        String uniqueMark, String ownerId, String ownerName, String ownerContact,String documentType,
                        String docNum) {
    	
        super(description, location, date, time, status, uniqueMark, ownerId, ownerName, ownerContact);
        this.documentType = documentType;
        this.docNum = docNum;
    }

    public DocumentItem(String itemId, String description, String location, LocalDate date, LocalTime time, ItemStatus status,
                         String uniqueMark, String ownerId, String ownerName, String ownerContact,
                         String documentType, String docNum) {
        super(itemId, description, location, date, time, status, uniqueMark, ownerId, ownerName, ownerContact);
        this.documentType = documentType;
        this.docNum = docNum;
    }
    //-----------------------
    
    
    
    
    //GET SET
    public String getDocumentType() { return documentType; }
    public String getdocNum() { return docNum; }
    @Override
    public String getCategory() { return "Document"; }
    @Override
    public String getCategoryDetails() {return "Document Type: " + documentType + "\nDocument Number: " + docNum;}
    //------------------
    
    
 
    //Calculate Percentage
    @Override
    public double calculateMatchPercentage(Item other) {
        double score = 0;

        score += locationMatch(other) * 15;
        score += dateMatch(other) * 15;
        score += descriptionMatch(other) * 15;
        score += uniqueMarkMatch(other) * 15;
        score += categoryMatch(other) * 10;

        if (other instanceof DocumentItem) 
        {
            DocumentItem d = (DocumentItem) other;
            if (documentType != null && documentType.equalsIgnoreCase(d.documentType)) {score += 15;}
            if (docNum != null && d.docNum != null && docNum.equalsIgnoreCase(d.docNum)) {score += 15;}
        }
        
        return Math.min(100, score);
    }
    //-------------------------
    
    
    @Override
    public String toFileString() {
        return CommonAtrributesString() + "," + nullSafe(documentType) + "," + nullSafe(docNum)+"\n";
    }
}
