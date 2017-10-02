package com.example.tae.second_assignment;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tae.second_assignment.network.AppDataManager;
import com.example.tae.second_assignment.network.model.Music_Model;
import com.example.tae.second_assignment.network.model.OnItemClickListener;
import com.example.tae.second_assignment.network.model.Result;
import com.example.tae.second_assignment.ui.popList.PopListPresenter;
import com.example.tae.second_assignment.ui.popList.iPopListMvpView;
import com.example.tae.second_assignment.ui.utils.rx.AppSchedulerProvider;

import java.io.IOException;

import io.reactivex.disposables.CompositeDisposable;


public class PopFragment extends Fragment implements iPopListMvpView {


    private PopListPresenter<iPopListMvpView> iPopListMvpViewPopListPresenter;
    public RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main,  container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i("Test", "classic opened");
        iPopListMvpViewPopListPresenter = new PopListPresenter<>(
                new AppDataManager(),
                new AppSchedulerProvider(),
                new CompositeDisposable());

        iPopListMvpViewPopListPresenter.onAttach(this);
        iPopListMvpViewPopListPresenter.onViewPrepared();

        initaliseRecyclerView(view);

        super.onViewCreated(view, savedInstanceState);
    }

    private void initaliseRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerMusic);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        Log.i("RecyclerCheck", "has been initialised");
    }

    @Override
    public void onFetchPopCompleted(Music_Model music_model) {
        recyclerView.setAdapter(new MusicAdapter(music_model.getResults(), R.layout.list_item_movie, new OnItemClickListener() {

            @Override
            public void onItemClick(Result item) {
                Toast.makeText(getContext(), item.getArtistId().toString(), Toast.LENGTH_LONG).show();

                String url = item.getPreviewUrl(); // your URL here
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {

                    mediaPlayer.prepare(); // might take long! (for buffering, etc)
                } catch (IOException e) {
                    e.printStackTrace();
                }


                mediaPlayer.start();
            }

        }, getActivity().getApplicationContext()));
    }
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }
}
