package com.querybuilder.transformer;

/**
 * <p>
 * Implementacion abstracta de la interfaz Transformer. Esta clase permite
 * pasar un transformador como argumento de metodo sin necesidad de crear una
 * instancia concreta y necesitando solo sobreescribir el metodo transform:
 * </p>
 * <pre>
 * public T metodo(Transformer<T> transformer, Object o) {
 * 	return transformer.transform(o);
 * }
 * </pre>
 * Uso:
 * <pre>
 * metodo(new AbstractTransformer<ClaseConcreta>(){
 *  @Override
 *  public ClaseConcreta transform(Object o) {
 *  	Operaciones sobre o;
 *  	...
 *  	return instanciaClaseConcreta;
 *  }
 * 
 * }, argumento);
 * </pre>
 * @author rtincar
 *
 * @param <T>
 */
public abstract class AbstractTransformer<T> implements Transformer<T> {
	
}
