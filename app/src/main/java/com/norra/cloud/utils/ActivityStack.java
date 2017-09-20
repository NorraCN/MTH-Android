package com.norra.cloud.utils;

import java.util.LinkedList;

import android.app.Activity;

public class ActivityStack {

    private LinkedList<Activity> mActivityArray = new LinkedList<Activity>();

    public void push(Activity a) {
        if (!mActivityArray.contains(a)) {
            mActivityArray.addFirst(a);
        }
    }

    public void remove(Activity a) {
        if (mActivityArray.contains(a)) {
            mActivityArray.remove(a);
        }
    }

    public Activity pop() {
        return mActivityArray.removeFirst();
    }

    public boolean hasNext() {
        return mActivityArray.size() > 0;
    }

    public int getCountActivity() {
        return mActivityArray.size();
    }

    public boolean isEmpty() {
        return mActivityArray.size() == 0;
    }

    public void finishAll() {
        while (hasNext()) {
            Activity a = pop();
            if (!a.isFinishing()) {
                a.finish();
            }
        }
    }

}
