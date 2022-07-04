package com.example.gamergroups;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private EditText et_email;
    private EditText et_password;
    private Button btn_Login;
    private Button btn_back;
    private TextView tv_ClickToRegister;
    private FirebaseAuth fb_auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        fb_auth = FirebaseAuth.getInstance();

        getUIIDs();

        String text = "Sign up!";
        SpannableString ss = new SpannableString(text);
        ClickableSpan cs = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent switchToSignup = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(switchToSignup);
                finish();
            }
        };

        ss.setSpan(cs, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_ClickToRegister.setText(ss);
        tv_ClickToRegister.setMovementMethod(LinkMovementMethod.getInstance());

        setListeners();
    }

    private void getUIIDs() {
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_Login = findViewById(R.id.btn_login);
        btn_back = findViewById(R.id.btn_back);
        tv_ClickToRegister = findViewById(R.id.tv_clickToRegister);
    }

    private void loginUser(String email, String password) {
        fb_auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(Consts.LOGCAT_TAG, "signInWithEmail:success");

                        DAOManager.daoUser.CurrentUser = new User(email, fb_auth.getCurrentUser().getDisplayName(),
                                fb_auth.getCurrentUser().getPhotoUrl().toString(), new ArrayList<>());
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(Consts.LOGCAT_TAG, "signInWithEmail:failure", task.getException());

                        String msg = "";
                        if (task.getException() != null)
                            msg = task.getException().getMessage();

                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setListeners() {
        btn_back.setOnClickListener(view -> {
            finish();
        });

        btn_Login.setOnClickListener(view -> {
            String email = et_email.getText().toString();
            String password = et_password.getText().toString();

            loginUser(email, password);
        });
    }
}