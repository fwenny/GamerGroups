package com.example.gamergroups;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DAOGroup {
    private DatabaseReference dbRef;

    public DAOGroup() {
        dbRef = FirebaseDatabase.getInstance("https://gamergroupsproject-default-rtdb.europe-west1.firebasedatabase.app").getReference(Group.class.getSimpleName());
    }

    public void add(Group group) {
        Query queryToGetData = dbRef.child(group.getGroupName());

        queryToGetData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    dbRef.child(group.getGroupName()).setValue(group);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
