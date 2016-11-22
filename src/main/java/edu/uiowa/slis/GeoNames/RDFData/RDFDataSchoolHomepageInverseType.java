package edu.uiowa.slis.GeoNames.RDFData;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class RDFDataSchoolHomepageInverseType extends edu.uiowa.slis.GeoNames.TagLibSupport {
	static RDFDataSchoolHomepageInverseType currentInstance = null;
	private static final Log log = LogFactory.getLog(RDFDataSchoolHomepageInverseType.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			RDFDataSchoolHomepageInverseIterator theRDFDataSchoolHomepageInverseIterator = (RDFDataSchoolHomepageInverseIterator)findAncestorWithClass(this, RDFDataSchoolHomepageInverseIterator.class);
			pageContext.getOut().print(theRDFDataSchoolHomepageInverseIterator.getType());
		} catch (Exception e) {
			log.error("Can't find enclosing RDFData for schoolHomepage tag ", e);
			throw new JspTagException("Error: Can't find enclosing RDFData for schoolHomepage tag ");
		}
		return SKIP_BODY;
	}
}
