package com.example.clubfam.helpers;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;

import com.example.clubfam.R;

public class ClubData extends Data {

    FragmentActivity mCtx;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private static final String TEMP_INFO="temp_info";

    public ClubData(FragmentActivity ctx){
        this.mCtx = ctx;
        sp = this.mCtx.getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE);
        editor = this.mCtx.getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE).edit();
    }

    public JSONArray currentUserClubs() {
        JSONArray userClubs = new JSONArray();
        String userClubsString = cleanCSVString(sp.getString("userClubs", ""));
        String[] userClubsAsArray = userClubsString.split(",");

        for(int i = 0; i < userClubsAsArray.length; i++){
            String clubId = userClubsAsArray[i].trim();
            if(clubId.length() > 0) {
                userClubs.put(getClubFromId(clubId));
            }
        }

        return userClubs;
    }

    public void unfollowClub(String clubId) {

        // Get the user clubs => 1,3,2,5
        String userClubsString = sp.getString("userClubs", "");

        // Remove the club => 1,,2,5
        if(userClubsString.contains(clubId)) {
            userClubsString = userClubsString.replace(clubId, "");
        }

        userClubsString = cleanCSVString(userClubsString);

        editor.putString("userClubs", userClubsString);
        editor.commit();

    }

    public void followClub(String clubId) {

        // Check if we are in the club already or not
        // we cant do a simple if clubId in userClubsString because if you follow club 20,
        // it will say you follow club 2 so check each actual value
        String userClubsString = cleanCSVString(sp.getString("userClubs", ""));
        String[] currentClubs = userClubsString.split(",");
        boolean needToAddClub = true;
        for(int i = 0; i < currentClubs.length; i++){
            if(currentClubs[i].equals(clubId)) {
                // we already follow this club !
                needToAddClub = false;
            }
        }

        if(needToAddClub) {
            userClubsString = userClubsString + "," + clubId;
            editor.putString("userClubs", userClubsString);
            editor.commit();
        }

    }

    public boolean haveClub(String clubId){
        boolean result = false;
        String userClubsString = sp.getString("userClubs", "");
        if(userClubsString.contains(clubId)) {
            result = true;
            return result;
        }
        return result;
    }

    public JSONObject fetchClubData(){

        JSONArray clubArray = new JSONArray();


        clubArray.put(createClub(
                mCtx.getString(R.string.basketball_Id),
                "Basketball Club",
                "basketball",
                "Basketball club is a great way to get involved on campus. Fun and active!",
                "20",
                "Monday and Wednesdays 7:30 - 8:30",
                "basketball,sport,active,hoop",
                "basketballgame,tinder_basketball_1,tinder_basketball_2,tinder_basketball_3"
        ));

        clubArray.put(createClub(
                mCtx.getString(R.string.bowling_Id),
                "University Bowlers",
                "bowlingpin",
                "University Bowlers is the premier bowling club on campus. Come check us out and get on the road to 300!",
                "12",
                "Monday and Wednesdays 9:00pm - 10:00pm",
                "bowling,sport,active,pin,fun",
                "bowlingpin,bowlinggame"
        ));

        clubArray.put(createClub(
                mCtx.getString(R.string.skiing_Id),
                "Ski and Snowboarding Club",
                "ski",
                "Ski and Snowboarding Club (SSC) is a good way to meet new friends, fellow shredders, and potential lovers",
                "12",
                "Wednesdays 9:00pm - 10:00pm Moos 2-530",
                "skiing,sport,active,snow,mountain,cold,fun",
                "skingclub,ski"
        ));

        clubArray.put(createClub(
                "4",
                "Game Development Club",
                "controller",
                "Game Development Club is the U of M's most action packed club!",
                "7",
                "Tuesdays 5:00pm - 6:30pm Keller 1-250",
                "video,video games,controller,games,xbox,ps4,computer",
                "video_game_1,video_game_2"
        ));

        clubArray.put(createClub(
                "5",
                "Hiking Club",
                "hiking_logo",
                "Love the outdoors? Love getting exercise while appreciating nature? Hiking club is for you!",
                "9",
                "Every other Tuesday from 7:00pm to 8:00pm Wilson 150",
                "hike,hiking,nature,outside,outdoors,trees,walking",
                "hiking1, hiking2"
        ));

        clubArray.put(createClub(
                "6",
                "Dentistry Club",
                "dentist_logo",
                "Want to do something bigger than yourself? Have a passion for clean teeth? Join Dental Club!",
                "16",
                "Every other Monday from 3:00pm to 4:00pm Moos 2-530",
                "dentist,medical,medicine,doctor,dentist,teeth,tooth,clean,brush",
                "dentist1,dentist2"
        ));



        JSONObject clubs = new JSONObject();
        try {
            clubs.put("clubs", clubArray);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return clubs;
    }

    public JSONObject getClubFromId(String clubId) {

        JSONObject club = new JSONObject();

        try {
            club.put("clubId", clubId);
            club.put("name", sp.getString("club" + clubId + "_name", ""));
            club.put("logoName", sp.getString("club" + clubId + "_logoName", ""));
            club.put("description", sp.getString("club" + clubId + "_description", ""));
            club.put("numberMembers", sp.getString("club" + clubId + "_numberMembers", ""));
            club.put("meetingTimes", sp.getString("club" + clubId + "_meetingTimes", ""));
            club.put("tags", sp.getString("club" + clubId + "_tags", ""));
            club.put("clubImages", sp.getString("club" + clubId + "_clubImages", ""));
        } catch (JSONException e) {
            // Idk. Hope we don't get here lol
        }
        return club;
    }

    private JSONObject createClub(String clubId, String name, String logoName, String description, String numberMembers, String meetingTimes, String tags, String clubImages ){
        JSONObject club = new JSONObject();


        try {
            // Setup the shared preferences

            String name2 = sp.getString("club" + clubId + "_name", "");
            String logoName2 = sp.getString("club" + clubId + "_logoName", "");
            String description2 = sp.getString("club" + clubId + "_description", "");
            String numberMembers2 = sp.getString("club" + clubId + "_numberMembers", "");
            String meetingTimes2 = sp.getString("club" + clubId + "_meetingTimes", "");
            String tags2 = sp.getString("club" + clubId + "_tags", "");
            String clubImages2 = sp.getString("club" + clubId + "_clubImages", "");

            if(name2.equals("")) {
                // If we don't have a saved value for this, create one
                editor.putString("club" + clubId + "_name", name);
            } else {
                // We do have a saved value so use that in the response
                name = name2;
            }

            if(logoName2.equals("")) {
                editor.putString("club" + clubId + "_logoName", logoName);
            } else {
                logoName = logoName2;
            }

            if(description2.equals("")) {
                editor.putString("club" + clubId + "_description", description);
            } else {
                description = description2;
            }

            if(numberMembers2 == "") {
                editor.putString("club" + clubId + "_numberMembers", numberMembers);
            } else {
                numberMembers = numberMembers2;
            }

            if(meetingTimes2.equals("")) {
                editor.putString("club" + clubId + "_meetingTimes", meetingTimes);
            } else {
                meetingTimes = meetingTimes2;
            }

            if(tags2.equals("")) {
                editor.putString("club" + clubId + "_tags", tags);
            } else {
                tags = tags2;
            }

            if(clubImages2.equals("")) {
                editor.putString("club" + clubId + "_clubImages", clubImages);
            } else {
                clubImages = clubImages2;
            }

            editor.apply();
            editor.commit();

            club.put("clubId", clubId);
            club.put("name", name);
            club.put("logoName", logoName);
            club.put("description", description);
            club.put("numberMembers", numberMembers);
            club.put("meetingTimes", meetingTimes);
            club.put("tags", tags);
            club.put("clubImages", clubImages);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return club;
    }


}
