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
 * Created by Admin on 21-01-2018.
 */

public class TransactionListAdapter extends ArrayAdapter<TransactionModelClass> {

    private Activity context;
    private List<TransactionModelClass> list;

    public TransactionListAdapter(Activity context,List<TransactionModelClass> list)
    {
        super(context, R.layout.custom_layout_transaction,list);
        this.context =context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.custom_layout_transaction,null,true);

        TextView txtCTName = (TextView) listViewItem.findViewById(R.id.txtCTName);
        TextView txtCTDate = (TextView) listViewItem.findViewById(R.id.txtCTDate);
        TextView txtCTPlate = (TextView) listViewItem.findViewById(R.id.txtCTPlate);
        ImageView imgCT = (ImageView) listViewItem.findViewById(R.id.imgCT);

        TransactionModelClass obj = list.get(position);

        txtCTName.setText(obj.getNameOfCustomer());
        txtCTDate.setText(obj.getDateOfIsuue());
        txtCTPlate.setText(obj.getNoOfPlates()+" Plates at ("+obj.getTimeOfSubmit()+")");
        imgCT.setImageResource(R.drawable.transaction);

        return listViewItem;
    }
}
