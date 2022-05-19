package com.mygdx.game.main;

import obstacles.Forest;
import obstacles.Tree;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

public class settingsMenu{
    static boolean finished = true;

    //ball stuff ;))
    JTextField originX ;
    JLabel originXText;
    JTextField originY;
    JLabel originYText;

    //tree amount
    String[] option = {"0", "1", "2", "3", "4", "5"};
    JComboBox<String> amountOfTree = new JComboBox<>(option);
    JLabel treeAmount;

    //Sandpit
    JTextField sandpitCoordsX1;
    JTextField sandpitCoordsY1;
    JTextField sandpitCoordsX2;
    JTextField sandpitCoordsY2;
    JLabel sandpitCoordText;

    //solvers drop down
    String[] solvers = {"Choose solver", "Euler's Method", "Runge-Kutta 2", "Runge-Kutta 4"};
    JComboBox<String> chooseSolvers = new JComboBox<>(solvers);

    //frictions
    JLabel sfricLabel;
    JTextField sfric;
    JLabel kfricLabel;
    JTextField kfric;

    //function
    JLabel funcField;
    JTextField function;

    //Hole
    JLabel holeXLabel;
    JTextField holeX;
    JLabel holeYLabel;
    JTextField holeY;
    JLabel holeRLabel;
    JTextField holeR;

    //preset buttons
    JButton test1;
    JButton test2;
    JButton test3;

    //set button
    JButton setButton = new JButton("Set!");

    settingsMenu(){
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
        test3 = new JButton("Preset 3 (sandpit)");

        test1.addActionListener(e ->{
            DataField.x = -3;
            DataField.y = 0;
            DataField.targetRXY = new double[]{0.15,4,1};
            DataField.gameForest = new Forest(0);
            DataField.gameForest.getForest().add(new Tree(15,15));
            DataField.kFriction = 0.08;
            DataField.sFriction = 0.2;
            DataField.terrain = (x,y)->(1/10.0)*(Math.sin(x+y)+1);
            chooseSolvers.setSelectedIndex(2);
            settingsMenu.finished = false;
            DataField.sandPit = new double[]{40,40,40,40};

            frameMain.setVisible(false);
        });


        test2.addActionListener(e ->{
            DataField.x = -3;
            DataField.y = 0;
            DataField.targetRXY = new double[]{0.5,4,1};
            DataField.gameForest = new Forest(0);
            DataField.kFriction = 0.08;
            DataField.sFriction = 0.3;//TODO: fix this with saman
            DataField.terrain = (x,y)->0.4*(0.9-Math.exp(-(Math.pow(x,2)+Math.pow(y,2))/8));
            chooseSolvers.setSelectedIndex(2);
            DataField.sandPit = new double[]{40,40,40,40};
            settingsMenu.finished = false;

            frameMain.setVisible(false);//{5,5,10,10}
        });

        test3.addActionListener(e ->{
            DataField.x = 0;
            DataField.y = 0;
            DataField.targetRXY = new double[]{0.5,18,18};
            DataField.gameForest = new Forest(0);
            DataField.kFriction = 0.1;
            DataField.sFriction = 0.2;
            DataField.terrain = (x,y)->1.0;
            DataField.sandPit = new double[]{5,5,10,10};
            chooseSolvers.setSelectedIndex(2);
            settingsMenu.finished = false;
            frameMain.setVisible(false);
        });



        setButton.addActionListener(y->{

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

                DataField.gameForest = new Forest(amountOfTree.getSelectedIndex());

                //if no sandpit is desired, it just gets placed outside the view of the player considering the x and y constraints of [-25, 25]
                DataField.sandPit = coordSP.clone();
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

        panelMain.add(setButton);
        panelMain.setLayout(new FlowLayout());

        //adding tree related components
        panelMain.add(treeAmount);
        panelMain.add(amountOfTree);

        //adding sandpit related components
        panelMain.add(sandpitCoordText);
        panelMain.add(sandpitCoordsX1);
        panelMain.add(sandpitCoordsY1);
        panelMain.add(sandpitCoordsX2);
        panelMain.add(sandpitCoordsY2);

        //adding ball related components
        panelMain.add(originXText);
        panelMain.add(originX);
        panelMain.add(originYText);
        panelMain.add(originY);

        //add friction and function elements
        panelMain.add(sfricLabel);
        panelMain.add(sfric);
        panelMain.add(kfricLabel);
        panelMain.add(kfric);

//        panelMain.add(funcField);
//        panelMain.add(function);
//        function.setColumns(16);

        panelMain.add(chooseSolvers);

        //hole coords
        panelMain.add(holeXLabel);
        panelMain.add(holeX);

        panelMain.add(holeYLabel);
        panelMain.add(holeY);

        panelMain.add(holeRLabel);
        panelMain.add(holeR);

        //preset buttons contain predetermined values
        panelMain.add(test1);
        panelMain.add(test2);
        panelMain.add(test3);


        //adds the set button. Confirms all the user inputted values
        panelMain.add(setButton);

        frameMain.add(panelMain);
        frameMain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameMain.setResizable(true);
        frameMain.setSize(480,160);
        frameMain.setVisible(true);
    }

}