package edu.uiowa.slis.GeoNames.OnlineEcommerceAccount;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class OnlineEcommerceAccountTopic_interestInverseType extends edu.uiowa.slis.GeoNames.TagLibSupport {
	static OnlineEcommerceAccountTopic_interestInverseType currentInstance = null;
	private static final Log log = LogFactory.getLog(OnlineEcommerceAccountTopic_interestInverseType.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			OnlineEcommerceAccountTopic_interestInverseIterator theOnlineEcommerceAccountTopic_interestInverseIterator = (OnlineEcommerceAccountTopic_interestInverseIterator)findAncestorWithClass(this, OnlineEcommerceAccountTopic_interestInverseIterator.class);
			pageContext.getOut().print(theOnlineEcommerceAccountTopic_interestInverseIterator.getType());
		} catch (Exception e) {
			log.error("Can't find enclosing OnlineEcommerceAccount for topic_interest tag ", e);
			throw new JspTagException("Error: Can't find enclosing OnlineEcommerceAccount for topic_interest tag ");
		}
		return SKIP_BODY;
	}
}
