<%@page import="it.uniroma3.giw.model.AutoComplete"%>
<%@page import="org.apache.lucene.search.suggest.Lookup.LookupResult"%>
<%@page import="java.util.*"%>


<%
	String query = (String) request.getParameter("q");
	AutoComplete autoComplete = new AutoComplete();
	List<LookupResult> lookupRes = autoComplete.getAutoComplete(query);
	int length = lookupRes.size();
	String[] results = new String[length];
	for(int i = 0; i<length; i++) {
		//results[i] = lookupRes.get(i).key;
		out.print(lookupRes.get(i).key+"\n");
	}

	
	
	
%>