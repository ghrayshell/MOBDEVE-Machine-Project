package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui;

public class ProductModel {
    int image, pricetag, bookmarkBtn, addBtn;
    String name, fabric, colors, price; //hardcoded for now
    boolean isBookmarked, isAdded;

    public ProductModel(int image, int pricetag, int bookmarkBtn, int addBtn, String name, String fabric, String colors, String price) {
        this.image = image;
        this.pricetag = pricetag;
        this.bookmarkBtn = bookmarkBtn;
        this.addBtn = addBtn;
        this.name = name;
        this.fabric = fabric;
        this.colors = colors;
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public int getPricetag() {
        return pricetag;
    }

    public int getBookmarkBtn() {
        return bookmarkBtn;
    }

    public int getAddBtn() {
        return addBtn;
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

    public String getPrice() {
        return price;
    }

    public boolean getAdded() { return isAdded; }

    public boolean getBookmarked() {return isBookmarked; }

    public void setImage(int image) {
        this.image = image;
    }

    public void setPricetag(int pricetag) {
        this.pricetag = pricetag;
    }

    public void setBookmarkBtn(int bookmarkBtn) {
        this.bookmarkBtn = bookmarkBtn;
    }

    public void setAddBtn(int addBtn) {
        this.addBtn = addBtn;
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

    public void setPrice(String price) {
        this.price = price;
    }

    public void setAdded(boolean isAdded) { this.isAdded = isAdded; }

    public void setBookmarked(boolean isBookmarked) { this.isBookmarked = isBookmarked; }

}