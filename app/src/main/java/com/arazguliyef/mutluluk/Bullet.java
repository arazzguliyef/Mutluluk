package com.arazguliyef.mutluluk;

public class Bullet {

    int bulletX, bulletY, bulletVelocity;

    public Bullet() {
        resetBullet();
    }

    public void resetBullet() {
        bulletX = GameView.screenWidth/2;
        bulletY = GameView.screenHeight - GameView.solider.getHeight();
        bulletVelocity = 40;
    }
}
