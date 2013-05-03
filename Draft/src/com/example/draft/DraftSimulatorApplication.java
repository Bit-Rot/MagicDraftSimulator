package com.example.draft;

import android.content.Context;

public class DraftSimulatorApplication extends android.app.Application {

    private static DraftSimulatorApplication s_instance;

    /**
     * Called when application is launched.  Used to set up static context.
     */
    public DraftSimulatorApplication() {
    }

    public void onCreate() {
    	super.onCreate();
    	s_instance = this;
    }
    
    public static Context getContext() {
    	return s_instance;
    }
}
