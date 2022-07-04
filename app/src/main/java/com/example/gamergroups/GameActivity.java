package com.example.gamergroups;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private ImageView iv_icon;
    private TextView tv_title;
    private Button btn_back;
    private Button btn_create;
    private ListView lst_groupList;
    private GroupsAdapter adapter;
    private Intent intent;

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String groupName = data.getStringExtra("groupName");
                        String groupDesc = data.getStringExtra("groupDesc");

                        Group grp = new Group(groupName, groupDesc, tv_title.getText().toString(), 1,
                                new ArrayList<User>() {{
                                    add(DatabaseManager.Instance.CurrentUser);
                                }});

                        Game g = DatabaseManager.Instance.getGameData(tv_title.getText().toString());
                        g.getGameGroups().add(grp);
                        DatabaseManager.Instance.addGame(g);

                        adapter.addNew(grp);
                        adapter.notifyDataSetChanged();
                    }
                }
            });

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamepage);

        getUIIDs();
        intent = getIntent();

        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            btn_create.setVisibility(View.GONE);
        else
            btn_create.setVisibility(View.VISIBLE);

        tv_title.setText(intent.getStringExtra("gameTitle"));
        Picasso.get().load(intent.getStringExtra("icon")).into(iv_icon);
        adapter = new GroupsAdapter(this, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        Game g = DatabaseManager.Instance.getGameData(tv_title.getText().toString());

        for (Group CurrGroup : g.getGameGroups()) {
            adapter.addNew(CurrGroup);
        }

        lst_groupList.setAdapter(adapter);

        setListeners();
    }

    private void getUIIDs() {
        btn_back = findViewById(R.id.btn_back);
        btn_create = findViewById(R.id.btn_create);
        iv_icon = findViewById(R.id.iv_icon);
        tv_title = findViewById(R.id.tv_title);
        lst_groupList = findViewById(R.id.lst_groupList);
    }

    private void setListeners() {
        btn_back.setOnClickListener(view -> finish());

        lst_groupList.setOnItemClickListener((parent, view, position, id) -> {
            TextView tvGroupName = (TextView) view.findViewById(R.id.tv_groupName);
            TextView tvGroupDescription = (TextView) view.findViewById(R.id.tv_groupDescription);
            TextView tvNumberOfUsers = (TextView) view.findViewById(R.id.tv_numberOfUsers);
            ImageView ivIsInGroup = (ImageView) view.findViewById(R.id.iv_inGroup);

            Intent switchToGroup = new Intent(GameActivity.this, GroupActivity.class);
            switchToGroup.putExtra("groupName", tvGroupName.getText().toString());
            switchToGroup.putExtra("groupDesc", tvGroupDescription.getText().toString());
            switchToGroup.putExtra("numOfUsers", tvNumberOfUsers.getText().toString());
            switchToGroup.putExtra("isInGroup", ivIsInGroup.getVisibility() == View.VISIBLE);
            switchToGroup.putExtra("groupPos", position);
            switchToGroup.putExtra("gameName", tv_title.getText().toString());

            startActivity(switchToGroup);
        });

        btn_create.setOnClickListener(view -> {
            Intent createGroupIntent = new Intent(GameActivity.this, CreateGroupActivity.class);
            someActivityResultLauncher.launch(createGroupIntent);
        });
    }
}
