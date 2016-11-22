package edu.uiowa.slis.GeoNames.OnlineGamingAccount;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class OnlineGamingAccountTopic_interestInverseType extends edu.uiowa.slis.GeoNames.TagLibSupport {
	static OnlineGamingAccountTopic_interestInverseType currentInstance = null;
	private static final Log log = LogFactory.getLog(OnlineGamingAccountTopic_interestInverseType.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			OnlineGamingAccountTopic_interestInverseIterator theOnlineGamingAccountTopic_interestInverseIterator = (OnlineGamingAccountTopic_interestInverseIterator)findAncestorWithClass(this, OnlineGamingAccountTopic_interestInverseIterator.class);
			pageContext.getOut().print(theOnlineGamingAccountTopic_interestInverseIterator.getType());
		} catch (Exception e) {
			log.error("Can't find enclosing OnlineGamingAccount for topic_interest tag ", e);
			throw new JspTagException("Error: Can't find enclosing OnlineGamingAccount for topic_interest tag ");
		}
		return SKIP_BODY;
	}
}
