package org.wp.ab.ui.reader.models;



import org.wp.ab.models.ReaderPost;
import org.wp.ab.models.ReaderPostList;

import java.util.ArrayList;

public class ReaderRelatedPostList extends ArrayList<ReaderRelatedPost> {

    public ReaderRelatedPostList( ReaderPostList posts) {
        for (ReaderPost post: posts) {
            add(new ReaderRelatedPost(post));
        }
    }

}
