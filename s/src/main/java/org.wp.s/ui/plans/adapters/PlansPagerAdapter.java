package org.wp.s.ui.plans.adapters;

import android.app.FragmentManager;

import org.wp.s.ui.plans.PlanFragment;
import org.wp.s.ui.plans.models.Plan;
import org.wp.s.util.AppLog;

/**
 * ViewPager adapter for the main plans activity
 */
public class PlansPagerAdapter {
    private final Plan[] mSitePlans;
    private static final String UNICODE_CHECKMARK = "\u2713";

    public PlansPagerAdapter(FragmentManager fm,  Plan[] sitePlans) {
        mSitePlans = sitePlans.clone();
    }

    public PlanFragment getItem(int position) {
        return PlanFragment.newInstance(mSitePlans[position]);
    }

    public int getCount() {
        return mSitePlans.length;
    }

    public CharSequence getPageTitle(int position) {
        if (isValidPosition(position)) {
            Plan planDetails = mSitePlans[position];
            if (planDetails == null) {
                AppLog.w(AppLog.T.PLANS, "plans pager > empty plan details in getPageTitle");
                return "";
            } else if (mSitePlans[position].isCurrentPlan()) {
                return UNICODE_CHECKMARK + " " + planDetails.getProductNameShort();
            } else {
                return planDetails.getProductNameShort();
            }
        }
        return null;
    }

    public boolean isValidPosition(int position) {
        return (position >= 0 && position < getCount());
    }

    public int getPositionOfPlan(long planID) {
        for (int i = 0; i < getCount(); i++) {
            if (mSitePlans[i].getProductID() == planID) {
                return i;
            }
        }
        return -1;
    }

}
