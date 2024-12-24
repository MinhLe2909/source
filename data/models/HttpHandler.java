package tamhoang.ldpro4.data.models;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpHandler {
    private static final String TAG = "HttpHandler";

    private String convertStreamToString(InputStream is) {
        String readLine;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                readLine = bufferedReader.readLine();
                if (readLine != null) {
                    sb.append(readLine);
                    sb.append('\n');
                    is.close();
                } else {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public String makeServiceCall(String reqUrl) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(reqUrl).openConnection();
            httpURLConnection.setRequestMethod("GET");
            return convertStreamToString(new BufferedInputStream(httpURLConnection.getInputStream()));
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
            return null;
        } catch (ProtocolException e2) {
            Log.e(TAG, "ProtocolException: " + e2.getMessage());
            return null;
        } catch (IOException e3) {
            Log.e(TAG, "IOException: " + e3.getMessage());
            return null;
        } catch (Exception e4) {
            Log.e(TAG, "Exception: " + e4.getMessage());
            return null;
        }
    }
}
