package com.openclassrooms.realestatemanager.models.database;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity(foreignKeys ={
            @ForeignKey(entity = InterestPoint.class,
                parentColumns = "interest_point_id",
                childColumns = "property_interest_point_id"),
            @ForeignKey(entity = HouseSeller.class,
                parentColumns = "house_seller_id",
                childColumns = "property_house_seller_id")
            }
       )
public class Property {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "property_id")
    private long id;
    private int price;
    private int area;
    private int rooms;
    private String type;
    private String description;
    private String address;
    private boolean sold;
    private Calendar createDate;
    private Calendar sellDate;
    @ColumnInfo(name = "property_interest_point_id", index = true)
    private long interestPointId;
    @ColumnInfo(name = "property_house_seller_id", index = true)
    private long houseSellerId;

    // --- CONSTRUCTORS ---
    public Property() {}

    public Property(int price, int area, int rooms, String type, String description,
                    String address, boolean sold, Calendar sellStartingDate, Calendar sellDate,
                    long interestPointId, long houseSellerId) {
        this.price = price;
        this.area = area;
        this.rooms = rooms;
        this.type = type;
        this.description = description;
        this.address = address;
        this.sold = sold;
        this.createDate = sellStartingDate;
        this.sellDate = sellDate;
        this.interestPointId = interestPointId;
        this.houseSellerId = houseSellerId;
    }


    // --- GETTER ---
    public long getId() {return id;}
    public int getPrice() {return price;}
    public int getArea() {return area;}
    public int getRooms() {return rooms;}
    public String getType() {return type;}
    public String getDescription() {return description;}
    public String getAddress() {return address;}
    public boolean getSold() {return sold;}
    public Calendar getCreateDate() {return createDate;}
    public Calendar getSellDate() {return sellDate;}
    public long getInterestPointId() {return interestPointId;}
    public long getHouseSellerId() {return houseSellerId;}


    // --- SETTERS ---
    public void setId(long id) {this.id = id;}
    public void setPrice(int price) {this.price = price;}
    public void setArea(int area) {this.area = area;}
    public void setRooms(int rooms) {this.rooms = rooms;}
    public void setType(String type) {this.type = type;}
    public void setDescription(String description) {this.description = description;}
    public void setAddress(String address) {this.address = address;}
    public void setSold(boolean sold) {this.sold = sold;}
    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;}
    public void setSellDate(Calendar sellDate) {this.sellDate = sellDate;}
    public void setInterestPointId(long interestPointId) {this.interestPointId = interestPointId;}
    public void setHouseSellerId(long houseSellerId) {this.houseSellerId = houseSellerId;}
}
