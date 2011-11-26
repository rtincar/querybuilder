package com.querybuilder.util.processor;

/**
 * Procesa (modifica, altera, muta) el objeto recibido como
 * argumento
 * 
 * @author rtinoco
 *
 * @param <T> Tipo concreto que se desea procesar
 */
public interface Processor<T> {

	public void process(T object);

}
