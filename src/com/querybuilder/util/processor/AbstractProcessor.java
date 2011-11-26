package com.querybuilder.util.processor;

/**
 * <p>
 * Implementacion abstracta de la interfaz que permite pasar un Processor anonimo
 * como argumento a metodos sin necesidad de crear una implementacion concreta. 
 * Ejemplo de uso:
 * </p>
 * <pre>
 * public void metodo(Processor<T> processor, T o) {
 * 		processor.process(o);
 * }
 * </pre>
 * Uso:
 * <pre>
 * metodo(new AbstractProcessor<ClaseConcreta>(){
 * 		@Override
 * 		public void process(ClaseConcreta o) {
 * 			Operaciones sobre o
 * 			...
 * 		}
 * }, instanciaClaseConcreta);
 * </pre>
 * @author rtincar
 *
 * @param <T>
 */
public abstract class AbstractProcessor<T> implements Processor<T> {

}
