package com.querybuilder.expression;

import com.querybuilder.QueryObject;

/**
 * Esta expresion representa una ruta dentro del grafo de objetos
 * de la consulta, es decir, una propiedad
 * 
 * 
 * @author rtincar
 *
 */
public class PathExpression implements Expression {
	
	private String path;

	public PathExpression(String path) {
		this.path = path;
	}

	public String parse(QueryObject queryObject) {
		return this.path;
	}

}
