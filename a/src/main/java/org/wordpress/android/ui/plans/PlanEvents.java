package org.wordpress.android.ui.plans;



import org.wordpress.android.ui.plans.models.Plan;

import java.util.List;

/**
 * Plan-related EventBus event classes
 */
class PlanEvents {

    public static class PlansUpdated {
        private final List<Plan> mPlans;
        private final int mLocalBlogId;
        public PlansUpdated(int localBlogId,  List<Plan> plans) {
            mLocalBlogId = localBlogId;
            mPlans = plans;
        }
        public int getLocalBlogId() {
            return mLocalBlogId;
        }
        public List<Plan> getPlans() {
            return mPlans;
        }
    }

    public static class PlansUpdateFailed { }
}
