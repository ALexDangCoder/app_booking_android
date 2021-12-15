package com.example.fbooking.room;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Room implements Serializable {
    @SerializedName("_id")
    @Expose
    private String roomId;
    @SerializedName("roomPhoto")
    @Expose
    private List<RoomPhoto> roomPhoto = null;
    @SerializedName("roomNumber")
    @Expose
    private String roomNumber;
    @SerializedName("typeRoom")
    @Expose
    private String typeRoom;
    @SerializedName("rankRoom")
    @Expose
    private String rankRoom;
    @SerializedName("peopleRoom")
    @Expose
    private Integer peopleRoom;
    @SerializedName("priceRoom")
    @Expose
    private double priceRoom;
    @SerializedName("statusRoom")
    @Expose
    private String statusRoom;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("wifi")
    @Expose
    private Boolean wifi;
    @SerializedName("parking")
    @Expose
    private Boolean parking;
    @SerializedName("receptionist")
    @Expose
    private Boolean receptionist;
    @SerializedName("gym")
    @Expose
    private Boolean gym;
    @SerializedName("roomMeeting")
    @Expose
    private Boolean roomMeeting;
    @SerializedName("laundry")
    @Expose
    private Boolean laundry;
    @SerializedName("pool")
    @Expose
    private Boolean pool;
    @SerializedName("restaurant")
    @Expose
    private Boolean restaurant;
    @SerializedName("elevator")
    @Expose
    private Boolean elevator;
    @SerializedName("wheelChairWay")
    @Expose
    private Boolean wheelChairWay;
    @SerializedName("shuttle")
    @Expose
    private Boolean shuttle;
    @SerializedName("other")
    @Expose
    private String other;
    @SerializedName("otherText")
    @Expose
    private String otherText;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("favorite")
    @Expose
    private List<String> favorite = null;

    private boolean isChecked;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public List<RoomPhoto> getRoomPhoto() {
        return roomPhoto;
    }

    public void setRoomPhoto(List<RoomPhoto> roomPhoto) {
        this.roomPhoto = roomPhoto;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getTypeRoom() {
        return typeRoom;
    }

    public void setTypeRoom(String typeRoom) {
        this.typeRoom = typeRoom;
    }

    public String getRankRoom() {
        return rankRoom;
    }

    public void setRankRoom(String rankRoom) {
        this.rankRoom = rankRoom;
    }

    public Integer getPeopleRoom() {
        return peopleRoom;
    }

    public void setPeopleRoom(Integer peopleRoom) {
        this.peopleRoom = peopleRoom;
    }

    public double getPriceRoom() {
        return priceRoom;
    }

    public void setPriceRoom(double priceRoom) {
        this.priceRoom = priceRoom;
    }

    public String getStatusRoom() {
        return statusRoom;
    }

    public void setStatusRoom(String statusRoom) {
        this.statusRoom = statusRoom;
    }

    public Boolean getWifi() {
        return wifi;
    }

    public void setWifi(Boolean wifi) {
        this.wifi = wifi;
    }

    public Boolean getParking() {
        return parking;
    }

    public void setParking(Boolean parking) {
        this.parking = parking;
    }

    public Boolean getReceptionist() {
        return receptionist;
    }

    public void setReceptionist(Boolean receptionist) {
        this.receptionist = receptionist;
    }

    public Boolean getGym() {
        return gym;
    }

    public void setGym(Boolean gym) {
        this.gym = gym;
    }

    public Boolean getRoomMeeting() {
        return roomMeeting;
    }

    public void setRoomMeeting(Boolean roomMeeting) {
        this.roomMeeting = roomMeeting;
    }

    public Boolean getLaundry() {
        return laundry;
    }

    public void setLaundry(Boolean laundry) {
        this.laundry = laundry;
    }

    public Boolean getPool() {
        return pool;
    }

    public void setPool(Boolean pool) {
        this.pool = pool;
    }

    public Boolean getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Boolean restaurant) {
        this.restaurant = restaurant;
    }

    public Boolean getElevator() {
        return elevator;
    }

    public void setElevator(Boolean elevator) {
        this.elevator = elevator;
    }

    public Boolean getWheelChairWay() {
        return wheelChairWay;
    }

    public void setWheelChairWay(Boolean wheelChairWay) {
        this.wheelChairWay = wheelChairWay;
    }

    public Boolean getShuttle() {
        return shuttle;
    }

    public void setShuttle(Boolean shuttle) {
        this.shuttle = shuttle;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getOtherText() {
        return otherText;
    }

    public void setOtherText(String otherText) {
        this.otherText = otherText;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getFavorite() {
        return favorite;
    }

    public void setFavorite(List<String> favorite) {
        this.favorite = favorite;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public static Comparator<Room> roomComparatorMinToMaxPrice = new Comparator<Room>() {
        @Override
        public int compare(Room o1, Room o2) {
            return Double.valueOf(o1.getPriceRoom()).compareTo(o2.priceRoom);
        }
    };

    public static Comparator<Room> roomComparatorMaxToMinPrice = new Comparator<Room>() {
        @Override
        public int compare(Room o1, Room o2) {
            return Double.valueOf(o2.getPriceRoom()).compareTo(o1.priceRoom);
        }
    };
}
