package com.werbsert.draft.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.werbsert.draft.R;
import com.werbsert.draft.model.CardCollection;
import com.werbsert.draft.service.CardService;
import com.werbsert.draft.view.CardCollectionAdapter;
import com.werbsert.draftcommon.model.Card;
import com.werbsert.draftcommon.model.CardSet;
import com.werbsert.draftcommon.model.SerializedCard;

/**
 * Text activity for viewing a generic collection of cards.  Not to be used in release build.
 *
 * @author Andrew
 */
public class CardCollectionViewActivity extends Activity {
	
	public static final String CARD_COLLECTION_PARCEL_KEY = "CardCollection";
	public static final String CARD_SELECTED_PARCEL_KEY = "CardSelected";
	
	public static final int RESULT_CARD_SELECTED = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.com_werbsert_draft_activity_boosterviewactivity);

    	//Pull the card collection from the given intent
    	Bundle bundle = getIntent().getExtras();
    	CardCollectionParcelable cardCollectionParcelable = (CardCollectionParcelable)bundle.getParcelable(CARD_COLLECTION_PARCEL_KEY);
    	if (cardCollectionParcelable == null) {
    		//TODO: Handle errors gracefully
    	}
    	
    	//Pull selected card and remaining cards from booster pack
    	SerializedCard[] serializedCards = cardCollectionParcelable.getCards();
    	CardCollection boosterPack = new CardCollection();
    	for (SerializedCard serializedCard : serializedCards) {
    		boosterPack.addCard(CardService.getInstance().getCard(CardSet.getSet(serializedCard.getSetCode()), serializedCard.getId()));
    	}
        
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new CardCollectionAdapter(this, boosterPack));
        gridview.setOnItemClickListener(new OnItemClickListener(){
    		public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
    			
//				int imageId = (int) parent.getAdapter().getItemId(position);
//				Intent fullScreenIntent = new Intent(v.getContext(),FullScreenImageActivity.class);
//				fullScreenIntent.putExtra("imageId",imageId);
//				fullScreenIntent.putExtra("multiverseId", id);
//				CardCollectionViewActivity.this.startActivity(fullScreenIntent);
    			
    			//Identify selected card and remaining cards
    			CardCollectionAdapter adapter = (CardCollectionAdapter)parent.getAdapter();
    			CardCollection boosterPack = adapter.getCards();
			
				//For now, we'll just select the clicked card, no fancy zoom functionality
				Intent intent = new Intent();
				intent.putExtra(CARD_SELECTED_PARCEL_KEY, new CardSelectedParcelable(boosterPack, position));
				setResult(RESULT_CARD_SELECTED, intent);     
				finish();
			}
		});
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.com_werbsert_draft_activity_boosterviewactivity, menu);
        return true;
    }
	
	/**
	 * Shit's a Parcelable for card collections.  Nuff' said.
	 */
	public static class CardCollectionParcelable implements Parcelable {
		int m_size;
		SerializedCard[] m_cards;

		public SerializedCard[] getCards() {
			return m_cards;
		}

		public void setCards(SerializedCard[] cards) {
			m_cards = cards;
		}

		public int getSize() {
			return m_size;
		}

		public void setSize(int size) {
			m_size = size;
		}

		public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel out, int flags) {
        	out.writeInt(m_size);
            for (SerializedCard card : m_cards) {
                out.writeSerializable(card);
            }
        }

        public static final Parcelable.Creator<CardCollectionParcelable> CREATOR = new Parcelable.Creator<CardCollectionParcelable>() {
            public CardCollectionParcelable createFromParcel(Parcel in) {
                return new CardCollectionParcelable(in);
            }

            public CardCollectionParcelable[] newArray(int size) {
                return new CardCollectionParcelable[size];
            }
        };
        
        private CardCollectionParcelable(Parcel in) {
            int m_size = in.readInt();
            m_cards = new SerializedCard[m_size];
            
            for (int i=0; i < m_size; i++) {
                m_cards[i] = (SerializedCard)in.readSerializable();
            }
        }
        
        public CardCollectionParcelable(CardCollection cards) {
        	m_size = cards.getSize();
        	m_cards = new SerializedCard[m_size];
        	int i=0;
        	for (Card card : cards.getCards()) {
        		m_cards[i++] = new SerializedCard(card);
        	}
        }
    }
	
	/**
	 * Shit's a Parcelable for selecting cards.  Nuff' said.
	 */
	public static class CardSelectedParcelable implements Parcelable {
		int m_size;
		SerializedCard[] m_cards;
		int m_selectionPosition;

		public SerializedCard[] getCards() {
			return m_cards;
		}

		public void setCards(SerializedCard[] cards) {
			m_cards = cards;
		}

		public int getSize() {
			return m_size;
		}

		public void setSize(int size) {
			m_size = size;
		}

		public int getSelectionPosition() {
			return m_selectionPosition;
		}

		public void setSelectionPosition(int selectionPosition) {
			m_selectionPosition = selectionPosition;
		}

		public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel out, int flags) {
        	out.writeInt(m_size);
            for (SerializedCard card : m_cards) {
                out.writeSerializable(card);
            }
            out.writeInt(m_selectionPosition);
        }

        public static final Parcelable.Creator<CardSelectedParcelable> CREATOR = new Parcelable.Creator<CardSelectedParcelable>() {
            public CardSelectedParcelable createFromParcel(Parcel in) {
                return new CardSelectedParcelable(in);
            }

            public CardSelectedParcelable[] newArray(int size) {
                return new CardSelectedParcelable[size];
            }
        };
        
        private CardSelectedParcelable(Parcel in) {
            int m_size = in.readInt();
            m_cards = new SerializedCard[m_size];
            
            for (int i=0; i < m_size; i++) {
                m_cards[i] = (SerializedCard)in.readSerializable();
            }
            m_selectionPosition = (int)in.readInt();
        }
        
        public CardSelectedParcelable(CardCollection cards, int selectionPosition) {
        	m_size = cards.getSize();
        	m_cards = new SerializedCard[m_size];
        	int i=0;
        	for (Card card : cards.getCards()) {
        		m_cards[i++] = new SerializedCard(card);
        	}
        	m_selectionPosition = selectionPosition;
        }
    }
}
