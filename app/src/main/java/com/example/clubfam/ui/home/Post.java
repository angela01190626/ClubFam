package com.example.clubfam.ui.home;

public class Post {
    private String clubId;
    private String title;
    private String shortdesc;
    private int image;
    private int image1;
    private String eventTimeLocation;

    public Post(String clubId, String title, String shortdesc, int image,int image1, String eventTimeLocation) {
        this.clubId = clubId;
        this.title = title;
        this.shortdesc = shortdesc;
        this.image = image;
        this.image1 = image1;
        this.eventTimeLocation = eventTimeLocation;

    }

    public String getClubId() { return clubId; }

    public String getTitle() {
        return title;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public int getImage() {
        return image;
    }

    public int getImage1() {
        return image1;
    }

    public String getEventTimeLocation () {
        return eventTimeLocation;
    }
}
