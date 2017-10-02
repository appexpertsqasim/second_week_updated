package com.example.tae.second_assignment.ui.popList;

import com.example.tae.second_assignment.network.model.Music_Model;
import com.example.tae.second_assignment.ui.base.MvpView;

public interface iPopListMvpView extends MvpView {

    void onFetchPopCompleted(Music_Model music_model);
}
