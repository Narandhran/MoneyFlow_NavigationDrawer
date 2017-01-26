/**
 * @author Balaji
 *
 *         13-Dec-2016 - Balaji creation Name.java
 */
package com.neemshade.moneyflow_navdrawer.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balaji
 *
 */
public class Name {
	protected String name;
	protected String location;
	
	protected static List<Name> nameList = new ArrayList<Name>();
	protected static int nextNameIndex = 0;
	
	static {
		nameList.add(new Name("Balaji store", "Coimbatore"));
		nameList.add(new Name("Kumar wholesale", "Madurai"));		
		
		nameList.add(new Name("Kannan enterprises", "Kanuvai"));
		nameList.add(new Name("Selvi flower mart", "Delhi"));
		nameList.add(new Name("Kasi store", "Trichy"));
		
		nameList.add(new Name("Ramesh shop", "Chennai"));
		nameList.add(new Name("Guru mobiles", "Virudhunagar"));
		nameList.add(new Name("Subbu electricals", "Sivakasi"));
		
		nameList.add(new Name("Vasavi food service", "Theni"));
		nameList.add(new Name("Vetri vegetables", "Kangeyam"));
		nameList.add(new Name("Kavi stationary", "Watrap"));
		
		nameList.add(new Name("Sakthi medicals", "Ganapathy"));
		nameList.add(new Name("Amma canteen", "Kovilpatti"));
		nameList.add(new Name("Baskar", "RS puram"));		
		
	}

	public Name(String name, String location) {
		this.name  = name;
		this.location = location;
	}

	public String displayName() {
		if(name == null) return null;

		int pos = name.indexOf(" ");
		
		if(pos < 0) return name;
		return name.substring(0, pos);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public static Name getNextName()
	{
		Name name = nameList.get(nextNameIndex);
		nextNameIndex = (++ nextNameIndex) % nameList.size();
		
		return name;
	}
}
