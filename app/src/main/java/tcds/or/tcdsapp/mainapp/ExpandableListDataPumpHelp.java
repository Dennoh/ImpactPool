package tcds.or.tcdsapp.mainapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Minja on 22/03/2017.
 */
public class ExpandableListDataPumpHelp {

    static String economicSectors = "economicSectors.";
    static String onlineShop = "onlineShop.";
    static String eBooksServices = "eBooksServices.";
    static String careerServices = "careerServices.";
    static String onlineTraining = "onlineTraining.";
    static String newsandAlerts = "newsandAlerts.";
    static String occupationalPathways = "occupationalPathways.";
    static String undergraduateProgramme = "undergraduateProgramme.";
    static String highLearningInstitutions = "highLearningInstitutions.";
    static String careerDatabase = "careerDatabase.";
    static String Connect = "Connect.";
    static String Settings = "Settings.";
    static String MyOrders = "MyOrders.";
    static String myCart = "myCart.";

    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> economicSectors = new ArrayList<String>();
        economicSectors.add(ExpandableListDataPumpHelp.economicSectors);

        List<String> highLearningInstitutions = new ArrayList<String>();
        highLearningInstitutions.add(ExpandableListDataPumpHelp.highLearningInstitutions);

        List<String> occupationalPathways = new ArrayList<String>();
        occupationalPathways.add(ExpandableListDataPumpHelp.occupationalPathways);

        List<String> undergraduateProgramme = new ArrayList<String>();
        undergraduateProgramme.add(ExpandableListDataPumpHelp.undergraduateProgramme);

        List<String> newsandAlerts = new ArrayList<String>();
        newsandAlerts.add(ExpandableListDataPumpHelp.newsandAlerts);

        List<String> careerDatabase = new ArrayList<String>();
        careerDatabase.add(ExpandableListDataPumpHelp.careerDatabase);

        List<String> careerServices = new ArrayList<String>();
        careerServices.add(ExpandableListDataPumpHelp.careerServices);

        List<String> onlineShop = new ArrayList<String>();
        onlineShop.add(ExpandableListDataPumpHelp.onlineShop);

        List<String> eBooksServices = new ArrayList<String>();
        eBooksServices.add(ExpandableListDataPumpHelp.eBooksServices);

        List<String> onlineTraining = new ArrayList<String>();
        onlineTraining.add(ExpandableListDataPumpHelp.onlineTraining);

        List<String> Connect = new ArrayList<String>();
        Connect.add(ExpandableListDataPumpHelp.Connect);

        List<String> Settings = new ArrayList<String>();
        Settings.add(ExpandableListDataPumpHelp.Settings);

        List<String> MyOrders = new ArrayList<String>();
        MyOrders.add(ExpandableListDataPumpHelp.MyOrders);

        List<String> myCart = new ArrayList<String>();
        myCart.add(ExpandableListDataPumpHelp.myCart);

        expandableListDetail.put("Economic Sectors", economicSectors);
        expandableListDetail.put("High Learning Institutions", highLearningInstitutions);
        expandableListDetail.put("Occupational Pathways", occupationalPathways);
        expandableListDetail.put("Undergraduate Programme", undergraduateProgramme);
        expandableListDetail.put("News and Alerts", newsandAlerts);
        expandableListDetail.put("Career Database", careerDatabase);
        expandableListDetail.put("Career Services", careerServices);
        expandableListDetail.put("Online Shop", onlineShop);
        expandableListDetail.put("eBooks Services", eBooksServices);
        expandableListDetail.put("Online Training", onlineTraining);
        expandableListDetail.put("Connect", Connect);
        expandableListDetail.put("Settings", Settings);
        expandableListDetail.put("My Orders", MyOrders);
        expandableListDetail.put("My Cart", myCart);

        return expandableListDetail;
    }
}
