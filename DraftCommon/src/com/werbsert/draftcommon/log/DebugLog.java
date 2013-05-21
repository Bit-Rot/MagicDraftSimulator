package com.werbsert.draftcommon.log;
import android.util.Log;

public class DebugLog
{
	//TODO: replace with a more sophistocated mechanism.  Perhaps System.getSystemProperty().
    public final static boolean DEBUG = true;

    public static void log(String message)
    {
        if (DEBUG)
        {
            String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();            
            String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
            int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();

            Log.d(className + "." + methodName + "():" + lineNumber, message);
        }
    }
}