/**
 * @author Balaji
 *
 *         13-Dec-2016 - Balaji creation Company.java
 */
package com.neemshade.moneyflow_navdrawer.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balaji
 *
 */
public class Company {

	private static Company selectedCompany;
	private static List<Company> companies = null;

	private Name name;
	private List<Party> parties;
	
	public Company(Name name)
	{
		this.name = name;
	}
	
	public void display()
	{
		System.out.println(getName().displayName() + ", " + getName().getLocation());
		for (Party party : parties) {
			party.display();
		}
	}

	public static Company getSelectedCompany() {
		if(selectedCompany == null)
			fetchCompanies();

		return selectedCompany;
	}

	public static String setSelectedCompany(Company selectedCompany) {
		Company.selectedCompany = selectedCompany;
		return null;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public List<Party> getParties() {
		return parties;
	}

	public void setParties(List<Party> parties) {
		this.parties = parties;
	}

	public static List<Company> fetchCompanies()
	{

		if(companies != null)
			return companies;

		companies = new ArrayList<Company>();
		
		for(int i = 0; i < 2; i++)
			companies.add(generateCompany());
		
		selectedCompany = companies.get(0);

		return companies;
	}

	private static Company generateCompany() {
		Company company = new Company(Name.getNextName());
		company.setParties(Party.fetchParties());
		return company;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Company> companies = Company.fetchCompanies();
		
		for (Company company : companies) {
			company.display();
		}
	}

}
