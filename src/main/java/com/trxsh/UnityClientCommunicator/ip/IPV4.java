package com.trxsh.UnityClientCommunicator.ip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Enumeration;

public class IPV4 {

    public static InetAddress getV4() {
        try {
            URL url = new URL("https://api64.ipify.org?format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String json = response.toString();
            String publicIPv4 = json.split(":")[1].replaceAll("[\"}]", "").trim();
            return Inet4Address.getByName(publicIPv4);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
