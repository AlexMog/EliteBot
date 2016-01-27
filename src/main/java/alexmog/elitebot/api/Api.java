package alexmog.elitebot.api;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;

import alexmog.elitebot.beans.Profile;

public class Api {
    private static final String API_URL = "https://companion.orerve.net",
            URL_LOGIN = "/user/login",
            URL_VERIFICATION = "/user/confirm",
            URL_PROFILE = "/profile",
            URL_EDDN = "http://eddn-gateway.elite-markets.net:8080/upload/";
    private ApiRequester mRequester = new ApiRequester();
    private Gson mGson = new Gson();
    private String mUsername, mPassword;
    
    public enum Status {
        Ok,
        Ko,
        NeedVerification
    }
    
    public Status authenticate(String username, String password) throws ClientProtocolException, IOException, URISyntaxException {
        ApiResponse resp = mRequester.doPost(API_URL + URL_LOGIN,
                new BasicNameValuePair("email", username),
                new BasicNameValuePair("password", password));
        if (resp.getContent().contains("verification code")) return Status.NeedVerification;
        if (resp.getContent().contains("Password")) return Status.Ko;
        mUsername = username;
        mPassword = password;
        return Status.Ok;
    }
    
    public Status submitVerification(String verificationCode) throws ClientProtocolException, IOException, URISyntaxException {
        mRequester.doPost(API_URL + URL_VERIFICATION,
                new BasicNameValuePair("code", verificationCode));
        return authenticate(mUsername, mPassword);
    }
    
    public Profile getProfile() throws ClientProtocolException, IOException, URISyntaxException {
        ApiResponse resp = mRequester.doGet(API_URL + URL_PROFILE);
        return mGson.fromJson(resp.getContent(), Profile.class);
    }
    
    public void clearCookies() {
        mRequester.clearCookies();
    }
}
