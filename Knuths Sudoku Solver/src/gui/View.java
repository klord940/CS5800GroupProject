package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.Scanner; // Import the Scanner class to read text files


/**
 * The GUI of the Sudoku solver.  creates a JFrame, with sub panels to 
 * hold the various game objects.
 * 
 * @author Robert Wilson
 * @author Kristina Lord
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
	private JButton textInputButton;
	private JFileChooser fileChooser;
	private JButton uploadFileButton;
	private JDialog dialog = new JDialog();

	
	// window settings
	private final int resolution = 35;
	private int screenWidth = resolution * 20;
	private int screenHeight = resolution * 20;
	private Dimension cellSize = new Dimension(80, 80);
	private Font cellFont = new Font("Arial", Font.BOLD, 40);
	
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
		this.textInputButton = new JButton();
		this.fileChooser = new JFileChooser();
				
		// JLabel and ActionListener initialization
		this.importText = new JLabel();
		this.listener = new Listener(controller, this);
		this.uploadFileButton = new JButton();
		
		// Set the gameState before calling setActionListener
		//this.gameState = GameState.TITLESCREEN;
		
		// Call setup methods
		setActionListener(controller);
		checkSolution.addActionListener(listener);
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
			textInputButton.addActionListener(listener);
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
		textInputButton.setText("Text Input");
		
		// Add option to start game.
		reset.setBackground(Color.cyan);
	    options.add(reset);
	    options.add(textInputButton);
	    options.remove(back);
	    options.remove(checkSolution);

		
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
		options.removeAll();
		messages.removeAll();
		
		// The game itself, a grid of n x n tiles.
		JPanel game = new JPanel(new GridLayout(9,9));
		for(int row = 0; row<9 ;row++) {
	        for(int col = 0; col<9 ;col++) {
	            board[row][col] = new JButton();
	            board[row][col].setPreferredSize(cellSize);
	            board[row][col].setText("");
	            if ((row >= 1 && row <= 3 && ((col >=1 && col <= 3) || (col >=5 && col <= 7))) ||
	            		(row >= 5 && row <= 7 && ((col >=1 && col <= 3) || (col >=5 && col <= 7)))) {
	            	board[row][col].setBackground(Color.LIGHT_GRAY);
	            }
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
	    setActionListener(controller);
	}
	
	/**
	 * Creates the play state of the game by first removing all title related components 
	 * and then loading in the play state components which includes the board, reset button, 
	 * and check solution button.
	 */
	protected void setupTextInputView(Controller controller) {
		// Remove all title screen components and validates to make sure it can still be used.
		gamePanel.removeAll();
		gamePanel.validate();
		options.removeAll();
		messages.removeAll();
		
        // restrict the user to select files of all types
        fileChooser.setAcceptAllFileFilterUsed(false);

        // only allow files of .txt extension
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
        fileChooser.addChoosableFileFilter(restrict);
        		
		// Center this game matrix on the gamePanel.
        uploadFileButton.addActionListener(listener);
	    
		uploadFileButton.setText("Upload Text File");
	    gamePanel.add(uploadFileButton, BorderLayout.AFTER_LAST_LINE);
	    
	    // Options panel holds all options a user can take.
	    reset.setBackground(Color.cyan);
	    options.remove(reset);
	    options.remove(textInputButton);	    
	    back.addActionListener(listener);
		options.add(back);
	    
	    window.getContentPane().add(gamePanel, BorderLayout.NORTH);
	    window.getContentPane().add(options, BorderLayout.CENTER);
	    window.pack();
	    
	    setActionListener(controller);
	    
	}
	
	/**
	 * Displays the Completed text
	 */
	void completedScreen() {
		// Displays end text
		importText.setText("Solved");
		options.remove(checkSolution);
		
	}
	
	/**
	 * Resets the current game board displayed 
	 */
	private void setupNewGame() {
		for(int row = 0; row<9 ;row++) {
	        for(int col = 0; col<9 ;col++) {
	            board[row][col].setText("");
	            if ((row >= 1 && row <= 3 && ((col >=1 && col <= 3) || (col >=5 && col <= 7))) ||
	            		(row >= 5 && row <= 7 && ((col >=1 && col <= 3) || (col >=5 && col <= 7)))) {
	            	board[row][col].setBackground(Color.LIGHT_GRAY);
	            }
	            board[row][col].setEnabled(true);
	            board[row][col].setBackground(new JButton().getBackground());
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
			break;
		case TEXTINPUTSCREEN:
			setup(controller);
		case INPUTSCREEN:
			setup(controller);
		case GAMESCREEN: 
			setup(controller);
			break;
		case COMPLETESCREEN: 
			setup(controller);
			break;
		}
	}
	
	private void setupBoardFromTextInput(Controller controller, Integer[][] boardNums) {
		// Remove all title screen components and validates to make sure it can still be used.
		gamePanel.removeAll();
		options.removeAll();
		messages.removeAll();
		gamePanel.validate();
		
		// The game itself, a grid of n x n tiles.
		JPanel game = new JPanel(new GridLayout(9,9));
		for(int row = 0; row<9 ;row++) {
	        for(int col = 0; col<9 ;col++) {
	            board[row][col] = new JButton();
	            board[row][col].setPreferredSize(cellSize);
	    		board[row][col].setFont(cellFont);
	            if ((row >= 1 && row <= 3 && ((col >=1 && col <= 3) || (col >=5 && col <= 7))) ||
	            		(row >= 5 && row <= 7 && ((col >=1 && col <= 3) || (col >=5 && col <= 7)))) {
	            	board[row][col].setBackground(Color.LIGHT_GRAY);
	            }
	    		if (boardNums[row][col] == 0) {
	    			board[row][col].setText("");
	    		}
	    		else {
		            board[row][col].setText(String.valueOf(boardNums[row][col]));	
	    		}
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
	    window.getContentPane().add(messages, BorderLayout.SOUTH);
	    window.pack();
	    
	    // change the gameState to play state.
	    controller.gameState = GameState.INPUTSCREEN;
	    setActionListener(controller);

	}
	
	
	public void showUploadFile() {
        // restrict the user to select files of all types
		try {
	        fileChooser.setAcceptAllFileFilterUsed(false);
	
	        // only allow files of .txt extension
	        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
	        fileChooser.addChoosableFileFilter(restrict);
	        fileChooser.addActionListener(this.listener);
			dialog.setSize(new Dimension(400, 400));
			uploadFileButton.setEnabled(false);
			dialog.add(fileChooser);
			dialog.show();
			dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			dialog.addWindowListener(new WindowAdapter()
		    {
			      public void windowClosing(WindowEvent e)
			      {
			    	uploadFileButton.setEnabled(true);
			      }
			    });
			
		} catch (Exception error) {
			System.out.println("error: " + error.toString());
		}

	}
	
	public void closeDialog() {
		uploadFileButton.setEnabled(true);
		dialog.dispose();
	}
	
	public void getSelectedFile(Controller controller) throws FileNotFoundException {
		File selectedFile = fileChooser.getSelectedFile();
		Scanner scanner = new Scanner(selectedFile);
		int i = 0;
		Integer[][] boardNums = new Integer[9][9];
		try {		
			while (scanner.hasNext()) {
				String data = scanner.next();
				char[] charArray = data.toCharArray();
				int j;
				for (j = 0; j < charArray.length; j++) {
					boardNums[i][j] = new Integer(Character.getNumericValue(charArray[j]));		
				}
				i++;				
			}
			controller.setBoard(boardNums);
			setupBoardFromTextInput(controller, boardNums);
			closeDialog();
			scanner.close();			
		} catch (Exception error) {
			this.closeDialog();
			importText.setText("Unable to upload. Please review the formatting of the file.");
			messages.add(importText);
			gamePanel.add(messages, BorderLayout.CENTER);
		}
			
	}
	
	public void updateBoard(Integer[][] updatedBoard) {
		int i;
		int j;
		for (i=0; i<9; i++) {
			for (j=0; j<9; j++) {
				if (!this.board[i][j].getText().equals(String.valueOf(updatedBoard[i][j]))) {
					this.board[i][j].setText(String.valueOf(updatedBoard[i][j]));
					this.board[i][j].setBackground(Color.GREEN);
				}
				else {
		            if ((i >= 1 && i <= 3 && ((j >=1 && j <= 3) || (j >=5 && j <= 7))) ||
		            		(i >= 5 && i <= 7 && ((j >=1 && j <= 3) || (j >=5 && j <= 7)))) {
		            	board[i][j].setBackground(Color.LIGHT_GRAY);
		            }
		            else {
						this.board[i][j].setBackground(new JButton().getBackground());         	
		            }
				}
	            board[i][j].setPreferredSize(cellSize);
	    		board[i][j].setFont(cellFont);
			}
		}
		completedScreen();
	}
	
	public void setInvalidBoard() {
	    // Messages is the turn indicator and relays important messages about the game state.
	    messages.setBackground(Color.white);
	    messages.add(importText);
	    importText.setFont(importText.getFont().deriveFont(Font.BOLD, 20F));
	    importText.setText("Invalid puzzle. Try again.");
		int i;
		int j;
		for (i=0; i<9; i++) {
			for (j=0; j<9; j++) {
				this.board[i][j].setBackground(Color.RED);
			}
		}
		options.remove(checkSolution);

	    // Add each component to the window in their respective positions.
	    window.getContentPane().add(messages, BorderLayout.SOUTH);
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
	 * Checks to see if the text input button was pressed last.
	 * 
	 * @param e (ActionEvent) button pressed.
	 * @return boolean of whether reset button was pressed.
	 */
	public boolean isTextInput(ActionEvent e) {
		if(e.getSource() == textInputButton) {
			return true;
		}  return false;
	}
	
	
	public boolean isFileChooser(ActionEvent e) {
		if(e.getSource() == fileChooser) {
			return true;
		}  return false;
	}
	/**
	 * Checks to see if the upload file button was pressed last.
	 * 
	 * @param e (ActionEvent) button pressed.
	 * @return boolean of whether reset button was pressed.
	 */
	public boolean isUploadFileButton(ActionEvent e) {
		if(e.getSource() == uploadFileButton) {
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
		board[playerMove.get(0)][playerMove.get(1)].setFont(cellFont);
		if(integer == null) {
			board[playerMove.get(0)][playerMove.get(1)].setText("");
		} else {
			board[playerMove.get(0)][playerMove.get(1)].setText(integer.toString());
		}
	}
}
