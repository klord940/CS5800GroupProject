import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Perform the requested action depending on button pressed
		System.out.println("e: " + e.toString());
		System.out.println("view: " + view.toString());
		try {
			if(view.isReset(e)) {
				controller.reset();
			} else if(view.isCheckSolution(e)) {
				controller.checkSolution();
			} else if(view.isBack(e)) {
				controller.back();
			} else {
				controller.update(e);
			}			
		} catch (Exception error) {
			System.out.println("error: " + error.toString());
		}

	}
}