package com.querybuilder.util.processor;

/**
 * Procesa (modifica o altera) el objeto
 * 
 * @author rtinoco
 *
 * @param <T>
 */
public interface Processor<T> {
	
	public void process(T object);

}
