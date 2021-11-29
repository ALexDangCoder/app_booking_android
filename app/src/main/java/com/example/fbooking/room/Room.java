package com.example.fbooking.room;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Room implements Serializable {
    @SerializedName("_id")
    @Expose
    private String id;
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
    private String peopleRoom;
    @SerializedName("priceRoom")
    @Expose
    private Integer priceRoom;
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
    private Boolean other;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public Room(String id, List<RoomPhoto> roomPhoto, String roomNumber, String typeRoom, String rankRoom, String peopleRoom, Integer priceRoom, String statusRoom, String description, Boolean wifi, Boolean parking, Boolean receptionist, Boolean gym, Boolean roomMeeting, Boolean laundry, Boolean pool, Boolean restaurant, Boolean elevator, Boolean wheelChairWay, Boolean shuttle, Boolean other, Integer v) {
        this.id = id;
        this.roomPhoto = roomPhoto;
        this.roomNumber = roomNumber;
        this.typeRoom = typeRoom;
        this.rankRoom = rankRoom;
        this.peopleRoom = peopleRoom;
        this.priceRoom = priceRoom;
        this.statusRoom = statusRoom;
        this.description = description;
        this.wifi = wifi;
        this.parking = parking;
        this.receptionist = receptionist;
        this.gym = gym;
        this.roomMeeting = roomMeeting;
        this.laundry = laundry;
        this.pool = pool;
        this.restaurant = restaurant;
        this.elevator = elevator;
        this.wheelChairWay = wheelChairWay;
        this.shuttle = shuttle;
        this.other = other;
        this.v = v;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPeopleRoom() {
        return peopleRoom;
    }

    public void setPeopleRoom(String peopleRoom) {
        this.peopleRoom = peopleRoom;
    }

    public Integer getPriceRoom() {
        return priceRoom;
    }

    public void setPriceRoom(Integer priceRoom) {
        this.priceRoom = priceRoom;
    }

    public String getStatusRoom() {
        return statusRoom;
    }

    public void setStatusRoom(String statusRoom) {
        this.statusRoom = statusRoom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Boolean getOther() {
        return other;
    }

    public void setOther(Boolean other) {
        this.other = other;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }
}
