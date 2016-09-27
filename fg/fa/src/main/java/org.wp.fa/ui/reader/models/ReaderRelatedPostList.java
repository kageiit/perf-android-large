package org.wp.fa.ui.reader.models;



import org.wp.fa.models.ReaderPost;
import org.wp.fa.models.ReaderPostList;

import java.util.ArrayList;

public class ReaderRelatedPostList extends ArrayList<ReaderRelatedPost> {

    public ReaderRelatedPostList( ReaderPostList posts) {
        for (ReaderPost post: posts) {
            add(new ReaderRelatedPost(post));
        }
    }

}
