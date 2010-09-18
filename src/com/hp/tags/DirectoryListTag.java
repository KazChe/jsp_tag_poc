package com.hp.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

public class DirectoryListTag extends SimpleTagSupport {

	private String path;
	private String suffix;
		
	@Override
	public void doTag() throws JspException, IOException {
	    // first of all, find the names of the files
	    Collection<String> files = findFiles();
	    if (files != null && !files.isEmpty()) {
	      String filename;
	      // now that the names have been found, iterate over each of them
	      // and invoke the body content (JspFragment)
	      Iterator<String> it = files.iterator();
	      while (it.hasNext()) {
	        filename = (String)it.next();
	        JspFragment jspFragment = getJspBody();
	        if (jspFragment != null) {
	        	getJspContext().setAttribute("filename", filename); // set the filename attribute here for the jsp to use.
	        	getJspContext().setAttribute("includedJSP", "/index.jsp"); // test for jsp:include
	        	jspFragment.invoke(getJspContext().getOut()); // calling the invoke will place the attribute value for filename and print the jsp/html content.
	        }
	      }
	    }
	  }

	@SuppressWarnings({ "unchecked" })
	private Collection<String> findFiles() {
		PageContext pageCtx = (PageContext)getJspContext();
		Collection<String> resources = pageCtx.getServletContext().getResourcePaths(path);
		List filteredResources = new ArrayList();
	    if (resources == null || resources.isEmpty()) {
	      return filteredResources;
	    }

	    Iterator it = resources.iterator();
	    String uri;
	    String testSuffix;
	    if (this.suffix != null) {
	      testSuffix = this.suffix;
	    } else {
	      testSuffix = ".jpg";
	    }
	    // now filter out those files that don't end in the suffix
	    while (it.hasNext()) {
	      uri = (String)it.next();
	      if (uri.endsWith(testSuffix)) {
	        filteredResources.add(uri);
	      }
	    }
		return resources;
	}

	/*
	 * tag's attribute(s) setters.
	*/
	public void setPath(String path) {
		this.path = path;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}
