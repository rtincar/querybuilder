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

	public void parse() {
		List<String> groups = getClausule().getGroups();
		sb = new StringBuilder();
		int i = 0;
		sb.append(" group by ");
		for (String g : groups) {
			sb.append(g);
			if (i < groups.size() - 1) {
				sb.append(", ");
			}
			i++;
		}
		sb.append(" ");
	}
	
	

}
