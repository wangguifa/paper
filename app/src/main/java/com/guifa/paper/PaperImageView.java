package com.guifa.paper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;

public class PaperImageView extends FrameLayout {

    /**
     * 阴影View未被选中时的透明度
     */
    private static final int ALPHA_DEFAULT = 0;
    /**
     * 阴影View被选中时的透明度
     */
    private static final int ALPHA_CLICKED = 80;
    /**
     * 上下文
     */
    private Context context;
    /**
     * 用户点击后的回调
     */
    private CallBackAction callBack;
    /**
     * 加载进度条
     */
    private ProgressBar progressBar;
    /**
     * 版面是否加载成功
     */
    private boolean loadComplete;
    private CoverView selectView = null;
    private float lastX;

    public PaperImageView(Context context) {
        super(context);
        this.context = context;
    }

    public PaperImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    /**
     * 加载报纸版面图
     */
    public void loadingPaperImage(PaperImage image) {
        if (progressBar == null) {
            // 显示加载进度条
            showProgressBar();
        }
        Glide.with(this).load(image.getPage_pic()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                // 加载报纸版面缩略图
                setBackgroundDrawable(resource);
            }
        });
        PaperImageView.this.removeAllViews();
        // 每版报纸的文章List
        List<PaperImage.ItemsBean> items = image.getItems();
        for (int i = 0; i < items.size(); i++) {
            List<PointF> ps = new ArrayList<>();
            // 每篇文章的newsId
            String newsId = items.get(i).getArticleid() + "";
            // 每篇文章的点阵（"points":"1047,139;1047,7;538,7;538,139;538,238;1047,238"）
            String points = items.get(i).getPoints();
            // 根据";"分割成单个坐标
            String[] split = points.split(";");
            for (String aSplit : split) {
                // 根据","分割成x、y坐标
                String[] s = aSplit.split(",");
                if (TextUtils.isEmpty(s[0]) || TextUtils.isEmpty(s[1])) {
                    return;
                }
                // 将x、y坐标加入List<PointF>中
                ps.add(new PointF(Float.parseFloat(s[0]), Float.parseFloat(s[1])));
            }
            // 将文章的坐标和newsId传入CoverView，加载阴影View
            CoverView cImageView = new CoverView(context, ps, newsId);
            // addView
            PaperImageView.this.addView(cImageView);
        }
        // 隐藏加载进度条
        hideProgressBar();
        loadComplete = true;
    }

    /**
     * 正在加载报纸版面时的加载进度条
     */
    public void showProgressBar() {
        loadComplete = false;
        setBackgroundDrawable(null);
        PaperImageView.this.removeAllViews();
        progressBar = new ProgressBar(context);
        LayoutParams layout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layout.gravity = Gravity.CENTER;
        this.addView(progressBar, layout);
    }

    /**
     * 隐藏加载进度条
     */
    public void hideProgressBar() {
        if (progressBar != null) {
            this.removeView(progressBar);
        }
    }

    /**
     * 用户点击事件
     *
     * @param event
     * @return
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!loadComplete) {
            return true;
        }
        switch (event.getAction()) {
            // 用户手指按下
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                PointF pointF = new PointF();
                pointF.set(lastX, event.getY());
                for (int i = 0; i < getChildCount(); i++) {
                    CoverView cImageView = (CoverView) getChildAt(i);
                    List<PointF> ps = cImageView.getPs();
                    // 判断用户点击的坐标是否在该阴影View范围中，若在，则该阴影View显示阴影效果
                    if (isPolygonContainPoint(pointF, ps)) {
                        cImageView.setAlpha(ALPHA_CLICKED);
                        cImageView.invalidate();
                        selectView = cImageView;
                    }
                }
                return true;
            // 用户手指抬起
            case MotionEvent.ACTION_UP:
                if (selectView != null) {
                    selectView.setAlpha(ALPHA_DEFAULT);
                    selectView.invalidate();
                    if (Math.abs(lastX - event.getX()) < 12) {
                        // 当用户手指移动距离的绝对值小于12时，认为它是点击事件，触发点击操作
                        callBack.doAction(selectView.getNewsId());
                    }
                    selectView = null;
                }
                return true;
            // 用户取消操作
            case MotionEvent.ACTION_CANCEL:
                if (selectView != null) {
                    selectView.setAlpha(ALPHA_DEFAULT);
                    selectView.invalidate();
                    selectView = null;
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 判断用户点击的坐标是否在阴影View范围中
     *
     * @param pointF        用户点击的坐标
     * @param vertexPointFs 阴影View的坐标
     * @return true / false
     */
    private boolean isPolygonContainPoint(PointF pointF, List<PointF> vertexPointFs) {
        int nCross = 0;
        for (int i = 0; i < vertexPointFs.size(); i++) {
            PointF p1 = vertexPointFs.get(i);
            PointF p2 = vertexPointFs.get((i + 1) % vertexPointFs.size());
            if (p1.y == p2.y) {
                continue;
            }
            if (pointF.y < Math.min(p1.y, p2.y)) {
                continue;
            }
            if (pointF.y >= Math.max(p1.y, p2.y)) {
                continue;
            }
            double x = (double) (pointF.y - p1.y) * (double) (p2.x - p1.x) / (double) (p2.y - p1.y) + p1.x;
            if (x > pointF.x) {
                nCross++;
            }
        }
        return (nCross % 2 == 1);
    }

    public void setCallBack(CallBackAction callBack) {
        this.callBack = callBack;
    }

    /**
     * 用户点击事件
     */
    public interface CallBackAction {
        public void doAction(String newsId);
    }
}