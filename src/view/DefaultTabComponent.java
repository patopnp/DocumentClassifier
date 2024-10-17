package view;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

public class DefaultTabComponent extends JPanel{

	
	public DefaultTabComponent(JTabbedPane tabbedPane) {
		JPanel content = new JPanel();
    	content.setLayout(new BorderLayout(0, 0));
		JLabel instruction = new JLabel("To add a new category click on + in the upper right corner", SwingConstants.CENTER);
		//instruction.setLocation(200, 500);
		content.add(instruction);
    	// Add the content to the tab
        tabbedPane.add(content);

        
	}
}
