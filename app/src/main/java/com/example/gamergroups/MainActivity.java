package com.example.gamergroups;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button btnAccount;
    private ListView list;
    private GamesAdapter adapter;

//    ArrayList<String> maintitle = new ArrayList<String>() {
//        {
//            add("Astroneer");
//            add("Bloons TD");
//            add("Duck Hunt");
//            add("Idle Game");
//            add("My life in Portia");
//            add("Skull Hunters");
//            add("VRChat");
//        }
//    };
//
//    ArrayList<String> imgid  = new ArrayList<String>() {
//        {
//            add("https://i.imgur.com/haOd2Xb.png");
//            add("https://i.imgur.com/GMH14pc.png");
//            add("https://i.imgur.com/fvG8XKp.png");
//            add("https://i.imgur.com/wIlaCv7.png");
//            add("https://i.imgur.com/YVooqCg.png");
//            add("https://i.imgur.com/7XP2dNs.png");
//            add("https://i.imgur.com/KsvScaB.png");
//        }
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        mAuth = FirebaseAuth.getInstance();

        getUIIDs();
        initDatabase();
        adapter = new GamesAdapter(this, new ArrayList<>(), new ArrayList<>());
        list = (ListView) findViewById(R.id.lstGameList);
        list.setAdapter(adapter);

        list.setOnItemClickListener((parent, view, position, id) -> {
            String gameTitle = ((TextView) view.findViewById(R.id.title)).getText().toString();
            String iconURL = (String) ((ImageView) view.findViewById(R.id.icon)).getTag();
            Intent switchToGame = new Intent(MainActivity.this, GameActivity.class);
            switchToGame.putExtra("gameTitle", gameTitle);
            switchToGame.putExtra("icon", iconURL);

            startActivity(switchToGame);
        });
    }

    private void initDatabase() {
//        for (int i = 0; i < maintitle.size(); i++) {
//            saveGameToDB(maintitle.get(i), null, imgid.get(i));
//        }

        OnGetDataListener listener = new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                for (int i = 0; i < DAOManager.daoGame.games.size(); i++) {
                    adapter.addNew(DAOManager.daoGame.games.get(i));
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
            }
        };

        DAOManager.daoGame.init(listener);
    }

    // Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//
//        btnSignUp.setOnClickListener(l ->
//        {
//            String displayName = etDisplayName.getText().toString();
//            String email = etEmail.getText().toString();
//            String password = etPassword.getText().toString();
//
//            Game game1 = new Game("VRChat", null);
//            Game game2 = new Game("Zenith", null);
//            saveGameToDB(game1);
//            saveGameToDB(game2);
//
//            Group grp1 = new Group("VRCFanatics", "We love VRChat!", game1, 0, null);
//            Group grp2 = new Group("Zenitheans", "Zenith grind, go!", game2, 0, null);
//            saveGroupToDB(grp1);
//            saveGroupToDB(grp2);
//
//            User usr = new User("xandering@gmail.com", null);
//            saveUserToDB(usr);
//        });

    private void saveUserToDB(String email, String displayName, String icon, ArrayList<String> groups) {
        User usr = new User(email, displayName, icon, groups);
        saveUserToDB(usr);
    }

    private void saveUserToDB(User usr) {
        DAOManager.daoUser.add(usr);
    }

    private void saveGameToDB(String gameName, ArrayList<String> gameGroups, String icon) {
        Game game = new Game(gameName, gameGroups, icon);
        saveGameToDB(game);
    }

    private void saveGameToDB(Game game) {
        DAOManager.daoGame.add(game);
    }

    private void signUpUser(String displayName, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(Consts.LOGCAT_TAG, "createUserWithEmail:success");

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(displayName)
                                .build();

                        if (mAuth.getCurrentUser() != null)
                            mAuth.getCurrentUser().updateProfile(profileUpdates)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Log.d(Consts.LOGCAT_TAG, "User profile updated.");
                                        }
                                    });
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(Consts.LOGCAT_TAG, "createUserWithEmail:failure", task.getException());

                        String msg = "";
                        if (task.getException() != null)
                            msg = task.getException().getMessage();

                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(Consts.LOGCAT_TAG, "signInWithEmail:success");
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(Consts.LOGCAT_TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getUIIDs() {
        btnAccount = findViewById(R.id.btnAccount);
    }
}