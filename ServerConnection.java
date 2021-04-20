package pl.rafal;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class ServerConnection {
    URL url;
    HttpURLConnection connection;
    OutputStream outputStream;
    InputStream inputStream;


    int liczbaGraczyOnline() {
        try {
            this.url = new URL("http://192.168.1.122:8080/onlineplayers");
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            JSONObject jsonObject = new JSONObject(in.readLine());
            int wynik = jsonObject.getInt("liczbaGraczy");
            return wynik;
        } catch (IOException e) {
            System.out.println("players online connection error");
            e.printStackTrace();
        }
        return 0;
    }

    JSONObject graczeOnline(int id) throws IOException {
        this.url = new URL("http://192.168.1.122:8080/downloaddata?id="+id+"");

        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        JSONObject jsonObject = new JSONObject(in.readLine());

        return jsonObject;
    }

    void wyslijDaneGracza(Gracz gracz) throws IOException {
        this.url = new URL("http://192.168.1.122:8080/tester");
        this.connection = (HttpURLConnection) url.openConnection();
        this.connection.setDoOutput(true);
        this.connection.setRequestMethod("PUT");
        this.connection.setRequestProperty("Content-Type", "application/json");
        this.outputStream = this.connection.getOutputStream();
        JSONObject send = new JSONObject(gracz);
        this.outputStream.write(send.toString().getBytes());
        this.connection.getResponseCode();
        this.outputStream.close();
    }

    void wyslijWiadomosc(Gracz gracz) throws IOException {
        this.url = new URL("http://192.168.1.122:8080/sendMessage");
        this.connection = (HttpURLConnection) url.openConnection();
        this.connection.setDoOutput(true);
        this.connection.setRequestMethod("PUT");
        this.connection.setRequestProperty("Content-Type", "application/json");
        this.outputStream = this.connection.getOutputStream();
        JSONObject jsonObject = new JSONObject(gracz);
        this.outputStream.write(jsonObject.toString().getBytes());
        this.connection.getResponseCode();
        this.outputStream.close();
    }

    void ustawStatusFalse(Logowanie logowanie) throws IOException {
        this.url = new URL("http://192.168.1.122:8080/statusfalse");
        this.connection = (HttpURLConnection) url.openConnection();
        this.connection.setDoOutput(true);
        this.connection.setRequestMethod("PUT");
        this.connection.setRequestProperty("Content-Type", "application/json");
        this.outputStream = this.connection.getOutputStream();
        JSONObject jsonObject = new JSONObject(logowanie);
        this.outputStream.write(jsonObject.toString().getBytes());
        this.connection.getResponseCode();
        this.outputStream.close();
    }
}
