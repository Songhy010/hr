package biz.bizsolution.hrportal.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by songhy on 03/12/19.
 */
public class MyViewPagerWithPhotoView extends ViewPager {

    public MyViewPagerWithPhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        try {
            return super.onInterceptTouchEvent(event);
        } catch (Exception e) {
            return false;
        }
    }
}
