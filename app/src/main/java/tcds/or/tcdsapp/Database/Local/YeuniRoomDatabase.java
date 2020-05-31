package tcds.or.tcdsapp.Database.Local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import tcds.or.tcdsapp.Database.ModelDB.Cart;


@Database(entities = {Cart.class},version = 1,exportSchema = false)

public abstract class YeuniRoomDatabase extends RoomDatabase {


    public abstract CartDAO cartDAO();




    private static YeuniRoomDatabase instance;

     public static YeuniRoomDatabase getInstance(Context context)
     {
         if(instance==null)
             instance= Room.databaseBuilder(context,YeuniRoomDatabase.class,"tcdsonlineshop_DB")
                     .allowMainThreadQueries()
                     .build();
         return instance;
     }

}
