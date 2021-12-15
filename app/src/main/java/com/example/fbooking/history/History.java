package com.example.fbooking.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("maPhong")
    @Expose
    private String maPhong;
    @SerializedName("hoten")
    @Expose
    private String hoten;
    @SerializedName("loaiPhong")
    @Expose
    private String loaiPhong;
    @SerializedName("cmnd")
    @Expose
    private String cmnd;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("giaPhong")
    @Expose
    private Integer giaPhong;
    @SerializedName("ngayNhan")
    @Expose
    private String ngayNhan;
    @SerializedName("ngayTra")
    @Expose
    private String ngayTra;
    @SerializedName("soDem")
    @Expose
    private Integer soDem;
    @SerializedName("soNguoi")
    @Expose
    private Integer soNguoi;
    @SerializedName("gioNhanPhong")
    @Expose
    private String gioNhanPhong;
    @SerializedName("gioTraPhong")
    @Expose
    private String gioTraPhong;
    @SerializedName("sdt")
    @Expose
    private Integer sdt;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("soPhong")
    @Expose
    private String soPhong;

    public History(String id, String maPhong, String hoten, String loaiPhong, String cmnd, String email, Integer giaPhong, String ngayNhan, String ngayTra, Integer soDem, Integer soNguoi, String gioNhanPhong, String gioTraPhong, Integer sdt, Integer v, String soPhong) {
        this.id = id;
        this.maPhong = maPhong;
        this.hoten = hoten;
        this.loaiPhong = loaiPhong;
        this.cmnd = cmnd;
        this.email = email;
        this.giaPhong = giaPhong;
        this.ngayNhan = ngayNhan;
        this.ngayTra = ngayTra;
        this.soDem = soDem;
        this.soNguoi = soNguoi;
        this.gioNhanPhong = gioNhanPhong;
        this.gioTraPhong = gioTraPhong;
        this.sdt = sdt;
        this.v = v;
        this.soPhong = soPhong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(Integer giaPhong) {
        this.giaPhong = giaPhong;
    }

    public String getNgayNhan() {
        return ngayNhan;
    }

    public void setNgayNhan(String ngayNhan) {
        this.ngayNhan = ngayNhan;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = ngayTra;
    }

    public Integer getSoDem() {
        return soDem;
    }

    public void setSoDem(Integer soDem) {
        this.soDem = soDem;
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

    public String getGioTraPhong() {
        return gioTraPhong;
    }

    public void setGioTraPhong(String gioTraPhong) {
        this.gioTraPhong = gioTraPhong;
    }

    public Integer getSdt() {
        return sdt;
    }

    public void setSdt(Integer sdt) {
        this.sdt = sdt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(String soPhong) {
        this.soPhong = soPhong;
    }
}
