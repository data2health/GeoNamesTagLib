package edu.uiowa.slis.GeoNames.utility;

import java.io.File;
import java.io.IOException;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.Syntax;
import org.apache.jena.tdb.TDBFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;

import edu.uiowa.slis.GeoNames.TagLibSupport;

public class Indexer {
    protected static Logger logger = Logger.getLogger(Indexer.class);
    static TagLibSupport theTag = new TagLibSupport();
    
    static boolean useSPARQL = false;
    static Dataset dataset = null;
    static String tripleStore = null;
    static String endpoint = null;

    static String dataPath = "/Volumes/LD4L/";
    static String lucenePath = "/Volumes/LD4L/lucene/geonames/feature";
    static String prefix = 
	    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
	    + " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
	    + " PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
	    + " PREFIX mads: <http://www.loc.gov/mads/rdf/v1#> "
	    + " PREFIX skos: <http://www.w3.org/2004/02/skos/core#> "
	    + " PREFIX bib: <http://bib.ld4l.org/ontology/> ";


    
    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws CorruptIndexException, LockObtainFailedException, IOException {
	PropertyConfigurator.configure("log4j.info");

	tripleStore = dataPath + "geonames";
	endpoint = "http://guardian.slis.uiowa.edu:3030/geonames/sparql";

	IndexWriter theWriter = new IndexWriter(FSDirectory.open(new File(lucenePath)), new StandardAnalyzer(org.apache.lucene.util.Version.LUCENE_30), true, IndexWriter.MaxFieldLength.UNLIMITED);

	indexFeatures(theWriter);

	logger.info("optimizing index...");
	theWriter.optimize();
	theWriter.close();
    }
    
    static void indexFeatures(IndexWriter theWriter) throws CorruptIndexException, IOException {
	int count = 0;
	String query =
		" SELECT ?s ?lab where { "+
		"  ?s rdf:type <http://www.geonames.org/ontology#Feature> . "+
		"  OPTIONAL { ?s rdfs:label ?labelUS  FILTER (lang(?labelUS) = \"en-US\") } "+
		"  OPTIONAL { ?s rdfs:label ?labelENG FILTER (langMatches(?labelENG,\"en\")) } "+
		"  OPTIONAL { ?s rdfs:label ?label    FILTER (lang(?label) = \"\") } "+
		"  OPTIONAL { ?s rdfs:label ?labelANY FILTER (lang(?labelANY) != \"\") } "+
		"  OPTIONAL { ?s <http://www.geonames.org/ontology#name> ?altLabel } "+
		"  BIND(COALESCE(?labelUS, ?labelENG, ?label, ?labelANY , ?altLabel) as ?lab) "+
		"}";
	ResultSet rs = getResultSet(prefix + query);
	while (rs.hasNext()) {
	    QuerySolution sol = rs.nextSolution();
	    String address = sol.get("?s").toString();
	    String name = sol.get("?lab").toString();
	    logger.debug("address: " + address + "\ttitle: " + name);
	    
	    Document theDocument = new Document();
	    theDocument.add(new Field("uri", address, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("name", name, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    theDocument.add(new Field("content", name, Field.Store.NO, Field.Index.ANALYZED));
	    
	    indexVariant(theDocument, address, "alternateName");
	    indexVariant(theDocument, address, "officialName");
	    indexVariant2(theDocument, address, "parentCountry");
	    indexVariant2(theDocument, address, "parentFeature");
	    indexVariant2(theDocument, address, "parentADM1");
	    indexVariant2(theDocument, address, "parentADM2");
	    indexVariant2(theDocument, address, "parentADM3");
	    indexVariant2(theDocument, address, "parentADM4");
	    
	    theWriter.addDocument(theDocument);
	    count++;
	    if (count % 10000 == 0)
		logger.info("count: " + count);
	}
	logger.info("total titles: " + count);
    }
    
    static void indexVariant(Document theDocument, String uri, String className) throws CorruptIndexException, IOException {
	String query =
		"SELECT DISTINCT ?name WHERE { "
		+ "<" + uri + "> <http://www.geonames.org/ontology#" + className + "> ?name . "
    		+ "}";
	ResultSet rs = getResultSet(prefix + query);
	while (rs.hasNext()) {
	    QuerySolution sol = rs.nextSolution();
	    String name = sol.get("?name").toString();
	    logger.debug("\tclassName: " + className + "\tname: " + name);
	    
	    theDocument.add(new Field("content", name, Field.Store.NO, Field.Index.ANALYZED));
	}
    }
    
    static void indexVariant2(Document theDocument, String uri, String className) throws CorruptIndexException, IOException {
	String query =
		"SELECT DISTINCT ?name WHERE { "
		+ "<" + uri + "> <http://www.geonames.org/ontology#" + className + "> ?v . "
		+ "?v <http://www.geonames.org/ontology#name> ?name . "
		    		+ "}";
	ResultSet rs = getResultSet(prefix + query);
	while (rs.hasNext()) {
	    QuerySolution sol = rs.nextSolution();
	    String name = sol.get("?name").toString();
	    logger.debug("\tclassName: " + className + "\tname: " + name);
	    
	    theDocument.add(new Field("content", name, Field.Store.NO, Field.Index.ANALYZED));
	}
    }

    static public ResultSet getResultSet(String queryString) {
	if (useSPARQL) {
	    Query theClassQuery = QueryFactory.create(queryString, Syntax.syntaxARQ);
	    QueryExecution theClassExecution = QueryExecutionFactory.sparqlService(endpoint, theClassQuery);
	    return theClassExecution.execSelect();
	} else {
	    dataset = TDBFactory.createDataset(tripleStore);
	    Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
	    QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
	    return qexec.execSelect();
	}
    }
}
