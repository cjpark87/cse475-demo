package com.example.ble_keyboard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.clj.fastble.data.BleDevice;

import java.util.ArrayList;

public class BleDeviceAdapter extends ArrayAdapter<BleDevice> {
    public BleDeviceAdapter(Context context, @LayoutRes int resource) {
        super(context, resource, 0, new ArrayList<>());
    }

    @androidx.annotation.NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @androidx.annotation.NonNull ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setText(getItem(position).getName() + ", " + getItem(position).getMac());
        return view;
    }
}
