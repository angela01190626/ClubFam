package com.example.clubfam.ui.myclubs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.app.Fragment;
import com.example.clubfam.R;
import com.example.clubfam.ui.club.ClubFragment;
import android.app.FragmentTransaction;

public class MyClubManage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_my_clubs);

//        Button button = (Button) findViewById(R.id.leaderButton);
//        button.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                goToClubPage();
//            }
//        });
    }

//    public void goToClubPage(){
//        FragmentTransaction ft = this.getFragmentManager().beginTransaction();
//        Fragment clubf = new ClubFragment();
//        ft.replace(R.id.nav_host_fragment, clubf);
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        ft.addToBackStack(null);
//        ft.commit();
//    }
}
