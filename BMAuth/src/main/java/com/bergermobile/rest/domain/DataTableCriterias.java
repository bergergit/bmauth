package com.bergermobile.rest.domain;

import java.util.List;
import java.util.Map;

/**
 * Pojo containing all parameters senty by Datatable
 * @author fabioberger
 *
 */
public class DataTableCriterias {
	private int draw;
	private int start;
	private int length;
	private int produtoId;
	
	private Map<SearchCriterias, String> search;
	
	private List<Map<ColumnCriterias, String>> columns;
	
	private List<Map<OrderCriterias, String>> order;
	
	public List<Map<ColumnCriterias, String>> getColumns() {
		return columns;
	}

	public void setColumns(List<Map<ColumnCriterias, String>> columns) {
		this.columns = columns;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}
	
	public Map<SearchCriterias, String> getSearch() {
		return search;
	}

	public void setSearch(Map<SearchCriterias, String> search) {
		this.search = search;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public List<Map<OrderCriterias, String>> getOrder() {
		return order;
	}

	public void setOrder(List<Map<OrderCriterias, String>> order) {
		this.order = order;
	}
	
	public int getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(int produtoId) {
		this.produtoId = produtoId;
	}



	/**
	 * SearchCriterias enum
	 * @author fabioberger
	 *
	 */
	public enum SearchCriterias {
		value,
		regex
	}
	
	/**
	 * Order enum
	 * @author fabioberger
	 *
	 */
	public enum OrderCriterias {
		column,
		dir
	}
	
	/**
	 * Column Definitions
	 * @author fabioberger
	 *
	 */
	public enum ColumnCriterias {
		data,
		name,
		searchable,
		orderable,
		searchValue,
		searchRegex
	}
}
