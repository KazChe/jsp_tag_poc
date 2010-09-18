package com.hp.tags;
import java.io.IOException;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;


public class BaseTagHandler extends SimpleTagSupport {

	private String nameAttrValue;
	
	public BaseTagHandler() {
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		super.doTag();
			getJspContext().getOut().write("<font color=\"blue\"><b> - " + nameAttrValue + " - " + "</b></font>" );
//			System.out.println(getJspBody());
			getJspContext().setAttribute("myMessage", "Hey there, I am your message.");
			//getJspContext().setAttribute("date", new Date());
			JspFragment jspFragment = getJspBody();
			jspFragment.invoke(getJspContext().getOut());
	}
	
	@SuppressWarnings("unused")
	private String displayDateTime() {		
		return new Date().toString();
	}
	
	private boolean isAttributeAvailable(Object obj) {
		return ((getJspContext().findAttribute(obj.toString())) != null);
	}
	
	@SuppressWarnings("unused")
	private Object getTagAttribute(Object obj) {
		Object value = null;
		if (isAttributeAvailable(obj)) {
			value = getJspContext().findAttribute(obj.toString());
		}
		return value;
	}

	public void setName(String name) {
		this.nameAttrValue = name;
	}
}