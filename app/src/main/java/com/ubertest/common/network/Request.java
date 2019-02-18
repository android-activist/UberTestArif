package com.ubertest.common.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public abstract class Request<T> {
    private static final int BUFFER_SIZE = 4096;
    private static final int DEFAULT_TIMEOUT = 3000;
    private static final String DEFAULT_CHARSET = "UTF-8";

    private int timeOut = DEFAULT_TIMEOUT;
    private String method = Method.GET.getType();
    private String charsetName = DEFAULT_CHARSET;
    private String url;

    public Request(String url, ResponseListener<T> listener) {
        this.url = url;
    }

    synchronized T request() {
        String responseStr;
        try {
            responseStr = download(new URL(url));
        } catch (IOException e) {
            final NetworkError networkError = parseError(e);
            onError(networkError);

            return null;
        } catch (Throwable e) {
            final NetworkError networkError = parseError(e);
            onError(networkError);

            return null;
        }

        if (responseStr == null || responseStr.length() == 0) {
            final Throwable throwable = new Throwable("Empty response");
            final NetworkError networkError = new NetworkError(throwable);

            onError(networkError);
        }

        return parseNetworkResponse(responseStr);
    }

    private String download(URL url) throws IOException {
        InputStream stream = null;
        HttpsURLConnection connection = null;
        String result = null;
        try {
            connection = (HttpsURLConnection) url.openConnection();
            connection.setReadTimeout(timeOut);
            connection.setConnectTimeout(timeOut);
            connection.setRequestMethod(method);
            connection.setDoInput(true);
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }

            stream = connection.getInputStream();

            if (stream != null) {
                result = readStream(stream);
            }
        } finally {
            // Close Stream and disconnect HTTPS connection.
            if (stream != null) {
                stream.close();
            }

            if (connection != null) {
                connection.disconnect();
            }
        }

        return result;
    }

    private String readStream(InputStream stream) throws IOException {
        Reader reader = new InputStreamReader(stream, charsetName);
        char[] rawBuffer = new char[BUFFER_SIZE];

        StringBuffer buffer = new StringBuffer();
        while ((reader.read(rawBuffer)) != -1) {
            buffer.append(rawBuffer);
        }

        return buffer.toString();
    }

    protected abstract T parseNetworkResponse(String response);

    protected abstract void onError(NetworkError error);

    protected NetworkError parseError(Throwable response) {
        NetworkError error = new NetworkError(response);
        return error;
    }
}
