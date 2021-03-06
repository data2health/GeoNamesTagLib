package edu.uiowa.slis.GeoNames;

import javax.servlet.http.HttpServletRequest;

import java.util.Hashtable;
import java.util.Properties;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.Syntax;
import org.apache.jena.tdb.TDBFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.tagUtil.property.PropertyLoader;

@SuppressWarnings("serial")
public class TagLibSupport extends javax.servlet.jsp.tagext.TagSupport {
	private static final Log log = LogFactory.getLog(TagLibSupport.class);
	static protected String prefix = 
		"PREFIX ld4l: <http://bib.ld4l.org/ontology/>"
			+ "PREFIX ld4lcornell: <http://draft.ld4l.org/cornell/>"
			+ "PREFIX madsrdf: <http://www.loc.gov/mads/rdf/v1#>"
			+ "PREFIX oa: <http://www.w3.org/ns/oa#>"
			+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "PREFIX void: <http://rdfs.org/ns/void#>"
			+ "PREFIX worldcat: <http://www.worldcat.org/oclc/>"
			+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";
	static Hashtable<String, String> classNameHash = new Hashtable<String, String>();
	static Hashtable<String, String> classLocalNameHash = new Hashtable<String, String>();

	static {
		classNameHash.put("http://www.w3.org/2002/07/owl#SymmetricProperty", "SymmetricProperty");
		classNameHash.put("http://xmlns.com/foaf/0.1/Organization", "Organization");
		classNameHash.put("http://www.w3.org/2002/07/owl#TransitiveProperty", "TransitiveProperty");
		classNameHash.put("http://www.w3.org/2002/07/owl#Restriction", "Restriction");
		classNameHash.put("http://www.geonames.org/ontology#Feature", "Feature");
		classNameHash.put("http://www.w3.org/2002/07/owl#ObjectProperty", "ObjectProperty");
		classNameHash.put("http://www.geonames.org/ontology#Code", "Code");
		classNameHash.put("http://www.geonames.org/ontology#Class", "Class");
		classNameHash.put("http://xmlns.com/foaf/0.1/Person", "Person");
		classNameHash.put("http://www.w3.org/2002/07/owl#DatatypeProperty", "DatatypeProperty");
		classNameHash.put("http://www.w3.org/2002/07/owl#Ontology", "Ontology");

		classLocalNameHash.put("SymmetricProperty", "http://www.w3.org/2002/07/owl#SymmetricProperty");
		classLocalNameHash.put("Organization", "http://xmlns.com/foaf/0.1/Organization");
		classLocalNameHash.put("TransitiveProperty", "http://www.w3.org/2002/07/owl#TransitiveProperty");
		classLocalNameHash.put("Restriction", "http://www.w3.org/2002/07/owl#Restriction");
		classLocalNameHash.put("Feature", "http://www.geonames.org/ontology#Feature");
		classLocalNameHash.put("ObjectProperty", "http://www.w3.org/2002/07/owl#ObjectProperty");
		classLocalNameHash.put("Code", "http://www.geonames.org/ontology#Code");
		classLocalNameHash.put("Class", "http://www.geonames.org/ontology#Class");
		classLocalNameHash.put("Person", "http://xmlns.com/foaf/0.1/Person");
		classLocalNameHash.put("DatatypeProperty", "http://www.w3.org/2002/07/owl#DatatypeProperty");
		classLocalNameHash.put("Ontology", "http://www.w3.org/2002/07/owl#Ontology");
	}

	boolean useSPARQL = true;
	Dataset dataset = null;
	String tripleStore = null;
	String endpoint = null;
	Properties properties = null;

	private void loadProperties() {
		if (properties != null)
			return;

		String theURI = ((HttpServletRequest)pageContext.getRequest()).getRequestURI();
		String applicationRoot = theURI.substring(0, theURI.indexOf('/', 1));
		log.info("loading " + applicationRoot + ".properties");
		properties = PropertyLoader.loadProperties(applicationRoot + ".properties");
		useSPARQL = properties.getProperty("useSPARQL", "true").equals("true");
		endpoint = properties.getProperty("endpoint", "localhost");
		tripleStore = properties.getProperty("tripleStore", "localhost");

	}


	public ResultSet getResultSet(String queryString) {
		loadProperties();
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

	public void freeConnection() {
		if (!useSPARQL) {
			//dataset.close();
		}
	}

	public String getLocalName(String classString) {
		if (classString == null) {
			return null;
		}
		return classNameHash.get(classString);
	}

}
