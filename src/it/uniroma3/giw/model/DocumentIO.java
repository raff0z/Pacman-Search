package it.uniroma3.giw.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DocumentIO {

	private String indexPath;
	private String documentPath;

	public DocumentIO(){
		Properties conf = new Properties();
		try {
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config/pacman_configuration.properties");
			if(inputStream == null)
				System.out.println("qua");
			
			conf.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}		

		this.indexPath = conf.getProperty("index-path");
		this.documentPath = conf.getProperty("documents-path");
	}

	public String getIndexPath() {
		return indexPath;
	}

	public String getDocumentPath() {
		return documentPath;
	}



}
