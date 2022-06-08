package com.mygdx.game.main;

//import Bots.AI;
//import Bots.AdjacencyField;
//import Bots.SlopeField;
import Bot_Work.CosineFinder;
import Bot_Work.WeightedVector;
import Music.MusicControls;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WindowMain{
    JLabel counter;
    int c = 0;//counter integer for strokes

    //Window elements
    JFrame frame;
    JPanel panel;
    JPanel sideMenu;

    //Velocity swing text fields and labels
    JTextField velocityX;
    JTextField velocityY;
    JLabel inputVelocityX;
    JLabel inputVelocityY;

    //Origin swing labels
    JLabel ballXCoord;
    JLabel ballYCoord;

    //Bot buttons
    JButton ruleBotButton;
    JButton pathBot;

    //Push Button
    JButton pushButton = new JButton("PUTT!");

    //initialising bot elements
    //BotBasic Charley = new BotBasic();



   // AI newtonSlave = new AI(DataField.terrain, 1, null, null, DataField.x, DataField.y, DataField.sFriction, DataField.kFriction, DataField.targetRXY[1], DataField.targetRXY[2], DataField.targetRXY[0]);
    List<List> vel;

    //solver label
    //JLabel selectedSolver = new JLabel("Solver: ");   will be used in phase 3
    WindowMain(){
        pathBot = new JButton("Start Path Finding Bot");
        ruleBotButton = new JButton("Start rule Bot");


        CosineFinder Frank = new CosineFinder();
        ArrayList<WeightedVector> v = Frank.vectorFind(DataField.x,DataField.y,DataField.targetRXY[1],DataField.targetRXY[2]);
        ArrayList<Double> xVel = new ArrayList<>();
        ArrayList<Double> yVel = new ArrayList<>();
        for (WeightedVector wv:v) {
            xVel.add(wv.getX());
            yVel.add(wv.getY());
        }
        ruleBotButton.addActionListener(e -> {
            Charley.start();
        });

        pathBot.addActionListener(e -> {
//            DataField.aiRunning = true;
//            DataField.velocityX = new ArrayList<>();
//            DataField.velocityY = new ArrayList<>();
//            vel = newtonSlave.getAllVelocities(DataField.x, DataField.y);
//
//            for (int i = 0; i<vel.get(0).size();i++) {
//                DataField.velocityX.add((Double) vel.get(0).get(i));
//                DataField.velocityY.add((Double) vel.get(1).get(i));
//            }
//
//            while(!DataField.velocityX.isEmpty()) {
//                DataField.GUI = false;
//            }
//
//            Timer t = new Timer(100, e1 -> {
//                DataField.GUI = true;
//            });

        });

        pushButton.addActionListener (e -> {
            //counter updater
            c=c+1;
            counter.setText("Strokes: " + c);
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

        frame = new JFrame("Control Panel");
        Image frameIcon = new ImageIcon("Icon.png").getImage();
        frame.setIconImage(frameIcon);
        panel = new JPanel();
        sideMenu = new JPanel();
        JMenuBar menuBar = new JMenuBar();

        //velocity visual elements
        velocityX = new JTextField();
        velocityY = new JTextField();
        velocityX.setColumns(15);
        velocityY.setColumns(15);
        inputVelocityX = new JLabel("Velocity X axis");
        inputVelocityY = new JLabel("Velocity Y axis");

        //origin coordinates visual elements
        ballXCoord = new JLabel("Ball X coord: "+ DataField.y);

        ballXCoord.setText("Ball X coord:" + DataField.x);

        ballYCoord = new JLabel("Ball X coord: "+ DataField.y);

        ballYCoord = new JLabel("Ball Y coord: ");

        counter = new JLabel("Strokes: " + c); //this returns the number of total strokes use

        // Elevation Depiction
        JLabel elevationDepictionLabel = new JLabel("\nElevation Depiction");
        JLabel highElevationLabel = new JLabel("10");
        JLabel lowElevationLabel = new JLabel("-10");

        //Adding the image to the control window
        ImageIcon gradientImage = new ImageIcon("gradient_chart.jpg");
        JLabel imgL = new JLabel(gradientImage);
        frame.add(imgL);

        //Adding menu bar items
        JMenu fileTab = new JMenu("File");
        JMenu extrasTab = new JMenu("Extras");
        menuBar.add(fileTab);
        menuBar.add(extrasTab);

        //"File" tab buttons
        JMenuItem exit = new JMenuItem("Exit");
        fileTab.add(exit);

        exit.addActionListener (e -> {
            if(e.getSource()==exit){
                System.exit(0);
            }
        });

        //create a music object
        MusicControls control = new MusicControls();
        control.setVisible(false);

        //"Extras" tab buttons
        JMenuItem music = new JMenuItem("Music");
        extrasTab.add(music);
            music.addActionListener(e -> {
                if(e.getSource()==music){
                    System.out.println("music button pressed");
                    control.setVisible(true);
                }
            });

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
        gc.anchor = GridBagConstraints.LINE_START;
        panel.add(ballXCoord, gc);

        //This adds a label for the input of the initial y-coordinate of the ball.
        gc.gridx = 1;
        gc.gridy = 0;
        gc.gridwidth = 1;
        panel.add(ballYCoord, gc);

        //This adds a label for inputting the x-velocity of the ball
        gc.gridx = 0;
        gc.gridy = 1;
        gc.fill=GridBagConstraints.CENTER;
        gc.gridwidth = 1;
        panel.add(inputVelocityX, gc);

        //This adds a label for inputting the y-velocity of the ball
        gc.gridx = 1;
        gc.gridy = 1;
        gc.fill=GridBagConstraints.CENTER;
        gc.gridwidth = 1;
        panel.add(inputVelocityY, gc);

        //This adds a text box for inputting the x-velocity of the ball
        gc.gridx = 0;
        gc.gridy = 2;
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridwidth = 1;
        panel.add(velocityX, gc);

        //This adds a text box for inputting the y-velocity of the ball
        gc.gridx = 1;
        gc.gridy = 2;
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridwidth = 1;
        panel.add(velocityY, gc);

        //This adds the "PUTT!" button to the menu
        gc.gridx = 0;
        gc.gridy = 3;
        gc.gridwidth = 1;
        panel.add(pushButton, gc);

        //This adds the counter which represents the number of strokes made until now
        gc.gridx = 1;
        gc.gridy = 3;
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridwidth = 1;
        panel.add(counter, gc);

        //This adds the label for Elevation Depiction
        gc.gridx = 0;
        gc.gridy = 5;
        gc.fill=GridBagConstraints.CENTER;
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridwidth = 2;
        panel.add(elevationDepictionLabel, gc);

        //This adds the label for the lower bound
        gc.gridx = 0;
        gc.gridy = 7;
        gc.gridwidth = 1;
        panel.add(lowElevationLabel, gc);

        //This adds the label for the upper bound
        gc.gridx = 1;
        gc.gridy = 7;
        gc.gridwidth = 1;
        panel.add(highElevationLabel, gc);

        //This adds the legend of the gradient in as a .jpg
        gc.gridx = 0;
        gc.gridy = 8;
        gc.gridwidth = 2;
        panel.add(imgL, gc);

        //This adds the bot buttons
        gc.gridx = 0;
        gc.gridy = 4;
        gc.gridwidth = 1;
        panel.add(pathBot, gc);

        gc.gridx = 1;
        gc.gridy = 4;
        gc.gridwidth = 1;
        panel.add(ruleBotButton, gc);

        //Swing frame settings
        frame.add(sideMenu);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(340,600);
        frame.setVisible(true);
    }

    /**
     *This method retrieves the ball's coordinates and updates them as the ball is moving
     */
    public void update(){
        ballXCoord.setText("Ball X coord: " + Math.round(DataField.x*100000.0)/100000.0);//this rounds the output
        ballYCoord.setText("Ball Y coord: " + Math.round(DataField.y*100000.0)/100000.0);//to 5 d.p
    }

}