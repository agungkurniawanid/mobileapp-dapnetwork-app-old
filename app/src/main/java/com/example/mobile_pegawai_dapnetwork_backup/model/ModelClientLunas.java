package com.example.mobile_pegawai_dapnetwork_backup.model;

public class ModelClientLunas {
    public int mdl_img_client_lunas;
    public String mdl_name_client_lunas;
    public String mdl_status_client_lunas;
    public int mdl_tagihan_client_lunas;

    public ModelClientLunas(int mdl_img_client_lunas, String mdl_name_client_lunas, String mdl_status_client_lunas, int mdl_tagihan_client_lunas) {
        this.mdl_img_client_lunas = mdl_img_client_lunas;
        this.mdl_name_client_lunas = mdl_name_client_lunas;
        this.mdl_status_client_lunas = mdl_status_client_lunas;
        this.mdl_tagihan_client_lunas = mdl_tagihan_client_lunas;
    }

    public int getMdl_img_client_lunas() {
        return mdl_img_client_lunas;
    }

    public String getMdl_name_client_lunas() {
        return mdl_name_client_lunas;
    }

    public String getMdl_status_client_lunas() {
        return mdl_status_client_lunas;
    }

    public int getMdl_tagihan_client_lunas() {
        return mdl_tagihan_client_lunas;
    }
}
