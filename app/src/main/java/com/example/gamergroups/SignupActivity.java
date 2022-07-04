package com.example.gamergroups;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {
    private EditText et_displayName;
    private EditText et_email;
    private EditText et_password;
    private Button btn_signUp;
    private Button btn_back;
    private EditText et_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        getUIIDs();
        setListeners();
    }

    private void getUIIDs() {
        et_displayName = findViewById(R.id.et_displayName);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_signUp = findViewById(R.id.btn_signUp);
        btn_back = findViewById(R.id.btn_back);
        et_icon = findViewById(R.id.et_icon);
    }

    private void saveUserToDB(String email, String displayName, String iconURL, ArrayList<Integer> groups) {
        User usr = new User(email, displayName, iconURL, groups);
        saveUserToDB(usr);
    }

    private void saveUserToDB(User usr) {
        DAOManager.daoUser.add(usr);
    }

    private void setListeners() {
        btn_back.setOnClickListener(view -> {
            finish();
        });

        btn_signUp.setOnClickListener(view -> {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            String displayName = et_displayName.getText().toString();
            String email = et_email.getText().toString();
            String password = et_password.getText().toString();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(Consts.LOGCAT_TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                if (user != null) {
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(displayName)
                                            .setPhotoUri(Uri.parse(et_icon.getText().toString()))
                                            .build();

                                    user.updateProfile(profileUpdates);

                                    DAOManager.daoUser.CurrentUser = new User(email, displayName, et_icon.getText().toString(), new ArrayList<>());

                                    finish();
                                }
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(Consts.LOGCAT_TAG, "createUserWithEmail:failure", task.getException());

                                String msg = "";
                                if (task.getException() != null)
                                    msg = task.getException().getMessage();

                                Toast.makeText(SignupActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });
    }
}