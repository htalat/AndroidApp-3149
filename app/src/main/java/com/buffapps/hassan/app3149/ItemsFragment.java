package com.buffapps.hassan.app3149;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_menu_one, parent, false);


    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }
}
