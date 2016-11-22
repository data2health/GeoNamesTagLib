package edu.uiowa.slis.GeoNames.PersonalProfileDocument;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PersonalProfileDocumentWorkplaceHomepageInverseType extends edu.uiowa.slis.GeoNames.TagLibSupport {
	static PersonalProfileDocumentWorkplaceHomepageInverseType currentInstance = null;
	private static final Log log = LogFactory.getLog(PersonalProfileDocumentWorkplaceHomepageInverseType.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			PersonalProfileDocumentWorkplaceHomepageInverseIterator thePersonalProfileDocumentWorkplaceHomepageInverseIterator = (PersonalProfileDocumentWorkplaceHomepageInverseIterator)findAncestorWithClass(this, PersonalProfileDocumentWorkplaceHomepageInverseIterator.class);
			pageContext.getOut().print(thePersonalProfileDocumentWorkplaceHomepageInverseIterator.getType());
		} catch (Exception e) {
			log.error("Can't find enclosing PersonalProfileDocument for workplaceHomepage tag ", e);
			throw new JspTagException("Error: Can't find enclosing PersonalProfileDocument for workplaceHomepage tag ");
		}
		return SKIP_BODY;
	}
}

