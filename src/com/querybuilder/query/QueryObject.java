package com.querybuilder.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.querybuilder.expression.ConditionExpression;

/**
 * Representa el objeto Context en el patron Interpreter
 *
 * <h3>Encapsulamiento y Protección de Datos</h3>
 * Los getters públicos retornan vistas inmutables (Collections.unmodifiable*) para
 * prevenir modificaciones externas del estado interno. Para modificar las colecciones,
 * use QueryCreator o los métodos addParameter/addAllParameters.
 *
 * <h3>Patrón de Uso Correcto</h3>
 * <pre>{@code
 * // Correcto: Usar QueryCreator
 * QueryCreator qc = QueryCreator.init(entityManager);
 * qc.select(...).from(...).whereAll(...);
 * List<T> results = qc.all();
 *
 * // Incorrecto: Modificar directamente
 * QueryObject qo = qc.getQueryObject();
 * qo.getSelects().add(select); // Lanza UnsupportedOperationException
 * }</pre>
 *
 * <h3>Limitaciones de Concurrencia y Reutilización</h3>
 * IMPORTANTE: Este objeto es mutable y su estado (especialmente startParamIndex)
 * cambia durante el parsing. Por lo tanto:
 * <ul>
 *   <li>NO reutilice el mismo QueryObject para múltiples operaciones de parsing</li>
 *   <li>NO use el mismo QueryObject desde múltiples hilos simultáneamente</li>
 *   <li>Cree un nuevo QueryCreator para cada consulta independiente</li>
 * </ul>
 *
 * @author rtincar
 *
 */
public class QueryObject implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6160919509754143622L;

	public enum ConditionEvaluationMode {
		ALL, ANY
	}

	public enum JoinType {
		FULL, LEFT, INNER
	}

	private int startParamIndex = 0;

	private Map<String, Object> parameters = new LinkedHashMap<String, Object>();
	private List<Select> selects = new ArrayList<Select>(0);
	private List<From> froms = new ArrayList<From>(0);
	private List<Join> joins = new ArrayList<Join>(0);
	private List<ConditionExpression> conditions = new ArrayList<ConditionExpression>(0);
	private List<ConditionExpression> havings = new ArrayList<ConditionExpression>(0);
	private String orderBy;
	private String groupBy;
	private Integer first;
	private Integer max;
	private QueryObject.ConditionEvaluationMode conditionEvaluationMode = QueryObject.ConditionEvaluationMode.ALL;

	/**
	 * Retorna una vista inmutable de la lista de selects.
	 * Para modificar, use getSelectsInternal() (solo accesible desde el mismo paquete).
	 *
	 * @return Vista inmutable de selects
	 */
	public List<Select> getSelects() {
		return Collections.unmodifiableList(selects);
	}

	/**
	 * Acceso interno a la lista mutable de selects (package-private).
	 * Solo para uso de QueryCreator y clases del mismo paquete.
	 *
	 * @return Lista mutable de selects
	 */
	List<Select> getSelectsInternal() {
		return selects;
	}

	public void setSelects(List<Select> selects) {
		this.selects = selects;
	}

	/**
	 * Retorna una vista inmutable de la lista de froms.
	 * Para modificar, use getFromsInternal() (solo accesible desde el mismo paquete).
	 *
	 * @return Vista inmutable de froms
	 */
	public List<From> getFroms() {
		return Collections.unmodifiableList(froms);
	}

	/**
	 * Acceso interno a la lista mutable de froms (package-private).
	 * Solo para uso de QueryCreator y clases del mismo paquete.
	 *
	 * @return Lista mutable de froms
	 */
	List<From> getFromsInternal() {
		return froms;
	}

	public void setFroms(List<From> froms) {
		this.froms = froms;
	}

	/**
	 * Retorna una vista inmutable de la lista de joins.
	 * Para modificar, use getJoinsInternal() (solo accesible desde el mismo paquete).
	 *
	 * @return Vista inmutable de joins
	 */
	public List<Join> getJoins() {
		return Collections.unmodifiableList(joins);
	}

	/**
	 * Acceso interno a la lista mutable de joins (package-private).
	 * Solo para uso de QueryCreator y clases del mismo paquete.
	 *
	 * @return Lista mutable de joins
	 */
	List<Join> getJoinsInternal() {
		return joins;
	}

	public void setJoins(List<Join> joins) {
		this.joins = joins;
	}

	/**
	 * Retorna una vista inmutable de la lista de havings.
	 * Para modificar, use getHavingsInternal() (solo accesible desde el mismo paquete).
	 *
	 * @return Vista inmutable de havings
	 */
	public List<ConditionExpression> getHavings() {
		return Collections.unmodifiableList(havings);
	}

	/**
	 * Acceso interno a la lista mutable de havings (package-private).
	 * Solo para uso de QueryCreator y clases del mismo paquete.
	 *
	 * @return Lista mutable de havings
	 */
	List<ConditionExpression> getHavingsInternal() {
		return havings;
	}

	public void setHavings(List<ConditionExpression> havings) {
		this.havings = havings;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public Integer getFirst() {
		return first;
	}

	public void setFirst(Integer first) {
		this.first = first;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	/**
	 * Retorna una vista inmutable del mapa de parámetros.
	 * Para modificar, use getParameterMapInternal() (solo accesible desde el mismo paquete).
	 *
	 * @return Vista inmutable del mapa de parámetros
	 */
	public Map<String, Object> getParameterMap() {
		return Collections.unmodifiableMap(parameters);
	}

	/**
	 * Acceso interno al mapa mutable de parámetros (package-private).
	 * Solo para uso de clases de expresión del mismo paquete.
	 *
	 * @return Mapa mutable de parámetros
	 */
	Map<String, Object> getParameterMapInternal() {
		return parameters;
	}

	/**
	 * Agrega un parámetro al mapa de parámetros de la consulta.
	 * Método público para uso de clases de expresión.
	 *
	 * @param key Clave del parámetro
	 * @param value Valor del parámetro
	 */
	public void addParameter(String key, Object value) {
		parameters.put(key, value);
	}

	/**
	 * Agrega múltiples parámetros al mapa de parámetros de la consulta.
	 * Método público para uso de clases de expresión.
	 *
	 * @param params Mapa de parámetros a agregar
	 */
	public void addAllParameters(Map<String, Object> params) {
		parameters.putAll(params);
	}

	public void setParameterMap(Map<String, Object> parameterMap) {
		this.parameters = parameterMap;
	}

	public int getStartParamIndex() {
		return startParamIndex;
	}

	public void setStartParamIndex(int startParamIndex) {
		this.startParamIndex = startParamIndex;
	}

	public QueryObject.ConditionEvaluationMode getConditionEvaluationMode() {
		return conditionEvaluationMode;
	}

	public void setConditionEvaluationMode(
			QueryObject.ConditionEvaluationMode conditionEvaluationMode) {
		this.conditionEvaluationMode = conditionEvaluationMode;
	}

	/**
	 * Retorna una vista inmutable de la lista de condiciones.
	 * Para modificar, use getConditionsInternal() (solo accesible desde el mismo paquete).
	 *
	 * @return Vista inmutable de condiciones
	 */
	public List<ConditionExpression> getConditions() {
		return Collections.unmodifiableList(conditions);
	}

	/**
	 * Acceso interno a la lista mutable de condiciones (package-private).
	 * Solo para uso de QueryCreator y clases del mismo paquete.
	 *
	 * @return Lista mutable de condiciones
	 */
	List<ConditionExpression> getConditionsInternal() {
		return conditions;
	}

	public void setConditions(List<ConditionExpression> conditions) {
		this.conditions = conditions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((conditionEvaluationMode == null) ? 0
						: conditionEvaluationMode.hashCode());
		result = prime * result
				+ ((conditions == null) ? 0 : conditions.hashCode());
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((froms == null) ? 0 : froms.hashCode());
		result = prime * result + ((groupBy == null) ? 0 : groupBy.hashCode());
		result = prime * result + ((havings == null) ? 0 : havings.hashCode());
		result = prime * result + ((joins == null) ? 0 : joins.hashCode());
		result = prime * result + ((max == null) ? 0 : max.hashCode());
		result = prime * result + ((orderBy == null) ? 0 : orderBy.hashCode());
		result = prime * result
				+ ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result + ((selects == null) ? 0 : selects.hashCode());
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
		QueryObject other = (QueryObject) obj;
		if (conditionEvaluationMode == null) {
			if (other.conditionEvaluationMode != null)
				return false;
		} else if (!conditionEvaluationMode
				.equals(other.conditionEvaluationMode))
			return false;
		if (conditions == null) {
			if (other.conditions != null)
				return false;
		} else if (!conditions.equals(other.conditions))
			return false;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (froms == null) {
			if (other.froms != null)
				return false;
		} else if (!froms.equals(other.froms))
			return false;
		if (groupBy == null) {
			if (other.groupBy != null)
				return false;
		} else if (!groupBy.equals(other.groupBy))
			return false;
		if (havings == null) {
			if (other.havings != null)
				return false;
		} else if (!havings.equals(other.havings))
			return false;
		if (joins == null) {
			if (other.joins != null)
				return false;
		} else if (!joins.equals(other.joins))
			return false;
		if (max == null) {
			if (other.max != null)
				return false;
		} else if (!max.equals(other.max))
			return false;
		if (orderBy == null) {
			if (other.orderBy != null)
				return false;
		} else if (!orderBy.equals(other.orderBy))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		if (selects == null) {
			if (other.selects != null)
				return false;
		} else if (!selects.equals(other.selects))
			return false;
		return true;
	}
	
	

}
