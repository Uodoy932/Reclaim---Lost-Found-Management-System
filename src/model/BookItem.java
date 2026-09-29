package  model;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookItem extends Item {
    private String title;
    private String isbn;
    private String author;

    //Constructors
    public BookItem(String description, String location, LocalDate date, LocalTime time, ItemStatus status,
                    String uniqueMark, String ownerId, String ownerName, String ownerContact,String title,
                    String isbn, String author) {
    	
        super(description, location, date, time, status, uniqueMark, ownerId, ownerName, ownerContact);
        this.title = title;
        this.isbn = isbn;
        this.author = author;
    }

    public BookItem(String itemId, String description, String location, LocalDate date, LocalTime time, ItemStatus status,
                    String uniqueMark, String ownerId, String ownerName, String ownerContact,String title, 
                    String isbn, String author) {
    	
        super(itemId, description, location, date, time, status, uniqueMark, ownerId, ownerName, ownerContact);
        this.title = title;
        this.isbn = isbn;
        this.author = author;
    }
    //---------------------
    
    
    
    //GET SET
    public String getTitle() { return title; }
    public String getIsbn() { return isbn; }
    public String getAuthor() { return author; }
    @Override
    public String getCategory() { return "Book"; }
    @Override
    public String getCategoryDetails() {return "Title: " + title + "\nISBN: " + isbn + "\nAuthor: " + author;}
    //---------------------
    
    
    
    //Calculate Percentage
    @Override
    public double calculateMatchPercentage(Item other) {
        double score = 0;

        score += locationMatch(other) * 10;
        score += dateMatch(other) * 10;
        score += descriptionMatch(other) * 10;
        score += uniqueMarkMatch(other) * 10;
        score += categoryMatch(other) * 10;

        if (other instanceof BookItem) 
        {
            BookItem b = (BookItem) other;
            if (title != null && title.equalsIgnoreCase(b.title)) 						 {score += 15;}
            if (isbn != null && !isbn.trim().isEmpty() && isbn.equalsIgnoreCase(b.isbn)) {score += 25;}
            if (author != null && author.equalsIgnoreCase(b.author)) 					 {score += 10;}
        }

        return Math.min(100, score);
    }
    //---------------------
    
    
    @Override
    public String toFileString() {
        return CommonAtrributesString() + "," + nullSafe(title) + "," + nullSafe(isbn) + "," + nullSafe(author)+"\n";
    }
}
