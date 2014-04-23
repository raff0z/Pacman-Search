package it.uniroma3.giw.action;

import it.uniroma3.giw.model.DocumentIO;
import it.uniroma3.giw.model.DocumentResult;
import it.uniroma3.giw.model.SearchFiles;
import it.uniroma3.giw.model.SearchImages;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;


public class ActionImageQueryProcessing implements Action {
	
	@Override
	public String execute(HttpServletRequest request) {
		
		String query = request.getParameter("query");
		DocumentIO io = new DocumentIO();
		String indextPath = io.getImagesIndexPath();
		if (query != null){
			SearchImages searchImages;
			try {
				searchImages = new SearchImages(indextPath);
				
				String stringStart = (String) request.getParameter("start");
				
				DocumentResult[] hits;
				
				int start; 
				
				if((stringStart == null)){
				    start = 1;
				}else{
				    start = Integer.parseInt(stringStart);
				}
				
				query += "*";
				
				hits = searchImages.doSearch(query,start);
				    
				query = query.subSequence(0, query.length()-1).toString();
				
				int totalPages;
				
				int totalDoc = searchImages.getTotalDoc();
				
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
				return "resultsImage";
			} catch (ParseException | IOException e) {
				e.printStackTrace();
			}
		}

		return "error";
	}

}
