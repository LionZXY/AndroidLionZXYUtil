package com.lionzxy.firstandroidapp.app.vk.music;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lionzxy.firstandroidapp.app.R;
import com.squareup.picasso.Picasso;

/**
 * Created by LionZXY on 12.08.2016.
 */
public class MusicViewHolder extends RecyclerView.ViewHolder {
    CardView cardView;
    TextView authorName, musicName;
    ImageView imageSave, imageDownload, musicImage;

    public MusicViewHolder(View itemView) {
        super(itemView);

        cardView = (CardView) itemView.findViewById(R.id.musicCardView);
        authorName = (TextView) itemView.findViewById(R.id.textAuthorName);
        musicName = (TextView) itemView.findViewById(R.id.textMusicName);
        imageSave = (ImageView) itemView.findViewById(R.id.imageSave);
        imageDownload = (ImageView) itemView.findViewById(R.id.imageDownload);
        musicImage = (ImageView) itemView.findViewById(R.id.musicImage);

    }

    public MusicViewHolder setItem(@NonNull final MusicObject musicObject) {
        if (musicObject == null)
            return this;

        authorName.setText(musicObject.getAuthorName());
        musicName.setText(musicObject.getMusicName());
        if (musicObject.checkCached()) {
            imageSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Аудио уже кешированно. Скачивать повторно не нужно", Toast.LENGTH_LONG).show();
                }
            });
            imageSave.setVisibility(View.VISIBLE);
        } else imageSave.setVisibility(View.GONE);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), musicObject.getCoverURL(), Toast.LENGTH_LONG).show();
                Log.i("View", TextUtils.isEmpty(musicObject.getCoverURL()) ? "Пусто" : musicObject.getCoverURL());
            }
        });

        if (!TextUtils.isEmpty(musicObject.getCoverURL()))
            Picasso.with(musicImage.getContext()).load(musicObject.getCoverURL()).error(R.drawable.notfoundmusic).into(musicImage);
        else musicImage.setImageResource(R.drawable.notfoundmusic);

        return this;
    }


}
