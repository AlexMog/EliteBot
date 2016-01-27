package alexmog.elitebot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.ivona.services.tts.IvonaSpeechCloudClient;
import com.ivona.services.tts.model.CreateSpeechRequest;
import com.ivona.services.tts.model.CreateSpeechResult;
import com.ivona.services.tts.model.Input;
import com.ivona.services.tts.model.Voice;

import alexmog.elitebot.api.Api;
import alexmog.elitebot.api.Api.Status;
import alexmog.elitebot.datas.ConfigurationManager;
import alexmog.elitebot.frames.LoginFrame;
import alexmog.elitebot.frames.ProfileFrame;
import alexmog.elitebot.frames.WelcomeFrame;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class Main {
    public static final LoginFrame loginFrame = new LoginFrame();
    public static final Api API = new Api();
    private static IvonaSpeechCloudClient speechCloud;
    public static final String USERDATA_PATH = System.getProperty("user.home") + File.separator + "elitebot" + File.separator;
    
    public static void main(String[] args) throws Exception {
        File f = new File(USERDATA_PATH);
        if (!f.exists()) {
            f.mkdirs();
        }
        
        ConfigurationManager.getInstance().initializeConfig();
        
        /*speechCloud = new IvonaSpeechCloudClient(new PropertiesFileCredentialsProvider("./credentials.properties"));
        speechCloud.setEndpoint("https://tts.eu-west-1.ivonacloud.com");
        f = new File("cookies.txt");
        new Thread(new SpeechWorker(), "Speech worker thread").start();*/
        if (f.exists()) {
            Status status = Main.API.authenticate("", "");
            if (status == Status.Ok) {
                try {
                    new ProfileFrame(API.getProfile()).setVisible(true);;
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                API.clearCookies();
            }
        }
        WelcomeFrame welcomeFrame = new WelcomeFrame();
        welcomeFrame.setVisible(true);
    }
    
    public static void talk(final String text) {
        try {
            CreateSpeechRequest createSpeechRequest = new CreateSpeechRequest();
            Input input = new Input();
            Voice voice = new Voice();
    
            voice.setName("Brian");
            input.setData(text);
    
            createSpeechRequest.setInput(input);
            createSpeechRequest.setVoice(voice);
    
            InputStream in = null;
            FileOutputStream outputStream = null;
    
            try {
    
                CreateSpeechResult createSpeechResult = speechCloud.createSpeech(createSpeechRequest);
    
    /*            System.out.println("\nSuccess sending request:");
                System.out.println(" content type:\t" + createSpeechResult.getContentType());
                System.out.println(" request id:\t" + createSpeechResult.getTtsRequestId());
                System.out.println(" request chars:\t" + createSpeechResult.getTtsRequestCharacters());
                System.out.println(" request units:\t" + createSpeechResult.getTtsRequestUnits());
    
                System.out.println("\nStarting to retrieve audio stream:");*/
    
                in = createSpeechResult.getBody();
                outputStream = new FileOutputStream(new File("tmp.mp3"));
    
                byte[] buffer = new byte[2 * 1024];
                int readBytes;
    
                while ((readBytes = in.read(buffer)) > 0) {
                    // In the example we are only printing the bytes counter,
                    // In real-life scenario we would operate on the buffer0
    //                System.out.println(" received bytes: " + readBytes);
                    outputStream.write(buffer, 0, readBytes);
                }
    
                AdvancedPlayer player = new AdvancedPlayer(new FileInputStream("tmp.mp3"));
                player.play();
                player.close();
                
    //            System.out.println("\nFile saved.");
    
            } finally {
                if (in != null) {
                    in.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
