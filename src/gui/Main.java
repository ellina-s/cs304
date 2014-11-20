package gui;

/**
 * Contains main() to run the program.
 * Creates a login dialog.
 */

public class Main{
	
	public static void main(String[] args) 
    {
	//MvbView mvb = new MvbView();

	// we will not call pack() on the main frame 
	// because the size set by setSize() will be ignored
	//mvb.setVisible(true);
		
	// create the login window
	Login lw = new Login();
    }
}