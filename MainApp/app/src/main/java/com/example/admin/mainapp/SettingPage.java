package com.example.admin.mainapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mainapp.Model.SettingModelClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingPage extends AppCompatActivity {
    private EditText edtRate1,edtRate2,edtPlates;
    private Button btnSettingCancel,btnSettingSave;
    DatabaseReference myrefthree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);

        edtRate1 = (EditText)findViewById(R.id.edtRate1);
        edtRate2 = (EditText)findViewById(R.id.edtRate2);
        edtPlates = (EditText)findViewById(R.id.edtPlates);
        btnSettingCancel = (Button) findViewById(R.id.btnSettingCancel);
        btnSettingSave = (Button) findViewById(R.id.btnSettingSave);
        myrefthree = FirebaseDatabase.getInstance().getReference("settings");

        getSettingsFromFirebase();

        btnSettingCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSettingSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer rate1 = Integer.parseInt(edtRate1.getText().toString());
                Integer rate2 = Integer.parseInt(edtRate2.getText().toString());
                Integer plates = Integer.parseInt(edtPlates.getText().toString());


                    SettingModelClass s = new SettingModelClass(rate1, rate2, plates);
                    myrefthree.setValue(s).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                                Toast.makeText(SettingPage.this, " Settings Saved ", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(SettingPage.this, " Settings not Saved ", Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();

            }
        });


    }

    private void getSettingsFromFirebase(){
        myrefthree.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SettingModelClass settingModelClass = dataSnapshot.getValue(SettingModelClass.class);
                if(settingModelClass!=null) {
                    Integer dummyrate1 = settingModelClass.getRate1();
                    Integer dummyrate2 = settingModelClass.getRate2();
                    Integer dummyplates = settingModelClass.getPlates();

                    edtRate1.setText(dummyrate1.toString());
                    edtRate2.setText(dummyrate2.toString());
                    edtPlates.setText(dummyplates.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
