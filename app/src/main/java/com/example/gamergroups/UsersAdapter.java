package com.example.gamergroups;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> userNames;
    private final ArrayList<String> userIcons;
    private final ArrayList<String> userEmails;

    public UsersAdapter(Activity context, ArrayList<String> userNames, ArrayList<String> userIcons, ArrayList<String> userEmails) {
        super(context, R.layout.gameselection, userNames);

        this.context = context;
        this.userNames = userNames;
        this.userIcons = userIcons;
        this.userEmails = userEmails;
    }

    public void addNew(User user) {
        this.userNames.add(user.getDisplayName());
        this.userIcons.add(user.getIconURL());
        this.userEmails.add(user.getEmail());
    }

    public void remove(String email) {
        int pos = userEmails.indexOf(email);
        this.userNames.remove(pos);
        this.userIcons.remove(pos);
        this.userEmails.remove(pos);
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.userselection, null, true);

        TextView titleText = (TextView) rowView.findViewById(R.id.tv_userName);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.iv_icon);

        titleText.setText(userNames.get(position));
        Picasso.get().load(userIcons.get(position)).into(imageView);
        imageView.setTag(userIcons.get(position));

        return rowView;
    }
}