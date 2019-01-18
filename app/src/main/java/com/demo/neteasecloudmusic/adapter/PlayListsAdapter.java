package com.demo.neteasecloudmusic.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.neteasecloudmusic.R;
import com.demo.neteasecloudmusic.gson.playList.PlayList;

import java.util.List;


public class PlayListsAdapter extends RecyclerView.Adapter<PlayListsAdapter.ViewHolder> {
    private List<PlayList> mPlayLists;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView playlistImage;
        TextView playlistName, playCount;


        public ViewHolder(View view) {
            super(view);
            playlistImage = (ImageView) view.findViewById(R.id.playlist_image);
            playlistName = (TextView) view.findViewById(R.id.playlist_Name);
            playCount = (TextView) view.findViewById(R.id.play_count);

        }
    }

    public PlayListsAdapter(List<PlayList> playLists) {
        mPlayLists = playLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.playlist_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlayList playList = mPlayLists.get(position);
        Glide.with(mContext).load(playList.ImageUrl).into(holder.playlistImage);
        holder.playlistName.setText(playList.playListName);
        holder.playCount.setText(playList.trackCount + "首,播放" + playList.playCount + "次");
    }


    @Override
    public int getItemCount() {
        return mPlayLists.size();
    }

}
