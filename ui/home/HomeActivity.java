package com.mg.kode.kodebrowser.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.mg.kode.kodebrowser.R;
import com.mg.kode.kodebrowser.data.model.Favorites;
import com.mg.kode.kodebrowser.data.model.NewsArticle;
import com.mg.kode.kodebrowser.ui.base.BaseActivity;
import com.mg.kode.kodebrowser.ui.base.ItemClickedListener;
import com.mg.kode.kodebrowser.ui.custom.NotifyingScrollView;
import com.mg.kode.kodebrowser.ui.custom.SearchBar;
import com.mg.kode.kodebrowser.ui.custom.SubmenuView;
import com.mg.kode.kodebrowser.ui.home.favorites.FavoritesFragment;
import com.mg.kode.kodebrowser.ui.home.search.SearchFragment;
import com.mg.kode.kodebrowser.utils.UIUtils;

import butterknife.BindView;

public class HomeActivity extends BaseActivity implements HomeScreenContract.HomeView, ItemClickedListener, View.OnClickListener {

    private static final String SEARCH_FRAG_TAG = "search_suggestions_fragment";
    private static final String FLAG_TOP_SEARCH_VISIBLE = "top_search_visible";
    private static final String FLAG_TOP_SEARCH_TEXT = "top_search_text";

    // Main scroll view
    @BindView(R.id.sv_main_content_scroll)
    NotifyingScrollView mMainContentScrollWrapper;

    // Favorites
    FavoritesFragment mFavoritesFragment;

    // Menu
    @BindView(R.id.home_menu_container)
    ViewGroup mMenu;
    // Menu items
    @BindView(R.id.iv_home_more_menu)
    View mMenuMore;
    // Submenu
    @BindView(R.id.home_submenu_container)
    SubmenuView mSubmenuView;

    // Searchbar
    @BindView(R.id.search_view_placeholder)
    View mScrollSearchBar;
    // This is search bar where typing should happen
    @BindView(R.id.home_static_search_bar)
    SearchBar mStaticSearchBar;
    // Search bar vars
    int mDefaultScrollSearchBarMargin;
    int mScreenWidth;
    RelativeLayout.LayoutParams mScrollBarParams;
    int mScrollPositionToStartAnim = -1;
    private boolean scrollParamsReady = false;
    
    // Search suggestions fragment
    SearchFragment mSearchFragment;
    @BindView(R.id.search_suggestions_container)
    ViewGroup mSearchFragmentContainer;
    @BindView(R.id.mSpace)
    View mSpace;

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFavoritesFragment = (FavoritesFragment) getSupportFragmentManager().findFragmentById(R.id.home_favorites_fragment);
        mMenuMore.setOnClickListener(this);
        mSubmenuView.setOnClosedListener(() -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                UIUtils.setStatusBarColor(this, R.color.colorPrimaryDark);
            }
        });
        mStaticSearchBar.setOnClickListener(this);
        mStaticSearchBar.setFullRectBackground();
        DisplayMetrics d = getResources().getDisplayMetrics();
        mScreenWidth = d.widthPixels;
        mDefaultScrollSearchBarMargin = getResources().getDimensionPixelSize(R.dimen.mSpace_xxlarge);
        mScrollSearchBar.setOnClickListener(this);
        mScrollSearchBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mScrollSearchBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mScrollBarParams = (RelativeLayout.LayoutParams) mScrollSearchBar.getLayoutParams();
                mScrollBarParams.width = mScreenWidth - (mDefaultScrollSearchBarMargin * 2);
                mScrollSearchBar.setLayoutParams(mScrollBarParams);
                mScrollPositionToStartAnim = mScrollSearchBar.getTop() - mDefaultScrollSearchBarMargin;
                scrollParamsReady = true;
            }
        });

        mMainContentScrollWrapper.setScrollListener(new NotifyingScrollView.ScrollListener() {
            @Override
            public void onScrollChanged(int x, int y, int oldX, int oldY) {
                mFavoritesFragment.dismissPopupOptions();
                if (scrollParamsReady)
                    if (y > oldY) {
                        // scrolling from bottom to top
                        if (mScrollSearchBar.getTop() <= y) {
                            // scrolled search bar to top
                            if (mStaticSearchBar.getVisibility() != View.VISIBLE) {
                                showTopSearchBar();
                            }
                        } else if (y >= mScrollPositionToStartAnim) {
                            // search bar left is equal to top screen position. start contraction
                            int margin = mScrollSearchBar.getTop() - y;
                            mScrollBarParams.width = mScreenWidth - (margin * 2);
                            mScrollSearchBar.setLayoutParams(mScrollBarParams);
                        }
                    } else {
                        // scrolling from top to bottom
                        if (mScrollSearchBar.getTop() >= y) {
                            if (y >= mScrollPositionToStartAnim) {
                                // scrolled search bar to top
                                int margin = mScrollSearchBar.getTop() - y;
                                mScrollBarParams.width = mScreenWidth - (margin * 2);
                                mScrollSearchBar.setLayoutParams(mScrollBarParams);
                            } else {
                                mScrollBarParams.width = mScreenWidth - (mDefaultScrollSearchBarMargin * 2);
                                mScrollSearchBar.setLayoutParams(mScrollBarParams);
                            }
                            if (mStaticSearchBar.getVisibility() != View.GONE) {
                                hideTopSearchBar();
                                clearSearchBar();
                                UIUtils.hideKeyboard(HomeActivity.this);
                            }
                        }
                    }
            }
        });

        // Setup search frag
        if (savedInstanceState == null) {
      		mSearchFragment = new SearchFragment();
       		getSupportFragmentManager().beginTransaction().add(R.id.search_suggestions_container, mSearchFragment, SEARCH_FRAG_TAG).commit();	
        } else {
        	nSearchFragment = (SearchFragment) getSupportFragmentManager().findFragmentByTag(SEARCH_FRAG_TAG);
        }
        mSearchFragment.setOnSearchFragmentCallback(helperText -> {
	        mStaticSearchBar.appendText(helperText);
	    });

        mStaticSearchBar.setSearchBarListener(new SearchBar.SearchBarListener() {
            @Override
            public void onSearchQueryExecuted(String searchQuery) {
                mSearchFragment.onSearchQueryExecuted(searchQuery);
            }

            @Override
            public void onSearchQueryChanged(String newSearchQuery) {
                if (newSearchQuery != null && !newSearchQuery.isEmpty()) {
                    showSearchFragment();
                    mSearchFragment.searchQueryChanged(newSearchQuery);
                } else {
                    hideSearchFragment();
                }
            }
        });
        hideTopSearchBar();
        hideSearchFragment();
    }

    @Override
    public void onItemClicked(Object item) {
        mFavoritesFragment.dismissPopupOptions();
        if (item instanceof Favorites) {

        } else if (item instanceof NewsArticle) {

        } else {

        }
    }

    @Override
    public void onClick(View v) {
        mFavoritesFragment.dismissPopupOptions();
        switch (v.getId()) {
            case R.id.iv_home_more_menu: {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    UIUtils.setStatusBarColor(this, R.color.statusbar_dimmed_bg);
                }
                mSubmenuView.showSubmenu();
                break;
            }
            case R.id.search_view_placeholder: {
                mMainContentScrollWrapper.smoothScrollTo(0, mScrollSearchBar.getTop());
                UIUtils.showKeyboard(mStaticSearchBar);
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mSubmenuView.isSubmenuShown()) {
            mSubmenuView.hideSubmenu();
        } else if (mFavoritesFragment.dismissPopupOptions()) {
            // options hidden
        } else if (mStaticSearchBar.getVisibility() == View.VISIBLE) {
            clearSearchBar();
            scrollMainViewToDefault();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mStaticSearchBar.getVisibility() == View.VISIBLE) {
            outState.putBoolean(FLAG_TOP_SEARCH_VISIBLE, true);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getBoolean(FLAG_TOP_SEARCH_VISIBLE))
            showTopSearchBar();
    }


    private void hideTopSearchBar() {
        mStaticSearchBar.setVisibility(View.GONE);
    }

    private void showTopSearchBar() {
        mStaticSearchBar.setVisibility(View.VISIBLE);
    }

    private void showSearchFragment() {
        mSearchFragmentContainer.setVisibility(View.VISIBLE);
    }

    private void hideSearchFragment() {
        mSearchFragmentContainer.setVisibility(View.GONE);
    }

    private void clearSearchBar() {
        mStaticSearchBar.clearSearchBar();
    }

    private void scrollMainViewToDefault() {
        mMainContentScrollWrapper.smoothScrollTo(0, 0);
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
    }
}
