package com.example.admin.mainapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.mainapp.Adapter.NotificationAdapter;
import com.example.admin.mainapp.Model.TransactionModelClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NotificationPage extends AppCompatActivity {

    private ListView listViewNotification;
    private NotificationAdapter adapter;
    private List<TransactionModelClass> mylist3,temp3;
    private List<String> ParentID1;
    private DatabaseReference refGetParentID1;
    private Calendar cal1;
    private String DateofReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);

        listViewNotification = (ListView)findViewById(R.id.listViewNotification);
        mylist3 = new ArrayList<>();
        temp3 = new ArrayList<>();
        ParentID1 = new ArrayList<>();

        cal1 = Calendar.getInstance();
        int day1 = cal1.get(Calendar.DAY_OF_MONTH);
        int month1 = cal1.get(Calendar.MONTH);
        int year1 = cal1.get(Calendar.YEAR);

        DateofReturn = day1+"/"+month1+"/"+year1;

    }

    @Override
    protected void onStart() {
        super.onStart();
        refGetParentID1 = FirebaseDatabase.getInstance().getReference("transactions");
        refGetParentID1.addValueEventListener(new ValueEventListener() {
            @Override
            public synchronized void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren())
                {    ParentID1.clear(); mylist3.clear();
                    for(DataSnapshot obj : dataSnapshot.getChildren())
                    {
                        ParentID1.add(obj.getKey());
                    }
                    for(String childID : ParentID1)
                    {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("transactions").child(childID);
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot d : dataSnapshot.getChildren()) {
                                    TransactionModelClass cobj1 = d.getValue(TransactionModelClass.class);
                                        mylist3.add(cobj1);
                                }
                                adapter = new NotificationAdapter(NotificationPage.this, mylist3);
                                listViewNotification.setAdapter(adapter);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }

                }
                else
                {
                    Toast.makeText(NotificationPage.this,"No Customer Transactions", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
