package com.example.clubfam.ui.calendar;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clubfam.helpers.ClubData;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.applikeysolutions.cosmocalendar.dialog.CalendarDialog;
import com.applikeysolutions.cosmocalendar.dialog.OnDaysSelectionListener;
import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.selection.MultipleSelectionManager;
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager;
import com.applikeysolutions.cosmocalendar.selection.SingleSelectionManager;
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener;
import com.applikeysolutions.cosmocalendar.settings.appearance.ConnectedDayIconPosition;
import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.*;
import com.applikeysolutions.cosmocalendar.settings.lists.connected_days.ConnectedDays;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import com.example.clubfam.R;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.text.ParseException;

@SuppressWarnings("FieldCanBeLocal")
public class CalendarFragment extends Fragment {

//    private CalendarViewModel calendarViewModel;

    CalendarView cal;
    ClubData userClubs;
    JSONArray eventArray = new JSONArray();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        calendarViewModel =
//                ViewModelProviders.of(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);




        cal = (CalendarView) root.findViewById(R.id.cosmo_calendar);


        // Start calendar on Sunday
        cal.setFirstDayOfWeek(Calendar.SUNDAY);

        cal.setWeekendDays(new HashSet(){{
            add(Calendar.SUNDAY);
            add(Calendar.SATURDAY);
        }});


        //Set Orientation 0 = Horizontal | 1 = Vertical
        cal.setCalendarOrientation(0);

        // Only alow 1 day at a time to be clicked
        cal.setSelectionType(SelectionType.SINGLE);

        // Set days you want to connect
        addEventToCalendar("01/Dec/2019", "1", "Pizza Social at Rec! Free Food!"); // Made up event
        addEventToCalendar("04/Dec/2019", "2", "Masters Cup Bowling Tournament at Coffman"); // Made up event
        addEventToCalendar("02/Dec/2019", "1", "Basketball game, Team Donkey VS Team Elephant");
        addEventToCalendar("12/Dec/2019", "3", "Free skiing at Bloomington for all club members!");

        cal.setSelectionManager(new SingleSelectionManager(new OnDaySelectedListener() {

            @Override
            public void onDaySelected() {
                if (cal.getSelectedDates().size() <= 0) {
                    return;
                }

                // TODO: Here is how you can get the date. When the user clicks on a date here, do something
                DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
                String strDate = dateFormat.format(cal.getSelectedDays().get(0).getCalendar().getTime());
                Log.d(" CALENDAR ", "Date selected = " + strDate);
                //I am not sure why the for loop iteration does not work.
                //=============bug begins here==========================
                for(int i = 0; i < eventArray.length(); i++){
                    try {
                        JSONObject currentEvent = eventArray.getJSONObject(i);
                        if(currentEvent.get("date").toString().equals(strDate)){
                            String cID = (currentEvent.get("clubID")).toString();
                            String clubName = userClubs.getClubFromId(cID).get("name").toString();
                            Toast.makeText(getContext(),"On "+strDate+" "+clubName+" has "+currentEvent.get("shortDes"),Toast.LENGTH_LONG).show();
                            break;
                        }else{
                            Toast.makeText(getContext(),"Sorry, there is on event on "+strDate,Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }));
        return root;
    }

    private void addEventToCalendar(String date, String clubId, String ShortDesc){
        // TODO: this should check if userClubs contains clubId and if not, just return
        userClubs = new ClubData(getActivity());
        if(!userClubs.haveClub(clubId)) {
            return;
        }

        Set<Long> days = new TreeSet<>();
        SimpleDateFormat f = new SimpleDateFormat("dd/MMM/yyyy");
        try {
            Date d = f.parse(date);
            days.add(d.getTime()); // Christmas
        } catch (ParseException e) {
            e.printStackTrace();
        }



        // Define colors
        int textColor = Color.parseColor("#ff0000");
        int selectedTextColor = Color.parseColor("#ff4000");
        int disabledTextColor = Color.parseColor("#ff8000");
        ConnectedDays connectedDays = new ConnectedDays(days, textColor, selectedTextColor, disabledTextColor);

        // Connect days to calendar
        cal.addConnectedDays(connectedDays);
        JSONObject event = new JSONObject();
        try{
            event.put("clubID", clubId);
            event.put("date", date);
            event.put("shortDes", ShortDesc);
        }catch (JSONException e) {
            e.printStackTrace();
        }
        eventArray.put(event);

    }
}
