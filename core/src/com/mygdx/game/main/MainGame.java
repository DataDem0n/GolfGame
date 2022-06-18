package com.mygdx.game.main;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import obstacles.Tree;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;

public class MainGame extends ApplicationAdapter {
	ArrayList<Float> coordsX;
	ArrayList<Float> coordsY;

	SpriteBatch batch;
	ShapeRenderer s;
	Ball gBall;
	ScreenViewport viewport;
	Hole myHole;
	WindowMain WM;
	TreeVisual[] tree1;


	public static final float PPM =10f;

	float holeX= (float) DataField.targetRXY[1];
	float holeY= (float) DataField.targetRXY[2];

	/** Creates all the needed elements and sprites when the window is first opened.
	 *
	 */
	@Override
	public void create (){
		System.out.println(Arrays.toString(DataField.sandPit));
		WM = new WindowMain();
		pointGenerator();
		viewport = new ScreenViewport();
		viewport.setUnitsPerPixel(1/PPM);
		viewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		myHole = new Hole();
		batch = new SpriteBatch();
		gBall= new Ball();
		tree1 = new TreeVisual[DataField.gameForest.getForest().size()];

		for (int i = 0;i<tree1.length;i++) {
			tree1[i] = new TreeVisual();
			tree1[i].setPos((float) DataField.gameForest.getForest().get(i).getCoordX()*1.4f,(float) DataField.gameForest.getForest().get(i).getCoordY()*1.7f);
		}

		s = new ShapeRenderer();
		Gdx.gl.glClearColor(.5f,0,.5f,1);
		myHole.setPos(holeX*1.8f,holeY*1.8f);

	}

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
		return DataField.terrain.apply(x,y);
	}

	/**
	 * The void method renders the screen in x fps (x: is set from the DesktopLauncher file).
	 */

	@Override
	public void render (){
		WM.update();
		s.setProjectionMatrix(viewport.getCamera().combined);
		s.begin(ShapeRenderer.ShapeType.Filled);

		gBall.setPos(((float)DataField.x)*1.8f,((float)DataField.y)*1.8f);

		//draws lines based on the height at the coord that the line is drawn at
		for (int i = 0; i < coordsX.size(); i++) {
			for (int j = 0; j < coordsY.size(); j++) {

				double t = calcHeight(coordsX.get(i),coordsY.get(j));
				s.setColor(getColorHeight(t));//bad implementation should be stored and called from elsewhere.
				s.setColor(getColorHeight(t));

				if(coordsX.get(i) > DataField.sandPit[0] && coordsX.get(i) < DataField.sandPit[2] &&
						coordsY.get(j) > DataField.sandPit[1] && coordsY.get(j) < DataField.sandPit[3]) {
					s.setColor(Color.YELLOW);
				}

				if (i + 1 < coordsX.size() && j + 1 < coordsY.size())
					s.rectLine(new Vector2(coordsX.get(i)*2.5f,coordsY.get(j)*2.5f),new Vector2(coordsX.get(i+1)*2.5f,coordsY.get(j)*2.5f),2f);

			}
		}

		s.end();

		batch.setProjectionMatrix(viewport.getCamera().combined);

		batch.begin();

		for (int i = 0;i<tree1.length;i++) {
			if (gBall.ballHitBox.overlaps(tree1[i].treeHitBox)){
				tree1[i].setOpac(true);
			}
			else{
				tree1[i].setOpac(false);
			}
			tree1[i].draw(batch);
			tree1[i].drawLeaves(batch);
		}

		myHole.draw(batch);

		gBall.draw(batch);

		batch.end();

	}

	/**
	 * The pointGenerator method creates all the points on which the tiles are drawn.
	 */

	public void pointGenerator(){

		coordsX = new ArrayList<Float>();
		coordsY = new ArrayList<Float>();

		for (float i = -(25); i <= (25); i= i+.18f) {
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
