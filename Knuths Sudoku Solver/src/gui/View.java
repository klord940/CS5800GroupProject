package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The GUI of the Sudoku solver.  creates a JFrame, with sub panels to 
 * hold the various game objects.
 * 
 * @author Robert Wilson
 * Created: 19 NOV 2022
 * Class: CS5800
 *
 */

public class View extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5535050244970982644L;
	
	
	// JFrame and window components
	private JFrame window;
	private JButton[][] board;
	private JLabel importText;
	private JButton reset;
	private JButton checkSolution;
	private JButton back;
	private JPanel gamePanel;
	private JPanel messages;
	private JPanel options;
	
	// window settings
	private final int resolution = 48;
	private int screenWidth = resolution * 10;
	private int screenHeight = resolution * 10;
	
	// Other important fields
	//private GameState gameState;
	private Listener listener;
	
	/**
	 * Constructor creates new instances of JFrame and its subcomponents, finally calling the titleScreen setup method.
	 */
	public View(Controller controller) {
		// JFrame initialization
		this.window = new JFrame("Sudoku");
		
		// JPanel initializations
		this.gamePanel = new JPanel(new FlowLayout());
		this.messages = new JPanel(new FlowLayout());
		this.options = new JPanel(new FlowLayout());
		
		// JButton initializations
		this.board = new JButton[9][9];
		this.reset = new JButton();
		this.checkSolution = new JButton();
		this.back = new JButton("Back");
				
		// JLabel and ActionListener initialization
		this.importText = new JLabel();
		this.listener = new Listener(controller, this);
		
		// Set the gameState before calling setActionListener
		//this.gameState = GameState.TITLESCREEN;
		
		// Call setup methods
		setActionListener(controller);
		titleScreen();
	}
	
	/**
	 * Sets the action controller for the view
	 * 
	 * @param controller (Controller) this games controller
	 */
	void setActionListener(Controller controller) {
		switch(controller.gameState) {
		case TITLESCREEN:
			reset.addActionListener(listener);
			checkSolution.addActionListener(listener);
			break;
		case INPUTSCREEN: 
			for(int row = 0; row<9 ;row++) {
		        for(int col = 0; col<9 ;col++) {
		        		board[row][col].addActionListener(listener);
		        }
			}
			back.addActionListener(listener);
			break;
		case GAMESCREEN:
			// This can be a played game or solution viewer
		case COMPLETESCREEN:
			break;
		default:
			break;
		}
	}
	
	/**
	 * Creation method for the title screen, initializing window settings 
	 * and then drawing the title screen components.
	 */
	void titleScreen() {
		// Remove all title screen components and validates to make sure it can still be used.
		gamePanel.removeAll();
		gamePanel.validate();
		
		// Window settings
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(new Dimension(screenWidth, screenHeight));
		window.setBackground(Color.black);
		window.setResizable(true);
		window.setFocusable(true);
		
		// Title Message
		messages.setBackground(Color.white);
		Font arial_80B = new Font("Arial", Font.BOLD, 80);
		importText.setFont(arial_80B);
		importText.setText("Sudoku");
		importText.setSize(new Dimension(resolution*2, resolution*2));
		messages.add(importText);
		gamePanel.add(messages, BorderLayout.CENTER);
		
		// Button text fields
		reset.setText("Manual Input");
		checkSolution.setText("Text Input");
		
		// Add option to start game.
		reset.setBackground(Color.cyan);
	    options.add(reset);
	    options.add(checkSolution);
	    options.remove(back);
		
	    // Add each component to the window in their respective positions.
	    window.getContentPane().add(gamePanel, BorderLayout.NORTH);
	    window.getContentPane().add(options, BorderLayout.CENTER);
	    
		// Set the window to the center of the screen, pack it tight, and make it visible.
		window.setLocationRelativeTo(null);
		window.pack();
		window.setVisible(true);
	}
	
	/**
	 * Creates the play state of the game by first removing all title related components 
	 * and then loading in the play state components which includes the board, reset button, 
	 * and check solution button.
	 */
	private void setup(Controller controller) {
		// Remove all title screen components and validates to make sure it can still be used.
		gamePanel.removeAll();
		gamePanel.validate();
		
		// The game itself, a grid of n x n tiles.
		JPanel game = new JPanel(new GridLayout(9,9));
		for(int row = 0; row<9 ;row++) {
	        for(int col = 0; col<9 ;col++) {
	            board[row][col] = new JButton();
	            board[row][col].setPreferredSize(new Dimension(resolution*2, resolution*2));
	            board[row][col].setText("");
	            game.add(board[row][col]);
	            // Add Grid Lines most likely with paint.
	            if(col%3 == 0) {
	            	
	            }
		    }
		}
		
		// Center this game matrix on the gamePanel.
	    gamePanel.add(game, BorderLayout.AFTER_LAST_LINE);
	    
	    // Options panel holds all options a user can take.
	    reset.setBackground(Color.cyan);
	    reset.setText("New Board");
	    checkSolution.setText("Check if a solution exists and submit");
	    options.add(reset);
	    options.add(checkSolution);
	    options.add(back);
	    
	    // Messages is the turn indicator and relays important messages about the game state.
	    messages.setBackground(Color.white);
	    messages.add(importText);
	    importText.setFont(importText.getFont().deriveFont(Font.BOLD, 20F));
	    importText.setText("Select a square to increment number");
	    
	    // Add each component to the window in their respective positions.
	    window.getContentPane().add(gamePanel, BorderLayout.NORTH);
	    window.getContentPane().add(options, BorderLayout.CENTER);
	    window.getContentPane().add(messages, BorderLayout.SOUTH);
	    window.pack();
	    
	    // change the gameState to play state.
	    controller.gameState = GameState.INPUTSCREEN;
	}
	
	/**
	 * Displays the Completed text
	 */
	void completedScreen() {
		// Displays end text
		importText.setText("Solved");
		
		// Disable button
		checkSolution.setEnabled(false);
	}
	
	/**
	 * Resets the current game board displayed 
	 */
	private void setupNewGame() {
		for(int row = 0; row<9 ;row++) {
	        for(int col = 0; col<9 ;col++) {
	            board[row][col].setText("");
	            board[row][col].setEnabled(true);
		    }
		}
		importText.setText("Select a square to increment number");
	}
	
	/**
	 * Changes the action taken by the reset button. If on the title screen button should 
	 * call the play state setup method and update the Actionlistener to listen to board buttons.
	 * If on game screen then reset the board text displayed and enable buttons. If victory screen
	 * then perform same action as play screen.  
	 */
	void resetButton(Controller controller) {
		switch(controller.gameState) {
		case TITLESCREEN: 
			setup(controller);
			setActionListener(controller);
			break;
		case INPUTSCREEN:
		case GAMESCREEN: 
			setupNewGame();
			break;
		case COMPLETESCREEN: 
			setupNewGame();
			break;
		}
	}

	/**
	 * Checks to see if the reset button was pressed last.
	 * 
	 * @param e (ActionEvent) button pressed.
	 * @return boolean of whether reset button was pressed.
	 */
	public boolean isReset(ActionEvent e) {
		if(e.getSource() == reset) {
			return true;
		}  return false;
	}
	
	/**
	 * Checks to see if the Check Solutions button was pressed last.
	 * 
	 * @param e (ActionEvent) button pressed.
	 * @return boolean of whether check Solutions button was pressed.
	 */
	public boolean isCheckSolution(ActionEvent e) {
		if(e.getSource() == checkSolution) {
			return true;
		}  return false;
	}
	
	/**
	 * Checks to see if the reset button was pressed last.
	 * 
	 * @param e (ActionEvent) button pressed.
	 * @return boolean of whether reset button was pressed.
	 */
	public boolean isBack(ActionEvent e) {
		if(e.getSource() == back) {
			return true;
		}  return false;
	}
	
	/**
	 * Grabs the source of the button to send onto model as a two element arrayList.
	 * 
	 * @param e (ActionEvent) button pressed.
	 * @return ArrayList<Integer> a two element arrayList with 
	 * 			the row and column of the pressed button.
	 */
	ArrayList<Integer> move(ActionEvent e) {
		ArrayList<Integer> playerMove = new ArrayList<Integer>(); 
		for(int row = 0; row<9 ;row++) {
	        for(int col = 0; col<9 ;col++) {
	            if(e.getSource() == board[row][col]) {
	            	playerMove.add(row);
	            	playerMove.add(col);
	            }
		    }
		}
		return playerMove;
	}
	
	/**
	 * Updates the displayed game board from the model.
	 * 
	 * @param playerMove (ArrayList<Integer>) the two element arrayList of the players last move.
	 * @param integer (Integer) the current integer to display, nothing if null.
	 */
	public void update(ArrayList<Integer> playerMove, Integer integer) {
		board[playerMove.get(0)][playerMove.get(1)].setFont(new Font("Arial", Font.BOLD, 80));
		if(integer == null) {
			board[playerMove.get(0)][playerMove.get(1)].setText("");
		} else {
			board[playerMove.get(0)][playerMove.get(1)].setText(integer.toString());
		}
		//board[playerMove.get(0)][playerMove.get(1)].setEnabled(true);
	}
}
