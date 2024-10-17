package view;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabComponent extends JPanel {

	private JButton closeButton;
	
	// Method to create the custom tab component with a close button
    public TabComponent(JTabbedPane tabbedPane, String title) {
    	super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        //JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setOpaque(false);

        // Create a label with the tab title
        JLabel tabLabel = new JLabel(title);
        add(tabLabel);

        // Add some spacing between the label and the close button
        add(Box.createHorizontalStrut(5));

        // Create the close button
        closeButton = new JButton("x");
        
        closeButton.setOpaque(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        closeButton.setMargin(new Insets(0, 0, 0, 0)); // Remove extra margins
        closeButton.setPreferredSize(new Dimension(16, 16));

        

        // Add the close button to the tab panel
        add(closeButton);
        
    }
    
    public JButton getCloseButton() {
    	return closeButton;
    }
}
