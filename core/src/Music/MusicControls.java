package Music;

import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MusicControls {
    int playCounter = 0;

    JFrame frame;

    JLabel selectGroove;
    JLabel credit;
    //String[] grooveTypes = {"chill", "not chill"};
    JComboBox<String> groove;
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

        volumeSlider.addChangeListener (e -> {
            if(e.getSource()==volumeSlider){

                play1.volume.setValue((20f * (float) Math.log10((float)volumeSlider.getValue()/100f)));

                volumeInt.setText("vol: "+ volumeSlider.getValue());

            }
        });

        panel.add(play);
        panel.add(stop);
        panel.add(volumeSlider);
        panel.add(volumeInt);
        panel.add(credit);

        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(300,200);
        frame.setVisible(true);
    }
    public float getVolume(float musicVolume){
        return volumeSlider.getValue();
    }

    public boolean setVisible(boolean bool){
        frame.setVisible(bool);
        return bool;
    }
}