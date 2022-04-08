package com.mygdx.game.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hole {
    Sprite spriteHole;
    Texture tex;
    float x;
    float y;
    float holeradius = 1;
    float size = 250;


    public Hole(){
        tex = new Texture("Hole.png");
        spriteHole = new Sprite(tex);
        //spriteHole.setCenter(spriteHole.getHeight()/2f,spriteHole.getWidth()/2f);
    }

    /**
     * @param PPM: pixel per meter for the rendering system
     * @param x: the center x-coordinate is set to this
     * @param y: the center y-coordinate is set to this
     * setHolePos() method sets the position of the whole on the terrain
     */
    public void setHolePos(float x,float y, float PPM) {
        this.x = x;
        this.y = y;

        spriteHole.setPosition(x-(.25f*holeradius), y-(.25f*holeradius));
    }


    /**
     * This method gets the center x-coordinate of the ball
     * @return the center x-coordinate of the ball
     */
    public float getCenterX(){
        return spriteHole.getX()+spriteHole.getWidth()/2;
    }

    /**
     * This method gets the center y-coordinate of the ball
     * @return the center y-coordinate of the ball
     */
    public float getCenterY(){
        return spriteHole.getY()+spriteHole.getHeight()/2;
    }

    /**
     * This method draws the hole on the screen.
     * @return the center y-coordinate of the ball
     */
    public void draw(SpriteBatch Bat){
        spriteHole.setSize((tex.getWidth()/size)*(2*holeradius),(tex.getHeight()/size)*(2*holeradius));
        spriteHole.draw(Bat);
    }
}
