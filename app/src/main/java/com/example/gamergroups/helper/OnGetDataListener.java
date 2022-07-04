package com.example.gamergroups.helper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

// interface to help with callbacks
public interface OnGetDataListener {
    void onStart();

    void onSuccess(DataSnapshot data);

    void onFailed(DatabaseError databaseError);
}
