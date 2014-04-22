package it.uniroma3.giw.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.spell.Dictionary;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.tst.TSTLookup;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class AutoComplete{
	
	Lookup lookup;
	Dictionary dictionary;
	
	public AutoComplete() throws IOException{
		this.lookup = new TSTLookup();
		
		Directory indexPathDir = FSDirectory.open(new File(new DocumentIO().getIndexPath()));
		IndexReader ir = DirectoryReader.open(indexPathDir);
		dictionary = new LuceneDictionary(ir, "contents");
	}

	public List<LookupResult> getAutoComplete(String query) {
		Lookup lookup = new TSTLookup();
		try {
			lookup.build(dictionary);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<LookupResult> resultsList = lookup.lookup(query, true, 3);		
		return resultsList;
	}

}
