package com.promineotech.jeep.service;

import com.promineotech.jeep.dao.JeepOrderDao;
import com.promineotech.jeep.entity.Customer;
import com.promineotech.jeep.entity.Order;
import com.promineotech.jeep.entity.OrderRequest;

public interface JeepOrderService {

	
	JeepOrderDao jeepOrderDao;
	
	 Order createOrder(OrderRequest orderRequest);
		 
		 
	 }
	 
		

}
