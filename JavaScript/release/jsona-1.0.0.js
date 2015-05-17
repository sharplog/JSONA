(function(window) {

var defaultSymbol = "@";

var JSONA = function(symbol){
	this.symbol = symbol ? symbol : defaultSymbol;
}

/**
 * Transform a JSONA data object to a normal JSON data object.
 */
JSONA.prototype.fromJSONA = function(jsona){
	
	if( jsona instanceof Array && jsona.length > 1){
		if( jsona[0] instanceof Array && jsona[0][0] === this.symbol ) {
			var fields = jsona.shift();
			fields.shift();		// shift symbol

			var objects = []
			for( var i in jsona){
				objects.push( this.transObjectFromJSONA(fields, jsona[i]) );
			}

			return objects;
		}

		for(var i in jsona) jsona[i] = this.fromJSONA(jsona[i]);
	}
	else if( jsona instanceof Object){
		for(var e in jsona) jsona[e] = this.fromJSONA(jsona[e]);
	}

	return jsona;
}

/**
 * Transform a JSONA data object to a normal JSON object.
 */
JSONA.prototype.transObjectFromJSONA = function(fields, values){
	var object = {};

	for(var i=0, j=0, l=fields.length; i<l; i++, j++){
		// object field
		if( i+1 < l && fields[i+1] instanceof Array){
			object[fields[i]] = this.transObjectFromJSONA(fields[i+1], values[j]);
			i++;
		}
		else{
			object[fields[i]] = this.fromJSONA(values[j]);
		}
	}

	return object;
}

/**
 * Transform a normal JSON data object to a JSONA data object.
 */
JSONA.prototype.toJSONA = function(json){
	if( json instanceof Array && json.length > 1 ){
		if( !(json[0] instanceof Array) && json[0] instanceof Object ){
			var fields = this.transFieldsFromJSON(json[0]);
			fields.unshift(this.symbol);

			var objects = [fields];
			for( var i in json){
				objects.push( this.transValuesFromJSON(json[i]));
			}

			return objects;
		}

		for(var i in json) json[i] = this.toJSONA(json[i]);
	}
	else if( json instanceof Object){
		for(var e in json) json[e] = this.toJSONA(json[e]);
	}

	return json;
}

/**
 * Get all property's names of normal JSON object, including embeded object.
 */
JSONA.prototype.transFieldsFromJSON = function(object){
	var fields = [];
	for( var e in object ){
		fields.push(e);
		if( object[e] instanceof Object && !(object[e] instanceof Array) ){
			fields.push( this.transFieldsFromJSON(object[e]) );
		}
	}
	return fields;
}

/**
 * Get all property's values of normal JSON object, including embeded object.
 */
JSONA.prototype.transValuesFromJSON = function(object){
	var values = [];
	for( var e in object ){
		if( object[e] instanceof Object && !(object[e] instanceof Array) ){
			values.push( this.transValuesFromJSON(object[e]) );
		}
		else{
			values.push( this.toJSONA(object[e]) );
		}
	}
	return values;
}

window.JSONA = function(symbol){
	return new JSONA(symbol);
}

})(window);
