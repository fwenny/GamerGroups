package com.example.gamergroups;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GroupsAdapter extends ArrayAdapter<String> {

    private Activity context;
    private ArrayList<String> groupName;
    private ArrayList<String> groupDescription;
    private ArrayList<String> numberOfUsers;
    private ArrayList<Boolean> isInGroup;

    public GroupsAdapter(Activity context, ArrayList<String> groupName, ArrayList<String> groupDescription, ArrayList<String> numberOfUsers, ArrayList<Boolean> isInGroup) {
        super(context, R.layout.groupselection, groupName);

        this.context = context;
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.numberOfUsers = numberOfUsers;
        this.isInGroup = isInGroup;
    }

    public void addNew(Group grp) {
        this.groupName.add(grp.getGroupName());
        this.groupDescription.add(grp.getGroupDescription());
        this.numberOfUsers.add(String.valueOf(grp.getNumberOfUsers()));
        this.isInGroup.add(true);
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.groupselection, null, true);

        TextView tvGroupName = (TextView) rowView.findViewById(R.id.tv_groupName);
        TextView tvGroupDescription = (TextView) rowView.findViewById(R.id.tv_groupDescription);
        TextView tvNumberOfUsers = (TextView) rowView.findViewById(R.id.tv_numberOfUsers);
        ImageView ivIsInGroup = (ImageView) rowView.findViewById(R.id.iv_inGroup);

        tvGroupName.setText(groupName.get(position));
        tvGroupDescription.setText(groupDescription.get(position));
        tvNumberOfUsers.setText("# of users: " + numberOfUsers.get(position));
        ivIsInGroup.setVisibility(isInGroup.get(position) ? View.VISIBLE : View.INVISIBLE);

        return rowView;
    }
}