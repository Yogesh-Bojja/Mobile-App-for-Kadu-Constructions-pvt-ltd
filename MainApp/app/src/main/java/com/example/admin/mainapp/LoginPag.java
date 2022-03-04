package com.example.admin.mainapp;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPag extends AppCompatActivity {
    EditText edtLoginId,edtPassword;
    Button btnLogin,btnGoToSignupPage;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pag);

        edtLoginId = (EditText)findViewById(R.id.edtLoginId);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        btnLogin = (Button)findViewById(R.id.btnLoginLPa);
        btnGoToSignupPage = (Button) findViewById(R.id.btnGoToSignupPage);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null)
        {
            Intent intent = new Intent(LoginPag.this,MainActivity.class);
            startActivity(intent);
            finish();
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(LoginPag.this);
                progressDialog.setMessage("Signing... Please Wait");
                progressDialog.show();
                signinTheUserWithEmailAndPassword(edtLoginId.getText().toString(),edtPassword.getText().toString());
            }
        });

        btnGoToSignupPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginPag.this,SignupPage.class);
                startActivity(i);
            }
        });

    }


    private void signinTheUserWithEmailAndPassword(String email,String password){
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    Intent intent = new Intent(LoginPag.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(LoginPag.this,"Signin failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}



