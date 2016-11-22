package edu.uiowa.slis.GeoNames.Feature;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

@SuppressWarnings("serial")
public class FeatureParentCountryIterator extends edu.uiowa.slis.GeoNames.TagLibSupport {
	static FeatureParentCountryIterator currentInstance = null;
	private static final Log log = LogFactory.getLog(FeatureParentCountryIterator.class);

	String subjectURI = null;
	String parentCountry = null;
	ResultSet rs = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Feature ancestorInstance = (Feature) findAncestorWithClass(this, Feature.class);

			if (ancestorInstance != null) {
				subjectURI = ancestorInstance.getSubjectURI();
			}

			if (ancestorInstance == null && subjectURI == null) {
				throw new JspException("subject URI generation currently not supported");
			}

			rs = getResultSet(prefix+"SELECT ?s where { <" + subjectURI + "> <http://www.geonames.org/ontology#parentCountry> ?s } ");
			if(rs.hasNext()) {
				QuerySolution sol = rs.nextSolution();
				parentCountry = sol.get("?s").toString();
				return EVAL_BODY_INCLUDE;
			}
		} catch (Exception e) {
			log.error("Exception raised in FeatureIterator doStartTag", e);
			clearServiceState();
			freeConnection();
			throw new JspTagException("Exception raised in FeatureIterator doStartTag");
		}

		return SKIP_BODY;
	}

	public int doAfterBody() throws JspException {
		try {
			if(rs.hasNext()) {
				QuerySolution sol = rs.nextSolution();
				parentCountry = sol.get("?s").toString();
				return EVAL_BODY_AGAIN;
			}
		} catch (Exception e) {
			log.error("Exception raised in FeatureIterator doAfterBody", e);
			clearServiceState();
			freeConnection();
			throw new JspTagException("Exception raised in FeatureIterator doAfterBody");
		}

		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			// do processing
		} catch (Exception e) {
			log.error("Exception raised in Feature doEndTag", e);
			throw new JspTagException("Exception raised in Feature doEndTag");
		} finally {
			clearServiceState();
			freeConnection();
		}

		return super.doEndTag();
	}

	private void clearServiceState() {
		subjectURI = null;
	}

	public void setParentCountry(String parentCountry) {
		this.parentCountry = parentCountry;
	}

	public String getParentCountry() {
		return parentCountry;
	}

}