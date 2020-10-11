package com.example.shauryaworkers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    TextView btn;
    EditText inputUsername,inputEmail,inputPassword,inputConfirmPass;
    Button btnRegister;
    private FirebaseAuth mauth;
    private ProgressDialog mLoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         btn = findViewById(R.id.textAccount);
         inputUsername=findViewById(R.id.inputUsername);
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.iputPassword);
        inputConfirmPass=findViewById(R.id.inputConfirmPass);
        btnRegister=findViewById(R.id.btnRegister);
        mauth=FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(Register.this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCrededentials();
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });
    }

    private void checkCrededentials() {
        String username= inputUsername.getText().toString();
        String email= inputEmail.getText().toString();
        String password= inputPassword.getText().toString();
        String confirmPassword= inputConfirmPass.getText().toString();

        if(username.isEmpty() || username.length()<7){
            showError(inputUsername,"Your Username is not valid!");
        }
        else if(email.isEmpty() || !email.contains("@")){
            showError(inputEmail,"Your Email is not valid!");
        }
        else if(password.isEmpty() || password.length()<7){
            showError(inputPassword,"Your Password is not valid!");
        }
        else if(confirmPassword.isEmpty() && confirmPassword.equals(password)){
            showError(inputConfirmPass,"Password does not Match!");
        }
        else {
           // Toast.makeText(this, "call registration method", Toast.LENGTH_SHORT).show();
            mLoadingBar.setTitle("Registration");
            mLoadingBar.setMessage("Plaese Wait, Checking Your Credentials");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        mLoadingBar.dismiss();
                        Toast.makeText(Register.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this,Main2.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(Register.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
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