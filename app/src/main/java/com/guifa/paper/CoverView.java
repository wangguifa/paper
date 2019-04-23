package com.guifa.paper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.view.View;

import java.util.List;

/**
 * 阴影View
 */
public class CoverView extends View {

    /**
     * 阴影View的坐标，首尾顺次连接即可得到该阴影区域
     */
    private List<PointF> ps;
    /**
     * 文章ID，为了进入详情页获取该文章内容
     */
    private String newsId;
    /**
     * 阴影View默认的透明度
     */
    private int alpha = 0;

    public CoverView(Context context) {
        super(context);
    }

    public CoverView(Context context, List<PointF> ps, String newsId) {
        super(context);
        this.ps = ps;
        this.newsId = newsId;
    }

    @Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        for (int i = 0; i < ps.size(); i++) {
            PointF p = ps.get(i);
            if (p == null) {
                continue;
            }
            if (i == 0) {
                // 设置阴影View的坐标起点
                path.moveTo(p.x, p.y);
            } else {
                // 依次连接各个坐标得到该阴影区域
                path.lineTo(p.x, p.y);
            }
        }
        path.close();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.TRANSPARENT);
        paint.setAlpha(alpha);
        canvas.drawPath(path, paint);
    }

    public List<PointF> getPs() {
        return ps;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }
}