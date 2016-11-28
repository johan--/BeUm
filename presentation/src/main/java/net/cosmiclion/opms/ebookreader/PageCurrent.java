package net.cosmiclion.opms.ebookreader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by longpham on 11/24/2016.
 */

public class PageCurrent extends View {

    public int colorCode = Color.MAGENTA;

    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    public PageCurrent(Context context) {
        super(context);
    }

    public PageCurrent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public PageCurrent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = getWidth() / 2;
        int h = getHeight() / 2;

        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(w, 2 * h);
        path.lineTo(2 * w, 0);
        path.lineTo(0, 0);

        path.close();

        Paint p = new Paint();
        p.setColor(colorCode);
        p.setAntiAlias(true);

        canvas.drawPath(path, p);
    }
}