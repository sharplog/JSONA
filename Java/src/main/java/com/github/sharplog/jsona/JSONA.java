/*!
 * JSONA Java Library v1.0.1
 * https://github.com/sharplog/JSONA
 *
 * Copyright (c) 2015 https://github.com/sharplog
 * Released under the MIT license
 * http://opensource.org/licenses/MIT
 *
 * Date: 2015-06-03
 */
package com.github.sharplog.jsona;

import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONA {
	private String symbol = "@"; // default field symbol

	public JSONA() {
	}

	public JSONA(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Transform a JSONA data object/array to a normal JSON data object/array.
	 */
	public Object fromJSONA(Object jsona) {
		if (jsona instanceof JSONObject) {
			return this.fromJSONA((JSONObject) jsona);
		} else if (jsona instanceof JSONArray) {
			return this.fromJSONA((JSONArray) jsona);
		}
		return jsona;
	}

	/**
	 * Transform a JSONA data object to a normal JSON data object.
	 */
	private JSONObject fromJSONA(JSONObject jsona) {
		Iterator keys = jsona.keys();
		while (keys.hasNext()) {
			Object key = keys.next();
			Object value = jsona.get(key);
			jsona.put(key, this.fromJSONA(value));
		}
		return jsona;
	}

	/**
	 * Transform a JSONA data object array to a normal JSON data object array.
	 */
	private JSONArray fromJSONA(JSONArray jsona) {
		if (jsona.size() > 1) {
			Object f = jsona.get(0);
			if (f instanceof JSONArray && ((JSONArray) f).get(0).equals(symbol)) {
				JSONArray fields = (JSONArray) f;
				fields.remove(0); // remove symbol

				JSONArray objects = new JSONArray();
				for (int i = 1; i < jsona.size(); i++) {
					objects.add(this.transObjectFromJSONA(fields,
							(JSONArray) jsona.get(i)));
				}
				return objects;
			}
		}

		for (int i = 0; i < jsona.size(); i++) {
			jsona.set(i, this.fromJSONA(jsona.get(i)));
		}
		return jsona;
	}

	/**
	 * Transform a JSONA data object to a normal JSON object.
	 */
	private JSONObject transObjectFromJSONA(JSONArray fields, JSONArray values) {
		JSONObject object = new JSONObject();

		for (int i = 0, j = 0, l = fields.size(); i < l; i++, j++) {
			if (i + 1 < l && fields.get(i + 1) instanceof JSONArray) {
				JSONArray fds = (JSONArray) fields.get(i + 1);
				JSONArray vs = (JSONArray) values.get(j);
				object.put(fields.get(i), this.transObjectFromJSONA(fds, vs));
				i++;
			} else {
				object.put(fields.get(i), this.fromJSONA(values.get(j)));
			}
		}
		return object;
	}

	/**
	 * Transform a normal JSON data object to a JSONA data object.
	 */
	public Object toJSONA(Object json) {
		if (json instanceof JSONObject) {
			return this.toJSONA((JSONObject) json);
		} else if (json instanceof JSONArray) {
			return this.toJSONA((JSONArray) json);
		}
		return json;
	}

	/**
	 * Transform a normal JSON data object to a JSONA data object.
	 */
	private JSONObject toJSONA(JSONObject json) {
		Iterator keys = json.keys();
		while (keys.hasNext()) {
			Object key = keys.next();
			Object value = json.get(key);
			json.put(key, this.toJSONA(value));
		}
		return json;
	}

	/**
	 * Transform a normal JSON data object array to a JSONA data object array.
	 */
	private JSONArray toJSONA(JSONArray json) {
		if (json.size() > 1) {
			Object o = json.get(0);
			if (o instanceof JSONObject) {
				JSONArray fields = this.transFieldsFromJSON((JSONObject) o);
				fields.add(0, symbol);

				JSONArray objects = new JSONArray();
				objects.add(fields);
				for (int i = 0; i < json.size(); i++) {
					objects.add(this.transValuesFromJSON((JSONObject) json
							.get(i)));
				}
				return objects;
			}
		}
		for (int i = 0; i < json.size(); i++) {
			json.set(i, this.toJSONA(json.get(i)));
		}
		return json;
	}

	/**
	 * Get all property's names of normal JSON object, including embeded object.
	 */
	private JSONArray transFieldsFromJSON(JSONObject object) {
		JSONArray fields = new JSONArray();

		Iterator keys = object.keys();
		while (keys.hasNext()) {
			Object key = keys.next();
			Object value = object.get(key);
			fields.add(key);

			if (value instanceof JSONObject) {
				fields.add(this.transFieldsFromJSON((JSONObject) value));
			}
		}
		return fields;
	}

	/**
	 * Get all property's values of normal JSON object, including embeded
	 * object.
	 */
	private JSONArray transValuesFromJSON(JSONObject object) {
		JSONArray values = new JSONArray();

		Iterator keys = object.keys();
		while (keys.hasNext()) {
			Object key = keys.next();
			Object value = object.get(key);

			if (value instanceof JSONObject) {
				values.add(this.transValuesFromJSON((JSONObject) value));
			} else {
				values.add(this.toJSONA(value));
			}
		}
		return values;
	}
}
