package org.wp.zb.ui.reader.models;



import org.wp.zb.models.ReaderPost;
import org.wp.zb.models.ReaderPostList;

import java.util.ArrayList;

public class ReaderRelatedPostList extends ArrayList<ReaderRelatedPost> {

    public ReaderRelatedPostList( ReaderPostList posts) {
        for (ReaderPost post: posts) {
            add(new ReaderRelatedPost(post));
        }
    }

}
