package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import model.CategoryObservable;
import patterns.observer.Observer;

public class CategoryPanel extends JPanel implements Observer {

	private final JScrollPane scrollPaneMain;
    private final JLabel directoryLabel;
    private final JTextField directoryField;
    private final JButton searchDirectoryButton;
    private final JLabel filesLabel;
    private final DefaultListModel<String> l1;
    private CategoryObservable categoryObs;
    
	public CategoryPanel(CategoryObservable catObs) {
		setLayout(new GridLayout(1,0));
		
		catObs.attach(this);
		this.categoryObs = catObs;
		
		JPanel scrollPaneContents = new JPanel();
		scrollPaneContents.setPreferredSize(new Dimension(450, 250));
		scrollPaneMain = new JScrollPane(scrollPaneContents);
		
		scrollPaneContents.setLayout(null);
		
		l1 = new DefaultListModel<>();
        JList<String> list = new JList<>(l1);
        JScrollPane sp = new JScrollPane(list);
        
        sp.setBounds(200-30,90+20-10, 200,120);
        scrollPaneContents.add(sp);
        scrollPaneContents.setBounds(0, 0, 100, 300);
        
        
        directoryLabel = new JLabel("Training dataset:");
        directoryLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        directoryLabel.setSize(260, 20);
        directoryLabel.setLocation(83-30, 40+20-10);
        scrollPaneContents.add(directoryLabel);
 
        directoryField = new JTextField();
        directoryField.setFont(new Font("Arial", Font.PLAIN, 15));
        directoryField.setSize(200, 20);
        directoryField.setLocation(200-30, 40+20-10);
        scrollPaneContents.add(directoryField);

        searchDirectoryButton = new JButton("Open");
        searchDirectoryButton.setSize(70, 20);
        searchDirectoryButton.setLocation(400-30, 40+20-10);
        scrollPaneContents.add(searchDirectoryButton);
        
        filesLabel = new JLabel("Files:");
        filesLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        filesLabel.setSize(100, 20);
        filesLabel.setLocation(158-30, 90+20-10);
        scrollPaneContents.add(filesLabel);
        add(scrollPaneMain);
	}
	
	public File displayFilesFolderSelection() {
		JFileChooser chooser = new JFileChooser(); 
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle("Look for files");
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

	    chooser.setAcceptAllFileFilterUsed(false);

	    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
	    	return chooser.getSelectedFile();
	    }
	    else {
	    	return null;
	    }
	}
	
	public void resetFilesList(List<File> files) {
		l1.clear();
		files.forEach(file-> l1.addElement(file.getName()));
	}
	
	public JButton getSearchDirectoryButton() {
		return searchDirectoryButton;
	}
	
	@Override
	public void update() {
		String filesDirForDisplay = categoryObs.getFilesDirectory();
		List<File> docs = categoryObs.getListOfFiles();
		
		directoryField.setText(filesDirForDisplay);
		resetFilesList(docs);
	}
	
}
