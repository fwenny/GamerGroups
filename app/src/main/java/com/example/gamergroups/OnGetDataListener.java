package com.example.gamergroups;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface OnGetDataListener {
    void onStart();

    void onSuccess(DataSnapshot data);

    void onFailed(DatabaseError databaseError);
}
