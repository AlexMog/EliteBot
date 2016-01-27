package alexmog.elitebot;

import java.io.IOException;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

public class SpeechWorker implements Runnable {
    @Override
    public void run() {
        Configuration configuration = new Configuration();
        
         // Set path to acoustic model.
         configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
         // Set path to dictionary.
         configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
         // Set language model.
         configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
         
         Main.talk("Initialisating speech recognition management...");
         
         LiveSpeechRecognizer recognizer;
        try {
            recognizer = new LiveSpeechRecognizer(configuration);
            // Start recognition process pruning previously cached data.
            recognizer.startRecognition(true);
            Main.talk("Initialisation finished. I am now listening to your orders.");
            while (true) {
                String order = recognizer.getResult().getHypothesis();
                System.out.println(order);
                if (order.equals("hello")) {
                    Main.talk("Hello commander");
                }
            }        
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
