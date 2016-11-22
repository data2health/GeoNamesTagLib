package edu.uiowa.slis.GeoNames.Concept;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class ConceptStatusInverseType extends edu.uiowa.slis.GeoNames.TagLibSupport {
	static ConceptStatusInverseType currentInstance = null;
	private static final Log log = LogFactory.getLog(ConceptStatusInverseType.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			ConceptStatusInverseIterator theConceptStatusInverseIterator = (ConceptStatusInverseIterator)findAncestorWithClass(this, ConceptStatusInverseIterator.class);
			pageContext.getOut().print(theConceptStatusInverseIterator.getType());
		} catch (Exception e) {
			log.error("Can't find enclosing Concept for status tag ", e);
			throw new JspTagException("Error: Can't find enclosing Concept for status tag ");
		}
		return SKIP_BODY;
	}
}
