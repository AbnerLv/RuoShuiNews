package com.lzb.ruoshuinews;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lzb.ruoshuinews.adapter.CustomSimpleAdapter;
import com.lzb.utils.DpDipUtil;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private final int COLUMNWIDTHPX = 100;
    private final int FLINGVELOCITYPX = 800; // 滚动距离
    @StringArrayRes(R.array.categories)
    String[] categoryArray; // 获取新闻分类数据
    @ViewById(R.id.category_scrollview)
    HorizontalScrollView categoryScrollview; // 箭头
    @ViewById(R.id.news_list)
    ListView newlist;
    private int mColumnWidthDip;
    private int mFlingVelocityDip;

    @AfterInject
    public void px2dip() {
        mColumnWidthDip = DpDipUtil.px2dip(this, COLUMNWIDTHPX);
        mFlingVelocityDip = DpDipUtil.px2dip(this, FLINGVELOCITYPX);
    }

    @Click(R.id.category_arrow_right)
    void categoryArrowRightClicked() {
        categoryScrollview.fling(mFlingVelocityDip);
    }

    @AfterViews
    public void newsCategoriesDataToView() {

        // 把新闻分类保存到List中
        List<HashMap<String, String>> categories = new ArrayList<>();
        for (int i = 0; i < categoryArray.length; i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("category_title", categoryArray[i]);
            categories.add(hashMap);
        }
        // 创建Adapter，指明映射字段
        CustomSimpleAdapter categoryAdapter = new CustomSimpleAdapter(this,
                categories, R.layout.category_title,
                new String[] { "category_title" },
                new int[] { R.id.category_title });

        GridView category = new GridView(this);
        category.setColumnWidth(mColumnWidthDip);// 每个单元格宽度
        category.setNumColumns(GridView.AUTO_FIT);// 单元格数目
        category.setGravity(Gravity.CENTER);// 设置对其方式
        // 设置单元格选择是背景色为透明，这样选择时就不现实黄色
        category.setSelector(new ColorDrawable(Color.TRANSPARENT));
        int width = mColumnWidthDip * categories.size();// 根据单元格宽度和数目计算总宽度
        LayoutParams params = new LayoutParams(width, LayoutParams.MATCH_PARENT);
        category.setLayoutParams(params);
        category.setAdapter(categoryAdapter);
        LinearLayout categoryList = (LinearLayout) findViewById(R.id.category_layout);
        categoryList.addView(category);
        category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                TextView categoryTitle = null;
                for (int i = 0; i < parent.getChildCount(); i++) {
                    categoryTitle = (TextView) parent.getChildAt(i);
                    categoryTitle.setBackgroundDrawable(null);
                    categoryTitle.setTextColor(0XFFADB2AD);
                }
                categoryTitle = (TextView) view;
                updateUi(categoryTitle);
            }
        });
    }

    @UiThread
    public void updateUi(TextView textView) {
        textView.setBackgroundResource(R.drawable.categorybar_item_background);
        textView.setTextColor(0XFFFFFFFF);
        Toast.makeText(MainActivity.this, textView.getText(), Toast.LENGTH_LONG)
                .show();
    }

    @AfterViews
    public void fillNewListView() {
        // 填充ListView,在里面显示新闻列表
        List<HashMap<String, String>> newsData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("newslist_item_title", "最新新闻显示 " + i);
            hashMap.put("newslist_item_digest", "最新新闻摘要" + i);
            hashMap.put("newslist_item_source", "最新新闻来源" + i);
            hashMap.put("newslist_item_ptime", "2015-08-04 15:22:2" + i);
            newsData.add(hashMap);
        }
        SimpleAdapter newListAdapter = new SimpleAdapter(this, newsData,
                R.layout.newslist_item, new String[] { "newslist_item_title",
                        "newslist_item_digest", "newslist_item_source",
                        "newslist_item_ptime" }, new int[] {
                        R.id.newslist_item_title, R.id.newslist_item_digest,
                        R.id.newslist_item_source, R.id.newslist_item_ptime });

        newlist.setAdapter(newListAdapter);

    }

    @ItemClick(R.id.news_list)
    public void newListNewsClicked() {
        Intent intent = new Intent(MainActivity.this,
                NewsDetailsActivity_.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
