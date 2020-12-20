package com.xiaohao.app.sportsball;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;


public class Ball {

    private static final int SIZE = 90;
    public static int screenWith;
    public static int screenHeight;
    private final Paint paint;
    public int x;
    public int y;
    public int speedX = (int) (Math.random() * 10) + 5;
    public int speedY = (int) (Math.random() * 10) + 5;
    private long time = 0;


    private int COLOR;


    public Ball(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.COLOR = color;
        paint = new Paint();
        paint.setColor(COLOR);
        RadialGradient rg = new RadialGradient(x, y,
                SIZE, COLOR, COLOR, Shader.TileMode.CLAMP);
        paint.setShader(rg);
        paint.setAntiAlias(true);
    }


    public Ball(int x, int y) {
        this(x, y, Color.RED);
    }

    public void moveAndDraw(Canvas c) {
        this.x += this.speedX;
        this.y += this.speedY;

        if (this.x > screenWith || this.x <= 0) {
            this.speedX = -this.speedX;
        }
        if (this.y > screenHeight || this.y <= 0) {
            this.speedY = -this.speedY;
        }

        draw(c);
    }


    public void moveAndDrawWithEventCircle(Canvas c, int x, int y) {
        this.x += this.speedX;
        this.y += this.speedY;


        if (System.currentTimeMillis() - time >= 1 * 1000) {
            int xL = this.x - x;
            int yL = this.y - y;
            if (Math.abs(xL) <= EventCircle.SIZE + SIZE && Math.abs(yL) <= EventCircle.SIZE + SIZE) {
                this.speedX = (int) (xL < 0 ? this.speedX + 25 - 0.01 * Math.abs(xL)
                        : -this.speedX - 25 + 0.01 * Math.abs(xL));

                if (this.speedY > 0) {
                    this.speedY = this.speedY - 7;
                } else {
                    this.speedY = this.speedY + 7;
                }
            }
            time = System.currentTimeMillis();
        } else {
            if (Math.abs(this.speedX) > 15) {
                if (this.speedX >= 0) {
                    this.speedX = this.speedX - 1;
                } else {
                    this.speedX = this.speedX + 1;
                }

            }


            if (Math.abs(this.speedX) < 5) {
                if (this.speedX >= 0) {
                    this.speedX = this.speedY + 1;
                } else {
                    this.speedX = this.speedX - 1;
                }

            }


            if (Math.abs(this.speedY) < 5) {
                if (this.speedY >= 0) {
                    this.speedY = this.speedY + 1;
                } else {
                    this.speedY = this.speedY - 1;
                }

            }

        }


        if (this.x > screenWith || this.x <= 0) {
            this.speedX = -this.speedX;
        }
        if (this.y > screenHeight || this.y <= 0) {
            this.speedY = -this.speedY;
        }
        draw(c);

    }


    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Canvas c) {
        RadialGradient rg = new RadialGradient(x, y,
                SIZE, COLOR, COLOR, Shader.TileMode.CLAMP);
        paint.setShader(rg);
        c.drawCircle(x, y, SIZE, paint);
    }


}
