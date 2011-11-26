package com.querybuilder.util.transformer;

/**
 * Transforma un objeto de un tipo a otro
 * 
 * @author rtinoco
 *
 * @param <T>
 */
public interface Transformer<T> {
	
	public T transform(final Object object);

}
