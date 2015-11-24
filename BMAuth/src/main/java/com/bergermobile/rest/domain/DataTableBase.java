package com.bergermobile.rest.domain;

import java.util.List;
import java.util.Map;

/**
 * Pojo containing the necessary data for Datatable to render properly
 * @author fabioberger
 *
 */
public class DataTableBase <T> {
	
	private int draw;
	private long recordsTotal;
	private long recordsFiltered;
	private List<T> data;
	
	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public enum ColumnNames {
        
	}
}
