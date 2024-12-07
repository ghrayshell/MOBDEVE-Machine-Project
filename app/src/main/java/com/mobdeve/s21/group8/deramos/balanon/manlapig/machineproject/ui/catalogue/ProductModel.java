package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.catalogue;

public class ProductModel {
    int price;
    String name, fabric, colors, image; //hardcoded for now
    boolean isBookmarked, isAdded;

    // No-argument constructor (required for Firestore deserialization)
    public ProductModel() {
        // Firestore requires a no-argument constructor for deserialization
    }

    public ProductModel(String image, int pricetag, int bookmarkBtn, int addBtn, String name, String fabric, String colors, int price) {
        this.image = image;
        this.name = name;
        this.fabric = fabric;
        this.colors = colors;
        this.price = price;
        this.isBookmarked = false;
        this.isAdded = false;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getFabric() {
        return fabric;
    }

    public String getColors() {
        return colors;
    }

    public int getPrice() {
        return price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean isAdded) {
        this.isAdded = isAdded;
    }
}