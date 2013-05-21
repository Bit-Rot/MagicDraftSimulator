package com.werbsert.testdraftset;

import com.werbsert.draftcommon.log.DebugLog;

import android.content.Context;

public class TestDraftSetApplication extends android.app.Application {

    private static TestDraftSetApplication s_instance;

    /**
     * Called when application is launched.  Used to set up static context.
     */
    public TestDraftSetApplication() {
    }

    public void onCreate() {
    	DebugLog.log("Entering onCreate()");
    	super.onCreate();
    	s_instance = this;
    }
    
    public static Context getContext() {
    	return s_instance;
    }
}
