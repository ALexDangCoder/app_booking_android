package com.example.fbooking.accept;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Accept {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("Roomid")
    @Expose
    private String roomid;
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
    private Integer cccd;
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
    private Integer sodem;
    @SerializedName("soNguoi")
    @Expose
    private Integer soNguoi;
    @SerializedName("gioNhanPhong")
    @Expose
    private String gioNhanPhong;
    @SerializedName("gioTra")
    @Expose
    private String gioTra;
    @SerializedName("giaPhong")
    @Expose
    private Integer giaPhong;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
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

    public Integer getCccd() {
        return cccd;
    }

    public void setCccd(Integer cccd) {
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

    public Integer getSodem() {
        return sodem;
    }

    public void setSodem(Integer sodem) {
        this.sodem = sodem;
    }

    public Integer getSoNguoi() {
        return soNguoi;
    }

    public void setSoNguoi(Integer soNguoi) {
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

    public Integer getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(Integer giaPhong) {
        this.giaPhong = giaPhong;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }
}
