package com.mg.kode.kodebrowser.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mg.kode.kodebrowser.R;

import static com.mg.kode.kodebrowser.utils.UIUtils.adjustAlpha;


public class KodeStepProgressBar extends View {
	
    private Paint mDefaultPaint;
    private Paint mActivePaint;
    private int mCurrentStep;
    private int mTotalSteps;
    @ColorInt
    private int mDefaultColor;
    @ColorInt
    private int mActiveProgressColor;
    @Dimension
    private int mProgressSize;
    @Dimension
    private int mStepImageYOffset;
    @Dimension
    private int mStepImageXOffset;
    private Bitmap mStepImage;
    private Rect mDstRect;
    private Rect mExpandedDstRect;

    public KodeStepProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, R.style.KodeStepProgressBarStyle_DefaultStyle);
    }

    public KodeStepProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.KodeStepProgressBar, defStyleAttr, defStyleAttr);
        mDefaultColor = ta.getColor(R.styleable.KodeStepProgressBar_kd_default_progress_color, -1);
        mActiveProgressColor = ta.getColor(R.styleable.KodeStepProgressBar_kd_active_progress_color, getResources().getColor(R.color.colorAccent));
        mProgressSize = ta.getDimensionPixelSize(R.styleable.KodeStepProgressBar_kd_progress_size, 10);
        mStepImageYOffset = ta.getDimensionPixelSize(R.styleable.KodeStepProgressBar_kd_step_image_y_offset, 0);
        mStepImageXOffset = ta.getDimensionPixelSize(R.styleable.KodeStepProgressBar_kd_step_image_x_offset, 0);
        mTotalSteps = ta.getInteger(R.styleable.KodeStepProgressBar_kd_total_steps, 5);
        mCurrentStep = ta.getInteger(R.styleable.KodeStepProgressBar_kd_current_step, 0);
        int drawableId = ta.getResourceId(R.styleable.KodeStepProgressBar_kd_step_image, android.R.drawable.ic_dialog_alert);
        mStepImage = BitmapFactory.decodeResource(getResources(), drawableId);
        ta.recycle();
        mDefaultPaint = new Paint();
        mDefaultPaint.setAntiAlias(true);
        if (mDefaultColor == -1) {
            mDefaultColor = adjustAlpha(mActiveProgressColor, 0.2f);
        }
        mDefaultPaint.setColor(mDefaultColor);
        mActivePaint = new Paint();
        mActivePaint.setAntiAlias(true);
        mActivePaint.setColor(mActiveProgressColor);
        mDstRect = new Rect();
        mExpandedDstRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = mProgressSize + mStepImage.getHeight() + mStepImageYOffset + getPaddingBottom() + getPaddingTop();
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int sectionWidth = canvas.getWidth() / mTotalSteps;
        if (mStepImageXOffset != 0) {
            canvas.getClipBounds(mExpandedDstRect);
            if (mStepImageXOffset > 0) {
                if (sectionWidth < mStepImage.getWidth()) {
                    mExpandedDstRect.right += mStepImageXOffset + (mStepImage.getWidth() - sectionWidth);
                } else {
                    mExpandedDstRect.right += mStepImageXOffset;
                }
            } else {
                if (sectionWidth < mStepImage.getWidth()) {
                    mExpandedDstRect.left += mStepImageXOffset - (mStepImage.getWidth() - sectionWidth);
                } else {
                    mExpandedDstRect.left += mStepImageXOffset;
                }
            }
            canvas.clipRect(mExpandedDstRect, Region.Op.REPLACE);
        }

        // Move the drawing by mStepImageXOffset
        for (int i = 0; i < mTotalSteps; i++) {
            int sectionCenterPx = sectionWidth * i + sectionWidth / 2;
            mDstRect.left = sectionCenterPx - (mStepImage.getWidth() / 2) + mStepImageXOffset;
            mDstRect.top = 0;
            mDstRect.right = sectionCenterPx + (mStepImage.getWidth() / 2) + mStepImageXOffset;
            mDstRect.bottom = mStepImage.getHeight();
            if (mCurrentStep == i) {
                canvas.drawBitmap(mStepImage, null, mDstRect, mActivePaint);
                canvas.drawCircle(sectionCenterPx, canvas.getHeight() - mProgressSize / 2 - getPaddingBottom(), mProgressSize / 2, mActivePaint);
            } else {
                canvas.drawCircle(sectionCenterPx, canvas.getHeight() - mProgressSize / 2 - getPaddingBottom(), mProgressSize / 2, mDefaultPaint);
            }
        }
        super.onDraw(canvas);
    }

    public Paint getDefaultPaint() {
        return mDefaultPaint;
    }

    public void setDefaultPaint(Paint defaultPaint) {
        mDefaultPaint = defaultPaint;
    }

    public Paint getActivePaint() {
        return mActivePaint;
    }

    public void setActivePaint(Paint activePaint) {
        mActivePaint = activePaint;
    }

    public int getDefaultColor() {
        return mDefaultColor;
    }

    public void setDefaultColor(int defaultColor) {
        mDefaultColor = defaultColor;
        mDefaultPaint.setColor(defaultColor);
    }

    public int getActiveProgressColor() {
        return mActiveProgressColor;
    }

    public void setActiveProgressColor(@ColorInt int activeProgressColor) {
        mActiveProgressColor = activeProgressColor;
        mActivePaint.setColor(activeProgressColor);
    }

    /**
     * Sets color of progress steps. Inactive will be the same color with 20% transparency.
     *
     * @param activeProgressColor desired color of active step.
     */
    public void setActiveProgressColorWithDefaultColor(@ColorInt int activeProgressColor) {
        mDefaultColor = adjustAlpha(activeProgressColor, 0.2f);
        mDefaultPaint.setColor(mDefaultColor);
        mActiveProgressColor = activeProgressColor;
        mActivePaint.setColor(activeProgressColor);
    }

    public int getProgressSize() {
        return mProgressSize;
    }

    public void setProgressSize(int progressSize) {
        mProgressSize = progressSize;
    }

    public int getStepImageYOffset() {
        return mStepImageYOffset;
    }

    public void setStepImageYOffset(int stepImageYOffset) {
        mStepImageYOffset = stepImageYOffset;
    }

    public int getStepImageXOffset() {
        return mStepImageXOffset;
    }

    public void setStepImageXOffset(int stepImageXOffset) {
        mStepImageXOffset = stepImageXOffset;
    }

    public Bitmap getStepImage() {
        return mStepImage;
    }

    public void setStepImage(Bitmap stepImage) {
        mStepImage = stepImage;
    }

    public int getCurrentStep() {
        return mCurrentStep;
    }

    /**
     * Sets the current progress step, which will change the color of step number to color provided by {@link #setActiveProgressColor(int)} and all other steps to its default color.
     * Sets the step image above the step number provided as parameter.
     *
     * @param stepCount step number to change active step to. Can be a value from 0 to value returned by {@link #getTotalSteps()} exclusive.
     * @throws IllegalArgumentException if stepCount is bigger or equal then {@link #getTotalSteps()}.
     */
    public void setCurrentStep(int stepCount) throws IllegalArgumentException {
        if (stepCount >= mTotalSteps) {
            throw new IllegalArgumentException("Cannot set active step number bigger then total steps.");
        }
        mCurrentStep = stepCount;
    }

    public int getTotalSteps() {
        return mTotalSteps;
    }

    public void setTotalSteps(int totalSteps) {
        mTotalSteps = totalSteps;
    }

    public void updateView() {
        this.invalidate();
    }

}
