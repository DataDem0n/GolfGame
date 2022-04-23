package com.mygdx.game.main;

import com.badlogic.gdx.Gdx;
import obstacles.SandPits;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


    //set button
    JButton setButton = new JButton("Set");

    settingsMenu(){
        JPanel panelMain= new JPanel();
        JFrame frameMain= new JFrame("Pre-game settings");

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

        setButton.addActionListener(e->{
            DataField.x = Float.parseFloat(originX.getText());
            DataField.y = Float.parseFloat(originY.getText());
            double[] coordSP = new double[4];
            DataField.amountOfTrees = amountOfTree.getSelectedIndex();

            coordSP[0] = Double.parseDouble(sandpitCoordsX1.getText());
            coordSP[1] = Double.parseDouble(sandpitCoordsY1.getText());
            coordSP[2] = Double.parseDouble(sandpitCoordsX2.getText());
            coordSP[3] = Double.parseDouble(sandpitCoordsY2.getText());
            System.out.println(Arrays.toString(coordSP));
            DataField.sandPit = coordSP.clone();
            settingsMenu.finished = false;
            frameMain.setVisible(false);
        });

        /*
        This code is adapted from this stack overflow answer https://stackoverflow.com/a/1178720
        this is only for user convenience to quickly tab between the 4 JTextFields
         */
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addPropertyChangeListener("permanentFocusOwner", new PropertyChangeListener()
                {
                    public void propertyChange(final PropertyChangeEvent e)
                    {
                        if (e.getNewValue() instanceof JTextField)
                        {
                            SwingUtilities.invokeLater(new Runnable()
                            {
                                public void run()
                                {
                                    JTextField textField = (JTextField)e.getNewValue();
                                    textField.selectAll();
                                }
                            });
                        }
                    }
                });
        /*
        end of referenced code
         */

        panelMain.add(setButton);
//test
        panelMain.setLayout(new FlowLayout());
        //panelMain.setLayout(new GridLayout());
        panelMain.add(treeAmount);
        panelMain.add(amountOfTree);
        panelMain.add(sandpitCoordText);
        panelMain.add(sandpitCoordsX1);
        panelMain.add(sandpitCoordsY1);
        panelMain.add(sandpitCoordsX2);
        panelMain.add(sandpitCoordsY2);

        panelMain.add(originXText);
        panelMain.add(originX);
        panelMain.add(originYText);
        panelMain.add(originY);

        panelMain.add(setButton);

        frameMain.add(panelMain);
        frameMain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameMain.setResizable(true);
        frameMain.setSize(350,200);
        frameMain.setVisible(true);
        }
}
