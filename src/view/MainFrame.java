package view;

import javax.swing.*;

import model.CategoryObservable;

import java.awt.*;

public class MainFrame
    extends JFrame{
	
    // Components of the Form
    private Container c;
    private JLabel title;
    private JLabel result;
    private JLabel textDocLabel;
    private JButton classifyButton;
    private JButton saveButton;
    private JTextArea textDoc;
    private JComboBox<CategoryObservable> saveComboBox;
    private JLabel res;
    private JTabbedPane tabPanel;
    private JButton addNewTabButton;
    
    
    public MainFrame()
    {
        setTitle("Document classifier");
        setBounds(300, 90, 900-30, 600-30);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
 
        c = getContentPane();
        c.setLayout(null);

        
        title = new JLabel("Document classifier");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 10);
        c.add(title);
 

        saveComboBox = new JComboBox<CategoryObservable>();
        saveComboBox.setSize(200, 30);
        saveComboBox.setLocation(100, 410-30);
        
        c.add(saveComboBox);
        
        // Create a JTabbedPane, which will hold the category tabs 
        tabPanel = new JTabbedPane();

        
        addNewTabButton = createTabButton(tabPanel);
        addDefaultTab(tabPanel);
        
        tabPanel.setBounds(0, 100-40, 500, 300);
        
        // Add the tabbedPane to the JFrame's content 
        c.add(tabPanel); 
 
        result = new JLabel("Save into: ");
        result.setFont(new Font("Arial", Font.PLAIN, 15));
        result.setSize(100, 20);
        result.setLocation(10, 410-30);
        c.add(result);
        
        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 15));
        saveButton.setSize(100, 20);
        saveButton.setLocation(360, 410-30);
        
        c.add(saveButton);
        
        classifyButton = new JButton("Classify");
        classifyButton.setFont(new Font("Arial", Font.PLAIN, 15));
        classifyButton.setSize(100, 20);
        classifyButton.setLocation(360, 470-30);
        c.add(classifyButton);
 
        textDocLabel = new JLabel("Text for classification:");
        textDocLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        textDocLabel.setSize(200, 15);
        textDocLabel.setLocation(520, 60);
        c.add(textDocLabel);
        
        textDoc = new JTextArea();
        textDoc.setFont(new Font("Arial", Font.PLAIN, 15));
        textDoc.setLineWrap(true);
        JScrollPane documentScrollPane = new JScrollPane(textDoc); 
        documentScrollPane.setBounds(520, 100-20, 300, 400);
        c.add(documentScrollPane);
 

        
        res = new JLabel("");
        res.setFont(new Font("Arial", Font.PLAIN, 20));
        res.setSize(500, 25);
        res.setLocation(100, 500-40);
        c.add(res);
        
        setVisible(true);
    }

    public JTabbedPane getTabPanel() {
    	return tabPanel;
    }
    
    public JButton getClassifyButton() {
    	return classifyButton;
    }
    
    
    public void addDefaultTab(JTabbedPane tabbedPane) {
    	
		new DefaultTabComponent(tabbedPane);
		
		// Create the custom tab component with close button
        int index = tabbedPane.getTabCount() - 1;
        
        tabbedPane.setTabComponentAt(index, getAddButton());
        tabbedPane.setTitleAt(index, "Default");
        
    }
    
    
    public JButton getAddButton() {
    	return addNewTabButton;
    }
    
    
    
    public JComboBox<CategoryObservable> getComboBox() {
    	return saveComboBox;
    }
    
    public JButton getSaveButton() {
    	return saveButton;
    }
    
    public void showInfo(String message) {
    	JOptionPane.showMessageDialog(this, message, 
                "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void showError(String message) {
    	JOptionPane.showMessageDialog(this, message, 
                "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    
    private JButton createTabButton(JTabbedPane tabbedPane) {

        // Create the close button
        JButton addButton = new JButton("+");
        addButton.setOpaque(false);
        addButton.setContentAreaFilled(false);
        addButton.setBorderPainted(false);
        addButton.setMargin(new Insets(0, 0, 0, 0)); 
        addButton.setPreferredSize(new Dimension(16, 16));

        return addButton;
    }
    
    
    public JTextArea getDocumentTextArea() {
    	return textDoc;
    }
    
}

