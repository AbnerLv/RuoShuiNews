package com.lzb.ruoshuinews;

import android.app.Activity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lvzhe on 2015/8/19.
 */
@EActivity(R.layout.activity_comments)
public class CommentsActivity extends Activity {

    @AfterViews
    void addCommentsAdapter() {

        List<HashMap<String, String>> commentsList = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("commentator_from", "上海网易" + i);
            hashMap.put("comment_ptime", "2015-08-1" + i);
            hashMap.put("comment_content", "这个新闻很精彩" + i);
            commentsList.add(hashMap);
        }
        SimpleAdapter commentAdpater = new SimpleAdapter(this, commentsList,
                R.layout.item_comments_list,
                new String[] { "commentator_from", "comment_ptime",
                        "comment_content" },
                new int[] { R.id.commentator_from, R.id.comment_ptime,
                        R.id.comment_content });
        ListView listViewComments = (ListView) findViewById(R.id.comments_list);
        listViewComments.setAdapter(commentAdpater);
    }
}
