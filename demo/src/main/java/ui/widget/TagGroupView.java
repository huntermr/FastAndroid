package demo.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import demo.utils.PixelUtils;
import demo.vo.SearchHistory;


/**
 * Created by Administrator on 2017/3/24.
 */

public class TagGroupView extends RadioGroup {

    private List<SearchHistory> mTagGroups;
    private int mHorizontalSpacing;
    private int mVerticalSpacing;
    private OnTagCheckListener mOnTagCheckListener;

    public TagGroupView(Context context) {
        super(context);
    }

    public TagGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHorizontalSpacing = PixelUtils.dp2px(20);
        mVerticalSpacing = PixelUtils.dp2px(20);
    }
//
//    /**
//     * 获取选中的标签id(多个以逗号分隔)
//     *
//     * @return
//     */
//    public String getSelectTagIds() {
//        if (mSelectedTag == null || mSelectedTag.size() == 0) return "";
//        StringBuilder stringBuilder = new StringBuilder();
//
//        for (int i = 0; i < mSelectedTag.size(); i++) {
//            GoodsCategory tagItem = mSelectedTag.get(i);
//            stringBuilder.append(tagItem.getCategoryId());
//            if (i != mSelectedTag.size() - 1) {
//                stringBuilder.append(",");
//            }
//        }
//
//        return stringBuilder.toString();
//    }
//
//    /**
//     * 获取选中的标签名称(多个以逗号分隔)
//     *
//     * @return
//     */
//    public String getSelectTagNames() {
//        if (mSelectedTag == null || mSelectedTag.size() == 0) return "";
//        StringBuilder stringBuilder = new StringBuilder();
//
//        for (int i = 0; i < mSelectedTag.size(); i++) {
//            GoodsCategory tagItem = mSelectedTag.get(i);
//            stringBuilder.append(tagItem.getCategoryName());
//            if (i != mSelectedTag.size() - 1) {
//                stringBuilder.append(",");
//            }
//        }
//
//        return stringBuilder.toString();
//    }
//
//    public void checkTag(int position) {
//        if (mTagGroups == null || mTagGroups.size() == 0) return;
//        Tag tagGroup = mTagGroups.get(position);
//
//        int itemCount = mTagGroups.size();
//
//        if (!tagGroup.isCheck()) {
//            mSelectedTag.add(tagGroup);
////            check(position);
//        } else {
//            mSelectedTag.remove(tagGroup);
//        }
//
//        for (int i = 0; i < itemCount; i++) {
//            Tag itemData = mTagGroups.get(i);
//            if (position == i) {
//                itemData.setCheck(true);
//            } else {
//                itemData.setCheck(false);
//            }
//        }
//
//    }

    public void setTags(List<SearchHistory> tagGroups) {
        if (tagGroups == null || tagGroups.size() == 0) return;

        removeAllViews();

        mTagGroups = tagGroups;
        int size = mTagGroups.size();

        for (int i = 0; i < size; i++) {
            final SearchHistory tagGroup = mTagGroups.get(i);

            final TextView tvTag = (TextView) View.inflate(getContext(), R.layout.item_tag, null);
            tvTag.setText(tagGroup.getKeyword());

            tvTag.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnTagCheckListener != null) {
                        mOnTagCheckListener.onTagCheck(tagGroup.getKeyword());
                    }
                }
            });
            addView(tvTag);
        }
    }

    public void clear(){
        mTagGroups.clear();
        removeAllViews();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int groupWidth = resolveSize(0, widthMeasureSpec);
        int groupHeight = 0;
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int childLeft = paddingLeft;
        int childTop = paddingTop;

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            LayoutParams childLayoutParams = (LayoutParams) childView.getLayoutParams();
            childView.measure(
                    getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, childLayoutParams.width),
                    getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, childLayoutParams.height)
            );

            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            groupHeight = childHeight;

            if (childLeft + childWidth + paddingRight > groupWidth) {
                childLeft = paddingLeft;
                childTop += childHeight + mVerticalSpacing;
            }

            childLeft += childWidth + mHorizontalSpacing;
        }

        setMeasuredDimension(groupWidth, childTop + groupHeight + paddingBottom);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int childLeft = paddingLeft;
        int childTop = paddingTop;
        int width = r - l;

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            int measuredWidth = childView.getMeasuredWidth();
            int measuredHeight = childView.getMeasuredHeight();

            if (childLeft + measuredWidth + paddingRight > width) {
                childTop += measuredHeight + mVerticalSpacing;
                childLeft = paddingLeft;
            }

            childView.layout(childLeft, childTop, childLeft + measuredWidth, childTop + measuredHeight);

            childLeft += measuredWidth + mHorizontalSpacing;
        }
    }

    public void setOnTagClickListener(OnTagCheckListener onTagCheckListener) {
        mOnTagCheckListener = onTagCheckListener;
    }

    public interface OnTagCheckListener {
        void onTagCheck(String tagName);
    }

}
