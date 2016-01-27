package alexmog.elitebot.elite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogListener {
    private static final Pattern pattern = Pattern.compile(".*?(System).*?\\(((?:[^)]+)).*?\\)");
    private static int id = 0;
    private String mLastSystem = "";
    private Path mPath;
    private LogAction mAction;
    private WatchService mService;
    
    public LogListener(Path eliteFolder, LogAction action) {
        mPath = eliteFolder;
        mAction = action;
    }
    
    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                watchDirectory();
            }
        }, "LogThread-" + id).start();
    }
    
    private void watchDirectory() {
        // Sanity check - Check if path is a folder
        try {
            Boolean isFolder = (Boolean) Files.getAttribute(mPath,
                    "basic:isDirectory", LinkOption.NOFOLLOW_LINKS);
            if (!isFolder) {
                throw new IllegalArgumentException("Path: " + mPath + " is not a folder");
            }
        } catch (IOException ioe) {
            // Folder does not exists
            ioe.printStackTrace();
        }
        
        System.out.println("Watching path: " + mPath);
        
        // We obtain the file system of the Path
        FileSystem fs = mPath.getFileSystem ();
        
        // We create the new WatchService using the new try() block
        try(WatchService service = fs.newWatchService()) {
            mService = service;
            
            // We register the path to the service
            // We watch for creation events
            mPath.register(service, StandardWatchEventKinds.ENTRY_MODIFY);
            
            // Start the infinite polling loop
            WatchKey key = null;
            while(true) {
                key = service.take();
                
                // Dequeueing events
                Kind<?> kind = null;
                for(WatchEvent<?> watchEvent : key.pollEvents()) {
                    // Get the type of the event
                    kind = watchEvent.kind();
                    if (StandardWatchEventKinds.OVERFLOW == kind) {
                        continue; //loop
                    } else if (StandardWatchEventKinds.ENTRY_MODIFY == kind) {
                        Path changed = (Path) watchEvent.context();
                        String name = changed.toString();
                        System.out.println("A file has changed: " + name);
                        if (name.startsWith("netLog.") && name.endsWith(".log")) {
                            System.out.println("THIS IS A LOG FILE: " + name);
                            String lastSystem = getLastSystem(new File(mPath.toString() + "/" + changed.toString()));
                            if (lastSystem != null && !mLastSystem.equals(lastSystem)) {
                                mLastSystem = lastSystem;
                                System.out.println("Last system found: " + lastSystem);
                                mAction.onSystemChange(lastSystem);
                            } else System.out.println("Same system found.");
                        }
                    }
                }
                
                if(!key.reset()) {
                    break; //loop
                }
            }
            
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } catch(InterruptedException ie) {
            ie.printStackTrace();
        }
        
    }
    
    public void stop() throws IOException {
        mService.close();
    }

    public static String getLastSystem(File f) throws IOException {
        BufferedReader br = null;
        
        String lastSystem = null;
        try {
            br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    lastSystem = matcher.group(2);
                }
            }
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return lastSystem;
    }
}
