package com.example.mobile_pegawai_dapnetwork_backup.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_pegawai_dapnetwork_backup.MainApplication;
import com.example.mobile_pegawai_dapnetwork_backup.R;
import com.example.mobile_pegawai_dapnetwork_backup.adapter.AdapterBtnSemuaClient;
import com.example.mobile_pegawai_dapnetwork_backup.adapter.AdapterClientUmum;
import com.example.mobile_pegawai_dapnetwork_backup.adapter.AdapterPemesanan;
import com.example.mobile_pegawai_dapnetwork_backup.classes.CONFIG_IP;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelBtnSemuaClient;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelClientUmum;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelPemesanan;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Pemesanan extends Fragment {

    RecyclerView recyclePemesanan, recycleFilter;
    List<ModelPemesanan> listData = new ArrayList<>();
    String URL_GET_PEMESANAN = CONFIG_IP.HTTP+CONFIG_IP.URL_IP+CONFIG_IP.PATH+"getpemesanan.php";
    String url = URL_GET_PEMESANAN;
    String URL_UPDATE_PEMESANAN =  CONFIG_IP.HTTP+CONFIG_IP.URL_IP+CONFIG_IP.PATH+"updatepemesanan.php";
    String formattedDate;
    RelativeLayout dataRec;
    ImageView notFound;
    Toast toast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pemesanan, container, false);

        recycleFilter = view.findViewById(R.id.recycle_filter);
        recyclePemesanan = view.findViewById(R.id.recycle_pemesanan);
        notFound = view.findViewById(R.id.not_found);
        dataRec = view.findViewById(R.id.data_rec);

        // recycel fungsi filter
        List<ModelBtnSemuaClient> btnSemuaClients = new ArrayList<>();
        btnSemuaClients.add(new ModelBtnSemuaClient(R.drawable.calendar, "Semua"));
        btnSemuaClients.add(new ModelBtnSemuaClient(R.drawable.calendar, "Hari ini"));
        btnSemuaClients.add(new ModelBtnSemuaClient(R.drawable.calendar, "Besok"));

        // Tambahkan tanggal dalam bahasa Indonesia untuk item recycler yang kosong
        Calendar today = Calendar.getInstance();
        for (int i = 3; i <= 7; i++) {
            Calendar futureDay = (Calendar) today.clone();
            futureDay.add(Calendar.DAY_OF_MONTH, i);

            // Format teks untuk menampilkan tanggal dalam bahasa Indonesia
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMMM", new Locale("id", "ID"));
            String buttonText = sdf.format(futureDay.getTime());

            // Tambahkan tombol ke RecyclerView
            btnSemuaClients.add(new ModelBtnSemuaClient(R.drawable.calendar, buttonText));
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recycleFilter.setLayoutManager(linearLayoutManager);
        AdapterBtnSemuaClient adapter = new AdapterBtnSemuaClient(getContext(), btnSemuaClients, new AdapterBtnSemuaClient.ItemClickListener() {
            @Override
            public void onItemClick(ModelBtnSemuaClient modelBtnSemuaClient) {
                // Mendeteksi apakah item yang diklik adalah "Semua"
                if ("Semua".equals(modelBtnSemuaClient.getMdl_text_btn_semua_client())) {
                    getRecyclePemesanan(url);
                } else if ("Hari ini".equals(modelBtnSemuaClient.getMdl_text_btn_semua_client())) {
                    formattedDate = getCurrentDate();
                    getRecyclePemesanan(url + "?tanggal=" + formattedDate);
                } else if ("Besok".equals(modelBtnSemuaClient.getMdl_text_btn_semua_client())) {
                    formattedDate = getTomorrowDate();
                    getRecyclePemesanan(url + "?tanggal=" + formattedDate);
                } else {
                    // Jika item yang diklik bukan "Semua", "Hari Ini", atau "Besok"
                    int index = btnSemuaClients.indexOf(modelBtnSemuaClient);

                    if (index != -1) {
                        Calendar selectedDay = (Calendar) today.clone();
                        selectedDay.add(Calendar.DAY_OF_MONTH, index);

                        // Format tanggal dalam format "yyyy-MM-dd"
                        SimpleDateFormat sdfFormatted = new SimpleDateFormat("yyyy-MM-dd", new Locale("id", "ID"));
                        formattedDate = sdfFormatted.format(selectedDay.getTime());

                        // Format tanggal untuk ditampilkan dalam toast
                        SimpleDateFormat sdfDisplay = new SimpleDateFormat("EEEE dd MMMM", new Locale("id", "ID"));
                        String displayDate = sdfDisplay.format(selectedDay.getTime());

                        // Panggil metode untuk menampilkan data sesuai tanggal yang dipilih
                        getRecyclePemesanan(url + "?tanggal=" + formattedDate);
                    }
                }
            }
        });
        recycleFilter.setAdapter(adapter);


        getRecyclePemesanan(url);
        return view;
    }

    // mendapatkan tanggal sekarang
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(Calendar.getInstance().getTime());
    }

    // mendapatkan tanggal 1 hari kedepan
    private String getTomorrowDate() {
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(tomorrow.getTime());
    }

    private void getRecyclePemesanan(String url) {
        listData.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    showDataMessage();// Tambahkan log untuk melihat respons
                    Log.d("TagihanActivity", "Response: " + response);

                    // Periksa apakah respons dimulai dengan "<br"
                    if (response.startsWith("<br")) {
                        // Tangani pesan kesalahan di sini
                        Log.e("TagihanActivity", "Server error: " + response);
                        // Tampilkan pesan kesalahan atau set listData menjadi kosong
                        listData.clear();
                        showEmptyDataMessage();
                    } else {
                        // Membersihkan listData sebelum menambahkan data baru
                        listData.clear();
                        showDataMessage();

                        // Proses respons JSON
                        JSONObject jsonObject = new JSONObject(response.substring(response.indexOf('{'), response.length()));
                        JSONArray data = jsonObject.getJSONArray("data");

                        Log.d("TagihanActivity", "Number of items in response: " + data.length());

                        if (data.length() == 0) {
                            // Tampilkan pesan "Tidak ada data"
                            showEmptyDataMessage();
                        } else {
                            for (int i = 0; i < data.length(); i++) {
                                ModelPemesanan pemesanan = new ModelPemesanan();
                                JSONObject object = data.getJSONObject(i);
                                pemesanan.setMdl_id_pembayaran(object.getString("id_pembayaran"));
                                pemesanan.setMdl_id_pemesanan(object.getString("id_pemesanan"));
                                pemesanan.setMdl_id_client(object.getString("id_client"));
                                pemesanan.setMdl_id_paket_layanan(object.getString("id_paket_layanan"));
                                pemesanan.setMdl_nama_client(object.getString("nama_client"));
                                pemesanan.setMdl_tanggal_pesan_instalasi(object.getString("tanggal_pesan_instalasi"));
                                pemesanan.setMdl_status_pemesanan_instalasi(object.getString("status_pemesanan_instalasi"));
                                pemesanan.setMdl_tanggal_instalasi(object.getString("tanggal_instalasi"));
                                pemesanan.setMdl_catatan(object.getString("catatan"));
                                pemesanan.setMdl_jenis_paket_layanan(object.getString("jenis_paket_layanan"));
                                pemesanan.setMdl_harga(object.getInt("harga"));
                                pemesanan.setMdl_status_pembayaran(object.getString("status_pembayaran"));
                                listData.add(pemesanan);
                            }

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
                            recyclePemesanan.setLayoutManager(linearLayoutManager);

                            // Mengirimkan listener ke adapter
                            AdapterPemesanan adapterPemesanan = new AdapterPemesanan(requireContext(), listData);
                            recyclePemesanan.setAdapter(adapterPemesanan);

                            // mengatur on cllick listener dari adapter
                            adapterPemesanan.setOnSelesaiInstalasiClickListener(new AdapterPemesanan.OnSelesaiInstalasiClickListener() {
                                @Override
                                public void onSelesaiInstalasiClick(int position) {
                                    // Membuat konfirmasi AlertDialog
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setTitle("Konfirmasi");
                                    builder.setMessage("Apakah Anda yakin ingin menyelesaikan instalasi?");

                                    // Tombol Oke
                                    builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Aksi jika tombol Oke diklik
                                            RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity());
                                            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URL_UPDATE_PEMESANAN, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        JSONObject object = new JSONObject(response.substring(response.indexOf('{'), response.length()));
                                                        int status = object.getInt("status");
                                                        String message = object.getString("message");
                                                        if (status == 200) {
                                                            if(message.equals("Pemesanan Di Update")) {
                                                                ToastCustomSuccess(message);
                                                                // reload
                                                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                                                ft.replace(R.id.assets_frame_layout, new Pemesanan()); // Ganti R.id.container dengan ID dari kontainer fragment di MainApplication
                                                                ft.commit();
                                                            }
                                                        }
                                                    } catch (Exception error) {
                                                        error.printStackTrace();
                                                    }
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    // Handle error response
                                                    error.printStackTrace();
                                                }
                                            }) {
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    Map<String, String> params = new HashMap<>();
                                                    params.put("id_pemesanan", listData.get(position).getMdl_id_pemesanan());
                                                    return params;
                                                }
                                            };
                                            requestQueue1.add(stringRequest1);
                                        }
                                    });

                                    // Tombol Batal
                                    builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Aksi jika tombol Batal diklik
                                            dialog.dismiss();
                                        }
                                    });
                                    // Menampilkan AlertDialog
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }
                            });


                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // Tambahkan log untuk melihat kesalahan
                    Log.e("TagihanActivity", "Error: " + e.toString());
                    // Tampilkan pesan kesalahan atau set listData menjadi kosong
                    listData.clear();
                    showEmptyDataMessage();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
    }
    private void showEmptyDataMessage(){
        recyclePemesanan.setVisibility(View.GONE);
    }

    private void showDataMessage(){
        recyclePemesanan.setVisibility(View.VISIBLE);
    }

    private void ToastCustomWarning(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, (ViewGroup) getView(), false);

        ImageView toastIcon = layout.findViewById(R.id.toast_icon);
        TextView toastText = layout.findViewById(R.id.toast_text);

        toastText.setText(message);

        toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.setGravity(Gravity.TOP, 0, 100);
        toast.show();
    }

    private void ToastCustomSuccess(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_success, (ViewGroup) getView(), false);

        ImageView toastIcon = layout.findViewById(R.id.toas_icon_suc);
        TextView toastText = layout.findViewById(R.id.toast_text_success);

        toastText.setText(message);

        toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.setGravity(Gravity.TOP, 0, 100);
        toast.show();
    }
}