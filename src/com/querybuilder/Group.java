package com.querybuilder;

import java.util.ArrayList;
import java.util.List;

public class Group {
	
	private List<String> groups = new ArrayList<String>();

	private Group(String group) {
		groups.add(group);
	}
	
	public static Group by(String group) {
		return new Group(group);
	}
	
	public Group and(String group) {
		groups.add(group);
		return this;
	}
	
	public List<String> getGroups() {
		return groups;
	}

}
