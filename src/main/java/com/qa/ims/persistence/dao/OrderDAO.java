package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.qa.ims.controller.Queries;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Order;

import com.qa.ims.utils.DBUtils;

public class OrderDAO implements Dao<Order> {

	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
		String fname = resultSet.getString("first_name");
		String sname = resultSet.getString("surname");
		Long orderId = resultSet.getLong("order_id");
		Long itemId = resultSet.getLong("item_id");
		Long quantity = resultSet.getLong("quantity");
		String itemName = resultSet.getString("item_name");
		Double itemCost = resultSet.getDouble("item_cost");
		Double totalCost = resultSet.getDouble("TotalCost");
		String itemDesc = resultSet.getString("item_desc");
		return new Order(orderId, fname, sname, itemId, quantity, itemName, itemCost, itemDesc, totalCost);
	}

	public Order modelFromResultSetBeforeJoin(ResultSet resultSet) throws SQLException {
		Long orderId = resultSet.getLong("order_id");
		Long customerId = resultSet.getLong("cust_id");
		return new Order(orderId, customerId);
	}

	public Order modelFromResultSetOrderItems(ResultSet resultSet) throws SQLException {
		Long orderId = resultSet.getLong("order_id");
		Long itemId = resultSet.getLong("item_id");
		Long quantity = resultSet.getLong("quantity");
		return new Order(itemId, orderId, quantity);
	}
	
	/**
	 * Reads in either all orders or all orderItems.
	 * 
	 * @param allItems
	 * @return
	 */
	public List<Order> readAllItems(boolean allItems) {
		String query = allItems ? query = Queries.READALLORDERITEMS.getDescription()
				: Queries.READALL.specifyTable("orders");
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);) {
			List<Order> orders = new ArrayList<>();
			while (resultSet.next()) {
				if (allItems) {
					orders.add(modelFromResultSet(resultSet));
				} else {
					orders.add(modelFromResultSetBeforeJoin(resultSet));
				}
			}
			return orders;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	/**
	 * Reads a specific row in from the joined table.
	 * 
	 * @param id
	 * @return
	 */
	public List<Order> readSpecific(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement(Queries.READSPECIFICORDERITEM.getDescription());) {
			statement.setLong(1, id);
			List<Order> order = new ArrayList<>();
			try (ResultSet resultSet = statement.executeQuery();) {
				while (resultSet.next()) {
					order.add(modelFromResultSet(resultSet));
				}
				return order;
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	/**
	 * Reads in the latest order or orderItem. TRUE = order. FALSE = orderItem.
	 * 
	 * @param item
	 * @return
	 */
	public Order readLatestItem(boolean order) {
		String query = order ? Queries.READLATESTORDERITEM.getDescription() : Queries.READLATESTORDER.getDescription();
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);) {
			resultSet.next();
			if (order) {
				return modelFromResultSet(resultSet);
			} else {
				return modelFromResultSetBeforeJoin(resultSet);
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Reads in one row from order table.
	 * 
	 * @param id
	 * @param item
	 * @return
	 */
	@Override
	public Order read(Long id) {
		String query = Queries.READORDER.getDescription();
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery();) {
				resultSet.next();
				return modelFromResultSetBeforeJoin(resultSet);
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());

		}
		return null;
	}

	public Order readOrderItem(Long orderId, Long itemId) {
		String query = Queries.READORDERITEM.getDescription();
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setLong(1, orderId);
			statement.setLong(2, itemId);
			try (ResultSet resultSet = statement.executeQuery();) {
				resultSet.next();				
					return modelFromResultSetOrderItems(resultSet);				
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());

		}
		return null;
	}

	/**
	 * Creates an order.
	 * 
	 * @param order
	 * @return
	 */
	@Override
	public Order create(Order order) {
		String query = Queries.CREATEORDER.getDescription();
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setLong(1, order.getCustomerId());
			statement.executeUpdate();
			return readLatestItem(false);

		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Order update(Order order) {
		String query = Queries.CREATEORDER.getDescription();
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setLong(1, order.getQuantity());
			statement.setLong(2, order.getOrderId());
			statement.setLong(3, order.getItemId());
			statement.executeUpdate();
			return read(order.getOrderId());
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Creates an orderItem.
	 * 
	 * @param order
	 * @return
	 */
	public Order createOrderItem(Order order) {
		String query = Queries.CREATEORDERITEM.getDescription();
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setLong(1, order.getItemId());
			statement.setLong(2, order.getOrderId());
			statement.setLong(3, order.getQuantity());
			statement.executeUpdate();
			return readLatestItem(true);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public Order updateOrderItem(Order order) {
		String query = Queries.UPDATEORDERITEM.getDescription();
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setLong(1, order.getQuantity());
			statement.setLong(2, order.getOrderId());
			statement.setLong(3, order.getItemId());
			statement.executeUpdate();
			return readOrderItem(order.getOrderId(), order.getItemId());
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;

	}

	/**
	 * Deletes an order from the order table.
	 * 
	 * @param id
	 */
	@Override
	public int delete(long orderId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(Queries.DELETEORDER.getDescription());) {
			statement.setLong(1, orderId);
			return statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}

	/**
	 * Deletes an orderItem from the orderItem table.
	 * 
	 * @param orderId
	 * @param itemId
	 * @return
	 */
	public int deleteOrderItem(long orderId, long itemId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(Queries.DELETEORDERITEM.getDescription());) {
			statement.setLong(1, orderId);
			statement.setLong(2, itemId);
			return statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}

	/**
	 * Deletes null orders - orders without any items.
	 * 
	 * @return
	 */
	public int deleteNullOrders() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(Queries.DELETENULLORDERS.getDescription());) {

		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}

	@Override
	public List<Order> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

}