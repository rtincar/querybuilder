package com.querybuilder;

public class Select implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String path;
	private String alias;

	public Select(String path, String alias) {
		this.path = path;
		this.alias = alias;
	}

	public String getPath() {
		return path;
	}

	public String getAlias() {
		return alias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
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
		Select other = (Select) obj;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
	
	
}
