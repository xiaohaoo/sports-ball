package com.xiaohao.app.sportsball;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surfaceView = findViewById(R.id.surfaceView);
        init();
    }

    public void init() {
        SurfaceViewCallback surfaceViewCallback = new SurfaceViewCallback();
        surfaceView.setOnTouchListener(surfaceViewCallback);
        surfaceView.getHolder().addCallback(surfaceViewCallback);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> surfaceViewCallback.ballList.add(new Ball((int) (Math.random() * MainActivity.this.surfaceView.getWidth()), 0)));

        SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
    }

    private class SurfaceViewCallback implements SurfaceHolder.Callback, View.OnTouchListener {
        private final Paint paint;
        private final List<Ball> ballList;
        private final EventCircle eventCircle = new EventCircle(0, 0);
        private boolean isTouchDown = false;
        private int x;
        private int y;


        public SurfaceViewCallback() {
            paint = new Paint();
            paint.setColor(Color.WHITE);
            ballList = new ArrayList<>();

        }


        @Override
        public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

        }


        @Override
        public void surfaceCreated(SurfaceHolder arg0) {
            Ball.screenWith = MainActivity.this.surfaceView.getWidth();
            Ball.screenHeight = MainActivity.this.surfaceView.getHeight();
            new Thread(() -> {
                while (true) {
                    Canvas c = MainActivity.this.surfaceView.getHolder().lockCanvas();
                    c.drawPaint(paint);

                    if (isTouchDown) {
                        eventCircle.moveTo(x, y);
                        eventCircle.draw(c);
                        for (int i = 0; i < ballList.size(); i++) {
                            ballList.get(i).moveAndDrawWithEventCircle(c, x, y);
                        }
                        eventCircle.addSize();
                    } else {
                        for (int i = 0; i < ballList.size(); i++) {
                            ballList.get(i).moveAndDraw(c);
                        }
                    }
                    MainActivity.this.surfaceView.getHolder().unlockCanvasAndPost(c);

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            }).start();
        }


        @Override
        public void surfaceDestroyed(SurfaceHolder arg0) {

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE: {
                    isTouchDown = true;
                    this.x = (int) event.getX();
                    this.y = (int) event.getY();
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    eventCircle.SIZE = 90;
                    isTouchDown = false;
                    break;
                }
            }
            return true;
        }
    }
}