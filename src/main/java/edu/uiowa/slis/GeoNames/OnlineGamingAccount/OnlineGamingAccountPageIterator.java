package edu.uiowa.slis.GeoNames.OnlineGamingAccount;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

@SuppressWarnings("serial")
public class OnlineGamingAccountPageIterator extends edu.uiowa.slis.GeoNames.TagLibSupport {
	static OnlineGamingAccountPageIterator currentInstance = null;
	private static final Log log = LogFactory.getLog(OnlineGamingAccountPageIterator.class);

	String subjectURI = null;
	String page = null;
	ResultSet rs = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			OnlineGamingAccount ancestorInstance = (OnlineGamingAccount) findAncestorWithClass(this, OnlineGamingAccount.class);

			if (ancestorInstance != null) {
				subjectURI = ancestorInstance.getSubjectURI();
			}

			if (ancestorInstance == null && subjectURI == null) {
				throw new JspException("subject URI generation currently not supported");
			}

			rs = getResultSet(prefix+"SELECT ?s where { <" + subjectURI + "> <http://xmlns.com/foaf/0.1/page> ?s } ");
			if(rs.hasNext()) {
				QuerySolution sol = rs.nextSolution();
				page = sol.get("?s").toString();
				return EVAL_BODY_INCLUDE;
			}
		} catch (Exception e) {
			log.error("Exception raised in OnlineGamingAccountIterator doStartTag", e);
			clearServiceState();
			freeConnection();
			throw new JspTagException("Exception raised in OnlineGamingAccountIterator doStartTag");
		}

		return SKIP_BODY;
	}

	public int doAfterBody() throws JspException {
		try {
			if(rs.hasNext()) {
				QuerySolution sol = rs.nextSolution();
				page = sol.get("?s").toString();
				return EVAL_BODY_AGAIN;
			}
		} catch (Exception e) {
			log.error("Exception raised in OnlineGamingAccountIterator doAfterBody", e);
			clearServiceState();
			freeConnection();
			throw new JspTagException("Exception raised in OnlineGamingAccountIterator doAfterBody");
		}

		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			// do processing
		} catch (Exception e) {
			log.error("Exception raised in OnlineGamingAccount doEndTag", e);
			throw new JspTagException("Exception raised in OnlineGamingAccount doEndTag");
		} finally {
			clearServiceState();
			freeConnection();
		}

		return super.doEndTag();
	}

	private void clearServiceState() {
		subjectURI = null;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPage() {
		return page;
	}

}
