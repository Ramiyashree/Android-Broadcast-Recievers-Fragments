package com.example.cs478_project_app3;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class ShowListFragment extends ListFragment {

    private ListSelectionListener mListener;
    int selection = -1;

    public interface ListSelectionListener {
        void onListSelection(int index);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int pos, long id) {

        // Indicates the selected item has been checked
        getListView().setItemChecked(pos, true);
        selection = pos;
        // Inform the ShowListFragment that the item in position pos has been selected
        mListener.onListSelection(pos);
    }

    @Override
    public void onAttach(@NonNull Context activity) {
        Log.i("ShowListFragment", getClass().getSimpleName() + ":entered onAttach()");
        super.onAttach(activity);

        try {
            mListener = (ListSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("ShowListFragment", getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("ShowListFragment", getClass().getSimpleName() + ":entered onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onActivityCreated(Bundle savedState) {
        Log.i("ShowListFragment", getClass().getSimpleName() + ":entered onActivityCreated()");
        super.onActivityCreated(savedState);

        setListAdapter(new ArrayAdapter<>(getActivity(),
                R.layout.showlist_fragment, MainActivity.showTitle));

        // Set the list choice mode to allow only one selection at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //Retaining the previous selection
        if (selection >= 0) {
            getListView().setItemChecked(selection, true);
            if(MainActivity.flag!= 1) {
                MainActivity.setflag(0);
                this.mListener.onListSelection(selection);
            }
        }
    }

}