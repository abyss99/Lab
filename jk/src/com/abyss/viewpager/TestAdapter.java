package com.abyss.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abyss.R;

import java.util.List;

/**
 * Created by CA on 2014. 8. 7..
 */
public class TestAdapter extends PagerAdapter {
    public static final int MAX_PAGER_ADAPTER_COUNT = 50000;

    private List<String> mTestList;
    private Context mContext;

    public TestAdapter(Context context, List<String> testList) {
        mContext = context;
        mTestList = testList;
    }

    private List<String> getTestList() {
        return mTestList;
    }

    @Override
    public int getCount() {
        //보여줄 배너 갯수가 2개 미만일 경우에는 롤링하지 않는다. getCount는 Circular ViewPager를 위한 가상 Count이므로 1로 설정한다.
        if (getRealCount() == 1) {
            return 1;
        }

        return MAX_PAGER_ADAPTER_COUNT;
    }

    public int getRealCount() {
//        return getTestList().size();
        return 10;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final int realPosition = position % getRealCount();
        TextView v = (TextView)View.inflate(mContext, R.layout.pager_item, null);
        v.setText("" + realPosition);
        container.addView(v);

        return v;
    }


    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

}
