package pl.rafal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OknoLogowania extends JFrame implements ActionListener {
    TextField name;
    TextField id;
    JButton zaloguj;
    String nameGracza;
    int idGracza;

    boolean czyZalogowano = false;

    OknoLogowania(){
        setSize(500,500);
        setLayout(null);
        name = new TextField("");
        id = new TextField("");
        zaloguj = new JButton("zaloguj");

        zaloguj.setBounds(100,180,150,50);
        name.setBounds(100,50,150,50);
        id.setBounds(100,120,150,50);
        add(name);
        add(id);
        add(zaloguj);
        zaloguj.addActionListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        logowanie();
    }
    void logowanie(){
        while (true){
            if (this.czyZalogowano == true){
                setVisible(false);
                this.setFocusable(false);
                break;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == zaloguj) {
            this.nameGracza = name.getText();
            this.idGracza = Integer.valueOf(id.getText());
            this.czyZalogowano = true;

        }
    }
}
