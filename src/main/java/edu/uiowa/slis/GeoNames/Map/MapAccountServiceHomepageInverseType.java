package edu.uiowa.slis.GeoNames.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class MapAccountServiceHomepageInverseType extends edu.uiowa.slis.GeoNames.TagLibSupport {
	static MapAccountServiceHomepageInverseType currentInstance = null;
	private static final Log log = LogFactory.getLog(MapAccountServiceHomepageInverseType.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			MapAccountServiceHomepageInverseIterator theMapAccountServiceHomepageInverseIterator = (MapAccountServiceHomepageInverseIterator)findAncestorWithClass(this, MapAccountServiceHomepageInverseIterator.class);
			pageContext.getOut().print(theMapAccountServiceHomepageInverseIterator.getType());
		} catch (Exception e) {
			log.error("Can't find enclosing Map for accountServiceHomepage tag ", e);
			throw new JspTagException("Error: Can't find enclosing Map for accountServiceHomepage tag ");
		}
		return SKIP_BODY;
	}
}

