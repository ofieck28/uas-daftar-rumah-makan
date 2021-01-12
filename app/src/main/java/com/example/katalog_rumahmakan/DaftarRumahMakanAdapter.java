package com.example.katalog_rumahmakan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.katalog_rumahmakan.model.RumahMakan;

import java.util.List;

public class DaftarRumahMakanAdapter extends ArrayAdapter<RumahMakan> {
    Context context;

    public DaftarRumahMakanAdapter(@NonNull Context context, @NonNull List<RumahMakan> objects) {
        super(context, R.layout.row_rumahmakan, objects);
        this.context = context;
    }

    class ViewHolder {
        TextView txTgl;
        TextView txNama;
        TextView txAlamat;
        TextView txJenisMenu;
        TextView txKontak;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        RumahMakan tr = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_rumahmakan,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.txTgl = convertView.findViewById(R.id.row_tx_tgl_rumahmakan);
            viewHolder.txNama = convertView.findViewById(R.id.row_tx_nama);

            viewHolder.txAlamat = convertView.findViewById(R.id.row_tx_alamat);
            viewHolder.txJenisMenu = convertView.findViewById(R.id.row_tx_jenismenu);
            viewHolder.txKontak = convertView.findViewById(R.id.row_tx_kontak);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txTgl.setText(GenericUtility.DATE_FORMAT.format(tr.getTanggal()));
        viewHolder.txNama.setText(tr.getNama());

        viewHolder.txAlamat.setText(tr.getAlamat());
        viewHolder.txJenisMenu.setText(tr.getJenisMenu());
        viewHolder.txKontak.setText(tr.getKontak());
        return convertView;
    }
}