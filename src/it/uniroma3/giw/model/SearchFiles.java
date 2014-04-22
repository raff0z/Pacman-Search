package it.uniroma3.giw.model;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TotalHitCountCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchFiles {

	private String field = "contents";
	private StandardAnalyzer analyzer;
	private IndexSearcher searcher;
	private DirectoryReader reader;
	private int totalDoc;
	
	private int hitsPerPage = 10;

	public SearchFiles(String indexPath) throws IOException{
		this.reader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
		this.searcher = new IndexSearcher(this.reader);
		this.analyzer = new StandardAnalyzer(Version.LUCENE_47);	
	}

	public DocumentResult[] doSearch(String query, int start) throws ParseException, IOException, InvalidTokenOffsetsException{
		QueryParser parser = new QueryParser(Version.LUCENE_47, field, this.analyzer);
		Query queryObj = parser.parse(query);

		TopDocs results = searcher.search(queryObj, start * hitsPerPage);
		
		totalDoc = results.totalHits; 
		
		ScoreDoc[] hits = results.scoreDocs;
		//System.out.println(hits.length);
		DocumentResult[] documents;

		QueryScorer scorer = new QueryScorer(queryObj, "contents");
		Highlighter highlighter = new Highlighter(scorer); 
		
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
			String[] fragments = highlighter.getBestFragments(this.analyzer, "contents", document.get("contents"),3);

			DocumentResult documentResult = new DocumentResult(hits[i], document);
			documentResult.setNear( fragments );
			documentResult.setMoreLikeThis(getMoreLikeThis(hits[i]));
			documents[i-realStart] = documentResult;


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

	public String getMoreLikeThis(ScoreDoc hit) throws IOException, ParseException{
		MoreLikeThis mlt = new MoreLikeThis(this.reader);
		mlt.setAnalyzer(analyzer);

		Query query = mlt.like(hit.doc);

		String queryString = query.toString();

		String splitted = Arrays.toString(queryString.split("contents:"));

		splitted = splitted.substring(3,splitted.length()-1);

		return splitted;
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