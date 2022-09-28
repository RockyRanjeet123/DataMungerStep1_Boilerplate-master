package com.stackroute.datamunger.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;



import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.stackroute.datamunger.DataMunger;

public class DataMungerTestTask1 {

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
	public void testGetSplitStrings()
	{
		assertEquals("testGetSplitStrings(),Spliting Queries into tokens does not return correct values "
				, new String[]{"select","*","from","ipl.csv"}, dataMunger.getSplitStrings("select * from ipl.csv"));
		
		assertEquals("testGetSplitStrings(),Spliting Queries into tokens does not return correct values ",
				new String[]{"select","city,winner,player_match","from","ipl.csv","where","season",">","2014","and","city","=","'bangalore'","order","by","city"}, 
				dataMunger.getSplitStrings("select city,winner,player_match from ipl.csv where season > 2014 and city = 'Bangalore' order by city"));
		
		assertEquals("testGetSplitStrings(),Spliting Queries into tokens does not return correct values", new String[]{"select","count(city),max(winner)","from","ipl.csv"}, dataMunger.getSplitStrings("select count(city),max(winner) from ipl.csv"));
		
	}
	
	@Test
	public void testGetSplitStringsFailure()
	{
        assertNotNull("testGetSplitStringsFailure() , Splitting Queries into tokens returns null value", dataMunger.getSplitStrings("Select * from ipl.csv"));
		
		assertNotNull("testGetSplitStringsFailure() , Splitting Queries into tokens returns null value", dataMunger.getSplitStrings("Select sum(win_by_runs),city from ipl.csv group by city"));
		
	}
	
	@Test
	public void testGetFileName()
	{
	
		
		assertEquals("File name extraction failed .File name can be found after a space after from clause.Note: CSV file can contain a field that contains from as a part of the column name. For eg: from_date,from_hrs etc", 
				"ipl.csv", dataMunger.getFileName("select city,winner,team1,team2 from ipl.csv"));
		
		assertEquals("File name extraction failed .File name can be found after a space after from clause.Note: CSV file can contain a field that contains from as a part of the column name. For eg: from_date,from_hrs etc", 
				"ipl.csv", dataMunger.getFileName("select max(winner),city from ipl.csv group by city"));
	}
	
	@Test
	public void testGetFileNameFailure()
	{
		assertNotNull("File name extraction failed. File name can be found after a space after from clause. Note: CSV file can contain a field that contains from as a part of the column name. For eg: from_date,from_hrs etc",
				dataMunger.getFileName("select city,winner,team1,team2 from ipl.csv"));
		
		assertNotEquals("File name extraction failed. File name can be found after a space after from clause. Note: CSV file can contain a field that contains from as a part of the column name. For eg: from_date,from_hrs etc",
				      "ipl1.csv", dataMunger.getFileName("select city,winner,team1,team2 from ipl.csv"));
	}
	
	@Test
	public void testGetBaseQuery()
	{
		
		assertEquals("testGetBaseQuery(),Retrieval of Base Query failed. BaseQuery contains from the beginning of the query till the where clause", 
				"select city,winner,player_match from ipl.csv", dataMunger.getBaseQuery("select city,winner,player_match from ipl.csv where season > 2014"));
	}
	
	@Test
	public void testGetBaseQueryFailure()
	{
		
		assertNotEquals("testGetBaseQueryFailure(),Retrieval of Base Query failed. BaseQuery contains from the beginning of the query till the where clause",
				"select city,winner,player_match from ipl.csv", dataMunger.getBaseQuery("select city1,winner,player_match from ipl1.csv where season > 2014"));
		
		assertNotNull("testGetBaseQueryFailure() , Retrieval of Base Query returns Null", dataMunger.getBaseQuery("select * from ipl.csv"));
	}
	
	
	
}
