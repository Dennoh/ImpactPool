package tcds.or.tcdsapp.Database.ModelDB;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName ="Cart")
public class Cart {

    @NonNull
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "name")
    public String name;

 @ColumnInfo(name = "link")
    public String link;


    @ColumnInfo(name = "price")
    public Double price;

    @ColumnInfo(name = "amount")
    public int amount;

    @ColumnInfo(name = "productdetails")
    public String productdetails;

    @ColumnInfo(name = "booktype")
    public String booktype;

    @ColumnInfo(name = "bookauthor")
    public String bookauthor;


    @ColumnInfo(name = "restaurantname")
    public String restaurantname;
//
//    @ColumnInfo(name = "toppingExtras")
//    public String toppingExtraS;
//

}
