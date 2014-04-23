package it.uniroma3.giw.model;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchImages {
	
	private String field = "title";
	private DirectoryReader reader;
	private IndexSearcher searcher;
	private StandardAnalyzer analyzer;
	private int totalDoc;	
	private int hitsPerPage = 10;
	
	
	public SearchImages(String indexPath) throws IOException {
		this.reader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
		this.searcher = new IndexSearcher(this.reader);
		this.analyzer = new StandardAnalyzer(Version.LUCENE_47);	
	}
	
    public DocumentResult[] doSearch(String query, int start) throws ParseException, IOException  {

    	QueryParser parser = new QueryParser(Version.LUCENE_47, field, this.analyzer);
		Query queryObj = parser.parse(query);

		TopDocs results = searcher.search(queryObj, start * hitsPerPage);
		
		totalDoc = results.totalHits; 
		
		ScoreDoc[] hits = results.scoreDocs;
		//System.out.println(hits.length);
		DocumentResult[] documents;

		int realStart =  (start-1)*hitsPerPage;
		
		int end = realStart + hitsPerPage;
		
		if(end>=totalDoc){
		    documents = new DocumentResult[totalDoc - realStart];
		    end = totalDoc;
		}else{
		    documents = new DocumentResult[hitsPerPage];
		}
		
		for (int i = realStart ; i < end; i++){
			Document document = searcher.doc(hits[i].doc);
			DocumentResult documentResult = new DocumentResult(hits[i], document);
			documents[i-realStart] = documentResult;
		}

		return documents;
    }
    
	public int getTotalDoc() {
	    return totalDoc;
	}

	public int getHitsPerPage() {
	    return hitsPerPage;
	}

	public void setHitsPerPage(int hitsPerPage) {
	    this.hitsPerPage = hitsPerPage;
	}
	
}
