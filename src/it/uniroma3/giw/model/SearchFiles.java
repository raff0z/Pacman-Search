package it.uniroma3.giw.model;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Fields;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchFiles {

	private String field = "contents";
	private StandardAnalyzer analyzer;
	private IndexSearcher searcher;
	private DirectoryReader reader;

	private int hitsPerPage = 10;

	public SearchFiles(String indexPath) throws IOException{
		this.reader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
		this.searcher = new IndexSearcher(this.reader);
		this.analyzer = new StandardAnalyzer(Version.LUCENE_47);	
	}

	public DocumentResult[] doSearch(String query) throws ParseException, IOException{
		QueryParser parser = new QueryParser(Version.LUCENE_47, field, this.analyzer);
		Query queryObj = parser.parse(query);
		
		TopDocs results = searcher.search(queryObj, 5 * hitsPerPage);
		
		ScoreDoc[] hits = results.scoreDocs;
		DocumentResult[] documents = new DocumentResult[hits.length];
		
		for (int i=0; i<hits.length; i++){
			documents[i] = new DocumentResult(hits[i], searcher.doc(hits[i].doc));
			Fields invertedIndex = getInvertedIndex(documents[i].getScoreDoc().doc);



		}
		return documents;
	}
	
	public String[] getDidYouMean(String query) throws IOException {
		DocumentIO io = new DocumentIO();
		File dir = new File(io.getSpellCheckerPath());
		Directory directory = FSDirectory.open(dir);
		SpellChecker spellChecker = new SpellChecker(directory);

		String[] results = spellChecker.suggestSimilar(query, 5);
		
		spellChecker.close();
		
		return results;

	}

	private static Fields getInvertedIndex(int docID){
		return null;
		//return new IndexReader().getTermVectors(docID);  //TODO Da sistemare
	}
}