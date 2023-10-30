package samurai;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        JFrame f = new JFrame();
        Board suko = new Board();
        f.setTitle("Samurai Sudoku");
        f.add(suko);
        f.setSize(646,667);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
