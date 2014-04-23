package it.uniroma3.giw.action;

import it.uniroma3.giw.model.DocumentIO;
import it.uniroma3.giw.model.DocumentResult;
import it.uniroma3.giw.model.SearchFiles;

import java.io.IOException;

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
				
				String stringStart = (String) request.getParameter("start");
				
				DocumentResult[] hits;
				
				int start; 
				
				if((stringStart == null)){
				    start = 1;
				}else{
				    start = Integer.parseInt(stringStart);
				}
				
				hits = searchFiles.doSearch(query,start);
				    

				//gestione del "forse cercavi"
				if((hits.length == 0 || hits[0].getScore() <= THRESHOLD)&&(start == 1)) {


						String[] didYouMean = searchFiles.getDidYouMean(query);
						if(didYouMean.length != 0)
						    request.setAttribute("did you mean", didYouMean[0]);

				}	
				
				
				if(hits.length > 0) {
				    	String keywords = hits[0].getKeywords();
				    	if(keywords != null){
            				    	String[] keywordSplitted = keywords.split(",");
            					
            					request.setAttribute("keywords", keywordSplitted);
				    	}
					
					
					
				}
				
				int totalPages;
				
				int totalDoc = searchFiles.getTotalDoc();
				
				if(totalDoc % 10 == 0){
				    totalPages = totalDoc/10;
				}else{
				    totalPages = totalDoc/10 +1;
				}
				
				//per paging dinamico
				
				Integer endPaging = start + 5;
				
				if(endPaging > totalPages){
				    endPaging = totalPages;
				}
				
				Integer realStartPaging = 1;
				
				if(start > 5){
					realStartPaging = start - 5;
				}
				
				request.setAttribute("total docs", totalDoc);
				request.setAttribute("end paging", endPaging);
				request.setAttribute("real start paging", realStartPaging);
				request.setAttribute("totalPages", totalPages);
				request.setAttribute("query", query);
				request.setAttribute("results", hits);
				return "results";
			} catch (ParseException | IOException | InvalidTokenOffsetsException e) {
				e.printStackTrace();
			}
		}

		return "error";
	}

}
