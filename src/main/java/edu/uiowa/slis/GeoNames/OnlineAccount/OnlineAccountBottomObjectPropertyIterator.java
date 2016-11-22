package edu.uiowa.slis.GeoNames.OnlineAccount;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

@SuppressWarnings("serial")
public class OnlineAccountBottomObjectPropertyIterator extends edu.uiowa.slis.GeoNames.TagLibSupport {
	static OnlineAccountBottomObjectPropertyIterator currentInstance = null;
	private static final Log log = LogFactory.getLog(OnlineAccountBottomObjectPropertyIterator.class);

	String subjectURI = null;
	String bottomObjectProperty = null;
	ResultSet rs = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			OnlineAccount ancestorInstance = (OnlineAccount) findAncestorWithClass(this, OnlineAccount.class);

			if (ancestorInstance != null) {
				subjectURI = ancestorInstance.getSubjectURI();
			}

			if (ancestorInstance == null && subjectURI == null) {
				throw new JspException("subject URI generation currently not supported");
			}

			rs = getResultSet(prefix+"SELECT ?s where { <" + subjectURI + "> <http://www.w3.org/2002/07/owl#bottomObjectProperty> ?s } ");
			if(rs.hasNext()) {
				QuerySolution sol = rs.nextSolution();
				bottomObjectProperty = sol.get("?s").toString();
				return EVAL_BODY_INCLUDE;
			}
		} catch (Exception e) {
			log.error("Exception raised in OnlineAccountIterator doStartTag", e);
			clearServiceState();
			freeConnection();
			throw new JspTagException("Exception raised in OnlineAccountIterator doStartTag");
		}

		return SKIP_BODY;
	}

	public int doAfterBody() throws JspException {
		try {
			if(rs.hasNext()) {
				QuerySolution sol = rs.nextSolution();
				bottomObjectProperty = sol.get("?s").toString();
				return EVAL_BODY_AGAIN;
			}
		} catch (Exception e) {
			log.error("Exception raised in OnlineAccountIterator doAfterBody", e);
			clearServiceState();
			freeConnection();
			throw new JspTagException("Exception raised in OnlineAccountIterator doAfterBody");
		}

		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			// do processing
		} catch (Exception e) {
			log.error("Exception raised in OnlineAccount doEndTag", e);
			throw new JspTagException("Exception raised in OnlineAccount doEndTag");
		} finally {
			clearServiceState();
			freeConnection();
		}

		return super.doEndTag();
	}

	private void clearServiceState() {
		subjectURI = null;
	}

	public void setBottomObjectProperty(String bottomObjectProperty) {
		this.bottomObjectProperty = bottomObjectProperty;
	}

	public String getBottomObjectProperty() {
		return bottomObjectProperty;
	}

}
