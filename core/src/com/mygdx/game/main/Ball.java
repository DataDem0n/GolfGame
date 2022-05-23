package com.mygdx.game.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Ball extends Sprite {
    Sprite sMain;
    Texture tex;
    float x;
    float y;
    Rectangle ballHitBox;

    public Ball(){
        tex = new Texture("Ball.png");
        sMain = new Sprite(tex);
        ballHitBox = new Rectangle();
        sMain.setCenter(sMain.getHeight()/2,sMain.getWidth()/2);
    }

    /**
     * setPos() method sets the position of the ball on the terrain.
     * @param x: the center x-coordinate of the ball
     * @param y: the center y-coordinate of the ball
     * @param PPM: This method disposes all the elements that were previously on the screen.
     */
    public void setPos(float x,float y,float PPM) {
        this.x = x;
        this.y = y;
        sMain.setPosition(x+(0.125f), y+(0.125f));//0.125 comes from having 50 points per axis
        ballHitBox.set(x,y,1,1);
    }

    /**
     * This method draws the ball on the terrain.
     * @param Bat: is the spritebatch used to render the texture of the ball inside the render method of MainGame
     */
    public void draw(SpriteBatch Bat){
        sMain.setSize((tex.getWidth())/200f,(tex.getHeight())/200f);
        sMain.draw(Bat);
    }
}
