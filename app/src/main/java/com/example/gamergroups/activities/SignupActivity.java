package com.example.gamergroups.activities;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamergroups.R;
import com.example.gamergroups.data.User;
import com.example.gamergroups.helper.Consts;
import com.example.gamergroups.helper.DatabaseManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

// used to sign a new user
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

    private void setListeners() {
        btn_back.setOnClickListener(view -> finish());

        btn_signUp.setOnClickListener(view -> {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            String displayName = et_displayName.getText().toString();
            String email = et_email.getText().toString();
            String password = et_password.getText().toString();

            // create new user
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(Consts.LOGCAT_TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            // if user successfully signed up, update the display name and save for future use
                            if (user != null) {
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(displayName)
                                        .setPhotoUri(Uri.parse(et_icon.getText().toString()))
                                        .build();

                                user.updateProfile(profileUpdates);

                                DatabaseManager.Instance.CurrentUser = new User(email, displayName, et_icon.getText().toString());

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
                    });
        });
    }
}