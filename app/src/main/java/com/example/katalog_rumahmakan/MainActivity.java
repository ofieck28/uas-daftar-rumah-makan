package com.example.katalog_rumahmakan;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.katalog_rumahmakan.model.RumahMakan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton btnTambahRumahMakan;
    ImageButton btnChangeUserName;
    ListView lvDaftarRumahMakan;
    TextView txNoData, txUsername;
    DaftarRumahMakanAdapter adapter;
    List<RumahMakan> daftarRumahMakan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inisialisasiView();
        loadDaftarRumahMakan();
        setupListview();
    }

    private void inisialisasiView() {
        btnTambahRumahMakan = findViewById(R.id.btn_add_RumahMakan);
        btnTambahRumahMakan.setOnClickListener(view -> bukaFormTambahRumahMakan());
        btnChangeUserName = findViewById(R.id.btn_change_username);
        btnChangeUserName.setOnClickListener(view -> changeUserName());
        lvDaftarRumahMakan = findViewById(R.id.lv_RumahMakan);
        txNoData = findViewById(R.id.tx_nodata);
        txUsername = findViewById(R.id.tx_user_name);
        txUsername.setText(SharedPreferenceUtility.getUserName(this));

    }

    private void setupListview() {
        adapter = new DaftarRumahMakanAdapter(this, daftarRumahMakan);
        lvDaftarRumahMakan.setAdapter(adapter);
    }

    private void loadDaftarRumahMakan() {
        daftarRumahMakan = SharedPreferenceUtility.getAllRumahMakan(this);
    }
    private void refreshListView() {
        adapter.clear();
        loadDaftarRumahMakan();
        adapter.addAll(daftarRumahMakan);
    }

    private void bukaFormTambahRumahMakan() {
        Intent intent = new Intent(this, formRumahMakanActivity.class);
        startActivity(intent);
    }

    private void changeUserName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ganti nama");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferenceUtility.saveUserName(getApplicationContext(),input.getText().toString());
                Toast.makeText(getApplicationContext(),"Nama user berhasil disimpan",Toast.LENGTH_SHORT).show();
                txUsername.setText(SharedPreferenceUtility.getUserName(getApplicationContext()));
            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshListView();
    }
}