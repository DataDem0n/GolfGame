package com.mygdx.game.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tree {
    Sprite spriteLog;
    Texture log;
    Sprite spriteLeaves;
    Texture leaves;
    float x;
    float y;
    float treeradius = 1;
    float size = 250;


    public Tree(){
        /**
         * Tree log sprite and textures
         */
        //TODO: make sprites for logs and leaves
        log = new Texture("Hole.png");
        spriteLog = new Sprite(log);

        leaves = new Texture("Hole.png");
        spriteLeaves = new Sprite(log);
    }

    /**
     * @param PPM: pixel per meter for the rendering system
     * @param x: the center x-coordinate is set to this
     * @param y: the center y-coordinate is set to this
     * setHolePos() method sets the position of the whole on the terrain
     */
    public void setTreePos(float x,float y, float PPM) {
        this.x = x;
        this.y = y;

        //spriteHole.setPosition(x-(.25f*holeradius), y-(.25f*holeradius));
    }


    /**
     * This method gets the center x-coordinate of the ball
     * @return the center x-coordinate of the ball
     */
    public float getCenterX(){
        return spriteLeaves.getX()+spriteLeaves.getWidth();
    }

    /**
     * This method gets the center y-coordinate of the ball
     * @return the center y-coordinate of the ball
     */
    public float getCenterY(){
        return spriteLeaves.getY()+spriteLeaves.getHeight()/2;
    }

    /**
     * This method draws the hole on the screen.
     * @return the center y-coordinate of the ball
     */
    public void drawLog(SpriteBatch Bat){
        spriteLog.setSize((log.getWidth()/size)*(2*treeradius),(log.getHeight()/size)*(2*treeradius));
        spriteLog.draw(Bat);

        spriteLeaves.setSize((leaves.getWidth()/size)*(2*treeradius),(leaves.getHeight()/size)*(2*treeradius));
        spriteLeaves.draw(Bat);
    }
}
