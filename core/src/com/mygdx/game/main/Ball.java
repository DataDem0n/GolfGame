package com.mygdx.game.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ball {
    Sprite sMain;
    Texture tex;
    float x;
    float y;

    public Ball(){
        tex = new Texture("Ball.png");
        sMain = new Sprite(tex);
        //sMain.setCenter(sMain.getHeight()/2f,sMain.getWidth()/2f);
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
        sMain.setPosition(x+(0.125f), y+(0.125f));//0.125 comes from having 50 points per axis s
    }

    /**
     * This method gets the center x-coordinate of the ball
     * @return: returns the center x-coordinate of the ball
     */

    public float getCenterX(){
        return sMain.getX()+sMain.getWidth()/2;
    }

    /**
     * This method gets the center y-coordinate of the ball
     * @return: returns the center y-coordinate of the ball
     */
    public float getCenterY(){
        return sMain.getY()+sMain.getHeight()/2;
    }


    /**
     * This method draws the ball on the terrain.
     * @param Bat: is the spritebatch used to render the texture of the ball inside the render method of MainGame
     */
    //
    public void draw(SpriteBatch Bat){
        sMain.setSize((tex.getWidth())/200f,(tex.getHeight())/200f);
        sMain.draw(Bat);
    }
}
