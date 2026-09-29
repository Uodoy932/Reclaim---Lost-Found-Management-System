package storage;

import model.Item;

public class MatchResult {

    private Item item;
    private double matchPercentage;


    // Constructor
    public MatchResult(Item item, double matchPercentage) 
    {
        this.item = item;
        this.matchPercentage = matchPercentage;
    }
    
    public Item getItem() { return item;}
    public double getMatchPercentage() {return matchPercentage;}
}