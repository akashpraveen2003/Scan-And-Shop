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
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView register,forgotpassword;
    EditText EditTextemail,EditTextpassword;
    Button Login;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("Scan And Shop");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register=(TextView)findViewById(R.id.register);
        register.setOnClickListener(this);
        Login=(Button) findViewById(R.id.login);
        Login.setOnClickListener(this);
        EditTextemail=(EditText) findViewById(R.id.email);
        EditTextpassword=(EditText) findViewById(R.id.password);
        forgotpassword=(TextView)findViewById(R.id.forgotpassword);
        forgotpassword.setOnClickListener(this);

        mAuth=FirebaseAuth.getInstance();
    }

    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.login:
                userLogin();
                break;
                case R.id.forgotpassword:
                startActivity(new Intent(this,Forgotpassword.class));
                break;

        }
    }

    private void userLogin() {
        String email=EditTextemail.getText().toString().trim();
        String password=EditTextpassword.getText().toString().trim();

        if(email.isEmpty())
        {
            EditTextemail.setError("Enter your email");
            EditTextemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            EditTextemail.setError("Enter valid Email Address");
            EditTextemail.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            EditTextpassword.setError("Enter the password");
            EditTextpassword.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            EditTextpassword.setError("less than 6 characters");
            EditTextpassword.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser User=FirebaseAuth.getInstance().getCurrentUser();
                    if(User.isEmailVerified()) {
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    }
                    else
                    {
                        User.sendEmailVerification();
                        Toast.makeText(MainActivity.this,"Check your email",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Failed to login",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
