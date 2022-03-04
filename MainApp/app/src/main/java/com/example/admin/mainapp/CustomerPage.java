package com.example.admin.mainapp;

import android.app.ProgressDialog;
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
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.mainapp.Adapter.CustomerListAdapter;
import com.example.admin.mainapp.Model.CustomerModelClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerPage extends AppCompatActivity {
    private SearchView searchViewCustomer;
    private ListView listViewCustomer;
    private FloatingActionButton fabCustomer;
    private DatabaseReference myrefone;
    private List<CustomerModelClass> mylist,temp;
    private ProgressDialog progressDialog;
    private CustomerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_page);

        fabCustomer = (FloatingActionButton)findViewById(R.id.fabCustomer);
        listViewCustomer = (ListView)findViewById(R.id.listViewCustomer);
        searchViewCustomer = (SearchView)findViewById(R.id.searchViewCustomer);
        mylist = new ArrayList<>();
        temp = new ArrayList<>();
        myrefone = FirebaseDatabase.getInstance().getReference("Customers");


        searchViewCustomer.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getData(newText);
                return false;
            }
        });
        fabCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerPage.this,AddCustomer.class);
                startActivity(i);
            }
        });
        listViewCustomer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModelClass cu = temp.get(position);
                showUpdateDialog(cu.getId(),cu.getName());
                return true;
            }
        });
        listViewCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(CustomerPage.this,CustomerTransactionPage.class);
                i.putExtra("CUST_ID",temp.get(position).getId());
                i.putExtra("CUST_NAME",temp.get(position).getName());
                startActivity(i);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

         myrefone.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               if(dataSnapshot.hasChildren()) {
                   mylist.clear();
                   temp.clear();
                   for (DataSnapshot d : dataSnapshot.getChildren()) {
                       CustomerModelClass cobj = d.getValue(CustomerModelClass.class);
                       mylist.add(cobj);
                   }
                   for (CustomerModelClass o : mylist) {
                       temp.add(o);
                   }
                   adapter = new CustomerListAdapter(CustomerPage.this, temp);
                   listViewCustomer.setAdapter(adapter);
                   adapter.notifyDataSetChanged();
               }
               else
               {
                   Toast.makeText(CustomerPage.this,"No Customers",Toast.LENGTH_SHORT).show();
               }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

    }

    private void getData(String Query){
     //   List<CustomerModelClass> filteredOutput = new ArrayList<>();
        temp.clear();
        if(searchViewCustomer!=null)
        {
            for(CustomerModelClass o : mylist )
            {
                if(o.getName().toLowerCase().startsWith(Query.toLowerCase()))
                {
                    temp.add(o);
                }

            }

        }
        else
        {
            temp=mylist;
        }
        adapter =new CustomerListAdapter(CustomerPage.this,temp);
        listViewCustomer.setAdapter(adapter);
    }

    private void showUpdateDialog(String cId,String name)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_customer,null);
        dialogBuilder.setView(dialogView);

        final EditText edtn = (EditText) dialogView.findViewById(R.id.edtUpdateCustName);
        final EditText edte = (EditText) dialogView.findViewById(R.id.edtUpdtaeCustEmail);
        final EditText edtp = (EditText) dialogView.findViewById(R.id.edtUpdateCustPhone);
        final EditText edta = (EditText) dialogView.findViewById(R.id.edtUpdateCustAddress);
        final Button btnu = (Button) dialogView.findViewById(R.id.btnUpdateCustUpdate);
        final Button btnd = (Button) dialogView.findViewById(R.id.btnUpdateCustDelete);
        final String cid = cId;
        edtn.setText(name);

        dialogBuilder.setTitle("Update Customer Details");
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        btnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtn.getText().toString();
                String email = edte.getText().toString();
                String phone = edtp.getText().toString();
                String address = edta.getText().toString();
                updateCustomer(name,email,phone,address,cid);
                alertDialog.dismiss();
            }
        });
        btnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCustomer(cid);
                alertDialog.dismiss();
            }
        });

    }
    private void deleteCustomer(String Id){
        DatabaseReference myrefthree = FirebaseDatabase.getInstance().getReference("Customers").child(Id);
        myrefthree.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Toast.makeText(CustomerPage.this," Deleted Successfully ",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(CustomerPage.this," Deletion Failed ",Toast.LENGTH_SHORT).show();
            }
        });
        DatabaseReference myrefeight = FirebaseDatabase.getInstance().getReference("transactions").child(Id);
        myrefeight.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Toast.makeText(CustomerPage.this," Deleted Successfully ",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(CustomerPage.this," Deletion Failed ",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateCustomer(String name,String email,String phone,String address,String id)
    {
        DatabaseReference myreftwo = FirebaseDatabase.getInstance().getReference("Customers");
        CustomerModelClass c = new CustomerModelClass(name,email,phone,address,id);
        myreftwo.child(id).setValue(c).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Toast.makeText(CustomerPage.this," Updated Successfully ",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(CustomerPage.this," Updation Failed ",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
















