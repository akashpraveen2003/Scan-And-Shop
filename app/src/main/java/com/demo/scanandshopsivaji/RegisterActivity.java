package com.demo.scanandshopsivaji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
     FirebaseAuth mAuth;
    TextView backtohome;
    Button register_Button;
    EditText register_name, register_phonenumber, register_email, register_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        register_Button = (Button) findViewById(R.id.register_Button);
        register_Button.setOnClickListener(this);

        backtohome = (TextView) findViewById(R.id.backtohome);
        backtohome.setOnClickListener(this);

        register_name = (EditText) findViewById(R.id.register_name);
        register_phonenumber = (EditText) findViewById(R.id.register_phonenumber);
        register_email = (EditText) findViewById(R.id.register_email);
        register_password = (EditText) findViewById(R.id.register_password);
    mAuth=FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backtohome:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.register_Button:
                registeruser();
                break;
        }
    }

    private void registeruser() {
        String email = register_email.getText().toString().trim();
        String name = register_name.getText().toString().trim();
        String phonenumber = register_phonenumber.getText().toString().trim();
        String password = register_password.getText().toString().trim();

        if (name.isEmpty()) {
            register_name.setError("Enter your name");
            register_name.requestFocus();
            return;
        }
        if (phonenumber.isEmpty()) {
            register_phonenumber.setError("enter your phone number");
            register_phonenumber.requestFocus();
            return;
        }
        if (phonenumber.length() != 10) {
            register_phonenumber.setError("enter valid phone numer");
            register_phonenumber.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            register_email.setError("Enter your email");
            register_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            register_email.setError("valid email needed");
            register_email.requestFocus();
            return;

        }
        if (password.isEmpty()) {
            register_password.setError("enter password");
            register_password.requestFocus();
            return;
        }
        if (password.length() < 6) {
            register_password.setError("more than 6 characterss");
            register_password.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    UserActivity user=new UserActivity(name ,phonenumber,email);
                    FirebaseDatabase.getInstance().getReference("USERS").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(RegisterActivity.this,"User has been registered",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this,"Failed to register",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,"Failed to register",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}