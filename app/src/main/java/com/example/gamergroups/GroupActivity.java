package com.example.gamergroups;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity {
    private TextView tv_groupName;
    private TextView tv_groupDescription;
    private TextView tv_numOfUsers;
    private ImageView iv_isInGroup;
    private ListView lst_userList;
    private Button btn_joinGroup;
    private Button btn_back;
    private UsersAdapter adapter;
    private boolean isInGroup;
    private int groupPos;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grouppage);

        getUIIDs();

        intent = getIntent();
        tv_groupName.setText(intent.getStringExtra("groupName"));
        tv_groupDescription.setText(intent.getStringExtra("groupDesc"));
        tv_numOfUsers.setText(intent.getStringExtra("numOfUsers"));
        iv_isInGroup.setImageResource(R.drawable.dr_correct);
        isInGroup = intent.getBooleanExtra("isInGroup", false);

        if (isInGroup) {
            iv_isInGroup.setVisibility(View.VISIBLE);
            btn_joinGroup.setText("Leave group");
        } else {
            iv_isInGroup.setVisibility(View.INVISIBLE);
            btn_joinGroup.setText("Join group");
        }

        adapter = new UsersAdapter(this, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        groupPos = intent.getIntExtra("groupPos", 0);
        Game g = DatabaseManager.Instance.getGameData(intent.getStringExtra("gameName"));

        for (User CurrUser : g.getGameGroups().get(groupPos).getUsersInGroup()) {
            adapter.addNew(CurrUser);
        }

        lst_userList.setAdapter(adapter);

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
        btn_back.setOnClickListener(view -> finish());

        btn_joinGroup.setOnClickListener(view -> {
            isInGroup = !isInGroup;
            Game g = DatabaseManager.Instance.getGameData(intent.getStringExtra("gameName"));

            if (isInGroup) {
                g.getGameGroups().get(groupPos).addUser(DatabaseManager.Instance.CurrentUser);
                adapter.addNew(DatabaseManager.Instance.CurrentUser);

                iv_isInGroup.setVisibility(View.VISIBLE);
                btn_joinGroup.setText("Leave group");
            } else {
                g.getGameGroups().get(groupPos).removeUser(DatabaseManager.Instance.CurrentUser);
                adapter.remove(DatabaseManager.Instance.CurrentUser.getEmail());

                iv_isInGroup.setVisibility(View.INVISIBLE);
                btn_joinGroup.setText("Join group");
            }

            tv_numOfUsers.setText(String.valueOf(g.getGameGroups().get(groupPos).getNumberOfUsers()));
            DatabaseManager.Instance.addGame(g);
            adapter.notifyDataSetChanged();
        });
    }
}