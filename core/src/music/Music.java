package music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class Music {

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
        volume.setValue(1f);
    }

    /**
     * this method starts the music and loops it upon pressing the button
     */
    public void startMusic(){
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * this method stops the music upon pressing the stop button
     */
    public void stopMusic(){
        clip.stop();
    }

}
