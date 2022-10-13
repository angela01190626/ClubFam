package com.example.clubfam.helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// Parent class of ClubData and PostData. Contains nonspecific methods, few as they are.

public class Data {
    public String cleanCSVString(String stringToClean) {
        // this function removes double commas and starting/ending commas if appropriate
        // Remove double commas => 1,2,5
        String cleanedString = stringToClean;

        while(cleanedString.contains(",,")) {
            cleanedString = cleanedString.replace(",,", ",");
        }

        // Remove starting , if neccesary
        if (cleanedString.length() > 0 &&cleanedString.charAt(0) == ',') {
            cleanedString = cleanedString.substring(1, cleanedString.length());
        }

        // Remove ending comma if neccesary
        if (cleanedString.length() > 0 && cleanedString.charAt(cleanedString.length() - 1) == ',') {
            cleanedString = cleanedString.substring(0, cleanedString.length() - 1);
        }

        return cleanedString;
    }

    public String getAttribute(JSONObject object, String attribute) {
        String retrievedAttribute = "";
        try {
            retrievedAttribute = object.getString(attribute);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retrievedAttribute;
    }

    public JSONObject getArrayElement(JSONArray array, int index) {
        JSONObject retrievedObject = new JSONObject();
        try {
            retrievedObject = array.getJSONObject(index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retrievedObject;
    }
}
