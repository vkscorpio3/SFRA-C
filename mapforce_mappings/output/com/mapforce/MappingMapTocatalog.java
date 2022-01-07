/**
 * MappingMapTocatalog.java
 *
 * This file was generated by MapForce 2021r2sp1.
 *
 * YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
 * OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
 *
 * Refer to the MapForce Documentation for further details.
 * http://www.altova.com/mapforce
 */
package com.mapforce;
import com.altova.mapforce.*;
import com.altova.types.*;
import com.altova.xml.*;
import com.altova.text.tablelike.*;
import com.altova.text.*;
import com.altova.text.edi.*;
import java.util.*;

public class MappingMapTocatalog extends com.altova.TraceProvider {
	private boolean runDoesCloseAll = true;
	public void setCloseObjectsAfterRun(boolean c) {runDoesCloseAll = c;}
	public boolean getCloseObjectsAfterRun() {return runDoesCloseAll;}


	static class main implements IEnumerable {
		com.altova.mapforce.IMFNode var1_product_feed_Instance;

		public main(com.altova.mapforce.IMFNode var1_product_feed_Instance)	{
			this.var1_product_feed_Instance = var1_product_feed_Instance;
		}

		public IEnumerator enumerator() {return new Enumerator(this);}

		public static class Enumerator implements IEnumerator {
			int pos = 0;
			int state = 2;
			Object current;
			main closure;

			public Enumerator(main closure) {
				this.closure = closure;
			}

			public Object current() {return current;}

			public int position() {return pos;}

			public boolean moveNext() throws Exception {
				while (state != 0) {
					switch (state) {
						case 2: if (moveNext_2()) return true; break;
					}
				}
				return false;
			}

			private boolean moveNext_2() throws Exception {
				state = 0;				
				current = com.altova.functions.Core.createElement(com.altova.functions.Core.createQName("catalog", "http://www.demandware.com/xml/impex/catalog/2006-10-31"), (new seq1_join(closure.var1_product_feed_Instance)));
				pos++;
				return true;
			}


			public void close() {
			}
		}
	}
	static class seq1_join implements IEnumerable {
		com.altova.mapforce.IMFNode var1_product_feed_Instance;

		public seq1_join(com.altova.mapforce.IMFNode var1_product_feed_Instance)	{
			this.var1_product_feed_Instance = var1_product_feed_Instance;
		}

		public IEnumerator enumerator() {return new Enumerator(this);}

		public static class Enumerator implements IEnumerator {
			int pos = 0;
			int state = 1;
			Object current;
			seq1_join closure;
			IEnumerator var2_filter;
			IEnumerator var3_object_member_by_name;
			IEnumerator var4_as_array;

			public Enumerator(seq1_join closure) {
				this.closure = closure;
			}

			public Object current() {return current;}

			public int position() {return pos;}

			public boolean moveNext() throws Exception {
				while (state != 0) {
					switch (state) {
						case 1: if (moveNext_1()) return true; break;
						case 2: if (moveNext_2()) return true; break;
						case 3: if (moveNext_3()) return true; break;
						case 4: if (moveNext_4()) return true; break;
						case 5: if (moveNext_5()) return true; break;
						case 6: if (moveNext_6()) return true; break;
						case 7: if (moveNext_7()) return true; break;
						case 8: if (moveNext_8()) return true; break;
					}
				}
				return false;
			}

			private boolean moveNext_1() throws Exception {
				state = 2;				
				current = com.altova.functions.Core.createAttribute(com.altova.functions.Core.createQName("xsi:schemaLocation", "http://www.w3.org/2001/XMLSchema-instance"), com.altova.functions.Core.box("http://www.demandware.com/xml/impex/catalog/2006-10-31 file:///C:/Users/admin/Desktop/output%20files/catalog.xsd"));
				pos++;
				return true;
			}
			private boolean moveNext_2() throws Exception {
				state = 3;				
				var2_filter = ((new seq2_join(closure.var1_product_feed_Instance))).enumerator();
				return false;
			}
			private boolean moveNext_3() throws Exception {
				state = 5;				
				if (!var2_filter.moveNext()) {state = 4; return false; }
				if (!(com.altova.functions.Json.IsA(((com.altova.mapforce.IMFNode)(var2_filter.current())), "file:///C:/Users/admin/Desktop/output%20files/product-feed.schema.json#//definitions//object_04/@64"))) {state = 3; return false; }
				var3_object_member_by_name = (com.altova.functions.Json.GetMemberByName(((com.altova.mapforce.IMFNode)(var2_filter.current())), "attributes")).enumerator();
				return false;
			}
			private boolean moveNext_4() throws Exception {
				state = 0;				
				if( var2_filter != null ) { var2_filter.close(); var2_filter = null; }
				return false;
			}
			private boolean moveNext_5() throws Exception {
				state = 7;				
				if (!var3_object_member_by_name.moveNext()) {state = 6; return false; }
				var4_as_array = (com.altova.functions.Json.AsArray(com.altova.functions.Json.GetMemberValue(((com.altova.mapforce.IMFNode)(var3_object_member_by_name.current()))))).enumerator();
				return false;
			}
			private boolean moveNext_6() throws Exception {
				state = 3;				
				if( var3_object_member_by_name != null ) { var3_object_member_by_name.close(); var3_object_member_by_name = null; }
				return false;
			}
			private boolean moveNext_7() throws Exception {
				state = 7;				
				if (!var4_as_array.moveNext()) {state = 8; return false; }
				current = com.altova.functions.Core.createElement(com.altova.functions.Core.createQName("product-attribute-definitions", "http://www.demandware.com/xml/impex/catalog/2006-10-31"), (new seq3_join(((com.altova.mapforce.IMFNode)(var4_as_array.current())))));
				pos++;
				return true;
			}
			private boolean moveNext_8() throws Exception {
				state = 5;				
				if( var4_as_array != null ) { var4_as_array.close(); var4_as_array = null; }
				return false;
			}


			public void close() {
				try {
					if( var4_as_array != null ) { var4_as_array.close(); var4_as_array = null; }
					if( var3_object_member_by_name != null ) { var3_object_member_by_name.close(); var3_object_member_by_name = null; }
					if( var2_filter != null ) { var2_filter.close(); var2_filter = null; }
				} catch (Exception e) {
				}
			}
		}
	}
	static class seq2_join implements IEnumerable {
		com.altova.mapforce.IMFNode var1_product_feed_Instance;

		public seq2_join(com.altova.mapforce.IMFNode var1_product_feed_Instance)	{
			this.var1_product_feed_Instance = var1_product_feed_Instance;
		}

		public IEnumerator enumerator() {return new Enumerator(this);}

		public static class Enumerator implements IEnumerator {
			int pos = 0;
			int state = 1;
			Object current;
			seq2_join closure;
			IEnumerator var2_as_array;
			IEnumerator var3_array_items;
			IEnumerator var4_as_object;

			public Enumerator(seq2_join closure) {
				this.closure = closure;
			}

			public Object current() {return current;}

			public int position() {return pos;}

			public boolean moveNext() throws Exception {
				while (state != 0) {
					switch (state) {
						case 1: if (moveNext_1()) return true; break;
						case 2: if (moveNext_2()) return true; break;
						case 3: if (moveNext_3()) return true; break;
						case 4: if (moveNext_4()) return true; break;
						case 5: if (moveNext_5()) return true; break;
						case 6: if (moveNext_6()) return true; break;
						case 7: if (moveNext_7()) return true; break;
					}
				}
				return false;
			}

			private boolean moveNext_1() throws Exception {
				state = 2;				
				var2_as_array = (com.altova.functions.Json.AsArray(com.altova.functions.Json.RootValue(closure.var1_product_feed_Instance))).enumerator();
				return false;
			}
			private boolean moveNext_2() throws Exception {
				state = 4;				
				if (!var2_as_array.moveNext()) {state = 3; return false; }
				var3_array_items = (com.altova.functions.Json.GetArrayItems(((com.altova.mapforce.IMFNode)(var2_as_array.current())))).enumerator();
				return false;
			}
			private boolean moveNext_3() throws Exception {
				state = 0;				
				if( var2_as_array != null ) { var2_as_array.close(); var2_as_array = null; }
				return false;
			}
			private boolean moveNext_4() throws Exception {
				state = 6;				
				if (!var3_array_items.moveNext()) {state = 5; return false; }
				var4_as_object = (com.altova.functions.Json.AsObject(((com.altova.mapforce.IMFNode)(var3_array_items.current())))).enumerator();
				return false;
			}
			private boolean moveNext_5() throws Exception {
				state = 2;				
				if( var3_array_items != null ) { var3_array_items.close(); var3_array_items = null; }
				return false;
			}
			private boolean moveNext_6() throws Exception {
				state = 6;				
				if (!var4_as_object.moveNext()) {state = 7; return false; }
				current = var4_as_object.current();
				pos++;
				return true;
			}
			private boolean moveNext_7() throws Exception {
				state = 4;				
				if( var4_as_object != null ) { var4_as_object.close(); var4_as_object = null; }
				return false;
			}


			public void close() {
				try {
					if( var4_as_object != null ) { var4_as_object.close(); var4_as_object = null; }
					if( var3_array_items != null ) { var3_array_items.close(); var3_array_items = null; }
					if( var2_as_array != null ) { var2_as_array.close(); var2_as_array = null; }
				} catch (Exception e) {
				}
			}
		}
	}
	static class seq3_join implements IEnumerable {
		com.altova.mapforce.IMFNode var1_cur;

		public seq3_join(com.altova.mapforce.IMFNode var1_cur)	{
			this.var1_cur = var1_cur;
		}

		public IEnumerator enumerator() {return new Enumerator(this);}

		public static class Enumerator implements IEnumerator {
			int pos = 0;
			int state = 1;
			Object current;
			seq3_join closure;
			IEnumerator var2_array_items;
			IEnumerator var3_as_object;
			com.altova.mapforce.IEnumerable var4_box;

			public Enumerator(seq3_join closure) {
				this.closure = closure;
			}

			public Object current() {return current;}

			public int position() {return pos;}

			public boolean moveNext() throws Exception {
				while (state != 0) {
					switch (state) {
						case 1: if (moveNext_1()) return true; break;
						case 2: if (moveNext_2()) return true; break;
						case 3: if (moveNext_3()) return true; break;
						case 4: if (moveNext_4()) return true; break;
						case 5: if (moveNext_5()) return true; break;
					}
				}
				return false;
			}

			private boolean moveNext_1() throws Exception {
				state = 2;				
				var2_array_items = (com.altova.functions.Json.GetArrayItems(closure.var1_cur)).enumerator();
				return false;
			}
			private boolean moveNext_2() throws Exception {
				state = 4;				
				if (!var2_array_items.moveNext()) {state = 3; return false; }
				var3_as_object = (com.altova.functions.Json.AsObject(((com.altova.mapforce.IMFNode)(var2_array_items.current())))).enumerator();
				return false;
			}
			private boolean moveNext_3() throws Exception {
				state = 0;				
				if( var2_array_items != null ) { var2_array_items.close(); var2_array_items = null; }
				return false;
			}
			private boolean moveNext_4() throws Exception {
				state = 4;				
				if (!var3_as_object.moveNext()) {state = 5; return false; }
				var4_box = new com.altova.functions.Core.SequenceCache(com.altova.functions.Core.box(com.altova.CoreTypes.booleanToString(com.altova.CoreTypes.parseBoolean("false"))));
				current = com.altova.functions.Core.createElement(com.altova.functions.Core.createQName("attribute-definition", "http://www.demandware.com/xml/impex/catalog/2006-10-31"), (new seq4_join(var4_box, ((com.altova.mapforce.IMFNode)(var3_as_object.current())))));
				pos++;
				return true;
			}
			private boolean moveNext_5() throws Exception {
				state = 2;				
				if( var3_as_object != null ) { var3_as_object.close(); var3_as_object = null; }
				return false;
			}


			public void close() {
				try {
					if( var3_as_object != null ) { var3_as_object.close(); var3_as_object = null; }
					if( var2_array_items != null ) { var2_array_items.close(); var2_array_items = null; }
				} catch (Exception e) {
				}
			}
		}
	}
	static class seq4_join implements IEnumerable {
		com.altova.mapforce.IEnumerable var1_box;
		com.altova.mapforce.IMFNode var2_cur;

		public seq4_join(com.altova.mapforce.IEnumerable var1_box, com.altova.mapforce.IMFNode var2_cur)	{
			this.var1_box = var1_box;
			this.var2_cur = var2_cur;
		}

		public IEnumerator enumerator() {return new Enumerator(this);}

		public static class Enumerator implements IEnumerator {
			int pos = 0;
			int state = 1;
			Object current;
			seq4_join closure;
			IEnumerator var3_object_member_by_name;
			IEnumerator var4_as_string;
			IEnumerator var6_object_member_by_name;
			java.lang.String var5_char_from_code;
			IEnumerator var7_as_string;
			IEnumerator var8_object_member_by_name;
			IEnumerator var9_as_string;
			IEnumerator var10_object_member_by_name;
			IEnumerator var11_as_string;

			public Enumerator(seq4_join closure) {
				this.closure = closure;
			}

			public Object current() {return current;}

			public int position() {return pos;}

			public boolean moveNext() throws Exception {
				while (state != 0) {
					switch (state) {
						case 1: if (moveNext_1()) return true; break;
						case 2: if (moveNext_2()) return true; break;
						case 3: if (moveNext_3()) return true; break;
						case 4: if (moveNext_4()) return true; break;
						case 5: if (moveNext_5()) return true; break;
						case 7: if (moveNext_7()) return true; break;
						case 8: if (moveNext_8()) return true; break;
						case 9: if (moveNext_9()) return true; break;
						case 10: if (moveNext_10()) return true; break;
						case 12: if (moveNext_12()) return true; break;
						case 13: if (moveNext_13()) return true; break;
						case 14: if (moveNext_14()) return true; break;
						case 15: if (moveNext_15()) return true; break;
						case 17: if (moveNext_17()) return true; break;
						case 18: if (moveNext_18()) return true; break;
						case 19: if (moveNext_19()) return true; break;
						case 20: if (moveNext_20()) return true; break;
						case 22: if (moveNext_22()) return true; break;
						case 23: if (moveNext_23()) return true; break;
						case 24: if (moveNext_24()) return true; break;
					}
				}
				return false;
			}

			private boolean moveNext_1() throws Exception {
				state = 2;				
				var3_object_member_by_name = (com.altova.functions.Json.GetMemberByName(closure.var2_cur, "salsify:id")).enumerator();
				return false;
			}
			private boolean moveNext_2() throws Exception {
				state = 4;				
				if (!var3_object_member_by_name.moveNext()) {state = 3; return false; }
				var4_as_string = (com.altova.functions.Json.AsString(com.altova.functions.Json.GetMemberValue(((com.altova.mapforce.IMFNode)(var3_object_member_by_name.current()))))).enumerator();
				return false;
			}
			private boolean moveNext_3() throws Exception {
				state = 7;				
				if( var3_object_member_by_name != null ) { var3_object_member_by_name.close(); var3_object_member_by_name = null; }
				var6_object_member_by_name = (com.altova.functions.Json.GetMemberByName(closure.var2_cur, "salsify:name")).enumerator();
				return false;
			}
			private boolean moveNext_4() throws Exception {
				state = 4;				
				if (!var4_as_string.moveNext()) {state = 5; return false; }
				var5_char_from_code = com.altova.functions.Core.charFromCode(com.altova.CoreTypes.decimalToInteger(new java.math.BigDecimal("32")));
				current = com.altova.functions.Core.createAttribute(com.altova.functions.Core.createQName("attribute-id", ""), com.altova.functions.Core.box(com.altova.functions.Lang.replace(com.altova.functions.Core.normalizeSpace(com.altova.functions.Lang.replace(((java.lang.String)(var4_as_string.current())), "-", var5_char_from_code)), var5_char_from_code, "-")));
				pos++;
				return true;
			}
			private boolean moveNext_5() throws Exception {
				state = 2;				
				if( var4_as_string != null ) { var4_as_string.close(); var4_as_string = null; }
				return false;
			}
			private boolean moveNext_7() throws Exception {
				state = 9;				
				if (!var6_object_member_by_name.moveNext()) {state = 8; return false; }
				var7_as_string = (com.altova.functions.Json.AsString(com.altova.functions.Json.GetMemberValue(((com.altova.mapforce.IMFNode)(var6_object_member_by_name.current()))))).enumerator();
				return false;
			}
			private boolean moveNext_8() throws Exception {
				state = 12;				
				if( var6_object_member_by_name != null ) { var6_object_member_by_name.close(); var6_object_member_by_name = null; }
				var8_object_member_by_name = (com.altova.functions.Json.GetMemberByName(closure.var2_cur, "salsify:help_text")).enumerator();
				return false;
			}
			private boolean moveNext_9() throws Exception {
				state = 9;				
				if (!var7_as_string.moveNext()) {state = 10; return false; }
				current = com.altova.functions.Core.createElement(com.altova.functions.Core.createQName("display-name", "http://www.demandware.com/xml/impex/catalog/2006-10-31"), com.altova.functions.Core.box(((java.lang.String)(var7_as_string.current()))));
				pos++;
				return true;
			}
			private boolean moveNext_10() throws Exception {
				state = 7;				
				if( var7_as_string != null ) { var7_as_string.close(); var7_as_string = null; }
				return false;
			}
			private boolean moveNext_12() throws Exception {
				state = 14;				
				if (!var8_object_member_by_name.moveNext()) {state = 13; return false; }
				var9_as_string = (com.altova.functions.Json.AsString(com.altova.functions.Json.GetMemberValue(((com.altova.mapforce.IMFNode)(var8_object_member_by_name.current()))))).enumerator();
				return false;
			}
			private boolean moveNext_13() throws Exception {
				state = 17;				
				if( var8_object_member_by_name != null ) { var8_object_member_by_name.close(); var8_object_member_by_name = null; }
				var10_object_member_by_name = (com.altova.functions.Json.GetMemberByName(closure.var2_cur, "salsify:data_type")).enumerator();
				return false;
			}
			private boolean moveNext_14() throws Exception {
				state = 14;				
				if (!var9_as_string.moveNext()) {state = 15; return false; }
				current = com.altova.functions.Core.createElement(com.altova.functions.Core.createQName("description", "http://www.demandware.com/xml/impex/catalog/2006-10-31"), com.altova.functions.Core.box(((java.lang.String)(var9_as_string.current()))));
				pos++;
				return true;
			}
			private boolean moveNext_15() throws Exception {
				state = 12;				
				if( var9_as_string != null ) { var9_as_string.close(); var9_as_string = null; }
				return false;
			}
			private boolean moveNext_17() throws Exception {
				state = 19;				
				if (!var10_object_member_by_name.moveNext()) {state = 18; return false; }
				var11_as_string = (com.altova.functions.Json.AsString(com.altova.functions.Json.GetMemberValue(((com.altova.mapforce.IMFNode)(var10_object_member_by_name.current()))))).enumerator();
				return false;
			}
			private boolean moveNext_18() throws Exception {
				state = 22;				
				if( var10_object_member_by_name != null ) { var10_object_member_by_name.close(); var10_object_member_by_name = null; }
				current = com.altova.functions.Core.createElement(com.altova.functions.Core.createQName("site-specific-flag", "http://www.demandware.com/xml/impex/catalog/2006-10-31"), closure.var1_box);
				pos++;
				return true;
			}
			private boolean moveNext_19() throws Exception {
				state = 19;				
				if (!var11_as_string.moveNext()) {state = 20; return false; }
				current = com.altova.functions.Core.createElement(com.altova.functions.Core.createQName("type", "http://www.demandware.com/xml/impex/catalog/2006-10-31"), com.altova.functions.Core.box(((java.lang.String)(var11_as_string.current()))));
				pos++;
				return true;
			}
			private boolean moveNext_20() throws Exception {
				state = 17;				
				if( var11_as_string != null ) { var11_as_string.close(); var11_as_string = null; }
				return false;
			}
			private boolean moveNext_22() throws Exception {
				state = 23;				
				current = com.altova.functions.Core.createElement(com.altova.functions.Core.createQName("mandatory-flag", "http://www.demandware.com/xml/impex/catalog/2006-10-31"), closure.var1_box);
				pos++;
				return true;
			}
			private boolean moveNext_23() throws Exception {
				state = 24;				
				current = com.altova.functions.Core.createElement(com.altova.functions.Core.createQName("visible-flag", "http://www.demandware.com/xml/impex/catalog/2006-10-31"), com.altova.functions.Core.box(com.altova.CoreTypes.booleanToString(com.altova.CoreTypes.parseBoolean("true"))));
				pos++;
				return true;
			}
			private boolean moveNext_24() throws Exception {
				state = 0;				
				current = com.altova.functions.Core.createElement(com.altova.functions.Core.createQName("externally-managed-flag", "http://www.demandware.com/xml/impex/catalog/2006-10-31"), closure.var1_box);
				pos++;
				return true;
			}


			public void close() {
				try {
					if( var4_as_string != null ) { var4_as_string.close(); var4_as_string = null; }
					if( var3_object_member_by_name != null ) { var3_object_member_by_name.close(); var3_object_member_by_name = null; }
					if( var7_as_string != null ) { var7_as_string.close(); var7_as_string = null; }
					if( var6_object_member_by_name != null ) { var6_object_member_by_name.close(); var6_object_member_by_name = null; }
					if( var9_as_string != null ) { var9_as_string.close(); var9_as_string = null; }
					if( var8_object_member_by_name != null ) { var8_object_member_by_name.close(); var8_object_member_by_name = null; }
					if( var11_as_string != null ) { var11_as_string.close(); var11_as_string = null; }
					if( var10_object_member_by_name != null ) { var10_object_member_by_name.close(); var10_object_member_by_name = null; }
				} catch (Exception e) {
				}
			}
		}
	}
	static class Outer implements IEnumerable {
		com.altova.mapforce.IMFNode var1_product_feed_Instance;

		public Outer(com.altova.mapforce.IMFNode var1_product_feed_Instance)	{
			this.var1_product_feed_Instance = var1_product_feed_Instance;
		}

		public IEnumerator enumerator() {return new Enumerator(this);}

		public static class Enumerator implements IEnumerator {
			int pos = 0;
			int state = 1;
			Object current;
			Outer closure;
			IEnumerator var2_box;

			public Enumerator(Outer closure) {
				this.closure = closure;
			}

			public Object current() {return current;}

			public int position() {return pos;}

			public boolean moveNext() throws Exception {
				while (state != 0) {
					switch (state) {
						case 1: if (moveNext_1()) return true; break;
						case 2: if (moveNext_2()) return true; break;
						case 3: if (moveNext_3()) return true; break;
					}
				}
				return false;
			}

			private boolean moveNext_1() throws Exception {
				state = 2;				
				var2_box = ((new main(closure.var1_product_feed_Instance))).enumerator();
				return false;
			}
			private boolean moveNext_2() throws Exception {
				state = 2;				
				if (!var2_box.moveNext()) {state = 3; return false; }
				current = var2_box.current();
				pos++;
				return true;
			}
			private boolean moveNext_3() throws Exception {
				state = 0;				
				if( var2_box != null ) { var2_box.close(); var2_box = null; }
				return false;
			}


			public void close() {
				try {
					if( var2_box != null ) { var2_box.close(); var2_box = null; }
				} catch (Exception e) {
				}
			}
		}
	}


	// instances
	protected com.altova.json.Document varproduct_feed2Instance;

	public void run(String product_feed2SourceFilename, String catalog2TargetFilename) throws Exception {
		try(
			// open source streams
			com.altova.io.FileInput product_feed2Source = new com.altova.io.FileInput(product_feed2SourceFilename);
		// open target stream
			com.altova.io.FileOutput catalog2Target = new com.altova.io.FileOutput(catalog2TargetFilename);

		) {
			// run
			run(product_feed2Source, catalog2Target);

		}
	}


	// main entry point

	public void run(com.altova.io.Input product_feed2Source, com.altova.io.Output catalog2Target) throws Exception {
		// Open the source(s)
			varproduct_feed2Instance = com.altova.functions.Json.Load(product_feed2Source, MapForceJsonLibs_product_feed.Schemas, false, false);

		// Create the target
		org.w3c.dom.Document catalog2Doc = (catalog2Target.getType() == com.altova.io.Output.IO_DOM) ? catalog2Target.getDocument() : XmlTreeOperations.createDocument();

		// Execute mapping

		main mapping = new main(varproduct_feed2Instance);

		com.altova.xml.MFDOMWriter.write(mapping, catalog2Doc);
		// Close the target
		XmlTreeOperations.saveDocument(catalog2Doc, catalog2Target, "UTF-8", false, false, true, false);


		if (runDoesCloseAll) {
			catalog2Target.close();
		}
	}



}
