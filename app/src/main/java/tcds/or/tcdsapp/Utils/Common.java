package tcds.or.tcdsapp.Utils;


import tcds.or.tcdsapp.Database.DataSource.CartRepository;
import tcds.or.tcdsapp.Database.Local.YeuniRoomDatabase;
import tcds.or.tcdsapp.Model.Book;
import tcds.or.tcdsapp.Model.Order;
import tcds.or.tcdsapp.Model.User;
import tcds.or.tcdsapp.Retrofit.RetrofitClient;
import tcds.or.tcdsapp.Retrofit.TcdsAPI;

public class Common {
    public static final String BASE_URL="http://nougattechnologies.com/somavitabu/";
//http://nougattechnologies.com/somavitabu/getbanner.php
//Database
public static CartRepository cartRepository;
    public static Order currentOder=null;
    public static Book currentProduct=null;
    public static YeuniRoomDatabase yeuniRoomDatabase;
    public static User currentuser=null;
    public static User currentUser=null;

    public static TcdsAPI getAPI(){
       return RetrofitClient.getClient(BASE_URL).create(TcdsAPI.class);
    }


    public static String convertCodeToStatus(int orderStatus) {
        switch (orderStatus){
            case 0:
                return "Placed";
            case 1:
                return "Processing";
            case 2:
                return "On route";
            case 3:
                return "Delivered";
            case -1:
                return  "Cancelled";
            default:
                return "Order Error";

        }
    }





}
