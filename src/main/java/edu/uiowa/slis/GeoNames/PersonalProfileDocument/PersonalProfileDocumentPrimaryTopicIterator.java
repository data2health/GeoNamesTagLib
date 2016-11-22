package edu.uiowa.slis.GeoNames.PersonalProfileDocument;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

@SuppressWarnings("serial")
public class PersonalProfileDocumentPrimaryTopicIterator extends edu.uiowa.slis.GeoNames.TagLibSupport {
	static PersonalProfileDocumentPrimaryTopicIterator currentInstance = null;
	private static final Log log = LogFactory.getLog(PersonalProfileDocumentPrimaryTopicIterator.class);

	String subjectURI = null;
	String primaryTopic = null;
	ResultSet rs = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			PersonalProfileDocument ancestorInstance = (PersonalProfileDocument) findAncestorWithClass(this, PersonalProfileDocument.class);

			if (ancestorInstance != null) {
				subjectURI = ancestorInstance.getSubjectURI();
			}

			if (ancestorInstance == null && subjectURI == null) {
				throw new JspException("subject URI generation currently not supported");
			}

			rs = getResultSet(prefix+"SELECT ?s where { <" + subjectURI + "> <http://xmlns.com/foaf/0.1/primaryTopic> ?s } ");
			if(rs.hasNext()) {
				QuerySolution sol = rs.nextSolution();
				primaryTopic = sol.get("?s").toString();
				return EVAL_BODY_INCLUDE;
			}
		} catch (Exception e) {
			log.error("Exception raised in PersonalProfileDocumentIterator doStartTag", e);
			clearServiceState();
			freeConnection();
			throw new JspTagException("Exception raised in PersonalProfileDocumentIterator doStartTag");
		}

		return SKIP_BODY;
	}

	public int doAfterBody() throws JspException {
		try {
			if(rs.hasNext()) {
				QuerySolution sol = rs.nextSolution();
				primaryTopic = sol.get("?s").toString();
				return EVAL_BODY_AGAIN;
			}
		} catch (Exception e) {
			log.error("Exception raised in PersonalProfileDocumentIterator doAfterBody", e);
			clearServiceState();
			freeConnection();
			throw new JspTagException("Exception raised in PersonalProfileDocumentIterator doAfterBody");
		}

		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			// do processing
		} catch (Exception e) {
			log.error("Exception raised in PersonalProfileDocument doEndTag", e);
			throw new JspTagException("Exception raised in PersonalProfileDocument doEndTag");
		} finally {
			clearServiceState();
			freeConnection();
		}

		return super.doEndTag();
	}

	private void clearServiceState() {
		subjectURI = null;
	}

	public void setPrimaryTopic(String primaryTopic) {
		this.primaryTopic = primaryTopic;
	}

	public String getPrimaryTopic() {
		return primaryTopic;
	}

}
