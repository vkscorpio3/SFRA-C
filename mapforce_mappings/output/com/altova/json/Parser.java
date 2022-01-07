////////////////////////////////////////////////////////////////////////
//
// Parser.java
//
// This file was generated by MapForce MapForce 2021r2sp1.
//
// YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
// OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
//
// Refer to the MapForce Documentation for further details.
// http://www.altova.com/mapforce
//
////////////////////////////////////////////////////////////////////////

package com.altova.json;

import java.util.*;
import java.io.*;
import java.math.BigInteger;

import com.altova.io.*;
import com.altova.text.*;
import com.altova.json.Value.Type;
 
public class Parser {
	static class State {
		public Reader reader;
		public int Current;
		boolean Json5;

		public State(Reader reader, int current, boolean json5) {
			this.reader = reader;
			Current = current;
			Json5 = json5;
		}

		public void Skip() throws IOException {
			Current = reader.read();
		}

		public void SkipWS() throws IOException {
			while (Current != -1 && Character.isWhitespace((char)Current))
				Current = reader.read();
		}

		public Array ParseArray() throws IOException {
			Skip(); 
			SkipWS();
			if (Current == ']') {
				Skip();
				return new Array(new Value[0]);
			}
			ArrayList<Value> content = new ArrayList<Value>();
			while (true) {
				content.add(Parse());
				SkipWS();
				if (Current == ',') {
					Skip();
					continue;
				}
				if (Current == ']')
					break;
				throw new UnsupportedOperationException("Invalid array syntax.");
			}
			Skip();
			return new Array(content.toArray(new Value[0]));
		}

		public Array ParseArrayJson5() throws IOException {
			Skip();
			SkipWS();
			ArrayList<Value> content = new ArrayList<Value>();
			while (true) {
				if (Current == ']')
					break;
				content.add(Parse());
				SkipWS();
				if (Current == ',') {
					Skip();
					SkipWS();
				} else if (Current != ']')
					throw new UnsupportedOperationException("Invalid array syntax."); 
			}
			Skip();
			return new Array(content.toArray(new Value[0]));
		}
		
		int XD() throws IOException {
			int c = Current;
			Skip();
			if (c >= '0' && c <= '9')
				return c - '0';
			if (c >= 'A' && c <= 'F')
				return (c - 'A') + 10;
			if (c >= 'a' && c <= 'f')
				return (c - 'a') + 10;
			throw new UnsupportedOperationException("Invalid hex digit.");
		}

		String ParseStringLiteral() throws IOException {
			if (Current != '"')
				throw new UnsupportedOperationException("JSON: Expected a string.");
			Skip();

			StringBuilder sb = new StringBuilder();
			while (true) {
				if (Current == -1)
					throw new UnsupportedOperationException("JSON: Expected closing quote.");
				if (Current == '"')
					break;
				if (Current == '\\') {
					Skip();
					switch (Current) {
						case '/': { sb.append("/"); Skip(); } break;
						case 'b': { sb.append("\b"); Skip(); } break;
						case 'f': { sb.append("\f"); Skip(); } break;
						case 'r': { sb.append("\r"); Skip(); } break;
						case 't': { sb.append("\t"); Skip(); } break;
						case 'n': { sb.append("\n"); Skip(); } break;
						case '\\': { sb.append("\\"); Skip(); } break;
						case '"': { sb.append("\""); Skip(); } break;
						case '\'': { sb.append("\'"); Skip(); } break;
						case 'u': {
								Skip();
								int a = XD();
								int b = XD();
								int c = XD();
								int d = XD();
								sb.append((char)(d + 16 * c + 256 * b + 4096 * a));
							}
							break;
						default:
							throw new UnsupportedOperationException("JSON: Invalid escape sequence.");
					}
				} else {
					sb.append((char)Current);
					Skip();
				}
			}
			Skip();

			return sb.toString();
		}

		String ParseKeyword() throws IOException {
			StringBuilder bld = new StringBuilder();
			bld.append((char)Current);
			Skip();
			while (Current != -1 && Character.isLetterOrDigit((char)Current)) {
				bld.append((char)Current);
				Skip();
			}
			return bld.toString();
		}

		String ParseStringOrKeyword() throws IOException {
			if (Current == '"')
				return ParseStringLiteral();
			if (Current != -1 && Character.isLetter((char)Current))
				return ParseKeyword();
			throw new UnsupportedOperationException("JSON: Invalid object member key.");
		}

		Primitive ParseString() throws IOException {
			return new Primitive(Type.String, ParseStringLiteral());
		}

		void CheckKeyword(String keyword) throws IOException {
			Skip(); // first character matched outside.
			for (int i = 1; i != keyword.length(); ++i) {
				if (Current != keyword.charAt(i))
					throw new UnsupportedOperationException("JSON: Invalid keyword.");
				Skip();
			}
			if (Current != -1 && Character.isLetterOrDigit((char)Current))
				throw new UnsupportedOperationException("JSON: Invalid keyword.");
		}

		Primitive ParseTrue() throws IOException {
			CheckKeyword("true");
			return new Primitive(Type.Boolean, "true");
		}

		Primitive ParseFalse() throws IOException {
			CheckKeyword("false");
			return new Primitive(Type.Boolean, "false");
		}
		Primitive ParseNull() throws IOException {
			CheckKeyword("null");
			return new Primitive(Type.Null, "null");
		}
		Primitive ParseNumber() throws IOException {
			StringBuilder bld = new StringBuilder();
			if (Current == '-') {
				bld.append('-');
				Skip();
			}
			if (Current == '0') {
				bld.append('0');
				Skip();
				if (Current >= '0' && Current <= '9')
					throw new UnsupportedOperationException("Invalid number literal.");
			} else if (Current >= '1'&& Current <= '9') {
				bld.append((char)Current);
				Skip();
				while (Current >= '0' && Current <= '9') {
					bld.append((char)Current);
					Skip();
				}
			} else
				throw new UnsupportedOperationException("Invalid number literal.");

			if (Current == '.') {
				bld.append('.');
				Skip();
				if (!(Current >= '0' && Current <= '9')) throw new UnsupportedOperationException("Invalid number literal.");
				bld.append((char)Current);
				Skip();
				while (Current >= '0' && Current <= '9') {
					bld.append((char)Current);
					Skip();
				}
			}

			if (Current == 'e' || Current == 'E') {
				bld.append((char)Current);
				Skip();
				if (Current == '+' || Current == '-') {
					bld.append((char)Current);
					Skip();
				}
				if (!(Current >= '0' && Current <= '9'))
					throw new UnsupportedOperationException("Invalid number literal.");
				bld.append((char)Current);
				Skip();
				while (Current >= '0' && Current <= '9') {
					bld.append((char)Current);
					Skip();
				}
			}

			if (Current != -1 && Character.isLetterOrDigit((char)Current))
				throw new UnsupportedOperationException("Invalid number literal.");

			return new Primitive(Type.Number, bld.toString());
		}

		String ParseXN() throws IOException {
			StringBuilder bld = new StringBuilder();
			while ((Current >= '0' && Current <= '9') ||
				(Current >= 'A' && Current <= 'F') ||
				(Current >= 'a' && Current <= 'f')) {
				bld.append((char)Current);
				Skip();
			}
			return new BigInteger(bld.toString(), 16).toString();
		}

		Primitive ParseNumberJson5() throws IOException {
			StringBuilder bld = new StringBuilder();
			if (Current == '-') {
				bld.append('-');
				Skip();
			} else if (Current == '+') {
				Skip();
			}
			if (Current == 'I') {
				CheckKeyword("Infinity");
				bld.append("Infinity");
				return new Primitive(Type.Number, bld.toString());
			}
			if (Current == 'N') {
				CheckKeyword("NaN");
				return new Primitive(Type.Number, "NaN");
			}

			if (Current == '0') {
				Skip();
				if (Current == 'x') {
					Skip();
					bld.append(ParseXN());
					return new Primitive(Type.Number, bld.toString());
				}
				if (Current != -1 && Current != '.' && Current != 'e' && Current != 'E')
					throw new UnsupportedOperationException("Invalid number literal.");
				bld.append("0");
			} else if (Current >= '1' && Current <= '9') {
				bld.append((char)Current);
				Skip();
				while (Current >= '0' && Current <= '9') {
					bld.append((char)Current);
					Skip();
				}
			} else if (Current != '.')
				throw new UnsupportedOperationException("Invalid number literal.");
			else
				bld.append("0");

			if (Current == '.') {
				Skip();
				bld.append('.');
				boolean norm = true;
				while (Current >= '0' && Current <= '9') {
					bld.append((char)Current);
					Skip();
					norm = false;
				}
				if (norm)
					bld.append('0');
			}
			if (Current == 'e' || Current == 'E') {
				bld.append((char)Current);
				Skip();
				if (Current == '+' || Current == '-') {
					bld.append((char)Current);
					Skip();
				}
				if (Current >= '0' && Current <= '9') {
					bld.append((char)Current);
					Skip();
					while (Current >= '0' && Current <= '9') {
						bld.append((char)Current);
						Skip();
					}
				}
				else
					throw new UnsupportedOperationException("Invalid number literal.");
			}
			return new Primitive(Type.Number, bld.toString());
		}

		public Object ParseObject() throws IOException {
			Skip(); // '{'
			SkipWS();
			if (Current == '}') {
				Skip();
				return new Object(new Member[0]);
			}
			ArrayList<Member> content = new ArrayList<Member>();
			while (true) {
				String key = ParseStringLiteral();
				SkipWS();
				if (Current != ':')
					throw new UnsupportedOperationException("Invalid object syntax.");
				Skip();
				Value value = Parse();
				content.add(new Member(key, value));
				SkipWS();
				if (Current == ',') {
					Skip();
					SkipWS();
					continue;
				}
				if (Current == '}')
					break;
				throw new UnsupportedOperationException("Invalid object syntax.");
			}
			Skip();
			return new Object(content.toArray(new Member[0]));
		}
		public Object ParseObjectJson5() throws IOException {
			Skip(); // '{'
			SkipWS();
			ArrayList<Member> content = new ArrayList<Member>();
			while (true) {
				if (Current == '}')
					break;
				String key = ParseStringOrKeyword();
				SkipWS();
				if (Current != ':')
					throw new UnsupportedOperationException("Invalid object syntax.");
				Skip();
				Value value = Parse();
				content.add(new Member(key, value));
				SkipWS();
				if (Current == ',') {
					Skip();
					SkipWS();
				} else if (Current != '}')
					throw new UnsupportedOperationException("Invalid object syntax.");
			}
			Skip();
			return new Object(content.toArray(new Member[0]));
		}
		public Value Parse() throws IOException {
			SkipWS();
				if (Json5) {
					switch (Current) {
						case '"': return ParseString();
						case '0':
						case '-':
						case '+':
						case 'I':
						case 'N':
						case '.':
						case '1': case '2': case '3': case '4':
						case '5': case '6': case '7': case '8': case '9':
							return ParseNumberJson5();
						case 't': return ParseTrue();
						case 'f': return ParseFalse();
						case 'n': return ParseNull();
						case '[': return ParseArrayJson5();
						case '{': return ParseObjectJson5();
						default: throw new UnsupportedOperationException("Unexpected character in JSON input.");
					}
				} else {
			switch (Current) {
				case '"': return ParseString();
				case '0': case '1': case '2': case '3': case '4':
				case '5': case '6': case '7': case '8': case '9':
				case '-': return ParseNumber();
				case 't': return ParseTrue();
				case 'f': return ParseFalse();
				case 'n': return ParseNull();
				case '[': return ParseArray();
				case '{': return ParseObject();
				default: throw new UnsupportedOperationException("Unexpected character in JSON input.");
			}
		}
	}
		}



	public static Value Parse(java.io.Reader source, boolean json5, boolean jsonLines) throws IOException {
		State state = new State(source, source.read(), json5);
		if (jsonLines) {
			ArrayList<Value> content = new ArrayList<Value>();
			do {
				content.add(state.Parse());
				state.SkipWS();
			}
			while (state.Current != -1);

			return new Array(content.toArray(new Value[0]));
		} else {
			Value res = state.Parse();
			state.SkipWS();
			if (state.Current != -1)
				throw new UnsupportedOperationException("Not well-formed JSON.");
			return res;
		}
	}
	
	public static Value Parse(java.io.InputStream stream, boolean json5, boolean jsonLines) throws Exception {
		try (FileIO filestream = new FileIO(stream, null, false, false);
				StringInput source = new StringInput(filestream.readToEnd().toString())) {
			Reader reader = source.getReader();
			State state = new State(reader, reader.read(), json5);
			if (jsonLines) {
				ArrayList<Value> content = new ArrayList<Value>();
				do {
					content.add(state.Parse());
					state.SkipWS();
				}
				while (state.Current != -1);

				return new Array(content.toArray(new Value[0]));
			} else {
				Value res = state.Parse();
				state.SkipWS();
				if (state.Current != -1)
					throw new UnsupportedOperationException("Not well-formed JSON.");
				return res;
			}
		}
	}
}
