package com.querybuilder;

import com.querybuilder.clausules.Condition;
import com.querybuilder.clausules.From;
import com.querybuilder.clausules.Group;
import com.querybuilder.clausules.Having;
import com.querybuilder.clausules.Join;
import com.querybuilder.clausules.Select;
import com.querybuilder.clausules.Where;


public abstract class AbstractQuery<T> {

	protected Select select;
	protected From from;
	protected Join join;
	protected Where where;
	protected Group group;
	protected Having having;
	
	protected abstract T self();
	
	public T select(String s) {
		select = Select.get(s);
		return self();
	}
	
	public T select(Select s) {
		select = s;
		return self();
	}
	
	public T from(Class<?> clazz, String alias) {
		from = From.entity(clazz, alias);
		return self();
	}
	
	public T from(From f) {
		from = f;
		return self();
	}
	
	public T join(String joins, String alias, Join.Type type) {
		join = Join.with(joins, alias, type);
		return self();
	}
	
	public T join(Join j) {
		join = j;
		return self();
	}
	
	public T where(Condition condition) {
		where = Where.given(condition);
		return self();
	}
	
	public T where(Where w) {
		where = w;
		return self();
	}
	
	public T group(String groups) {
		group = Group.by(groups);
		return self();
	}
	
	public T group(Group g) {
		group = g;
		return self();
	}
	
	public T having(Condition condition) {
		having = Having.that(condition);
		return self();
	}
	
	public T having(Having h) {
		having = h;
		return self();
	}

	public Select getSelect() {
		return select;
	}

	public From getFrom() {
		return from;
	}

	public Join getJoin() {
		return join;
	}

	public Where getWhere() {
		return where;
	}

	public Group getGroup() {
		return group;
	}

	public Having getHaving() {
		return having;
	}
	

}
