package com.github.sharplog.jsona;

import static org.junit.Assert.assertEquals;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class JSONATest {
	JSONA jsona = new JSONA();
	
	 // a object
	@Test
	public void test1(){
		String js = "{\"name\":\"Rose\",\"gender\":\"female\",\"height\":150}";
		JSONObject jo = JSONObject.fromObject(js);
		
		JSONObject	ja = (JSONObject)jsona.toJSONA(jo);
		System.out.println(ja.toString());
		assertEquals(js, ja.toString());
		
		JSONObject joo = (JSONObject)jsona.fromJSONA(ja);
		System.out.println(joo.toString());
		assertEquals(js, joo.toString());
	}
	
	// a object array, with embeded array
	@Test
	public void test2(){
		String js = "[{\"name\":\"Rose\",\"gender\":\"female\",\"height\":[123,150]}," +
							"{\"name\":\"John\",\"gender\":\"male\",\"height\":[120,152]}]";
		String as = "[[\"@\",\"name\",\"gender\",\"height\"],[\"Rose\",\"female\",[123,150]],[\"John\",\"male\",[120,152]]]";
		JSONArray jo = JSONArray.fromObject(js);
		
		JSONArray	ja = (JSONArray)jsona.toJSONA(jo);
		System.out.println(ja.toString());
		assertEquals(as, ja.toString());
		
		JSONArray joo = (JSONArray)jsona.fromJSONA(ja);
		System.out.println(joo.toString());
		assertEquals(js, joo.toString());
	}
	
	// a object array with embeded object
	@Test
	public void test3(){
		String js = "[{\"name\":\"Rose\",\"gender\":\"female\",\"scores\":{\"math\":90,\"music\":85},\"height\":150}," +
						"{\"name\":\"John\",\"gender\":\"male\",\"scores\":{\"math\":95,\"music\":80},\"height\":152}]";
		String as = "[[\"@\",\"name\",\"gender\",\"scores\",[\"math\",\"music\"],\"height\"],[\"Rose\",\"female\",[90,85],150],[\"John\",\"male\",[95,80],152]]";
		JSONArray jo = JSONArray.fromObject(js);
		
		JSONArray	ja = (JSONArray)jsona.toJSONA(jo);
		System.out.println(ja.toString());
		assertEquals(as, ja.toString());
		
		JSONArray joo = (JSONArray)jsona.fromJSONA(ja);
		System.out.println(joo.toString());
		assertEquals(js, joo.toString());
	}
	
	// a object array with multi-level embeded object
	@Test
	public void test4(){
		String js = "[{\"name\":\"Rose\",\"gender\":\"female\",\"scores\":{\"math\":{\"algebra\":90,\"geometry\":92},\"music\":85},\"height\":150}," +
						"{\"name\":\"John\",\"gender\":\"male\",\"scores\":{\"math\":{\"algebra\":95,\"geometry\":89},\"music\":80},\"height\":152}]";
		String as = "[[\"@\",\"name\",\"gender\",\"scores\",[\"math\",[\"algebra\",\"geometry\"],\"music\"],\"height\"],[\"Rose\",\"female\",[[90,92],85],150],[\"John\",\"male\",[[95,89],80],152]]";
		JSONArray jo = JSONArray.fromObject(js);
		
		JSONArray	ja = (JSONArray)jsona.toJSONA(jo);
		System.out.println(ja.toString());
		assertEquals(as, ja.toString());
		
		JSONArray joo = (JSONArray)jsona.fromJSONA(ja);
		System.out.println(joo.toString());
		assertEquals(js, joo.toString());
	}
	
	// object arry in a object
	@Test
	public void test5(){
		String js = "{\"name\":\"test\",\"data\":[{\"name\":\"Rose\",\"gender\":\"female\",\"scores\":{\"math\":{\"algebra\":90,\"geometry\":92},\"music\":85},\"height\":150}," +
						"{\"name\":\"John\",\"gender\":\"male\",\"scores\":{\"math\":{\"algebra\":95,\"geometry\":89},\"music\":80},\"height\":152}]}";
		String as = "{\"name\":\"test\",\"data\":[[\"@\",\"name\",\"gender\",\"scores\",[\"math\",[\"algebra\",\"geometry\"],\"music\"],\"height\"],[\"Rose\",\"female\",[[90,92],85],150],[\"John\",\"male\",[[95,89],80],152]]}";
		JSONObject jo = JSONObject.fromObject(js);
		
		JSONObject	ja = (JSONObject)jsona.toJSONA(jo);
		System.out.println(ja.toString());
		assertEquals(as, ja.toString());
		
		JSONObject joo = (JSONObject)jsona.fromJSONA(ja);
		System.out.println(joo.toString());
		assertEquals(js, joo.toString());
	}
	
	// object arry in a array
	@Test
	public void test6(){
		String js = "[\"test\",[{\"name\":\"Rose\",\"gender\":\"female\",\"scores\":{\"math\":{\"algebra\":90,\"geometry\":92},\"music\":85},\"height\":150}," +
						"{\"name\":\"John\",\"gender\":\"male\",\"scores\":{\"math\":{\"algebra\":95,\"geometry\":89},\"music\":80},\"height\":152}],1234]";
		String as = "[\"test\",[[\"@\",\"name\",\"gender\",\"scores\",[\"math\",[\"algebra\",\"geometry\"],\"music\"],\"height\"],[\"Rose\",\"female\",[[90,92],85],150],[\"John\",\"male\",[[95,89],80],152]],1234]";
		JSONArray jo = JSONArray.fromObject(js);
		
		JSONArray	ja = (JSONArray)jsona.toJSONA(jo);
		System.out.println(ja.toString());
		assertEquals(as, ja.toString());
		
		JSONArray joo = (JSONArray)jsona.fromJSONA(ja);
		System.out.println(js);
		System.out.println(joo.toString());
		assertEquals(js, joo.toString());
	}
	
	// object arry in a object array
	@Test
	public void test7(){
		String js = "[{\"name\":\"test1\",\"data\":[{\"name\":\"Rose\",\"gender\":\"female\",\"scores\":{\"math\":{\"algebra\":90,\"geometry\":92},\"music\":85},\"height\":150}," +
																  "{\"name\":\"John\",\"gender\":\"male\",\"scores\":{\"math\":{\"algebra\":95,\"geometry\":89},\"music\":80},\"height\":152}],\"size\":1234}," +
						  "{\"name\":\"test2\",\"data\":[{\"name\":\"Rose\",\"gender\":\"female\",\"scores\":{\"math\":{\"algebra\":90,\"geometry\":92},\"music\":85},\"height\":150}," +
						  										  "{\"name\":\"John\",\"gender\":\"male\",\"scores\":{\"math\":{\"algebra\":95,\"geometry\":89},\"music\":80},\"height\":152}],\"size\":5678}]";
		String as = "[[\"@\",\"name\",\"data\",\"size\"]," + 
						  	"[\"test1\",[[\"@\",\"name\",\"gender\",\"scores\",[\"math\",[\"algebra\",\"geometry\"],\"music\"],\"height\"],[\"Rose\",\"female\",[[90,92],85],150],[\"John\",\"male\",[[95,89],80],152]],1234]," + 
						  	"[\"test2\",[[\"@\",\"name\",\"gender\",\"scores\",[\"math\",[\"algebra\",\"geometry\"],\"music\"],\"height\"],[\"Rose\",\"female\",[[90,92],85],150],[\"John\",\"male\",[[95,89],80],152]],5678]]";
		JSONArray jo = JSONArray.fromObject(js);
		
		JSONArray	ja = (JSONArray)jsona.toJSONA(jo);
		System.out.println(ja.toString());
		assertEquals(as, ja.toString());
		
		JSONArray joo = (JSONArray)jsona.fromJSONA(ja);
		System.out.println(joo.toString());
		assertEquals(js, joo.toString());
	}
	
	// customized symbol
	@Test
	public void test8(){
		JSONA jsona = new JSONA("$#@");

		String js = "[{\"name\":\"Rose\",\"gender\":\"female\",\"height\":[123,150]}," +
							"{\"name\":\"John\",\"gender\":\"male\",\"height\":[120,152]}]";
		String as = "[[\"$#@\",\"name\",\"gender\",\"height\"],[\"Rose\",\"female\",[123,150]],[\"John\",\"male\",[120,152]]]";
		JSONArray jo = JSONArray.fromObject(js);
		
		JSONArray	ja = (JSONArray)jsona.toJSONA(jo);
		System.out.println(ja.toString());
		assertEquals(as, ja.toString());
		
		JSONArray joo = (JSONArray)jsona.fromJSONA(ja);
		System.out.println(joo.toString());
		assertEquals(js, joo.toString());
	}
}
