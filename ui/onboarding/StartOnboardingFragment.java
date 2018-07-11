package com.mg.kode.kodebrowser.ui.onboarding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mg.kode.kodebrowser.R;

public class StartOnboardingFragment extends Fragment {
    public static StartOnboardingFragment newInstance() {
        StartOnboardingFragment fragment = new StartOnboardingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_onboarding, container, false);
    }

}
