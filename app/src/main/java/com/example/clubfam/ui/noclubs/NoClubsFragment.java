package com.example.clubfam.ui.noclubs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.clubfam.R;
import com.example.clubfam.ui.explore.ExploreFragment;

public class NoClubsFragment extends Fragment {
    private NoClubsViewModel noClubsViewModel;

    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        noClubsViewModel = ViewModelProviders.of(this).get(NoClubsViewModel.class);

        root = inflater.inflate(R.layout.fragment_noclubs, container, false);

        Button exploreButton = (Button) root.findViewById(R.id.exploreButton);
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadExplorePage();
            }
        });

        return root;
    }

    public void loadExplorePage() {
        getFragmentManager().beginTransaction()
                .replace(((ViewGroup) getView().getParent()).getId(), new ExploreFragment())
                .addToBackStack(null)
                .commit();
    }
}
