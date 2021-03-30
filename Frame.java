package bababooey;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Frame extends JFrame {

    public Frame() throws FileNotFoundException, TeamNorFoundException {
        Panel updog = new Panel();
        Table[] tables = updog.getTables();
        for(Table table: tables) table.updateScore();
        setSize(1000, 1300);
        add(updog);
        setVisible(true);
        setDefaultCloseOperation(3);
    }

    public static void main(String[] args) throws FileNotFoundException, TeamNorFoundException {
        new Frame();
    }

}
