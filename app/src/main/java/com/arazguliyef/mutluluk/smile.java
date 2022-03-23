package com.arazguliyef.mutluluk;

public class smile extends devil {
    public smile() {
        resetBalloon();
    }

    @Override
    public void resetBalloon() {
        if(Math.random() < 0.5){
            bX = -200;
            bSpeedX = 6 + (int)(Math.random() * 15);
        }else{
            bX = GameView.screenWidth + 200;
            bSpeedX = -1 * (6 + (int)(Math.random() * 15));
        }
        bY = (int)(Math.random() * 250);
        if(Math.random() < 0.5){
            bSpeedY = 3 + (int)(Math.random() * 6);
        }else{
            bSpeedY =  -1 * (3 + (int)(Math.random() * 6));
        }
    }
}
