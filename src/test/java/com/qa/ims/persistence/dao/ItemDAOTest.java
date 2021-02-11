package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import org.junit.Test;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.DBUtils;

public class ItemDAOTest {

	private final ItemDAO itemDAO = new ItemDAO();

	@Before
	public void setup() {
	//	DBUtils.connect("Fail");
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}

	@Test
	public void testCreate() {
		final Item created = new Item(2L, "Mouse", 8.0, "Clicker");
		assertEquals(created, itemDAO.create(created));
	}

	@Test
	public void testReadAll() {
		List<Item> expected = new ArrayList<>();
		expected.add(new Item(1L, "Keyboard", 10.0, "Tapper"));
		assertEquals(expected, itemDAO.readAll());
	}

	@Test
	public void testReadLatest() {
		assertEquals(new Item(1L, "Keyboard", 10.0, "Tapper"), itemDAO.readLatest());
	}

	@Test
	public void testRead() {
		final long ID = 1L;

		assertEquals(new Item(ID, "Keyboard", 10.0, "Tapper"), itemDAO.read(ID));
	}
	
	@Test
	public void testUpdate() {
		final Item updated = new Item(1L, "Mouse", 8.0, "Clicker");
		assertEquals(updated, itemDAO.update(updated));
	}
	
	@Test
	public void testDelete() {
		assertEquals(1, itemDAO.delete(1));
	} 
}
