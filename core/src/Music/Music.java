package Music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Music {
    //adding and initialising clip and volume variable
    public Clip clip;
    FloatControl volume;

    /**
    *@param fileLocation points to the music source inside the project root folder. The music file has to be in .wav format to work.
     */
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

    /**
     * this method starts the music and loops it upon pressing the button
     */
    public void startMusic(){
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

//will be used in phase 3
//  public void playWin(){
//        if (clip.isRunning()) {
//            clip.stop();
//            clip.start();
//        }
//    }

    /**
     * this method stops the music upon pressing the stop button
     */
    public void stopMusic(){
        clip.stop();
    }

}
