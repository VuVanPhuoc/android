package com.example.appdoctruyen.adapter;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.model.TaiKhoan;

import java.util.List;

public class adapterthongtin extends BaseAdapter {

    private Context context;
    private int layout;
    private List<TaiKhoan> taiKhoanList;

    public adapterthongtin(Context context, int layout, List<TaiKhoan> taiKhoanList) {
        this.context = context;
       this.layout = layout;
        this.taiKhoanList = taiKhoanList;
    }

    @Override
    public int getCount() {
        return taiKhoanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(layout,null);

        TextView txtTenTaiKhoan=(TextView) convertView.findViewById(R.id.TEXT_NAME);
        TextView txtEmail=(TextView) convertView.findViewById(R.id.TEXT_Gmail);
        TaiKhoan taiKhoan=taiKhoanList.get(position);

        txtTenTaiKhoan.setText(taiKhoan.getmTenTaiKhoan());
        txtEmail.setText(taiKhoan.getmEmail());
        return convertView;
    }
}
