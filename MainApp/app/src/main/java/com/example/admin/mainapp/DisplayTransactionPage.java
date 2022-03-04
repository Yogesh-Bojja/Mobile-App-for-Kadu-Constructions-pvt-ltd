package com.example.admin.mainapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.example.admin.mainapp.Model.TransactionModelClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayTransactionPage extends AppCompatActivity {
    private String parentID,childID;
    private TextView txtDTCustomerName,txtDTPlate,txtDTPricePerPlate,txtDTTotalAmount,txtDTAdvance,txtDTRemaining,txtDTDOI,
                        txtDTDOR,txtDTEmployeeName,txtDTTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_display_transaction_page);

        parentID = getIntent().getStringExtra("PARENT_ID");
        childID = getIntent().getStringExtra("CHILD_ID");
        txtDTCustomerName = (TextView)findViewById(R.id.txtDTCustomerName);
        txtDTPlate = (TextView)findViewById(R.id.txtDTPlate);
        txtDTPricePerPlate = (TextView)findViewById(R.id.txtDTPricePerPlate);
        txtDTTotalAmount = (TextView)findViewById(R.id.txtDTTotalAmount);
        txtDTAdvance = (TextView)findViewById(R.id.txtDTAdvance);
        txtDTRemaining = (TextView)findViewById(R.id.txtDTRemaining);
        txtDTDOI = (TextView)findViewById(R.id.txtDTDOI);
        txtDTDOR = (TextView)findViewById(R.id.txtDTDOR);
        txtDTEmployeeName = (TextView)findViewById(R.id.txtDTEmployeeName);
        txtDTTime = (TextView)findViewById(R.id.txtDTTime);
        DatabaseReference myref7 = FirebaseDatabase.getInstance().getReference("transactions").child(parentID).child(childID);

        myref7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TransactionModelClass v = dataSnapshot.getValue(TransactionModelClass.class);
                setTextField(v);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setTextField(TransactionModelClass obj){
        txtDTCustomerName.setText(": "+obj.getNameOfCustomer());
        txtDTPlate.setText(": "+obj.getNoOfPlates()+"");
        txtDTPricePerPlate.setText(": "+obj.getPricePerPlate()+"");
        txtDTTotalAmount.setText(": "+obj.getTotalPrice()+"");
        txtDTAdvance.setText(": "+obj.getAdvanceAmount()+"");
        txtDTRemaining.setText(": "+obj.getRemainingAmount()+"");
        txtDTDOI.setText(": "+obj.getDateOfIsuue());
        txtDTDOR.setText(": "+obj.getDateOfReturn());
        txtDTEmployeeName.setText(": "+obj.getEmployeePerson());
        txtDTTime.setText(": "+obj.getTimeOfSubmit());
    }
}
