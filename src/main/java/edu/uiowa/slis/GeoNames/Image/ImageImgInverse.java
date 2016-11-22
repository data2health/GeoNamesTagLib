package edu.uiowa.slis.GeoNames.Image;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

@SuppressWarnings("serial")
public class ImageImgInverse extends edu.uiowa.slis.GeoNames.TagLibSupport {
	static ImageImgInverse currentInstance = null;
	private static final Log log = LogFactory.getLog(ImageImgInverse.class);

	// object property

	public int doStartTag() throws JspException {
		try {
			ImageImgInverseIterator theImageImgInverseIterator = (ImageImgInverseIterator)findAncestorWithClass(this, ImageImgInverseIterator.class);
			pageContext.getOut().print(theImageImgInverseIterator.getImgInverse());
		} catch (Exception e) {
			log.error("Can't find enclosing Image for img tag ", e);
			throw new JspTagException("Error: Can't find enclosing Image for img tag ");
		}
		return SKIP_BODY;
	}
}
