package com.bergermobile.rest.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.bergermobile.rest.domain.DataTableCriterias;
import com.bergermobile.rest.domain.DataTableCriterias.ColumnCriterias;
import com.bergermobile.rest.domain.DataTableCriterias.OrderCriterias;

@Service
public class PageServiceImpl implements PageService {

	@Override
	/**
	 * Builds the Sort object based on sorting parameters
	 * @param order
	 * @return
	 */
	public PageRequest buildPageRequest(DataTableCriterias criterias) {
		List<Order> sortings = new ArrayList<Order>();
		Sort sort;
		
		// loops through all sort[] parameters and adds to the Sorting object
		for (Map<OrderCriterias, String> order : criterias.getOrder()) {
			Integer columnIndexToOrder = Integer.parseInt(order.get(OrderCriterias.column));
			String columnNameToOrder = criterias.getColumns().get(columnIndexToOrder).get(ColumnCriterias.data);
			
			sortings.add(new Sort.Order(
					Direction.valueOf(order.get(OrderCriterias.dir).toUpperCase()),
					columnNameToOrder
			));
		}
		if (sortings.isEmpty()) {
			sort = new Sort(Direction.ASC, criterias.getColumns().get(0).get(ColumnCriterias.name));
		} else {
			sort = new Sort(sortings);
		}
		
		return new PageRequest(criterias.getStart() / criterias.getLength(), criterias.getLength(), sort);
	}
	
}
