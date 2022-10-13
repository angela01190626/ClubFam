package com.example.clubfam.ui.tinder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.clubfam.R;
import com.example.clubfam.ui.club.ClubFragment;
import com.example.clubfam.ui.myclubs.MyClubsFragment;
import com.example.clubfam.ui.tinder.TinderViewModel;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.clubfam.helpers.ClubData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class TinderFragment  extends Fragment {

//    private TinderViewModel tinderViewModel;
//    int[] ImageArray = new int[]{R.drawable.tinder_basketball_1, R.drawable.tinder_bowling_1, R.drawable.tinder_ski_1};
//    int[] ClubNameArray = new int[]{R.string.basketball_name,R.string.bowling_name,R.string.skiing_name };

    private ClubData cd;
    private JSONArray clubs;
    private ImageView pic;
    private TextView clubNameText;
    private TextView clubDescriptionText;
    private int currentIndex;
    private String currentClubId;
    private Random random;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        tinderViewModel =
//                ViewModelProviders.of(this).get(TinderViewModel.class);


        // Grab the layout
        View root = inflater.inflate(R.layout.fragment_tinder, container, false);
        // Define the images and text boxes
        pic = (ImageView)root.findViewById(R.id.imageView);
        clubNameText = (TextView) root.findViewById(R.id.clubName);
        clubDescriptionText = (TextView) root.findViewById(R.id.descriptionText);

        // Get all clubs
        cd = new ClubData(getActivity());
        try {
           clubs = cd.fetchClubData().getJSONArray("clubs");
        } catch (JSONException e) {
           Log.d("Shit", "STRING");
        }

        // Set up a random number picker
        random = new Random();

        // Grab a random club
        currentIndex = random.nextInt(clubs.length());

        // Setup the view to match the current club
        setCurrentClub(currentIndex);

        // Add button handlers
        Button moreInfoBtn = (Button) root.findViewById(R.id.moreInfoButton);
        moreInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadClubPage();
            }
        });
        Button next = (Button) root.findViewById(R.id.Next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadNewClub();
            }
        });
        return root;
    }

    private void setCurrentClub(int index){
        try {
            JSONObject currentClub = clubs.getJSONObject(index);

            // Set currentClubId and index (index is used so we don't show the same club twice in a row)
            currentClubId = cd.getAttribute(currentClub, "clubId");
            currentIndex = index;

            // Update the text views and images
            String clubName = cd.getAttribute(currentClub, "name");
            String clubDescription = cd.getAttribute(currentClub, "description");
            String clubImage = cd.getAttribute(currentClub, "logoName");
            int imageId = getResources().getIdentifier(clubImage, "drawable", this.getActivity().getPackageName());
            pic.setImageResource(imageId);
            clubNameText.setText(clubName);
            clubDescriptionText.setText(clubDescription);

        } catch (JSONException e) {
            Log.d("Shit", "String");
        }
    }

    private void loadClubPage() {

        ClubFragment clubFrag = new ClubFragment();
        Bundle bundle = new Bundle();
        bundle.putString("clubId", currentClubId);
        clubFrag.setArguments(bundle);

        getFragmentManager().beginTransaction()
                .replace(((ViewGroup) getView().getParent()).getId(), clubFrag)
                .addToBackStack(null)
                .commit();
    }

    private void loadNewClub() {
        int tmp = currentIndex;
        while(tmp == currentIndex) {
            tmp = random.nextInt(clubs.length());
        }
        currentIndex = tmp;
        setCurrentClub(currentIndex);
    }
}
