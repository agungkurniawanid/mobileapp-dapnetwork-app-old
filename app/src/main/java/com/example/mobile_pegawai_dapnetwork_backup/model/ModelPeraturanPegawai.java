package com.example.mobile_pegawai_dapnetwork_backup.model;

public class ModelPeraturanPegawai {
    public int mdl_img_number_peraturan;
    public String mdl_text_peraturan_pegawai;

    public ModelPeraturanPegawai(int mdl_img_number_peraturan, String mdl_text_peraturan_pegawai) {
        this.mdl_img_number_peraturan = mdl_img_number_peraturan;
        this.mdl_text_peraturan_pegawai = mdl_text_peraturan_pegawai;
    }

    public int getMdl_img_number_peraturan() {
        return mdl_img_number_peraturan;
    }

    public String getMdl_text_peraturan_pegawai() {
        return mdl_text_peraturan_pegawai;
    }
}
