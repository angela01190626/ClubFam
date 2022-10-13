package com.example.clubfam.ui.myclubs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clubfam.R;
import com.example.clubfam.ui.club.ClubFragment;
import com.example.clubfam.ui.tinder.TinderFragment;

import android.app.Fragment;

import java.util.EventListener;
import java.util.List;

public class MyClubAdapter extends RecyclerView.Adapter<MyClubAdapter.MyClubViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;
    //we are storing all the posts in a list
    private List<MyClub> clubList;


    MyClubsFragment fragment;

    //getting the context and posts list with constructor
    public MyClubAdapter(Context mCtx, List<MyClub> clubList, MyClubsFragment fragment) {
        this.mCtx = mCtx;
        this.clubList = clubList;
        this.fragment = fragment;
    }

    @Override
    public MyClubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_my_clubs, null);
        return new MyClubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyClubViewHolder holder, int position) {
        //getting the post of the specified position
        MyClub club = clubList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(club.getTitle());
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(club.getImage()));
        if(club.getLeader()) {
            // nothing to do here
        } else {
            holder.leaderButton.setVisibility(View.INVISIBLE);
        }

        final String clubId = club.getId();
        holder.leaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadClubView(clubId);
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadClubView(clubId);
            }
        });

        holder.textViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadClubView(clubId);
            }
        });

    }


    @Override
    public int getItemCount() {
        return clubList.size();
    }


    class MyClubViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        ImageView imageView;
        Button leaderButton;

        public MyClubViewHolder(final View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.manage1);
            imageView = itemView.findViewById(R.id.imageView);
            leaderButton = (Button) itemView.findViewById(R.id.leaderButton);
        }
    }

    void loadClubView(String clubId){
        this.fragment.loadClubView(clubId);

    }
}
