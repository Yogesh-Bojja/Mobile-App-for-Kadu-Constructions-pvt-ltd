package com.example.admin.mainapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class UserPage extends AppCompatActivity {
    private FirebaseAuth firebaseAuth1;
    private TextView txtUserName,txtUserEmail;
    private FloatingActionButton fabUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        firebaseAuth1 = FirebaseAuth.getInstance();
        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtUserEmail = (TextView) findViewById(R.id.txtUserEmail);
        fabUser = (FloatingActionButton)findViewById(R.id.fabUser);
        fabUser.setVisibility(View.INVISIBLE);
        txtUserName.setText(firebaseAuth1.getCurrentUser().getDisplayName());
        txtUserEmail.setText(firebaseAuth1.getCurrentUser().getEmail());
        if(firebaseAuth1.getCurrentUser().getEmail().equals("root@gmail.com"))
        {
            fabUser.setVisibility(View.VISIBLE);
        }

        fabUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserPage.this,SignupPage.class);
                startActivity(i);
            }
        });
    }
}
