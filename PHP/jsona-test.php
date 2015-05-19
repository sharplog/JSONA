<?php
include "jsona.php";

/**
 * Unit test.
 */

$jsona = new JSONA();

function assertEquals($s1, $s2){
	if($s1===$s2){
		print 'true<br>';
	}
	else{
		print $s1 . '<br>' . $s2 . '<br>false<br>';
	}
	
}

 // a object
function test1(){
	global $jsona;
	print "<br>==== test1 ====<br>";

	$js = "{\"name\":\"Rose\",\"gender\":\"female\",\"height\":150}";
	$jo = json_decode($js);
	
	$ja = $jsona->toJSONA($jo);

	assertEquals($js, json_encode($ja));
	
	$joo = $jsona->fromJSONA($ja);

	assertEquals($js, json_encode($joo));
}

// a object array, with embeded array
function test2(){
	global $jsona;
	print "<br>==== test2 ====<br>";
	
	$js = "[{\"name\":\"Rose\",\"gender\":\"female\",\"height\":[123,150]}," .
						"{\"name\":\"John\",\"gender\":\"male\",\"height\":[120,152]}]";
	$as = "[[\"@\",\"name\",\"gender\",\"height\"],[\"Rose\",\"female\",[123,150]],[\"John\",\"male\",[120,152]]]";
	$jo = json_decode($js);

	$ja = $jsona->toJSONA($jo);

	assertEquals($as, json_encode($ja));
	
	$joo = $jsona->fromJSONA($ja);

	assertEquals($js, json_encode($joo));
}

// a object array with embeded object
function test3(){
	global $jsona;
	print "<br>==== test3 ====<br>";
	
	$js = "[{\"name\":\"Rose\",\"gender\":\"female\",\"scores\":{\"math\":90,\"music\":85},\"height\":150}," .
					"{\"name\":\"John\",\"gender\":\"male\",\"scores\":{\"math\":95,\"music\":80},\"height\":152}]";
	$as = "[[\"@\",\"name\",\"gender\",\"scores\",[\"math\",\"music\"],\"height\"],[\"Rose\",\"female\",[90,85],150],[\"John\",\"male\",[95,80],152]]";
	$jo = json_decode($js);
	
	$ja = $jsona->toJSONA($jo);

	assertEquals($as, json_encode($ja));
	
	$joo = $jsona->fromJSONA($ja);

	assertEquals($js, json_encode($joo));
}

// a object array with multi-level embeded object
function test4(){
	global $jsona;
	print "<br>==== test4 ====<br>";
	
	$js = "[{\"name\":\"Rose\",\"gender\":\"female\",\"scores\":{\"math\":{\"algebra\":90,\"geometry\":92},\"music\":85},\"height\":150}," .
					"{\"name\":\"John\",\"gender\":\"male\",\"scores\":{\"math\":{\"algebra\":95,\"geometry\":89},\"music\":80},\"height\":152}]";
	$as = "[[\"@\",\"name\",\"gender\",\"scores\",[\"math\",[\"algebra\",\"geometry\"],\"music\"],\"height\"],[\"Rose\",\"female\",[[90,92],85],150],[\"John\",\"male\",[[95,89],80],152]]";
	$jo = json_decode($js);
	
	$ja = $jsona->toJSONA($jo);

	assertEquals($as, json_encode($ja));
	
	$joo = $jsona->fromJSONA($ja);

	assertEquals($js, json_encode($joo));
}

// object arry in a object
function test5(){
	global $jsona;
	print "<br>==== test5 ====<br>";
	
	$js = "{\"name\":\"test\",\"data\":[{\"name\":\"Rose\",\"gender\":\"female\",\"scores\":{\"math\":{\"algebra\":90,\"geometry\":92},\"music\":85},\"height\":150}," .
					"{\"name\":\"John\",\"gender\":\"male\",\"scores\":{\"math\":{\"algebra\":95,\"geometry\":89},\"music\":80},\"height\":152}]}";
	$as = "{\"name\":\"test\",\"data\":[[\"@\",\"name\",\"gender\",\"scores\",[\"math\",[\"algebra\",\"geometry\"],\"music\"],\"height\"],[\"Rose\",\"female\",[[90,92],85],150],[\"John\",\"male\",[[95,89],80],152]]}";
	$jo = json_decode($js);
	
	$ja = $jsona->toJSONA($jo);

	assertEquals($as, json_encode($ja));
	
	$joo = $jsona->fromJSONA($ja);

	assertEquals($js, json_encode($joo));
}

// object arry in a array
function test6(){
	global $jsona;
	print "<br>==== test6 ====<br>";
	
	$js = "[\"test\",[{\"name\":\"Rose\",\"gender\":\"female\",\"scores\":{\"math\":{\"algebra\":90,\"geometry\":92},\"music\":85},\"height\":150}," .
					"{\"name\":\"John\",\"gender\":\"male\",\"scores\":{\"math\":{\"algebra\":95,\"geometry\":89},\"music\":80},\"height\":152}],1234]";
	$as = "[\"test\",[[\"@\",\"name\",\"gender\",\"scores\",[\"math\",[\"algebra\",\"geometry\"],\"music\"],\"height\"],[\"Rose\",\"female\",[[90,92],85],150],[\"John\",\"male\",[[95,89],80],152]],1234]";
	$jo = json_decode($js);
	
	$ja = $jsona->toJSONA($jo);

	assertEquals($as, json_encode($ja));
	
	$joo = $jsona->fromJSONA($ja);

	assertEquals($js, json_encode($joo));
}

// object arry in a object array
function test7(){
	global $jsona;
	print "<br>==== test7 ====<br>";
	
	$js = "[{\"name\":\"test1\",\"data\":[{\"name\":\"Rose\",\"gender\":\"female\",\"scores\":{\"math\":{\"algebra\":90,\"geometry\":92},\"music\":85},\"height\":150}," .
															  "{\"name\":\"John\",\"gender\":\"male\",\"scores\":{\"math\":{\"algebra\":95,\"geometry\":89},\"music\":80},\"height\":152}],\"size\":1234}," .
					  "{\"name\":\"test2\",\"data\":[{\"name\":\"Rose\",\"gender\":\"female\",\"scores\":{\"math\":{\"algebra\":90,\"geometry\":92},\"music\":85},\"height\":150}," .
					  										  "{\"name\":\"John\",\"gender\":\"male\",\"scores\":{\"math\":{\"algebra\":95,\"geometry\":89},\"music\":80},\"height\":152}],\"size\":5678}]";
	$as = "[[\"@\",\"name\",\"data\",\"size\"]," . 
					  	"[\"test1\",[[\"@\",\"name\",\"gender\",\"scores\",[\"math\",[\"algebra\",\"geometry\"],\"music\"],\"height\"],[\"Rose\",\"female\",[[90,92],85],150],[\"John\",\"male\",[[95,89],80],152]],1234]," . 
					  	"[\"test2\",[[\"@\",\"name\",\"gender\",\"scores\",[\"math\",[\"algebra\",\"geometry\"],\"music\"],\"height\"],[\"Rose\",\"female\",[[90,92],85],150],[\"John\",\"male\",[[95,89],80],152]],5678]]";
	$jo = json_decode($js);
	
	$ja = $jsona->toJSONA($jo);

	assertEquals($as, json_encode($ja));
	
	$joo = $jsona->fromJSONA($ja);

	assertEquals($js, json_encode($joo));
}

// customized symbol
function test8(){
	$jsona = new JSONA("$#@");
	print "<br>==== test8 ====<br>";

	$js = "[{\"name\":\"Rose\",\"gender\":\"female\",\"height\":[123,150]}," .
						"{\"name\":\"John\",\"gender\":\"male\",\"height\":[120,152]}]";
	$as = "[[\"$#@\",\"name\",\"gender\",\"height\"],[\"Rose\",\"female\",[123,150]],[\"John\",\"male\",[120,152]]]";
	$jo = json_decode($js);
	
	$ja = $jsona->toJSONA($jo);

	assertEquals($as, json_encode($ja));
	
	$joo = $jsona->fromJSONA($ja);

	assertEquals($js, json_encode($joo));
}

test1();
test2();
test3();
test4();
test5();
test6();
test7();
test8();
