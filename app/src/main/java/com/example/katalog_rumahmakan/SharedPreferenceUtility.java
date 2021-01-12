package com.example.katalog_rumahmakan;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.katalog_rumahmakan.model.RumahMakan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SharedPreferenceUtility {

    private static final String PREF_FILE = "simple.example.katalog_rumahmakan.DATA";
    // PREF_FILE -> Nama file utk penyimpanan,
    // biasanya menyertakan app.id agar tidak bentrok dgn app lain
    private static final String FAVOR_KEY = "TRANS"; // KEY utk daftar transaksi
    private static final String USER_NAME_KEY = "USERNAME"; // KEY untuk nama user

    private static SharedPreferences getSharedPref(Context ctx) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(
                PREF_FILE, Context.MODE_PRIVATE);
        return sharedPref;
    }
    /*
        Ambil data username dari Shared Preference
     */
    public static String getUserName(Context ctx) {
        return getSharedPref(ctx).getString(USER_NAME_KEY,"NO NAME");
    }
    /*
        Simpan data username ke Shared Preference
     */
    public static void saveUserName(Context ctx, String userName) {
        Log.d("SH PREF","Change user name to"+userName);
        getSharedPref(ctx).edit().putString(USER_NAME_KEY,userName).apply();
    }
    /*
        Ambil data transaksi dari Shared Preference
        Perhatikan bahwa data disimpan dalam format JSON String
     */
    public static List<RumahMakan> getAllRumahMakan(Context ctx) {
        String jsonString = getSharedPref(ctx).getString(FAVOR_KEY, null);
        List<RumahMakan> trs = new ArrayList<RumahMakan>();
        if (jsonString != null) {
            Log.d("SH PREF","json string is:"+jsonString);
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i=0;i<jsonArray.length();i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    trs.add(RumahMakan.fromJSONObject(obj));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(trs,(rumahMakan, t1) -> {
            return rumahMakan.getTanggal().compareTo(t1.getTanggal());
        }); // urutkan transaksi berdasarkan tanggal
        return trs;
    }
    /*
        Simpan data transaksi ke Shared Preference
        Perhatikan bahwa data disimpan dalam format JSON String
     */
    private static void saveAllRumahMakan(Context ctx, List<RumahMakan> trs) {
        List<JSONObject> jsonTrs = new ArrayList<JSONObject>();
        JSONArray jsonArr = new JSONArray();
        for (RumahMakan tr : trs) {
            jsonArr.put(tr.toJSONObject());
        }
        getSharedPref(ctx).edit().putString(FAVOR_KEY,jsonArr.toString()).apply();
    }

    /*
        Tambah data transaksi baru ke Shared Preference
     */
    public static void addRumahMakan(Context ctx, RumahMakan tr) {
        List<RumahMakan> trs = getAllRumahMakan(ctx);
        trs.add(tr);
        saveAllRumahMakan(ctx,trs);
    }

    public static void editRumahMakan(Context ctx, RumahMakan tr) {
        List<RumahMakan> trs = getAllRumahMakan(ctx);
        for (RumahMakan tre:trs) {
            if (tr.getNama().equals(tre.getNama())) {
                trs.remove(tre);
                trs.add(tr);
            }
        }
        saveAllRumahMakan(ctx,trs);
    }

    public static void deleteRumahMakan(Context ctx, String nama) {
        List<RumahMakan> trs = getAllRumahMakan(ctx);
        for (RumahMakan tr:trs) {
            if (tr.getNama().equals(nama)) {
                trs.remove(tr);
            }
        }
        saveAllRumahMakan(ctx,trs);
    }

}