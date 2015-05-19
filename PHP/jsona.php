<?php
/*!
 * JSONA PHP Library v1.0.0
 * https://github.com/sharplog/JSONA
 *
 * Copyright (c) 2015 https://github.com/sharplog
 * Released under the MIT license
 * http://opensource.org/licenses/MIT
 *
 * Date: 2015-05-19
 */

class JSONA
{
	// field symbol, default '@'
	private $_symbol;		
	
	function JSONA($symbol = '@'){
		$this->_symbol = $symbol;
	}

	/**
	 * Transform a JSONA data object to a normal PHP data array.
	 */
	public function fromJSONA($jsona){
		if( is_array($jsona) && count($jsona) > 1){
			if( is_array($jsona[0]) && $jsona[0][0] === $this->_symbol ) {
				$fields = array_shift($jsona);
				array_shift($fields);		// shift symbol

				$objects = array();
				foreach($jsona as $value){
					array_push( $objects, $this->_transObjectFromJSONA($fields, $value) );
				}

				return $objects;
			}

			foreach($jsona as $key => $value) {
				$jsona[$key] = $this->fromJSONA( $jsona[$key]);
			}
		}
		else if( is_object($jsona) ){
			foreach($jsona as $key => $value) {
				$jsona->$key = $this->fromJSONA( $jsona->$key);
			}
		}

		return $jsona;
	}


	/**
	 * Transform a JSONA data object to a normal PHP array.
	 */
	private function _transObjectFromJSONA($fields, $values){
		$object = [];

		for($i=0, $j=0, $l=count($fields); $i<$l; $i++, $j++){
			// object field
			if( $i+1 < $l && is_array($fields[$i+1]) ){
				$object[$fields[$i]] = $this->_transObjectFromJSONA($fields[$i+1], $values[$j]);
				$i++;
			}
			else{
				$object[$fields[$i]] = $this->fromJSONA($values[$j]);
			}
		}

		return $object;
	}

	/**
	 * Transform a normal PHP data object to a JSONA data object.
	 */
	public function toJSONA($php){
		if( is_array($php) && count($php) > 1 ){
			if( is_object($php[0]) || ( is_array($php[0]) && !isset($php[0][0])) ){
				$fields = $this->_transFieldsFromJSON($php[0]);
				array_unshift( $fields, $this->_symbol);

				$objects = array($fields);
				foreach( $php as $value){
					array_push( $objects, $this->_transValuesFromJSON($value) );
				}

				return $objects;
			}

			foreach( $php as  $key => $value) $php[$key] = $this->toJSONA($value);
		}
		else if( is_object($php) ){
			foreach( $php as  $key => $value) $php->$key = $this->toJSONA($value);
		}

		return $php;
	}

	/**
	 * Get all property's names of normal PHP object, including embeded object.
	 */
	private function _transFieldsFromJSON($object){
		$fields = [];
		foreach( $object as $key => $value){
			array_push($fields, $key);
			if( is_object($value) || ( is_array($value) && !isset($value[0])) ){
				array_push( $fields, $this->_transFieldsFromJSON($value) );
			}
		}
		return $fields;
	}

	/**
	 * Get all property's values of normal PHP object, including embeded object.
	 */
	private function _transValuesFromJSON($object){
		$values = [];
		foreach( $object as $key => $value){
			if( is_object($value) || ( is_array($value) && !isset($value[0])) ){
				array_push( $values, $this->_transValuesFromJSON($value) );
			}
			else{
				array_push( $values, $this->toJSONA( $value ) );
			}
		}
		return $values;
	}
}
