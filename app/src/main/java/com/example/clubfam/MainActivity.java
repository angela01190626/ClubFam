package com.example.clubfam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.clubfam.ui.contactdevelopers.ContactDevelopersActivity;
import com.example.clubfam.ui.home.PostActivity;
import com.example.clubfam.ui.preferences.PreferencesActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity {

    private static final String TEMP_INFO="temp_info";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_calendar, R.id.navigation_explore,
                R.id.navigation_myclubs, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        getMenuInflater().inflate(R.menu.right_menu, menu);

        // set visibility of createPost menu item to the value of the boolean flag
        editor = this.getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE).edit();
        sp = this.getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE);

        // initialize menu item
        MenuItem createPostItem = menu.findItem(R.id.action_post);

        // showCreatePost flag. "" = never stored, "0" = do not show, "1" = show
        String showCreatePost = sp.getString("showCreatePost", "");

        if( showCreatePost.equals("1") ) { // flag was set to 1 on basketball page, then this function was called.
            createPostItem.setVisible(true);
        } else { // value was not stored, or was stored as 0
            createPostItem.setVisible(false);
        }

        // We always want it false when leaving this function.
        showCreatePost = "0";

        editor.putString("showCreatePost", showCreatePost);

        editor.commit();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.action_preferences:
                Intent preferencesIntent = new Intent(this, PreferencesActivity.class);
                startActivity(preferencesIntent);
                break;
            case R.id.action_contact:
                Intent contactIntent = new Intent(this, ContactDevelopersActivity.class);
                startActivity(contactIntent);
                break;
            case R.id.action_post:
                Intent postIntent = new Intent(this, PostActivity.class);
                startActivity(postIntent);
                break;
            default:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
