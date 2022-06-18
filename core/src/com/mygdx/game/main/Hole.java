package com.mygdx.game.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hole implements SpriteLibGDX{
    Sprite spriteHole;
    Texture tex;
    float x;
    float y;
    float holeradius = 1;
    float size = 250;


    public Hole(){
        tex = new Texture("Hole.png");
        spriteHole = new Sprite(tex);
    }

    /**
     * @param x: the center x-coordinate is set to this
     * @param y: the center y-coordinate is set to this
     * setHolePos() method sets the position of the whole on the terrain
     */

    public void setPos(float x,float y) {
        this.x = x;
        this.y = y;

        spriteHole.setPosition(x-(.25f*holeradius), y-(.25f*holeradius));
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
