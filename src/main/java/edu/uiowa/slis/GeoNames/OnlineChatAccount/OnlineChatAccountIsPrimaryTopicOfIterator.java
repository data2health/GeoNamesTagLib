package edu.uiowa.slis.GeoNames.OnlineChatAccount;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

@SuppressWarnings("serial")
public class OnlineChatAccountIsPrimaryTopicOfIterator extends edu.uiowa.slis.GeoNames.TagLibSupport {
	static OnlineChatAccountIsPrimaryTopicOfIterator currentInstance = null;
	private static final Log log = LogFactory.getLog(OnlineChatAccountIsPrimaryTopicOfIterator.class);

	String subjectURI = null;
	String isPrimaryTopicOf = null;
	ResultSet rs = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			OnlineChatAccount ancestorInstance = (OnlineChatAccount) findAncestorWithClass(this, OnlineChatAccount.class);

			if (ancestorInstance != null) {
				subjectURI = ancestorInstance.getSubjectURI();
			}

			if (ancestorInstance == null && subjectURI == null) {
				throw new JspException("subject URI generation currently not supported");
			}

			rs = getResultSet(prefix+"SELECT ?s where { <" + subjectURI + "> <http://xmlns.com/foaf/0.1/isPrimaryTopicOf> ?s } ");
			if(rs.hasNext()) {
				QuerySolution sol = rs.nextSolution();
				isPrimaryTopicOf = sol.get("?s").toString();
				return EVAL_BODY_INCLUDE;
			}
		} catch (Exception e) {
			log.error("Exception raised in OnlineChatAccountIterator doStartTag", e);
			clearServiceState();
			freeConnection();
			throw new JspTagException("Exception raised in OnlineChatAccountIterator doStartTag");
		}

		return SKIP_BODY;
	}

	public int doAfterBody() throws JspException {
		try {
			if(rs.hasNext()) {
				QuerySolution sol = rs.nextSolution();
				isPrimaryTopicOf = sol.get("?s").toString();
				return EVAL_BODY_AGAIN;
			}
		} catch (Exception e) {
			log.error("Exception raised in OnlineChatAccountIterator doAfterBody", e);
			clearServiceState();
			freeConnection();
			throw new JspTagException("Exception raised in OnlineChatAccountIterator doAfterBody");
		}

		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			// do processing
		} catch (Exception e) {
			log.error("Exception raised in OnlineChatAccount doEndTag", e);
			throw new JspTagException("Exception raised in OnlineChatAccount doEndTag");
		} finally {
			clearServiceState();
			freeConnection();
		}

		return super.doEndTag();
	}

	private void clearServiceState() {
		subjectURI = null;
	}

	public void setIsPrimaryTopicOf(String isPrimaryTopicOf) {
		this.isPrimaryTopicOf = isPrimaryTopicOf;
	}

	public String getIsPrimaryTopicOf() {
		return isPrimaryTopicOf;
	}

}
