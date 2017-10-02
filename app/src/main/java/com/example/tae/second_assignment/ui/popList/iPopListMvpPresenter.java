package com.example.tae.second_assignment.ui.popList;


import com.example.tae.second_assignment.ui.base.MvpPresenter;

public interface iPopListMvpPresenter<V extends iPopListMvpView> extends MvpPresenter<V> {

    void onViewPrepared();
}
