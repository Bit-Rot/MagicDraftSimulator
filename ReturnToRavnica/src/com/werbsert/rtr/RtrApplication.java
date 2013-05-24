package com.werbsert.rtr;

import com.werbsert.draftcommon.log.DebugLog;

import android.content.Context;

public class RtrApplication extends android.app.Application {

    private static RtrApplication s_instance;

    /**
     * Called when application is launched.  Used to set up static context.
     */
    public RtrApplication() {
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
