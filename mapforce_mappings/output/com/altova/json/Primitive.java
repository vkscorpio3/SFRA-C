////////////////////////////////////////////////////////////////////////
//
// Primitive.java
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

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

public class Primitive extends Value {
	private Type type;
	private java.lang.Object value;


	public Primitive(String s) { type = Type.String; value = s; }
	public Primitive(BigDecimal d) { type = Type.Number; value = d; }
	public Primitive(boolean b) { type = Type.Boolean; value = b; }
	public Primitive(Type _type, String text) { type = _type; value = text; }
		
	private Primitive() { type = Type.Null; }
		
	public Type getType() { return type; }
	public java.lang.Object getValue() { return value; }

	private static final Primitive _Null = new Primitive();
	public static final Primitive Null() { return _Null; }

	String ValueAsString() {
		String s = value instanceof String ? (String)value : null;
		if (s != null)
			return s;
		switch (type) {
			case Number:
				return ((BigDecimal)value).toString();
			case Null:
				return "null";
			case Boolean:
				return (Boolean)value ? "true" : "false";
			default:
				throw new UnsupportedOperationException("JSON primitive is invalid.");
		}

	}

	protected int GetHashCode() {
		String s = ValueAsString();
		switch (type) {
			case String:
				return s.hashCode();
			case Boolean:
				return s.equals("true") ? 2 : 1;
			case Null:
				return 0;
		}
		return 961757429;
	}

	protected boolean Equals(java.lang.Object obj) {
		Primitive p = obj instanceof Primitive ? (Primitive)obj : null;
		if (p == null)
			return false;
		if (type != p.type)
			return false;
		switch (type) {
			case String:
			case Boolean:
				return ValueAsString().equals(p.ValueAsString());
			case Null:
				return true;
			case Number:
				return CompareNumbers(ValueAsString(), p.ValueAsString()) == 0;
			default:
				throw new UnsupportedOperationException("JSON primitive is invalid.");
		}
	}
	protected void ApplyDescendants(HashMap<Value, ValidationInfo> itemInfos) { }

	public ValidationInfo ValidateBoolean(String id, BooleanAcceptor schema) {
		if (schema != null) {
				
			String s = value instanceof String ? (String)value : null;
			if (s != null) {
				if (schema.getAllowTrue() && s.equals("true"))
					return new ValidationInfo(id);
				if (schema.getAllowFalse() && s.equals("false"))
					return new ValidationInfo(id);
			} else {
				if (schema.getAllowTrue() && (Boolean)value)
					return new ValidationInfo(id);
				if (schema.getAllowFalse() && !(Boolean)value)
					return new ValidationInfo(id);
			}
		}
		return new ValidationInfo(Validity.Invalid);
	}

	static ValidationInfo ValidateNull(String id, NullAcceptor schema) {
		if (schema != null) {
			return new ValidationInfo(id);
		}
		return new ValidationInfo(Validity.Invalid);
	}

	static class CanonicalNumber {
		public String Mantissa;
		public boolean Negative;
		public int Exponent;
		public boolean Transfinite;
	}

	static void ComputeCanonical(String num, CanonicalNumber res) {
		res.Transfinite = false;

		// To support extended JSON stuff (not covered by core JSON grammar):
		if (num.equals("Infinity")) {
			res.Transfinite = true;
			res.Exponent = 0;
			res.Negative = false;
			res.Mantissa = "Infinity";
			return;
		} else if (num.equals("-Infinity")) {
			res.Transfinite = true;
			res.Exponent = 0;
			res.Negative = true;
			res.Mantissa = "Infinity";
			return;
		} else if (num.equals("NaN") || num.equals("-NaN") || num.equals("+NaN")) {
			res.Transfinite = true;
			res.Exponent = 1;
			res.Negative = false;
			res.Mantissa = "NaN";
			return;
		}
		if (num.startsWith("0x") || num.startsWith("0X")) {
			BigInteger value = new BigInteger(num.substring(2), 16);
			num = value.toString();
		}

		int i = 0;
		if (i != num.length() && num.charAt(i) == '-') {
			res.Negative = true;
			++i;
		} else {
			res.Negative = false;
		}
		if (i == num.length())
			throw new IllegalArgumentException("Not a valid number: " + num);

		res.Exponent = 0;

		int ld;

		if (num.charAt(i) == '0') {
			if (i + 1 == num.length() || num.charAt(i+1) == 'e' || num.charAt(i+1) == 'E') {
				res.Negative = false;
				res.Mantissa = "";
				res.Exponent = Integer.MIN_VALUE;
				return;
			}
			if (num.charAt(i+1) != '.' || i + 2 == num.length())
				throw new IllegalArgumentException("Not a valid number: " + num);

			i += 2;
			res.Exponent -= 1;
			while (num.charAt(i) == '0') {
				if (++i == num.length() || num.charAt(i) == 'e' || num.charAt(i) == 'E') {
					res.Negative = false;
					res.Mantissa = "";
					res.Exponent = Integer.MIN_VALUE;
					return;
				}
				res.Exponent -= 1;
			}
			ld = i + 1;
		} else {
			ld = i + 1;
			while (ld != num.length() && num.charAt(ld) != '.' && num.charAt(ld) != 'e' && num.charAt(ld) != 'E') {
				++ld;
				res.Exponent += 1;
			}
			if (ld != num.length() && num.charAt(ld) == '.')
				++ld;

		}
		while (ld != num.length() && num.charAt(ld) != 'e' && num.charAt(ld) != 'E')
			++ld;

		int exp = ld;
		while (ld > i) {
			if (num.charAt(ld-1) == '0' || num.charAt(ld-1) == '.')
				--ld;
			else
				break;
		}

		int x = num.indexOf('.', i);
		if (x >= 0 && x < ld)
			res.Mantissa = num.substring(i, x) + num.substring(x + 1, ld);
		else
			res.Mantissa = num.substring(i, ld);

		if (exp != num.length()) {
			boolean esign = false;
			int eval = 0;
			exp += 1;
			if (exp == num.length())
				throw new IllegalArgumentException("Not a valid number: " + num);
			if (num.charAt(exp) == '+')
				++exp;
			else if (num.charAt(exp) == '-') {
				esign = true;
				++exp;
			}
			if (exp == num.length())
				throw new IllegalArgumentException("Not a valid number: " + num);

			while (exp != num.length()) {
				eval = eval * 10 + (num.charAt(exp) - '0');
				++exp;
			}
			if (esign)
				res.Exponent -= eval;
			else
				res.Exponent += eval;
		}

	}

	static int CompareNumbers(String a, String b) {
		CanonicalNumber ca = new CanonicalNumber();
		CanonicalNumber cb = new CanonicalNumber();
		ComputeCanonical(a, ca);
		ComputeCanonical(b, cb);

		// Sort NaN first.
		if (ca.Transfinite && ca.Exponent != 0) {
			if (cb.Transfinite && cb.Exponent != 0)
				return 0;
			else
				return -1;
		}
		if (cb.Transfinite && cb.Exponent != 0)
			return +1;

		if (ca.Negative == cb.Negative) {
			if (ca.Transfinite && cb.Transfinite)
				return 0;
			if (ca.Transfinite)
				return +1;
			if (cb.Transfinite)
				return -1;

			// same sign
			if (ca.Exponent == cb.Exponent) {
				// same order of magnitude
				return ca.Mantissa.compareTo(cb.Mantissa) * (ca.Negative ? -1 : +1);
			} else {
				return (ca.Exponent < cb.Exponent ? -1 : +1) * (ca.Negative ? -1 : +1);
			}
		} else {
			return ca.Negative ? -1 : +1;
		}
	}

	static int ModExp(long value, int exponent, int modulus) {
		long result = 1;
		value %= modulus;
		while (exponent > 0) {
			if ((exponent & 1) == 1)
				result = (result * value) % modulus;
			exponent >>= 1;
			value = (value * value) % modulus;
		}
		return (int)result;
	}
		
	static boolean IsMultipleOf(String numerator, String denominator) {
		CanonicalNumber canonicalNumerator = new CanonicalNumber();
		CanonicalNumber canonicalDenominator = new CanonicalNumber();
		ComputeCanonical(numerator, canonicalNumerator);
		ComputeCanonical(denominator, canonicalDenominator);
		if (canonicalNumerator.Exponent == Integer.MIN_VALUE)
			return true;
		if (canonicalDenominator.Exponent == Integer.MIN_VALUE)
			return false;

		int leastSignificantDenominator = canonicalDenominator.Exponent - canonicalDenominator.Mantissa.length() + 1;
		int lastDigitPosition = canonicalNumerator.Exponent - leastSignificantDenominator - canonicalNumerator.Mantissa.length() + 1;
		if (lastDigitPosition < 0)
			return false;

		int basis = Integer.parseInt(canonicalDenominator.Mantissa);
		if (basis == 1)
			return true; 

		long weight = ModExp(10, lastDigitPosition, basis);
		long residue = 0;
		for (int i = 0; i != canonicalNumerator.Mantissa.length(); ++i) {
			if (weight == 0)
				break;
			int digit = canonicalNumerator.Mantissa.charAt(canonicalNumerator.Mantissa.length() - i - 1) - '0';
			residue = (residue + digit * weight) % basis;
			weight = (weight * 10) % basis;
		}

		return residue == 0;
	}

	ValidationInfo ValidateNumber(String id, NumberAcceptor schema) {
		if (schema != null) {
				
			if (!(value instanceof String))
				value = ((BigDecimal)value).toString();

			if (schema.getMaximum() != null) {
				int n = CompareNumbers((String)value, schema.getMaximum().Value);
				if (n > 0 || (n == 0 && schema.getMaximum().Exclusive))
					return new ValidationInfo(Validity.Invalid);
			}
			if (schema.getMinimum() != null) {
				int n = CompareNumbers((String)value, schema.getMinimum().Value);
				if (n < 0 || (n == 0 && schema.getMinimum().Exclusive))
					return new ValidationInfo(Validity.Invalid);
			}
			if (schema.getMultipleOf() != null) {
				if (!IsMultipleOf((String)value, schema.getMultipleOf()))
					return new ValidationInfo(Validity.Invalid);
	
			}
			if (schema.getPattern() != null) {
				if (!Pattern.matches(schema.getPattern(), (String)value))//? RegexOptions.ECMAScript
					return new ValidationInfo(Validity.Invalid);
			}
			if (schema.getEnum() != null) {
				boolean found = false;
				for (String e: schema.getEnum()) {
					if (CompareNumbers((String)value, e) == 0) {
						found = true;
						break;
					}
				}
				if (!found)
					return new ValidationInfo(Validity.Invalid);
			}
			return new ValidationInfo(id);
		}
		return new ValidationInfo(Validity.Invalid);
	}

	static int JsonStringLength(String s) {
		int length = 0;
		for (char c: s.toCharArray()) {
			if (Character.isHighSurrogate(c))
				continue;
			length += 1;
		}
		return length;			
	}

	ValidationInfo ValidateString(String id, StringAcceptor schema) {
		if (schema != null) {
			if (schema.getMinLength() != null) {
				if (JsonStringLength((String)value) < schema.getMinLength())
					return new ValidationInfo(Validity.Invalid);
			}
			if (schema.getMaxLength() != null) {
				if (JsonStringLength((String)value) > schema.getMaxLength())
					return new ValidationInfo(Validity.Invalid);
			}
			if (schema.getPattern() != null) {
				if (!Pattern.matches(schema.getPattern(), (String)value))//? RegexOptions.ECMAScript
					return new ValidationInfo(Validity.Invalid);
			}
			if (schema.getEnum() != null) {
				boolean found = false;
				for (String e: schema.getEnum()) {
					if (e.equals((String)value)) {
						found = true;
						break;
					}
				}
				if (!found)
					return new ValidationInfo(Validity.Invalid);
			}
			return new ValidationInfo(id);
		}
		return new ValidationInfo(Validity.Invalid);
	}

	protected ValidationInfo DoValidateCore(Validator validator, ValueAcceptor schema, HashMap<Value, ValidationInfo> itemInfos) {
		switch (type) {
			case String: return ValidateString(schema.getId(), schema.getString());
			case Number: return ValidateNumber(schema.getId(), schema.getNumber());
			case Boolean: return ValidateBoolean(schema.getId(), schema.getBoolean());
			case Null: return ValidateNull(schema.getId(), schema.getNull());
			default: throw new UnsupportedOperationException("Value has invalid type.");
		}
	}

	public java.lang.Object typedValue() {
		switch (getType()) {
			case String: return value;
			case Number:
				String s = value instanceof String ? (String)value : null;
				if (s == null)
					return (BigDecimal)value;
				else
					return new BigDecimal(s);
			case Boolean:
				String s2 = value instanceof String ? (String)value : null;
				if (s2 == null)
					return (Boolean)value;
				else
					return s2.equals("true");
			case Null: return value;
			default: throw new UnsupportedOperationException("Value has invalid type.");
		}
	}
}
