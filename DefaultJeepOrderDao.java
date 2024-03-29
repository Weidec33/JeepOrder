package com.promineotech.jeep.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.promineotech.jeep.dao.JeepOrderDao;
import com.promineotech.jeep.entity.Color;
import com.promineotech.jeep.entity.Customer;
import com.promineotech.jeep.entity.Engine;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.entity.Option;
import com.promineotech.jeep.entity.Order;
import com.promineotech.jeep.entity.OrderRequest;
import com.promineotech.jeep.entity.Tire;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class DefaultJeepOrderDao implements JeepOrderDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public  Order saveOrder(Customer customer, Jeep jeep, Color color, Engine engine, Tire tire, BigDecimal price, List<Option> options) {
	SqlParams params = generateInsertSql(customer, jeep, color, engine, tire, price);
	
	KeyHolder keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(params.sql, params.source, keyHolder);
	
	
	Long orderPK = keyHolder.getKey().longValue();
	saveOptions(options, orderPK);
	
	return Order.builder()
			.orderPK(orderPK)
			.customer(customer)
			.model(jeep)
			.color(color)
			.engine(engine)
			.tire(tire)
			.options(options)
			.price(price)
			.build();
	}
	
	
	private SqlParams generateInsertSql(Customer customer, Jeep jeep, Color color, Engine engine, Tire tire,
			BigDecimal price) {
		String sql = ""
				+ "INSERT INTO orders ("
				+ "customer_fk, color_fk, engine_fk, tire_fk, model_fk, price"
				+ ") VALUES ("
				+ ":customer_fk, :color_fk, :engine_fk, :tire_fk, :model_fk, :price" 
				+ ")";
		
		SqlParams params = new SqlParams();
		
		params.sql = sql;
		params.source.addValue("customer_fk", customer.getCustomerPK());
		params.source.addValue("color_fk", color.getColorPK());
		params.source.addValue("engine_fk", engine.getEnginePK());
		params.source.addValue("tire_fk", tire.getTirePK());
		params.source.addValue("model_fk", jeep.getModelPK());
		params.source.addValue("price", price);
		
		return params;
	}
	
	
	
	@Override
	public Order createOrder(OrderRequest orderRequest) {
		log.debug("Order={}", orderRequest);
		return null;
	}

	@Override
	public List<Option> fetchOptions(List<String> optionIds) {
		String sql = ""
				+ "SELECT * "
				+ "FROM options "
				+ "WHERE option_id IN"
		return null;
	}

	
	private void saveOptions(List<Option> options, Long orderPK) {
		for(Option option : options) {
			SqlParams params = generateInsertSql(option, orderPK);
			jdbcTemplate.update(params.sql, params.source);
		}
	}
	private SqlParams generateInsertSql(Option option, Long orderPK) {
		SqlParams params = new SqlParams();
		
		params.sql = ""
				+ "INSERT INTO order_options ("
				+ "option_fk, order_fk"
				+ ") VALUES ("
				+ ":option_fk, :order_fk"
				+ ")";
		
		params.source.addValue("option_fk", option.getOptionPK());
		params.source.addValue("order_fk", orderPK);
		
		return params;
	}
	@Override
	public Optional<Customer> fetchCustomer(String customerId) {
		
		String sql = ""
				+ "SELECT * "
				+ "FROM customers "
				+ "WHERE customer_id = : customer_id";
		
		Map<String, Object> params = new HashMap<>();
		params.put("customer_id", customerId);
		
		return Optional.ofNullable(jdbcTemplate.query(sql, params, new CustomerResultSetExtractor()));
	}

	@Override
	public Optional<Jeep> fetchModel(JeepModel model, String trim, int doors) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Color> fetchColor(String colorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Engine> fetchEngine(String engineId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Tire> fetchTire(String tireId) {
		// TODO Auto-generated method stub
		return null;
	}

	class SqlParams{
		String sql;
		MapSqlParameterSource source = new MapSqlParameterSource();
	}
	
}
