package com.example.clubfam.ui.myclubs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubfam.R;
import com.example.clubfam.ui.club.ClubFragment;

import com.example.clubfam.helpers.ClubData;
import com.example.clubfam.ui.noclubs.NoClubsFragment;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("FieldCanBeLocal")
public class MyClubsFragment extends Fragment {

    //a list to store all the posts
    List<MyClub> clubList;

    //the recyclerview
    RecyclerView recyclerView;

    JSONArray userClubs;
    JSONArray allClubs;

    boolean userHasNoClubs = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // setup the root for grabbing elements
        View root = inflater.inflate(R.layout.fragment_myclubs, container, false);

        //getting the recyclerview from xml
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        //initializing the postlist
        clubList = new ArrayList<>();

        // get lists of clubs
        try {
            allClubs = new ClubData(getActivity()).fetchClubData().getJSONArray("clubs");
        } catch (JSONException e) {
            Log.d("UNKNOWN ERROR", "STRING");
        }

        userClubs = new ClubData(getActivity()).currentUserClubs();

         // Get passed in arguments
        Bundle bundle = this.getArguments();

        if(bundle != null){
            String searchQuery = bundle.getString("query");
            for(int i = 0; i < allClubs.length(); i++){
                try {
                    JSONObject club = allClubs.getJSONObject(i);
                    String clubSearchTerms = club.getString("tags");

                    Log.d("searchTerms =" + clubSearchTerms, "String");
                    Log.d("searchQuery =" + searchQuery, "String");

                    if (clubSearchTerms.contains(searchQuery)) {
                        Log.d("in contains!", "String");
                        addToClubList(club);
                    } else {
                        Log.d("not in contains", "String");
                    }

                } catch (JSONException e) {
                    Log.e("ERROR", "what happened :(", e);
                }
            }
        } else {
            // bundle is null. Got here from bottom nav bar so load MyClubs
            //adding some items to our list

            for(int i = 0; i < userClubs.length(); i++){
                try {
                    JSONObject club = userClubs.getJSONObject(i);
                    addToClubList(club);
                } catch (JSONException e) {
                    Log.e("ERROR", "what happened :(", e);
                }
            }

            // if we are not doing a search, and the user is not following any clubs, send them to Explore
            if( userClubs.length() == 0 ) {
                userHasNoClubs = true;
            }
        }

        // Now that the clubList has been set, show the results!

        //creating recyclerview adapter
        MyClubAdapter adapter = new MyClubAdapter(this.getActivity(), clubList, this);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

        return root;
    }

//     If there is a "no clubs" text on the MyClubs page, feel free to delete this.
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // check if the user is a member of any clubs. If not, send them to noClubs page
        userClubs = new ClubData(getActivity()).currentUserClubs();

        if( userHasNoClubs ) {
            loadNoClubsPage();
            userHasNoClubs = false; // reset immediately.
        }
    }

    public void loadNoClubsPage() {
        getFragmentManager().beginTransaction()
                .replace(((View) getView().getParent()).getId(), new NoClubsFragment())
                .addToBackStack(null)
                .commit();
    }

    // DO NOT MAKE THIS PRIVATE !
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

    private void addToClubList(JSONObject club) {
        try {
            int imageId = getResources().getIdentifier(club.getString("logoName"), "drawable", this.getActivity().getPackageName());

            // TODO: this currently just uses leader: false, have some logic here to get that
            clubList.add(
                    new MyClub(
                            club.getString("clubId"),
                            club.getString("name"),
                            imageId,
//                            (club.getString("clubId").equals("1"))
                            false
                    ));
        } catch (JSONException e) {
            Log.e("ERROR", "what happened :(", e);
        }
    }
}
