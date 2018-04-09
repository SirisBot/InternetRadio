package com.software.osirisgadson.internetradioapp.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.software.osirisgadson.internetradioapp.R;
import com.software.osirisgadson.internetradioapp.data.model.Channel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RadioChannelListAdapter extends RecyclerView.Adapter<RadioChannelListAdapter.RadioChannelViewHolder> {

    private Context context;
    private List<Channel> channels;
    private OnItemClickedListener onItemClickedListener;

    public interface OnItemClickedListener extends View.OnClickListener {
        void onClicked(Channel channel);
    }

    public RadioChannelListAdapter(Context context, List<Channel> channels, OnItemClickedListener onItemClickedListener) {
        this.context = context;
        this.channels = channels;
        this.onItemClickedListener = onItemClickedListener;
    }

    @Override
    public RadioChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.channel_list_item, parent, false);
        return new RadioChannelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RadioChannelViewHolder holder, int position) {

        final Channel channel = channels.get(position);

        Picasso.get().load(channel.getImage()).into(holder.imageViewChannelThumbnail);
        holder.textViewChannelTitle.setText(channel.getTitle());
        holder.textViewChannelDesc.setText(channel.getDescription());
        holder.textViewChannelDj.setText(channel.getDj());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickedListener.onClicked(channel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    class RadioChannelViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_channel_thumbnail)
        ImageView imageViewChannelThumbnail;

        @BindView(R.id.tv_channel_title)
        TextView textViewChannelTitle;

        @BindView(R.id.tv_channel_description)
        TextView textViewChannelDesc;

        @BindView(R.id.tv_channel_dj)
        TextView textViewChannelDj;

        RadioChannelViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
