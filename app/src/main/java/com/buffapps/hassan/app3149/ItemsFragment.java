package com.buffapps.hassan.app3149;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hassan on 4/21/16.
 */
public class ItemsFragment extends Fragment
{
/*    // Construct the data source
    ArrayList<User> arrayOfUsers = new ArrayList<User>();
    // Create the adapter to convert the array to views
    UsersAdapter adapter = new UsersAdapter(this, arrayOfUsers);
    // Attach the adapter to a ListView
    ListView listView = (ListView) findViewById(R.id.lvItems);
    listView.setAdapter(adapter);*/
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    ListView mListView ;
    ItemAdapter mItemAdapter;
    ArrayList<Item> mData;
    Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_menu_one, parent, false);


    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        mListView = (ListView) view.findViewById(R.id.items_list);
        mContext = getActivity().getApplicationContext();

        getItemsList();

        mData = new ArrayList<>();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.items_add_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemsCreateFragment fragment = new ItemsCreateFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.placeholder, fragment).commit();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = mData.get(position);

                ItemsCreateFragment fragment = new ItemsCreateFragment();
                Bundle bundle = new Bundle();
                bundle.putString(ItemsCreateFragment.NEW_ITEM_ID, item.getID());
                bundle.putString(ItemsCreateFragment.NEW_ITEM_NAME,item.getName());
                bundle.putInt(ItemsCreateFragment.NEW_ITEM_QUANTITY,item.getQuantity());
                bundle.putDouble(ItemsCreateFragment.NEW_ITEM_PRICE,item.getPrice());
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.placeholder, fragment).commit();

            }
        });
    }

    private void getItemsList()
    {
        ArrayList<Item> items = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        String url ="https://project-wtf-htalat.c9users.io/api/items";
        Request request = new Request.Builder().url(url).build();


        if(isNetworkAvailable())
        {
            final Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                   getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            //TODO: show error
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException
                {

                    if(response.isSuccessful())
                    {

                        String jsonData = response.body().string();
                        parseData(jsonData);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateDisplay();
                            }
                        });
                    }


                }
            });
        }

    }

    private void updateDisplay()
    {
        mItemAdapter = new ItemAdapter(mContext,mData);
        mListView.setAdapter(mItemAdapter);
    }


    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected())
        {
            isAvailable = true;
        }
        return isAvailable;
    }


    private void parseData(String body)
    {
        try
        {
            JSONArray array = new JSONArray(body);
            JSONObject currentItem;


            for(int i=0;i<array.length();i++)
            {
                currentItem = array.getJSONObject(i);
                Item item = new Item();
                item.setID(currentItem.getString("_id"));
                item.setName(currentItem.getString("name"));
                item.setPrice(currentItem.getDouble("price"));
                item.setQuantity(currentItem.getInt("quantity"));
                mData.add(item);
            }

        }catch (Exception e)
        {

        }


    }

}
