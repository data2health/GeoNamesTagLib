package edu.uiowa.slis.GeoNames.OnlineGamingAccount;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class OnlineGamingAccountPastProjectInverse extends edu.uiowa.slis.GeoNames.TagLibSupport {
	static OnlineGamingAccountPastProjectInverse currentInstance = null;
	private static final Log log = LogFactory.getLog(OnlineGamingAccountPastProjectInverse.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			OnlineGamingAccountPastProjectInverseIterator theOnlineGamingAccountPastProjectInverseIterator = (OnlineGamingAccountPastProjectInverseIterator)findAncestorWithClass(this, OnlineGamingAccountPastProjectInverseIterator.class);
			pageContext.getOut().print(theOnlineGamingAccountPastProjectInverseIterator.getPastProjectInverse());
		} catch (Exception e) {
			log.error("Can't find enclosing OnlineGamingAccount for pastProject tag ", e);
			throw new JspTagException("Error: Can't find enclosing OnlineGamingAccount for pastProject tag ");
		}
		return SKIP_BODY;
	}
}
