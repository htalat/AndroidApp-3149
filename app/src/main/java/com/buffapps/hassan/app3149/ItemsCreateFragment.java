package com.buffapps.hassan.app3149;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hassan on 4/23/16.
 */
public class ItemsCreateFragment extends Fragment
{
    private Context mContext;
    private Button mSaveUpdateButton;
    private Button mCancelButton;
    private EditText mItemName;
    private EditText mItemPrice;
    private EditText mItemQuantity;
    private Switch mItemIsBulk;


    public static String NEW_ITEM_NAME = "NEW_ITEM_NAME";
    public static String NEW_ITEM_QUANTITY = "NEW_ITEM_QUANTITY";
    public static String NEW_ITEM_PRICE = "NEW_ITEM_PRICE";
    public static String NEW_ITEM_ID = "NEW_ITEM_ID";
    public static String NEW_ITEM =  "NEW_ITEM";
    private Item mItem;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        Bundle extras = this.getArguments();
        if(extras != null)
        {
            String id = extras.getString(NEW_ITEM_ID);
            String name = extras.getString(NEW_ITEM_NAME);
            int quantity = extras.getInt(NEW_ITEM_QUANTITY);
            Double price = extras.getDouble(NEW_ITEM_PRICE);

            mItem = new Item(name,quantity);
            mItem.setID(id);
            mItem.setPrice(price);

        }
        return inflater.inflate(R.layout.fragment_create_item, parent, false);


    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        mContext      = getActivity();
        mItemName     = (EditText) view.findViewById(R.id.itemName);
        mItemPrice    = (EditText) view.findViewById(R.id.itemPrice);
        mItemQuantity = (EditText) view.findViewById(R.id.itemQuantity);
        mItemIsBulk   = (Switch)   view.findViewById(R.id.itemIsBulk);
        mSaveUpdateButton = (Button)   view.findViewById(R.id.itemSaveUpdateButton);
        mCancelButton = (Button) view.findViewById(R.id.itemCancelButton);

        if(mItem != null)
        {
            mItemQuantity.setText(mItem.getQuantity()+"");
            mItemName.setText(mItem.getName());
            mItemPrice.setText(mItem.getPrice()+"");
            mSaveUpdateButton.setText("UPDATE");
        }
        setupButtonListener();

    }

    private void setupButtonListener() {

        mSaveUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String name = mItemName.getText().toString();
                int quantity = Integer.parseInt(mItemQuantity.getText().toString());
                Double price = Double.parseDouble(mItemPrice.getText().toString());
                Item item = new Item(name,quantity);
                item.setPrice(price);

                if(isNetworkAvailable())
                {
                    try
                    {
                        if(item.getID() == null)
                            postData(item);
                        else
                            updateData();
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });


        mCancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setMessage("Are you sure you want to cancel?")
                        .setTitle("Cancel?");

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        ItemsFragment fragment = new ItemsFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.placeholder, fragment).commit();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {

                    }
                });
                alertDialog.create().show();
            }
        });

    }


    public void updateData() throws IOException
    {
        String name = mItemName.getText().toString();
        int quantity = Integer.parseInt(mItemQuantity.getText().toString());
        Double price = Double.parseDouble(mItemPrice.getText().toString());
        Item item = new Item(name,quantity);
        item.setPrice(price);
        putData(item);
    }
    public void putData(Item item) throws IOException
    {
        String jsonString = jsonfyData(item);
        String url = "https://project-wtf-htalat.c9users.io/api/items/" + mItem.getID();
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonString);
        Request request =  new Request.Builder()
                .url(url)
                .put(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                if (response.isSuccessful())
                    statusMessage("Success!",true);
                else
                    statusMessage("Something went wrong!Could not update. Try again!",false);
            }

        });
    }


    public void postData(Item item) throws IOException
    {
        String jsonString = jsonfyData(item);
        Log.d("TAG",jsonString);
        String url = "https://project-wtf-htalat.c9users.io/api/items/";
        OkHttpClient client  = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonString);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                if (response.isSuccessful())
                    statusMessage("Success!",true);
                else
                    statusMessage("Something went wrong! Try again!",false);
            }

        });


    }

    public void statusMessage(final String status, final boolean isSuccessful)
    {
       getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
              //  Toast.makeText(CreateItemActivity.this,status , Toast.LENGTH_SHORT)
              //          .show();
              //  if(isSuccessful)
              //  {
                   // Intent intent = new Intent(CreateItemActivity.this,HomeActivity.class);
                   // startActivity(intent);
              //  }
            }
        });

    }

    public String jsonfyData(Item i)
    {
        return"{\"name\": \"" + i.getName()+ "\","
                + " \"quantity\" : "+ i.getQuantity() +","
                + "\"price\": " + i.getPrice()
                //  + "'lastSaved':1367702411696,"
                //  + "'dateStarted':1367702378785,"
                //   + "'players':["
                //  + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
                //   + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
                + "}";
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
}
