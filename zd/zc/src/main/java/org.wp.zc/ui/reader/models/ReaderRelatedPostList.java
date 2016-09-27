package org.wp.zc.ui.reader.models;



import org.wp.zc.models.ReaderPost;
import org.wp.zc.models.ReaderPostList;

import java.util.ArrayList;

public class ReaderRelatedPostList extends ArrayList<ReaderRelatedPost> {

    public ReaderRelatedPostList( ReaderPostList posts) {
        for (ReaderPost post: posts) {
            add(new ReaderRelatedPost(post));
        }
    }

}
