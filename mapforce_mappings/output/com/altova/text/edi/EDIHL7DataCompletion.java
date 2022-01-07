////////////////////////////////////////////////////////////////////////
//
// EDIHL7DataCompletion.java
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

package com.altova.text.edi;

import com.altova.text.ITextNode;
import com.altova.text.ITextNodeList;

public class EDIHL7DataCompletion extends DataCompletion {
	private EDIHL7Settings mSettings = null;

	public EDIHL7DataCompletion(TextDocument document, EDIHL7Settings settings, String structurename) {
		super(document, structurename);
		mSettings = settings;
	}

	public void completeData(ITextNode dataroot, Particle rootParticle) {
		completeMandatory(dataroot, rootParticle);

		if (rootParticle.getNode().getName() == "GroupFHS") { //batch format
			completeGroupFHS( dataroot );
		} else {
			completeMSH( mSettings.getMessageType(), makeSureExists( dataroot, "MSH"));
		}
	}
	
	protected void completeGroupFHS( ITextNode gFHS) {
		if (hasKid( gFHS, "FTS" )) {
			makeSureExists( gFHS, "FHS" );
		}
		if (hasKid( gFHS, "FHS" )) {
			completeSegmentFHS( gFHS.getChildren().getFirstNodeByName( "FHS" ) );
			makeSureExists( gFHS, "FTS" );
		}

		completeGroupBHS( gFHS.getChildren().getFirstNodeByName( "GroupBHS" ) );

		if (hasKid( gFHS, "FTS" )) {
			ITextNode fts = gFHS.getChildren().getFirstNodeByName( "FTS" );
			ITextNodeList batchGroups = gFHS.getChildren().filterByName( "GroupBHS");
			ITextNode fts1 = makeSureExists( fts, "FTS-1" );
			fts1.setValue( batchGroups.size() + "" );
		}
	}

	protected void completeGroupBHS( ITextNode gBHS) {
		if (hasKid( gBHS, "BTS" )) {
			makeSureExists( gBHS, "BHS" );
		}
		if (hasKid( gBHS, "BHS" )) {
			completeSegmentBHS( gBHS.getChildren().getFirstNodeByName( "BHS" ) );
			makeSureExists( gBHS, "BTS" );
		}

		ITextNodeList multimessages = null;
		for (String sMessageType : m_Document.getMessageTypes()) {
			multimessages = gBHS.getChildren().filterByName("Message_" + sMessageType);
			for (int i=0; i< multimessages.size(); ++i) {
				completeMandatory(multimessages.getAt(i), m_Document.getMessage(sMessageType).getRootParticle());
				completeMSH(sMessageType, multimessages.getAt(i).getChildren().getFirstNodeByName("MSH"));
			}
		}

		if (hasKid( gBHS, "BTS" )) {
			int nMessages = multimessages != null ? multimessages.size() : 0;
			ITextNode bts = gBHS.getChildren().getFirstNodeByName( "BTS" );
			ITextNode bts1 = makeSureExists( bts, "BTS-1" );
			bts1.setValue( nMessages + "" );
		}
	}

	protected void completeSegmentFHS( ITextNode fhs) {
		//Data element separator
		ITextNode fhs1 = makeSureExists( fhs, "FHS-1");
		//this value will be overwritten, it just needs to be set
		fhs1.setValue("-");

		//Encoding Characters
		ITextNode fhs2 = makeSureExists( fhs, "FHS-2");
		//this value will be overwritten, it just needs to be set
		fhs2.setValue("#@!");

		//Date/Time of Message
		ITextNode fhs7 = makeSureExists( fhs, "FHS-7");
		fillDateTime(fhs7);
	}

	protected void completeSegmentBHS( ITextNode bhs) {
		//Data element separator
		ITextNode bhs1 = makeSureExists( bhs, "BHS-1");
		//this value will be overwritten, it just needs to be set
		bhs1.setValue("-");

		//Encoding Characters
		ITextNode bhs2 = makeSureExists( bhs, "BHS-2");
		//this value will be overwritten, it just needs to be set
		bhs2.setValue("#@!");

		//Date/Time of Message
		ITextNode bhs7 = makeSureExists( bhs, "BHS-7");
		fillDateTime(bhs7);
	}

	private void completeMSH(String sMessageType, ITextNode msh) {
		//MSH-1 Data element separator
		ITextNode msh1 = makeSureExists( msh, "MSH-1");
		//this value will be overwritten, it just needs to be set
		conservativeSetValue( msh1, mSettings.getServiceChars().getDataElementSeparator());

		//MSH-2 Encoding Characters
		ITextNode msh2 = makeSureExists( msh, "MSH-2");
		//this value will be overwritten, it just needs to be set
		conservativeSetValue(
				msh2,
				"" + mSettings.getServiceChars().getComponentSeparator() +
				mSettings.getServiceChars().getRepetitionSeparator() +
				mSettings.getServiceChars().getReleaseCharacter() +
				mSettings.getServiceChars().getSubComponentSeparator()
		);

		//MSH-7 Date/Time of Message
		ITextNode msh7 = makeSureExists( msh, "MSH-7");
		fillDateTime(msh7);

		//MSH-9 Message Type
		ITextNode msh9 = makeSureExists( msh, "MSH-9");
		completeMSG( sMessageType, msh9);

		//MSH-12 Version ID
		ITextNode msh12 = makeSureExists( msh, "MSH-12");
		ITextNode vid1 = makeSureExists( msh12 , "VID-1");
		conservativeSetValue( vid1, mSettings.getRelease());
	}

	private void completeMSG( String sMessageType, ITextNode msg) {
		//the example MSG Type is: QBP_Z73
		String[] structure = sMessageType.split( "_");
		
		//Message Code: QBP
		ITextNode msg1 = makeSureExists( msg, "MSG-1");
		conservativeSetValue( msg1, structure[0]);

		//Trigger Event: Z73
		ITextNode msg2 = makeSureExists( msg, "MSG-2");
		conservativeSetValue( msg2, structure[1]);

		//Message Structure: QBP_Z73
		ITextNode msg3 = makeSureExists( msg, "MSG-3");
		conservativeSetValue( msg3, sMessageType);
	}

	private void fillDateTime(ITextNode dateField) {
		if (mSettings.getRelease().equals( "2.6" ))
			conservativeSetValue( dateField, getCurrentDateAsEDIString(4) + getCurrentTimeAsEDIString());
		else {
			ITextNode ts1 = makeSureExists( dateField, "TS-1");
			conservativeSetValue( ts1, getCurrentDateAsEDIString(4) + getCurrentTimeAsEDIString());
		}
	}
}
