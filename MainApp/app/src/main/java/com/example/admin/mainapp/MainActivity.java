package com.example.admin.mainapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private CardView cardViewCustomers;
    private CardView cardViewTransactions;
    private CardView cardViewUsers;
    private CardView cardViewNotifications;
    private CardView cardViewSettings;
    private  ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardViewUsers = (CardView)findViewById(R.id.cardviewUsers);
        cardViewCustomers = (CardView)findViewById(R.id.cardviewCustomers);
        cardViewTransactions = (CardView)findViewById(R.id.cardviewTransactions);
        cardViewNotifications = (CardView)findViewById(R.id.cardviewNotifications);
        cardViewSettings = (CardView)findViewById(R.id.cardviewSettings);
        firebaseAuth = FirebaseAuth.getInstance();

        cardViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,UserPage.class);
                startActivity(i);
            }
        });
        cardViewCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,CustomerPage.class);
                startActivity(i);
            }
        });
        cardViewTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,TransactionPage.class);
                startActivity(i);
            }
        });
        cardViewNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,NotificationPage.class);
                startActivity(i);
            }
        });
        cardViewSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SettingPage.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuSignout)
        {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Signing out ... Please Wait");
            progressDialog.show();
            firebaseAuth.signOut();
            progressDialog.dismiss();
            Intent mainActivityIntent = new Intent(MainActivity.this,LoginPag.class);
            startActivity(mainActivityIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
