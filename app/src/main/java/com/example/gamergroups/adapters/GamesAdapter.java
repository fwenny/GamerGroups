package com.example.gamergroups.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamergroups.data.Game;
import com.example.gamergroups.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// used to display games in a ListView
public class GamesAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> maintitle;
    private final ArrayList<String> iconURL;

    public GamesAdapter(Activity context, ArrayList<String> maintitle, ArrayList<String> iconURL) {
        super(context, R.layout.gameselection, maintitle);

        this.context = context;
        this.maintitle = maintitle;
        this.iconURL = iconURL;

    }

    public void addNew(Game game) {
        this.maintitle.add(game.getGameName());
        this.iconURL.add(game.getIcon());
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.gameselection, null, true);

        TextView titleText = (TextView) rowView.findViewById(R.id.tv_title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.iv_icon);

        titleText.setText(maintitle.get(position));
        Picasso.get().load(iconURL.get(position)).into(imageView);
        imageView.setTag(iconURL.get(position));

        return rowView;
    }

    @Override
    public void clear() {
        super.clear();

        this.maintitle.clear();
        this.iconURL.clear();
    }
}