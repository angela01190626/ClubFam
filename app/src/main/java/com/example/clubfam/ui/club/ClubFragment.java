package com.example.clubfam.ui.club;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.clubfam.helpers.ClubData;

import com.example.clubfam.R;
import com.example.clubfam.helpers.PostData;
import com.example.clubfam.ui.clubedit.ClubEditFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("FieldCanBeLocal")
public class ClubFragment extends Fragment {

    private static final String TEMP_INFO = "temp_info";

    private ClubViewModel clubViewModel;

    View root;
    String clubId;
    ClubData cd;

    TextView clubName;
    TextView clubDescription;
    Button editButton;

    SharedPreferences.Editor editor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences sp = getActivity().getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE);
        editor = this.getActivity().getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE).edit();


        clubViewModel =
                ViewModelProviders.of(this).get(ClubViewModel.class);
        root = inflater.inflate(R.layout.fragment_club, container, false);

        ImageView clubLogo = (ImageView) root.findViewById(R.id.clubImage);
        ImageView clubImage1 = (ImageView) root.findViewById(R.id.clubImage1);
        ImageView clubImage2 = (ImageView) root.findViewById(R.id.clubImage2);
        clubName = (TextView) root.findViewById(R.id.clubName);
        clubDescription = (TextView) root.findViewById(R.id.clubDescription);
        Button followButton = (Button) root.findViewById(R.id.followButton);
        editButton = (Button) root.findViewById(R.id.editButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadEditClub();
            }
        });

        // Get passed in arguments
        Bundle bundle = this.getArguments();
        clubId = bundle.getString("clubId");

        cd = new ClubData(getActivity());
        cd.fetchClubData();
        JSONObject club = cd.getClubFromId(clubId);

        // BUTTON SETUP
        // check if the user has joined this club
        String userClubIds = sp.getString("userClubs", "");

        // user has NOT joined this club
        if ( !userClubIds.contains(clubId) ) {
            // set button action
            followButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cd.followClub(clubId);
                    reloadFragment();
                }
            });
            followButton.setText("Follow");

        } else { // user HAS joined this club
            followButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cd.unfollowClub(clubId);
                    reloadFragment();
                }
            });
            followButton.setText("Unfollow");
        }

        try {
            clubName.setText(club.getString("name"));
            clubDescription.setText(club.getString("description"));

            int imageId = getResources().getIdentifier(club.getString("logoName"), "drawable", this.getActivity().getPackageName());
            clubLogo.setImageResource(imageId);

            String clubImages = club.getString("clubImages");
            String[] imageArray = clubImages.split(",");

            int image1 = getResources().getIdentifier(imageArray[0], "drawable", this.getActivity().getPackageName());
            int image2 = getResources().getIdentifier(imageArray[1], "drawable", this.getActivity().getPackageName());
            clubImage1.setImageResource(image1);
            clubImage2.setImageResource(image2);

        } catch (JSONException e) {
            // well shit
        }

        // If we are on the basketball page, and the user has followed it, we need to show the "Create Post" option.
        // also make the textEdit fields editable
        if( clubId.equals("1") && userClubIds.contains(clubId) ) {
            String showCreatePost = "1";
            editor.putString("showCreatePost", showCreatePost);
            editor.commit();
            this.getActivity().invalidateOptionsMenu();

            // make edit button appear
            editButton.setVisibility(View.VISIBLE);

        }

        return root;
    }

    public void loadEditClub() {
        getFragmentManager().beginTransaction()
                .replace(((ViewGroup) getView().getParent()).getId(), new ClubEditFragment())
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.getActivity().invalidateOptionsMenu(); // invalidate options menu to remove edit button
    }

    private void reloadFragment() {
        if (getFragmentManager() != null) {

            getFragmentManager()
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
        }
    }
}
