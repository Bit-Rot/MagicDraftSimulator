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

	private Spinner m_pack1Spinner, m_pack2Spinner, m_pack3Spinner;
	
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.com_werbsert_draft_activity_setselectionmenuactivity);
        m_pack1Spinner = (Spinner) this.findViewById(R.id.com_werbsert_draft_activity_setselectionmenuactivity_pack1spinner);
        m_pack2Spinner = (Spinner) this.findViewById(R.id.com_werbsert_draft_activity_setselectionmenuactivity_pack2spinner);
        m_pack3Spinner = (Spinner) this.findViewById(R.id.com_werbsert_draft_activity_setselectionmenuactivity_pack3spinner);
        
        List<String> availableSets = new ArrayList<String>();
        for (CardSet set : CardSet.values()) {
        	availableSets.add(set.getCode());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, availableSets);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        m_pack1Spinner.setAdapter(adapter);
        m_pack2Spinner.setAdapter(adapter);
        m_pack3Spinner.setAdapter(adapter);

        bindListenerToButton(R.id.com_werbsert_draft_activity_setselectionmenuactivity_button1, new OnClickListener() {
    		public void onClick(View v) {
    			//Create an intent to launch
				Intent intent = new Intent(SetSelectionMenuActivity.this, DraftActivity.class);
				
				//Jam the selected sets in there
				DraftActivity.BoosterSetParcelable boosterSetParcelable = new DraftActivity.BoosterSetParcelable(
					(String)m_pack1Spinner.getSelectedItem(), 
					(String)m_pack2Spinner.getSelectedItem(), 
					(String)m_pack3Spinner.getSelectedItem());
				intent.putExtra(DraftActivity.SETS_SELECTED_PARCEL_KEY, boosterSetParcelable);
				
				//Rip it
				SetSelectionMenuActivity.this.startActivity(intent);
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