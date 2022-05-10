package com.mygdx.game.main;

import obstacles.Forest;

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
    String[] solvers = {"Choose solver", "Euler's Method", "Runge-Kutta 2", "Runge-Kutta 4", "Adams-Moulton"};
    JComboBox<String> chooseSolvers = new JComboBox<>(solvers);

    //frictions
    JLabel sfricLabel;
    JTextField sfric;
    JLabel kfricLabel;
    JTextField kfric;

    //function
    JLabel funcField;
    JTextField function;

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

        setButton.addActionListener(e->{

            if(chooseSolvers.getSelectedIndex()==0){
                JOptionPane.showMessageDialog(null, "Please select a solver.", "Invalid solver choice", JOptionPane.ERROR_MESSAGE);
            }
            else if(Double.parseDouble(sfric.getText())<=0 || Double.parseDouble(kfric.getText()) <= 0){
                JOptionPane.showMessageDialog(null, "Please select proper friction values.", "Invalid friction values", JOptionPane.ERROR_MESSAGE);
            }
            else {

                double[] coordSP = new double[]{40.0, 40.0, 40.0, 40.0};

                if (!sandpitCoordsX1.getText().equals("x1") && !sandpitCoordsY1.getText().equals("y1") && !sandpitCoordsX2.getText().equals("x2") && !sandpitCoordsY2.getText().equals("y2")) {

                    coordSP[0] = Double.parseDouble(sandpitCoordsX1.getText());
                    coordSP[1] = Double.parseDouble(sandpitCoordsY1.getText());
                    coordSP[2] = Double.parseDouble(sandpitCoordsX2.getText());
                    coordSP[3] = Double.parseDouble(sandpitCoordsY2.getText());

                    System.out.println(Arrays.toString(coordSP));
                    DataField.sandPit = coordSP.clone();
                }

                DataField.kFriction = Double.parseDouble(kfric.getText());
                DataField.sFriction = Double.parseDouble(sfric.getText());

                DataField.x = Double.parseDouble(originX.getText());
                DataField.y = Double.parseDouble(originY.getText());

                DataField.gameForest = new Forest(amountOfTree.getSelectedIndex());

                //if no sandpit is desired, it just gets placed outside the view of the player considering the x and y constraints of [-25, 25]
                DataField.sandPit = coordSP.clone();
                System.out.println(Arrays.toString(DataField.sandPit));
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

        panelMain.add(funcField);
        panelMain.add(function);
        function.setColumns(16);

        panelMain.add(chooseSolvers);

        panelMain.add(setButton);

        frameMain.add(panelMain);
        frameMain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameMain.setResizable(true);
        frameMain.setSize(480,160);
        frameMain.setVisible(true);
    }
}