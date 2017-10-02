package com.example.tae.second_assignment.ui.popList;


import com.example.tae.second_assignment.network.DataManager;
import com.example.tae.second_assignment.network.model.Music_Model;
import com.example.tae.second_assignment.ui.base.BasePresenter;
import com.example.tae.second_assignment.ui.utils.rx.SchedulerProvider;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;



public class PopListPresenter
        <V extends iPopListMvpView>
        extends BasePresenter<V>
        implements iPopListMvpPresenter<V> {

    public PopListPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared() {

        getCompositeDisposable().add(getDataManager()
                .useCasePopList()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Music_Model>() {
                               @Override
                               public void accept(@NonNull Music_Model musicList) throws Exception {
                                   getMvpView().onFetchPopCompleted(musicList);
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                getMvpView().onError(throwable.getMessage());
                            }
                        }));
    }
}
