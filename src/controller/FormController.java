package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import model.Category;
import model.CategoryObservable;
import model.IOFileHandler;
import model.machinelearning.NaiveBayesClassifier;
import view.CategoryPanel;
import view.MainFrame;
import view.DefaultTabComponent;
import view.TabComponent;

public class FormController {
	
	private NaiveBayesClassifier nbc;
	private MainFrame documentClassifierView ;
	private Validator validator = new Validator();
	
	HashMap<CategoryPanel, Category> panelToCategory = new HashMap<>();
	
	public FormController(NaiveBayesClassifier nbc, MainFrame f) {
		this.nbc = nbc;
		this.documentClassifierView = f;
		addActionListeners();
	}
	
	
	
	private void addActionListeners() {
	
		documentClassifierView.getSaveButton().addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				Object currentSelectedItem = documentClassifierView.getComboBox().getSelectedItem();
				
				if (currentSelectedItem != null & currentSelectedItem instanceof CategoryObservable) {
					CategoryObservable selectedCategory = (CategoryObservable) documentClassifierView.getComboBox().getSelectedItem();
					String filepath = selectedCategory.getFilesDirectory();
					if (validator.validateSaveDocument(filepath)) {
					
						IOFileHandler.saveIntoFile(filepath, selectedCategory.getName(), documentClassifierView.getDocumentTextArea().getText());
						documentClassifierView.getTabPanel().getSelectedComponent();
						
						if (currentSelectedItem instanceof Category) {
						
							reloadDirectoryFiles((Category)currentSelectedItem);
						}
						//view.showSuccess("File saved successfully");
					}
				}
				
				
			}
	    	
	    });
		
		// Add an ActionListener to handle closing the tab
		documentClassifierView.getAddButton().addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            System.out.println("Line executed");
				JTabbedPane tabbedPane = documentClassifierView.getTabPanel();
	    		int tabCount = tabbedPane.getTabCount();
	    			
				String input = JOptionPane.showInputDialog(null, "Enter category name");
				
				if (input == null) 
					return;
				
				if (input.isBlank()) {
					input = "new category " + tabCount;
				}
				
				tabbedPane.removeTabAt(tabbedPane.getTabCount()-1);
				
				Category ct = new Category(input);
				nbc.addCategory(ct);
				documentClassifierView.getComboBox().addItem(ct);
				
				//saveComboBox.addItem(ct);
				CategoryPanel cp = new CategoryPanel(ct);
				cp.getSearchDirectoryButton().addActionListener(new ActionListener() {
	
					@Override
					public void actionPerformed(ActionEvent e) {
						loadDirectoryFiles(cp, ct);
					}}
				);
				addTab(tabbedPane, input, cp);
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
				addDefaultTab(tabbedPane);
	    	    
	        }
	    });
	    
	    documentClassifierView.getClassifyButton().addActionListener(new ActionListener() {
	    	
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println(documentClassifierView.getDocumentTextArea().getText());
				if (validator.validateClassify()) {
					
					Category result = nbc.clasify(documentClassifierView.getDocumentTextArea().getText());
					documentClassifierView.showInfo("Document belong to " + result);
					documentClassifierView.getComboBox().setSelectedItem(result);
					//saveComboBox.setSelectedItem(result);
				}
			}
	    	
	    	
	    });
	}
	public void addDefaultTab(JTabbedPane tabbedPane) {
    	
		new DefaultTabComponent(tabbedPane);
		
		// Create the custom tab component with close button
        int index = tabbedPane.getTabCount() - 1;
        
        tabbedPane.setTabComponentAt(index, documentClassifierView.getAddButton());
        tabbedPane.setTitleAt(index, "Default"); 
        
        
    }
	
    public void addTab(JTabbedPane tabbedPane, String name, CategoryPanel content) {
        // Add the content to the tab
        tabbedPane.add(content);
        
        TabComponent tc = new TabComponent(tabbedPane, name);
        
        // Add an ActionListener to handle closing the tab
        tc.getCloseButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Find the index of the tab that contains this tab component
            	//System.out.println("Patrick is awesome.");
                int index = tabbedPane.indexOfTabComponent(tc);
                
                
                
                if (index == tabbedPane.getTabCount()-2 && tabbedPane.getTabCount()>=3) {
                	tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-3);
                	
                }
                
                if (index != -1) {
                    // Remove the tab at this index
                    tabbedPane.remove(index);
                    documentClassifierView.getComboBox().removeItemAt(index);
                    nbc.removeCategory(index);
                }
            }
        });
        
        // Create the custom tab component with close button
        int index = tabbedPane.getTabCount() - 1;
        tabbedPane.setTabComponentAt(index, tc);
        tabbedPane.setTitleAt(index, name); 
    }
	
    
	
    
    public void reloadDirectoryFiles(Category category) {
    	   
	    
	    File file = new File(category.getFilesDirectory());
	    
	    if (file != null) {
	    	List<File> filesList = Arrays.asList(file.listFiles());
	    	String pathToFiles = file.getAbsolutePath();
	    	category.setFilesDirectory(pathToFiles);
	        
	        //Retain only the txt files
	        filesList.removeIf(aFile -> { return !(aFile.getName().length()>3 && aFile.getName().substring(aFile.getName().length()-3).equals("txt"));});
	    	
	    	category.setListOfFiles(filesList);
	    	category.notifyObservers();
	    }
	       
	} 
    
	public void loadDirectoryFiles(CategoryPanel displayedCategoryPanel, Category category) {
	    
	    File file = displayedCategoryPanel.displayFilesFolderSelection();
	    
	    if (file != null) {
	    	List<File> filesList = new ArrayList<>();
	    	
	    	for (File f : file.listFiles((dir, name)->{return name.toLowerCase().endsWith(".txt");})) {
	    		filesList.add(f);
	    	}
	    	
	    	String pathToFiles = file.getAbsolutePath();
	    	category.setFilesDirectory(pathToFiles);
	        
	    	
	    	category.setListOfFiles(filesList);
	    	category.notifyObservers();
	    }
	       
	}
	
	class Validator {
		
		boolean validateSaveDocument(String filepath) {
			return validateTextNotEmpty() && validateCorrectFilePath(filepath);
		}
		
		boolean validateClassify() {
			return (validateTextNotEmpty() && validateEnoughCategories() && validateNoEmptyDocumentLists());
		}
		
		

		private boolean validateTextNotEmpty() {
			JTextArea textDoc = documentClassifierView.getDocumentTextArea();
			if (textDoc.getText() == null || textDoc.getText().isBlank()) {
				documentClassifierView.showError("Text is empty.");
				return false;
			}
			return true;

	    }
	    
	    private boolean validateNoEmptyDocumentLists() {
	    	if (nbc.isAnyCategoryEmpty()) {
	    		documentClassifierView.showError("Some categories have empty folders.");
	    		
				return false;
	    	}
	    	return true;
	    }
	    
	    private boolean validateEnoughCategories() {
	    	if (nbc.numberOfCategories()<2) {
	    		documentClassifierView.showError("There needs to be at least 2 categories.");
				return false;
			}
	    	return true;
	    }
	    
	    
	    private boolean validateCorrectFilePath(String fp) {
	    	
	    	File fileDirectory = new File(fp);
			if (fileDirectory.exists() && fileDirectory.isDirectory()) {
				System.out.println("The directory exists.");
				return true;
			} else {
				
				documentClassifierView.showError("The directory does not exist.");
		        return false;
		    }
		    
	    }
		
	}
	
	
	
}
