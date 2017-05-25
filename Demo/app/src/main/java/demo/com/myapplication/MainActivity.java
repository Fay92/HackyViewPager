package demo.com.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import demo.com.myapplication.view.HackyViewPager;

public class MainActivity extends AppCompatActivity {

    private HackyViewPager viewPager;

    private ImageViewPagerAdapter mAdapter;


    private ImageView imageView;

    private ImageView[] imageViews;

    private List<String> imagUrl;

    private LinearLayout viewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        viewPager = (HackyViewPager) findViewById(R.id.pager);
        viewGroup = (LinearLayout) findViewById(R.id.viewGroup);
        mAdapter = new ImageViewPagerAdapter(getSupportFragmentManager(), imagUrl);
        setImageView();
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new ChangePagePointListener());
    }

    private void setImageView() {
        imageViews = new ImageView[imagUrl.size()];
        for (int i = 0; i < imagUrl.size(); i++) {
            // 设置小圆点imageview的参数
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    10, 10);
            imageView = new ImageView(this);
            // 设置每个小圆点的宽高
            layoutParams.setMargins(10, 0, 0, 0);
            imageView.setLayoutParams(layoutParams);
            imageViews[i] = imageView;
            if (i == 0) {
                // 默认选中第一张图片
                imageViews[i].setBackgroundResource(R.drawable.dot_focused);
            } else {
                // 其他图片都设置未选中状态
                imageViews[i].setBackgroundResource(R.drawable.dot_normal);
            }
            viewGroup.addView(imageViews[i]);
        }
    }

    /**
     * Created by csonezp on 15-12-28.
     */
    public class ImageViewPagerAdapter extends FragmentStatePagerAdapter {

        private static final String IMAGE_URL = "image";

        List<String> mDatas;

        public ImageViewPagerAdapter(FragmentManager fm, List data) {
            super(fm);
            mDatas = data;
        }

        @Override
        public Fragment getItem(int position) {
            String url = mDatas.get(position);
            Fragment fragment = ImageFragment.newInstance(url);
            return fragment;
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }
    }

    class ChangePagePointListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[position]
                        .setBackgroundResource(R.drawable.dot_focused);
                if (position != i) {
                    imageViews[i].setBackgroundResource(R.drawable.dot_normal);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
