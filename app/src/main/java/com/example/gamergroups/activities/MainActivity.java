package com.example.gamergroups.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamergroups.R;
import com.example.gamergroups.adapters.GamesAdapter;
import com.example.gamergroups.data.User;
import com.example.gamergroups.helper.DatabaseManager;
import com.example.gamergroups.helper.OnGetDataListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

// main class, handles showing all of the games
public class MainActivity extends AppCompatActivity {
    private Button btn_login;
    private ListView lst_gameList;
    private GamesAdapter adapter;
    private FirebaseAuth fb_auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        // get current user if they are logged in and save for future use
        fb_auth = FirebaseAuth.getInstance();
        FirebaseUser fb_user = fb_auth.getCurrentUser();

        if (fb_user != null)
            DatabaseManager.Instance.CurrentUser = new User(fb_user.getEmail(), fb_user.getDisplayName(),
                    fb_user.getPhotoUrl().toString());

        getUIIDs();

        // fill the game list
        initDatabase();

        adapter = new GamesAdapter(this, new ArrayList<>(), new ArrayList<>());
        lst_gameList.setAdapter(adapter);

        setListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateLoginBtnText();
    }

    private void initDatabase() {
        // custom callback listener to update adapter once the game list is received from the DB
        OnGetDataListener gameListener = new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                // update adapter with game list
                for (int i = 0; i < DatabaseManager.Instance.games.size(); i++) {
                    adapter.addNew(DatabaseManager.Instance.games.get(i));
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
            }
        };

        // get game list from DB
        DatabaseManager.Instance.initGameList(gameListener);
    }

    private void getUIIDs() {

        btn_login = findViewById(R.id.btn_login);
        lst_gameList = findViewById(R.id.lst_gameList);
    }

    private void setListeners() {
        // choose selected game and pass it onto the next activity
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

            updateLoginBtnText();
        });
    }

    private void updateLoginBtnText(){
        if (fb_auth.getCurrentUser() != null)
            btn_login.setText("Log out");
        else
            btn_login.setText("Login");
    }
}