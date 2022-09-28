package com.stackroute.datamunger.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.stackroute.datamunger.DataMunger;

public class DataMungerTestTask3 {
	
	private static DataMunger dataMunger;
	
	@BeforeClass
	public static void setup() {
		// This methods runs, before running any one of the test case
		// This method is used to initialize the required variables
		dataMunger = new DataMunger();

	}

	@AfterClass
	public static void teardown() {
		// This method runs, after running all the test cases
		// This method is used to clear the initialized variables
		dataMunger = null;

	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void testGetLogicalOperators()
	{
		assertEquals("testGetLogicalOperators(): Retrieval of Logical Operators failed. AND/OR keyword will exist in the query only if where conditions exists and it contains multiple conditions.The extracted logical operators will be stored in a String array which will be returned by the method. "
				+ "Please note that AND/OR can exist as a substring in the conditions as well. For eg: name='Alexander',color='Red' etc.",
				new String[]{"and"},
				dataMunger.getLogicalOperators("select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore'"));
		assertEquals("testGetLogicalOperators(): Retrieval of Logical Operators failed. AND/OR keyword will exist in the query only if where conditions exists and it contains multiple conditions.The extracted logical operators will be stored in a String array which will be returned by the method. "
				+ "Please note that AND/OR can exist as a substring in the conditions as well. For eg: name='Alexander',color='Red' etc.",
				new String[]{"or"},
				dataMunger.getLogicalOperators("select city,winner,player_match from ipl.csv where season > 2014 or city ='Bangalore'"));
		
		assertEquals("testGetLogicalOperators(): Retrieval of Logical Operators failed. AND/OR keyword will exist in the query only if where conditions exists and it contains multiple conditions.The extracted logical operators will be stored in a String array which will be returned by the method. "
				+ "Please note that AND/OR can exist as a substring in the conditions as well. For eg: name='Alexander',color='Red' etc.",
				new String[]{"and","or"},
				dataMunger.getLogicalOperators("select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' or city ='Delhi'"));
		
		assertNull("testGetLogicalOperatorsFailure() :Retrieval of Logical Operators failed. AND/OR keyword will exist in the query only if where conditions exists and it contains multiple conditions.The extracted logical operators will be stored in a String array which will be returned by the method. Please note that AND/OR can exist as a substring in the conditions as well. For eg: name='Alexander',color='Red' etc", 
				dataMunger.getLogicalOperators("select city,winner,player_match from ipl1.csv group by winner"));
	}
	
	@Test
	public void testGetLogicalOperatorsFailure()
	{
		
		assertNotNull("testGetLogicalOperatorsFailure() :Retrieval of Logical Operators failed. AND/OR keyword will exist in the query only if where conditions exists and it contains multiple conditions.The extracted logical operators will be stored in a String array which will be returned by the method. Please note that AND/OR can exist as a substring in the conditions as well. For eg: name='Alexander',color='Red' etc", 
				dataMunger.getLogicalOperators("select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' group by winner"));
		
		assertNotNull("testGetLogicalOperatorsFailure() :Retrieval of Logical Operators failed. AND/OR keyword will exist in the query only if where conditions exists and it contains multiple conditions.The extracted logical operators will be stored in a String array which will be returned by the method. Please note that AND/OR can exist as a substring in the conditions as well. For eg: name='Alexander',color='Red' etc",
				dataMunger.getLogicalOperators("select city,winner,player_match from ipl1.csv where season > 2014 and city ='Bangalore' order by city"));
		
		
	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void testGetOrderByFields()
	{
		assertEquals("testGetOrderByFields():Check getOrderByFields() method. The query string can contain more than one order by fields.The query string might not contain order by clause at all. The field names, condition values might contain 'order' as a substring. For eg: order_number,job_order",
				new String[]{"winner"},
				dataMunger.getOrderByFields("select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' order by winner"));
		
		assertEquals("testGetOrderByFields():Check getOrderByFields() method. The query string can contain more than one order by fields.The query string might not contain order by clause at all. The field names, condition values might contain 'order' as a substring. For eg: order_number,job_order",
				new String[]{"team1"},
				dataMunger.getOrderByFields("select city,winner,player_match from ipl.csv order by team1"));
		
		assertNull("testGetOrderByFields():Check getOrderByFields() method. The query string can contain more than one order by fields.The query string might not contain order by clause at all. The field names, condition values might contain 'order' as a substring. For eg: order_number,job_order",
				dataMunger.getOrderByFields("select city,winner,player_match from ipl1.csv where season > 2014 and city ='Bangalore'"));
	}
	
	@Test
	public void testGetOrderByFieldsFailure()
	{
		assertNotNull("testGetOrderByFieldsFailure():Check getOrderByFields() method. The query string can contain more than one order by fields.The query string might not contain order by clause at all. The field names, condition values might contain 'order' as a substring. For eg: order_number,job_order", 
				dataMunger.getOrderByFields("select city,winner1,player_match from ipl1.csv order by winner"));
		
		
		assertNotNull("testGetOrderByFieldsFailure():Check getOrderByFields() method. The query string can contain more than one order by fields.The query string might not contain order by clause at all. The field names, condition values might contain 'order' as a substring. For eg: order_number,job_order",
				dataMunger.getOrderByFields("select city,winner,player_match from ipl1.csv where season > 2014 and city ='Bangalore' order by city"));
		
	}
	
	
	
	
	

}
