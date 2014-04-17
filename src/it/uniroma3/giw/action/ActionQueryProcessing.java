package it.uniroma3.giw.action;

import it.uniroma3.giw.model.DocumentIO;
import it.uniroma3.giw.model.DocumentResult;
import it.uniroma3.giw.model.SearchFiles;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

public class ActionQueryProcessing implements Action {

	private static final float THRESHOLD = 0.3f;
	@Override
	public String execute(HttpServletRequest request) {
		String query = request.getParameter("query");
		DocumentIO io = new DocumentIO();
		String indextPath = io.getIndexPath();
		if (query != null){
			SearchFiles searchFiles;
			try {
				searchFiles = new SearchFiles(indextPath);
				DocumentResult[] hits = searchFiles.doSearch(query);

				//gestione del "forse cercavi"
				if(hits.length == 0 || hits[0].getScore() <= THRESHOLD) {


						String[] didYouMean = searchFiles.getDidYouMean(query);
						if(didYouMean.length != 0)
						    request.setAttribute("did you mean", didYouMean[0]);

				}	
				
				
				if(hits.length > 0) {
					String[] keywords = hits[0].getKeywords().split(",");
					request.setAttribute("keywords", keywords);
					
					
				}

				request.setAttribute("results", hits);
				return "results";
			} catch (ParseException | IOException | InvalidTokenOffsetsException e) {
				e.printStackTrace();
			}
		}

		return "error";
	}
		
		
		
	

}
