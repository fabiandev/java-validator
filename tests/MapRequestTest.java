package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import validator.contracts.Request;
import validator.core.MapRequest;

public class MapRequestTest {
	
	private Request request;
	private Request emptyRequest;
	
	@Before
	public void createMapRequest() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("test1", "value1");
		map.put("test2", "value2");
		
		this.request = new MapRequest(map);
		this.emptyRequest = new MapRequest();
	}
	
	@Test
	public void testGet() {
		String value1 = this.request.get("test1");
		String value2 = this.request.get("test2");
		
		String value3 = this.request.get("test3");
		String value4 = this.emptyRequest.get("test");
		
		assertEquals("value1", value1);
		assertEquals("value2", value2);
		assertEquals(null, value3);
		assertEquals(null, value4);
	}
	
	@Test
	public void testContains() {
		assertTrue(this.request.contains("value1"));
		assertTrue(this.request.contains("value2"));
		assertFalse(this.request.contains("value3"));
		assertFalse(this.emptyRequest.contains("value"));
	}
	
	@Test
	public void testContainsKey() {
		assertTrue(this.request.containsKey("test1"));
		assertTrue(this.request.containsKey("test2"));
		assertFalse(this.request.containsKey("test3"));
		assertFalse(this.emptyRequest.containsKey("test"));
	}
	
	@Test
	public void testIsEmpty() {
		assertFalse(this.request.isEmpty());
		assertTrue(this.emptyRequest.isEmpty());
	}
	
	@Test
	public void testSize() {
		assertEquals(2, this.request.size());
		assertEquals(0, this.emptyRequest.size());
	}
	
	@Test
	public void testPut() {
		this.request.put("test3", "value3");
		this.emptyRequest.put("test", "value");
		
		this.request.containsKey("test3");
		this.request.contains("value3");
		
		this.emptyRequest.containsKey("test");
		this.emptyRequest.contains("value");
	}
	
	@Test
	public void testClear() {
		this.request.clear();
		this.emptyRequest.clear();
		
		assertTrue(this.request.isEmpty());
		assertTrue(this.emptyRequest.isEmpty());
	}

}
