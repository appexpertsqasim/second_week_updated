package com.example.tae.second_assignment;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.example.tae.second_assignment.ui.rock_music.IRockMusicListMvpView;
import com.example.tae.second_assignment.ui.rock_music.RockMusicListPresenter;
import com.example.tae.second_assignment.ui.utils.rx.AppSchedulerProvider;

import java.io.IOException;

import io.reactivex.disposables.CompositeDisposable;

public class RockMusicActivity extends Fragment implements IRockMusicListMvpView {


    SwipeRefreshLayout mySwipeRefreshLayout;
    RockMusicListPresenter<IRockMusicListMvpView> iMovieListMvpViewmovieListPresenter;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.activity_main,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iMovieListMvpViewmovieListPresenter= new RockMusicListPresenter<>(new AppDataManager(), new AppSchedulerProvider(), new CompositeDisposable());
        iMovieListMvpViewmovieListPresenter.onAttach(this);
        iMovieListMvpViewmovieListPresenter.onViewPrepared();
        IRockMusicListMvpView rView=this;
        mySwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("Refresh", "onRefresh called from SwipeRefreshLayout");
                        iMovieListMvpViewmovieListPresenter= new RockMusicListPresenter<>(new AppDataManager(), new AppSchedulerProvider(), new CompositeDisposable());
                        iMovieListMvpViewmovieListPresenter.onAttach(rView);
                        iMovieListMvpViewmovieListPresenter.onViewPrepared();
                        myUpdateOperation();
                        initialiseRecyclerView(view);
                    }
                }
        );
        initialiseRecyclerView(view);
    }


    public void myUpdateOperation() {

        mySwipeRefreshLayout.setRefreshing(false);}

    public void initialiseRecyclerView(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerMusic);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    @Override
    public void onFetchDataCompleted(Music_Model rockMusic_model) {
        recyclerView.setAdapter(new MusicAdapter(rockMusic_model.getResults(), R.layout.list_item_movie, new OnItemClickListener() {

            @Override
            public void onItemClick(Result item) {
                Toast.makeText(getContext(),item.getArtistId().toString(),Toast.LENGTH_LONG).show();
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

        }, getContext()));
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
