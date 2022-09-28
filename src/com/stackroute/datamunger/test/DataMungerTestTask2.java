package com.stackroute.datamunger.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.stackroute.datamunger.DataMunger;

public class DataMungerTestTask2 {

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
	public void testGetAllColumns() {
		assertEquals(
				"testGetAllColumns() : Select fields extractions failed. The query string can have multiple fields separated by comma after the 'select' keyword. The extracted fields is supposed to be stored in a String array which is to be returned by the method getFields(). Check getFields() method",
				new String[] { "*" }, dataMunger.getFields("select * from ipl.csv"));
	}

	@Test
	public void testGetAllColumnsFailure() {
		assertNotNull(
				"testGetAllColumnsFailure() : Select fields extractions failed. The query string can have multiple fields separated by comma after the 'select' keyword. The extracted fields is supposed to be stored in a String array which is to be returned by the method getFields(). Check getFields() method",
				dataMunger.getFields("select * from ipl.csv"));

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetColumnNames() {
		assertEquals(
				"testGetColumnNames() : Select fields extractions failed. The query string can have multiple fields separated by comma after the 'select' keyword. The extracted fields is supposed to be stored in a String array which is to be returned by the method getFields(). Check getFields() method",
				new String[] { "city", "winner", "team1", "team2" },
				dataMunger.getFields("select city,winner,team1,team2 from ipl.csv"));
	}

	
	@Test
	public void testGetColumnNamesFailure() {

		assertNotNull(
				"testGetColumnNamesFailure() : Select fields extractions failed. The query string can have multiple fields separated by comma after the 'select' keyword. The extracted fields is supposed to be stored in a String array which is to be returned by the method getFields(). Check getFields() method",
				dataMunger.getFields("select city1,winner1,team1,team2 from ipl.csv"));
		assertNotEquals(
				"testGetColumnNamesFailure() : Select fields extractions failed. The query string can have multiple fields separated by comma after the 'select' keyword. The extracted fields is supposed to be stored in a String array which is to be returned by the method getFields(). Check getFields() method",
				new String[] { "city", "winner", "team1", "team2" },
				dataMunger.getFields("select city1,winner1,team1,team2 from ipl.csv"));
	}

	@Test
	public void testGetConditionsPartQuery() {
		assertEquals(
				"testGetConditionsPartQuery() :Retrieval of conditions part failed. The conditions part contains starting from where keyword till the next keyword, which is either group by or order by clause. In case of absence of both group by and order by clause, it will contain till the end of the query string",
				"season > 2014",
				dataMunger.getConditionsPartQuery("select city,winner,player_match from ipl.csv where season > 2014"));

		assertEquals(
				"testGetConditionsPartQuery() :Retrieval of conditions part failed. The conditions part contains starting from where keyword till the next keyword, which is either group by or order by clause. In case of absence of both group by and order by clause, it will contain till the end of the query string",
				"season > 2014 and city ='bangalore'", dataMunger.getConditionsPartQuery(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore'"));

		assertEquals(
				"testGetConditionsPartQuery() :Retrieval of conditions part failed. The conditions part contains starting from where keyword till the next keyword, which is either group by or order by clause. In case of absence of both group by and order by clause, it will contain till the end of the query string",
				"season > 2014 and city ='bangalore' or city ='delhi'", dataMunger.getConditionsPartQuery(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' or city ='Delhi'"));
	}

	@Test
	public void testGetConditionsPartQueryFailure() {

		assertNotEquals(
				"testGetColumnsWithMultipleWhereClauseFailure() : Retrieval of conditions part failed. The conditions part contains starting from where keyword till the next keyword, which is either group by or order by clause. In case of absence of both group by and order by clause, it will contain till the end of the query string",
				"season < 2014 and city ='bangalore'", dataMunger.getConditionsPartQuery(
						"select city,winner,player_match from ipl1.csv where season1 > 2014 and city ='Bangalore'"));

		assertNotNull(
				"testGetColumnsWithMultipleWhereClauseFailure() : Retrieval of conditions part failed. The conditions part contains starting from where keyword till the next keyword, which is either group by or order by clause. In case of absence of both group by and order by clause, it will contain till the end of the query string",
				dataMunger.getConditionsPartQuery(
						"select city,winner,player_match from ipl1.csv where season > 2014 and city ='Bangalore' group by winner"));

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetConditions() {
		assertEquals(
				"testGetConditions() :Retrieval of conditions failed. Check getConditions() method.The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords.",
				new String[] { "season > 2014" },
				dataMunger.getConditions("select city,winner,player_match from ipl.csv where season > 2014"));

		assertEquals(
				"testGetConditions() :Retrieval of conditions failed. Check getConditions() method.The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords.",
				new String[] { "season > 2014", "city ='bangalore'" }, dataMunger.getConditions(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore'"));

		assertEquals(
				"testGetConditions() :Retrieval of conditions failed. Check getConditions() method.The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords.",
				new String[] { "season > 2014", "city ='bangalore'", "city ='delhi'" }, dataMunger.getConditions(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' or city ='Delhi'"));

		assertEquals(
				"testGetConditions() :Retrieval of conditions failed. Check getConditions() method.The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords.",
				new String[] { "season > 2014" }, dataMunger.getConditions(
						"select city,winner,player_match from ipl.csv where season > 2014 group by winner"));

		assertEquals(
				"testGetConditions() :Retrieval of conditions failed. Check getConditions() method.The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords.",
				new String[] { "season > 2014" }, dataMunger.getConditions(
						"select city,winner,player_match from ipl.csv where season > 2014 order by city"));

		assertNull(
				"testGetConditions() :Retrieval of conditions failed. Check getConditions() method.The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords.",
				dataMunger.getConditions("select city,winner,player_match from ipl.csv"));

		assertNull(
				"testGetConditions() :Retrieval of conditions failed. Check getConditions() method.The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords.",
				dataMunger.getConditions("select city,winner,player_match from ipl.csv group by winner"));
	}

	@Test
	public void testGetConditionsFailure() {

		assertNotNull(
				"testGetConditions() :Retrieval of conditions failed. Check getConditions() method.The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords.",
				dataMunger.getConditions(
						"select city,winner,player_match from ipl1.csv where season1 < 2014 and city ='Bangalore' or city ='Delhi'"));

		assertNotNull(
				"testGetConditions() :Retrieval of conditions failed. Check getConditions() method.The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords.",
				dataMunger.getConditions(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' group by winner"));

		assertNotNull(
				"testGetConditions() :Retrieval of conditions failed. Check getConditions() method.The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords.",
				dataMunger.getConditions(
						"select city,winner,player_match from ipl1.csv where season1 > 2014 and city ='Bangalore' order by city"));

	}

}
