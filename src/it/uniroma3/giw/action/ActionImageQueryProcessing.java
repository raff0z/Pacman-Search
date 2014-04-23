package it.uniroma3.giw.action;

import it.uniroma3.giw.model.DocumentIO;
import it.uniroma3.giw.model.SearchImages;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.semanticmetadata.lire.ImageSearchHits;


public class ActionImageQueryProcessing implements Action {

	@Override
	public String execute(HttpServletRequest request) {
		
		String query = request.getParameter("query");
		
		DocumentIO io = new DocumentIO();
		
		String indexImagePath = io.getImagesIndexPath();
		
		if (query != null){
			SearchImages searchImages;
			
			try {
				searchImages = new SearchImages(indexImagePath);
								
				ImageSearchHits hits;
								
				hits = searchImages.doSearch(query);	
				
				request.setAttribute("query", query);
				request.setAttribute("results", hits);
				
				return "results";
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return "error";
	}

}
