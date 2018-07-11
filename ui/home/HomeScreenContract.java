package com.mg.kode.kodebrowser.ui.home;

import com.mg.kode.kodebrowser.ui.base.BaseContract;

public interface HomeScreenContract {

    interface HomeView extends BaseContract.View {
    }

    interface HomePresenter<V extends HomeView> extends BaseContract.Presenter<V>{

    }
}
