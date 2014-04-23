package it.uniroma3.giw.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import net.semanticmetadata.lire.DocumentBuilder;
import net.semanticmetadata.lire.ImageSearchHits;
import net.semanticmetadata.lire.ImageSearcher;
import net.semanticmetadata.lire.ImageSearcherFactory;

public class SearchImages {
	
	private DirectoryReader reader;
	private String indexPath;
	
	public SearchImages(String indexPath) throws IOException {
		this.indexPath = indexPath;
		this.reader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
	}
	
    public ImageSearchHits doSearch(String query)  {

    	BufferedImage img = null;
        
        if (indexPath.length() > 0) {
            File f = new File(indexPath);
            if (f.exists()) {
                try {
                    img = ImageIO.read(f);
                } catch (IOException e) {
                    e.printStackTrace();  
                }
            }
        }
        
        System.out.println(img); //é null

 
        ImageSearcher searcher = ImageSearcherFactory.createCEDDImageSearcher(10);
        
        ImageSearchHits hits = null;
        
        try {
			hits = searcher.search(img, reader);
			
			for (int i = 0; i < hits.length(); i++) {
	            String fileName = hits.doc(i).getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)[0];
	            System.out.println(hits.score(i) + ": \t" + fileName);
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return hits;
    }
}
