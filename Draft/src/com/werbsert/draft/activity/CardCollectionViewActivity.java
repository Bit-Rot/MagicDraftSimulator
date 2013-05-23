package com.werbsert.draft.activity;


import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.werbsert.draft.R;
import com.werbsert.draft.model.CardCollection;
import com.werbsert.draft.service.CardService;
import com.werbsert.draft.view.FullScreenImage;
import com.werbsert.draft.view.ImageAdapter;
import com.werbsert.draftcommon.model.Card;
import com.werbsert.draftcommon.model.CardSet;

/**
 * Text activity for viewing a generic collection of cards.  Not to be used in release build.
 *
 * @author Andrew
 */
public class CardCollectionViewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.com_werbsert_draft_activity_boosterviewactivity);

        List<Card> entireSet = CardService.getInstance().getCardsBySet(CardSet.RTR);
        CardCollection cardCollection = new CardCollection(entireSet);
        
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, cardCollection));
        gridview.setOnItemClickListener(new OnItemClickListener(){
    		public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
					int imageId = (int) parent.getAdapter().getItemId(position);
					Intent fullScreenIntent = new Intent(v.getContext(),FullScreenImage.class);
					fullScreenIntent.putExtra("imageId",imageId);
					fullScreenIntent.putExtra("multiverseId", id);
					CardCollectionViewActivity.this.startActivity(fullScreenIntent); 
					
				}
			});
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_booster_view, menu);
        return true;
    }
}
