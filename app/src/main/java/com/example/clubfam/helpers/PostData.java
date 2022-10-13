package com.example.clubfam.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;

import com.example.clubfam.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PostData extends Data {

    FragmentActivity mCtx;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    ClubData clubData;
    JSONArray postArray = new JSONArray();
    String postArrayString;

    private static final String TEMP_INFO="temp_info";

    public PostData(FragmentActivity ctx) {
        this.mCtx = ctx;
        sp = this.mCtx.getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE);
        editor = this.mCtx.getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE).edit();
        clubData = new ClubData(ctx);   // clubData instance used to pull in club information without
                                        // rewriting it. Changes to club information in ClubData class
                                        // will be reflected automatically in PostData.
        clubData.fetchClubData(); // load the demo clubs
    }


    public JSONArray currentUserPosts() {
        JSONArray userPosts = new JSONArray();
        String userPostsString = cleanCSVString(sp.getString("userPosts", ""));
        String[] userPostsAsArray = userPostsString.split(",");

        for(int i = 0; i < userPostsAsArray.length; i++){
            String postId = userPostsAsArray[i].trim();
            if(postId.length() > 0) {
                userPosts.put(getPostFromId(postId));
            }
        }

        return userPosts;
    }

    public void addPost (String title, String body, String eventTimeLocation ) {
        String postImages = "newevent";
        String clubId = "1";
        String logoName = "basketball";

        // get current size of postArray
        JSONArray postArray = new JSONArray();
        String postArrayString = sp.getString("postArray", "");

        if( !postArrayString.equals("")) {
            try {
                postArray = new JSONArray(postArrayString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        int num_posts = postArray.length();

        JSONObject postObject = createPost(Integer.toString(num_posts), clubId, title, logoName, body, postImages, eventTimeLocation);
        fetchPostData(postObject);    // will result in post being added to sharedpreferences postarray
    }

    public void setUserPosts(JSONArray postArray) {
        // adds all posts where
        //      post.clubId is found in sp.userClubs
        //      to sp.userPosts

        String userClubIds = sp.getString("userClubs", "");

        String userPostsString = "";

        for( int i = 0; i < postArray.length(); i++ ) {
            // if the users clubs contain any of the same IDs as
            // the club IDs in the post array, add that POST ID to return value

            String clubId = this.getAttribute(this.getArrayElement(postArray, i), "clubId");
            String postId = this.getAttribute(this.getArrayElement(postArray, i), "postId");

            if (userClubIds.contains(clubId)) {
                userPostsString = userPostsString + "," + postId;
            }

        }

        editor.putString("userPosts", userPostsString);
        editor.commit();
    }

    public JSONObject fetchPostData(JSONObject newPost) {
        // please reuse any elements defined in the ClubData
        //
        // New posts can be created easily. If an element is not needed, just enter N/A. In the final implementation,
        // fields like eventTimeLocation will be displayed in a standard spot on the post.
        // The display order of the posts is based on the order of "postArray.put()" calls below.

        JSONObject basketball_club = clubData.getClubFromId(mCtx.getString(R.string.basketball_Id));
        JSONObject bowling_club = clubData.getClubFromId(mCtx.getString(R.string.bowling_Id));
        JSONObject skiing_club = clubData.getClubFromId(mCtx.getString(R.string.skiing_Id));
        JSONObject game_club = clubData.getClubFromId("4");
        JSONObject hiking_club = clubData.getClubFromId("5");
        JSONObject dentist_club = clubData.getClubFromId("6");

        // Currently, the setUserPosts method requires(?) that all of these fields be strings in order to be read back out. That will get annoying
        // for eventTime, so that will need to be reworked... // # TODO
        JSONObject basketball_upcoming_game = createPost(
                "1",
                clubData.getAttribute(basketball_club, "clubId"),
                "Pizza Social",
                clubData.getAttribute(basketball_club, "logoName"),
                "Join us December 1st for a pizza social at the Rec! Food is FREE to all club members! ",
                "pizzaparty",
                "6 p.m. on Wednesday at the Rec"
        );

        JSONObject bowling_regular_meeting = createPost(
                "2",
                clubData.getAttribute(bowling_club, "clubId"),
                "Meeting Reminder",
                clubData.getAttribute(bowling_club, "logoName"),
                "Don't forget about our regularly scheduled meeting! Be there or be square.",
                "reminderfinger",
                clubData.getAttribute(bowling_club, "meetingTimes")
        );

        JSONObject skiing_late_night = createPost(
                "3",
                clubData.getAttribute(skiing_club, "clubId"),
                clubData.getAttribute(skiing_club, "name"),
                clubData.getAttribute(skiing_club, "logoName"),
                "Late night skiing will be starting soon! Stay tuned for details on our first outing.",
                "skingclub",
                "tbd"
        );

        JSONObject bowling_upcoming_game = createPost(
                "4",
                clubData.getAttribute(bowling_club, "clubId"),
                "Open Bowling",
                clubData.getAttribute(bowling_club, "logoName"),
                "The bowling club is bussing club members to Brunswick for their Thursday night bowling special. See you down at the lanes!",
                "bowlinggame",
                "November 28th at 5 p.m, Brunswick Zone"
        );

        JSONObject game_event = createPost(
                "5",
                clubData.getAttribute(game_club, "clubId"),
                "Super Smash Bros Tournament",
                clubData.getAttribute(game_club, "logoName"),
                "Game Development is taking a break from coding to play some video games! Come join us",
                "video_game_1",
                "November 30th at 8 p.m, Coffman 330"
        );

        JSONObject hiking_event = createPost(
                "6",
                clubData.getAttribute(hiking_club, "clubId"),
                "Equipment Swap",
                clubData.getAttribute(hiking_club, "logoName"),
                "Need some new boots or perhaps a new backpack? Come to our equipment swap to get some new merch!",
                "hiking1",
                "November 23rd at 2p.m, outside coffman"
        );

        JSONObject dentist_event = createPost(
                "7",
                clubData.getAttribute(dentist_club, "clubId"),
                "Downtown Dental Clinic",
                clubData.getAttribute(dentist_club, "logoName"),
                "The dentistry club will be going downtown and giving free teeth cleanings.",
                "dentist1",
                "December 20th at 9am, Outside US Bank Stadium"
        );

        // get previous postArray
        postArrayString = sp.getString("postArray", "");

        if( postArrayString.equals("")) { // if this happens, postArray has not been initialized. Add all hardcoded posts, as well as the input (if not null)
            postArray = new JSONArray();

            postArray.put(basketball_upcoming_game);
            postArray.put(bowling_regular_meeting);
            postArray.put(skiing_late_night);
            postArray.put(bowling_upcoming_game);
            postArray.put(game_event);
            postArray.put(dentist_event);

            if( newPost != null ) {
                postArray.put(newPost);
            }
        } else {
            // if we get here, that means the hardcoded posts have already been put into the postArray
            try {
                postArray = new JSONArray(postArrayString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // add input post to array, if possible
            if( newPost != null ) {
                postArray.put(newPost);
            }

        }

        // save current postArray
        editor.putString("postArray", postArray.toString());
        editor.commit();

        // make return object
        JSONObject posts = new JSONObject();
        try {
            posts.put("posts", postArray);
        } catch (JSONException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        // set posts visible to user
        setUserPosts(postArray);

        return posts;
    }

    public JSONObject getPostFromId(String postId) {

        JSONObject post = new JSONObject();

        try {
            post.put("postId", postId);
            post.put("clubId", sp.getString("post" + postId + "_clubId", ""));
            post.put("title", sp.getString("post" + postId + "_title", ""));
            post.put("logoName", sp.getString("post" + postId + "_logoName", ""));
            post.put("body", sp.getString("post" + postId + "_body", ""));
            post.put("postImages", sp.getString("post" + postId + "_postImages", ""));
            post.put("eventTimeLocation", sp.getString("post" + postId + "_eventTimeLocation", ""));
        } catch (JSONException e) {
            // Idk. Hope we don't get here lol
        }
        return post;
    }

    private JSONObject createPost(String postId, String clubId, String title, String logoName, String body, String postImages, String eventTimeLocation ){
        JSONObject post = new JSONObject();


        try {
            // Setup the shared preferences

            String clubId2 = sp.getString("post" + postId + "_clubId", "");
            String title2 = sp.getString("post" + postId + "_title", "");
            String logoName2 = sp.getString("post" + postId + "_logoName", "");
            String body2 = sp.getString("post" + postId + "_body", "");
            String postImages2 = sp.getString("post" + postId + "_postImages", "");
            String eventTimeLocation2 = sp.getString("post" + postId + "_eventTimeLocation", "");

            if(clubId2.equals("")) {
                editor.putString("post" + postId + "_clubId", clubId);
            } else {
                clubId = clubId2;
            }
            if(title2.equals("")) {
                // If we don't have a saved value for this, create one
                editor.putString("post" + postId + "_title", title);
            } else {
                // We do have a saved value so use that in the response
                title = title2;
            }

            if(logoName2.equals("")) {
                editor.putString("post" + postId + "_logoName", logoName);
            } else {
                logoName = logoName2;
            }

            if(body2.equals("")) {
                editor.putString("post" + postId + "_body", body);
            } else {
                body = body2;
            }

            if(postImages2.equals("")) {
                editor.putString("post" + postId + "_postImages", postImages);
            } else {
                postImages = postImages2;
            }

            if(eventTimeLocation2.equals("")) {
                editor.putString("post" + postId + "_eventTimeLocation", eventTimeLocation);
            } else {
                eventTimeLocation = eventTimeLocation2;
            }

            editor.apply();
            editor.commit();

            post.put("postId", postId);
            post.put("clubId", clubId);
            post.put("title", title);
            post.put("logoName", logoName);
            post.put("body", body);
            post.put("postImages", postImages);
            post.put("eventTimeLocation", eventTimeLocation);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return post;
    }



}
