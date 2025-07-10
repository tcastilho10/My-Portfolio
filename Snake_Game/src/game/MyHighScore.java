package game;

import java.io.*;

public class MyHighScore {

    private final String saveFilePath = "saveFiles/HighScore.dat";


    public void saveHighScore(int score) throws IOException {

        try(FileOutputStream out = new FileOutputStream(saveFilePath);
            DataOutputStream dataOut = new DataOutputStream(out)){

            dataOut.writeInt(score);

        }catch (IOException e){

            System.out.println("Error setting the high score: " + e.getMessage());
        }
    }

    public int loadHighScore() {
        File file = new File(saveFilePath);

        if(!file.exists()){
            try{
                file.createNewFile();
                saveHighScore(0);
                System.out.println("High score file created");
                return 0;

            } catch (IOException e) {
                System.out.println("Error creating the high score file: " + e.getMessage());
                return -1;
            }
        }


        try (FileInputStream in = new FileInputStream(file);
             DataInputStream inData = new DataInputStream(in)) {

            return inData.readInt();

        } catch (IOException e) {
            System.out.println("Error loading high score: " + e.getMessage());
            return -1;  // Return -1 if the file doesn't exist or has an error
        }

    }

}
