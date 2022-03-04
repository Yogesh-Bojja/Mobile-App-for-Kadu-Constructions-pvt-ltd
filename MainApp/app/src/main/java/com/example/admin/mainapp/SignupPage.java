package com.example.admin.mainapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupPage extends AppCompatActivity {

    private EditText edtLoginId,edtPassword,edtName,edtConfirmPassword;
    private Button btnSignup;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);



        edtLoginId = (EditText)findViewById(R.id.edtLoginId);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText)findViewById(R.id.edtConfirmPassword);
        edtName = (EditText) findViewById(R.id.edtName);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        firebaseAuth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
                    progressDialog = new ProgressDialog(SignupPage.this);
                    progressDialog.setMessage("Creating Account ... Please Wait");
                    progressDialog.show();
                    signupUserWithEmailAndPassword(edtLoginId.getText().toString(), edtPassword.getText().toString());

                }else
                {
                    Toast.makeText(SignupPage.this, "Password not matched", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void signupUserWithEmailAndPassword(String email,String password){
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupPage.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful())
                {
                    Toast.makeText(SignupPage.this,"Un-Successfull Creation of Account ",Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(SignupPage.this,"Account Created succesfully",Toast.LENGTH_SHORT).show();
                    specifyUserProfile();
                }
            }
        });
    }

    private void specifyUserProfile(){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null)
        {
            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName(edtName.getText().toString()).build();
            firebaseUser.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        Toast.makeText(SignupPage.this,"Profile Updated Successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(SignupPage.this,"Profile Not Added",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}

