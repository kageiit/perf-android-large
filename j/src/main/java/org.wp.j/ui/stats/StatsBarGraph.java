package org.wp.j.ui.stats;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import org.wp.j.R;

import java.util.LinkedList;
import java.util.List;

/**
 * A Bar graph depicting the view and visitors.
 * Based on BarGraph from the GraphView library.
 */
class StatsBarGraph extends View {

    private static final int DEFAULT_MAX_Y = 10;

    // Keep tracks of every bar drawn on the graph.
    private final List<List<BarChartRect>> mSeriesRectsDrawedOnScreen = (List<List<BarChartRect>>) new LinkedList();
    private int mBarPositionToHighlight = -1;
    private boolean[] mWeekendDays;

    private final GestureDetectorCompat mDetector;
    private OnGestureListener mGestureListener;

    public StatsBarGraph(Context context) {
        super(context);

//        int width = LayoutParams.MATCH_PARENT;
//        int height = getResources().getDimensionPixelSize(R.dimen.stats_barchart_height);
//        setLayoutParams(new LayoutParams(width, height));

        setProperties();

//        paint.setTypeface(TypefaceCache.getTypeface(getContext()));

        mDetector = new GestureDetectorCompat(getContext(), new MyGestureListener());
        mDetector.setIsLongpressEnabled(false);
    }

    public void setGestureListener(OnGestureListener listener) {
        this.mGestureListener = listener;
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            highlightBarAndBroadcastDate();
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            highlightBarAndBroadcastDate();
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        private void highlightBarAndBroadcastDate() {
            int tappedBar = 0;
            //AppLog.d(AppLog.T.STATS, this.getClass().getName() + " Tapped bar " + tappedBar);
            if (tappedBar >= 0) {
                highlightBar(tappedBar);
                if (mGestureListener != null) {
                    mGestureListener.onBarTapped(tappedBar);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        boolean handled = super.onTouchEvent(event);
        if (mDetector != null && handled) {
            this.mDetector.onTouchEvent(event);
        }
       return handled;
    }

    private class HorizontalLabelsColor {
        public int get(int index) {
            if (mBarPositionToHighlight == index) {
                return getResources().getColor(R.color.orange_jazzy);
            } else {
                return getResources().getColor(R.color.grey_darken_30);
            }
        }
    }

    private void setProperties() {

    }

//    @Override
    protected void onBeforeDrawSeries() {
        mSeriesRectsDrawedOnScreen.clear();
    }
    public void setWeekendDays(boolean[] days) {
        mWeekendDays = days;
    }

    public void highlightBar(int barPosition) {
        mBarPositionToHighlight = barPosition;
//        this.redrawAll();
    }

    public int getHighlightBar() {
        return mBarPositionToHighlight;
    }

    public void resetHighlightBar() {
        mBarPositionToHighlight = -1;
    }

//    @Override
    protected double getMinY() {
        return 0;
    }

    // Make sure the highest number is always even, so the halfway mark is correctly balanced in the middle of the graph
    // Also make sure to display a default value when there is no activity in the period.
//    @Override
    protected double getMaxY() {
        double maxY = 0;
        if (maxY == 0) {
            return DEFAULT_MAX_Y;
        }

        return maxY + (maxY % 2);
    }

    /**
     * Private class that is used to hold the local (to the canvas) coordinate on the screen
     * of every single bar in the graph
     */
    private class BarChartRect {
        final float mLeft;
        final float mTop;
        final float mRight;
        final float mBottom;

        BarChartRect(float left, float top, float right, float bottom) {
            this.mLeft = left;
            this.mTop = top;
            this.mRight = right;
            this.mBottom = bottom;
        }

        /**
         * Check if the tap happens on a bar in the graph.
         *
         * @return true if the tap point falls within the bar for the X coordinate, and within the full canvas
         * height for the Y coordinate. This is a fix to make very small bars tappable.
         */
        public boolean isPointInside(float x, float y) {
            return x >= this.mLeft
                    && x <= this.mRight;
        }
    }

    interface OnGestureListener {
        void onBarTapped(int tappedBar);
    }
}
