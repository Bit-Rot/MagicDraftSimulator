package com.werbsert.draft.activity;

import java.util.ArrayList;
import java.util.List;

import com.werbsert.draft.R;

import com.werbsert.draftcommon.model.CardSet;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.view.View.OnClickListener;

public class SetSelectionMenuActivity extends Activity {

	private Spinner pack1Spinner, pack2Spinner, pack3Spinner;
	private Button draftStartButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);        
        setContentView(R.layout.draft_set_selection_view);
        pack1Spinner = (Spinner) this.findViewById(R.id.pack1Spinner);
        pack2Spinner = (Spinner) this.findViewById(R.id.pack2Spinner);
        pack3Spinner = (Spinner) this.findViewById(R.id.pack3Spinner);
        
        List<String> availableSets = new ArrayList<String>();
        for (CardSet set : CardSet.values()) {
        	availableSets.add(set.getShortName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, availableSets);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pack1Spinner.setAdapter(adapter);
        pack2Spinner.setAdapter(adapter);
        pack3Spinner.setAdapter(adapter);
        
        ButtonListener();
        
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("content");
        uriBuilder.authority("com.werbsert.testdraftset.testprovider");
        uriBuilder.path("");
        uriBuilder.query("");
        
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(
        		uriBuilder.build(), 
        		new String[] {"_ID", "VALUE"},
        		"", 
        		null, 
        		null);
        
        if (cursor != null && cursor.getCount() > 0) {
        	while (cursor.moveToNext()) {
		        Long myLong = cursor.getLong(0);
		        String myString = cursor.getString(1);
		        
		        Log.i("INFO: ", "id = " + myLong + " & value = " + myString);
        	}
        }
    }
    public void ButtonListener(){
    	draftStartButton = (Button)findViewById(R.id.button1);
    	draftStartButton.setOnClickListener(new OnClickListener(){
    		
    		
    		public void onClick(View v){
    				Intent CardSelectionIntent = new Intent(SetSelectionMenuActivity.this, BoosterViewActivity.class);
    				SetSelectionMenuActivity.this.startActivity(CardSelectionIntent);
    		}
    	});
    }
}


/*
ToDo:
	add button that switches to the next view and generates the Boosters.
*/