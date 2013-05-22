package com.campus.insideout.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.campus.insideout.R;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class NewListView extends ListView implements OnScrollListener {
	private int mScrollState;
	private OnRefreshListener refreshListener;

	private static final String TAG = "listview";
	public final static int RELEASE_To_REFRESH = 0;
	public final static int PULL_To_REFRESH = 1;
	public final static int REFRESHING = 2;
	public final static int DONE = 3;
	public final static int LOADING = 4;
	private View moreView;
	private TextView next;

	// 实际的padding的距离与界面上偏移距离的比例
	private final static int RATIO = 3;

	private LayoutInflater inflater;

	private LinearLayout headView;

	private TextView tipsTextview;
	private TextView lastUpdatedTextView;
	private ImageView arrowImageView;
	private ProgressBar progressBar;
	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;
	private boolean isRecored;
	private int headContentWidth;
	private int headContentHeight;
	private int startY;
	private int firstItemIndex;
	public int state;
	private boolean isBack;
	protected boolean isRefreshable;
	private boolean isRefreshFoot;
	private boolean nomore = false;
	private Context context;
	String formatedate = "";

	public NewListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public NewListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

		mScrollState = scrollState;
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && isRefreshFoot) {
			if (state != REFRESHING && state != LOADING) {
				if (!nomore) {
					state = LOADING;
					moreView.setVisibility(View.VISIBLE);
					next.setText(context.getResources().getString(R.string.tab_more));
					try {
						refreshListener.onNext();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		firstItemIndex = firstVisibleItem;
		if (firstVisibleItem + visibleItemCount == totalItemCount) {
			isRefreshFoot = true;
		} else {
			isRefreshFoot = false;
		}
	}

	public interface OnRefreshListener {
		public void onRefresh();

		public void onNext();
	}

	protected void init(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		moreView = (LinearLayout) inflater.inflate(R.layout.page_view_h, null);
		next = (TextView) moreView.findViewById(R.id.next);
		next.setText(context.getResources().getString(R.string.tab_more));
		setFocusable(false);
		setClickable(false);
		setCacheColorHint(Color.TRANSPARENT);
		setCacheColorHint(context.getResources().getColor(R.color.full_clear_color));

		headView = (LinearLayout) inflater.inflate(R.layout.head, null);

		arrowImageView = (ImageView) headView.findViewById(R.id.head_arrowImageView);
		arrowImageView.setMinimumWidth(70);
		arrowImageView.setMinimumHeight(50);
		progressBar = (ProgressBar) headView.findViewById(R.id.head_progressBar);
		tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
		lastUpdatedTextView = (TextView) headView.findViewById(R.id.head_lastUpdatedTextView);

		measureView(headView);
		headContentHeight = headView.getMeasuredHeight();
		headContentWidth = headView.getMeasuredWidth();

		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();

		addHeaderView(headView, null, false);
		setOnScrollListener(this);

		animation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);

		state = DONE;
		isRefreshable = false;
	}

	public boolean onTouchEvent(MotionEvent event) {

		if (isRefreshable) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (firstItemIndex == 0 && !isRecored) {
					isRecored = true;
					startY = (int) event.getY();
				}
				break;

			case MotionEvent.ACTION_UP:

				if (state != REFRESHING && state != LOADING) {
					if (state == DONE) {
					}
					if (state == PULL_To_REFRESH) {
						state = DONE;
						changeHeaderViewByState();

					}
					if (state == RELEASE_To_REFRESH) {
						state = REFRESHING;
						changeHeaderViewByState();
						onRefresh();

					}
				}

				isRecored = false;
				isBack = false;

				break;

			case MotionEvent.ACTION_MOVE:
				int tempY = (int) event.getY();
				if (!isRecored && firstItemIndex == 0) {
					isRecored = true;
					startY = tempY;
				}

				if (state != REFRESHING && isRecored && state != LOADING) {

					// 保证在设置padding的过程中，当前的位置�?��是在head，否则如果当列表超出屏幕的话，当在上推的时�?，列表会同时进行滚动

					// 可以松手去刷新了
					if (state == RELEASE_To_REFRESH) {

						setSelection(0);

						if (((tempY - startY) / RATIO < headContentHeight) && (tempY - startY) > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();

						}
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();

						}
						else {
							// 不用进行特别的操作，只用更新paddingTop的�?就行�?
						}
					}
					if (state == PULL_To_REFRESH) {

						setSelection(0);

						// 下拉到可以进入RELEASE_TO_REFRESH的状�?
						if ((tempY - startY) / RATIO >= headContentHeight) {
							state = RELEASE_To_REFRESH;
							isBack = true;
							changeHeaderViewByState();

						}
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();

						}
					}

					// done状�?�?
					if (state == DONE) {
						if (tempY - startY > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();
						}
					}

					// 更新headView的size
					if (state == PULL_To_REFRESH) {
						headView.setPadding(0, -1 * headContentHeight + (tempY - startY) / RATIO, 0, 0);

					}

					// 更新headView的paddingTop
					if (state == RELEASE_To_REFRESH) {
						headView.setPadding(0, (tempY - startY) / RATIO - headContentHeight, 0, 0);
					}

				}

				break;
			}
		}

		return super.onTouchEvent(event);
	}

	public void initView() {
		lastUpdatedTextView.setText(context.getString(R.string.last_reflesh) + formatedate);
	}

	protected void changeHeaderViewByState() {
		switch (state) {
		case RELEASE_To_REFRESH:
			arrowImageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			arrowImageView.clearAnimation();
			arrowImageView.startAnimation(animation);

			tipsTextview.setText(context.getString(R.string.release_to_reflesh));

			break;
		case PULL_To_REFRESH:
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.VISIBLE);
			if (isBack) {
				isBack = false;
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(reverseAnimation);

				tipsTextview.setText(context.getString(R.string.pull_to_refresh));
			} else {
				tipsTextview.setText(context.getString(R.string.pull_to_refresh));
			}
			break;

		case REFRESHING:

			headView.setPadding(0, 0, 0, 0);

			progressBar.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.GONE);
			tipsTextview.setText(context.getString(R.string.reflesh_refreshing));
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			break;
		case DONE:
			headView.setPadding(0, -1 * headContentHeight, 0, 0);

			progressBar.setVisibility(View.GONE);
			arrowImageView.clearAnimation();
			arrowImageView.setImageResource(R.drawable.refresh_arrow);
			tipsTextview.setText(context.getString(R.string.pull_to_refresh));
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			break;
		}
	}

	public void setonRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
		isRefreshable = true;
	}

	public void onNextComplete() {
		state = DONE;
		moreView.setVisibility(View.GONE);
	}

	public void onRefreshComplete() {
		moreView.setVisibility(View.GONE);
		state = DONE;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		formatedate = dateFormat.format(date);
		lastUpdatedTextView.setText(context.getString(R.string.last_reflesh) + formatedate);
		changeHeaderViewByState();
	}

	private void onRefresh() {
		if (refreshListener != null) {
			refreshListener.onRefresh();
		}
	}

	public void doRefresh() {
		state = REFRESHING;
		changeHeaderViewByState();
		if (refreshListener != null) {
			refreshListener.onRefresh();
		}
	}

	// 此方法直接照搬自网络上的�?��下拉刷新的demo，此处是“估计�?headView的width以及height
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	public void setAdapter(BaseAdapter adapter) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		formatedate = dateFormat.format(date);
		lastUpdatedTextView.setText(context.getString(R.string.last_reflesh) + formatedate);
		super.setAdapter(adapter);
	}

	public void addFooter() {
		addFooterView(moreView);
		moreView.setVisibility(View.GONE);
	}

	public void setheader() {
		state = DONE;
		moreView.setVisibility(View.GONE);
	}

	public boolean isNomore() {
		return nomore;
	}

	public void setNomore(boolean nomore) {
		this.nomore = nomore;
	}

}