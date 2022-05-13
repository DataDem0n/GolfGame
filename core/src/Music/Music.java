package Music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Music {
    public Clip clip;

    FloatControl volume;

    public void playMusic(String fileLocation){
        try{
            File musicPath = new File(fileLocation);

            if (musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);


            }
            else{
                System.out.println("Cannot find song file path");
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(1f); // volume needs to be initialised when converting the setVisible method inside MusicControls.Java
        //this is a semi random band-aid value which sounds close to its default max value
    }

    public void startMusic(){
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);

    }

    public void playWin(){
        if (clip.isRunning()) {
            clip.stop();
            clip.start();
        }
    }

    public void stopMusic(){
        clip.stop();
    }

}
