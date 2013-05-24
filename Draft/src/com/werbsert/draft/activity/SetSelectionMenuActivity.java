package com.werbsert.draft.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.werbsert.draft.R;
import com.werbsert.draftcommon.model.CardSet;

public class SetSelectionMenuActivity extends Activity {

	private Spinner pack1Spinner, pack2Spinner, pack3Spinner;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.com_werbsert_draft_activity_setselectionmenuactivity);
        pack1Spinner = (Spinner) this.findViewById(R.id.com_werbsert_draft_activity_setselectionmenuactivity_pack1spinner);
        pack2Spinner = (Spinner) this.findViewById(R.id.com_werbsert_draft_activity_setselectionmenuactivity_pack2spinner);
        pack3Spinner = (Spinner) this.findViewById(R.id.com_werbsert_draft_activity_setselectionmenuactivity_pack3spinner);
        
        List<String> availableSets = new ArrayList<String>();
        for (CardSet set : CardSet.values()) {
        	availableSets.add(set.getShortName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, availableSets);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pack1Spinner.setAdapter(adapter);
        pack2Spinner.setAdapter(adapter);
        pack3Spinner.setAdapter(adapter);

        bindListenerToButton(R.id.com_werbsert_draft_activity_setselectionmenuactivity_button1, new OnClickListener() {
    		public void onClick(View v) {
				Intent CardSelectionIntent = new Intent(SetSelectionMenuActivity.this, BoosterViewActivity.class);
				SetSelectionMenuActivity.this.startActivity(CardSelectionIntent);
			}
		});

        bindListenerToButton(R.id.com_werbsert_draft_activity_setselectionmenuactivity_button2, new OnClickListener() {
    		public void onClick(View v) {
				Intent CardSelectionIntent = new Intent(SetSelectionMenuActivity.this, CardCollectionViewActivity.class);
				SetSelectionMenuActivity.this.startActivity(CardSelectionIntent);
			}
		});
    }
    
    public void bindListenerToButton(int buttonId, OnClickListener listener) {
    	Button draftStartButton = (Button)findViewById(buttonId);
    	draftStartButton.setOnClickListener(listener);
    }
}