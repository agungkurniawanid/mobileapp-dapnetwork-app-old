package com.example.mobile_pegawai_dapnetwork_backup.model;

public class ModelCardInformation {
    public String card_judul_model, card_deskripsi_model;
    public int card_image_model, card_jumlah_plg_model;

    public ModelCardInformation(String card_judul_model, String card_deskripsi_model, int card_image_model, int card_jumlah_plg_model) {
        this.card_judul_model = card_judul_model;
        this.card_deskripsi_model = card_deskripsi_model;
        this.card_image_model = card_image_model;
        this.card_jumlah_plg_model = card_jumlah_plg_model;
    }

    public String getCard_judul_model() {
        return card_judul_model;
    }

    public void setCard_judul_model(String card_judul_model) {
        this.card_judul_model = card_judul_model;
    }

    public String getCard_deskripsi_model() {
        return card_deskripsi_model;
    }

    public void setCard_deskripsi_model(String card_deskripsi_model) {
        this.card_deskripsi_model = card_deskripsi_model;
    }

    public int getCard_image_model() {
        return card_image_model;
    }

    public void setCard_image_model(int card_image_model) {
        this.card_image_model = card_image_model;
    }

    public int getCard_jumlah_plg_model() {
        return card_jumlah_plg_model;
    }

    public void setCard_jumlah_plg_model(int card_jumlah_plg_model) {
        this.card_jumlah_plg_model = card_jumlah_plg_model;
    }
}
