package alexmog.elitebot.elite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogListenersManager {
    private static final LogListenersManager instance = new LogListenersManager();
    private List<LogListener> mListeners = new ArrayList<>();
    private boolean mStopped = false;
    
    private LogListenersManager() {}
    
    public static LogListenersManager getInstance() {
        return instance;
    }
    
    public void clearListeners() {
        if (!mStopped) stopListeners();
        mListeners.clear();
    }
    
    public void addListener(LogListener listener) {
        mListeners.add(listener);
    }
    
    public void startListeners() {
        mStopped = false;
        for (LogListener l : mListeners) {
            l.start();
        }
    }
    
    public void stopListeners() {
        for (LogListener l : mListeners) {
            try {
                l.stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mStopped = true;
    }
}
