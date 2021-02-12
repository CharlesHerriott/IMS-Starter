package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Order;

import com.qa.ims.utils.Utils;

public class OrderController implements CrudController<Order> {
	public static final Logger LOGGER = LogManager.getLogger();

	private OrderDAO orderDAO;
	private Utils utils = new Utils();

	public OrderController(OrderDAO orderDAO, Utils utils) {
		super();
		this.orderDAO = orderDAO;
		this.utils = utils;
	}
  
	@Override
	public List<Order> readAll() {
		List<Order> orders = orderDAO.readAllItems(false);
		for (Order order : orders) {
			LOGGER.info(order.toString());
		}
		LOGGER.info("Would you like to read ALL orders or by an ID?");
		String choice = utils.getString();

		if (choice.equalsIgnoreCase("ALL")) {
			List<Order> orderItems = orderDAO.readAllItems(true);
			for (Order orderItem : orderItems) {
				LOGGER.info(orderItem.itemsToString());
			}
			LOGGER.info("Total Cost: £" + calculateCost(orderItems));
			return orderItems;
		} else if (choice.equalsIgnoreCase("ID")) {
			readSpecific();
		}
		
		return orders;
	} 

	public List<Order> readSpecific() {
		LOGGER.info("Enter ID of order");
		Long id = utils.getLong();
		List<Order> orderSpecific = orderDAO.readSpecific(id);
		for (Order order : orderSpecific) {
			LOGGER.info(order.itemsToString());
		}
		calculateCost(orderSpecific);
		return orderSpecific;
	}

	@Override
	public Order create() {
		boolean complete = false;
		String choice;
		LOGGER.info("Enter your customer id");
		Long custId = utils.getLong();
		Order order = orderDAO.create(new Order(custId));
		do {
			LOGGER.info("Add item? Yes (Y)/No (N)");
			choice = utils.getString();
			if (choice.equalsIgnoreCase("Y")) {
				createOrderItem(order.getOrderId());
			} else if (choice.equalsIgnoreCase("N")) {
				complete = true;
			}
		} while (!complete);
		return order;
	}

	/**
	 * Displays information to user. Calls orderDOA.createUpdateITem();
	 * 
	 * @param orderId
	 * @return
	 */
	public Order createOrderItem(Long orderId) {
		LOGGER.info("Please enter the item id");
		Long itemId = utils.getLong();
		LOGGER.info("Please enter the quantity");
		Long quantity = utils.getLong();
		return orderDAO.createOrderItem(new Order(itemId, orderId, quantity));

	} 

	@Override
	public Order update() {
		LOGGER.info("Please enter your Order ID: ");
		Long orderId = utils.getLong();
		LOGGER.info("Please enter the Item ID: ");
		Long itemId = utils.getLong();
		LOGGER.info("Please enter the quantity: ");
		Long quantity = utils.getLong();
		return orderDAO.updateOrderItem(new Order(itemId, orderId, quantity));
	}

	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the order you want to delete");
		Long orderId = utils.getLong();
		LOGGER.info("Delete complete Order or individual item. Order | Item");
		String choice = utils.getString();
		if (choice.equalsIgnoreCase("Order")) {
			return orderDAO.delete(orderId);
		} else if (choice.equalsIgnoreCase("Item")) {
			deleteItem(orderId);
			return 0;
		} else {
			LOGGER.info("Incorrect selection. Please choose - Order | Item");
		}
		return 0;
	}

	public int deleteItem(Long orderId) {
		LOGGER.info("Enter the id of the item you want to delete");
		Long itemId = utils.getLong();
		return orderDAO.deleteOrderItem(orderId, itemId);
	}

	public Double calculateCost(List<Order> oi) {
		Double sum = 0.0;
		for (Order o : oi) {
			sum += o.getTotalCost();
		}
		return sum;
	}

	public int deleteNullOrders() {
		return orderDAO.deleteNullOrders();
	}

}