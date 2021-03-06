package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;
import org.junit.rules.ExpectedException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.DBUtils;
 
public class CustomerDAOTest {

	private final CustomerDAO custDAO = new CustomerDAO();
	
	
	@Rule
	public ExpectedException thrown = ExpectedException.none(); 

	@Before
	public void setup() {
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}

	@Test
	public void testCreate() {
		final Customer created = new Customer(2L, "chris", "perrins");
	//	thrown.expect(Exception.class);
		assertEquals(created, custDAO.create(created));
		
	}	  

	@Test
	public void testReadAll() {		
	//	thrown.expect(SQLException.class);
		
		List<Customer> expected = new ArrayList<>();
		expected.add(new Customer(1L, "jordan", "harrison"));
		assertEquals(expected, custDAO.readAll());
	}
	
	@Test
	public void testReadLatest() {
		
		assertEquals(new Customer(1L, "jordan", "harrison"), custDAO.readLatest());
	}

	@Test
	public void testRead() {
		final long ID = 1L;
		
		assertEquals(new Customer(ID, "jordan", "harrison"), custDAO.read(ID));
	}

	@Test
	public void testUpdate() {
		final Customer updated = new Customer(1L, "chris", "perrins");
	
		assertEquals(updated, custDAO.update(updated));

	}
	
	
	@Test
	public void testDelete() {
		assertEquals(0, custDAO.delete(1));
	}
}
