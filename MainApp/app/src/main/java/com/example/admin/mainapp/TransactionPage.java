package com.example.admin.mainapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.mainapp.Adapter.TransactionListAdapter;
import com.example.admin.mainapp.Adapter.TransactionPageAdapter;
import com.example.admin.mainapp.Model.TransactionModelClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TransactionPage extends AppCompatActivity {
    private SearchView searchViewTransactionPage;
    private ListView listViewTransactionPage;
    private DatabaseReference refGetParentID;
    private List<TransactionModelClass> temp2, mylist2;
    private List<String> ParentID;
    private TransactionPageAdapter adapter;

    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_page);


        listViewTransactionPage = (ListView) findViewById(R.id.listViewTransactionPage);
        mylist2 = new ArrayList<>();
        temp2 = new ArrayList<>();
        ParentID = new ArrayList<>();
        searchViewTransactionPage = (SearchView)findViewById(R.id.searchViewTransactionPage);


        refGetParentID = FirebaseDatabase.getInstance().getReference("transactions");
        refGetParentID.addValueEventListener(new ValueEventListener() {
            @Override
            public synchronized void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren())
                {    ParentID.clear(); mylist2.clear();temp2.clear();
                    for(DataSnapshot obj : dataSnapshot.getChildren())
                    {
                        ParentID.add(obj.getKey());
                    }
                    for(String childID : ParentID)
                    {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("transactions").child(childID);
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot d : dataSnapshot.getChildren()) {
                                    TransactionModelClass cobj = d.getValue(TransactionModelClass.class);
                                    mylist2.add(cobj);
                                }
                                adapter = new TransactionPageAdapter(TransactionPage.this, mylist2);
                                listViewTransactionPage.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                }
                else
                {
                    Toast.makeText(TransactionPage.this,"No transactions of Single Customer",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        searchViewTransactionPage.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    }
    private void getData(String date){
        temp2.clear();
        if(searchViewTransactionPage!=null)
        {
            for(TransactionModelClass o : mylist2 )
            {
                if(o.getDateOfIsuue().startsWith(date))
                {
                    temp2.add(o);
                }

            }

        }
        else
        {
            temp2=mylist2;
        }
        adapter =new TransactionPageAdapter(TransactionPage.this,temp2);
        listViewTransactionPage.setAdapter(adapter);
    }

}