package net.datafans.android.test.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.datafans.android.test.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditTextFragment extends Fragment {


    public EditTextFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_edit_text, container, false);
    }


}
