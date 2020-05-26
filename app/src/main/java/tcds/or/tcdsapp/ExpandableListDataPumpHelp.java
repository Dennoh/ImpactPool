package tcds.or.tcdsapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Minja on 22/03/2017.
 */
public class ExpandableListDataPumpHelp {

    static String stringCalendar = "View TWUK calender by clicking on Calendar button and swipe from right to left to share the calendar info with your friends.";
    static String stringInfoPortal = "Get a summary of the TWUK ,union and the Legislature by clicking on info portal button.";
    static String stringMap = "Get GPS location of TWUK Headquarters by clicking on the location icon.";
    static String stringAbout = "Get version and copyright details of the TWUK app by clicking on About button.";
    static String stringHelp = "Get assistance on how to use and navigate the TWUK app by clicking on help button";
    static String stringFeedback = "Give response, reaction, comments, criticism; reception, reviews to the TWUK administrators by clicking on feedback button and filling the feedback form.";
    static String stringNews = "Get TWUK related news on both Transit and Air division by clicking on view all news button.";
    static String stringSocialMedia = "Get connected to TWUK social media pages by clicking on the respective icons.";
    static String stringGallery = "View best of TWUK Album photos by clicking on gallery icon. Once inside you can apply gestures to zoon in and out";
    static String stringJoinUs = "Register as a member of TWUK, by reading and accepting the terms and conditions, filling the registration form and submitting it.";
    static String stringcall = "Get a speed dial to TWUK head office mobile phone by clicking on call icon.";

    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> tawuCalendar = new ArrayList<String>();
        tawuCalendar.add(stringCalendar);

        List<String> gallery = new ArrayList<String>();
        gallery.add(stringGallery);

        List<String> news = new ArrayList<String>();
        news.add(stringNews);

        List<String> sociaMedia = new ArrayList<String>();
        sociaMedia.add(stringSocialMedia);


        List<String> feedback = new ArrayList<String>();
        feedback.add(stringFeedback);

        List<String> joinus = new ArrayList<String>();
        joinus.add(stringJoinUs);

        List<String> about = new ArrayList<String>();
        about.add(stringAbout);

        List<String> infoportal = new ArrayList<String>();
        infoportal.add(stringInfoPortal);

        List<String> mapandlocation = new ArrayList<String>();
        mapandlocation.add(stringMap);

        List<String> Help = new ArrayList<String>();
        Help.add(stringHelp);

        List<String> ServiceCall = new ArrayList<String>();
        ServiceCall.add(stringcall);

        expandableListDetail.put("Calendar", tawuCalendar);
        expandableListDetail.put("Gallery", gallery);
        expandableListDetail.put("News", news);
        expandableListDetail.put("Social Media", sociaMedia);
        expandableListDetail.put("Feedback", feedback);
        expandableListDetail.put("Join Us", joinus);
        expandableListDetail.put("About", about);
        expandableListDetail.put("Info Portal", infoportal);
        expandableListDetail.put("Map and Location", mapandlocation);
        expandableListDetail.put("Help", Help);
        expandableListDetail.put("Service Call", ServiceCall);

        return expandableListDetail;
    }
}
