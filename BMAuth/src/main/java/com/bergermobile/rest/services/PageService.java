package com.bergermobile.rest.services;

import org.springframework.data.domain.PageRequest;

import com.bergermobile.rest.domain.DataTableCriterias;

public interface PageService {
	
	public PageRequest buildPageRequest(DataTableCriterias criterias);

}
