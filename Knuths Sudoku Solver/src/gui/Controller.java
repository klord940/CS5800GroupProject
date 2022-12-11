package gui;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Controller for the Sudoku game.  Communicates between the model, the view, 
 * and the ActionEvent Listener.
 * 
 * @author Robert Wilson
 * Created: 19 NOV 2022
 * Class: CS5800 
 * 
 */

public class Controller {

	// Model for the game
	private Model model;
	// view for the game
	private View view;
	// GameState
	GameState gameState;
	
	/**
	 * Controller creates an instance of the model, the view, and adds actionListener
	 */
	public Controller() {
		this.model = new Model();
		this.gameState = GameState.TITLESCREEN;
		this.view = new View(this);
		
	}
	
	/**
	 * Resets the view and makes a new model to start the game from scratch. Making sure to preserve the input parameters.
	 * 
	 */
	public void reset() {
		model = new Model();
		view.resetButton(this);
	}	
	
	/**
	 * if gameState is input, checks whether it is a viable Sudoku Puzzle, otherwise 
	 * it checks whether it is a solution to the Sudoku puzzle.
	 * 
	 */
	public void checkSolution() {
		switch(this.gameState) {
		case INPUTSCREEN:
			if(!model.validPuzzle) {
				model.isValidPuzzle();
				if(model.validPuzzle) {
					setGameState(GameState.GAMESCREEN);
				}
			}
		case GAMESCREEN:
			if(!model.gameOver) {
				model.isGameOver();
				if(model.gameOver) {
					setGameState(GameState.COMPLETESCREEN);
				}
			}
		default:
			break;
		}
	}
	
	/**
	 * Tells the view to go back to the previous page
	 * 
	 */
	public void back() {
		switch(this.gameState) {
		case TITLESCREEN:
			break;
		case INPUTSCREEN:
			setGameState(GameState.TITLESCREEN);
			break;
		case GAMESCREEN:
			setGameState(GameState.INPUTSCREEN);
			break;
		case COMPLETESCREEN:
			setGameState(GameState.TITLESCREEN);
			break;
		}
	}
	
	/**
	 * Receives the location of the button pressed and gives it to the model, then tells the view to update itself with the given information from the model. 
	 * 
	 * @param e (ActionEvent) the event notification from the button pressed
	 */
	public void update(ActionEvent e) {
		if(!model.isGameOver()) {
			ArrayList<Integer> playerMove = view.move(e);
			model.move(playerMove.get(0), playerMove.get(1));
			view.update(playerMove, model.getMarkAt(playerMove.get(0), playerMove.get(1)));
		}
	}
	
	/**
	 * Sets the current gameState to parameter gameState. If victory state, then call completedScreen method.
	 * 
	 * @param gameState (GameState) the gameState to switch to.
	 */
	void setGameState(GameState gameState) {
		this.gameState = gameState;
		switch(gameState) {
		case TITLESCREEN:
			view.titleScreen();
			break;
		case GAMESCREEN:
			break;
		case COMPLETESCREEN:
			view.completedScreen();
			break;
		default:
			break;
		}
	}	
}