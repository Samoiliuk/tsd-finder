package com.warehouse.tsd_finder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.Charset;

public class UdpListenService extends Service {
    private volatile boolean running = true;
    private Thread worker;

    // ЗМІНІТЬ ПІД СЕБЕ: унікальний ID цього ТСД (можете зробити однаковим на всіх для тесту)
    private static final String DEVICE_ID = "TSD-01";

    private static final int UDP_PORT = 33333;

    @Override public void onCreate() {
        super.onCreate();
        worker = new Thread(new Runnable() {
            @Override public void run() { listenLoop(); }
        }, "udp-listener");
        worker.start();
    }

    private void listenLoop() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(UDP_PORT);
            socket.setBroadcast(true);

            byte[] buf = new byte[512];
            while (running) {
                DatagramPacket p = new DatagramPacket(buf, buf.length);
                socket.receive(p);

                String msg = new String(p.getData(), 0, p.getLength(), Charset.forName("UTF-8")).trim();

                // Команди:
                // BEEP:ALL
                // BEEP:TSD-01
                // STOP:ALL
                // STOP:TSD-01
                if (msg.equalsIgnoreCase("BEEP:ALL") || msg.equalsIgnoreCase("BEEP:" + DEVICE_ID)) {
                    AlarmPlayer.play(getApplicationContext(), 90); // 90 секунд
                } else if (msg.equalsIgnoreCase("STOP:ALL") || msg.equalsIgnoreCase("STOP:" + DEVICE_ID)) {
                    AlarmPlayer.stop();
                }
            }
        } catch (Exception ignored) {
        } finally {
            if (socket != null) socket.close();
        }
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override public void onDestroy() {
        running = false;
        super.onDestroy();
    }

    @Override public IBinder onBind(Intent intent) { return null; }
}
