package org.wp.r.ui.posts.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.wp.r.R;
import org.wp.r.models.PostStatus;
import org.wp.r.models.PostsListPost;
import org.wp.r.widgets.PostListButton;

import java.util.ArrayList;
import java.util.List;

/*
 * adapter for the popup menu that appears when clicking "..." in the pages list - each item
 * in the menu item array is an integer that matches a specific PostListButton button type
 */
public class PageMenuAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private final List<Integer> mMenuItems = new ArrayList<>();

    public PageMenuAdapter(Context context,  PostsListPost page) {
        super();
        mInflater = LayoutInflater.from(context);

        boolean showViewItem = !page.isLocalDraft() && page.getStatusEnum() == PostStatus.PUBLISHED;
        boolean showStatsItem = !page.isLocalDraft() && page.getStatusEnum() == PostStatus.PUBLISHED;
        boolean showTrashItem = !page.isLocalDraft();
        boolean showDeleteItem = !showTrashItem;

        // edit item always appears
        mMenuItems.add(PostListButton.BUTTON_EDIT);

        if (showViewItem) {
            mMenuItems.add(PostListButton.BUTTON_VIEW);
        }
        if (showStatsItem) {
            mMenuItems.add(PostListButton.BUTTON_STATS);
        }
        if (showTrashItem) {
            mMenuItems.add(PostListButton.BUTTON_TRASH);
        }
        if (showDeleteItem) {
            mMenuItems.add(PostListButton.BUTTON_DELETE);
        }
    }

    @Override
    public int getCount() {
        return mMenuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mMenuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mMenuItems.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PageMenuHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.popup_menu_item, parent, false);
            holder = new PageMenuHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (PageMenuHolder) convertView.getTag();
        }

        int buttonType = mMenuItems.get(position);
        holder.text.setText(PostListButton.getButtonTextResId(buttonType));
        holder.icon.setImageResource(PostListButton.getButtonIconResId(buttonType));

        return convertView;
    }

    class PageMenuHolder {
        private final TextView text;
        private final ImageView icon;

        PageMenuHolder(View view) {
            text = (TextView) view.findViewById(R.id.text);
            icon = (ImageView) view.findViewById(R.id.image);
        }
    }
}
