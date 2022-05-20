package com.mygdx.game.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
public class TreeVisual {
    Sprite spriteLog;
    Texture log;
    Sprite spriteLeaves;
    Texture leaves;
    float x;
    float y;
    float treeradius = 1;
    float size = 250;
    Rectangle treeHitBox;

    public TreeVisual(){
        /**
         * Tree log sprite and textures
         */
        log = new Texture("Log.png");
        spriteLog = new Sprite(log);
        treeHitBox = new Rectangle();
        leaves = new Texture("Leaves.png");
        spriteLeaves = new Sprite(leaves);
    }

    /**
     * @param x: the center x-coordinate is set to this
     * @param y: the center y-coordinate is set to this
     * setHolePos() method sets the position of the whole on the terrain
     */
    public void setTreePos(float x,float y) {
        this.x = x;
        this.y = y;
        spriteLog.setPosition(x-.25f,y-.25f);
        spriteLeaves.setPosition(x-.25f,y-.25f);

        treeHitBox.set(x,y+1.6f,8,5.8f);
    }

    public void setOpac(boolean s){
        if(s){
            spriteLeaves.setAlpha(0.25f);
            spriteLog.setAlpha(0.25f);
        }else{
            spriteLeaves.setAlpha(1);
            spriteLog.setAlpha(1);
        }
    }

    /**
     * This method draws the hole on the screen.
     * @return the center y-coordinate of the ball
     */
    public void draw(SpriteBatch Bat){
        spriteLog.setSize((log.getWidth()/size)*(2*treeradius),(log.getHeight()/size)*(2*treeradius));
        spriteLog.draw(Bat);
    }
    public void drawLeaves(SpriteBatch Bat){
        spriteLeaves.setSize((leaves.getWidth()/size)*(2*treeradius),(leaves.getHeight()/size)*(2*treeradius));
        spriteLeaves.draw(Bat);
    }

}
