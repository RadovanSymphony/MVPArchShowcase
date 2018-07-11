package com.mg.kode.kodebrowser.ui.onboarding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mg.kode.kodebrowser.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OnboardingFragment extends Fragment {
    private static final String PARAM_TITLE = "title";
    private static final String PARAM_INFO = "info";
    private static final String PARAM_ICON_RES = "icon_res_id";
    private static final String PARAM_BG_IMG_RES = "bg_image_res_id";
    private static final String PARAM_GRADIENT_BG_RES = "gradient_bg_res_id";


    @BindView(R.id.iv_onboard_icon)
    ImageView mIvIcon;
    @BindView(R.id.tv_onboard_title)
    TextView mTvTitle;
    @BindView(R.id.tv_onboard_info)
    TextView mTvInfo;
    @BindView(R.id.iv_onboard_image_bg)
    ImageView mIvBGImage;
    private Unbinder mUnbinder;

    public static OnboardingFragment newInstance(String title, String info, int iconResId, int bgImgResId, int gradientBgsResourceId) {
        OnboardingFragment fragment = new OnboardingFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_TITLE, title);
        args.putString(PARAM_INFO, info);
        args.putInt(PARAM_ICON_RES, iconResId);
        args.putInt(PARAM_BG_IMG_RES, bgImgResId);
        args.putInt(PARAM_GRADIENT_BG_RES, gradientBgsResourceId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle b = getArguments();
        View rootView = inflater.inflate(R.layout.fragment_onboarding, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        rootView.setBackgroundResource(b.getInt(PARAM_GRADIENT_BG_RES));
        mTvTitle.setText(b.getString(PARAM_TITLE));
        mTvInfo.setText(b.getString(PARAM_INFO));
        mIvIcon.setImageResource(b.getInt(PARAM_ICON_RES));
        mIvBGImage.setImageResource(b.getInt(PARAM_BG_IMG_RES));
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
