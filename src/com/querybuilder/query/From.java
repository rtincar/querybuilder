package com.querybuilder.query;

public class From implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Class<?> entity;
	private String alias;

	public From(Class<?> entity, String alias) {
		this.entity = entity;
		this.alias = alias;
	}

	public Class<?> getEntity() {
		return entity;
	}

	public String getAlias() {
		return alias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
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
		From other = (From) obj;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
			return false;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		return true;
	}
	
	
}
