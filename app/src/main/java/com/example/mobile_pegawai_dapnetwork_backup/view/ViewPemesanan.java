package com.example.mobile_pegawai_dapnetwork_backup.view;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobile_pegawai_dapnetwork_backup.R;

import org.w3c.dom.Text;

public class ViewPemesanan extends RecyclerView.ViewHolder {
    public TextView
            vw_nama_client,
            vw_tanggal_pesan_instalasi,
            vw_status_pemesanan_instalasi,
            vw_tanggal_instalasi,
            vw_catatan,
            vw_jenis_paket_layanan,
            vw_status_pembayaran,
            vw_harga,
            vw_btn_selesai_instalasi;

    public EditText vw_id_pembayaran, vw_id_pemesanan, vw_id_client, vw_id_paket_layanan;
    public ViewPemesanan(@NonNull View itemView) {
        super(itemView);
        vw_nama_client = itemView.findViewById(R.id.nama_client_pm);
        vw_tanggal_pesan_instalasi = itemView.findViewById(R.id.tanggal_pesan_pm);
        vw_status_pemesanan_instalasi = itemView.findViewById(R.id.status_pemesannan_pm);
        vw_tanggal_instalasi = itemView.findViewById(R.id.tanggal_instalasi_pm);
        vw_catatan = itemView.findViewById(R.id.catatan_pm);
        vw_jenis_paket_layanan = itemView.findViewById(R.id.paket_pm);
        vw_status_pembayaran = itemView.findViewById(R.id.status_pembayaran_pm);
        vw_harga = itemView.findViewById(R.id.harga_pm);
        vw_btn_selesai_instalasi = itemView.findViewById(R.id.btn_selesai_instalasi);
        vw_id_pembayaran = itemView.findViewById(R.id.id_pembayaran_pm);
        vw_id_pemesanan = itemView.findViewById(R.id.id_pemesanan_pm);
        vw_id_client = itemView.findViewById(R.id.id_client_pm);
        vw_id_paket_layanan = itemView.findViewById(R.id.id_paket_layanan_pm);
    }
}
