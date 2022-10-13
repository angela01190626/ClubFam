package com.example.clubfam.ui.myclubs;

public class MyClub {
    private String id;
    private String title;
    private int image;
    private boolean leader;

    public MyClub(String id, String title, int image, boolean leader) {
        // id here is the id of the club, not an id of the post
        this.id = id;
        this.title = title;
        this.image = image;
        this.leader = leader;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean getLeader() {
        return leader;
    }

    public int getImage() {
        return image;
    }
}
