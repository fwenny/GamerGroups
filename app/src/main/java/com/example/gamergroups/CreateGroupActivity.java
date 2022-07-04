package com.example.gamergroups;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreateGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.creategroup);

        EditText et_groupName = findViewById(R.id.et_groupName);
        EditText et_groupDesc = findViewById(R.id.et_groupDescription);
        Button btn_create = findViewById(R.id.btn_createGroup);

        btn_create.setOnClickListener(view -> {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("groupName", et_groupName.getText().toString());
            returnIntent.putExtra("groupDesc", et_groupDesc.getText().toString());
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });

    }
}