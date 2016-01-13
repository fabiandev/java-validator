package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import validator.contracts.Bag;
import validator.core.MessageBag;

public class MessageBagTest {

	Bag bag;
	
	@Before
	public void createMessageBag() {
		this.bag = new MessageBag();
	}
	
	@Test
	public void testIsEmpty() {
		assertTrue(this.bag.isEmpty());
	}
	
	@Test
	public void testAdd() {
		this.bag.add("test", "Hello World!");
		
		assertTrue(this.bag.getMessages().containsKey("test"));
		assertTrue(this.bag.getMessages().containsValue("Hello World!"));
		assertEquals(1, this.bag.size());
	}
	
	@Test
	public void testAddBag() {
		Bag other = new MessageBag();
		
		this.bag.add("test", "Hello World!");
	    other.add("hi", "Hi there.");
	    
	    this.bag.add(other);
		
	    assertTrue(this.bag.getMessages().containsKey("test"));
		assertTrue(this.bag.getMessages().containsValue("Hello World!"));
		
		assertTrue(this.bag.getMessages().containsKey("hi"));
		assertTrue(this.bag.getMessages().containsValue("Hi there."));
		
		assertEquals(2, this.bag.size());
	}

}
