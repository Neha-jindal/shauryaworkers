package com.example.shauryaworkers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText inputemail,inputpass;
    Button btnLogin;
    TextView btn;
    private FirebaseAuth mauth;
    private ProgressDialog mLoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         btn = findViewById(R.id.textViewSignUp);
        inputemail=findViewById(R.id.inputUser);
        inputpass=findViewById(R.id.inputpass);

        btnLogin=findViewById(R.id.btnLogin);
        mauth=FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(Login.this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    checkCrededentials();

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class));

            }
        });
    }
    private void checkCrededentials() {
        final String username= inputemail.getText().toString();
        final String pass= inputpass.getText().toString();


        if(username.isEmpty() || !username.contains("@")){
            showError(inputemail,"Your email is not valid!");
        }


        else if(pass.isEmpty() || pass.length()<7){
            showError(inputpass,"Your Password is not valid!");
        }

        else {
           // Toast.makeText(this, "call registration method", Toast.LENGTH_SHORT).show();
            mLoadingBar.setTitle("Login");
            mLoadingBar.setMessage("Plaese Wait, Checking Your Credentials");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mauth.signInWithEmailAndPassword(username,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        mLoadingBar.dismiss();
                        //Toast.makeText(Login.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this,Main2.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                    else {
                        Toast.makeText(Login.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void showError(EditText input, String s) {

        input.setError(s);
        input.requestFocus();
    }
}