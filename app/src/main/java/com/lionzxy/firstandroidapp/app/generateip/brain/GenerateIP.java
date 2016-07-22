package com.lionzxy.firstandroidapp.app.generateip.brain;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import com.lionzxy.firstandroidapp.app.generateip.activitys.GenerateIpAcitivy;
import com.lionzxy.firstandroidapp.app.generateip.enums.GenerateIpStep;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * com.lionzxy.firstandroidapp.app.brain
 * Created by LionZXY on 16.01.2016.
 * FirstAndroidApp
 */
public class GenerateIP implements Runnable {
    static String ip = null;
    static GenerateIpAcitivy generateIpAcitivy;
    static Random random = new Random();
    static ConnectivityManager connMgr = null;
    static int tryE = 0;

    public static void generateIP(GenerateIpAcitivy ipAcitivy) {
        ip = null;
        generateIpAcitivy = ipAcitivy;
        if (connMgr == null) {
            connMgr = (ConnectivityManager)
                    generateIpAcitivy.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo.isConnected()) {
            generateIpAcitivy.step = GenerateIpStep.Generate;
            int maxThread = generateIpAcitivy.getValue("maxThread",8);
            for (int i = 0; i < maxThread; i++) {
                new Thread(new GenerateIP()).start();
            }
        } else sendMessage(3, "Please connect to network!");
    }

    @Override
    public void run() {
        while (ip == null) {
            try {
                String tempIp = generateRandomIp();
                HttpURLConnection urlc = (HttpURLConnection)
                        (new URL("http://" + tempIp + "/")
                                .openConnection());
                urlc.setRequestProperty("User-Agent", "Android");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(generateIpAcitivy.getValue("timeout",1000));
                urlc.connect();
                ip = tempIp;
                urlc.disconnect();
                generateIpAcitivy.step = GenerateIpStep.Finish;
                sendMessage(1, ip);
                sendMessage(2, "Click on Link");
                tryE = 0;
            } catch (Exception e) {
                if (ip == null) {
                    sendMessage(1, "Wait.... " + tryE + " ip's checked");
                    tryE++;
                }
            }
        }
    }

    static void sendMessage(int id, String msg) {
        mHandler.obtainMessage(id, msg).sendToTarget();
    }

    static Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message message) {
            if (message != null && message.obj instanceof String)
                switch (message.what) {
                    case 1:
                        generateIpAcitivy.setText((String) message.obj);
                        return;
                    case 2:
                        generateIpAcitivy.setButtonText((String) message.obj);
                        return;
                    case 3:
                        Toast.makeText(generateIpAcitivy, (String) message.obj, Toast.LENGTH_LONG);
                        return;
                }

        }
    };

    public static String generateRandomIp() {
        String ip = String.valueOf(random.nextInt(256));
        for (int i = 1; i < 4; i++)
            ip += "." + random.nextInt(256);
        return ip;
    }
}
