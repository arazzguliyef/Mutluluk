package com.arazguliyef.mutluluk;

public class devil {

    public int bX, bY, bSpeedX, bSpeedY;

    public devil() {
        resetBalloon();
    }

    public void resetBalloon() {
        if(Math.random() < 0.5){
            bX = -200;
            bSpeedX = 5 + (int)(Math.random() * 11);
        }else{
            bX = GameView.screenWidth + 200;
            bSpeedX = -1 * (5 + (int)(Math.random() * 11));
        }
        bY = (int)(Math.random() * 200);
        if(Math.random() < 0.5){
            bSpeedY = 2 + (int)(Math.random() * 3);
        }else{
            bSpeedY =  -1 * (2 + (int)(Math.random() * 3));
        }
    }
}
