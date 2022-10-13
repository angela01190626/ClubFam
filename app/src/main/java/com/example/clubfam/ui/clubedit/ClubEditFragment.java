package com.example.clubfam.ui.clubedit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.clubfam.R;
import com.example.clubfam.helpers.ClubData;
import com.example.clubfam.ui.club.ClubFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class ClubEditFragment extends Fragment {
    private static final String TEMP_INFO = "temp_info";

    private ClubEditViewModel clubEditViewModel;
    View root;
    ClubData cd;

    EditText editClubName;
    EditText editClubDescription;
    Button editSaveButton;
    Button editCancelButton;

    SharedPreferences.Editor editor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences sp = getActivity().getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE);

        clubEditViewModel = ViewModelProviders.of(this).get(ClubEditViewModel.class);
        root = inflater.inflate(R.layout.fragment_clubedit, container, false);

        ImageView editClubLogo = (ImageView) root.findViewById(R.id.editClubImage);
        ImageView editClubImage1 = (ImageView) root.findViewById(R.id.editClubImage1);
        ImageView editClubImage2 = (ImageView) root.findViewById(R.id.editClubImage2);
        editClubName = (EditText) root.findViewById(R.id.editClubName);
        editClubDescription = (EditText) root.findViewById(R.id.editClubDescription);
        editSaveButton = (Button) root.findViewById(R.id.editSaveButton);
        editCancelButton = (Button) root.findViewById(R.id.editCancelButton);

        editSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAndLoadClub();
            }
        });

        editCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadClub();
            }
        });

        // Get basketball club object
        String clubId = "1";
        cd = new ClubData(getActivity());
        JSONObject club = cd.getClubFromId(clubId);

        // check if the user has joined this club
        String userClubIds = sp.getString("userClubs", "");

        // user has NOT joined this club
        if ( !userClubIds.contains(clubId) ) {
            loadClub(); // kick 'em out
        }

        try {
            editClubName.setText(club.getString("name"));
            editClubDescription.setText(club.getString("description"));

            int imageId = getResources().getIdentifier(club.getString("logoName"), "drawable", this.getActivity().getPackageName());
            editClubLogo.setImageResource(imageId);

            String clubImages = club.getString("clubImages");
            String[] imageArray = clubImages.split(",");

            int image1 = getResources().getIdentifier(imageArray[0], "drawable", this.getActivity().getPackageName());
            int image2 = getResources().getIdentifier(imageArray[1], "drawable", this.getActivity().getPackageName());
            editClubImage1.setImageResource(image1);
            editClubImage2.setImageResource(image2);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return root;
    }

    public void saveAndLoadClub() {
        editor = this.getActivity().getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE).edit();
        // hardcoded as basketball club, sorry
        editor.putString("club1_name", editClubName.getText().toString());
        editor.putString("club1_description", editClubDescription.getText().toString());
        editor.commit();

        ClubFragment clubFrag = new ClubFragment();
        Bundle bundle = new Bundle();
        bundle.putString("clubId", "1"); // basketball club ID
        clubFrag.setArguments(bundle);

        getFragmentManager().beginTransaction()
                .replace(((ViewGroup) getView().getParent()).getId(), clubFrag)
                .addToBackStack(null)
                .commit();
    }


    // what club will this open to?
    public void loadClub() {
        ClubFragment clubFrag = new ClubFragment();
        Bundle bundle = new Bundle();
        bundle.putString("clubId", "1"); // basketball club ID
        clubFrag.setArguments(bundle);

        getFragmentManager().beginTransaction()
                .replace(((ViewGroup) getView().getParent()).getId(), clubFrag)
                .addToBackStack(null)
                .commit();
    }

}
