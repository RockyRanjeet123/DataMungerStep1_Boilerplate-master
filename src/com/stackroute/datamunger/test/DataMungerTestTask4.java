package com.stackroute.datamunger.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.stackroute.datamunger.DataMunger;

public class DataMungerTestTask4 {
	
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
	public void testGetGroupByFields()
	{
		assertEquals("testGetGroupByFields():Check getGroupByFields() method. The query string can contain more than one group by fields. it is also possible thant the query string might not contain group by clause at all. The field names, condition values might contain 'group' as a substring. For eg: newsgroup_name", 
				new String[]{"winner"},
				dataMunger.getGroupByFields("select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' group by winner"));
		
		assertEquals("testGetGroupByFields():Check getGroupByFields() method. The query string can contain more than one group by fields. it is also possible thant the query string might not contain group by clause at all. The field names, condition values might contain 'group' as a substring. For eg: newsgroup_name",
				new String[]{"winner"},
				dataMunger.getGroupByFields("select city,winner,player_match from ipl.csv where group by winner"));
		
		assertNull("testGetGroupByFields():Check getGroupByFields() method. The query string can contain more than one group by fields. it is also possible thant the query string might not contain group by clause at all. The field names, condition values might contain 'group' as a substring. For eg: newsgroup_name", 
				dataMunger.getGroupByFields("select city,winner,player_match from ipl1.csv where season > 2014 and city ='Bangalore' order by city"));
	
	}
	
	@Test
	public void testGetGroupByFieldsFailure()
	{
		assertNotNull("testGetGroupByFieldsFailure():Check getGroupByFields() method. The query string can contain more than one group by fields. it is also possible thant the query string might not contain group by clause at all. The field names, condition values might contain 'group' as a substring. For eg: newsgroup_name", 
				dataMunger.getGroupByFields("select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' group by winner"));
		
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testGetAggregateFunctions()
	{
		assertEquals("testGetAggregateFunctions() : Aggregate function does not return the correct values",
				new String[]{"count(city)","sum(win_by_runs)","min(win_by_runs)","max(win_by_runs)","avg(win_by_runs)"},
				dataMunger.getAggregateFunctions("select count(city),sum(win_by_runs),min(win_by_runs),max(win_by_runs),avg(win_by_runs) from ipl.csv"));
		
		assertEquals("testGetAggregateFunctions() : Aggregate function does not return the correct values",
				new String[]{"count(city)"},
				dataMunger.getAggregateFunctions("select count(city),win_by_runs from ipl.csv where season > 2014 group by win_by_runs"));
		
		assertNull("testGetAggregateFunctions() : Aggregate function does not return the correct values",
				dataMunger.getAggregateFunctions("select * from ipl.csv"));
	}
	
	@Test
	public void testGetAggregateFunctionsFailure()
	{
		assertNotNull("testGetAggregateFunctionsFailure() :Aggregate function does not return the correct values",
				dataMunger.getAggregateFunctions("select count(city),sum(win_by_runs),min(win_by_runs) from ipl.csv"));
	}
	

}
