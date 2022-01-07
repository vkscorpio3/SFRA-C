////////////////////////////////////////////////////////////////////////
//
// CommandStore.java
//
// This file was generated by MapForce 2021r2sp1.
//
// YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
// OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
//
// Refer to the MapForce Documentation for further details.
// http://www.altova.com/mapforce
//
////////////////////////////////////////////////////////////////////////

package com.altova.text.flex;

import com.altova.text.*;

public class CommandStore extends Command
{
	private int trimSide;
	private String trimChars;
	
	public CommandStore(String name, int trimSide, String trimChars) {
		super(name);
		this.trimSide = trimSide;
		this.trimChars = trimChars;
	}
	
	public boolean readText(DocumentReader doc) {
		String value = doc.getRange().toString();
		if ((trimSide & 2) != 0) { // trim right
			int i;
			for (i = value.length(); i > 0 && trimChars.indexOf(value.charAt(i-1)) != -1; --i);
			value = value.substring(0, i);
		}
		if ((trimSide & 1) != 0) { // trim left
			int i;
			for (i = 0; i < value.length() && trimChars.indexOf(value.charAt(i)) != -1; ++i);
			value = value.substring(i);
		}
		doc.getOutputTree().insertElement(getName(), value, ITextNode.DataElement);
		return true;
	}
	
	public boolean writeText(DocumentWriter doc) {
		TextNodeList children = doc.getCurrentNode().getChildren().filterByName(getName());
		if (children.size() != 0 && children.getAt(0) != null) {
			String value = (children.getAt(0)).getValue();
			doc.appendText(value);
			return true;
		}
		return false;
	}
}
