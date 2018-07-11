package com.mg.kode.kodebrowser.ui.custom;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.mg.kode.kodebrowser.R;
import com.mg.kode.kodebrowser.data.model.Favorites;


public class FavoritesPopupOptions implements View.OnClickListener {

    private final View mRootView;
    private Favorites mCurrentItem;

    public interface OptionsClickListener {
        void onDeleteClicked(Favorites favorites);

        void onEditClicked(Favorites favorites);
    }

    private PopupWindow popupWindow;
    private OptionsClickListener listener;

    public FavoritesPopupOptions(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.favorites_popup_window, null);
        mRootView.findViewById(R.id.tv_favorite_delete).setOnClickListener(this);
        mRootView.findViewById(R.id.tv_favorite_edit).setOnClickListener(this);
        mRootView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWindow = new PopupWindow(mRootView);
        popupWindow.setHeight(mRootView.getMeasuredHeight());
        popupWindow.setWidth(mRootView.getMeasuredWidth());
    }

    public void show(View anchor, Favorites favorites) {
        int xOff = calculateXOffset();
        int yOff = calculateYOffset(anchor);
        popupWindow.showAsDropDown(anchor, xOff, yOff, Gravity.END);
    	mCurrentItem = favorites;
    }

    private int calculateYOffset(View anchor) {
        return -(anchor.getHeight() + mRootView.getHeight());
    }

    private int calculateXOffset() {
        return 0;
    }

    public void hide() {
        if (isVisible())
            popupWindow.dismiss();
    }

    public boolean isVisible() {
        return popupWindow.isShowing();
    }

    public void setClickListener(OptionsClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null)
            if (v.getId() == R.id.tv_favorite_delete) {
                listener.onDeleteClicked(mCurrentItem);
            } else {
                listener.onEditClicked(mCurrentItem);
            }
    }
}
