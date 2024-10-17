package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import patterns.observer.Observer;


public class Category implements CategoryObservable{
	private List<File> listOfFiles;
	private String filesDirectory;
	private final String name;
	
	List<Observer> observersList;
	
	public Category(String name) {
		this.name = name;
		this.observersList = new ArrayList<Observer>();
	}
	
	public String getFilesDirectory() {
		return filesDirectory;
	}
	public void setFilesDirectory(String filesDirectory) {
		this.filesDirectory = filesDirectory;
	}
	
	public String getName() {
		return name;
	}
	
	public List<File> getListOfFiles() {
		return listOfFiles;
	}
	public void setListOfFiles(List<File> listOfFiles) {
		this.listOfFiles = listOfFiles;
	}
	
	@Override
	public void attach(Observer o) {
		observersList.add(o);
	}
	
	@Override
	public void detach(Observer o) {
		observersList.remove(o);
	}
	
	@Override
	public void notifyObservers() {
		observersList.forEach(Observer::update);
	}
	
	@Override
	public String toString() {
		return getName();
	}

	
}
