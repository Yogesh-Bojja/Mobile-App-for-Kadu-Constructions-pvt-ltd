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

import com.example.admin.mainapp.Model.TransactionModelClass;
import com.example.admin.mainapp.R;

import java.util.List;

/**
 * Created by Admin on 23-01-2018.
 */

public class NotificationAdapter extends ArrayAdapter<TransactionModelClass> {

    private Activity context;
    private List<TransactionModelClass> list;

    public NotificationAdapter(Activity context,List<TransactionModelClass> list)
    {
        super(context, R.layout.custom_layout_notification,list);
        this.context =context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.custom_layout_notification, null, true);

        TextView txtNPName = (TextView) listViewItem.findViewById(R.id.txtNPName);
        TextView txtNPDOI = (TextView) listViewItem.findViewById(R.id.txtNPDOI);
        TextView txtNPDOR = (TextView) listViewItem.findViewById(R.id.txtNPDOR);
        TextView txtNPPlate = (TextView) listViewItem.findViewById(R.id.txtNPPlate);
        TextView txtNPRate = (TextView) listViewItem.findViewById(R.id.txtNPRate);
        ImageView imgNP = (ImageView) listViewItem.findViewById(R.id.imgNP);

        TransactionModelClass obj = list.get(position);

        txtNPName.setText(obj.getNameOfCustomer());
        txtNPDOI.setText("Issue Date : " + obj.getDateOfIsuue() + " (" + obj.getTimeOfSubmit() + ")");
        txtNPDOR.setText("Return Date : " + obj.getDateOfReturn());
        txtNPPlate.setText("Plate Quantity : " + obj.getNoOfPlates() + " Plates");
        txtNPRate.setText("Rate Per Plate : " + obj.getNoOfPlates() + "/-");
        imgNP.setImageResource(R.drawable.ring);

        return listViewItem;
    }
}
