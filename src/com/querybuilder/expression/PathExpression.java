package com.querybuilder.expression;

import com.querybuilder.query.QueryObject;

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

	public String getExpression() {
		return path;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PathExpression other = (PathExpression) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
	
	

}
