package com.example.cs478_project_app3;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cs478_project_app3.ImageFragment;
import com.example.cs478_project_app3.R;
import com.example.cs478_project_app3.ShowListFragment;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class MainActivity extends AppCompatActivity implements ShowListFragment.ListSelectionListener {

    private final static String KABOOM_PERMISSION = "edu.uic.cs478.s19.kaboom";
    private final static String DISPLAY_WEBSITE = "edu.uic.cs478.s19.displayWebsite";

    private ShowListFragment showListFragment;
    private ImageFragment imageFragment;

    public static String[] showTitle;
    public static Integer[] pictureResource;
    public static String[] url;
    public static int flag;
    int selectedId = -1;


    public static  void setflag(int flag) {MainActivity.flag = flag;}
    public static int getFlag() { return flag;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TVShow Details
        pictureResource = new Integer[]{R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4, R.drawable.pic5};
        url = new String[]{"https://en.wikipedia.org/wiki/Game_of_Thrones", "https://en.wikipedia.org/wiki/Prison_Break", "https://en.wikipedia.org/wiki/Mismatched", "https://en.wikipedia.org/wiki/Sherlock_(TV_series)", "https://en.wikipedia.org/wiki/Money_Heist"};
        showTitle = new String[]{"Game of Thrones", "Prison Break", "Mismatched", "SherLock", "Money Heist"};

        //Setting Action bar name and icon
        getSupportActionBar().setTitle("APP 3");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_star_rate_24);

        //fetching the selected item on orientation change
        if(savedInstanceState !=  null){
            selectedId = savedInstanceState.getInt("index");
        }

        //Defining fragments programmatically
        FragmentManager fm = getSupportFragmentManager();
        showListFragment = (ShowListFragment) fm.findFragmentByTag("showListFragment");
        if (showListFragment == null) {
            showListFragment = new ShowListFragment();
            fm.beginTransaction()
                    .replace(R.id.fragmentContainer1, showListFragment, "showListFragment")
                    .commit();
        }

        imageFragment = (ImageFragment) fm.findFragmentByTag("imageFragment");
        if (imageFragment == null) {
            imageFragment = new ImageFragment();
        }
        fm.addOnBackStackChangedListener(this::setLayout);
    }

    //Saving the clicked state when orientation changes
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("index", selectedId);
    }

    //Inflating OptionMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }

    //Handling the menu option selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.navigate) {
            //Check if an item has been selected
            if (selectedId == -1) { // When No item is selected
                Toast.makeText(this, "Select a show first!", Toast.LENGTH_SHORT).show();
            } else {
                // Send a broadcast intent
                String urlName = url[selectedId];
                Intent intent = new Intent();
                intent.setAction(DISPLAY_WEBSITE);
                intent.putExtra("ShowURL", urlName);
                sendOrderedBroadcast(intent, KABOOM_PERMISSION);
            }
        } else if (item.getItemId() == R.id.exit) {
            finish(); //Exit app3
        }
        return true;
    }

    @Override
    public void onListSelection(int selected) {
        // Add imageview to the fragment container if not added previously
        if (!imageFragment.isAdded()) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction t = fm.beginTransaction();
            t.replace(R.id.fragmentContainer2, imageFragment, "ImageFragment");
            t.addToBackStack(null);
            t.commit();
        }
        selectedId = selected;
        int img = pictureResource[selectedId];
        imageFragment.setCharImage(img);
        setLayout();
    }

    public void setLayout() {
        // Get fragment containers
        FrameLayout fragmentContainer1 = findViewById(R.id.fragmentContainer1);
        FrameLayout fragmentContainer2 = findViewById(R.id.fragmentContainer2);

        // Determine whether the imagefragment has been added
        if (imageFragment.isAdded()) {
            //when back pressed and in ORIENTATION_PORTRAIT and ORIENTATION_LANDSCAPE only listview(tv show should be displayed) should be showed
            if((getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && flag==1) || (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && flag==1)){

                fragmentContainer1.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                        MATCH_PARENT));
                fragmentContainer2.setLayoutParams(new LinearLayout.LayoutParams(
                        0, MATCH_PARENT));}

            //when ORIENTATION_PORTRAIT image added second fragment is alone displayed
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && flag==0){
                fragmentContainer1.setLayoutParams(new LinearLayout.LayoutParams(
                        0, MATCH_PARENT));
                fragmentContainer2.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                        MATCH_PARENT));

            }
            //when ORIENTATION_LANDSCAPE and image added the first fragment is 1/3 and second fragment is 2/3 of the width of the screen
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && flag==0){

                fragmentContainer1.setLayoutParams(new LinearLayout.LayoutParams(
                        0, MATCH_PARENT, 1f));
                fragmentContainer2.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));}

        }
        else {
            //If image not added in ORIENTATION_PORTRAIT and ORIENTATION_LANDSCAPE only first fragment should be dispalyed
            if ((getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ||(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) ) {

                fragmentContainer1.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                        MATCH_PARENT));

                fragmentContainer2.setLayoutParams(new LinearLayout.LayoutParams(
                        0, MATCH_PARENT));
            }
            flag=0;
        }
    }

    //Handling the imageview display when returned back to listview when orientation change
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setflag(1);
        //selectedId = -1;  //This is to avoid the launch of app1 and app2 when backpress is selected.
    }
}
