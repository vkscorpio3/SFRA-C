////////////////////////////////////////////////////////////////////////
//
// StructureItem.java
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
import com.altova.text.TextNodeList;
import com.altova.text.edi.Parser.Context;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.HashMap;
import java.io.IOException;

public abstract class StructureItem {
	protected byte mNodeClass;
	protected String mName;
	protected Particle[] mChildren;
	protected String mConditionPath;
	protected String mConditionValue;

	public String getConditionPath() {
		return mConditionPath;
	}

	public String getConditionValue() {
		return mConditionValue;
	}

	public byte getNodeClass() {
		return mNodeClass;
	}

	private boolean isRepeatingSequenceStarting(int it) {
		if (it == mChildren.length)
			return false;
		
		if (it + 1 == mChildren.length)
			return false;
		
		return particlesEquivalent(it, it+1);
	}

	private boolean particlesEquivalent(int i_a, int i_b) {
		Particle a = mChildren[i_a];
		Particle b = mChildren[i_b];
		
		if (a.getNode().getNodeClass() != b.getNode().getNodeClass())
			return false;
		
		if (a.getNode().getNodeClass() == ITextNode.Segment)
			return a.getNode().mName.equals(b.getNode().mName);
		
		if (a.getNode().getNodeClass() == ITextNode.Group) {
			String s_a = a.getNode().mName;
			String s_b = b.getNode().mName;
			
			StringBuilder n_a = new StringBuilder();
			for (int i=0; i<s_a.length(); i++)
				if (Character.isDigit(s_a.charAt(i)))
					n_a.append(s_a.charAt(i));
			
			StringBuilder n_b = new StringBuilder();
			for (int i=0; i<s_b.length(); i++)
				if (Character.isDigit(s_b.charAt(i)))
					n_b.append(s_b.charAt(i));
			
			String ns_a = n_a.toString();
			String ns_b = n_b.toString();
			
			if (ns_a.length() > 0 && !ns_a.endsWith("00") && ns_a.equals(ns_b))
				return true;
		}
		return false;
	}
	
	private void handleMissingSegmentOrGroup(Context childContext, Particle currentParticle) {
		if (childContext.getScanner().isAtEnd()) {
			childContext.handleError( 
				Parser.ErrorType.UnexpectedEndOfFile,
				ErrorMessages.GetUnexpectedEndOfFileMessage(),
				new ErrorPosition( childContext.getScanner() )
			);
		}
		if (currentParticle.getNode().getNodeClass() == ITextNode.Segment) {
			childContext.handleError( 
				Parser.ErrorType.MissingSegment,
				ErrorMessages.GetMissingSegmentMessage( currentParticle.getNode().getName() ),
				new ErrorPosition( childContext.getScanner() )
			);
		} else if (currentParticle.getNode().getNodeClass() == ITextNode.Group) {
			childContext.handleError(
				Parser.ErrorType.MissingGroup,
				ErrorMessages.GetMissingGroupMessage( currentParticle.getNode().getName() ),
				new ErrorPosition( childContext.getScanner() )
			);
		} else if (currentParticle.getNode().getNodeClass() == ITextNode.Select) {
			childContext.handleError(
				Parser.ErrorType.MissingGroup,
				ErrorMessages.GetMissingGroupMessage( "Message" ),
				new ErrorPosition( childContext.getScanner() )
			);
		}
	}
	
	protected boolean readChildren(Parser.Context context, byte separator) {
		
		if (getNodeClass() == ITextNode.Group)
			return readChildrenOfGroup(context, separator);
		
		return readChildrenOfSegment(context, separator);
	}
	
	protected boolean readChildrenOfGroup(Parser.Context context, byte separator) {
		HashMap<Particle, Integer> readMap = new HashMap<Particle, Integer>();
		Scanner scanner = context.getScanner();
		
		int it = 0; 
		int repeatingStart = mChildren.length;
		
		while (it != mChildren.length) {
			if (repeatingStart != mChildren.length) {
				if (particlesEquivalent(it, repeatingStart)) {
					;
				} else {
					for (Particle p : readMap.keySet()) {
						Parser.Context ctx = context.newContext(context, p);
						
						if (readMap.get(p) < p.mMinOccurs) {
							handleMissingSegmentOrGroup(ctx, p);
						}
					}
					
					repeatingStart = mChildren.length;
					readMap.clear();
				}
			}
		
			if (repeatingStart == mChildren.length && isRepeatingSequenceStarting(it)) {
				repeatingStart = it;
			}
			
			boolean inRepeatingSequence = repeatingStart != mChildren.length;
			
			Particle currentParticle = mChildren[it];
			if (context.getParser().getSettings().getStandard() == EDISettings.EDIStandard.EDIX12 && currentParticle.getNode().getNodeClass() == ITextNode.Group) {
				if (it > 0) {
					Particle precedingParticle = mChildren[it-1];

					if (precedingParticle.getNode().getNodeClass() == ITextNode.Segment && precedingParticle.getName().equals("LS"))
						currentParticle.boundedGroup = true;
				}
			}
			
			if (inRepeatingSequence && !readMap.containsKey(currentParticle))
				readMap.put(currentParticle, 0);
			
			int toRead = currentParticle.getMergedEntries();
			if (toRead == 1) { // no merged entry
				toRead = currentParticle.mRespectMaxOccurs ?
						currentParticle.mMaxOccurs : // read only max occurs
						Integer.MAX_VALUE; // try to read as much as possible -> errors are reported anyways
				
				if (currentParticle.getMaxOccurs() <= 1) {
					toRead = 1;
				}
			}
			
			Parser.Context childContext = context.newContext(context, currentParticle);
			
			boolean advance = true;
			boolean readSuccess = true;
			for (int count = 0; count < toRead; ++count) {
				childContext.setOccurence( count + 1 );
				
				Scanner.State preservedState = scanner.getCurrentState();
				
				readSuccess = !scanner.isAtEnd() && currentParticle.getNode().read(childContext);
				
				if (!readSuccess) {
					if (count >= currentParticle.getMinOccurs()) {
						break; // read enough
					}
					if (context.getParser().getSettings().getStandard() == EDISettings.EDIStandard.EDITRADACOMS && currentParticle.getNode().getNodeClass() == ITextNode.Select) {
						return false; // select failed because it was not read, report upwards
					}
					
					if (!inRepeatingSequence) {
						if (count < currentParticle.mMinOccurs) {
							scanner.setCurrentState(preservedState);
							String sSeg = readSegmentTag(childContext);
							childContext.handleError(
								Parser.ErrorType.SegmentUnexpected, 
								ErrorMessages.GetUnexpectedSegmentIDMessage( sSeg ),
								new ErrorPosition( preservedState )
								);
						}
					}
				}
				
				int readSoFar = inRepeatingSequence ? readMap.get(currentParticle) : count;
				if (readSuccess && currentParticle.mMergedEntries == 1 && readSoFar >= currentParticle.mMaxOccurs) {
					childContext.handleError(
							Parser.ErrorType.ExtraRepeat, 
							ErrorMessages.GetExtraRepeatMessage( currentParticle.getNode().getName() ),
							new ErrorPosition( preservedState )
						);
				}
				
				if (readSuccess) {
					advance = false;
					if (inRepeatingSequence)
						readMap.put(currentParticle, readMap.get(currentParticle) + 1);
				}
			}
			
			if (inRepeatingSequence && !advance) { // we are in repeating sequence
				it = repeatingStart;
			} else 
				++it;
		}
		
		return true;
	}
	
	protected boolean readChildrenOfSegment(Parser.Context context, byte separator) {
		Scanner scanner = context.getScanner();

		for (int childIndex = 0; childIndex != getChildCount(); ++childIndex) {
			Particle currentParticle = mChildren[childIndex];

			if (childIndex != 0 && separator != ServiceChars.None && scanner.isAtSeparator(separator))
				scanner.rawConsumeChar();

			int toRead = currentParticle.getMergedEntries();
			byte repSeparator = separator;
			if (toRead == 1) { // no merged entry
				toRead = currentParticle.mRespectMaxOccurs ?
						currentParticle.mMaxOccurs : // read only max occurs
						Integer.MAX_VALUE; // try to read as much as possible -> errors are reported anyways
				if (mNodeClass == ITextNode.Segment) {
					repSeparator = ServiceChars.RepetitionSeparator;
					if (scanner.getServiceChars().getRepetitionSeparator() == '\0') {
						toRead = 1;
						repSeparator = ServiceChars.None;
					} else if(context.getParser().getEDIKind() == EDISettings.EDIStandard.EDIHL7
							&& isHL7SpecialField(currentParticle.getName(), "-1")) {
						toRead = 1;
						repSeparator = ServiceChars.None;
					} else {
						toRead = Integer.MAX_VALUE;
					}
				} else if (currentParticle.getMaxOccurs() <= 1) {
					toRead = 1;
					repSeparator = ServiceChars.None;
				}
			}
			
			switch (context.mParticle.getNode().getNodeClass()) {
				case ITextNode.Segment: context.mParser.incrementDataElementPos(); break;
				case ITextNode.Composite: context.mParser.incrementComponentDataElementPos(); break;
			}

			Parser.Context childContext = context.newContext(context, currentParticle);
			for (int count = 0; count < toRead && !scanner.isAtEnd(); ++count) {
				childContext.setOccurence( count + 1 );
				// consume the proper separator. otherwise the children won't find anything to read and fail anyways.
				if (count != 0 && repSeparator != ServiceChars.None) {
					if (scanner.isAtSeparator(repSeparator))
						scanner.rawConsumeChar ();
					else
						break;
				}

				Scanner.State preservedState = scanner.getCurrentState();

				boolean readSuccess = currentParticle.getNode().read(childContext);

				if (!readSuccess && (repSeparator == ServiceChars.None || currentParticle.getMinOccurs() > 0)) {
					if (count >= currentParticle.getMinOccurs()) {
						if (count >= currentParticle.getMergedEntries())
							break; // enough read
					} else {
						if (currentParticle.getNode().getNodeClass() == ITextNode.Segment) {
							childContext.handleError( 
								Parser.ErrorType.MissingSegment,
								ErrorMessages.GetMissingSegmentMessage( currentParticle.getNode().getName() ),
								new ErrorPosition( preservedState )
							);
						} else if (currentParticle.getNode().getNodeClass() == ITextNode.Group) {
							childContext.handleError(
								Parser.ErrorType.MissingGroup,
								ErrorMessages.GetMissingGroupMessage( currentParticle.getNode().getName() ),
								new ErrorPosition( preservedState )
							);
						} else if (currentParticle.getNode().getNodeClass() == ITextNode.Select) {
							childContext.handleError(
								Parser.ErrorType.MissingGroup,
								ErrorMessages.GetMissingGroupMessage( "Message" ),
								new ErrorPosition( preservedState )
							);
						} else
							childContext.handleError(
								Parser.ErrorType.MissingFieldOrComposite,
								ErrorMessages.GetMissingFieldOrCompositeMessage( currentParticle.getNode().getName() ),
								new ErrorPosition( preservedState )
							);
					}
				}

				if (currentParticle.getMergedEntries() == 1 && count >= currentParticle.getMaxOccurs()) {
					if (currentParticle.getMaxOccurs() != 0 || readSuccess) {
						if (currentParticle.getMaxOccurs() == 0 && count == 0) {
							childContext.handleError(
								Parser.ErrorType.UsingNotUsed, 
								ErrorMessages.GetNotUsedPresent( currentParticle.getNode().getName() ),
								new ErrorPosition( preservedState )
							);
						} else {
							childContext.handleError(
								Parser.ErrorType.ExtraRepeat, 
								ErrorMessages.GetExtraRepeatMessage( currentParticle.getNode().getName() ),
								new ErrorPosition( preservedState )
							);
						}
					}
				}

				if (repSeparator != ServiceChars.None) {
					String sExtra = scanner.consumeString(repSeparator, true).toString();
					if (sExtra.length() > 0)
						childContext.handleError(
							Parser.ErrorType.ExtraData,
							ErrorMessages.GetExtraDataMessage(getName(), sExtra),
							new ErrorPosition( preservedState )
						);
				}
			}
		}
		return true;
	}

	protected void writeChildren (Writer writer, ITextNode node, byte separator) throws IOException {
		int structChildren = getChildCount();

		for (int childPos = 0; childPos < structChildren; ++childPos) {
			Particle currentParticle = mChildren[childPos];
			int nToWrite = currentParticle.getMergedEntries();
			byte actSeparator = separator;
			if (currentParticle.getMaxOccurs() > 1 || getNodeClass() == ITextNode.Group) {
				//	be tolerant for maxOccurs overruns, but don't eat all nodes for non-repeating items:
				nToWrite = Integer.MAX_VALUE;

				if (getNodeClass() == ITextNode.Segment) {
					actSeparator = ServiceChars.RepetitionSeparator;
					if (writer.getServiceChars().getSeparator(actSeparator) == '\0')
						nToWrite = 1; // no separator -> need to suppress extra repetitions
					if (writer.getEDIKind() == EDISettings.EDIStandard.EDIFixed)
						nToWrite = 1; //write exactly 1 data segment in fixed configs
				}
			}

			ITextNodeList children = (node != null) ? node.getChildren().filterByName(currentParticle.getNameOverride()) : new TextNodeList(null);

			if (children.size() > java.lang.Math.max(
					currentParticle.mMaxOccurs,
					currentParticle.mMergedEntries)) {
				//report error/warning
				writer.handleError(
					node,
					Parser.ErrorType.ExtraRepeat,
					ErrorMessages.GetExtraRepeatMessage( currentParticle.getName() ) 
				);
			}

			for (int nCount = 0; nCount < nToWrite; ++nCount) {
				if (nCount < children.size())
					currentParticle.getNode().write(writer, children.getAt(nCount), currentParticle);
				else {
					// if it's a fixed config, always write <Data> elements to keep the format
					if (writer.getEDIKind() == EDISettings.EDIStandard.EDIFixed && getNodeClass() == ITextNode.Segment) {
						currentParticle.getNode().write(writer, null, currentParticle);
					}

					// selects name doesn't match the generator tree. so always try to write them.
					if (currentParticle.getNode().getNodeClass() == ITextNode.Select) {
						currentParticle.getNode().write(writer, node, currentParticle);
						break;
					}

					if (nCount < currentParticle.getMinOccurs()) {
						if (getNodeClass() == ITextNode.Group && currentParticle.getName().equals("Message")) {
							//report error/warning
							writer.handleError(
								node,
								Parser.ErrorType.MissingGroup,
								ErrorMessages.GetMissingGroupMessage(currentParticle.getName())
							);
						} else {
							//report error/warning
							writer.handleError(
								node,
								Parser.ErrorType.MissingFieldOrComposite,
								ErrorMessages.GetMissingFieldOrCompositeMessage(currentParticle.getName())
							);
						}

						if (getNodeClass() == ITextNode.Group && !currentParticle.getName().equals("Message"))
							currentParticle.getNode().write(writer, null, currentParticle);
					} else if (nCount >= currentParticle.getMergedEntries())
							break;
				}
				if (!(writer.getEDIKind() == EDISettings.EDIStandard.EDIHL7
					&& isHL7SpecialField(currentParticle.getName(), "-1")))
					writer.addSeparator(actSeparator);
			}
			if (actSeparator != separator) {
				writer.clearPendingSeparators(actSeparator);
				writer.addSeparator(separator);
			}
		}
		writer.clearPendingSeparators(separator);
	}


	protected boolean isAtGroup (Parser.Context context) {
		if (mConditionPath != null && mConditionPath.length() > 0 && !checkConditionValue(context, mConditionPath, mConditionValue)) {
			return false;
		}
		
		if (context.getParticle().boundedGroup) {
			String loopID = mName;
			
			if (loopID.startsWith("Loop"))
				loopID = loopID.substring(4);
		
			if (loopID.length() > 4)
				loopID = loopID.substring(0, 4);
			
			if (loopID.length() == 0 || !loopID.equals(context.getParser().getF447()))
				return false;
		}
		
		StructureItem node = this;
		Particle particle = node.child(0);
		while (particle.getNode().getNodeClass() == ITextNode.Group) {
			node = particle.getNode();
			particle = node.child(0);
		}

		// for the special Interchange and Envelope groups different behavior is needed.
		for (int nIndex = 0;nIndex < node.getChildCount(); ++nIndex) {
			particle = node.child(nIndex);
			
			StructureItem child = particle.getNode();
			if (child.getNodeClass() == ITextNode.Segment) {
				// try to find out whether this segment appears here.
				Scanner.State preservedState = context.getScanner().getCurrentState();
				boolean result = child.isSegmentStarting (context);
				context.getScanner().setCurrentState(preservedState);
				if (result)
					return true;
			} else { // shouldn't be anything else.
				if (child.isAtGroup (context))
					return true;
			}

			// the segment is mandatory -> the group cannot occur here.
			if (particle.getMinOccurs() > 0)
				return false;
		}
		// this could happen in cases where groups have no indicator segment.
		return false;
	}

	protected String readSegmentTag (Parser.Context context) {
		StringBuffer sRet = new StringBuffer();
		Scanner scanner = context.getScanner();
		scanner.skipWhitespace(); // skip whitespace before/between segments

		if (mName.equals("UNA") || mName.equals("ISA") || isHL7SpecialSegment(mName) ||
			context.getParser().getEDIKind() == EDISettings.EDIStandard.EDIFixed) {
			// read character by character because separators are not known yet
			for (int i = 0; i < mName.length(); ++i) {
				char c = scanner.rawConsumeChar();
				if (c != 0)
					sRet.append( c );
			}
		} else
			sRet.append( scanner.consumeString(ServiceChars.ComponentSeparator, true).toString() );
		
		return sRet.toString();	
	}

	protected boolean isSegmentStarting (Parser.Context context) {
		return mName.equals(readSegmentTag( context)) && checkSegmentCondition(context);
	}

	protected boolean checkSegmentCondition(Parser.Context context) {
		if (mConditionPath.length() == 0)
			return true;
		return checkConditionValue(context, mConditionPath, mConditionValue);
	}

	protected boolean checkConditionValue(Parser.Context ctx, String conditionPath, String conditionValue) {
		Scanner.State preservedState = ctx.getScanner().getCurrentState();

		for (int k = 0 ; k < mChildren.length ; ++k) {
			Particle particle = mChildren[k];
			if (ctx.getScanner().isAtEnd() || ctx.getScanner().isAtSeparator(ServiceChars.SegmentTerminator)) {
				ctx.getScanner().setCurrentState( preservedState );
				return false;
			}

			if (ctx.getScanner().isAtAnySeparator())
				ctx.getScanner().rawConsumeChar();

			if (particle.getNode().getNodeClass() == ITextNode.DataElement) {
				if (ctx.getScanner().isAtEnd() || ctx.getScanner().isAtSeparator(ServiceChars.SegmentTerminator)) {
					ctx.getScanner().setCurrentState( preservedState );
					return false;
				}

				byte separator;
				switch (mNodeClass) {
					case ITextNode.Segment:
						separator = ServiceChars.DataElementSeparator;
						break;

					case ITextNode.Composite:
						separator = ServiceChars.ComponentSeparator;
						break;

					case ITextNode.SubComposite:
						separator = ServiceChars.SubComponentSeparator;
						break;

					default:
						ctx.getScanner().setCurrentState( preservedState );
						return false;
				}

				String value = ctx.getScanner().consumeString(separator, true).toString();

				if (particle.getName().equals( conditionPath )) {
					ctx.getScanner().setCurrentState( preservedState );
					if (conditionValue.length() > 0 && value.equals( conditionValue ))
						return true;

					String[] codeValues = particle.getCodeValues();
					for (int i = 0 ; i < codeValues.length ; ++i)
						if (codeValues[i].equals( value ))
							return true;

					return false;
				}
			} else if (particle.getNode().getNodeClass() == ITextNode.Composite) {
				int i = conditionPath.indexOf('/');
				if (i > 0) {
					String path = conditionPath.substring(i+1);
					if (particle.getName().equals( conditionPath.substring(0, i) )) {
						boolean result = particle.getNode().checkConditionValue(ctx, path, conditionValue);
						ctx.getScanner().setCurrentState( preservedState );
						return result;
					}
				}

				// skip composite
				for (; ; ) {
					ctx.getScanner().rawConsumeChar();

					if (ctx.getScanner().isAtEnd() || ctx.getScanner().isAtSeparator(ServiceChars.SegmentTerminator)) {
						ctx.getScanner().setCurrentState( preservedState );
						return false;
					}
					if (ctx.getScanner().isAtSeparator(ServiceChars.DataElementSeparator))
						break;
				}
			} else if (particle.getNode().getNodeClass() == ITextNode.Segment) {
				int i = conditionPath.indexOf('/');
				if (i > 0) {
					String path = conditionPath.substring(i+1);
					if (particle.getName().equals(conditionPath.substring(0, i)) && particle.getName().equals(particle.getNode().readSegmentTag(ctx))) {
						boolean result = particle.getNode().checkConditionValue(ctx, path, conditionValue);
						ctx.getScanner().setCurrentState( preservedState );
						return result;
					}
				}
				
				// skip segment
				for (;;) {
					ctx.getScanner().rawConsumeChar();

					if (ctx.getScanner().isAtEnd()) {
						ctx.getScanner().setCurrentState( preservedState );
						return false;
					}
					if (ctx.getScanner().isAtSeparator(ServiceChars.SegmentTerminator)) {
						ctx.getScanner().rawConsumeChar();
						break;
					}
				}
			}
		}

		ctx.getScanner().setCurrentState( preservedState );
		return false;
	}

	public abstract boolean read(Parser.Context context);

	public abstract void write(Writer writer, ITextNode node, Particle particle) throws IOException;

	public String getName() {
		return mName;
	}

	public Particle[] getChildren() {
		return mChildren;
	}

	public Particle child(int i) {
		return mChildren[i];
	}

	public int getChildCount() {
		return (mChildren == null) ? 0:mChildren.length;
	}

	protected StructureItem (String name, byte cls) {
		this.mName = name;
		this.mChildren = null;
		this.mNodeClass = cls;
	}

	protected StructureItem (String name, byte cls, Particle[] children) {
		this.mName = name;
		this.mChildren = children;
		this.mNodeClass = cls;
	}

	protected StructureItem (String name, byte cls, String conditionPath, String conditionValue, Particle[] children) {
		this.mName = name;
		this.mChildren = children;
		this.mNodeClass = cls;
		this.mConditionPath = conditionPath;
		this.mConditionValue = conditionValue;
	}

	protected boolean isHL7SpecialSegment(String sSegmentName) {
		return sSegmentName.equals("MSH") || sSegmentName.equals("BHS") || sSegmentName.equals("FHS");
	}
	
	protected boolean isHL7SpecialField(String sFieldName, String sFieldIndex) {
		if (sFieldName.indexOf('-') != -1)
			return isHL7SpecialSegment( sFieldName.substring(0, sFieldName.indexOf('-'))) && sFieldName.endsWith(sFieldIndex);
		else
			return false;
	}
}
