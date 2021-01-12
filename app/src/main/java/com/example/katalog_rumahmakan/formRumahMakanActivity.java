package com.example.katalog_rumahmakan;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.katalog_rumahmakan.model.RumahMakan;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;

public class formRumahMakanActivity extends AppCompatActivity {

    Button btnSimpan;
    TextInputLayout tilKontak,tilNama,tilAlamat;
    EditText edtTgl;
    Spinner spJenisMenu;
    Date tanggalRumahMakan;
    final String[] tipeRumahMakan = {RumahMakan.CAVACOFFEE,RumahMakan.BAKSOJOKOWI,RumahMakan.WARUNGMASDOYOK,RumahMakan.MOROSENENG};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_rumahmakan);
        inisialisasiView();
    }

    private void inisialisasiView() {
        btnSimpan = findViewById(R.id.btn_simpan);
        btnSimpan.setOnClickListener(view -> simpan());
        edtTgl = findViewById(R.id.edt_tgl);
        edtTgl.setOnClickListener(view -> pickDate());
        tilKontak = findViewById(R.id.til_kontak);
        tilNama= findViewById(R.id.til_nama);
        tilAlamat= findViewById(R.id.til_alamat);
        spJenisMenu = findViewById(R.id.spn_jenis);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                tipeRumahMakan
        );
        spJenisMenu.setAdapter(adapter);
        spJenisMenu.setSelection(0);
    }

    private void simpan() {
        if (isDataValid()) {
            RumahMakan tr = new RumahMakan();

            tr.setNama(tilNama.getEditText().getText().toString());
            tr.setJenisMenu(spJenisMenu.getSelectedItem().toString());
            tr.setAlamat(tilAlamat.getEditText().getText().toString());
            tr.setKontak(tilKontak.getEditText().getText().toString());
            tr.setTanggal(tanggalRumahMakan);
            SharedPreferenceUtility.addRumahMakan(this,tr);
            Toast.makeText(this,"Data berhasil disimpan",Toast.LENGTH_SHORT).show();

            // Kembali ke layar sebelumnya setelah 500 ms (0.5 detik)
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 500);


        }
    }

    private boolean isDataValid() {
        if (tilKontak.getEditText().getText().toString().isEmpty()
                || tilNama.getEditText().getText().toString().isEmpty()
                || tilAlamat.getEditText().getText().toString().isEmpty()

        ) {
            Toast.makeText(this,"Lengkapi semua isian",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    /*
        Dipanggil saat user ingin mengisi tanggal transaksi
        Menampilkan date picker dalam popup dialog
     */
    private void pickDate() {
        final Calendar c = Calendar.getInstance();
        int thn = c.get(Calendar.YEAR);
        int bln = c.get(Calendar.MONTH);
        int tgl = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePickerDialog.OnDateSetListener) (view, yyyy, mm, dd) -> {
                    edtTgl.setText(dd + "-" + (mm + 1) + "-" + yyyy);
                    c.set(yyyy,mm,dd);
                    tanggalRumahMakan = c.getTime();
                },
                thn, bln, tgl);
        datePickerDialog.show();
    }

}