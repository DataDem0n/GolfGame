package Music;

import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MusicControls {
    int playCounter = 0;

    JFrame frame;

    JLabel selectGroove;
    //String[] grooveTypes = {"song 1", "song 2"};
    JComboBox<String> groove;
    //Declaring a credit label to the artist
    JLabel credit;

    //Swing music controls
    JButton play;
    JButton stop;
    JSlider volumeSlider;
    JLabel volumeInt;

    public MusicControls(){
        JPanel panel= new JPanel();
        frame= new JFrame("Music Controls");
        Image frameIcon = new ImageIcon("Icon.png").getImage();
        frame.setIconImage(frameIcon);

        panel.setLayout(new FlowLayout());
        play = new JButton("⏯");
        stop = new JButton("⏹");
        volumeSlider = new JSlider(1, 100);
        volumeSlider.setPaintTicks(true);
        volumeInt = new JLabel();

        credit = new JLabel("credit: Stefan \"vegamane666\" Gorgos");
        credit.setForeground(Color.gray);

        //create a music object
        Music play1 = new Music();

        //play button functionality
        play.addActionListener (e -> {
            if(e.getSource()==play){
                playCounter = playCounter +1;
                if(playCounter<=1) {
                    play1.playMusic(
                            "gungle.wav");
                    play1.startMusic();
                }
            }
        });

        //stop button functionality
        stop.addActionListener (e -> {
            if(e.getSource()==stop){

                play1.stopMusic();
                playCounter = 0;
            }
        });

        //Adding a change listener to the volume slider
        //The slider only updates once the slider knob is released
        volumeSlider.addChangeListener (e -> {
            if(e.getSource()==volumeSlider){

                play1.volume.setValue((20f * (float) Math.log10((float)volumeSlider.getValue()/100f)));

                volumeInt.setText("vol: "+ volumeSlider.getValue());
            }
        });

        //adding all the swing elements
        panel.add(play);
        panel.add(stop);
        panel.add(volumeSlider);
        panel.add(volumeInt);
        panel.add(credit);

        //Swing frame settings
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(300,200);
        frame.setVisible(true);
    }

    /**
     * @param musicVolume receives the current value from the slider
     * @return returns the new selected value from the slider
     */
    public float getVolume(float musicVolume){
        return volumeSlider.getValue();
    }

    /**
     * @param bool is a true/false value to either show or not the music control tab
     * @return returns the selected boolean value
     */
    public boolean setVisible(boolean bool){
        frame.setVisible(bool);
        return bool;
    }
}