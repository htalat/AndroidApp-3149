package com.buffapps.hassan.app3149;

/**
 * Created by hassan on 4/21/16.
 */

public class Item {

    private String mID;
    private String mName;
    private double mPrice;
    private int mQuantity;
    private boolean mIsBulk;

    public Item(String n , int q)
    {
        mName = n;
        mQuantity = Integer.valueOf(q);
    }

    public Item(String n, String p, String q, Boolean i)
    {
        mName = n;
        mPrice = Integer.valueOf(p);
        mQuantity = Integer.valueOf(q);
        mIsBulk = i;
    }

    public Item()
    {

    }




    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

    public boolean isBulk() {
        return mIsBulk;
    }

    public void setIsBulk(boolean isBulk) {
        mIsBulk = isBulk;
    }

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        mID = ID;
    }
}