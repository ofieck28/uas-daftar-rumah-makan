package com.example.katalog_rumahmakan.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

public class RumahMakan {

    public static final String CAVACOFFEE="CAVA COFFE";
    public static final String BAKSOJOKOWI="BAKSO JOKOWI";
    public static final String WARUNGMASDOYOK="WARUNG MAS DOYOK";
    public static final String MOROSENENG="MOROSENENG";

    private String nama;
    private Date tanggal;
    private String alamat;
    private String kontak;
    private String jenisMenu;


    public RumahMakan() {
        this.nama = UUID.randomUUID().toString();
        this.tanggal = new Date();
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }

    public String getJenisMenu() {
        return jenisMenu;
    }

    public void setJenisMenu(String jenisMenu) {
        this.jenisMenu = jenisMenu;
    }


    public static RumahMakan fromJSONObject(JSONObject obj) {
        RumahMakan tr = new RumahMakan();
        try {
            tr.setNama(obj.getString("nama"));
            tr.setTanggal(new Date(obj.getLong("tanggal")));
            tr.setAlamat(obj.getString("alamat"));
            tr.setJenisMenu(obj.getString("jenis menu"));
            tr.setKontak(obj.getString("kontak"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tr;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("nama",this.nama);
            jsonObj.put("tanggal",this.tanggal.getTime());
            jsonObj.put("alamat",this.alamat);
            jsonObj.put("Jenis menu",this.jenisMenu);
            jsonObj.put("kontak",this.kontak);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }
}
