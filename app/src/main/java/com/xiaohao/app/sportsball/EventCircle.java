package com.xiaohao.app.sportsball;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

public class EventCircle {
    public static float SIZE = 90;
    private final int COLOR = Color.GREEN;
    public int x;
    public int y;
    private Paint paint;

    private float number = 1;

    public EventCircle(int x, int y, int color) {
        this.x = x;
        this.y = y;
    }


    public EventCircle(int x, int y) {
        this(x, y, Color.GREEN);
    }

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
        paint = new Paint();

    }

    public void addSize() {
        if (SIZE < 180) {
            SIZE = SIZE + 0.4f;
        }
    }

    public void draw(Canvas c) {
        RadialGradient rg = new RadialGradient(x, y,
                SIZE, COLOR, COLOR, Shader.TileMode.CLAMP);
        paint.setShader(rg);
        c.drawCircle(x, y, SIZE, paint);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);

        number = number + 0.1f;

        if (number > 4) {
            number = 1;
        }


        for (int i = 1; i < number; i++) {

            c.drawCircle(x, y, SIZE + i * 35, paint);
        }


    }


}
