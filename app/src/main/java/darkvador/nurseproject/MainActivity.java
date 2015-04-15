package darkvador.nurseproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends ActionBarActivity{
    Model myModel;
    private String[] menu;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String SENDER_ID = "111916247427";
    private CharSequence mDrawerTitle;
    static final String TAG = "GCMDemo";
    private CharSequence mTitle;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myModel= new Model();
        menu = getResources().getStringArray(R.array.menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setIcon(R.drawable.rsz_2logo);
            getSupportActionBar().setHomeButtonEnabled(true);

        }
        if (checkPlayServices()) {
            new registerToGCM(this.getApplicationContext(), getGcmPreferences(this));
        }
        TextView i = (TextView) findViewById(R.id.bienvenue);
        i.setText("Bienvenue "+Identity.prenom+" "+Identity.nom);
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (mDrawerToggle.onOptionsItemSelected(item)) {
                            return true;
                        }
                        switch (id){
                            case R.id.action_today:

                                return false;
                            case R.id.action_day_view:

                                return false;
                            case R.id.action_three_day_view:

                                return false;
                            case R.id.action_week_view:

                                return false;
                            case R.id.deconnexion:
                                Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                               Identity.deconnexion();
                                Intent mi= new Intent(MainActivity.this, login.class);
                                startActivity(mi);
                                finish();
                                return false;
                            case R.id.action_websearch:
                                Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();

                        }
                        return true;
                    }
                });
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, menu));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }
    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = new AfficheListePatient();
        if(position==0){
            fragment = new AfficheListePatient();
        }else if(position==1){
           fragment = new ActImport();
        }else if(position==2){
            fragment = new ActExport();
        }else if(position==3){
            fragment = new ActImportActe();
        }else if(position == 4){
            fragment = new AfficheListeActes();
        }else if(position == 5){
            fragment = new calendrier();
//            Intent myIntent = new Intent(this, test.class);
//            startActivity(myIntent);
        }else if(position == 6){

            Intent myIntent = new Intent(this, DemoActivity.class);
            startActivity(myIntent);
        }


        Bundle args = new Bundle();
        fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        //setTitle(menu[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    private SharedPreferences getGcmPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.

        return getSharedPreferences(DemoActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
    }
