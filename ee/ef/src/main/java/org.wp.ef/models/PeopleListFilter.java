package org.wp.ef.models;



import org.wp.ef.R;
import org.wp.ef.WordPress;

public enum PeopleListFilter implements FilterCriteria {
    TEAM(R.string.people_dropdown_item_team),
    FOLLOWERS(R.string.people_dropdown_item_followers),
    EMAIL_FOLLOWERS(R.string.people_dropdown_item_email_followers),
    VIEWERS(R.string.people_dropdown_item_viewers);

    private final int mLabelResId;

    PeopleListFilter( int labelResId) {
        mLabelResId = labelResId;
    }

    @Override
    public String getLabel() {
        return WordPress.getContext().getString(mLabelResId);
    }
}
