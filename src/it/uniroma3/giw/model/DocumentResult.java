package it.uniroma3.giw.model;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;

public class DocumentResult {

	private ScoreDoc scoreDoc;
	private Document document;
	
	public DocumentResult(ScoreDoc scoreDoc, Document document) {
		this.setScoreDoc(scoreDoc);
		this.setDocument(document);
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public ScoreDoc getScoreDoc() {
		return scoreDoc;
	}

	public void setScoreDoc(ScoreDoc scoreDoc) {
		this.scoreDoc = scoreDoc;
	}
	
	public String getTitle(){
		return this.document.get("title");
	}
	
	public float getScore(){
		return this.scoreDoc.score;
	}

	public String getPath(){
		return this.document.get("path");
	}
	
	public String getShortPath(){
		return this.document.get("shortPath");
	}
	
}	
	
	
	
	
	
	
	
	
	
	
	
