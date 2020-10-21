import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
// importing the necessary modules.

/** 
 * @author : Het Daftary.
 * Project : Tic Tac Toe game with GUI using JFrame. 
 * Game tpye : 2 player game. 
 * Just the Working code.
 * Most primitive version of Tic Tac Toe.
*/

class FrameOfGame implements ActionListener {

    private JButton[][] grid;
    private boolean isTurnOfX;
    private JFrame mainFrame;
    private JPanel mainPanel;
    private int currentX, currentY; 
    private char moves[][];
    public boolean hasWonX, hasWonO;
    //private JMenu menu;
    //private JMenuBar menuBar; // The menuBar functionality will come in Version 1.
    // Will start with the gmae.
    
    public FrameOfGame() {
        this.grid = new JButton[3][3];
        this.isTurnOfX = true;
        this.mainFrame = new JFrame();
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setSize(400,450); // Our default size.
        mainFrame.getContentPane(); 
        mainPanel = new JPanel();
        mainPanel.setSize(new Dimension(400, 400)); 
        mainPanel.setLayout(new GridLayout(3, 3));
        mainFrame.setResizable(false);
        hasWonO = false;
        hasWonX = false;
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Because, we want to exit after our work is done.
        initialzeGrid();
        mainFrame.setVisible(true); 
        // Frame related stuff.
 
        moves = new char[3][3]; 
        // Players related stuff.
    }

    private void seeIfWon(char a) { // Updates the value of hasWon for player after a's move.
        if(moves[1][1] == a) { // 4 possibilities to win or else notWon. /* 000111000, 010010010, 100010001, 001010100 */  
            if(moves[0][1]==a && moves[0][2]==a) { /* 000111000 */
                if(a=='X') hasWonX = true;
                else hasWonO = true;
            } else if(moves[0][1]==a && moves[2][1]==a) { /* 010010010 */
                if(a=='X') hasWonX = true;
                else hasWonO = true;
            } else if(moves[0][0]==a && moves[2][2]==a) { /* 100010001 */
                if(a=='X') hasWonX = true;
                else hasWonO = true;
            } else if(moves[0][2]==a && moves[2][0]==a) { /* 001010100 */
                if(a=='X') hasWonX = true;
                else hasWonO = true;
            } 
        } else { // 4 possbilities to win or else notWon. /* 111000000, 000000111, 100100100, 001001001 */
            if(moves[0][0]==a && moves[0][1]==a && moves[0][2]==a) { /* 111000000 */
                if(a=='X') hasWonX = true;
                else hasWonO = true;
            }
            else if(moves[2][0]==a && moves[2][1]==a && moves[2][2]==a) { /* 000000111 */
                if(a=='X') hasWonX = true;
                else hasWonO = true;
            } else if(moves[0][0]==a && moves[1][0]==a && moves[2][0]==a) { /* 100100100 */
                if(a=='X') hasWonX = true;
                else hasWonO = true;
            } else if(moves[0][2]==a && moves[1][2]==a && moves[2][2]==a) { /* 001001001 */
                if(a=='X') hasWonX = true;
                else hasWonO = true;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { // Doing the main task.
        int i,j;
        for(i = 0; i < 3; i++) {
            for(j = 0; j < 3; j++) {
                if(e.getSource() == grid[i][j]) {
                    currentX = i;
                    currentY = j;
                    
                    if(isTurnOfX) {
                        grid[i][j].setText("X");
                        isTurnOfX = false; // Toogling for changing the turn.
                        moves[currentX][currentY] = 'X';
                        seeIfWon('X');
                        if(hasWonX) mainFrame.add(new JLabel("X has won"));
                    } else {
                        grid[i][j].setText("O");
                        isTurnOfX = true;
                        moves[currentX][currentY] = 'O';
                        seeIfWon('O');
                        if(hasWonO) mainFrame.add(new JLabel("O has won"));
                    }
                } 
            }
        }
    }

    private void initialzeGrid() { // Initizalises a blank grid with all the buttons to null.
        int i, j;
        
        mainFrame.add(mainPanel);
        for(i = 0; i < 3; i++) {
            for(j = 0; j < 3; j++) {
                grid[i][j] = new JButton("");
                grid[i][j].addActionListener(this);
                grid[i][j].setPreferredSize(new Dimension(100,100));
                mainPanel.add(grid[i][j]);
            }
        }
    }
}

public class tictactoe0{
    public static void main(String[] args) {
        System.out.println("Let's start!");
        new FrameOfGame();
    }
}
