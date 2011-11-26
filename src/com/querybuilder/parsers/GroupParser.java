package com.querybuilder.parsers;

import java.util.List;

import com.querybuilder.clausules.Group;

public class GroupParser extends ClausuleParser<Group> {
	
	private GroupParser(Group g) {
		super(g);
	}
	
	public static GroupParser get(Group group) {
		return new GroupParser(group);
	}

	public String getParsedString() {
		// TODO Auto-generated method stub
		return null;
	}

	public void parse() {
		List<String> groups = getClausule().getGroups();
		
	}
	
	

}
