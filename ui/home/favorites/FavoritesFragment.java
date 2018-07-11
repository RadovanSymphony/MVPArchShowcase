package com.mg.kode.kodebrowser.ui.home.favorites;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mg.kode.kodebrowser.R;
import com.mg.kode.kodebrowser.data.model.Favorites;
import com.mg.kode.kodebrowser.di.components.ActivityComponent;
import com.mg.kode.kodebrowser.ui.base.BaseFragment;
import com.mg.kode.kodebrowser.ui.base.ItemClickedListener;
import com.mg.kode.kodebrowser.ui.custom.FavoritesPopupOptions;
import com.mg.kode.kodebrowser.ui.home.favorites_section.FavoritesSectionContract.FavoritesView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoritesFragment extends BaseFragment implements FavoritesView {

    private ItemClickedListener mFavoritesClickListener;
    private FavoritesAdapter mAdapter;

    @Inject
    public FavoritesSectionContract.FavoritesPresenter<FavoritesView> mPresenter;

    @BindView(R.id.rv_home_favorites)
    RecyclerView mFavoritesList;
    FavoritesPopupOptions mPopupOptions;


    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickedListener) {
            this.mFavoritesClickListener = (ItemClickedListener) context;
        }
        mAdapter = new FavoritesAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, root));
            mFavoritesList.setLayoutManager(new GridLayoutManager(getActivity(), 4));
            mFavoritesList.setAdapter(mAdapter);
            mFavoritesList.setNestedScrollingEnabled(false);
            mAdapter.setItemClickListener(mFavoritesClickListener);
            mAdapter.setOnAddBtnClickListener((v) -> {
                mPopupOptions.hide();
                mPresenter.onAddNewFavoriteClicked();
            });
            mAdapter.setLongItemClickListener((v, item) -> {
                mPopupOptions.hide();
                mPopupOptions.show(v, item);
            });
            mPopupOptions = new FavoritesPopupOptions(getLayoutInflater());
            mPopupOptions.setClickListener(new FavoritesPopupOptions.OptionsClickListener() {
                @Override
                public void onDeleteClicked(Favorites favorites) {
                    mPresenter.onDeleteFavoriteClicked(favorites);
                    mPopupOptions.hide();
                }

                @Override
                public void onEditClicked(Favorites favorites) {
                    EditFavoriteDialogFragment fragment = EditFavoriteDialogFragment.newInstance(getString(R.string.edit_favorite), favorites.getTitle(), favorites.getWebsite().getUrl());
                    fragment.setDialogListener((title, url) -> mPresenter.onEditFavoriteFormSubmitted(favorites, title, url));
                    fragment.setPresenter(mPresenter);
                    fragment.show(getActivity().getSupportFragmentManager(), EditFavoriteDialogFragment.FRAGMENT_TAG);
                    mPopupOptions.hide();
                }
            });
            mPresenter.onAttach(this);
        }
        return root;
    }

    /**
     * Dismiss {@link FavoritesPopupOptions} popup window if present. Returns true if window was visible and now dismissed, false if window was not visible.
     *
     * @return true if window has been hidden.
     */
    public boolean dismissPopupOptions() {
        boolean wasShowing = mPopupOptions.isVisible();
        mPopupOptions.hide();
        return wasShowing;
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        mAdapter.setItemClickListener(null);
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        mFavoritesClickListener = null;
        super.onDetach();
    }

    @Override
    public void setFavorites(List<Favorites> favorites) {
        mAdapter.setData(favorites);
    }

    @Override
    public void newFavoriteAdded(Favorites newFavorite) {
        mAdapter.addData(newFavorite);
    }

    @Override
    public void favoriteRemoved(Favorites removedFavorite) {
        mAdapter.removeData(removedFavorite);
    }

    @Override
    public void displayAddNewFavoriteDialog() {
        EditFavoriteDialogFragment fragment = EditFavoriteDialogFragment.newInstance(getString(R.string.add_favorite), null, null);
        fragment.setDialogListener((title, url) -> mPresenter.onAddFavoriteFormSubmitted(title, url));
        fragment.setPresenter(mPresenter);
        fragment.show(getActivity().getSupportFragmentManager(), EditFavoriteDialogFragment.FRAGMENT_TAG);
    }
}
