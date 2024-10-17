package model;

import java.io.File;
import java.util.List;

import patterns.observer.Observer;

public interface CategoryObservable {
	
	public void attach(Observer o);
	public void detach(Observer o);	
	public void notifyObservers();
	public List<File> getListOfFiles();
	public String getFilesDirectory();	
	public String getName();
	
	
}
