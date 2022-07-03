package com.example.gamergroups;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private ImageView icon;
    private TextView title;
    private Button btnBack;
    private Button btnCreate;
    private ListView list;
    private GroupsAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamepage);

        getUIIDs();

        title.setText(getIntent().getStringExtra("gameTitle"));
        Picasso.get().load(getIntent().getStringExtra("icon")).into(icon);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new GroupsAdapter(this, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        list = (ListView) findViewById(R.id.lstGroupList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String gameTitle = ((TextView) view.findViewById(R.id.title)).getText().toString();
//                String iconURL = (String) ((ImageView) view.findViewById(R.id.icon)).getTag();
//                Intent switchToGame = new Intent(MainActivity.this, GameActivity.class);
//                switchToGame.putExtra("gameTitle", gameTitle);
//                switchToGame.putExtra("icon", iconURL);
//
//                startActivity(switchToGame);
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Group grp = new Group("New group", "Group description", title.getText().toString(), 1,
                        new ArrayList<User>() {{
                            add(DAOManager.daoUser.CurrentUser);
                        }});

                saveGroupToDB(grp);
                adapter.addNew(grp);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void saveGroupToDB(String groupName, String groupDescription, String game, int numberOfUsers, ArrayList<User> usersInGroup) {
        Group grp = new Group(groupName, groupDescription, game, numberOfUsers, usersInGroup);
        saveGroupToDB(grp);
    }

    private void saveGroupToDB(Group grp) {
        DAOManager.daoGroup.add(grp);
    }

    private void getUIIDs() {
        btnBack = findViewById(R.id.btnBack);
        btnCreate = findViewById(R.id.btnCreate);
        icon = findViewById(R.id.icon);
        title = findViewById(R.id.title);
        list = findViewById(R.id.lstGroupList);
    }
}
