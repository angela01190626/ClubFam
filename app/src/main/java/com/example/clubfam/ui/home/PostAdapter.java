package com.example.clubfam.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.clubfam.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the posts in a list
    private List<Post> postList;

    private HomeFragment homeFragment;

    //getting the context and posts list with constructor
    public PostAdapter(Context mCtx, List<Post> postList, HomeFragment homeFragment) {
        this.mCtx = mCtx;
        this.postList = postList;
        this.homeFragment = homeFragment;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_posts, null);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, final int position) {
        //getting the post of the specified position
        Post post = postList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(post.getTitle());
        holder.textViewShortDesc.setText(post.getShortdesc());
        holder.textViewTimeLocation.setText(post.getEventTimeLocation());
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(post.getImage()));
        holder.imageView1.setImageDrawable(mCtx.getResources().getDrawable(post.getImage1()));
        final String clubId = post.getClubId();
		holder.textViewTitle.setOnClickListener(new View.OnClickListener() {
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
        holder.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				loadClubView(clubId);
            }
        });
    }


    @Override
    public int getItemCount() {
        return postList.size();
    }


    class PostViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewTimeLocation;
        ImageView imageView,imageView1;

        public PostViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewTitle2);
            imageView = itemView.findViewById(R.id.imageView);
            imageView1 = itemView.findViewById(R.id.image2);
            textViewTimeLocation = itemView.findViewById(R.id.textViewTimeLocation);
        }
    }

    void loadClubView(String clubId) {
        this.homeFragment.loadClubView(clubId);
    }
}

