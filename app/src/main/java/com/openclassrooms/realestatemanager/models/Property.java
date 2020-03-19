package com.openclassrooms.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys ={
            @ForeignKey(entity = InterestPoint.class,
                parentColumns = "id",
                childColumns = "interestPointId"),
            @ForeignKey(entity = HouseSeller.class,
                parentColumns = "id",
                childColumns = "houseSellerId")
            }
       )
public class Property {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private int price;
    private int area;
    private int rooms;
    private String type;
    private String description;
    private String address;
    private String Status;
    private String createDate;
    private String SellDate;
    private String interestPointId;
    private String houseSellerId;

    // --- CONSTRUCTORS ---
    public Property() {}
    public Property(int price, int area, int rooms, String type, String description,
                    String address, String status, String sellStartingDate, String sellDate,
                    String interestPointId, String houseSellerId) {
        this.price = price;
        this.area = area;
        this.rooms = rooms;
        this.type = type;
        this.description = description;
        this.address = address;
        Status = status;
        createDate = sellStartingDate;
        SellDate = sellDate;
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
    public String getStatus() {return Status;}
    public String getCreateDate() {return createDate;}
    public String getSellDate() {return SellDate;}
    public String getInterestPointId() {return interestPointId;}
    public String getHouseSellerId() {return houseSellerId;}


    // --- SETTERS ---
    public void setId(long id) {this.id = id;}
    public void setPrice(int price) {this.price = price;}
    public void setArea(int area) {this.area = area;}
    public void setRooms(int rooms) {this.rooms = rooms;}
    public void setType(String type) {this.type = type;}
    public void setDescription(String description) {this.description = description;}
    public void setAddress(String address) {this.address = address;}
    public void setStatus(String status) {Status = status;}
    public void setCreateDate(String createDate) {
        this.createDate = createDate;}
    public void setSellDate(String sellDate) {SellDate = sellDate;}
    public void setInterestPointId(String interestPointId) {this.interestPointId = interestPointId;}
    public void setHouseSellerId(String houseSellerId) {this.houseSellerId = houseSellerId;}
}
