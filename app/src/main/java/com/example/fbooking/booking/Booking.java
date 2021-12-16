package com.example.fbooking.booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Booking implements Serializable {
    @SerializedName("Roomid")
    @Expose
    private String Roomid;
    @SerializedName("sophong")
    @Expose
    private String sophong;
    @SerializedName("hoten")
    @Expose
    private String hoten;
    @SerializedName("sdt")
    @Expose
    private String sdt;
    @SerializedName("loaiPhong")
    @Expose
    private String loaiPhong;
    @SerializedName("hangPhong")
    @Expose
    private String hangPhong;
    @SerializedName("cccd")
    @Expose
    private int cccd;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("ngaynhan")
    @Expose
    private String ngaynhan;
    @SerializedName("ngayTra")
    @Expose
    private String ngayTra;
    @SerializedName("sodem")
    @Expose
    private int sodem;
    @SerializedName("soNguoi")
    @Expose
    private int soNguoi;
    @SerializedName("gioNhanPhong")
    @Expose
    private String gioNhanPhong;
    @SerializedName("gioTra")
    @Expose
    private String gioTra;
    @SerializedName("giaPhong")
    @Expose
    private double giaPhong;

    public Booking(String Roomid, String sophong, String hoten, String sdt, String loaiPhong, String hangPhong, int cccd, String email, String ngaynhan, String ngayTra, int sodem, int soNguoi, String gioNhanPhong, String gioTra, double giaPhong) {
        this.Roomid = Roomid;
        this.sophong = sophong;
        this.hoten = hoten;
        this.sdt = sdt;
        this.loaiPhong = loaiPhong;
        this.hangPhong = hangPhong;
        this.cccd = cccd;
        this.email = email;
        this.ngaynhan = ngaynhan;
        this.ngayTra = ngayTra;
        this.sodem = sodem;
        this.soNguoi = soNguoi;
        this.gioNhanPhong = gioNhanPhong;
        this.gioTra = gioTra;
        this.giaPhong = giaPhong;
    }

    public String getRoomid() {
        return Roomid;
    }

    public void setRoomid(String roomid) {
        Roomid = roomid;
    }

    public String getSophong() {
        return sophong;
    }

    public void setSophong(String sophong) {
        this.sophong = sophong;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public String getHangPhong() {
        return hangPhong;
    }

    public void setHangPhong(String hangPhong) {
        this.hangPhong = hangPhong;
    }

    public int getCccd() {
        return cccd;
    }

    public void setCccd(int cccd) {
        this.cccd = cccd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNgaynhan() {
        return ngaynhan;
    }

    public void setNgaynhan(String ngaynhan) {
        this.ngaynhan = ngaynhan;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = ngayTra;
    }

    public int getSodem() {
        return sodem;
    }

    public void setSodem(int sodem) {
        this.sodem = sodem;
    }

    public int getSoNguoi() {
        return soNguoi;
    }

    public void setSoNguoi(int soNguoi) {
        this.soNguoi = soNguoi;
    }

    public String getGioNhanPhong() {
        return gioNhanPhong;
    }

    public void setGioNhanPhong(String gioNhanPhong) {
        this.gioNhanPhong = gioNhanPhong;
    }

    public String getGioTra() {
        return gioTra;
    }

    public void setGioTra(String gioTra) {
        this.gioTra = gioTra;
    }

    public double getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(double giaPhong) {
        this.giaPhong = giaPhong;
    }
}
