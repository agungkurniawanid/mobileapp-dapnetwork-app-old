package com.example.mobile_pegawai_dapnetwork_backup.model;

public class ModelCardPemesananPembatalan {
    public String mdl_text_pemesanan_instalasi, mdl_text_instalasi, mdl_deskripsi_pemesanan_pembatalan;
    public int mdl_image_pemesanan_pembatalan;

    public ModelCardPemesananPembatalan(String mdl_text_pemesanan_instalasi, String mdl_text_instalasi, String mdl_deskripsi_pemesanan_pembatalan, int mdl_image_pemesanan_pembatalan) {
        this.mdl_text_pemesanan_instalasi = mdl_text_pemesanan_instalasi;
        this.mdl_text_instalasi = mdl_text_instalasi;
        this.mdl_deskripsi_pemesanan_pembatalan = mdl_deskripsi_pemesanan_pembatalan;
        this.mdl_image_pemesanan_pembatalan = mdl_image_pemesanan_pembatalan;
    }

    public String getMdl_text_pemesanan_instalasi() {
        return mdl_text_pemesanan_instalasi;
    }

    public String getMdl_text_instalasi() {
        return mdl_text_instalasi;
    }

    public String getMdl_deskripsi_pemesanan_pembatalan() {
        return mdl_deskripsi_pemesanan_pembatalan;
    }

    public int getMdl_image_pemesanan_pembatalan() {
        return mdl_image_pemesanan_pembatalan;
    }
}
