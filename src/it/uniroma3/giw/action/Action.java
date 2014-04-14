package it.uniroma3.giw.action;

import javax.servlet.http.HttpServletRequest;

public interface Action {
	
	public String execute(HttpServletRequest request);
	
}
