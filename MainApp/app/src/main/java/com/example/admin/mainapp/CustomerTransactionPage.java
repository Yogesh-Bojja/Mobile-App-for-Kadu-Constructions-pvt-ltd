package com.example.admin.mainapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mainapp.Adapter.TransactionListAdapter;
import com.example.admin.mainapp.Model.SettingModelClass;
import com.example.admin.mainapp.Model.TransactionModelClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerTransactionPage extends AppCompatActivity {
    private String ID,NAME,Child_id;
    private TextView txtETName;
    private SearchView searchViewCustomerTransaction;
    private ListView listViewCustomerTransaction;
    private FloatingActionButton fabCustomerTransaction;
    private List<TransactionModelClass> mylist1,temp1;
    private TransactionListAdapter adapter;
    private DatabaseReference myrefsix;
    private SettingModelClass sobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_transaction_page);
        txtETName = (TextView) findViewById(R.id.txtETName);
        ID = getIntent().getStringExtra("CUST_ID");
        NAME = getIntent().getStringExtra("CUST_NAME");
        fabCustomerTransaction = (FloatingActionButton)findViewById(R.id.fabCustomerTransaction);
        listViewCustomerTransaction = (ListView)findViewById(R.id.listViewCustomerTransaction) ;
        searchViewCustomerTransaction = (SearchView) findViewById(R.id.searchViewCustomerTransaction);
        mylist1 = new ArrayList<>();
        temp1 = new ArrayList<>();
        myrefsix = FirebaseDatabase.getInstance().getReference("transactions").child(ID);

        txtETName.setText("Transaction : "+NAME);

        searchViewCustomerTransaction.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getData(newText.toString());
                return false;
            }
        });
        fabCustomerTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerTransactionPage.this,AddTransactionPage.class);
                i.putExtra("CUST_NAME",NAME);
                i.putExtra("CUST_ID",ID);
                startActivity(i);
            }
        });
        listViewCustomerTransaction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(CustomerTransactionPage.this,DisplayTransactionPage.class);
                i.putExtra("PARENT_ID",ID);
                i.putExtra("CHILD_ID",temp1.get(position).getTransactionID());
                startActivity(i);
            }
        });
        listViewCustomerTransaction.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TransactionModelClass t = temp1.get(position);
                showUpdateDialog(t);
                return true;
            }
        });
    }

     @Override
   protected void onStart() {
        super.onStart();
        myrefsix.addValueEventListener(new ValueEventListener() {
           @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               if(dataSnapshot.hasChildren() && dataSnapshot.exists()) {
                   mylist1.clear();
                   temp1.clear();
                   for (DataSnapshot d : dataSnapshot.getChildren()) {
                       TransactionModelClass cobj = d.getValue(TransactionModelClass.class);
                       mylist1.add(cobj);
                   }
                   for (TransactionModelClass o : mylist1) {
                       temp1.add(o);
                   }
                   adapter = new TransactionListAdapter(CustomerTransactionPage.this, temp1);
                   listViewCustomerTransaction.setAdapter(adapter);
                   adapter.notifyDataSetChanged();
               }
               else {
                   Toast.makeText(CustomerTransactionPage.this,"No Transactions ",Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void getData(String Query){
        temp1.clear();
        if(searchViewCustomerTransaction!=null)
        {
            for(TransactionModelClass o : mylist1 )
            {
                if(o.getDateOfIsuue().toString().startsWith(Query))
                {
                    temp1.add(o);
                }

            }

        }
        else
        {
            temp1=mylist1;
        }
        adapter =new TransactionListAdapter(CustomerTransactionPage.this,temp1);
        listViewCustomerTransaction.setAdapter(adapter);
    }

    private void showUpdateDialog(TransactionModelClass obj){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_transaction,null);
        dialogBuilder.setView(dialogView);

        Child_id = obj.getTransactionID();
        final EditText edtUpdateTransName = (EditText) dialogView.findViewById(R.id.edtUpdateTransName);
        final EditText edtUpdateTransPlate = (EditText) dialogView.findViewById(R.id.edtUpdateTransPlate);
        final EditText edtUpdateTransRate = (EditText) dialogView.findViewById(R.id.edtUpdateTransRate);
        final EditText edtUpdateTransAdvance = (EditText) dialogView.findViewById(R.id.edtUpdateTransAdvance);
        final EditText edtUpdateTransDOI = (EditText)dialogView.findViewById(R.id.edtUpdateTransDOI);
        final EditText edtUpdateTransDOR = (EditText)dialogView.findViewById(R.id.edtUpdateTransDOR);
        Button btnUpdateTransDelete = (Button) dialogView.findViewById(R.id.btnUpdateTransDelete);
        Button btnUpdateTransUpdate = (Button) dialogView.findViewById(R.id.btnUpdateTransUpdate);
        final String EmpName = obj.getEmployeePerson();
        final String time = obj.getTimeOfSubmit();
        String s = obj.getNameOfCustomer();
        edtUpdateTransName.setText(s);
        edtUpdateTransName.setEnabled(false);
        edtUpdateTransPlate.setText(obj.getNoOfPlates()+"");
        edtUpdateTransDOI.setText(obj.getDateOfIsuue());
        edtUpdateTransDOR.setText(obj.getDateOfReturn());
        edtUpdateTransAdvance.setText(obj.getAdvanceAmount()+"");
        edtUpdateTransRate.setText(obj.getPricePerPlate()+"");


        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnUpdateTransUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cust_name = edtUpdateTransName.getText().toString();
                int plate = Integer.parseInt(edtUpdateTransPlate.getText().toString());
                String DOI = edtUpdateTransDOI.getText().toString();
                String DOR = edtUpdateTransDOR.getText().toString();
                int Advance = Integer.parseInt(edtUpdateTransAdvance.getText().toString());
                int Rate = Integer.parseInt(edtUpdateTransRate.getText().toString());


                TransactionModelClass t = new TransactionModelClass(cust_name,DOI,DOR,EmpName,time,plate,Rate,Advance,Child_id);
                DatabaseReference myrefseven = FirebaseDatabase.getInstance().getReference("transactions").child(ID).child(Child_id);
                myrefseven.setValue(t).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                           alertDialog.dismiss();
                            Toast.makeText(CustomerTransactionPage.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            alertDialog.dismiss();
                            Toast.makeText(CustomerTransactionPage.this, "Updation Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

        btnUpdateTransDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myrefEight = FirebaseDatabase.getInstance().getReference("transactions").child(ID).child(Child_id);
                myrefEight.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            alertDialog.dismiss();
                            Toast.makeText(CustomerTransactionPage.this, " Deleted Successfully ", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            alertDialog.dismiss();
                            Toast.makeText(CustomerTransactionPage.this, " Deletion Failed ", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }



}
