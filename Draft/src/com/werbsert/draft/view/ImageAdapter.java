package com.werbsert.draft.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.handlerexploit.prime.widgets.RemoteImageView;
import com.werbsert.draft.model.CardCollection;

public class ImageAdapter extends BaseAdapter {
    private Context m_context;
    private CardCollection m_cards;

    public ImageAdapter(Context c, CardCollection boosterPack) {
        this.m_context = c;
        this.m_cards = boosterPack;
    }

    public int getCount() {
        return this.m_cards.getSize();
    }

    public Object getItem(int position) {
    	return this.m_cards.getCard(position);
    }

    public long getItemId(int position) {
    	return m_context.getResources().getIdentifier("mid_" + m_cards.getCard(position).getMultiverseId(), "drawable", "com.werbsert.draft");
    }
    
    public String getImageUrl(int position) {
    	return m_cards.getCard(position).getImageUrl().toString();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    	RemoteImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new RemoteImageView(m_context);
            imageView.setLayoutParams(new GridView.LayoutParams(100, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (RemoteImageView) convertView;
        }
        imageView.setImageURL(getImageUrl(position));
        
        return imageView;
    }
}