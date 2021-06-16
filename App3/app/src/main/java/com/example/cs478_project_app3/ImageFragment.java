package com.example.cs478_project_app3;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ImageFragment extends Fragment {

    private ImageView CharImage;
    private int imgIfNull = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.image_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Retaining the state
        CharImage = getView().findViewById(R.id.charImage);
        if (imgIfNull != -1)
        {
            CharImage.setImageResource(imgIfNull);
        }
    }

    public void setCharImage(int imgResource) {
        // Set the new image
        this.imgIfNull = imgResource;
        if (CharImage != null)
            CharImage.setImageResource(imgResource);
    }


}

