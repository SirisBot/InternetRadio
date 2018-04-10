package com.software.osirisgadson.internetradioapp.ui.activity;

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.software.osirisgadson.internetradioapp.R;
import com.software.osirisgadson.internetradioapp.data.model.Channel;
import com.software.osirisgadson.internetradioapp.data.model.Channels;
import com.software.osirisgadson.internetradioapp.ui.BaseView;
import com.software.osirisgadson.internetradioapp.ui.adapter.RadioChannelListAdapter;
import com.software.osirisgadson.internetradioapp.ui.viewmodel.RadioChannelViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements BaseView, RadioChannelListAdapter.OnItemClickedListener {

    private static final String ERROR_TAG = "error";
    public static final String CHANNEL_EXTRA = "channel";

    @BindView(R.id.rv_radio_channel_list)
    RecyclerView recyclerViewRadioChannelList;

    @BindView(R.id.pb_channel_list)
    ProgressBar progressBarChannelsLoading;

    RadioChannelViewModel radioChannelViewModel;

    RadioChannelListAdapter radioChannelListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        recyclerViewRadioChannelList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRadioChannelList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        radioChannelViewModel = ViewModelProviders.of(this).get(RadioChannelViewModel.class);

        getRadioChannels();
    }

    private void getRadioChannels() {
        radioChannelViewModel.getRadioChannels(new Observer<Channels>() {
            @Override
            public void onSubscribe(Disposable d) {
                showLoading();
            }

            @Override
            public void onNext(Channels channels) {
                setChannelsList(channels.getChannels());
            }

            @Override
            public void onError(Throwable e) {
                //ideally log to an analytics sdk like Crashlytics
                Log.d(ERROR_TAG, "onError: " + e.getMessage());
                showError(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void setChannelsList(List<Channel> channelsList) {
        hideLoading();
        radioChannelListAdapter = new RadioChannelListAdapter(this, channelsList, this);
        recyclerViewRadioChannelList.setAdapter(radioChannelListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                radioChannelViewModel.getRadioChannelsByFilter(newText, new Observer<Channels>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Channels channels) {
                        setChannelsList(channels.getChannels());
                    }

                    @Override
                    public void onError(Throwable e) {
                        //log to an analytics sdk like Crashlytics
                        Log.d(ERROR_TAG, "onError: " + e.getMessage());
                        showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
                return true;
            }
        });

        return true;
    }

    @Override
    public void showLoading() {
        progressBarChannelsLoading.setVisibility(View.VISIBLE);
        recyclerViewRadioChannelList.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBarChannelsLoading.setVisibility(View.GONE);
        recyclerViewRadioChannelList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String error) {
        hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClicked(Channel channel) {
        Intent intent = new Intent(this, ChannelDetailActivity.class);
        intent.putExtra(CHANNEL_EXTRA, channel);
        startActivity(intent);
    }
}
