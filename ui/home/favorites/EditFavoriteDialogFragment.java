package com.mg.kode.kodebrowser.ui.home.favorites;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.mg.kode.kodebrowser.R;
import com.mg.kode.kodebrowser.utils.UIUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;


public class EditFavoriteDialogFragment extends DialogFragment {

    public final static String FRAGMENT_TAG = EditFavoriteDialogFragment.class.getName();
 	private final static String ITEM_NAME = "itemName";
    private final static String ITEM_URL = "itemUrl";
    private final static String DIALOG_TITLE = "dialogTitle";

    private FavoriteDialogListener mClickListener;
    private EditText mTitleEditText;
    private EditText mUrlEditText;
    private TextInputLayout mTitleHolder;
    private TextInputLayout mUrlHolder;
    private FavoritesSectionContract.FavoritesPresenter mPresenter;

    public static EditFavoriteDialogFragment newInstance(
           @NonNull String dialogTitle,
            String itemName,
            String itemUrl) {

        Bundle args = new Bundle();
        args.putString(ITEM_NAME, itemName);
        args.putString(ITEM_URL, itemUrl);
        args.putString(DIALOG_TITLE, dialogTitle);
        EditFavoriteDialogFragment fragment = new EditFavoriteDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View contentDialogLayout = LayoutInflater.from(getContext()).inflate(
                R.layout.dialog_favorites, null);
        this.setCancelable(false);

        Bundle args = getArguments();

        mTitleEditText = (EditText) contentDialogLayout.findViewById(R.id.et_favorites_dialog_title);
        mUrlEditText = (EditText) contentDialogLayout.findViewById(R.id.et_favorites_dialog_url);
        mTitleHolder = (TextInputLayout) contentDialogLayout.findViewById(R.id.et_favorites_dialog_title_holder);
        mUrlHolder = (TextInputLayout) contentDialogLayout.findViewById(R.id.et_favorites_dialog_url_holder);

        if (args != null) {
            mUrlEditText.setText(args.getString(ITEM_URL));
            mTitleEditText.setText(args.getString(ITEM_NAME));
        }

        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(contentDialogLayout)
                .setTitle(args.getString(DIALOG_TITLE))
                .setCancelable(false)
                .create();

        dialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok), (dialog1, which) -> {
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel), (dialog12, which) -> {
        });


        if (dialog.getWindow() != null)
            dialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        AlertDialog alertDialog = (AlertDialog) getDialog();
        Button okButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        // Dialog will always set default behavior is this is not overriden.
        okButton.setOnClickListener(v -> {
            if (isEmpty(mTitleEditText)) {
                mTitleHolder.setError(getString(R.string.fill_field_msg));
            } else if (isEmpty(mUrlEditText)) {
                mUrlHolder.setError(getString(R.string.fill_field_msg));
            } else {
                if (mPresenter != null)
                    mPresenter.validateFavoriteFormURLInputField(mUrlEditText.getText().toString())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(() -> dismissDialog(alertDialog),
                                    throwable -> mUrlHolder.setError(throwable.getMessage()));
                else
                    dismissDialog(alertDialog);
            }
        });

        Button cancelButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        cancelButton.setOnClickListener(v -> {
            UIUtils.hideKeyboard(getActivity());
            alertDialog.dismiss();
        });
    }

    private void dismissDialog(AlertDialog alertDialog) {
        UIUtils.hideKeyboard(getActivity());
        if (mClickListener != null) {
            mClickListener.onSaveClicked(mTitleEditText.getText().toString().trim(), mUrlEditText.getText().toString().trim());
        }
        alertDialog.dismiss();
    }

    private boolean isEmpty(EditText mTitleEditText) {
        return mTitleEditText.getText().toString().isEmpty();
    }

    public void setDialogListener(FavoriteDialogListener listener) {
        mClickListener = listener;
    }

    public void setPresenter(FavoritesSectionContract.FavoritesPresenter presenter) {
        mPresenter = presenter;
    }

    public interface FavoriteDialogListener {
        /**
         * Called when save button is clicked on a dialog, returning trimmed title and url strings.
         *
         * @param title
         * @param url
         */
        void onSaveClicked(String title, String url);
    }
}
