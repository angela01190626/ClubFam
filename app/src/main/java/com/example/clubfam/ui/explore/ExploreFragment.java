package com.example.clubfam.ui.explore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.widget.Button;
import com.example.clubfam.R;
import com.example.clubfam.ui.myclubs.MyClubsFragment;
import com.example.clubfam.ui.tinder.TinderFragment;
import com.example.clubfam.helpers.ClubData;

import android.util.Log;
import android.widget.EditText;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

@SuppressWarnings("FieldCanBeLocal")
public class ExploreFragment extends Fragment {

    private ExploreViewModel exploreViewModel;


    View root;
    EditText searchField;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        exploreViewModel =
                ViewModelProviders.of(this).get(ExploreViewModel.class);

        root = inflater.inflate(R.layout.fragment_explore, container, false);

        searchField = (EditText) root.findViewById(R.id.searchText);
        Button tinderBtn = (Button) root.findViewById(R.id.tinderButton);
        tinderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTinder();
            }
        });

        Button searchBtn = (Button) root.findViewById(R.id.searchButton);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMyClubs();
            }
        });
        return root;
    }

    public void loadTinder() {
        getFragmentManager().beginTransaction()
                .replace(((ViewGroup) getView().getParent()).getId(), new TinderFragment())
                .addToBackStack(null)
                .commit();
    }

    public void loadMyClubs() {
        String message = searchField.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("query", message);

        MyClubsFragment newFrag = new MyClubsFragment();
        newFrag.setArguments(bundle);


        getFragmentManager().beginTransaction()
                .replace(((ViewGroup) getView().getParent()).getId(), newFrag )
                .addToBackStack(null)
                .commit();
    }

}