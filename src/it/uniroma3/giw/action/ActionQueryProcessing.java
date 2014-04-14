package it.uniroma3.giw.action;

import it.uniroma3.giw.model.DocumentIO;
import it.uniroma3.giw.model.DocumentResult;
import it.uniroma3.giw.model.SearchFiles;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;


import org.apache.lucene.queryparser.classic.ParseException;

public class ActionQueryProcessing implements Action {

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
				// String[] didYouMean = searchFiles.getDidYouMean(query);






				// request.setAttribute("did you mean", didYouMean[0]);
				request.setAttribute("results", hits);
				return "results";
			} catch (ParseException | IOException e) {
				e.printStackTrace();
			}
		}

		return "error";
	}

}
