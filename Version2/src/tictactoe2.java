import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
// importing the necessary modules.

/** 
 * @author : Het Daftary.
 * Project : Tic Tac Toe game with GUI using JFrame. 
 * Game tpye : 1 player or 2 player game. 
 * Default mode when starting will be 2-player mode but we can change from the settings by a new 1-player game.
 * In 1-player mode you will be X and computer will be O.
 * What's new : 
 *  1. Bug Fixes.
 *  2. Added support for One player mode.
*/

class FrameOfGame implements ActionListener {
    /**
    * The class does the main work of making a game frame and using it. 
    * 
    */
    
    private static final Font myFont = new Font("Arial", Font. PLAIN, 40);
    private JButton[][] grid;
    private boolean isTurnOfX;
    private JFrame mainFrame; // Settings frame will be used later.
    private JPanel mainPanel, winnerPanel;
    private int currentX, currentY; 
    private char moves[][];
    public boolean hasWonX, hasWonO, is2playerMode; // HasWonX and HasWoO to be made false before every game.
    private boolean[][] canMoveIJ; // This array is used to have control allowing any button-press to effect only one time.
    // items in canMoveIJ to be made true before every game.
    private JMenu menu;       
    private JMenuBar menuBar; 
    private JMenuItem[] menuItems;
    private JLabel winnerLabel;
    private int onePlayerMoves; 
    // Will start with the gmae.

    public FrameOfGame() {
        moves = new char[3][3]; 
        canMoveIJ = new boolean[3][3]; 
        // Players related stuff.

        this.grid = new JButton[3][3];
        this.isTurnOfX = true;
        this.mainFrame = new JFrame();    
        mainFrame.setTitle("Tic Tac Toe");
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setSize(310,400); // Our default size.
        mainFrame.getContentPane();
        mainPanel = new JPanel(); 
        mainFrame.setTitle("Tic Tac Toe");
        
        mainFrame.setResizable(false);
        hasWonO = false;
        hasWonX = false;
        is2playerMode = true;
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
                grid[i][j].setFont(myFont);
                grid[i][j].addActionListener(this);
                grid[i][j].setPreferredSize(new Dimension(100,100));
                mainPanel.add(grid[i][j]);
                canMoveIJ[i][j] = true; // Pressing button will only affect if this is true. So, we can turn it false when button is clicked to have just one press effect.
            }
        }
        mainPanel.setVisible(true);
    }

    private void initializeMenu() { // Adds an menu bar to our mainFrame.
        menu = new JMenu("Menu items");
        menuBar = new JMenuBar();
        menuBar.add(menu);
        menuItems = new JMenuItem[3];
        menuItems[0] = new JMenuItem("New 2-player Game");
        menuItems[1] = new JMenuItem("New 1-player Game");
        menuItems[2] = new JMenuItem("About Game");
        menu.add(menuItems[0]);
        menu.add(menuItems[1]);
        menu.add(menuItems[2]);
        menuItems[0].addActionListener(this);
        menuItems[1].addActionListener(this);
        menuItems[2].addActionListener(this);
        mainFrame.setJMenuBar(menuBar);
    } /* Initialising functions called in contructor. (End)*/

    /* Functions for Game Control.(Start) */
    private void seeIfWon(char a) { // Updates the value of hasWon for player after a's move.
        if(moves[1][1] == a) { // 4 possibilities to win or else notWon. /* 000111000, 010010010, 100010001, 001010100 */  
            if(moves[1][0]==a && moves[1][2]==a) { /* 000111000 */
                if(a=='X') hasWonX = true;
                else hasWonO = true;
            } else if(moves[1][0]==a && moves[1][2]==a) { /* 010010010 */
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

    public int[] getAValidMoveForOnePlayer() { // Gives some random move for computer to make.
        int k;
        int[] answer = new int[2];
        do {
            for(k = 0; k < 2; k++) 
                do { // The step will lead to generation of i and j to be 0, 1, 2 with equal probabilitiy.
                    answer[k] =(int)(3*Math.random()); 
                } while(answer[k] == 3);
        }while(!canMoveIJ[answer[0]][answer[1]]);
        return answer;
    } /* Functions for Game Control.(End) */

    @Override
    public void actionPerformed(ActionEvent e) { // Doing the main task.
        int i,j;
        
        /* Action for the Menu Items.  */
        if(e.getSource() == menuItems[0]) { // New 2-player game.
            for(i = 0; i < 3; i++) {
                for(j = 0; j < 3; j++) {
                    grid[i][j].setText("");
                    moves[i][j] = '\0';
                    canMoveIJ[i][j] = true;
                }
            }
            hasWonO = false;
            hasWonX = false; // Because new game does not have winners when starting.
            isTurnOfX = true;
            is2playerMode = true;
            winnerPanel.remove(winnerLabel);
        } else if(e.getSource() == menuItems[1]) { // New 1-player game.
            for(i = 0; i < 3; i++) {
                for(j = 0; j < 3; j++) {
                    grid[i][j].setText("");
                    moves[i][j] = '\0';
                    canMoveIJ[i][j] = true;
                }
            }
            hasWonO = false;
            hasWonX = false; // Because new game does not have winners when starting.
            isTurnOfX = true;
            is2playerMode = false;
            onePlayerMoves = 0;
            if(winnerLabel.getText() != "") winnerPanel.remove(winnerLabel);
        } else if(e.getSource() == menuItems[2])  { // About Game.
            // Creating a local frame and displaying about game.
            JFrame aboutGameFrame = new JFrame();
            aboutGameFrame.setLayout(new GridLayout(5, 1));
            aboutGameFrame.setSize(new Dimension(400, 130));
            String[] toAdd = {"Made By : Het Daftary","Tic-Tac-Toe using Java GUI(JFrame).","Options for one-player and 2-player mode.","You will play X in one-player mode.","X will always play first."};
            
            for(int k = 0; k < 5; k++) {
                aboutGameFrame.add(new JLabel(toAdd[k]));
            }

            aboutGameFrame.setResizable(false);
            aboutGameFrame.setVisible(true);
        }
        
        /* Action for the buttons. */
        for(i = 0; i < 3; i++) {
            for(j = 0; j < 3; j++) {
                if(e.getSource() == grid[i][j]) {
                    
                    if(canMoveIJ[i][j] && !hasWonO && !hasWonX) {
                        canMoveIJ[i][j] = false; // This will indicate that the button is already pressed.
                        currentX = i;
                        currentY = j;
                        if(is2playerMode) { // Action for a 2-player game.
                            if(isTurnOfX) {
                                //grid[i][j].setFont(myFont);
                                grid[i][j].setText("X");
                                isTurnOfX = false; // Toogling for changing the turn.
                                moves[currentX][currentY] = 'X';
                                seeIfWon('X');
                                //winnerLabel.setFont(myFont);
                                if(hasWonX) {    
                                    winnerLabel = new JLabel("X has Won");
                                    winnerPanel.add(winnerLabel);
                                }
                            } else {
                                //grid[i][j].setFont(myFont);
                                grid[i][j].setText("O");
                                isTurnOfX = true;
                                moves[currentX][currentY] = 'O';
                                seeIfWon('O');
                                //winnerLabel.setFont(myFont);
                                if(hasWonO) {    
                                    winnerLabel = new JLabel("O has Won");
                                    winnerPanel.add(winnerLabel);
                                }
                            }  
                        } else { // Action for a 1-player game.
                            grid[i][j].setText("X");
                            moves[currentX][currentY] = 'X';
                            seeIfWon('X');
                            if(hasWonX) {    
                                winnerLabel = new JLabel("You Won.");
                                winnerPanel.add(winnerLabel);
                            } // Make a Label if when won.
                            else if(onePlayerMoves < 4) { // O will only make a move if X has not won.
                                int[] moveForO = getAValidMoveForOnePlayer(); // Local variable which stores the move that O(computer) should make.
                                grid[moveForO[0]][moveForO[1]].setText("O");
                                moves[moveForO[0]][moveForO[1]] = 'O';
                                canMoveIJ[moveForO[0]][moveForO[1]] = false;
                                seeIfWon('O');
                                if(hasWonO) {    
                                    winnerLabel = new JLabel("Better luck next time.");
                                    winnerPanel.add(winnerLabel);
                                } // Make a Label if when lost.
                                onePlayerMoves++; // Everytime O makes a move we increment onePayer move. Once it gets equal to 4 means the game is draw.
                            } else if(!hasWonO && !hasWonX) { // Label for the draw.
                                winnerLabel = new JLabel("Game is draw. Better luck next time.");
                                winnerPanel.add(winnerLabel);
                            }
                        }
                    }    
                } 
            }
        }
    }
}

public class tictactoe2{
    public static void main(String[] args) {
        System.out.println("Let's start!");
        new FrameOfGame();
    }
}
