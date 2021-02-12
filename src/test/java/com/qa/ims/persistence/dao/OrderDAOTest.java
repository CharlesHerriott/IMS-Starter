package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;


import org.junit.Before;

import org.junit.Test;
import org.mockito.Mockito;


import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;


public class OrderDAOTest {

	private final OrderDAO orderDAO = new OrderDAO();

	@Before
	public void setup() {
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}


	@Test
	public void testCreate() {
		Order created = new Order(3L,1L);
		assertEquals(created, orderDAO.create(created));
	}
	
	@Test
	public void testUpdate() {
		Order created = new Order(3L,1L);
	//	assertEquals(created, orderDAO.update(created));
	}
	
	@Test
	public void testOrderItemUpdate() {
		Order created = new Order(1L,1L,1L);
		assertEquals(null, orderDAO.update(created));
	}

	@Test
	public void testExceptionCreateUpdateOrder() {
		Order updated = new Order(2L, 3L);
		assertEquals(null, orderDAO.create(updated));
	}

	@Test
	public void testCreateOrderItem() {
		Order created = new Order( 1L, 1L, 4L);	
		System.out.println(created);
		System.out.println(orderDAO.createOrderItem(created));
		// Prints the same result..?
		
		//assertEquals(created, orderDAO.createOrderItem(created));
	}

	@Test
	public void testReadAllItems() {
		List<Order> expected = new ArrayList<>();
		expected.add(new Order(1L, 1L));
		expected.add(new Order(2L, 1L));
		
		assertEquals(expected, orderDAO.readAllItems(false));
		//assertEquals(null, orderDAO.readAllItems(true));
		
	}
	
	@Test
	public void testReadAllItemsJoined() {
		List<Order> expected = new ArrayList<>();
		expected.add(new Order(1L,1L,4L));
		expected.add(new Order(1L,1L,4L));
		
		//assertEquals(expected, orderDAO.readAllItems(true));
	}

	

	@Test
	public void testRead() {
		final long Id = 1L;
		Order expected = new Order(1L,1L);
		assertEquals(expected, orderDAO.read(Id));
	}
	
	@Test
	public void testReadOrderItem(){
		final long orderId = 2L;
		final long itemId = 1l;
		Order expected = new Order(1L, 1L, 1L);
		//assertEquals(expected, orderDAO.readOrderItem(orderId, itemId));
	}

	@Test
	public void testReadSpecific() {
		List<Order> expected = new ArrayList<>();
		expected.add(new Order(1L, "", "", 1L, 1L, "", 0.0, "", 0.0));
		List<Order> result = orderDAO.readSpecific(1L);
	//	assertEquals(expected.get(0).itemsToString(), result);
	}

	@Test 
	public void testDeleteNullOrders() {
		assertEquals(0, orderDAO.deleteNullOrders());
	}

	@Test
	public void testDelete() {
		assertEquals(0, orderDAO.delete(1));
	}

	@Test
	public void testDeleteOrderItem() {
		assertEquals(0, orderDAO.deleteOrderItem(1, 1));
	}


	@Test
	public void testBlanks() {
		assertEquals(null, orderDAO.readAll());
		assertEquals(null, orderDAO.create(null));
		assertEquals(null, orderDAO.update(null));
		assertEquals(null, orderDAO.read(null));
	}
}
