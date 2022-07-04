package com.example.gamergroups.activities;

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

import com.example.gamergroups.R;
import com.example.gamergroups.adapters.GroupsAdapter;
import com.example.gamergroups.data.Game;
import com.example.gamergroups.data.Group;
import com.example.gamergroups.data.User;
import com.example.gamergroups.helper.DatabaseManager;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// used to display a specific game and all its groups
public class GameActivity extends AppCompatActivity {
    private ImageView iv_icon;
    private TextView tv_title;
    private Button btn_back;
    private Button btn_create;
    private ListView lst_groupList;

    // adapter used to display group info
    private GroupsAdapter adapter;

    // intent to grab game info from previous activity
    private Intent intent;

    // used to grab info of newly created groups from CreateGroupActivity
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // get group info
                        Intent data = result.getData();
                        String groupName = data.getStringExtra("groupName");
                        String groupDesc = data.getStringExtra("groupDesc");

                        // create new group with current user in it
                        Group grp = new Group(groupName, groupDesc, tv_title.getText().toString(), 1,
                                new ArrayList<User>() {{
                                    add(DatabaseManager.Instance.CurrentUser);
                                }});

                        // add group to game and save it in DB
                        Game g = DatabaseManager.Instance.getGameData(tv_title.getText().toString());
                        g.getGameGroups().add(grp);
                        DatabaseManager.Instance.addGame(g);

                        // update adapter
                        adapter.addNew(grp);
                        adapter.notifyDataSetChanged();
                    }
                }
            });

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamepage);

        getUIIDs();

        // get info of current selected game
        intent = getIntent();

        // only authenticated users can create groups
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            btn_create.setVisibility(View.GONE);
        else
            btn_create.setVisibility(View.VISIBLE);

        tv_title.setText(intent.getStringExtra("gameTitle"));
        Picasso.get().load(intent.getStringExtra("icon")).into(iv_icon);

        setListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Game g = DatabaseManager.Instance.getGameData(intent.getStringExtra("gameTitle"));

        if (adapter == null) {
            adapter = new GroupsAdapter(this, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

            // get current game and fill the adapter with groups

            for (Group CurrGroup : g.getGameGroups()) {
                adapter.addNew(CurrGroup);
            }

            lst_groupList.setAdapter(adapter);
        } else {
            adapter.clear();

            for (Group CurrGroup : g.getGameGroups()) {
                adapter.addNew(CurrGroup);
            }

            adapter.notifyDataSetChanged();
        }
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

            // used to get info of selected group
            switchToGroup.putExtra("groupPos", position);

            // used to get the current selected game so we can access all groups
            switchToGroup.putExtra("gameName", tv_title.getText().toString());

            startActivity(switchToGroup);
        });

        btn_create.setOnClickListener(view -> {
            // launches an intent to create a new group
            Intent createGroupIntent = new Intent(GameActivity.this, CreateGroupActivity.class);
            someActivityResultLauncher.launch(createGroupIntent);
        });
    }
}
