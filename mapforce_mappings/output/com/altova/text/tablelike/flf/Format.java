////////////////////////////////////////////////////////////////////////
//
// Format.java
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

package com.altova.text.tablelike.flf;

public class Format {
	private boolean m_AssumeRecordDelimitersPresent = false;
	private boolean m_RemoveEmpty = true;
	private String m_lineEnd;

	private char m_FillCharacter = ' ';

	private char[] m_RecordDelimiters = new char[] { '\n', '\r' };

	public Format(int lineEnd) {
		switch(lineEnd) {
			case 0: this.m_lineEnd = java.lang.System.getProperty("line.separator"); break;
			case 1: this.m_lineEnd = "\r\n"; break;
			case 2: this.m_lineEnd = "\n"; break;
			case 3: this.m_lineEnd = "\r"; break;
			default: this.m_lineEnd = "\r\n"; break;
		}
	}

	public boolean getAssumeRecordDelimitersPresent() {
		return m_AssumeRecordDelimitersPresent;
	}

	public void setAssumeRecordDelimitersPresent(boolean rhs) {
		m_AssumeRecordDelimitersPresent = rhs;
	}

 	public void setRemoveEmpty(boolean rhs) {
		m_RemoveEmpty = rhs;
	}

	public boolean getRemoveEmpty() {
		return m_RemoveEmpty;
	}

	public char getFillCharacter() {
		return m_FillCharacter;
	}

	public void setFillCharacter(char rhs) {
		m_FillCharacter = rhs;
	}

	public String getLineEnd() {
		return m_lineEnd;
	}

	public boolean IsRecordDelimiter(char rhs) {
		for (int i = 0; i < m_RecordDelimiters.length; ++i)
			if (m_RecordDelimiters[i] == rhs)
				return true;
		return false;
	}
}