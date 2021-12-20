package com.example.bengkelrywin.models;

import com.google.gson.annotations.SerializedName;

public class Service {
    @SerializedName("id")
    private Long id;
    @SerializedName("nama_pelanggan")
    private String nama;
    @SerializedName("jenis_service")
    private String jenis;
    @SerializedName("alamat_pelanggan")
    private String alamat;

    public Service(String nama, String jenis, String alamat) {
        this.nama = nama;
        this.jenis = jenis;
        this.alamat = alamat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
