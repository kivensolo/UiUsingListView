package com.kingz.scroll.view_pager.sc_pagerinfo;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.View;

/**
 * Created by Samuel on 2015-07-06.
 */
public class SCViewAnimationUtil {

    public static void prepareViewToGetSize(View view) {
        view.measure( View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),  View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    public static Point getDisplaySize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        return size;
    }

}
