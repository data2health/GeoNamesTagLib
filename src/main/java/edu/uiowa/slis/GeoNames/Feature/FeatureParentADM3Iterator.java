package edu.uiowa.slis.GeoNames.Feature;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

import java.util.Hashtable;

@SuppressWarnings("serial")
public class FeatureParentADM3Iterator extends edu.uiowa.slis.GeoNames.TagLibSupport {
	static FeatureParentADM3Iterator currentInstance = null;
	private static final Log log = LogFactory.getLog(FeatureParentADM3Iterator.class);

	static boolean firstInstance = false;
	static boolean lastInstance = false;

	String subjectURI = null;
	String type = null;
	String parentADM3 = null;
	ResultSet rs = null;
	Hashtable<String,String> classFilter = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Feature theFeature = (Feature) findAncestorWithClass(this, Feature.class);

			if (theFeature != null) {
				subjectURI = theFeature.getSubjectURI();
			}

			if (theFeature == null && subjectURI == null) {
				throw new JspException("subject URI generation currently not supported");
			}

			rs = getResultSet(prefix+"SELECT ?s ?t where {"
					+" <" + subjectURI + "> <http://www.geonames.org/ontology#parentADM3> ?s . "
					+" OPTIONAL { ?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?t } ."
					+" FILTER NOT EXISTS {"
					+"   ?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?subtype ."
					+"   ?subtype <http://www.w3.org/2000/01/rdf-schema#subClassOf> ?t ."
					+"   filter ( ?subtype != ?t )"
					+" }"
					+"} ");
			while(rs.hasNext()) {
				QuerySolution sol = rs.nextSolution();
				parentADM3 = sol.get("?s").toString();
				type = sol.get("?t") == null ? null : getLocalName(sol.get("?t").toString());
//				if (type == null)
//					continue;
				if (classFilter == null || (classFilter != null && type != null && classFilter.containsKey(type))) {
					log.info("instance: " + parentADM3 + "	type: " + type);
					firstInstance = true;
					lastInstance = ! rs.hasNext();
					return EVAL_BODY_INCLUDE;
				}
			}
		} catch (Exception e) {
			log.error("Exception raised in FeatureParentADM3Iterator doStartTag", e);
			clearServiceState();
			freeConnection();
			throw new JspTagException("Exception raised in FeatureParentADM3Iterator doStartTag");
		}

		return SKIP_BODY;
	}

	public int doAfterBody() throws JspException {
		try {
			while(rs.hasNext()) {
				QuerySolution sol = rs.nextSolution();
				parentADM3 = sol.get("?s").toString();
				type = sol.get("?t") == null ? null : getLocalName(sol.get("?t").toString());
//				if (type == null)
//					continue;
				if (classFilter == null || (classFilter != null && type != null && classFilter.containsKey(type))) {
					log.info("instance: " + parentADM3 + "	type: " + type);
					firstInstance = false;
					lastInstance = ! rs.hasNext();
					return EVAL_BODY_AGAIN;
				}
			}
		} catch (Exception e) {
			log.error("Exception raised in FeatureParentADM3Iterator doAfterBody", e);
			clearServiceState();
			freeConnection();
			throw new JspTagException("Exception raised in FeatureParentADM3Iterator doAfterBody");
		}

		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			// do processing
		} catch (Exception e) {
			log.error("Exception raised in FeatureParentADM3 doEndTag", e);
			throw new JspTagException("Exception raised in FeatureParentADM3 doEndTag");
		} finally {
			clearServiceState();
			freeConnection();
		}

		return super.doEndTag();
	}

	private void clearServiceState() {
		subjectURI = null;
		type = null;
		parentADM3 = null;
		classFilter = null;
	}

	public  void setType(String theType) {
		type = theType;
	}

	public  String getType() {
		return type;
	}

	public  void setParentADM3(String theParentADM3) {
		parentADM3 = theParentADM3;
	}

	public  String getParentADM3() {
		return parentADM3;
	}

	public static void setFirstInstance(Boolean theFirstInstance) {
		firstInstance = theFirstInstance;
	}

	public static Boolean getFirstInstance() {
		return firstInstance;
	}

	public static void setLastInstance(Boolean theLastInstance) {
		lastInstance = theLastInstance;
	}

	public static Boolean getLastInstance() {
		return lastInstance;
	}

	public void setClassFilter(String filterString) {
		String[] classFilterArray = filterString.split(" ");
		this.classFilter = new Hashtable<String, String>();
		for (String filterClass : classFilterArray) {
			log.info("adding filterClass " + filterClass + " to FeatureParentADM3Iterator");
			classFilter.put(filterClass, "");
		}
	}

	public String getClassFilter() {
		return classFilter.toString();
	}

}
