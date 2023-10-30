package samurai;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;

public class Board extends JPanel implements ActionListener {

    private Font font = new Font( "Serif", Font.PLAIN, 25);
    private Image sablon;
    SudokuSolver sr = new SudokuSolver();

    public Board() throws IOException{
        ImageIcon img = new ImageIcon("board.png");
        sablon = img.getImage();
    }

    public Image getSablon() {
        return sablon;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        sr.elapsedTime = (new Date()).getTime() - sr.startTime;
        System.out.println("\n");
        System.out.println(sr.elapsedTime+" milisaniyede cozuldu.");

        g.drawImage(getSablon(),0,0,null);
        g.setFont(font);

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(sr.boardOne[i][j] != 0 && sr.boardOne[i][j] != -1)
                    g.drawString(String.valueOf(sr.boardOne[i][j]),((j+1)*30)-20 ,(i*30)+25);
            }
        }

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(sr.boardTwo[i][j] != 0 && sr.boardTwo[i][j] != -1)
                    g.drawString(String.valueOf(sr.boardTwo[i][j]),((j+13)*30)-20 ,(i*30)+25);
            }
        }

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(sr.boardMiddle[i][j] != 0 && sr.boardMiddle[i][j] != -1)
                    g.drawString(String.valueOf(sr.boardMiddle[i][j]),((j+7)*30)-20 ,((i+6)*30)+25);
            }
        }

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(sr.boardFour[i][j] != 0 && sr.boardFour[i][j] != -1)
                    g.drawString(String.valueOf(sr.boardFour[i][j]),((j+1)*30)-20 ,((i+12)*30)+25);
            }
        }

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(sr.boardFive[i][j] != 0 && sr.boardFive[i][j] != -1)
                    g.drawString(String.valueOf(sr.boardFive[i][j]),((j+13)*30)-20 ,((i+12)*30)+25);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
