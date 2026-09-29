package  interfaces;

import  model.Item;

public interface Match {

    
    double calculateMatchPercentage(Item other);
    double calculateMatchPercentage(Item other, double locationWeight, double dateWeight, double descriptionWeight);
    double calculateMatchPercentage(Item other,double categoryWeight,double descriptionWeight);
    double calculateMatchPercentage(Item other, boolean includeUniqueMark);
}
