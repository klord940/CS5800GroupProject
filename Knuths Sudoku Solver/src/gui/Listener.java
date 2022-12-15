package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

/**
 * The listener for the Sudoku game, contains the action to perform when buttons are clicked.
 * 
 * @author Robert Wilson
 * Created: 19 NOV 2022
 * Class: cs5800
 *
 */
public class Listener implements ActionListener {

	// Model for the game
	private Controller controller;
	// view for the game
	private View view;
	
	/**
	 * Set's the listeners controller and view
	 * 
	 * @param controller (Controller) controller of the game. 
	 * @param view (View) view of the game.
	 */
	public Listener(Controller controller, View view) {
		this.controller = controller;
		this.view = view;
	}
	
	/**
	 * Action listener for all buttons
	 * Checks for which button is being pressed and passes to 
	 * the appropriate controller method
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Perform the requested action depending on button pressed
		if(view.isReset(e)) {
			controller.reset();
		} else if(view.isCheckSolution(e)) {
			controller.checkSolution();
		} else if(view.isBack(e)) {
			controller.back();
		} else if(view.isTextInput(e)) {
			controller.sendToTextInput();
		} else if(view.isUploadFileButton(e)) {
			controller.uploadFile();
		} else if(view.isFileChooser(e)) {
			try {
				if (e.getActionCommand() == "ApproveSelection") {
					controller.getSelectedFile();
				}
				else if (e.getActionCommand() == "CancelSelection"){
					controller.closeDialog();
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			controller.update(e);
		}
	}

}
