package edu.uiowa.slis.GeoNames.Agent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class AgentTipjar extends edu.uiowa.slis.GeoNames.TagLibSupport {
	static AgentTipjar currentInstance = null;
	private static final Log log = LogFactory.getLog(AgentTipjar.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			AgentTipjarIterator theAgent = (AgentTipjarIterator)findAncestorWithClass(this, AgentTipjarIterator.class);
			pageContext.getOut().print(theAgent.getTipjar());
		} catch (Exception e) {
			log.error("Can't find enclosing Agent for tipjar tag ", e);
			throw new JspTagException("Error: Can't find enclosing Agent for tipjar tag ");
		}
		return SKIP_BODY;
	}
}

