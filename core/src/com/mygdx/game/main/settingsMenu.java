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
        String spGetX1 = sandpitCoordsX1.getText();
        String spGetY1 = sandpitCoordsX1.getText();
        String spGetX2 = sandpitCoordsX1.getText();
        String spGetY2 = sandpitCoordsX1.getText();

        sandpitCoordText = new JLabel("Enter Coords:");

        originXText = new JLabel("Enter ball's X coordinate origin");
        originX = new JTextField("0");
        originYText = new JLabel("Enter ball's Y coordinate origin");
        originY = new JTextField("0");

        setButton.addActionListener(e->{

            if(chooseSolvers.getSelectedIndex()==0){
                JOptionPane.showMessageDialog(null, "Please select a solver.", "Invalid solver choice", JOptionPane.ERROR_MESSAGE);
            }
            else {

                double[] coordSP = new double[4];
                coordSP[0] = 30.0;
                coordSP[1] = 31.0;
                coordSP[2] = 30.0;
                coordSP[3] = 31.0;

                if (!spGetX1.equals("x1") && !spGetY1.equals("y1") && !spGetX2.equals("x2") && !spGetY2.equals("y2")) {

                    coordSP[0] = Double.parseDouble(sandpitCoordsX1.getText());
                    coordSP[1] = Double.parseDouble(sandpitCoordsY1.getText());
                    coordSP[2] = Double.parseDouble(sandpitCoordsX2.getText());
                    coordSP[3] = Double.parseDouble(sandpitCoordsY2.getText());

                    System.out.println(Arrays.toString(coordSP));
                    DataField.sandPit = coordSP.clone();
                }

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

        panelMain.add(chooseSolvers);

        panelMain.add(setButton);

        frameMain.add(panelMain);
        frameMain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameMain.setResizable(true);
        frameMain.setSize(450,150);
        frameMain.setVisible(true);
    }
}