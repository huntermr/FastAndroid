package com.hunter.fastandroid.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hunter.fastandroid.R;

/**
 * 自定义头部布局
 *
 * @author Ht
 * @ClassName: HeaderLayout
 * @Description: Activity或者Fragment的头部标题栏
 */
public class HeaderLayout extends LinearLayout {
    private LayoutInflater mInflater;
    private View mHeader;
    private LinearLayout mLayoutLeftContainer, mLayoutRightContainer, mLayoutCenterContainer, mHeaderTitle;
    private TextView mTvTitle;
    private ImageButton mLeftImageButton, mRightImageButton;

    public HeaderLayout(Context context) {
        super(context);
        init(context);
    }

    public HeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mInflater = LayoutInflater.from(context);
        mHeader = mInflater.inflate(R.layout.common_header, null);
        addView(mHeader);
        initViews();
    }

    /**
     * 初始化布局
     */
    private void initViews() {
        mLayoutLeftContainer = (LinearLayout) findViewByHeaderId(R.id.header_layout_leftview_container);
        mLayoutRightContainer = (LinearLayout) findViewByHeaderId(R.id.header_layout_rightview_container);

        mHeaderTitle = (LinearLayout) findViewByHeaderId(R.id.header_title);
        mTvTitle = (TextView) findViewByHeaderId(R.id.header_htv_subtitle);

        mLayoutCenterContainer = (LinearLayout) findViewByHeaderId(R.id.header_layout_middleview_container);

        initLeftImageButton();
        initRightImageButton();

        mLayoutLeftContainer.setVisibility(View.INVISIBLE);
        mLayoutRightContainer.setVisibility(View.INVISIBLE);

        mHeaderTitle.setVisibility(View.VISIBLE);
        mTvTitle.setVisibility(View.VISIBLE);

        mLayoutCenterContainer.setVisibility(View.GONE);
    }

    /**
     * 在TitleBar中查找指定控件
     */
    public View findViewByHeaderId(int id) {
        return mHeader.findViewById(id);
    }

    /**
     * 初始化左侧按钮
     */
    private void initLeftImageButton() {
        View mleftImageButtonView = mInflater.inflate(
                R.layout.common_header_leftbutton, null);
        mLayoutLeftContainer.addView(mleftImageButtonView);
        mLeftImageButton = (ImageButton) mleftImageButtonView
                .findViewById(R.id.ib_titlebar_left);
    }

    /**
     * 初始化右侧按钮
     */
    private void initRightImageButton() {
        View mRightImageButtonView = mInflater.inflate(
                R.layout.common_header_rightbutton, null);
        mLayoutRightContainer.addView(mRightImageButtonView);
        mRightImageButton = (ImageButton) mRightImageButtonView
                .findViewById(R.id.ib_titlebar_right);
    }

    /**
     * 设置TitleBar的标题
     */
    public void setTitle(CharSequence title) {
        mTvTitle.setText(title);
        mHeaderTitle.setVisibility(View.VISIBLE);
        mLayoutCenterContainer.setVisibility(View.GONE);
        mLayoutLeftContainer.removeAllViews();
        mLayoutRightContainer.removeAllViews();
    }

    /**
     * 设置TitleBar的标题
     * @param res
     */
    public void setTitle(int res){
        mTvTitle.setText(res);
        mHeaderTitle.setVisibility(View.VISIBLE);
        mLayoutCenterContainer.setVisibility(View.GONE);
        mLayoutLeftContainer.removeAllViews();
        mLayoutRightContainer.removeAllViews();
    }

    /**
     * 设置TitleBar的标题及左侧按钮背景
     */
    public void setTitle(CharSequence title, int leftRes) {
        setTitle(title);
        mLeftImageButton.setBackgroundResource(leftRes);
        mLayoutLeftContainer.setVisibility(View.VISIBLE);
    }

    /**
     * 设置TitleBar的标题及右侧按钮背景
     */
    public void setTitle(CharSequence title, int leftRes, int rightRes) {
        setTitle(title, leftRes);
        mRightImageButton.setBackgroundResource(rightRes);
        mLayoutRightContainer.setVisibility(View.VISIBLE);
    }

    /**
     * 设置左侧按钮点击事件
     */
    public void setLeftListener(OnClickListener listener) {
        mLeftImageButton.setOnClickListener(listener);
    }

    /**
     * 设置右侧按钮点击事件
     */
    public void setRightListener(OnClickListener listener) {
        mRightImageButton.setOnClickListener(listener);
    }

    /**
     * 自定义左侧视图
     */
    public void setLeftView(View view) {
        mLayoutLeftContainer.removeAllViews();
        mLayoutLeftContainer.addView(view);
        mLayoutLeftContainer.setVisibility(View.VISIBLE);
    }

    /**
     * 自定义居中视图
     */
    public void setCenterView(View view) {
        mHeaderTitle.setVisibility(View.GONE);
        mLayoutCenterContainer.removeAllViews();
        mLayoutCenterContainer.addView(view);
        mLayoutCenterContainer.setVisibility(View.VISIBLE);
    }

    /**
     * 自定义右侧视图
     */
    public void setRightView(View view) {
        mLayoutRightContainer.removeAllViews();
        mLayoutRightContainer.addView(view);
        mLayoutRightContainer.setVisibility(View.VISIBLE);
    }
}
