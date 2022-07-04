package com.example.gamergroups.helper;

import com.example.gamergroups.data.Game;
import com.example.gamergroups.data.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

// manages connection to DB and other DB related info / classes
public class DatabaseManager {
    // reference to the DB
    private DatabaseReference dbRef;

    // list of games
    public ArrayList<Game> games;

    // current logged in user
    public User CurrentUser;

    // singleton
    public static final DatabaseManager Instance = new DatabaseManager();

    public DatabaseManager() {
        dbRef = FirebaseDatabase.getInstance(Consts.DB_REGION_STRING).getReference(Game.class.getSimpleName());
    }

    // add / update game in DB
    public void addGame(Game game) {
        Query queryToGetData = dbRef.child(game.getGameName());

        queryToGetData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dbRef.child(game.getGameName()).setValue(game);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // get game list from DB and fill the game collection
    public void initGameList(OnGetDataListener listener) {
        if (games == null)
            games = new ArrayList<>();

        games.clear();

        listener.onStart();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> itr = dataSnapshot.getChildren().iterator();

                while (itr.hasNext()) {
                    DataSnapshot dss = itr.next();
                    games.add(dss.getValue(Game.class));
                }

                listener.onSuccess(null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(null);
            }
        });
    }

    // get specific game from game list
    public Game getGameData(String key) {
        for (Game currGame : games) {
            if (currGame.getGameName().equals(key))
                return currGame;
        }

        return null;
    }
}
