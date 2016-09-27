package org.wp.x.ui.reader.models;



import org.wp.x.models.ReaderPost;
import org.wp.x.models.ReaderPostList;

import java.util.ArrayList;

public class ReaderRelatedPostList extends ArrayList<ReaderRelatedPost> {

    public ReaderRelatedPostList( ReaderPostList posts) {
        for (ReaderPost post: posts) {
            add(new ReaderRelatedPost(post));
        }
    }

}
