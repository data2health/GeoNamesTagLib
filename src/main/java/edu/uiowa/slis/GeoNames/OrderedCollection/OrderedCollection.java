package edu.uiowa.slis.GeoNames.OrderedCollection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

@SuppressWarnings("serial")
public class OrderedCollection extends edu.uiowa.slis.GeoNames.TagLibSupport {
	static OrderedCollection currentInstance = null;
	private static final Log log = LogFactory.getLog(OrderedCollection.class);

	// 'standard' properties

	String subjectURI = null;
	String label = null;
	boolean commitNeeded = false;

	// functional datatype properties, both local and inherited

	String memberList = null;
	String member = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			OrderedCollectionIterator theOrderedCollectionIterator = (OrderedCollectionIterator) findAncestorWithClass(this, OrderedCollectionIterator.class);

			if (theOrderedCollectionIterator != null) {
				subjectURI = theOrderedCollectionIterator.getSubjectURI();
				label = theOrderedCollectionIterator.getLabel();
			}

			if (this.getParent() instanceof edu.uiowa.slis.GeoNames.List.ListMemberListInverseIterator) {
				subjectURI = ((edu.uiowa.slis.GeoNames.List.ListMemberListInverseIterator)this.getParent()).getMemberListInverse();
			}

			if (theOrderedCollectionIterator == null && subjectURI == null) {
				throw new JspException("subject URI generation currently not supported");
			} else {
				ResultSet rs = getResultSet(prefix
				+ " SELECT ?label ?foafName ?schemaName ?rdfValue  ?memberList ?member where {"
				+ "  OPTIONAL { <" + subjectURI + "> rdfs:label ?label } "
				+ "  OPTIONAL { <" + subjectURI + "> <http://xmlns.com/foaf/0.1/name> ?foafName } "
				+ "  OPTIONAL { <" + subjectURI + "> <http://schema.org/name> ?schemaName } "
				+ "  OPTIONAL { <" + subjectURI + "> <http://www.w3.org/1999/02/22-rdf-syntax-ns#value> ?rdfValue } "
				+ "  OPTIONAL { <" + subjectURI + "> <http://www.w3.org/2004/02/skos/core#memberList> ?memberList } "
				+ "  OPTIONAL { <" + subjectURI + "> <http://www.w3.org/2004/02/skos/core#member> ?member } "
				+ "}");
				while(rs.hasNext()) {
					QuerySolution sol = rs.nextSolution();
					label = sol.get("?label") == null ? null : sol.get("?label").asLiteral().getString();
					if (label == null)
						label = sol.get("?foafName") == null ? null : sol.get("?foafName").asLiteral().getString();
					if (label == null)
						label = sol.get("?schemaName") == null ? null : sol.get("?schemaName").asLiteral().getString();
					if (label == null)
						label = sol.get("?rdfValue") == null ? null : sol.get("?rdfValue").asLiteral().getString();
					memberList = sol.get("?memberList") == null ? null : sol.get("?memberList").toString();
					member = sol.get("?member") == null ? null : sol.get("?member").toString();
				}
			}
		} catch (Exception e) {
			log.error("Exception raised in OrderedCollection doStartTag", e);
			throw new JspTagException("Exception raised in OrderedCollection doStartTag");
		} finally {
			freeConnection();
		}

		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			// do processing
		} catch (Exception e) {
			log.error("Exception raised in OrderedCollection doEndTag", e);
			throw new JspTagException("Exception raised in OrderedCollection doEndTag");
		} finally {
			clearServiceState();
			freeConnection();
		}

		return super.doEndTag();
	}

	private void clearServiceState() {
		subjectURI = null;
	}

	public void setSubjectURI(String subjectURI) {
		this.subjectURI = subjectURI;
	}

	public String getSubjectURI() {
		return subjectURI;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setMemberList(String memberList) {
		this.memberList = memberList;
	}

	public String getMemberList() {
		return memberList;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getMember() {
		return member;
	}

}
