package com.example.gamergroups;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button btn_login;
    private ListView lst_gameList;
    private GamesAdapter adapter;
    private FirebaseAuth fb_auth;

    ArrayList<String> maintitle = new ArrayList<String>() {
        {
            add("Astroneer");
            add("Bloons TD");
            add("Duck Hunt");
            add("Idle Game");
            add("My life in Portia");
            add("Skull Hunters");
            add("VRChat");
        }
    };

    ArrayList<String> imgid = new ArrayList<String>() {
        {
            add("https://i.imgur.com/haOd2Xb.png");
            add("https://i.imgur.com/GMH14pc.png");
            add("https://i.imgur.com/fvG8XKp.png");
            add("https://i.imgur.com/wIlaCv7.png");
            add("https://i.imgur.com/YVooqCg.png");
            add("https://i.imgur.com/7XP2dNs.png");
            add("https://i.imgur.com/KsvScaB.png");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        fb_auth = FirebaseAuth.getInstance();
        FirebaseUser fb_user = fb_auth.getCurrentUser();
        DatabaseManager.Instance.CurrentUser = new User(fb_user.getEmail(), fb_user.getDisplayName(),
                fb_user.getPhotoUrl().toString());

        getUIIDs();
        initDatabase();

        adapter = new GamesAdapter(this, new ArrayList<>(), new ArrayList<>());
        lst_gameList.setAdapter(adapter);

        setListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (fb_auth.getCurrentUser() != null)
            btn_login.setText("Log out");
        else
            btn_login.setText("Login");
    }

    private void initDatabase() {
//        for (int i = 0; i < maintitle.size(); i++) {
//            saveGameToDB(maintitle.get(i), new ArrayList<>(), imgid.get(i));
//        }

        OnGetDataListener gameListener = new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                for (int i = 0; i < DatabaseManager.Instance.games.size(); i++) {
                    adapter.addNew(DatabaseManager.Instance.games.get(i));
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
            }
        };

        DatabaseManager.Instance.initGameList(gameListener);
    }

    private void getUIIDs() {

        btn_login = findViewById(R.id.btn_login);
        lst_gameList = findViewById(R.id.lst_gameList);
    }

    private void setListeners() {
        lst_gameList.setOnItemClickListener((parent, view, position, id) -> {
            String gameTitle = ((TextView) view.findViewById(R.id.tv_title)).getText().toString();
            String iconURL = (String) ((ImageView) view.findViewById(R.id.iv_icon)).getTag();
            Intent switchToGame = new Intent(MainActivity.this, GameActivity.class);
            switchToGame.putExtra("gameTitle", gameTitle);
            switchToGame.putExtra("icon", iconURL);

            startActivity(switchToGame);
        });


        btn_login.setOnClickListener(view ->
        {
            if (fb_auth.getCurrentUser() != null) {
                fb_auth.signOut();
            } else {
                Intent switchToLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(switchToLogin);
            }
        });
    }
}