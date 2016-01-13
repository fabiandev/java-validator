package validator.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import validator.contracts.Bag;

public class MessageBag implements Bag {

	Map<String, String> messages;
	
	public MessageBag() {
		this.messages = new HashMap<String, String>();
	}
	
	@Override
	public void add(Bag bag) {
		for (Entry<String, String> entry : bag) {
			this.add(entry.getKey(), entry.getValue());
		}
	}
	
	@Override
	public String add(String key, String value) {
		return this.messages.put(key, value);
	}

	@Override
	public Map<String, String> getMessages() {
		return this.messages;
	}
	
	@Override
	public Iterator<Entry<String, String>> iterator() {
		return this.messages.entrySet().iterator();
	}
	
	public boolean isEmpty() {
		return this.messages.isEmpty();
	}
	
	public int size() {
		return this.messages.size();
	}
	
}
