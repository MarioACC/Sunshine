package com.example.mario.sunshine;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;


public class DetailActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent=new Intent(this,SettingsActivity.class);
            startActivity(settingsIntent);
        }else if(id==R.id.action_send){

        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {
        final String SHARE_SUNSHINE_HASHTAG="#SUNSHINEFUCKINGAWESOMEApp";
        String shareMessage;

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
            //Inflate the menu, adds the items to the action bar
            inflater.inflate(R.menu.menu_detail,menu);

            //Retrieve the share menu item
            MenuItem menuItem=menu.findItem(R.id.action_send);
            ShareActionProvider shareActionProvider=(ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

            if(shareActionProvider!=null){
                shareActionProvider.setShareIntent(createShareForecastIntent());
            }

        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            Intent detailIntent=getActivity().getIntent();
            if (detailIntent!=null && detailIntent.hasExtra(Intent.EXTRA_TEXT)){
                String forecast= detailIntent.getStringExtra(Intent.EXTRA_TEXT);
                TextView detailText=(TextView) rootView.findViewById(R.id.detail_text);
                shareMessage=forecast;
                detailText.setText(forecast);
            }
            else{
                Toast.makeText(getActivity(),"Error loading the details",Toast.LENGTH_SHORT).show();
            }

            return rootView;
        }

        public Intent createShareForecastIntent(){
            Intent sendIntent = new Intent();

            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            sendIntent.putExtra(Intent.EXTRA_TEXT,shareMessage+SHARE_SUNSHINE_HASHTAG );
            sendIntent.setType("text/plain");
            return sendIntent;


        }
    }



}
