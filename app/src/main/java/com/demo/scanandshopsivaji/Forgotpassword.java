package com.demo.scanandshopsivaji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgotpassword extends AppCompatActivity {

    EditText Emailreset;
    Button resetpassword;
    FirebaseAuth Auth;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        Emailreset = (EditText) findViewById(R.id.Emailedit);
        resetpassword = (Button) findViewById(R.id.resetpassword);
        Auth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetpassword();
            }
        });
    }

    private void resetpassword() {
        String email = Emailreset.getText().toString().trim();
        if (email.isEmpty()) {
            Emailreset.setError("Empty");
            Emailreset.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Emailreset.setError("enter valid email");
            Emailreset.requestFocus();
            return;
        }
        Auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Forgotpassword.this, "Check your email", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Forgotpassword.this, "Random error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}