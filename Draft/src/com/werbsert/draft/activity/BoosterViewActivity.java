package com.werbsert.draft.activity;


import com.example.draft.R;
import com.werbsert.draft.BoosterPackGenerator;
import com.werbsert.draft.db.CardDatabase;
import com.werbsert.draft.model.CardCollection;
import com.werbsert.draft.model.CardSet;
import com.werbsert.draft.view.FullScreenImage;
import com.werbsert.draft.view.ImageAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

public class BoosterViewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Initialize database with application context
        //TODO: Right now this activity is the splash page.  If this changes, you MUST move this call to the first activity launched.
        CardDatabase.getInstance().init(this);
        
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_booster_view);
        
        CardCollection boosterPack = BoosterPackGenerator.getBoosterPack(CardSet.RTR);
        
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, boosterPack));

        gridview.setOnItemLongClickListener(new OnItemLongClickListener() {
        		public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
			    	int imageId = (int) parent.getAdapter().getItemId(position);

			        Intent fullScreenIntent = new Intent(v.getContext(),FullScreenImage.class);
			        fullScreenIntent.putExtra("imageId",imageId);
			        BoosterViewActivity.this.startActivity(fullScreenIntent); 
			        return true;
			    }
			});
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_booster_view, menu);
        return true;
    }
}
