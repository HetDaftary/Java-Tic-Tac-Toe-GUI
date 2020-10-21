import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
// importing the necessary modules.

/** 
 * @author : Het Daftary.
 * Project : Tic Tac Toe game with GUI using JFrame. 
 * Game tpye : 2 player game.
 * What's new : 
 *  1. Added title field. 
 *  2. Added Menu Bar functionally. 
*/

class FrameOfGame implements ActionListener {

    private JButton[][] grid;
    private boolean isTurnOfX;
    private JFrame mainFrame, settingsFrame; // Settings frame will be used later.
    private JPanel mainPanel, winnerPanel;
    private int currentX, currentY; 
    private char moves[][];
    public boolean hasWonX, hasWonO;
    private JMenu menu;       
    private JMenuBar menuBar; 
    private JMenuItem[] menuItems;
    private JLabel winnerLabel; 
    // Will start with the gmae.
    
    public FrameOfGame() {
        this.grid = new JButton[3][3];
        this.isTurnOfX = true;
        this.mainFrame = new JFrame();    
        
        mainFrame.setTitle("Tic Tac Toe");
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setSize(400,550); // Our default size.
        mainFrame.getContentPane(); 
        mainPanel = new JPanel(); 
        mainFrame.setTitle("Tic Tac Toe");
        mainFrame.setResizable(false);
        hasWonO = false;
        hasWonX = false;
        menu = new JMenu();
        menuBar = new JMenuBar();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Because, we want to exit after our work is done.
        
        winnerPanel = new JPanel();
        winnerPanel.setSize(new Dimension(400,50));
        mainFrame.add(winnerPanel);
        // Frame related stuff.
 
        initializeMenu();
        initializeGrid();
        // Initailising various parts.

        mainFrame.setVisible(true); // The line is pushed below as we Buttons would not appear untill screen is refreshed.

        moves = new char[3][3]; 
        // Players related stuff.
    }

    /* Initialising functions called in contructor. (Start)*/
    private void initializeGrid() { // Initizalises a blank grid with all the buttons to null.
        int i, j;
        mainPanel.setSize(new Dimension(400,400));
        mainPanel.setLayout(new GridLayout(3, 3));
        mainFrame.add(mainPanel);
        mainPanel.setVisible(true);
        for(i = 0; i < 3; i++) {
            for(j = 0; j < 3; j++) {
                grid[i][j] = new JButton("");
                grid[i][j].addActionListener(this);
                grid[i][j].setPreferredSize(new Dimension(100,100));
                mainPanel.add(grid[i][j]);
            }
        }
        mainPanel.setVisible(true);
    }

    private void initializeMenu() { // Adds an menu bar to our mainFrame.
        menu = new JMenu("Menu items");
        menuBar = new JMenuBar();
        menuBar.add(menu);
        menuItems = new JMenuItem[2];
        menuItems[0] = new JMenuItem("New Game");
        menuItems[1] = new JMenuItem("Quit");
        menu.add(menuItems[0]);
        menu.add(menuItems[1]);
        menu.add(menuItems[2]);
        menuItems[0].addActionListener(this);
        menuItems[1].addActionListener(this);
        mainFrame.setJMenuBar(menuBar);
    } /* Initialising functions called in contructor. (End)*/

    /* Functions for Game Control.(Start) */
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
        } /* Functions for Game Control.(End) */
    }

    @Override
    public void actionPerformed(ActionEvent e) { // Doing the main task.
        int i,j;
        
        /* Action for the Menu Items.  */
        if(e.getSource() == menuItems[0]) { // Get a new Game.
            for(i = 0; i < 3; i++) {
                for(j = 0; j < 3; j++) {
                    grid[i][j].setText("");
                    moves[i][j] = '\0';
                }
            }
        } else if(e.getSource() == menuItems[1]) { // Quit.
            System.exit(0);
        }
        
        
        /* Action for the buttons. */
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
                        winnerLabel = new JLabel("X has Won");
                        if(hasWonX) winnerPanel.add(winnerLabel);
                    } else {
                        grid[i][j].setText("O");
                        isTurnOfX = true;
                        moves[currentX][currentY] = 'O';
                        seeIfWon('O');
                        winnerLabel = new JLabel("O has Won");
                        if(hasWonO) winnerPanel.add(winnerLabel);
                    }
                } 
            }
        }
    }
}

public class tictactoe1{
    public static void main(String[] args) {
        System.out.println("Let's start!");
        new FrameOfGame();
    }
}
