package com.mygdx.game.main;

import obstacles.Forest;
import obstacles.Tree;

import javax.swing.*;
import java.awt.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class settingsMenu{
    static boolean finished = true;

    //Ball swing text fields and labels
    JTextField originX ;
    JLabel originXText;
    JTextField originY;
    JLabel originYText;

    //Tree amount dropdown menu
    String[] option = {"0", "1", "2", "3", "4", "5"};
    JComboBox<String> amountOfTree = new JComboBox<>(option);
    JLabel treeAmount;

    //Sandpit swing text fields and labels
    JTextField sandpitCoordsX1;
    JTextField sandpitCoordsY1;
    JTextField sandpitCoordsX2;
    JTextField sandpitCoordsY2;
    JLabel sandpitCoordText;

    //Solvers drop down menu
    String[] solvers = {"Choose solver", "Euler's Method", "Runge-Kutta 2", "Runge-Kutta 4"};
    JComboBox<String> chooseSolvers = new JComboBox<>(solvers);

    //Friction swing text fields and labels
    JLabel sfricLabel;
    JTextField sfric;
    JLabel kfricLabel;
    JTextField kfric;

    //Function swing text fields and labels
    JLabel funcField;
    JTextField function;

    //Hole position and radius swing text fields and labels
    JLabel holeXLabel;
    JTextField holeX;
    JLabel holeYLabel;
    JTextField holeY;
    JLabel holeRLabel;
    JTextField holeR;

    //Preset buttons
    JButton test1;
    JButton test2;

    //Set button
    JButton setButton = new JButton("Set!");

    settingsMenu(){
        amountOfTree.setSelectedIndex(1);
        JPanel panelMain= new JPanel();
        JFrame frameMain= new JFrame("Pre-game settings");
        Image frameIcon = new ImageIcon("Icon.png").getImage();
        frameMain.setIconImage(frameIcon);

        treeAmount = new JLabel("Amount of trees:");

        sandpitCoordsX1 = new JTextField("x1");
        sandpitCoordsY1 = new JTextField("y1");
        sandpitCoordsX2 = new JTextField("x2");
        sandpitCoordsY2 = new JTextField("y2");

        sandpitCoordText = new JLabel("Enter Coords:");

        originXText = new JLabel("Enter ball's X coordinate origin");
        originX = new JTextField("0");
        originYText = new JLabel("Enter ball's Y coordinate origin");
        originY = new JTextField("0");
        originX.setColumns(3);
        originY.setColumns(3);

        sfricLabel = new JLabel("Static Friction:");
        sfric = new JTextField("0.05");

        kfricLabel = new JLabel("Kinetic Friction:");
        kfric = new JTextField("0.01");

        sfric.setColumns(5);
        kfric.setColumns(5);

        funcField = new JLabel("Enter Function:");
        function = new JTextField();

        holeXLabel = new JLabel("Hole X:");
        holeX = new JTextField("X");
        holeX.setColumns(5);

        holeYLabel = new JLabel("Hole Y:");
        holeY = new JTextField("Y");
        holeY.setColumns(5);

        holeRLabel = new JLabel("Hole radius:");
        holeR = new JTextField("Radius");
        holeR.setColumns(5);

        test1 = new JButton("Preset 1 (Tree)");
        test2 = new JButton("Preset 2 (Lake)");

        //initialising preset 1
        test1.addActionListener(e ->{
            DataField.x = 0;
            DataField.y = 0;
            DataField.targetRXY = new double[]{0.15,20,20};
            DataField.gameForest = new Forest(0);
            DataField.gameForest.getForest().add(new Tree(15,15));
            DataField.kFriction = 0.2;
            DataField.sFriction = 0.3;
            DataField.terrain = (x,y)->(1/10.0)*(Math.sin(x+y)+1);
            chooseSolvers.setSelectedIndex(2);
            settingsMenu.finished = false;
            DataField.sandPit = new double[]{40,40,40,40};

            frameMain.setVisible(false);
        });

        //initialising preset 2
        test2.addActionListener(e ->{
            DataField.x = -3;
            DataField.y = 0;
            DataField.targetRXY = new double[]{0.5,4,1};
            DataField.gameForest = new Forest(0);
            DataField.gameForest.getForest().add(new Tree(100,100));
            DataField.kFriction = 0.2;
            DataField.sFriction = 0.3;//TODO: fix this with saman
            DataField.terrain = (x,y)->1.0;//0.4*(0.9-Math.exp(-(Math.pow(x,2)+Math.pow(y,2))/8))
            chooseSolvers.setSelectedIndex(3);
            DataField.sandPit = new double[]{40,40,40,40};
            settingsMenu.finished = false;

            frameMain.setVisible(false);
        });

        /**
        implementing "Set" button functionality - This confirms all the user-inserted values and parses them on to DataField.java
         */
        setButton.addActionListener(y->{
            //adding error messages for missed elements
            if(chooseSolvers.getSelectedIndex()==0){
                JOptionPane.showMessageDialog(null, "Please select a solver.", "Invalid solver choice", JOptionPane.ERROR_MESSAGE);
            }
            else if(Double.parseDouble(sfric.getText())<=0 || Double.parseDouble(kfric.getText()) <= 0){
                JOptionPane.showMessageDialog(null, "Please select proper friction values.", "Invalid friction values", JOptionPane.ERROR_MESSAGE);
            }
            else if(holeY.getText().equals("Y") ||holeX.getText().equals("X")||holeR.getText().equals("Radius")&&Double.parseDouble(holeX.getText())<-25.0 || Double.parseDouble(holeX.getText())>25.0 ||Double.parseDouble(holeY.getText())<-25.0 || Double.parseDouble(holeY.getText())>25.0 ){
                JOptionPane.showMessageDialog(null, "Please select correct Hole coordinates", "Invalid Hole Coords", JOptionPane.ERROR_MESSAGE);
            }
            else {
                DataField.targetRXY= new double[]{Double.parseDouble(holeR.getText()),Double.parseDouble(holeX.getText()),Double.parseDouble(holeY.getText())};

                double[] coordSP = new double[]{40.0, 40.0, 40.0, 40.0};

                if (!sandpitCoordsX1.getText().equals("x1") && !sandpitCoordsY1.getText().equals("y1") && !sandpitCoordsX2.getText().equals("x2") && !sandpitCoordsY2.getText().equals("y2")) {

                    coordSP[0] = Double.parseDouble(sandpitCoordsX1.getText());
                    coordSP[1] = Double.parseDouble(sandpitCoordsY1.getText());
                    coordSP[2] = Double.parseDouble(sandpitCoordsX2.getText());
                    coordSP[3] = Double.parseDouble(sandpitCoordsY2.getText());

                    DataField.sandPit = coordSP.clone();
                }

                DataField.kFriction = Double.parseDouble(kfric.getText());
                DataField.sFriction = Double.parseDouble(sfric.getText());

                DataField.x = Double.parseDouble(originX.getText());
                DataField.y = Double.parseDouble(originY.getText());

                DataField.gameForest = new Forest(0);
                DataField.gameForest.getForest().add(new Tree(100,100));
                //if no sandpit is desired, it just gets placed outside the view of the player considering the x and y constraints of [-25, 25]
                DataField.sandPit = coordSP.clone();
                DataField.kFriction = 0.2;
                DataField.sFriction = 0.3;
                settingsMenu.finished = false;
                frameMain.setVisible(false);
            }
        });

        /**
         This code is adapted from this stack overflow answer https://stackoverflow.com/a/1178720
         this is only for user convenience to quickly tab between the 4 JTextFields
         */
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener("permanentFocusOwner", new PropertyChangeListener() {
            public void propertyChange(final PropertyChangeEvent e){
                if (e.getNewValue() instanceof JTextField){
                    SwingUtilities.invokeLater(new Runnable(){
                        public void run(){
                            JTextField textField = (JTextField)e.getNewValue();
                            textField.selectAll();
                        }
                    });
                }
            }
        });
        /**
         end of referenced code
         */

        panelMain.setLayout(new FlowLayout());

        //Adding tree related components - phase 3
//        panelMain.add(treeAmount);
//        panelMain.add(amountOfTree);

        //Adding sandpit related components
        panelMain.add(sandpitCoordText);
        panelMain.add(sandpitCoordsX1);
        panelMain.add(sandpitCoordsY1);
        panelMain.add(sandpitCoordsX2);
        panelMain.add(sandpitCoordsY2);

        //Adding ball related components
        panelMain.add(originXText);
        panelMain.add(originX);
        panelMain.add(originYText);
        panelMain.add(originY);

        //Add friction and function elements
        panelMain.add(sfricLabel);
        panelMain.add(sfric);
        panelMain.add(kfricLabel);
        panelMain.add(kfric);

        //Adding the solver drop down
        panelMain.add(chooseSolvers);

        //Hole coordinate elements
        panelMain.add(holeXLabel);
        panelMain.add(holeX);

        panelMain.add(holeYLabel);
        panelMain.add(holeY);

        panelMain.add(holeRLabel);
        panelMain.add(holeR);

        //Preset buttons contain predetermined values
        panelMain.add(test1);
        panelMain.add(test2);

        //Adding the set button. Confirms all the user inputted values
        panelMain.add(setButton);

        //Swing frame options
        frameMain.add(panelMain);
        frameMain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameMain.setResizable(true);
        frameMain.setSize(540,180);
        frameMain.setVisible(true);
    }

}