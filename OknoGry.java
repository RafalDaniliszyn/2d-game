package pl.rafal;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class OknoGry extends JFrame {
    Logowanie logowanie;
    ServerConnection serverConnection;

    OknoGry(Logowanie logowanie){
        this.logowanie = logowanie;
        serverConnection = new ServerConnection();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    serverConnection.ustawStatusFalse(logowanie);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

    }
}
