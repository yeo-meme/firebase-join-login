package com.example.firebaselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.firebaselogin.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    private String email = "";
    private String password = "";
    private FirebaseAuth firebaseAuth;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        Log.d("meme", String.valueOf(firebaseAuth));
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singUp(v);
            }
        });

    }

    public void singUp(View view) {
        email = binding.etEmail.getText().toString();
        password = binding.etPassword.getText().toString();
        Log.d("meme",email);
        Log.d("meme", password);
        if (isValidEmail() && isValidPasswd()) {
            createUser(email, password);
        }

    }

    public void signIn(View view) {
        email = binding.etEmail.getText().toString();
        password = binding.etPassword.getText().toString();

        if (isValidEmail() && isValidPasswd()) {
            loginUser(email,password);
        }

    }

    private boolean isValidEmail() {
        if (email.isEmpty()) {
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        } else {
            return true;
        }
    }

    //비밀번호 유효성 검사
    private boolean isValidPasswd() {
        if (password.isEmpty()) {
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            Log.d("pawd","pass wro");
            return false;
        } else {
            Log.d("pawd","pass ok");
            return true;
        }
    }

    private void createUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,R.string.success_signup,Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this,R.string.failed_signup, Toast.LENGTH_SHORT).show();
                            Log.w("meme", "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
    }


    private void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, R.string.success_login, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, R.string.failed_login, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

