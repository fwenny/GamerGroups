package com.example.gamergroups;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class DatabaseManager {
    private DatabaseReference dbRef;
    public ArrayList<Game> games;
    public User CurrentUser;

    public static final DatabaseManager Instance = new DatabaseManager();

    public DatabaseManager() {
        dbRef = FirebaseDatabase.getInstance(Consts.DB_REGION_STRING).getReference(Game.class.getSimpleName());
    }

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

    public Game getGameData(String key) {
        for (Game currGame : games) {
            if (currGame.getGameName().equals(key))
                return currGame;
        }

        return null;
    }
}