package Music;

import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MusicControls {
    int playCounter = 0;

    JLabel selectGroove;
    JLabel credit;
    String[] grooveTypes = {"chill", "not chill"};
    JComboBox<String> groove;
    JButton play;
    JButton stop;
    JSlider volumeSlider;
    JLabel volumeInt;

    public MusicControls(){
        JPanel panel= new JPanel();
        JFrame frame= new JFrame("Music Controls");
        Image frameIcon = new ImageIcon("Icon.png").getImage();
        frame.setIconImage(frameIcon);

        panel.setLayout(new FlowLayout());
        play = new JButton("wooh");
        stop = new JButton("ay stop da noise man");
        volumeSlider = new JSlider(0, 100, 50);
        volumeSlider.setPaintTicks(true);
        volumeInt = new JLabel();

        credit = new JLabel("copi rite stefan dev 2022 :DDD");

        //create a music object
        Music play1 = new Music();

        //play button functionality
        play.addActionListener (e -> {
            if(e.getSource()==play){
                playCounter = playCounter +1;
                if(playCounter<=1) {
                    play1.playMusic("A:/fl studio stuff/fl projects/dnb trash/2022/golf22/H.wav");
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
}