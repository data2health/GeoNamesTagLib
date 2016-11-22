package edu.uiowa.slis.GeoNames.Group;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class GroupAimChatID extends edu.uiowa.slis.GeoNames.TagLibSupport {
	static GroupAimChatID currentInstance = null;
	private static final Log log = LogFactory.getLog(GroupAimChatID.class);

	// non-functional property

	public int doStartTag() throws JspException {
		try {
			GroupAimChatIDIterator theGroup = (GroupAimChatIDIterator)findAncestorWithClass(this, GroupAimChatIDIterator.class);
			pageContext.getOut().print(theGroup.getAimChatID());
		} catch (Exception e) {
			log.error("Can't find enclosing Group for aimChatID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Group for aimChatID tag ");
		}
		return SKIP_BODY;
	}
}
