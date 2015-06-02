package cn.bingoogolapple.refreshlayout;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.nineoldandroids.animation.ValueAnimator;

import java.lang.reflect.Field;


/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/21 22:35
 * 描述:下拉刷新、上拉加载更多、可添加自定义（固定、可滑动）头部控件（例如慕课网app顶部的广告位）
 */
public class BGARefreshLayout extends LinearLayout {
    private static final String TAG = BGARefreshLayout.class.getSimpleName();

    private BGARefreshViewHolder mRefreshViewHolder;
    /**
     * 整个头部控件，下拉刷新控件mRefreshHeaderView和下拉刷新控件下方的自定义组件mCustomHeaderView的父控件
     */
    private LinearLayout mWholeHeaderView;
    /**
     * 下拉刷新控件
     */
    private View mRefreshHeaderView;
    /**
     * 下拉刷新控件下方的自定义控件
     */
    private View mCustomHeaderView;
    /**
     * 下拉刷新控件下方的自定义控件是否可滚动，默认不可滚动
     */
    private boolean mIsCustomHeaderViewScrollable = false;
    /**
     * 下拉刷新控件的高度
     */
    private int mRefreshHeaderViewHeight;
    /**
     * 当前刷新状态
     */
    private RefreshStatus mCurrentRefreshStatus = RefreshStatus.IDLE;
    /**
     * 上拉加载更多控件
     */
    private View mLoadMoreFooterView;
    /**
     * 上拉加载更多控件的高度
     */
    private int mLoadMoreFooterViewHeight;
    /**
     * 下拉刷新和上拉加载更多代理
     */
    private BGARefreshLayoutDelegate mDelegate;
    /**
     * 手指按下时，y轴方向的偏移量
     */
    private int mWholeHeaderDownY = -1;
    /**
     * 整个头部控件最小的paddingTop
     */
    private int mMinWholeHeaderViewPaddingTop;
    /**
     * 整个头部控件最大的paddingTop
     */
    private int mMaxWholeHeaderViewPaddingTop;

    /**
     * 是否处于正在加载更多状态
     */
    private boolean mIsLoadingMore = false;

    private AbsListView mAbsListView;
    private ScrollView mScrollView;
    private RecyclerView mRecyclerView;
    private View mNormalView;
    private View mContentView;

    private float mInterceptTouchDownX = -1;
    private float mInterceptTouchDownY = -1;
    /**
     * 按下时整个头部控件的paddingTop
     */
    private int mWholeHeaderViewDownPaddingTop = 0;
    /**
     * 记录开始下拉刷新时的downY
     */
    private int mRefreshDownY = -1;

    /**
     * 是否已经设置内容控件滚动监听器
     */
    private boolean mIsInitedContentViewScrollListener = false;

    public BGARefreshLayout(Context context) {
        this(context, null);
    }

    public BGARefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        initWholeHeaderView();
    }

    /**
     * 初始化整个头部控件
     */
    private void initWholeHeaderView() {
        mWholeHeaderView = new LinearLayout(getContext());
        mWholeHeaderView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        mWholeHeaderView.setOrientation(LinearLayout.VERTICAL);
        addView(mWholeHeaderView);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        if (getChildCount() != 2) {
            throw new RuntimeException(BGARefreshLayout.class.getSimpleName() + "必须有且只有一个子控件");
        }

        mContentView = getChildAt(1);
        if (mContentView instanceof AbsListView) {
            mAbsListView = (AbsListView) mContentView;
        } else if (mContentView instanceof RecyclerView) {
            mRecyclerView = (RecyclerView) mContentView;
        } else if (mContentView instanceof ScrollView) {
            mScrollView = (ScrollView) mContentView;
        } else {
            mNormalView = mContentView;
            // 设置为可点击，否则在空白区域无法拖动
            mNormalView.setClickable(true);
        }
    }

    public void setRefreshViewHolder(BGARefreshViewHolder refreshViewHolder) {
        mRefreshViewHolder = refreshViewHolder;
        mRefreshViewHolder.setRefreshLayout(this);
        initRefreshHeaderView();
        initLoadMoreFooterView();
    }

    public void startChangeWholeHeaderViewPaddingTop(int distance) {
        ValueAnimator animator = ValueAnimator.ofInt(mWholeHeaderView.getPaddingTop(), mWholeHeaderView.getPaddingTop() - distance);
        animator.setDuration(mRefreshViewHolder.getTopAnimDuration());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int paddingTop = (Integer) animation.getAnimatedValue();
                mWholeHeaderView.setPadding(0, paddingTop, 0, 0);
            }
        });
        animator.start();
    }

    /**
     * 初始化下拉刷新控件
     *
     * @return
     */
    private void initRefreshHeaderView() {
        mRefreshHeaderView = mRefreshViewHolder.getRefreshHeaderView();
        if (mRefreshHeaderView != null) {
            mRefreshHeaderView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            mRefreshHeaderViewHeight = mRefreshViewHolder.getRefreshHeaderViewHeight();
            mMinWholeHeaderViewPaddingTop = -mRefreshHeaderViewHeight;
            mMaxWholeHeaderViewPaddingTop = (int) (mRefreshHeaderViewHeight * mRefreshViewHolder.getSpringDistanceScale());

            mWholeHeaderView.setPadding(0, mMinWholeHeaderViewPaddingTop, 0, 0);
            mWholeHeaderView.addView(mRefreshHeaderView, 0);
        }
    }

    /**
     * 设置下拉刷新控件下方的自定义控件
     *
     * @param customHeaderView 下拉刷新控件下方的自定义控件
     * @param scrollable       是否可以滚动
     */
    public void setCustomHeaderView(View customHeaderView, boolean scrollable) {
        if (mCustomHeaderView != null && mCustomHeaderView.getParent() != null) {
            ViewGroup parent = (ViewGroup) mCustomHeaderView.getParent();
            parent.removeView(mCustomHeaderView);
        }
        mCustomHeaderView = customHeaderView;
        if (mCustomHeaderView != null) {
            mCustomHeaderView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            mWholeHeaderView.addView(mCustomHeaderView);
            mIsCustomHeaderViewScrollable = scrollable;
        }
    }

    /**
     * 初始化上拉加载更多控件
     *
     * @return
     */
    private void initLoadMoreFooterView() {
        mLoadMoreFooterView = mRefreshViewHolder.getLoadMoreFooterView();
        if (mLoadMoreFooterView != null) {
            // 测量上拉加载更多控件的高度
            mLoadMoreFooterView.measure(0, 0);
            mLoadMoreFooterViewHeight = mLoadMoreFooterView.getMeasuredHeight();
            mLoadMoreFooterView.setPadding(0, 0, 0, -mLoadMoreFooterViewHeight);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        // 被添加到窗口后再设置监听器，这样开发者就不必烦恼先初始化RefreshLayout还是先设置自定义滚动监听器
        if (!mIsInitedContentViewScrollListener && mLoadMoreFooterView != null) {
            setRecyclerViewOnScrollListener();
            setAbsListViewOnScrollListener();

            addView(mLoadMoreFooterView, getChildCount());

            mIsInitedContentViewScrollListener = true;
        }
    }

    private void setRecyclerViewOnScrollListener() {
        if (mRecyclerView != null) {
            mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if ((newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_SETTLING) && shouldHandleRecyclerViewLoadingMore()) {
                        beginLoadingMore();
                    }
                }
            });
        }
    }

    private void setAbsListViewOnScrollListener() {
        if (mAbsListView != null) {
            try {
                // 通过反射获取开发者自定义的滚动监听器，并将其替换成自己的滚动监听器，触发滚动时也要通知开发者自定义的滚动监听器（非侵入式，不让开发者继承特定的控件）
                // mAbsListView.getClass().getDeclaredField("mOnScrollListener")获取不到mOnScrollListener，必须通过AbsListView.class.getDeclaredField("mOnScrollListener")获取
                Field field = AbsListView.class.getDeclaredField("mOnScrollListener");
                field.setAccessible(true);
                // 开发者自定义的滚动监听器
                final AbsListView.OnScrollListener onScrollListener = (AbsListView.OnScrollListener) field.get(mAbsListView);
                mAbsListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                        if ((scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING) && shouldHandleAbsListViewLoadingMore()) {
                            beginLoadingMore();
                        }

                        if (onScrollListener != null) {
                            onScrollListener.onScrollStateChanged(absListView, scrollState);
                        }
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        if (onScrollListener != null) {
                            onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                        }
                    }
                });
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean shouldHandleAbsListViewLoadingMore() {
        if (mIsLoadingMore || mLoadMoreFooterView == null || mDelegate == null || mAbsListView.getAdapter() == null || mAbsListView.getAdapter().getCount() == 0) {
            return false;
        }

        int lastChildBottom = 0;
        if (mAbsListView.getChildCount() > 0) {
            // 如果AdapterView的子控件数量不为0，获取最后一个子控件的bottom
            lastChildBottom = mAbsListView.getChildAt(mAbsListView.getChildCount() - 1).getBottom();
        }
        return mAbsListView.getLastVisiblePosition() == mAbsListView.getAdapter().getCount() - 1 && lastChildBottom <= mAbsListView.getHeight();
    }

    private boolean shouldHandleRecyclerViewLoadingMore() {
        if (mIsLoadingMore || mLoadMoreFooterView == null || mDelegate == null || mRecyclerView.getAdapter() == null || mRecyclerView.getAdapter().getItemCount() == 0) {
            return false;
        }

        RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
        if (manager == null || manager.getItemCount() == 0) {
            return false;
        }

        if (manager instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) manager;
            if (layoutManager.findLastCompletelyVisibleItemPosition() == mRecyclerView.getAdapter().getItemCount() - 1) {
                return true;
            }
        } else if (manager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) manager;
            // TODO
        }
        return false;
    }

    /**
     * 是否满足处理刷新的条件
     *
     * @return
     */
    private boolean shouldHandleLoadingMore() {
        if (mIsLoadingMore || mLoadMoreFooterView == null || mDelegate == null) {
            return false;
        }

        // 内容是普通控件，满足
        if (mNormalView != null) {
            return true;
        }

        // 内容是ScrollView，并且其scrollY为0时满足
        if (mScrollView != null) {
            int scrollY = mScrollView.getScrollY();
            int height = mScrollView.getHeight();
            int scrollViewMeasuredHeight = mScrollView.getChildAt(0).getMeasuredHeight();
            if ((scrollY + height) == scrollViewMeasuredHeight) {
                return true;
            }
        }

        if (mAbsListView != null) {
            return shouldHandleAbsListViewLoadingMore();
        }

        if (mRecyclerView != null) {
            return shouldHandleRecyclerViewLoadingMore();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInterceptTouchDownX = event.getRawX();
                mInterceptTouchDownY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsLoadingMore || (mCurrentRefreshStatus == RefreshStatus.REFRESHING)) {
                    // ACTION_DOWN时没有消耗掉事件，子控件会处于按下状态，这里设置ACTION_CANCEL，使子控件取消按下状态
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    super.onInterceptTouchEvent(event);
                    return true;
                }

                if (mInterceptTouchDownX == -1) {
                    mInterceptTouchDownX = (int) event.getRawX();
                }
                if (mInterceptTouchDownY == -1) {
                    mInterceptTouchDownY = (int) event.getRawY();
                }

                int interceptTouchMoveDistanceY = (int) (event.getRawY() - mInterceptTouchDownY);
                // 可以没有上拉加载更多，但是必须有下拉刷新，否则就不拦截事件
                if (Math.abs(event.getRawX() - mInterceptTouchDownX) < Math.abs(interceptTouchMoveDistanceY) && mRefreshHeaderView != null) {
                    if ((interceptTouchMoveDistanceY > 0 && shouldHandleRefresh()) || (interceptTouchMoveDistanceY < 0 && shouldHandleLoadingMore()) || interceptTouchMoveDistanceY < 0 && !isWholeHeaderViewCompleteInvisible()) {

                        // ACTION_DOWN时没有消耗掉事件，子控件会处于按下状态，这里设置ACTION_CANCEL，使子控件取消按下状态
                        event.setAction(MotionEvent.ACTION_CANCEL);
                        super.onInterceptTouchEvent(event);
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                // 重置
                mInterceptTouchDownX = -1;
                mInterceptTouchDownY = -1;
                break;
        }

        return super.onInterceptTouchEvent(event);
    }

    /**
     * 是否满足处理刷新的条件
     *
     * @return
     */
    private boolean shouldHandleRefresh() {
        if (mRefreshHeaderView == null) {
            return false;
        }

        // 内容是普通控件，满足
        if (mNormalView != null) {
            return true;
        }

        // 内容是ScrollView，并且其scrollY为0时满足
        if (mScrollView != null && mScrollView.getScrollY() == 0) {
            return true;
        }

        if (mAbsListView != null) {
            int firstChildTop = 0;
            if (mAbsListView.getChildCount() > 0) {
                // 如果AdapterView的子控件数量不为0，获取第一个子控件的top
                firstChildTop = mAbsListView.getChildAt(0).getTop();
            }
            if (mAbsListView.getFirstVisiblePosition() == 0 && firstChildTop == 0) {
                return true;
            }
        }

        if (mRecyclerView != null) {
            int firstChildTop = 0;
            if (mRecyclerView.getChildCount() > 0) {
                // 如果RecyclerView的子控件数量不为0，获取第一个子控件的top
                firstChildTop = mRecyclerView.getChildAt(0).getTop();
            }

            RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
            if (manager == null) {
                return true;
            }
            if (manager.getItemCount() == 0) {
                return true;
            }

            if (manager instanceof LinearLayoutManager) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) manager;
                if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0 && firstChildTop == 0) {
                    return true;
                }
            } else if (manager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) manager;
                // TODO
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != mRefreshHeaderView) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mWholeHeaderDownY = (int) event.getY();

                    if (mCustomHeaderView != null) {
                        mWholeHeaderViewDownPaddingTop = mWholeHeaderView.getPaddingTop();
                    }

                    if (mCustomHeaderView == null || !mIsCustomHeaderViewScrollable) {
                        mRefreshDownY = (int) event.getY();
                    }
                    if (isWholeHeaderViewCompleteInvisible()) {
                        mRefreshDownY = (int) event.getY();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (handleActionMove(event)) {
                        return true;
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (handleActionUpOrCancel(event)) {
                        return true;
                    }
                    break;
                default:
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 自定义头部控件是否已经完全显示
     *
     * @return true表示已经完全显示，false表示没有完全显示
     */
    private boolean isCustomHeaderViewCompleteVisible() {
        if (mCustomHeaderView != null) {
            // 0表示x，1表示y
            int[] location = new int[2];
            getLocationOnScreen(location);
            int mOnScreenY = location[1];

            mCustomHeaderView.getLocationOnScreen(location);
            int customHeaderViewOnScreenY = location[1];
            if (mOnScreenY <= customHeaderViewOnScreenY) {
                return true;
            } else {
                return false;
            }

        }
        return true;
    }

    /**
     * 整个头部控件是否已经完全隐藏
     *
     * @return true表示完全隐藏，false表示没有完全隐藏
     */
    private boolean isWholeHeaderViewCompleteInvisible() {
        if (mCustomHeaderView != null && mIsCustomHeaderViewScrollable) {
            // 0表示x，1表示y
            int[] location = new int[2];
            getLocationOnScreen(location);
            int mOnScreenY = location[1];

            mWholeHeaderView.getLocationOnScreen(location);
            int wholeHeaderViewOnScreenY = location[1];
            if (wholeHeaderViewOnScreenY + mWholeHeaderView.getMeasuredHeight() <= mOnScreenY) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 处理手指滑动事件
     *
     * @param event
     * @return true表示自己消耗掉该事件，false表示不消耗该事件
     */
    private boolean handleActionMove(MotionEvent event) {
        if (mCurrentRefreshStatus == RefreshStatus.REFRESHING || mIsLoadingMore) {
            return true;
        }

        if ((mCustomHeaderView == null || !mIsCustomHeaderViewScrollable) && mRefreshDownY == -1) {
            mRefreshDownY = (int) event.getY();
        }
        if (mCustomHeaderView != null && mIsCustomHeaderViewScrollable && isCustomHeaderViewCompleteVisible() && mRefreshDownY == -1) {
            mRefreshDownY = (int) event.getY();
        }

        int refreshDiffY = (int) event.getY() - mRefreshDownY;
        refreshDiffY = (int) (refreshDiffY / mRefreshViewHolder.getPaddingTopScale());

        // 如果是向下拉，并且当前可见的第一个条目的索引等于0，才处理整个头部控件的padding
        if (refreshDiffY > 0 && shouldHandleRefresh() && isCustomHeaderViewCompleteVisible()) {
            int paddingTop = mMinWholeHeaderViewPaddingTop + refreshDiffY;
            if (paddingTop > 0 && mCurrentRefreshStatus != RefreshStatus.RELEASE_REFRESH) {
                // 下拉刷新控件完全显示，并且当前状态没有处于释放开始刷新状态
                mCurrentRefreshStatus = RefreshStatus.RELEASE_REFRESH;
                handleRefreshStatusChanged();

                mRefreshViewHolder.handleScale(1.0f, refreshDiffY);
            } else if (paddingTop < 0) {
                // 下拉刷新控件没有完全显示，并且当前状态没有处于下拉刷新状态
                if (mCurrentRefreshStatus != RefreshStatus.PULL_DOWN) {
                    boolean isPreRefreshStatusNotIdle = mCurrentRefreshStatus != RefreshStatus.IDLE;
                    mCurrentRefreshStatus = RefreshStatus.PULL_DOWN;
                    if (isPreRefreshStatusNotIdle) {
                        handleRefreshStatusChanged();
                    }
                }
                float scale = 1 - paddingTop * 1.0f / mMinWholeHeaderViewPaddingTop;
                /**
                 * 往下滑
                 * paddingTop    mMinWholeHeaderViewPaddingTop ==> 0
                 * scale         0 ==> 1
                 * 往上滑
                 * paddingTop    0 ==> mMinWholeHeaderViewPaddingTop
                 * scale         1 ==> 0
                 */
                mRefreshViewHolder.handleScale(scale, refreshDiffY);
            }
            paddingTop = Math.min(paddingTop, mMaxWholeHeaderViewPaddingTop);
            mWholeHeaderView.setPadding(0, paddingTop, 0, 0);

            if (mRefreshViewHolder.canChangeToRefreshingStatus()) {
                mWholeHeaderDownY = -1;
                mRefreshDownY = -1;
                changeToRefreshing();
            }

            return true;
        }


        if (mCustomHeaderView != null && mIsCustomHeaderViewScrollable) {
            if (mWholeHeaderDownY == -1) {
                mWholeHeaderDownY = (int) event.getY();
                if (mCustomHeaderView != null) {
                    mWholeHeaderViewDownPaddingTop = mWholeHeaderView.getPaddingTop();
                }
            }

            int wholeHeaderDiffY = (int) event.getY() - mWholeHeaderDownY;
            if (!isWholeHeaderViewCompleteInvisible() || (wholeHeaderDiffY > 0 && shouldHandleRefresh() && !isCustomHeaderViewCompleteVisible())) {

                int paddingTop = mWholeHeaderViewDownPaddingTop + wholeHeaderDiffY;
                if (paddingTop < mMinWholeHeaderViewPaddingTop - mCustomHeaderView.getMeasuredHeight()) {
                    paddingTop = mMinWholeHeaderViewPaddingTop - mCustomHeaderView.getMeasuredHeight();
                }
                mWholeHeaderView.setPadding(0, paddingTop, 0, 0);

                return true;
            }
        }

        return false;
    }

    /**
     * 处理手指抬起事件
     *
     * @return true表示自己消耗掉该事件，false表示不消耗该事件
     */
    private boolean handleActionUpOrCancel(MotionEvent event) {
        boolean isReturnTrue = false;
        // 如果当前头部刷新控件没有完全隐藏，则需要返回true，自己消耗ACTION_UP事件
        if ((mCustomHeaderView == null || (mCustomHeaderView != null && !mIsCustomHeaderViewScrollable)) && mWholeHeaderView.getPaddingTop() != mMinWholeHeaderViewPaddingTop) {
            isReturnTrue = true;
        }

        if (mCurrentRefreshStatus == RefreshStatus.PULL_DOWN || mCurrentRefreshStatus == RefreshStatus.IDLE) {
            // 处于下拉刷新状态，松手时隐藏下拉刷新控件
            if (mCustomHeaderView == null || (mCustomHeaderView != null && mWholeHeaderView.getPaddingTop() < 0 && mWholeHeaderView.getPaddingTop() > mMinWholeHeaderViewPaddingTop)) {
                hiddenRefreshHeaderView();
            }
            mCurrentRefreshStatus = RefreshStatus.IDLE;
            handleRefreshStatusChanged();
        } else if (mCurrentRefreshStatus == RefreshStatus.RELEASE_REFRESH) {
            // 处于松开进入刷新状态，松手时完全显示下拉刷新控件，进入正在刷新状态
            changeToRefreshing();
        }

        if (mRefreshDownY == -1) {
            mRefreshDownY = (int) event.getY();
        }
        int diffY = (int) event.getY() - mRefreshDownY;
        if (shouldHandleLoadingMore() && (diffY < 0 || (mScrollView != null && diffY == 0)) && !mIsLoadingMore) {
            // 处理上拉加载更多，需要返回true，自己消耗ACTION_UP事件
            isReturnTrue = true;
            beginLoadingMore();
        }

        mWholeHeaderDownY = -1;
        mRefreshDownY = -1;
        return isReturnTrue;
    }

    /**
     * 切换到正在刷新状态
     */
    private void changeToRefreshing() {
        changeRefreshHeaderViewToZero();
        mCurrentRefreshStatus = RefreshStatus.REFRESHING;
        handleRefreshStatusChanged();

        if (mDelegate != null) {
            mDelegate.onBGARefreshLayoutBeginRefreshing();
        }
    }

    /**
     * 处理下拉刷新控件状态变化
     */
    private void handleRefreshStatusChanged() {
        switch (mCurrentRefreshStatus) {
            case IDLE:
                mRefreshViewHolder.changeToIdle();
                break;
            case PULL_DOWN:
                mRefreshViewHolder.changeToPullDown();
                break;
            case RELEASE_REFRESH:
                mRefreshViewHolder.changeToReleaseRefresh();
                break;
            case REFRESHING:
                mRefreshViewHolder.changeToRefreshing();
                break;
            default:
                break;
        }
    }

    /**
     * 结束下拉刷新和上拉加载更多
     */
    public void endRefreshing() {
        if (mIsLoadingMore) {
            mIsLoadingMore = false;
            hiddenLoadMoreFooterView();
            mRefreshViewHolder.onEndLoadingMore();
        } else {
            mCurrentRefreshStatus = RefreshStatus.IDLE;
            hiddenRefreshHeaderView();
            handleRefreshStatusChanged();
            mRefreshViewHolder.onEndRefreshing();
        }
    }

    /**
     * 隐藏下拉刷新控件，带动画
     */
    private void hiddenRefreshHeaderView() {
        ValueAnimator animator = ValueAnimator.ofInt(mWholeHeaderView.getPaddingTop(), mMinWholeHeaderViewPaddingTop);
        animator.setDuration(mRefreshViewHolder.getTopAnimDuration());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int paddingTop = (Integer) animation.getAnimatedValue();
                mWholeHeaderView.setPadding(0, paddingTop, 0, 0);
            }
        });
        animator.start();
    }

    /**
     * 设置下拉刷新控件的paddingTop到0，带动画
     */
    private void changeRefreshHeaderViewToZero() {
        ValueAnimator animator = ValueAnimator.ofInt(mWholeHeaderView.getPaddingTop(), 0);
        animator.setDuration(mRefreshViewHolder.getTopAnimDuration());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int paddingTop = (Integer) animation.getAnimatedValue();
                mWholeHeaderView.setPadding(0, paddingTop, 0, 0);
            }
        });
        animator.start();
    }

    /**
     * 隐藏上拉加载更多控件
     */
    private void hiddenLoadMoreFooterView() {
        startHiddenLoadingMoreViewAnim();
    }

    /**
     * 开始上拉加载更多
     */
    private long lastLoadTime;
    private void beginLoadingMore() {
    	long current = System.currentTimeMillis();
    	if (current - lastLoadTime <2*1000) {
			return;
		}
    	lastLoadTime = current;
        if (!mIsLoadingMore && mLoadMoreFooterView != null && mDelegate != null) {
            mIsLoadingMore = true;
            Log.e("load_more_more_more", " more +" +mIsLoadingMore+Thread.currentThread());

            startShowLoadingMoreViewAnim();

            mRefreshViewHolder.changeToLoadingMore();
            mDelegate.onBGARefreshLayoutBeginLoadingMore();
        }
    }

    private void startHiddenLoadingMoreViewAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, -mLoadMoreFooterViewHeight);
        animator.setDuration(mRefreshViewHolder.getBottomAnimDuration());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int paddingBottom = (Integer) animation.getAnimatedValue();
                mLoadMoreFooterView.setPadding(0, 0, 0, paddingBottom);
            }
        });
        animator.start();
    }

    private void startShowLoadingMoreViewAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(-mLoadMoreFooterViewHeight, 0);
        animator.setDuration(mRefreshViewHolder.getBottomAnimDuration());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int paddingBottom = (Integer) animation.getAnimatedValue();
                mLoadMoreFooterView.setPadding(0, 0, 0, paddingBottom);

                if (mScrollView != null) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                }
                if (mRecyclerView != null) {
                    RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
                    if (mRecyclerView.getAdapter() != null && mRecyclerView.getAdapter().getItemCount() > 0) {
                        layoutManager.scrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
                    }
                }
                if (mAbsListView != null) {
                    if (mAbsListView.getAdapter() != null && mAbsListView.getAdapter().getCount() > 0) {
                        mAbsListView.smoothScrollToPosition(mAbsListView.getAdapter().getCount() - 1);
                    }
                }
            }
        });
        animator.start();
    }

    public void setDelegate(BGARefreshLayoutDelegate delegate) {
        mDelegate = delegate;
    }

    public interface BGARefreshLayoutDelegate {
        /**
         * 开始刷新
         */
        void onBGARefreshLayoutBeginRefreshing();

        /**
         * 开始加载更多
         */
        void onBGARefreshLayoutBeginLoadingMore();
    }

    public enum RefreshStatus {
        IDLE, PULL_DOWN, RELEASE_REFRESH, REFRESHING
    }
}