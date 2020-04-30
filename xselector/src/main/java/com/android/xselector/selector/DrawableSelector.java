package com.android.xselector.selector;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.TextView;

import com.android.xselector.interfaces.ISelector;
import com.android.xselector.utils.XHelper;

/**
 * @author :liujc
 * @date : 2020/4/30
 * @Description : 背景状态选择器（颜色背景、图片背景）
 */

public class DrawableSelector implements ISelector<Drawable, View> {
    private static DrawableSelector mDrawableSelector;
    //不可点击
    private Drawable mDisabledDrawable;
    //选中
    private Drawable mSelectedDrawable;
    //获取焦点
    private Drawable mFocusedDrawable;
    //触摸
    private Drawable mPressedDrawable;
    //正常
    private Drawable mNormalDrawable;
    //是否设置TextView颜色选择器
    private boolean isSelectorColor;
    private ColorStateList mColorStateList;

    public static DrawableSelector getInstance() {
        mDrawableSelector = new DrawableSelector();
        return mDrawableSelector;
    }

    private DrawableSelector() {
        mNormalDrawable = new ColorDrawable(Color.TRANSPARENT);
    }

    public DrawableSelector defaultDrawable(Drawable drawable) {
        mNormalDrawable = drawable;
        return this;
    }

    public DrawableSelector disabledDrawable(Drawable drawable) {
        mDisabledDrawable = drawable;
        return this;
    }

    public DrawableSelector pressedDrawable(Drawable drawable) {
        mPressedDrawable = drawable;
        return this;
    }

    public DrawableSelector selectedDrawable(Drawable drawable) {
        mSelectedDrawable = drawable;
        return this;
    }

    public DrawableSelector focusedDrawable(Drawable drawable) {
        mFocusedDrawable = drawable;
        return this;
    }

    public DrawableSelector defaultDrawable(@DrawableRes int drawableRes) {
        return defaultDrawable(XHelper.getDrawableRes(drawableRes));
    }

    public DrawableSelector disabledDrawable(@DrawableRes int drawableRes) {
        return disabledDrawable(XHelper.getDrawableRes(drawableRes));
    }

    public DrawableSelector pressedDrawable(@DrawableRes int drawableRes) {
        return pressedDrawable(XHelper.getDrawableRes(drawableRes));
    }

    public DrawableSelector selectedDrawable(@DrawableRes int drawableRes) {
        return selectedDrawable(XHelper.getDrawableRes(drawableRes));
    }

    public DrawableSelector focusedDrawable(@DrawableRes int drawableRes) {
        return focusedDrawable(XHelper.getDrawableRes(drawableRes));
    }

    /**
     * .
     * 背景状态选择器（背景Drawable）
     *
     * @param pressedDrawable 触摸颜色 例：Context.getResources.getDrawable(R.drawable/mipmap.xxx)
     * @param normalDrawable  正常颜色 例：Context.getResources.getDrawable(R.drawable/mipmap.xxx)
     * @return DrawableSelector
     */
    public DrawableSelector selectorBackground(Drawable pressedDrawable, Drawable normalDrawable) {
        this.mPressedDrawable = pressedDrawable;
        this.mNormalDrawable = normalDrawable;
        return this;
    }
    /**
     * 设置背景选择器同时设置字体颜色颜色器
     *
     * @param pressedColorResId 触摸颜色 例：R.color.colorPrimary
     * @param normalColorResId  正常颜色 例：R.color.colorPrimary
     * @return DrawableSelector
     */
    public DrawableSelector selectorColor(@ColorRes int pressedColorResId, @ColorRes int normalColorResId) {
        mColorStateList = ColorSelector.getInstance()
                .pressedColor(pressedColorResId)
                .defaultColor(normalColorResId)
                .build();
        this.isSelectorColor = true;
        return this;
    }


    /**
     * 设置背景选择器同时设置字体颜色颜色器
     *
     * @param pressedColor 触摸颜色 例：#ffffff
     * @param normalColor  正常颜色 例：#ffffff
     * @return DrawableSelector
     */
    public DrawableSelector selectorColor(String pressedColor, String normalColor) {
        mColorStateList = ColorSelector.getInstance()
                .pressedColor(pressedColor)
                .defaultColor(normalColor)
                .build();
        this.isSelectorColor = true;
        return this;
    }


    @Override
    public void into(View view) {
        view.setClickable(true);
        view.setBackground(create());
        if (isSelectorColor) {
            try {
                ((TextView) view).setTextColor(mColorStateList);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassCastException("设置字体颜色选择器（Selector）请传入TextView（包括Button）！！！");
            }
        }
    }

    /**
     * @return 返回背景Drawable
     */
    @Override
    public Drawable build() {
        return create();
    }

    /**
     * 创建触摸颜色变化
     *
     * @return StateListDrawable
     */
    public StateListDrawable create() {
        StateListDrawable selector = new StateListDrawable();
        if (mDisabledDrawable != null)
            selector.addState(new int[]{-android.R.attr.state_enabled}, mDisabledDrawable);
        if (mPressedDrawable != null)
            selector.addState(new int[]{android.R.attr.state_pressed}, mPressedDrawable);
        if (mSelectedDrawable != null)
            selector.addState(new int[]{android.R.attr.state_selected}, mSelectedDrawable);
        if (mFocusedDrawable != null)
            selector.addState(new int[]{android.R.attr.state_focused}, mFocusedDrawable);
        selector.addState(new int[]{}, mNormalDrawable);
        return selector;
    }
}
