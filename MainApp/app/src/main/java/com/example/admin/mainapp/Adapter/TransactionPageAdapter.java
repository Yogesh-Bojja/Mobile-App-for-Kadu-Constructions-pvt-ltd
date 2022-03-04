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
 * Created by Admin on 22-01-2018.
 */

public class TransactionPageAdapter extends ArrayAdapter<TransactionModelClass> {

    private Activity context;
    private List<TransactionModelClass> list;

    public TransactionPageAdapter(Activity context,List<TransactionModelClass> list)
    {
        super(context, R.layout.custom_layout_transaction_row,list);
        this.context =context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.custom_layout_transaction_row,null,true);

        TextView txtTPName = (TextView) listViewItem.findViewById(R.id.txtTPName);
        TextView txtTPDOI = (TextView) listViewItem.findViewById(R.id.txtTPDOI);
        TextView txtTPDOR = (TextView) listViewItem.findViewById(R.id.txtTPDOR);
        TextView txtTPPlate = (TextView) listViewItem.findViewById(R.id.txtTPPlate);
        TextView txtTPRate = (TextView) listViewItem.findViewById(R.id.txtTPRate);
        ImageView imgTP = (ImageView) listViewItem.findViewById(R.id.imgTP);

        TransactionModelClass obj = list.get(position);

        txtTPName.setText(obj.getNameOfCustomer());
        txtTPDOI.setText("Issue Date : "+obj.getDateOfIsuue()+" ("+obj.getTimeOfSubmit()+")");
        txtTPDOR.setText("Return Date : "+obj.getDateOfReturn());
        txtTPPlate.setText("Plate Quantity : "+obj.getNoOfPlates()+" Plates");
        txtTPRate.setText("Rate Per Plate : "+obj.getNoOfPlates()+"/-");
        imgTP.setImageResource(R.drawable.ic_assignment_black_150dp);

        return listViewItem;
    }
}
