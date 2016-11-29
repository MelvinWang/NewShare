package com.melvin.share.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ScrollView;

import com.melvin.share.Utils.LogUtils;

/**
 * @author Melvin
 */
public class ScrollRecyclerView extends RecyclerView {

    public ScrollRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollRecyclerView(Context context) {
        super(context);
    }

}
