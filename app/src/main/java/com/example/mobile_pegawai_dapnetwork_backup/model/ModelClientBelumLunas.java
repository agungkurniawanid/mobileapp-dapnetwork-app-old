package com.example.mobile_pegawai_dapnetwork_backup.model;

public class ModelClientBelumLunas {
    public int mdl_img_client_belumlunas;
    public String mdl_name_client_belumlunas;
    public String mdl_status_client_belumlunas;
    public int mdl_tagihan_client_belumlunas;

    public ModelClientBelumLunas(int mdl_img_client_belumlunas, String mdl_name_client_belumlunas, String mdl_status_client_belumlunas, int mdl_tagihan_client_belumlunas) {
        this.mdl_img_client_belumlunas = mdl_img_client_belumlunas;
        this.mdl_name_client_belumlunas = mdl_name_client_belumlunas;
        this.mdl_status_client_belumlunas = mdl_status_client_belumlunas;
        this.mdl_tagihan_client_belumlunas = mdl_tagihan_client_belumlunas;
    }

    public int getMdl_img_client_belumlunas() {
        return mdl_img_client_belumlunas;
    }

    public String getMdl_name_client_belumlunas() {
        return mdl_name_client_belumlunas;
    }

    public String getMdl_status_client_belumlunas() {
        return mdl_status_client_belumlunas;
    }

    public int getMdl_tagihan_client_belumlunas() {
        return mdl_tagihan_client_belumlunas;
    }
}
