package com.example.draft;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SetSelectionMenuActivity extends Activity {

	private Spinner pack1Spinner, pack2Spinner, pack3Spinner;
	
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
    }

}
