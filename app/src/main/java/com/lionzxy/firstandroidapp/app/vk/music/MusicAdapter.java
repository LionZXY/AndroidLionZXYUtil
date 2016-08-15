package com.lionzxy.firstandroidapp.app.vk.music;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lionzxy.firstandroidapp.app.R;

import java.util.List;

/**
 * Created by LionZXY on 12.08.2016.
 */
public class MusicAdapter extends RecyclerView.Adapter<MusicViewHolder> {
    private List<MusicObject> musicObjects;

    public MusicAdapter(List<MusicObject> musicObjects) {
        super();
        this.musicObjects = musicObjects;
    }

    @Override
    public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.music_item, parent, false);
        return new MusicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MusicViewHolder holder, int position) {
        holder.setItem(musicObjects.get(position));
    }

    public void add(MusicObject musicObject) {
        musicObjects.add(musicObject);
        notifyItemInserted(musicObjects.size() - 1);
    }

    @Override
    public int getItemCount() {
        return musicObjects.size();
    }
}
