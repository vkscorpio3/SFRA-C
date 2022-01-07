////////////////////////////////////////////////////////////////////////
//
// SplitAtDelimiterLineBasedRegex.java
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

public class SplitAtDelimiterLineBasedRegex extends SplitAtDelimiterRegex {
	public SplitAtDelimiterLineBasedRegex( String pattern, boolean matchcase ) {
		super( pattern, matchcase, null );
	}
	
	public Range split( Range range ) {
		if ( pattern.length() == 0 ) {
			Range result = new Range(range);
			range.start = range.end;
			return result;
		}
		
		Range result = super.split( range );
		
		while (result.isValid() && result.charAt(result.end-1) != CR && result.charAt(result.end-1) != LF)
			--result.end;
		range.start = result.end;
		return result;
	}
	
	public void appendDelimiter( Appender output ) {}
}
