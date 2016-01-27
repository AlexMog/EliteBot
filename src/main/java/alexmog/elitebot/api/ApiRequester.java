package alexmog.elitebot.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;

import alexmog.elitebot.Main;
import alexmog.elitebot.helpers.CookieHelper;

public class ApiRequester {
    // Used to emulate an iOS device and API URLs
    public static final String USER_AGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 7_1_2 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) Mobile/11D257";
    
    private BasicCookieStore mCookieStore = new BasicCookieStore();
    private CookieHelper mCookieHelper = new CookieHelper("./", Main.USERDATA_PATH + "cookies.txt");
    private HttpClient mClient = HttpClientBuilder.create()
            .setRedirectStrategy(new LaxRedirectStrategy())
            .setDefaultCookieStore(mCookieStore)
            .build();
    private HttpPost mPostRequest = new HttpPost();
    private HttpGet mGetRequest = new HttpGet();
    
    public ApiRequester() {
        addHeader("User-Agent", USER_AGENT);
        mCookieHelper.retrieveCookie(mCookieStore);
    }
    
    public void addHeader(String header, String value) {
        mPostRequest.addHeader(header, value);
        mGetRequest.addHeader(header, value);
    }
    
    private ApiResponse doRequest(String url, HttpRequestBase requester) throws ClientProtocolException, IOException, URISyntaxException {
        requester.setURI(new URI(url));
        try {
            HttpResponse response = mClient.execute(requester);
            int code = response.getStatusLine().getStatusCode();
            System.out.println("Code received for query to " + requester.getURI().toString() + " : " + code);

            StringBuffer pageContents = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            
            String line;
            while ((line = br.readLine()) != null) {
                pageContents.append(line);
            }
            
            try {br.close();} catch (Exception e) {}
//            System.out.println("Contents:");
//            System.out.println(pageContents.toString());
            
            mCookieHelper.clearFile();
            for (Cookie c : mCookieStore.getCookies()) {
                mCookieHelper.writeCookieTofile(c);
            }
            
            ApiResponse resp = new ApiResponse(code, pageContents.toString());
            return resp;
        } finally {
            requester.releaseConnection();
        }
    }
    
    public ApiResponse doGet(String url) throws ClientProtocolException, IOException, URISyntaxException {
        return doRequest(url, mGetRequest);
    }
    
    public ApiResponse doPost(String url, NameValuePair... params) throws ClientProtocolException, IOException, URISyntaxException {
        mPostRequest.setEntity(EntityBuilder.create().setParameters(params).build());
        return doRequest(url, mPostRequest);
    }

    public void clearCookies() {
        mCookieHelper.clearFile();
        mCookieStore.clear();
    }
}
