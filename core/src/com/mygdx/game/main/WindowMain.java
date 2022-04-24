package com.mygdx.game.main;

import com.badlogic.gdx.Gdx;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WindowMain{
    JLabel counter;
    int c = 0;//counter integer for strokes

    //window
    JFrame frame;
    JPanel panel;
    JPanel sideMenu;

    //velocity
    JTextField velocityX;
    JTextField velocityY;
    JLabel inputVelocityX;
    JLabel inputVelocityY;

    //origin
    JLabel ballXCoord;
    JLabel ballYCoord;
    JButton initC;

    // Push Button
    JButton pushButton = new JButton("PUTT!");
    BotBasic Charley = new BotBasic();

    WindowMain(){
        initC = new JButton("Start Bot");
        initC.addActionListener(e -> {
            Charley.start();
        });

        pushButton.addActionListener (e -> {
            //counter updater
            c=c+1;
            counter.setText("Storkes: " + c);
            System.out.println("No. of strokes: " + c);

            //receive velocities
            ArrayList<Double> xc = new ArrayList<Double>();
            xc.add(Double.parseDouble(velocityX.getText()));
            DataField.velocityX = xc;
            ArrayList<Double> yc = new ArrayList<Double>();
            yc.add(Double.parseDouble(velocityY.getText()));
            DataField.velocityY = yc;
            DataField.GUI = false;
            Timer t = new Timer(100, e1 -> {
                DataField.GUI = true;
            });
        });

        frame = new JFrame("Golf controls");
        Image frameIcon = new ImageIcon("Icon.png").getImage();
        frame.setIconImage(frameIcon);
        panel = new JPanel();
        sideMenu = new JPanel();
        JMenuBar menuBar = new JMenuBar();

        //velocity visual elements
        velocityX = new JTextField();
        velocityY = new JTextField();
        inputVelocityX = new JLabel("Velocity X axis");
        inputVelocityY = new JLabel("Velocity Y axis");

        //origin coordinates visual elements

        ballXCoord = new JLabel("Ball X coord: ");

        ballXCoord.setText("Ball X coord:" + DataField.x);


        ballYCoord = new JLabel("Ball Y coord: ");

        counter = new JLabel("Strokes: " + c); //this returns the number of total strokes use

        // Elevation Depiction
        JLabel elevationDepictionLabel = new JLabel("\nElevation Depiction");
        JLabel highElevationLabel = new JLabel("10");
        JLabel lowElevationLabel = new JLabel("-10");

        //imgL.set
        ImageIcon gradientImage = new ImageIcon("gradient_chart.jpg");
        JLabel imgL = new JLabel(gradientImage);
        frame.add(imgL);

        //menubar items
        JMenu fileTab = new JMenu("File");
        JMenu extrasTab = new JMenu("Extras");
        menuBar.add(fileTab);
        menuBar.add(extrasTab);

        //file tab buttons
        JMenuItem exit = new JMenuItem("Exit");
        fileTab.add(exit);

        exit.addActionListener (e -> {
            if(e.getSource()==exit){
                System.exit(0);
            }
        });
        //extras tab buttons
//        JMenuItem music = new JMenuItem("Music????");
//        extrasTab.add(music);

        frame.setJMenuBar(menuBar);
        menuBar.setVisible(true);

        //elements inside the control window
        sideMenu.add(panel);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints(); // init constraints
        gc.insets = new Insets(2, 2, 5, 2);


        //This adds a label for the input of the initial x-coordinate of the ball.
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 1;
        panel.add(ballXCoord, gc);

        //This adds a label for the input of the initial y-coordinate of the ball.
        gc.gridx = 1;
        gc.gridy = 0;
        gc.gridwidth = 1;
        panel.add(ballYCoord, gc);

        //This adds a label for inputting the x-velocity of the ball
        gc.gridx = 0;
        gc.gridy = 9;
        gc.fill=GridBagConstraints.CENTER;
        gc.gridwidth = 1;
        panel.add(inputVelocityX, gc);

        //This adds a label for inputting the y-velocity of the ball
        gc.gridx = 1;
        gc.gridy = 9;
        gc.fill=GridBagConstraints.CENTER;
        gc.gridwidth = 1;
        panel.add(inputVelocityY, gc);

        //This adds a text box for inputting the x-velocity of the ball
        gc.gridx = 0;
        gc.gridy = 10;
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridwidth = 1;
        panel.add(velocityX, gc);

        //This adds a text box for inputting the y-velocity of the ball
        gc.gridx = 1;
        gc.gridy = 10;
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridwidth = 1;
        panel.add(velocityY, gc);

        //This adds the "PUTT!" button to the menu
        gc.gridx = 0;
        gc.gridy = 14;
        gc.gridwidth = 1;
        panel.add(pushButton, gc);

        //This adds the counter which represents the number of strokes made until now
        gc.gridx = 1;
        gc.gridy = 14;
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridwidth = 1;
        panel.add(counter, gc);

        //This adds a button that locks the origin coordinates in
        gc.gridx = 0;
        gc.gridy = 15;
        gc.gridwidth = 1;
        panel.add(initC, gc);

        //This adds the label for Elevation Depiction
        gc.gridx = 0;
        gc.gridy = 16;
        gc.fill=GridBagConstraints.CENTER;
        gc.gridwidth = 2;
        panel.add(elevationDepictionLabel, gc);

        //This adds the label for the lower bound
        gc.gridx = 0;
        gc.gridy = 17;
        gc.gridwidth = 1;
        panel.add(lowElevationLabel, gc);

        //This adds the label for the upper bound
        gc.gridx = 1;
        gc.gridy = 17;
        gc.gridwidth = 1;
        panel.add(highElevationLabel, gc);

        //This adds the legend of the gradient in as a .jpg
        gc.gridx = 0;
        gc.gridy = 18;
        gc.gridwidth = 2;
        panel.add(imgL, gc);

        frame.add(sideMenu);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(300,637);
        frame.setVisible(true);
    }
}