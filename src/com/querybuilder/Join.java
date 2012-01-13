package com.querybuilder;

import com.querybuilder.QueryObject.JoinType;


public class Join implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	private String path;
	private String alias;
	private JoinType joinType;

	public Join(String path, String alias, JoinType joinType) {
		this.path = path;
		this.alias = alias;
		this.joinType = joinType;
	}

	public String getPath() {
		return path;
	}

	public String getAlias() {
		return alias;
	}

	public JoinType getJoinType() {
		return joinType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		result = prime * result
				+ ((joinType == null) ? 0 : joinType.hashCode());
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
		Join other = (Join) obj;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
			return false;
		if (joinType == null) {
			if (other.joinType != null)
				return false;
		} else if (!joinType.equals(other.joinType))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
	
	
}
