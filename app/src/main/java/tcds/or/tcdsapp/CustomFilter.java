package tcds.or.tcdsapp;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import tcds.or.tcdsapp.Adapter.ProductsAdapter;
import tcds.or.tcdsapp.Model.Book;


/**
 * Created by Yeuni on 3/17/2016.
 */
public class CustomFilter extends Filter {

    ProductsAdapter productsAdapter;

    List<Book> filterList;



    public CustomFilter(List<Book> filterList, ProductsAdapter productsAdapter)
    {
        this.productsAdapter = productsAdapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            List<Book> filteredPlayers=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getName().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));
                }
            }

            results.count=filteredPlayers.size();
            results.values=filteredPlayers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }


        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        productsAdapter.bookList = (List<Book>) results.values;

        //REFRESH
        productsAdapter.notifyDataSetChanged();
    }
}
