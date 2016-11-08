package com.example.santiagolopezgarcia.subastapp.helpers;

import android.util.*;

import timber.log.Timber;

/**
 * Created by santiagolopezgarcia on 28/10/16.
 */

public class ReleaseTree extends Timber.Tree {

    private static final int MAXIMA_LONGITUD = 4000;

    @Override
    protected boolean isLoggable(int priority) {
//        if (priority == Log.DEBUG) {
//            return false;
//        }
        return true;
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {

        if (priority == android.util.Log.ERROR && t != null) {
            //Crashlytics.log(e);
        }

        if (isLoggable(priority)) {
            if (message.length() < MAXIMA_LONGITUD) {
                if (priority == android.util.Log.ASSERT) {
                    android.util.Log.wtf(tag, message);
                } else {
                    android.util.Log.println(priority, tag, message);
                }
                return;
            }

            for (int i = 0, length = message.length(); i < length; i++) {
                int newline = message.indexOf('\n', i);
                newline = newline != -1 ? newline : length;
                do {
                    int end = Math.min(newline, i + MAXIMA_LONGITUD);
                    String part = message.substring(i, end);
                    if (priority == android.util.Log.ASSERT) {
                        android.util.Log.wtf(tag, part);
                    } else {
                        android.util.Log.println(priority, tag, part);
                    }
                    i = end;
                } while (i < newline);
            }
        }
    }
}