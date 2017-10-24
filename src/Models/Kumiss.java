package Models;

import java.io.Serializable;

public class Kumiss implements Serializable {
    private int id;
    private double price;
    private String name;
    private String imageUrl;
    private String description;

    public int getId(){
        return id;
    }

    public double getPrice(){
        return price;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public void setId(int x){
        this.id = x;
    }

    public void setPrice(double x) {
        this.price = x;
    }

    public void setName(String x){
        this.name = x;
    }

    public void setImageUrl(String x){
        this.imageUrl = x;
    }

    public void setDescription(String x){
        this.description = x;
    }
}
