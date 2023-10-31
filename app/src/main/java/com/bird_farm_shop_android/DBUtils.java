package com.bird_farm_shop_android;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtils {
    Connection connection;
    public static Connection getConnection() {
        Connection connection = null; // Declare connection here
        String ip = "10.86.31.130";
        String port = "1433";
        String db = "BIRD_FARM_SHOP_ANDROID";
        String username = "sa";
        String password = "12345";

        // Avoid network operations on the main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String connectURL;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databasename=" + db + ";user=" + username + ";" + "password=" + password + ";";
            connection = DriverManager.getConnection(connectURL);
        } catch (Exception e) {
            // Print the full stack trace to understand the issue better
            e.printStackTrace();
            Log.e("Error: ", e.getMessage());
        }
        return connection;
    }
}
