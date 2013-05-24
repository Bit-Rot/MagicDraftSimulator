package com.werbsert.draft.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.werbsert.draft.BoosterPackGenerator;
import com.werbsert.draft.R;
import com.werbsert.draft.model.CardCollection;
import com.werbsert.draft.view.FullScreenImage;
import com.werbsert.draft.view.ImageAdapter;
import com.werbsert.draftcommon.model.CardSet;

public class BoosterViewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.com_werbsert_draft_activity_boosterviewactivity);

        CardCollection boosterPack = BoosterPackGenerator.getBoosterPack(CardSet.RTR);
        
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, boosterPack));
        gridview.setOnItemClickListener(new OnItemClickListener(){
    		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				int imageId = (int) parent.getAdapter().getItemId(position);
				Intent fullScreenIntent = new Intent(v.getContext(),FullScreenImage.class);
				fullScreenIntent.putExtra("imageId",imageId);
				BoosterViewActivity.this.startActivity(fullScreenIntent);
			}
		});
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.com_werbsert_draft_activity_boosterviewactivity, menu);
        return true;
    }
}
