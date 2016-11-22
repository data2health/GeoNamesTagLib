package edu.uiowa.slis.GeoNames.Person;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PersonAlt extends edu.uiowa.slis.GeoNames.TagLibSupport {
	static PersonAlt currentInstance = null;
	private static final Log log = LogFactory.getLog(PersonAlt.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			PersonAltIterator thePerson = (PersonAltIterator)findAncestorWithClass(this, PersonAltIterator.class);
			pageContext.getOut().print(thePerson.getAlt());
		} catch (Exception e) {
			log.error("Can't find enclosing Person for alt tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for alt tag ");
		}
		return SKIP_BODY;
	}
}

