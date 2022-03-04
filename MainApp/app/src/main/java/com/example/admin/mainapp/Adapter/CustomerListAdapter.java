package com.example.admin.mainapp.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.mainapp.Model.CustomerModelClass;
import com.example.admin.mainapp.R;

import java.util.List;

/**
 * Created by Admin on 19-01-2018.
 */

public class CustomerListAdapter extends ArrayAdapter<CustomerModelClass> {

    private Activity context;
    private List<CustomerModelClass> list;

    public CustomerListAdapter(Activity context,List<CustomerModelClass> list)
    {
        super(context, R.layout.custom_layout_customer,list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.custom_layout_customer,null,true);
        TextView txtNameCustom = (TextView)listViewItem.findViewById(R.id.txtNameCustom);
        TextView txtEmailCustom = (TextView)listViewItem.findViewById(R.id.txtEmailCustom);
        TextView txtPhoneCustom = (TextView)listViewItem.findViewById(R.id.txtPhoneCustom);
        TextView txtAddressCustom = (TextView)listViewItem.findViewById(R.id.txtAddressCustom);
        ImageView imgCustom = (ImageView) listViewItem.findViewById(R.id.imgCustom);

        CustomerModelClass obj = list.get(position);

        txtNameCustom.setText(obj.getName());
        txtEmailCustom.setText(obj.getEmail());
        txtPhoneCustom.setText(obj.getPhone());
        txtAddressCustom.setText(obj.getAddress());

        return listViewItem;
    }
}
