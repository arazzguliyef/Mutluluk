package com.arazguliyef.mutluluk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class GameView extends View {

    int skor = 0;
    static int screenWidth, screenHeight;
    int touchX, touchY;
    long UPDATE_MILLIS = 30;
    Bitmap bg, devil, smile;
    static Bitmap solider;
    Runnable runnable;
    Handler handler;
    Context context;
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<com.arazguliyef.mutluluk.devil> devils = new ArrayList<>();
    ArrayList<com.arazguliyef.mutluluk.smile> balloonsBad = new ArrayList<>();
    Paint bulletPaint, scorePaint;
    Rect dest;
    final int TEXT_SIZE = 60;
    boolean win = false;

    public GameView(Context context) {
        super(context);
        this.context = context;
        bg = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
        solider = BitmapFactory.decodeResource(context.getResources(), R.drawable.savasci);
        devil = BitmapFactory.decodeResource(context.getResources(), R.drawable.devil);
        smile = BitmapFactory.decodeResource(context.getResources(), R.drawable.smile);
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        screenWidth = point.x;
        screenHeight = point.y;
        dest = new Rect(0,0,screenWidth, screenHeight);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        for (int i=0; i <2; i++){
            com.arazguliyef.mutluluk.devil devil = new devil();
            devils.add(devil);
        }
        for (int i=0; i <2; i++){
            com.arazguliyef.mutluluk.smile smile = new smile();
            balloonsBad.add(smile);
        }

        bulletPaint = new Paint();
        bulletPaint.setColor(Color.BLACK);
        scorePaint = new Paint();
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(TEXT_SIZE);
        scorePaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bg, null, dest, null);
        for (int i = 0; i < devils.size(); i++){
            devils.get(i).bX += devils.get(i).bSpeedX;
            devils.get(i).bY += devils.get(i).bSpeedY;
            if(devils.get(i).bY > 250){
                devils.get(i).bSpeedY *= -1;
            }
            if(devils.get(i).bY < 0){
                devils.get(i).bSpeedY *= -1;
            }
            if(devils.get(i).bX < -devil.getWidth() - 200 || devils.get(i).bX > screenWidth + 200)
                devils.get(i).resetBalloon();

            balloonsBad.get(i).bX += balloonsBad.get(i).bSpeedX;
            balloonsBad.get(i).bY += balloonsBad.get(i).bSpeedY;
            if(balloonsBad.get(i).bY > 350){
                balloonsBad.get(i).bSpeedY *= -1;
            }
            if(balloonsBad.get(i).bY < 0){
                balloonsBad.get(i).bSpeedY *= -1;
            }
            if(balloonsBad.get(i).bX < -smile.getWidth() - 200 || balloonsBad.get(i).bX > screenWidth + 200)
                balloonsBad.get(i).resetBalloon();
        }

        for (int i = 0; i < devils.size(); i++){
            canvas.drawBitmap(devil, devils.get(i).bX, devils.get(i).bY, null);
            canvas.drawBitmap(smile, balloonsBad.get(i).bX, balloonsBad.get(i).bY, null);
        }
        for(int i=0; i< bullets.size(); i++){
            if(bullets.get(i).bulletY > -bullets.get(i).bulletY){
                bullets.get(i).bulletY -= bullets.get(i).bulletVelocity;
                canvas.drawCircle(bullets.get(i).bulletX, bullets.get(i).bulletY, 10, bulletPaint);
            }else{
                bullets.remove(i);
            }
        }
        for(int i=0; i<bullets.size(); i++){
            for(int j = 0; j< devils.size(); j++){
                if(bullets.get(i).bulletX >= balloonsBad.get(j).bX
                && bullets.get(i).bulletX <= balloonsBad.get(j).bX + smile.getWidth()
                && bullets.get(i).bulletY >= balloonsBad.get(j).bY
                && bullets.get(i).bulletY <= balloonsBad.get(j).bY + smile.getHeight()){
                    balloonsBad.get(j).resetBalloon();
                    skor++;
                    if(skor > 4){
                        Intent intent = new Intent(getContext(), GameOver.class);
                        win = true;
                        intent.putExtra("win", win);
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    }
                }
                else if(bullets.get(i).bulletX >= devils.get(j).bX
                        && bullets.get(i).bulletX <= devils.get(j).bX + devil.getWidth()
                        && bullets.get(i).bulletY >= devils.get(j).bY
                        && bullets.get(i).bulletY <= devils.get(j).bY + devil.getHeight()){
                    devils.get(j).resetBalloon();
                    skor--;
                    if(skor < -2){
                        Intent intent = new Intent(getContext(), GameOver.class);
                        win = false;
                        intent.putExtra("win", win);
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    }
                }
            }
        }

        canvas.drawText("Puan Tablosu: " + skor, 0, TEXT_SIZE, scorePaint);
        canvas.drawBitmap(solider, screenWidth/2 - solider.getWidth()/2, screenHeight - solider.getHeight(), null);
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        touchX = (int)event.getX();
        touchY = (int)event.getY();
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            if(touchX >= screenWidth/2 - solider.getWidth()/2
                && touchX <= screenWidth/2 + solider.getWidth()/2
                    && touchY >= screenHeight - solider.getHeight()){
                if(bullets.size() < 4){
                    Bullet bullet = new Bullet();
                    bullets.add(bullet);
                }
            }
        }
        return true;
    }
}
