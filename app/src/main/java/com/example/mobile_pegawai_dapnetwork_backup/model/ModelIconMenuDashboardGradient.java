package com.example.mobile_pegawai_dapnetwork_backup.model;

public class ModelIconMenuDashboardGradient {
    public String text1_model, text2_model;
    public int backgroundIcon_model, imageIcon_model;

    public ModelIconMenuDashboardGradient(String text1_model, String text2_model, int backgroundIcon_model, int imageIcon_model) {
        this.text1_model = text1_model;
        this.text2_model = text2_model;
        this.backgroundIcon_model = backgroundIcon_model;
        this.imageIcon_model = imageIcon_model;
    }

    public String getText1_model() {
        return text1_model;
    }

    public void setText1_model(String text1_model) {
        this.text1_model = text1_model;
    }

    public String getText2_model() {
        return text2_model;
    }

    public void setText2_model(String text2_model) {
        this.text2_model = text2_model;
    }

    public int getBackgroundIcon_model() {
        return backgroundIcon_model;
    }

    public void setBackgroundIcon_model(int backgroundIcon_model) {
        this.backgroundIcon_model = backgroundIcon_model;
    }

    public int getImageIcon_model() {
        return imageIcon_model;
    }

    public void setImageIcon_model(int imageIcon_model) {
        this.imageIcon_model = imageIcon_model;
    }
}

