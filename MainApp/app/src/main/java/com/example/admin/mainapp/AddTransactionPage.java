package com.example.admin.mainapp;

import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mainapp.Model.SettingModelClass;
import com.example.admin.mainapp.Model.TransactionModelClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Calendar;

public class AddTransactionPage extends AppCompatActivity {
    private String ID;
    private String EmployeeName;
    private String DOIString,DORString,SubmitTime;
    private EditText edtATName,edtATPlate,edtATAdvance,edtATDOIssue,edtATDOReturn;
    private ImageView imgATDOI,imgATDOR;
    private Button btnATCancel,btnATAdd;
    private DatabaseReference myreffour;
    private SettingModelClass sobj;
    private FirebaseAuth firebaseAuth2;
    RadioGroup rgAT;
    RadioButton rbAT1,rbAT2;
    private int rate1=40,rate2,finalRate,stock;
   DatePickerDialog datePickerDialog1,datePickerDialog2;
    int day,month,year,hour,min;
    Calendar cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction_page);


        ID = getIntent().getStringExtra("CUST_ID");
        firebaseAuth2 = FirebaseAuth.getInstance();

        edtATName = (EditText)findViewById(R.id.edtATName);
        edtATPlate = (EditText)findViewById(R.id.edtATPlate);
        edtATAdvance = (EditText)findViewById(R.id.edtATAdvance);
        edtATDOIssue = (EditText)findViewById(R.id.edtATDOIssue);
        edtATDOReturn = (EditText)findViewById(R.id.edtATDOReturn);
        imgATDOI = (ImageView)findViewById(R.id.imgATDOI);
        imgATDOR = (ImageView)findViewById(R.id.imgATDOR);
        btnATAdd = (Button)findViewById(R.id.btnATAdd);
        btnATCancel = (Button)findViewById(R.id.btnATCancel);
        rgAT = (RadioGroup) findViewById(R.id.rdgAT);
        rbAT1 = (RadioButton) findViewById(R.id.rbAT1);
        rbAT2 = (RadioButton) findViewById(R.id.rbAT2);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = (cal.get(Calendar.MONTH)+1);
        year = cal.get(Calendar.YEAR);
        DOIString = day+"/"+month+"/"+year;
        DORString = day+"/"+(month+1)+"/"+year;
        myreffour = FirebaseDatabase.getInstance().getReference("transactions");

        getSettingObject();


        edtATName.setText(getIntent().getStringExtra("CUST_NAME"));
        edtATName.setEnabled(false);
        edtATDOIssue.setText(DOIString);
        edtATDOReturn.setText(DORString);
        getEmployeeName();
        btnATCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgATDOI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog1 = new DatePickerDialog(AddTransactionPage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        edtATDOIssue.setText(dayOfMonth+"/"+month+"/"+year);
                        DOIString = dayOfMonth+"/"+month+"/"+year;
                    }
                },year,month-1,day);
                datePickerDialog1.show();
            }
        });
        imgATDOR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog2 = new DatePickerDialog(AddTransactionPage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        edtATDOReturn.setText(dayOfMonth+"/"+month+"/"+year);
                        DORString = dayOfMonth+"/"+month+"/"+year;
                    }
                },year,month,day);
                datePickerDialog2.show();
            }
        });
        btnATAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameofCustomer = edtATName.getText().toString();
                String dateofissue = edtATDOIssue.getText().toString();
                String dateofreturn = edtATDOReturn.getText().toString();
                SubmitTime = getTime();
                int noofplate = Integer.parseInt(edtATPlate.getText().toString());
                int priceperplate = finalRate;
                int advanceamount = Integer.parseInt(edtATAdvance.getText().toString());
                String trasactionID = myreffour.push().getKey();
                TransactionModelClass t = new TransactionModelClass(nameofCustomer,dateofissue,dateofreturn,EmployeeName,SubmitTime,
                        noofplate,priceperplate,advanceamount,trasactionID);

                myreffour.child(ID).child(trasactionID).setValue(t).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                            Toast.makeText(AddTransactionPage.this,"Transaction Added",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(AddTransactionPage.this,"Transaction Failed",Toast.LENGTH_SHORT).show();
                    }
                });
                finish();
            }
        });
    }

    private void getSettingObject(){
        DatabaseReference myreffive = FirebaseDatabase.getInstance().getReference("settings");
        myreffive.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    sobj = dataSnapshot.getValue(SettingModelClass.class);
                    rate1 = sobj.getRate1();
                    rate2 = sobj.getRate2();
                    stock = sobj.getPlates();
                    setRadioButton();
                }
                else
                {
                    Toast.makeText(AddTransactionPage.this,"Setting not present ",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void setRadioButton(){
        rbAT1.setText(rate1+" Rupees.");
        rbAT2.setText(rate2+" Rupees.");
        finalRate = rate1;
    }

    public void selectFinalRate(View v){
        int rbtns = rgAT.getCheckedRadioButtonId();
        if(rbtns == R.id.rbAT1) {
            finalRate = rate1;
            Toast.makeText(AddTransactionPage.this, finalRate + " Rs.Selected", Toast.LENGTH_SHORT).show();
        } else {
            finalRate = rate2;
            Toast.makeText(AddTransactionPage.this, finalRate + " Rs. Selected", Toast.LENGTH_SHORT).show();
        }
    }
    private  void getEmployeeName(){
        EmployeeName = firebaseAuth2.getCurrentUser().getDisplayName();
        if(EmployeeName == null)
        {
            EmployeeName = "null";
        }
    }
    private String getTime()
    {
        hour = cal.get(Calendar.HOUR_OF_DAY);
        min = cal.get(Calendar.MINUTE);
        String ext = " am";
        if(hour >= 12)
            ext=" pm";
        if(hour>12)
            hour = hour%12;
        return String.format("%02d:%02d %s", hour, min, ext);
    }
}
