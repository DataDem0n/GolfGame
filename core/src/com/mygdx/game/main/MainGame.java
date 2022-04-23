package com.mygdx.game.main;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.BiFunction;

public class MainGame extends ApplicationAdapter {
	ArrayList<Float> coordsX;
	ArrayList<Float> coordsY;

	SpriteBatch batch;
	ShapeRenderer s;
	Ball gBall;
	ScreenViewport viewport;
	Hole myHole;
	Tree tree;

	public static final float PPM =10f;

	float holeX= (float) DataField.targetRXY[1];
	float holeY= (float) DataField.targetRXY[2];

	@Override
	public void create (){
		pointGenerator();
		viewport = new ScreenViewport();
		viewport.setUnitsPerPixel(1/PPM);
		viewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		myHole = new Hole();
		batch = new SpriteBatch();
		gBall= new Ball();
		//TODO implement trees for each tree: draw tree.
		tree = new Tree();
		tree.setTreePos(4,4,PPM);
		s = new ShapeRenderer();
		Gdx.gl.glClearColor(.5f,0,.5f,1);
		myHole.setHolePos(holeX*1.8f,holeY*1.8f,PPM);//1.8f why?
	}
//test
	/**
	 * This method sets the color of the terrain based on the height of the point.
	 * @param val: this is the height of the terrain
	 * @return: the color based on the height
	 */

	public Color getColorHeight(double val) {

		if(val <-10||val>10){
			return Color.BLACK;
		}

		int greenPositive = (int) (55 + val * 20);
		int bluePositive = (int) (11 + val * 4);
		int greenNegative = (int) Math.abs(76 + val * 6);
		int blueNegative = (int) Math.abs(255 +val * 20);

		if(val>=0){
			return new Color(Color.rgba8888(0 / 255f, greenPositive / 255f, bluePositive / 255f, 1));
		}

		if (val < 0) {
			return new Color(Color.rgba8888(0 / 255f, greenNegative / 255f, blueNegative / 255f, 1));
		}

		return null;
	}


	/**
	 *This method calculates height
	 * @param x gets the x-coordinate of the points of which the height is being calculated
	 * @param y
	 * @return
	 */
	public double calcHeight(double x,double y){
//		return 0;
//		return 0.1*x + 1;
//		return Math.pow(Math.E,(-(((x*x)+(y*y))/40)));
//		return (-Math.E*.5)*((-(x*x)-(y*y))/35f);
//		return 0.05*((x*x)+(y*y));
//		return Math.cos(x+(y*y)); //testing
		return  1;
	}

	/**
	 * The void method renders the screen in x fps (x: is gotten from the DesktopLauncher file).
	 */
	@Override
	public void render (){
		s.setProjectionMatrix(viewport.getCamera().combined);
		s.begin(ShapeRenderer.ShapeType.Filled);


		gBall.setPos((DataField.x)*1.8f,(DataField.y)*1.8f,PPM);
		//draws lines based on the height at the coord that the line is drawn at
		for (int i = 0; i < coordsX.size(); i++) {
			for (int j = 0; j < coordsY.size(); j++) {

				double t = calcHeight(coordsX.get(i),coordsY.get(j));
				s.setColor(getColorHeight(t));//bad implementation should be stored and called from elsewhere.
				s.setColor(getColorHeight(t));

				if(coordsX.get(i)>DataField.sandPit[0]&&coordsX.get(i)<DataField.sandPit[1]&&coordsY.get(j)>DataField.sandPit[2]&&coordsY.get(j)<DataField.sandPit[3]){
					s.setColor(Color.YELLOW);
				}

				if (i + 1 < coordsX.size() && j + 1 < coordsY.size())
					s.rectLine(new Vector2(coordsX.get(i)*2.5f,coordsY.get(j)*2.5f),new Vector2(coordsX.get(i+1)*2.5f,coordsY.get(j)*2.5f),2f);

//				if (i + 1 < coordsX.size() && j + 1 < coordsY.size())
//					s.line(coordsX.get(i)*2.5f,coordsY.get(j)*2.5f,coordsX.get(i)*2.5f,coordsY.get(j+1)*2.5f);
//
//				if (i + 1 < coordsX.size() && j + 1 < coordsY.size())
//					s.line(coordsX.get(i)*2.5f,coordsY.get(j)*2.5f,coordsX.get(i+1)*2.5f,coordsY.get(j)*2.5f);

			}
		}

//		s.rect(tree.treeHitBox.x, tree.treeHitBox.y,8,5.8f ); hitboxes
//		s.rect(gBall.ballHitBox.x, gBall.ballHitBox.y,1,1 );
		s.end();

		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();

		if (gBall.ballHitBox.overlaps(tree.treeHitBox)){
			tree.setOpac(true);
		}
		else{
			tree.setOpac(false);
		}


		myHole.draw(batch);

		gBall.draw(batch);
		tree.draw(batch);
		tree.drawLeaves(batch);
		batch.end();

	}


	/**
	 * The pointGenerator method creates all the points on which the tiles are drawn.
	 */
	public void pointGenerator(){

		coordsX = new ArrayList<Float>();
		coordsY = new ArrayList<Float>();

		for (float i = -(25); i <= (25); i= i+.18f) {//TODO fix zoom offset on non reoccurring formula. based on .25
			//this for loop dertermines which points are painted on the screen, the use above starts from -(pointAmount)/2f
			// and then loops through the whole array which isnt ideal. we need to make the array smaller with out losing definition.
				coordsX.add(i);//generates coords from -pointA/2 to +pointA/2(eg screen size =10 its results in [-5...+5])
				coordsY.add(i);
		}

	}

	/**
	 * This method disposes all the elements that were previously on the screen.
	 */
	@Override
	public void dispose () {
		s.dispose();
		batch.dispose();
	}
}
