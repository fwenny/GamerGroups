package com.example.gamergroups;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DAOUser {
    private DatabaseReference dbRef;
    public User CurrentUser;

    public DAOUser() {
        dbRef = FirebaseDatabase.getInstance("https://gamergroupsproject-default-rtdb.europe-west1.firebasedatabase.app").getReference(User.class.getSimpleName());
    }

    public void add(User user) {
        Query queryToGetData = dbRef.child(user.getEmail());

        queryToGetData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    dbRef.child(user.getEmail()).setValue(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
