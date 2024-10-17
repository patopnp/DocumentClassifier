package app;

import controller.FormController;
import model.machinelearning.NaiveBayesClassifier;
import view.MainFrame;

public class DocumentClassifier {
	public static void main(String[] args) throws Exception
    {
		MainFrame mainFrame = new MainFrame();
		NaiveBayesClassifier nbc = new NaiveBayesClassifier();
		FormController fc = new FormController(nbc, mainFrame);
    }
}
