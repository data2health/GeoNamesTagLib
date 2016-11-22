package edu.uiowa.slis.GeoNames.Person;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class PersonWeblog extends edu.uiowa.slis.GeoNames.TagLibSupport {
	static PersonWeblog currentInstance = null;
	private static final Log log = LogFactory.getLog(PersonWeblog.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			PersonWeblogIterator thePerson = (PersonWeblogIterator)findAncestorWithClass(this, PersonWeblogIterator.class);
			pageContext.getOut().print(thePerson.getWeblog());
		} catch (Exception e) {
			log.error("Can't find enclosing Person for weblog tag ", e);
			throw new JspTagException("Error: Can't find enclosing Person for weblog tag ");
		}
		return SKIP_BODY;
	}
}
