package com.querybuilder.parsers;

import com.querybuilder.clausules.Clausule;

/**
 * Clase abstracta que deben heredar todos parsers de las distintas clausulas
 * de la consulta
 * 
 * @author rtincar
 *
 */
public abstract class ClausuleParser<T extends Clausule> implements Parser {
	protected T clausule;
	protected StringBuilder sb;
	
	
	protected ClausuleParser(T concret) {
		clausule = concret;
	}

	protected T getClausule() {
		return clausule;
	}


}
