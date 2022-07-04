package com.example.gamergroups;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GroupActivity extends AppCompatActivity {
    private TextView tv_groupName;
    private TextView tv_groupDescription;
    private TextView tv_numOfUsers;
    private ImageView iv_isInGroup;
    private ListView lst_userList;
    private Button btn_joinGroup;
    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grouppage);

        getUIIDs();

        tv_groupName.setText(getIntent().getStringExtra("groupName"));
        tv_groupDescription.setText(getIntent().getStringExtra("groupDesc"));
        tv_numOfUsers.setText(getIntent().getStringExtra("numOfUsers"));
        iv_isInGroup.setImageResource(R.drawable.dr_correct);

        if (getIntent().getBooleanExtra("isInGroup", false)) {
            iv_isInGroup.setVisibility(View.VISIBLE);
        } else
            iv_isInGroup.setVisibility(View.INVISIBLE);

        setListeners();
    }

    private void getUIIDs() {
        tv_groupName = findViewById(R.id.tv_groupName);
        tv_groupDescription = findViewById(R.id.tv_groupDescription);
        tv_numOfUsers = findViewById(R.id.tv_numberOfUsers);
        iv_isInGroup = findViewById(R.id.iv_inGroup);
        lst_userList = findViewById(R.id.lst_userList);
        btn_joinGroup = findViewById(R.id.btn_joinGroup);
        btn_back = findViewById(R.id.btn_back);
    }

    private void setListeners() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}