package com.sherchen.likewechatphotoviewer.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.sherchen.likewechatphotoviewer.R;
import com.sherchen.likewechatphotoviewer.entity.PicInfo;

/**
 * Created by 123lf on 2016/12/7.
 */
public class SceneHelp {
    private Activity m_Activity;
    private int mShortAnimationDuration;
    private Animator mCurrentAnimator;
    private int screenWidth;

    public SceneHelp(Activity activity) {
        m_Activity = activity;
        mShortAnimationDuration = m_Activity.getResources().getInteger(android.R.integer.config_shortAnimTime);
        screenWidth = ScreenUtils.getScreenWidth(m_Activity);
    }

    /**
     * 放大缩略图
     * @param container
     * @param thumbView
     * @param expandedImageView
     * @param l
     */
    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void zoomThumb2Expaned(
            final View container,
            final View thumbView,
            final ImageView expandedImageView,
            final AnimFinishListener l
    ) {
        // If there's an animation in progress, cancel it immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
//        expandedImageView.setImageResource(imageResId);

        // Calculate the starting and ending bounds for the zoomed-in image. This step
        // involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail, and the
        // final bounds are the global visible rectangle of the container view. Also
        // set the container view's offset as the origin for the bounds, since that's
        // the origin for the positioning animation properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        container.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final bounds using the
        // "center crop" technique. This prevents undesirable stretching during the animation.
        // Also calculate the start scaling factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation begins,
        // it will position the zoomed-in view in the place of the thumbnail.
//        thumbView.setAlpha(0f);

        // Set the pivot point for SCALE_X and SCALE_Y transformations to the top-left corner of
        // the zoomed-in view (the default is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and scale properties
        // (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left,
                        finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top,
                        finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
//                thumbView.setAlpha(1);
//                expandedImageView.setVisibility(View.GONE);
//                expandedImageView.setBackgroundColor(Color.TRANSPARENT);
                if(l != null) {
                    l.onAnimFinished();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
//                thumbView.setAlpha(1);
//                expandedImageView.setVisibility(View.GONE);
//                expandedImageView.setBackgroundColor(Color.TRANSPARENT);
                if(l != null) {
                    l.onAnimFinished();
                }
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down to the original bounds
        // and show the thumbnail instead of the expanded image.
//        final float startScaleFinal = startScale;
//        expandedImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mCurrentAnimator != null) {
//                    mCurrentAnimator.cancel();
//                }
//
//                // Animate the four positioning/sizing properties in parallel, back to their
//                // original values.
//                AnimatorSet set = new AnimatorSet();
//                set
//                        .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left))
//                        .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
//                        .with(ObjectAnimator
//                                .ofFloat(expandedImageView, View.SCALE_X, startScaleFinal))
//                        .with(ObjectAnimator
//                                .ofFloat(expandedImageView, View.SCALE_Y, startScaleFinal));
//                set.setDuration(mShortAnimationDuration);
//                set.setInterpolator(new DecelerateInterpolator());
//                set.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        thumbView.setAlpha(1f);
//                        expandedImageView.setVisibility(View.GONE);
//                        mCurrentAnimator = null;
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//                        thumbView.setAlpha(1f);
//                        expandedImageView.setVisibility(View.GONE);
//                        mCurrentAnimator = null;
//                    }
//                });
//                set.start();
//                expandedImageView.setBackgroundColor(Color.TRANSPARENT);
//                mCurrentAnimator = set;
//            }
//        });
    }

    /**
     * 缩小原图
     * @param container 图片预览容器
     * @param thumbView 要缩小到的容器
     * @param expandedImageView 放大的图片
     * @param l 动画结束监听
     */
    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void zoomExpand2Thumb(final View container,
                                 final View thumbView,
                                 final ImageView expandedImageView,
                                 final AnimFinishListener l
    ) {
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Calculate the starting and ending bounds for the zoomed-in image. This step
        // involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail, and the
        // final bounds are the global visible rectangle of the container view. Also
        // set the container view's offset as the origin for the bounds, since that's
        // the origin for the positioning animation properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        container.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final bounds using the
        // "center crop" technique. This prevents undesirable stretching during the animation.
        // Also calculate the start scaling factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);
        expandedImageView.setBackgroundColor(Color.TRANSPARENT);

        // Animate the four positioning/sizing properties in parallel, back to their
        // original values.
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                .with(ObjectAnimator
                        .ofFloat(expandedImageView, View.SCALE_X, startScale))
                .with(ObjectAnimator
                        .ofFloat(expandedImageView, View.SCALE_Y, startScale));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                thumbView.setAlpha(1f);
                expandedImageView.setVisibility(View.GONE);
                mCurrentAnimator = null;
                if(l != null) {
                    l.onAnimFinished();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                thumbView.setAlpha(1f);
                expandedImageView.setVisibility(View.GONE);
                mCurrentAnimator = null;
                if(l != null) {
                    l.onAnimFinished();
                }
            }
        });
        set.start();
        mCurrentAnimator = set;
    }

    public interface AnimFinishListener {
        void onAnimFinished();
    }

    public void showExpandedView(ImageView imageView, PicInfo picInfo) {
        int viewerDW = picInfo.getViewerDW(screenWidth);
        int viewerDH = picInfo.getViewerDH(screenWidth);
        if(viewerDW != 0 && viewerDH != 0){
            GradientDrawable placeholder = (GradientDrawable) m_Activity.getResources().getDrawable(R.drawable.ic_default_placeholder);
            placeholder.setSize(viewerDW, viewerDH);
            GlideUtils.displayImage(m_Activity, imageView, picInfo.getPic_url(), placeholder);
        }else{
            GlideUtils.displayImage(m_Activity, imageView, picInfo.getPic_url(), R.drawable.ic_placeholder_preview);
        }
    }
}
