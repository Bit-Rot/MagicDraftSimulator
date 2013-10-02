package com.werbsert.draft.view;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.handlerexploit.prime.widgets.RemoteImageView;
import com.werbsert.draft.DraftSimulatorApplication;
import com.werbsert.draft.model.CardCollection;
import com.werbsert.draft.util.CardImageManager;
import com.werbsert.draft.util.FileManager;
import com.werbsert.draftcommon.model.Card;

public class CardCollectionAdapter extends BaseAdapter {
    private Context m_context;
    private CardCollection m_cards;

    public CardCollectionAdapter(Context c, CardCollection boosterPack) {
        this.m_context = c;
        this.m_cards = boosterPack;
    }

    public int getCount() {
        return this.m_cards.getSize();
    }

    public CardCollection getCards() {
		return m_cards;
	}

	public Card getItem(int position) {
    	return this.m_cards.getCard(position);
    }

    public long getItemId(int position) {
    	return m_context.getResources().getIdentifier("mid_" + m_cards.getCard(position).getMultiverseId(), "drawable", "com.werbsert.draft");
    }
    
    public String getImageUrl(int position) {
    	return m_cards.getCard(position).getImageUrl().toString();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

    	//Recycle content if possible
    	if (convertView != null) {
            return convertView;
        }
    	
    	//Pull card and attempt to pull existing card image
    	final Card card = getItem(position);
    	File cardImage = CardImageManager.getInstance().getImage(card.getMultiverseId());
    	
    	ImageView view = null;
    	if (cardImage != null) {
    		//Use the existing card image, shove it in a view
        	ImageView imageView = new ImageView(DraftSimulatorApplication.getContext());

            Bitmap bitmap = BitmapFactory.decodeFile(cardImage.getAbsolutePath());
            imageView.setImageBitmap(bitmap);
            
        	view = imageView;
    	} else {
    		//Use remote image view
        	RemoteImageView imageView;
            imageView = new RemoteImageView(m_context);
            imageView.setImageURL(card.getImageUrl());

            //Save the file once we've loaded that shit
            view = imageView;
            imageView.setOnImageReceived(new RemoteImageView.OnImageReceivedListener() {
				public void onImageReceived(RemoteImageView view) {
					Bitmap bitmap = ((BitmapDrawable)view.getDrawable()).getBitmap();
					FileManager.getInstance().saveBitmapAsPng(bitmap, card.getMultiverseId() + ".png");
				}
			});
    	}
        
        //Set some default parameters
    	view.setLayoutParams(new GridView.LayoutParams(100, 150));
    	view.setScaleType(ImageView.ScaleType.CENTER_CROP);
    	view.setPadding(8, 8, 8, 8);
    	
    	//Snap!
        return view;
    }
}