package com.example.mobile_pegawai_dapnetwork_backup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mobile_pegawai_dapnetwork_backup.adapter.AdapterPeraturanPegawai;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelPeraturanPegawai;

import java.util.ArrayList;
import java.util.List;

public class PeraturanPegawai extends AppCompatActivity {

    RecyclerView cardPeraturan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peraturan_pegawai);

        // todo set peraturan pegawai
        cardPeraturan = findViewById(R.id.recycle_peraturan_pegawai);
        setPeraturan();
    }
    private void setPeraturan() {
        List<ModelPeraturanPegawai> modelPeraturanPegawais = new ArrayList<>();
        modelPeraturanPegawais.add(new ModelPeraturanPegawai(R.drawable.icon_number1v, "Pegawai harus menjaga kerahasiaan informasi pelanggan dan perusahaan serta tidak boleh mengungkapkan data pelanggan tanpa izin."));
        modelPeraturanPegawais.add(new ModelPeraturanPegawai(R.drawable.icon_number2, "Selalu prioritaskan kebutuhan pelanggan dan berikan pelayanan yang ramah dan efisien."));
        modelPeraturanPegawais.add(new ModelPeraturanPegawai(R.drawable.icon_number3, "Pegawai harus menjaga integritas perangkat dan jaringan, serta melaporkan segera jika terdapat masalah atau kebocoran keamanan."));
        modelPeraturanPegawais.add(new ModelPeraturanPegawai(R.drawable.icon_number4, "Patuhi prosedur keselamatan kerja dan gunakan peralatan pelindung diri yang sesuai saat bekerja."));
        modelPeraturanPegawais.add(new ModelPeraturanPegawai(R.drawable.icon_number5, "Lakukan pemeliharaan rutin dan perbaikan perangkat keras serta perangkat lunak secara berkala untuk memastikan kinerja yang optimal."));
        modelPeraturanPegawais.add(new ModelPeraturanPegawai(R.drawable.icon_number6, "Selalu perbarui pengetahuan dan keterampilan teknis agar selaras dengan perkembangan teknologi jaringan internet."));
        modelPeraturanPegawais.add(new ModelPeraturanPegawai(R.drawable.icon_number7, "Pegawai harus bekerja sama dalam tim dan berkomunikasi secara efektif untuk memecahkan masalah dengan cepat."));
        modelPeraturanPegawais.add(new ModelPeraturanPegawai(R.drawable.icon_number8, "Patuhi jadwal kerja dan target penyelesaian pekerjaan yang telah ditetapkan."));
        modelPeraturanPegawais.add(new ModelPeraturanPegawai(R.drawable.icon_number9, "Hindari konflik kepentingan dan jangan menerima suap atau hadiah yang dapat mempengaruhi keputusan profesional."));
        cardPeraturan.setLayoutManager(new LinearLayoutManager((this), RecyclerView.VERTICAL, false));

        AdapterPeraturanPegawai adapterPeraturanPegawai = new AdapterPeraturanPegawai(this, modelPeraturanPegawais);
        cardPeraturan.setAdapter(adapterPeraturanPegawai);
    }
}