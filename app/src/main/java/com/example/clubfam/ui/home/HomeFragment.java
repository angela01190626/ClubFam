package com.example.clubfam.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubfam.R;
import com.example.clubfam.helpers.ClubData;
import com.example.clubfam.helpers.PostData;
import com.example.clubfam.ui.club.ClubFragment;
import com.example.clubfam.ui.noclubs.NoClubsFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    //a list to store all the posts
    List<Post> postList;

    //the recyclerview
    RecyclerView recyclerView;

    JSONArray userClubs;
    JSONArray userPosts;
    JSONArray allPosts;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //getting the recyclerview from xml
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        //initializing the postlist
        postList = new ArrayList<>();

        // get lists of posts
        try {
            allPosts = new PostData(getActivity()).fetchPostData(null).getJSONArray("posts");
        } catch (JSONException e) {
            Log.d("UNKNOWN ERROR", "STRING");
        }

        userPosts = new PostData(getActivity()).currentUserPosts();

        // reverse order of userPosts
        JSONArray tempUserPosts = new JSONArray();
        for (int i = userPosts.length()-1; i>=0; i--) {
            try {
                tempUserPosts.put(userPosts.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        userPosts = tempUserPosts;

        //adding some items to our list
        for(int i = 0; i < userPosts.length(); i++){
            try {
                JSONObject post = userPosts.getJSONObject(i);
                addToPostList(post);
            } catch (JSONException e) {
                Log.e("ERROR", "what happened :(", e);
            }

        }

        //creating recyclerview adapter
        PostAdapter adapter = new PostAdapter(this.getActivity(), postList, this);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
        
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // check if the user is a member of any clubs. If not, send them to noClubs page
        userClubs = new ClubData(getActivity()).currentUserClubs();

        if( userClubs.length() == 0 ) {
            loadNoClubs();
        }
    }

    public void loadClubView(String clubId) {

        ClubFragment clubFrag = new ClubFragment();
        Bundle bundle = new Bundle();
        bundle.putString("clubId", clubId);
        clubFrag.setArguments(bundle);

        getFragmentManager().beginTransaction()
                .replace(((ViewGroup) getView().getParent()).getId(), clubFrag)
                .addToBackStack(null)
                .commit();
    }

    public void loadNoClubs() {
        getFragmentManager().beginTransaction()
                .replace(((View) getView().getParent()).getId(), new NoClubsFragment())
                .addToBackStack(null)
                .commit();
    }

    private void addToPostList(JSONObject post) {
        try {
            int imageId = getResources().getIdentifier(post.getString("logoName"), "drawable", this.getActivity().getPackageName());
            int imageId2 = getResources().getIdentifier(post.getString("postImages"), "drawable", this.getActivity().getPackageName());

            postList.add(
                    new Post(
                            post.getString("clubId"),
                            post.getString("title"),
                            post.getString("body"),
                            imageId,
                            imageId2,
                            post.getString("eventTimeLocation")
                    ));
        } catch (JSONException e) {
            Log.e("ERROR", "what happened :(", e);
            e.printStackTrace();
        }
    }
}
