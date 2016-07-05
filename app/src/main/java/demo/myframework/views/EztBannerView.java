package demo.myframework.views;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import demo.myframework.R;

/**
 * @Author: lizhipeng
 * @Data: 16/6/6 上午10:18
 * @Description: 自定义轮播图
 */
public class EztBannerView extends RelativeLayout {

    private List<View> mDatas;//数据模型的集合
    //    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private ViewPager mViewPager;
    private LinearLayout mPointLL;//底部小点
    private int mPointColor = R.drawable.new_ezt_banner_view_bg_point_selector;//小点的颜色值
    /**
     * 上一个被选中的小圆点的索引，默认值为0
     */
    private int mPreDotPosition = 0;
    private boolean mIsStartScroll = false;//是否滚动
    private int mScrollSpaceTime = 5*1000;//滚动时间间隔，默认3秒

    private EztBannerAdapter mAdapter;

    private OnBannerClickListener mOnBannerClickListener;
    private InitItemViewListener mInitItemViewListener;

    /**
     * 点击监听的接口
     */
    public interface OnBannerClickListener {
        void onBannerClick(View v, int position);
    }

    /**
     * 设置/处理数据的接口
     */
    public interface InitItemViewListener {
        void initItemView(View v, int position);
    }

    public EztBannerView(Context context) {
        super(context);
        initView(context);
    }

    public EztBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public EztBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDatas = new ArrayList<>();
//        mLayoutInflater = ((Activity) getContext()).getLayoutInflater();
        mViewPager = (ViewPager) findViewById(R.id.new_ezt_banner_vp);
        mPointLL = (LinearLayout) findViewById(R.id.new_ezt_banner_ll_dot_group);
        mAdapter = new EztBannerAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new BannerPageChangeListener());
        mViewPager.setCurrentItem(0);
    }

    private void initView(Context context) {
        mContext = context;
        View.inflate(context, R.layout.new_ezt_banner_view, this);

    }

    /**
     * 设置不同item 的 视图集合
     *
     * @param datas
     */
    public void setItemView(List<View> datas) {
        if (mDatas.size() > 0) {
            mDatas.clear();
        }
        mDatas.addAll(datas);
        addPointView();
        startScroll();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 设置item的view视图
     *
     * @param layoutId view 视图id
     * @param itemNum  view 个数
     */
    public void setItemView(int layoutId, int itemNum) {
        if (itemNum < 0) {
            itemNum = 1;
        }
        if (mDatas.size() > 0) {
            mDatas.clear();
        }
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < itemNum; i++) {
            mDatas.add(inflater.inflate(layoutId, null));
        }
        addPointView();
        startScroll();
    }

    /**
     * 添加 点
     */
    private void addPointView() {
        View dot = null;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
//        params.leftMargin = 150;
//        params.rightMargin = 150;
//        params.setMargins(100,0,100,0);
        for (int i = 0; i < mDatas.size(); i++) {
            // 每循环一次添加一个点到线行布局中
            dot = new View(getContext());
            dot.setBackgroundResource(mPointColor);
            dot.setEnabled(false);
            params.leftMargin = 20;
            dot.setLayoutParams(params);
            mPointLL.addView(dot); // 向线性布局中添加"点"
        }
        mPointLL.getChildAt(0).setEnabled(true);
    }

    /**
     * ********滚动*******
     */
    private Handler mHandler = new Handler();

    private Runnable mScrollTask = new Runnable() {
        @Override
        public void run() {
            mHandler.postDelayed(this, mScrollSpaceTime);

            ((Activity) getContext()).runOnUiThread(new Runnable() {
                public void run() {
                    int newindex = mViewPager.getCurrentItem() + 1;
                    mViewPager.setCurrentItem(newindex);
                }
            });
        }
    };

    /**
     * 开启滚动
     */
    public void startScroll() {
        if (!mIsStartScroll){
            mHandler.postDelayed(mScrollTask,mScrollSpaceTime);
            mIsStartScroll = true;
        }
    }

    /**
     * 暂停滚动
     */
    public void stopScroll() {
        mIsStartScroll = false;
        mHandler.removeCallbacks(mScrollTask);
    }

    /**
     * 设置圆点颜色
     * @param mPointColor
     */
    public void setPointColor(int mPointColor) {
        this.mPointColor = mPointColor;
        invalidate();
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    /**
     * 设置点击监听接口
     *
     * @param listener
     */
    public void setOnBannerClickListener(OnBannerClickListener listener) {
        mOnBannerClickListener = listener;
    }

    /**
     * 设置数据处理接口
     *
     * @param listener
     */
    public void setInitItemViewListener(InitItemViewListener listener) {
        mInitItemViewListener = listener;
    }

    /**
     * ViewPager的适配器
     */
    private class EztBannerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mDatas.get(position % mDatas.size()));
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view;
            if (mDatas.size() > 0) {
                view = mDatas.get(position % mDatas.size());
                container.addView(view);
            } else {
                view = null;
            }
//            initItemView();
            if (mInitItemViewListener != null) {
                mInitItemViewListener.initItemView(view, position);
            }
            // 为每一个page添加点击事件
            if (view != null)
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnBannerClickListener != null) {
                            mOnBannerClickListener.onBannerClick(v, position);
                        }
                    }

                });


            return view;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    /**
     * Banner的Page切换监听器
     */
    private class BannerPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            // 取余后的索引，得到新的page的索引
            int newPositon = position % mDatas.size();
            // 根据索引设置图片的描述
            // 把上一个点设置为被选中
            mPointLL.getChildAt(mPreDotPosition).setEnabled(false);
            // 根据索引设置那个点被选中
            mPointLL.getChildAt(newPositon).setEnabled(true);
            // 新索引赋值给上一个索引的位置
            mPreDotPosition = newPositon;
        }

    }


}
