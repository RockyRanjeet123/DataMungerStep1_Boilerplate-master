package com.stackroute.datamunger.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.stackroute.datamunger.DataMunger;

public class DataMungerTest {

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

	@Test
	public void testGetFileName() {

		assertEquals(
				"File name extraction failed. File name can be found after a space after from clause. Note: CSV file can contain a field that contains from as a part of the column name. For eg: from_date,from_hrs etc",
				"ipl.csv", dataMunger.getFileName("select city,winner,team1,team2 from ipl.csv"));

	}

	@Test
	public void testGetFileNameFailure() {
		assertNotNull(
				"File name extraction failed. File name can be found after a space after from clause. Note: CSV file can contain a field that contains from as a part of the column name. For eg: from_date,from_hrs etc",
				dataMunger.getFileName("select city,winner,team1,team2 from ipl.csv"));
		assertNotEquals(
				"File name extraction failed. File name can be found after a space after from clause. Note: CSV file can contain a field that contains from as a part of the column name. For eg: from_date,from_hrs etc",
				"ipl1.csv", dataMunger.getFileName("select city,winner,team1,team2 from ipl.csv"));

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
				dataMunger.getFields("select * from ipl1.csv"));

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

	@SuppressWarnings("deprecation")
	@Test
	public void testGetColumnsWithWhereClause() {

		assertEquals(
				"testGetColumnsWithWhereClause() : File name extraction failed. Check getFile() method. File name can be found after a space after from clause. Note: CSV file can contain a field that contains from as a part of the column name. For eg: from_date,from_hrs etc",
				"ipl.csv", dataMunger.getFileName("select city,winner,player_match from ipl.csv where season > 2014"));
		assertEquals(
				"testGetColumnsWithWhereClause() : Select fields extractions failed. The query string can have multiple fields separated by comma after the 'select' keyword. The extracted fields is supposed to be stored in a String array which is to be returned by the method getFields(). Check getFields() method",
				new String[] { "city", "winner", "player_match" },
				dataMunger.getFields("select city,winner,player_match from ipl.csv where season > 2014"));
		assertEquals(
				"testGetColumnsWithWhereClause() : Retrieval of Base Query failed. BaseQuery contains from the beginning of the query till the where clause",
				"select city,winner,player_match from ipl.csv",
				dataMunger.getBaseQuery("select city,winner,player_match from ipl.csv where season > 2014"));
		assertEquals(
				"testGetColumnsWithWhereClause() : Retrieval of conditions part failed. The conditions part contains starting from where keyword till the next keyword, which is either group by or order by clause. In case of absence of both group by and order by clause, it will contain till the end of the query string. ",
				"season > 2014",
				dataMunger.getConditionsPartQuery("select city,winner,player_match from ipl.csv where season > 2014"));
		assertEquals(
				"testGetColumnsWithWhereClause() : Retrieval of conditions failed. Check getConditions() method. The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords.",
				new String[] { "season > 2014" },
				dataMunger.getConditions("select city,winner,player_match from ipl.csv where season > 2014"));

	}

	@Test
	public void testGetColumnsWithWhereClauseFailure() {

		assertNotNull(
				"testGetColumnsWithWhereClauseFailure() : File name extraction failed. Check getFile() method. File name can be found after a space after from clause. Note: CSV file can contain a field that contains from as a part of the column name. For eg: from_date,from_hrs etc",
				dataMunger.getFileName("select city,winner,player_match from ipl1.csv where season1 > 2014"));
		assertNotEquals(
				"testGetColumnsWithWhereClauseFailure() : File name extraction failed. Check getFile() method. File name can be found after a space after from clause. Note: CSV file can contain a field that contains from as a part of the column name. For eg: from_date,from_hrs etc",
				"ipl.csv",
				dataMunger.getFileName("select city,winner,player_match from ipl1.csv where season1 > 2014"));
		assertNotEquals(
				"testGetColumnsWithWhereClauseFailure() : Select fields extractions failed. The query string can have multiple fields separated by comma after the 'select' keyword. The extracted fields is supposed to be stored in a String array which is to be returned by the method getFields(). Check getFields() method",
				new String[] { "city", "winner", "player_match" },
				dataMunger.getFields("select city,winner,player_match from ipl1.csv where season1 > 2014"));
		assertNotEquals(
				"testGetColumnsWithWhereClauseFailure() : Retrieval of Base Query failed. BaseQuery contains from the beginning of the query till the where clause",
				"select city,winner,player_match from ipl1.csv",
				dataMunger.getBaseQuery("select city1,winner,player_match from ipl1.csv where season > 2014"));
		assertNotEquals(
				"testGetColumnsWithWhereClauseFailure() : Retrieval of conditions part failed. The conditions part contains starting from where keyword till the next keyword, which is either group by or order by clause. In case of absence of both group by and order by clause, it will contain till the end of the query string.",
				"season > 2014", dataMunger
						.getConditionsPartQuery("select city,winner,player_match from ipl1.csv where season1 > 2014"));
		assertNotEquals(
				"testGetColumnsWithWhereClauseFailure() : Retrieval of conditions failed. Check getConditions() method. The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords.",
				new String[] { "season > 2014" },
				dataMunger.getConditions("select city,winner,player_match from ipl1.csv where season1 > 2014"));

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetColumnsWithMultipleWhereClause() {

		assertEquals(
				"testGetColumnsWithMultipleWhereClause() : File name extraction failed. Check getFile() method. File name can be found after a space after from clause. Note: CSV file can contain a field that contains from as a part of the column name. For eg: from_date,from_hrs etc",
				"ipl.csv", dataMunger.getFileName(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore'"));
		assertEquals(
				"testGetColumnsWithMultipleWhereClause() : Select fields extractions failed. The query string can have multiple fields separated by comma after the 'select' keyword. The extracted fields is supposed to be stored in a String array which is to be returned by the method getFields(). Check getFields() method",
				new String[] { "city", "winner", "player_match" }, dataMunger.getFields(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore'"));
		assertEquals(
				"testGetColumnsWithMultipleWhereClause() : Retrieval of Base Query failed. BaseQuery contains from the beginning of the query till the where clause",
				"select city,winner,player_match from ipl.csv", dataMunger.getBaseQuery(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore'"));
		assertEquals(
				"testGetColumnsWithMultipleWhereClause() : Retrieval of conditions part failed. The conditions part contains starting from where keyword till the next keyword, which is either group by or order by clause. In case of absence of both group by and order by clause, it will contain till the end of the query string.",
				"season > 2014 and city ='bangalore'", dataMunger.getConditionsPartQuery(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore'"));
		assertEquals(
				"testGetColumnsWithMultipleWhereClause() : Retrieval of conditions failed. Check getConditions() method. The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords",
				new String[] { "season > 2014", "city ='bangalore'" }, dataMunger.getConditions(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore'"));
		assertEquals(
				"testGetColumnsWithMultipleWhereClause() : Retrieval of Logical Operators failed. AND/OR keyword will exist in the query only if where conditions exists and it contains multiple conditions.The extracted logical operators will be stored in a String array which will be returned by the method. Please note that AND/OR can exist as a substring in the conditions as well. For eg: name='Alexander',color='Red' etc.",
				new String[] { "and" }, dataMunger.getLogicalOperators(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore'"));
	}

	@Test
	public void testGetColumnsWithMultipleWhereClauseFailure() {

		assertNotNull(
				"testGetColumnsWithMultipleWhereClauseFailure() : File name extraction failed. Check getFile() method. File name can be found after a space after from clause. Note: CSV file can contain a field that contains from as a part of the column name. For eg: from_date,from_hrs etc",
				dataMunger.getFileName(
						"select city,winner,player_match from ipl1.csv where season1 > 2014 and city ='Bangalore'"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereClauseFailure() : File name extraction failed. Check getFile() method. File name can be found after a space after from clause. Note: CSV file can contain a field that contains from as a part of the column name. For eg: from_date,from_hrs etc",
				"ipl.csv", dataMunger.getFileName(
						"select city,winner,player_match from ipl1.csv where season1 > 2014 and city ='Bangalore'"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereClauseFailure() : Select fields extractions failed. The query string can have multiple fields separated by comma after the 'select' keyword. The extracted fields is supposed to be stored in a String array which is to be returned by the method getFields(). Check getFields() method",
				new String[] { "city", "winner", "player_match" }, dataMunger.getFields(
						"select city1,winner1,player_match from ipl1.csv where season1 > 2014 and city ='Bangalore'"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereClauseFailure() : Retrieval of Base Query failed. BaseQuery contains from the beginning of the query till the where clause",
				"select city,winner,player_match from ipl1.csv ", dataMunger.getBaseQuery(
						"select city,winner,player_match from ipl.csv where season1 > 2014 and city ='Bangalore'"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereClauseFailure() : Retrieval of conditions part failed. The conditions part contains starting from where keyword till the next keyword, which is either group by or order by clause. In case of absence of both group by and order by clause, it will contain till the end of the query string",
				"season < 2014 and city ='bangalore'", dataMunger.getConditionsPartQuery(
						"select city,winner,player_match from ipl1.csv where season1 > 2014 and city ='Bangalore'"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereClauseFailure() : Retrieval of conditions failed. Check getConditions() method. The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords",
				new String[] { "season > 2014", "city ='bangalore'" }, dataMunger.getConditions(
						"select city,winner,player_match from ipl1.csv where season > 2014 and city ='chennai'"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereClauseFailure() : Retrieval of Logical Operators failed. AND/OR keyword will exist in the query only if where conditions exists and it contains multiple conditions.The extracted logical operators will be stored in a String array which will be returned by the method. Please note that AND/OR can exist as a substring in the conditions as well. For eg: name='Alexander',color='Red' etc.",
				new String[] { "and" }, dataMunger.getLogicalOperators(
						"select city,winner,player_match from ipl1.csv where season > 2014 or city ='Bangalore'"));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetColumnsWithMultipleWhereOrClause() {
		assertNotNull(
				"testGetColumnsWithMultipleWhereOrClause() : File name extraction failed. Check getFile() method. File name can be found after a space after from clause. Note: CSV file can contain a field that contains from as a part of the column name. For eg: from_date,from_hrs etc",
				dataMunger.getFileName(
						"select city,winner,player_match from ipl.csv where season > 2014 or city ='Bangalore'"));
		assertEquals(
				"testGetColumnsWithMultipleWhereOrClause() : File name extraction failed. Check getFile() method. File name can be found after a space after from clause. Note: CSV file can contain a field that contains from as a part of the column name. For eg: from_date,from_hrs etc",
				"ipl.csv", dataMunger.getFileName(
						"select city,winner,player_match from ipl.csv where season > 2014 or city ='Bangalore'"));
		assertEquals(
				"testGetColumnsWithMultipleWhereOrClause() : Select fields extractions failed. The query string can have multiple fields separated by comma after the 'select' keyword. The extracted fields is supposed to be stored in a String array which is to be returned by the method getFields(). Check getFields() method",
				new String[] { "city", "winner", "player_match" }, dataMunger.getFields(
						"select city,winner,player_match from ipl.csv where season > 2014 or city ='Bangalore'"));
		assertEquals(
				"testGetColumnsWithMultipleWhereOrClause() : Retrieval of Base Query failed. BaseQuery contains from the beginning of the query till the where clause",
				"select city,winner,player_match from ipl.csv", dataMunger.getBaseQuery(
						"select city,winner,player_match from ipl.csv where season > 2014 or city ='Bangalore'"));
		assertEquals(
				"testGetColumnsWithMultipleWhereOrClause() :  Retrieval of conditions part failed. The conditions part contains starting from where keyword till the next keyword, which is either group by or order by clause. In case of absence of both group by and order by clause, it will contain till the end of the query string",
				"season > 2014 or city ='bangalore'", dataMunger.getConditionsPartQuery(
						"select city,winner,player_match from ipl.csv where season > 2014 or city ='Bangalore'"));
		assertEquals(
				"testGetColumnsWithMultipleWhereOrClause() :  Retrieval of conditions failed. Check getConditions() method. The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords",
				new String[] { "season > 2014", "city ='bangalore'" }, dataMunger.getConditions(
						"select city,winner,player_match from ipl.csv where season > 2014 or city ='Bangalore'"));
		assertEquals(
				"testGetColumnsWithMultipleWhereOrClause() :  Retrieval of Logical Operators failed. AND/OR keyword will exist in the query only if where conditions exists and it contains multiple conditions.The extracted logical operators will be stored in a String array which will be returned by the method. Please note that AND/OR can exist as a substring in the conditions as well. For eg: name='Alexander',color='Red' etc",
				new String[] { "or" }, dataMunger.getLogicalOperators(
						"select city,winner,player_match from ipl.csv where season > 2014 or city ='Bangalore'"));
	}

	@Test
	public void testGetColumnsWithMultipleWhereOrClauseFailure() {

		assertNotNull(
				"testGetColumnsWithMultipleWhereOrClauseFailure() :  File name extraction failed. Check getFile() method. File name can be found after a space after from clause. Note: CSV file can contain a field that contains from as a part of the column name. For eg: from_date,from_hrs etc",
				dataMunger.getFileName(
						"select city,winner,player_match from ipl1.csv where season > 2014 or city ='Bangalore'"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereOrClauseFailure() :  File name extraction failed. Check getFile() method. File name can be found after a space after from clause. Note: CSV file can contain a field that contains from as a part of the column name. For eg: from_date,from_hrs etc",
				"ipl.csv", dataMunger.getFileName(
						"select city,winner,player_match from ipl1.csv where season > 2014 or city ='Bangalore'"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereOrClauseFailure() :  Select fields extractions failed. The query string can have multiple fields separated by comma after the 'select' keyword. The extracted fields is supposed to be stored in a String array which is to be returned by the method getFields(). Check getFields() method",
				new String[] { "city1", "winner1", "player_match1" }, dataMunger.getFields(
						"select city,winner,player_match from ipl1.csv where season > 2014 or city ='Bangalore'"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereOrClauseFailure() :  Retrieval of Base Query failed. BaseQuery contains from the beginning of the query till the where clause",
				"select city,winner,player_match from ipl.csv", dataMunger.getBaseQuery(
						"select city,winner,player_match from ipl1.csv where season > 2014 or city ='Bangalore'"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereOrClauseFailure() :  Retrieval of conditions part failed. The conditions part contains starting from where keyword till the next keyword, which is either group by or order by clause. In case of absence of both group by and order by clause, it will contain till the end of the query string",
				"season < 2014 or city !='bangalore'", dataMunger.getConditionsPartQuery(
						"select city,winner,player_match from ipl1.csv where season1 > 2014 or city ='Bangalore'"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereOrClauseFailure() :  Retrieval of conditions failed. Check getConditions() method. The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords",
				new String[] { "season1 < 2014", "city ='bangalore'" }, dataMunger.getConditions(
						"select city,winner,player_match from ipl1.csv where season1 > 2014 or city ='Bangalore'"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereOrClauseFailure() :  Retrieval of Logical Operators failed. AND/OR keyword will exist in the query only if where conditions exists and it contains multiple conditions.The extracted logical operators will be stored in a String array which will be returned by the method. Please note that AND/OR can exist as a substring in the conditions as well. For eg: name='Alexander',color='Red' etc",
				new String[] { "or" }, dataMunger.getLogicalOperators(
						"select city,winner,player_match from ipl1.csv where season1 > 2014 and city ='Bangalore'"));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetColumnsWithThreeWhereOrClause() {

		assertEquals(
				"testGetColumnsWithThreeWhereOrClause() :  File name extraction failed. Check getFile() method. File name can be found after a space after from clause. Note: CSV file can contain a field that contains from as a part of the column name. For eg: from_date,from_hrs etc",
				"ipl.csv", dataMunger.getFileName(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' or city ='Delhi'"));
		assertEquals(
				"testGetColumnsWithThreeWhereOrClause() :  Select fields extractions failed. The query string can have multiple fields separated by comma after the 'select' keyword. The extracted fields is supposed to be stored in a String array which is to be returned by the method getFields(). Check getFields() method",
				new String[] { "city", "winner", "player_match" }, dataMunger.getFields(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' or city ='Delhi'"));
		assertEquals(
				"testGetColumnsWithThreeWhereOrClause() :  Retrieval of Base Query failed. BaseQuery contains from the beginning of the query till the where clause",
				"select city,winner,player_match from ipl.csv", dataMunger.getBaseQuery(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' or city ='Delhi'"));
		assertEquals(
				"testGetColumnsWithThreeWhereOrClause() :  Retrieval of conditions part failed. The conditions part contains starting from where keyword till the next keyword, which is either group by or order by clause. In case of absence of both group by and order by clause, it will contain till the end of the query string",
				"season > 2014 and city ='bangalore' or city ='delhi'", dataMunger.getConditionsPartQuery(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' or city ='Delhi'"));
		assertEquals(
				"testGetColumnsWithThreeWhereOrClause() :  Retrieval of conditions failed. Check getConditions() method. The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords",
				new String[] { "season > 2014", "city ='bangalore'", "city ='delhi'" }, dataMunger.getConditions(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' or city ='Delhi'"));
		assertEquals(
				"testGetColumnsWithThreeWhereOrClause() :  Retrieval of Logical Operators failed. AND/OR keyword will exist in the query only if where conditions exists and it contains multiple conditions.The extracted logical operators will be stored in a String array which will be returned by the method. Please note that AND/OR can exist as a substring in the conditions as well. For eg: name='Alexander',color='Red' etc",
				new String[] { "and", "or" }, dataMunger.getLogicalOperators(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' or city ='Delhi'"));
	}

	@Test
	public void testGetColumnsWithThreeWhereOrClauseFailure() {

		assertNotNull(
				"testGetColumnsWithThreeWhereOrClauseFailure() :  File name extraction failed. Check getFile() method. File name can be found after a space after from clause. Note: CSV file can contain a field that contains from as a part of the column name. For eg: from_date,from_hrs etc",
				dataMunger.getFileName(
						"select city,winner,player_match from ipl1.csv where season1 > 2014 and city ='Bangalore' or city ='Delhi'"));
		assertNotEquals(
				"testGetColumnsWithThreeWhereOrClauseFailure() :  File name extraction failed. Check getFile() method. File name can be found after a space after from clause. Note: CSV file can contain a field that contains from as a part of the column name. For eg: from_date,from_hrs etc",
				"ipl.csv", dataMunger.getFileName(
						"select city,winner,player_match from ipl1.csv where season1 > 2014 and city ='Bangalore' or city ='Delhi'"));
		assertNotEquals(
				"testGetColumnsWithThreeWhereOrClauseFailure() :  Select fields extractions failed. The query string can have multiple fields separated by comma after the 'select' keyword. The extracted fields is supposed to be stored in a String array which is to be returned by the method getFields(). Check getFields() method",
				new String[] { "city", "winner", "player_match" }, dataMunger.getFields(
						"select city1,winner,player_match from ipl.csv where season1 > 2014 and city ='Bangalore' or city ='Delhi'"));
		assertNotEquals(
				"testGetColumnsWithThreeWhereOrClauseFailure() :  Retrieval of Base Query failed. BaseQuery contains from the beginning of the query till the where clause",
				"select city,winner,player_match from ipl.csv", dataMunger.getBaseQuery(
						"select city,winner,player_match from ipl1.csv where season > 2014 and city ='Bangalore' or city ='Delhi'"));
		assertNotEquals(
				"testGetColumnsWithThreeWhereOrClauseFailure() : Retrieval of conditions part failed. The conditions part contains starting from where keyword till the next keyword, which is either group by or order by clause. In case of absence of both group by and order by clause, it will contain till the end of the query string",
				"season > 2014 and city ='bangalore' or city ='delhi'", dataMunger.getConditionsPartQuery(
						"select city,winner,player_match from ipl1.csv where season1 < 2014 and city ='Bangalore' or city ='Delhi'"));
		assertNotEquals(
				"testGetColumnsWithThreeWhereOrClauseFailure() : Retrieval of conditions failed. Check getConditions() method. The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords",
				new String[] { "season > 2014", "city ='bangalore'", "city ='chennai'" }, dataMunger.getConditions(
						"select city,winner,player_match from ipl1.csv where season1 < 2014 and city ='Bangalore' or city ='Delhi'"));
		assertNotEquals(
				"testGetColumnsWithThreeWhereOrClauseFailure() : Retrieval of Logical Operators failed. AND/OR keyword will exist in the query only if where conditions exists and it contains multiple conditions.The extracted logical operators will be stored in a String array which will be returned by the method. Please note that AND/OR can exist as a substring in the conditions as well. For eg: name='Alexander',color='Red' etc",
				new String[] { "and", "or" }, dataMunger.getLogicalOperators(
						"select city,winner,player_match from ip11.csv where season > 2014 and1 city ='Bangalore' or city ='Delhi'"));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetColumnsWithMultipleWhereGroupByClause() {

		assertEquals(
				"testGetColumnsWithMultipleWhereGroupByClause() : Select fields extractions failed. The query string can have multiple fields separated by comma after the 'select' keyword. The extracted fields is supposed to be stored in a String array which is to be returned by the method getFields(). Check getFields() method",
				new String[] { "city", "winner", "player_match" }, dataMunger.getFields(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' group by winner"));
		assertEquals(
				"testGetColumnsWithMultipleWhereGroupByClause() : Retrieval of Base Query failed. BaseQuery contains from the beginning of the query till the where clause",
				"select city,winner,player_match from ipl.csv", dataMunger.getBaseQuery(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' group by winner"));
		assertEquals(
				"testGetColumnsWithMultipleWhereGroupByClause() : Retrieval of conditions part failed. The conditions part contains starting from where keyword till the next keyword, which is either group by or order by clause. In case of absence of both group by and order by clause, it will contain till the end of the query string",
				"season > 2014 and city ='bangalore'", dataMunger.getConditionsPartQuery(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' group by winner"));
		assertEquals(
				"testGetColumnsWithMultipleWhereGroupByClause() : Retrieval of conditions failed. Check getConditions() method. The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords",
				new String[] { "season > 2014", "city ='bangalore'" }, dataMunger.getConditions(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' group by winner"));
		assertEquals(
				"testGetColumnsWithMultipleWhereGroupByClause() : Retrieval of Logical Operators failed. AND/OR keyword will exist in the query only if where conditions exists and it contains multiple conditions.The extracted logical operators will be stored in a String array which will be returned by the method. Please note that AND/OR can exist as a substring in the conditions as well. For eg: name='Alexander',color='Red' etc",
				new String[] { "and" }, dataMunger.getLogicalOperators(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' group by winner"));
		assertEquals(
				"testGetColumnsWithMultipleWhereGroupByClause() : Check getGroupByFields() method. The query string can contain more than one group by fields. it is also possible thant the query string might not contain group by clause at all. The field names, condition values might contain 'group' as a substring. For eg: newsgroup_name",
				new String[] { "winner" }, dataMunger.getGroupByFields(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' group by winner"));

	}

	@Test
	public void testGetColumnsWithMultipleWhereGroupByClauseFailure() {

		assertNotNull(
				"testGetColumnsWithMultipleWhereGroupByClauseFailure() : Select fields extractions failed. The query string can have multiple fields separated by comma after the 'select' keyword. The extracted fields is supposed to be stored in a String array which is to be returned by the method getFields(). Check getFields() method",
				dataMunger.getFields(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' group by winner"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereGroupByClauseFailure() : Select fields extractions failed. The query string can have multiple fields separated by comma after the 'select' keyword. The extracted fields is supposed to be stored in a String array which is to be returned by the method getFields(). Check getFields() method",
				new String[] { "city1", "winner", "player_match" }, dataMunger.getFields(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' group by winner"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereGroupByClauseFailure() : Retrieval of Base Query failed. BaseQuery contains from the beginning of the query till the where clause",
				"select city1,winner,player_match from ipl1.csv", dataMunger.getBaseQuery(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' group by winner"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereGroupByClauseFailure() : Retrieval of conditions part failed. The conditions part contains starting from where keyword till the next keyword, which is either group by or order by clause. In case of absence of both group by and order by clause, it will contain till the end of the query string",
				"season1 < 2014 and city ='bangalore'", dataMunger.getConditionsPartQuery(
						"select city,winner,player_match from ipl1.csv where season > 2014 and city ='Bangalore' group by winner"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereGroupByClauseFailure() : Retrieval of conditions failed. Check getConditions() method. The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords ",
				new String[] { "season1 < 2014", "city ='bangalore'" }, dataMunger.getConditions(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' group by winner"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereGroupByClauseFailure() : Retrieval of Logical Operators failed. AND/OR keyword will exist in the query only if where conditions exists and it contains multiple conditions.The extracted logical operators will be stored in a String array which will be returned by the method. Please note that AND/OR can exist as a substring in the conditions as well. For eg: name='Alexander',color='Red' etc",
				new String[] { "or" }, dataMunger.getLogicalOperators(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' group by winner"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereGroupByClauseFailure() : Check getGroupByFields() method. The query string can contain more than one group by fields. it is also possible thant the query string might not contain group by clause at all. The field names, condition values might contain 'group' as a substring. For eg: newsgroup_name",
				new String[] { "winner1" }, dataMunger.getGroupByFields(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' group by winner"));

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetWithGroupByClause() {

		assertEquals(
				"testGetWithGroupByClause() : Select fields extractions failed. The query string can have multiple fields separated by comma after the 'select' keyword. The extracted fields is supposed to be stored in a String array which is to be returned by the method getFields(). Check getFields() method",
				new String[] { "city", "winner", "player_match" },
				dataMunger.getFields("select city,winner,player_match from ipl.csv group by winner"));
		assertEquals(
				"testGetWithGroupByClause() : Retrieval of Base Query failed. BaseQuery contains from the beginning of the query till the where clause",
				"select city,winner,player_match from ipl.csv",
				dataMunger.getBaseQuery("select city,winner,player_match from ipl.csv group by winner"));
		assertEquals(
				"testGetWithGroupByClause() : Retrieval of conditions part is not returning null. The conditions part contains starting from where keyword till the next keyword, which is either group by or order by clause. In case of absence of both group by and order by clause, it will contain till the end of the query string",
				null,
				dataMunger.getConditionsPartQuery("select city,winner,player_match from ipl.csv group by winner"));
		assertEquals(
				"testGetWithGroupByClause() : Retrieval of conditions failed. Check getConditions() method. The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords",
				null, dataMunger.getConditions("select city,winner,player_match from ipl.csv group by winner"));
		assertEquals(
				"testGetWithGroupByClause() : Logical Operators should be null. AND/OR keyword will exist in the query only if where conditions exists and it contains multiple conditions.The extracted logical operators will be stored in a String array which will be returned by the method. Please note that AND/OR can exist as a substring in the conditions as well. For eg: name='Alexander',color='Red' etc",
				null, dataMunger.getLogicalOperators("select city,winner,player_match from ipl.csv group by winner"));
		assertEquals(
				"testGetWithGroupByClause() : Check getGroupByFields() method. The query string can contain more than one group by fields. it is also possible thant the query string might not contain group by clause at all. The field names, condition values might contain 'group' as a substring. For eg: newsgroup_name",
				new String[] { "winner" },
				dataMunger.getGroupByFields("select city,winner,player_match from ipl.csv group by winner"));
	}

	@Test
	public void testGetWithGroupByClauseFailure() {

		assertNotNull(
				"testGetWithGroupByClauseFailure() : Select fields extractions failed. The query string can have multiple fields separated by comma after the 'select' keyword. The extracted fields is supposed to be stored in a String array which is to be returned by the method getFields(). Check getFields() method",
				dataMunger.getFields("select city,winner,player_match from ipl1.csv group by winner"));
		assertNotEquals(
				"testGetWithGroupByClauseFailure() : Select fields extractions failed. The query string can have multiple fields separated by comma after the 'select' keyword. The extracted fields is supposed to be stored in a String array which is to be returned by the method getFields(). Check getFields() method",
				new String[] { "city1", "winner1", "player_match" },
				dataMunger.getFields("select city,winner,player_match from ipl1.csv group by winner"));
		assertNotEquals(
				"testGetWithGroupByClauseFailure() : Retrieval of Base Query failed. BaseQuery contains from the beginning of the query till the where clause",
				"select city1,winner,player_match from ipl.csv",
				dataMunger.getBaseQuery("select city1,winner,player_match from ipl1.csv group by winner"));
		assertNotEquals(
				"testGetWithGroupByClauseFailure() : Retrieval of conditions part failed. The conditions part contains starting from where keyword till the next keyword, which is either group by or order by clause. In case of absence of both group by and order by clause, it will contain till the end of the query string",
				1, dataMunger.getConditionsPartQuery("select city,winner,player_match from ipl1.csv group by winner"));
		assertNotEquals(
				"testGetWithGroupByClauseFailure() : Retrieval of conditions failed. Check getConditions() method. The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords",
				1, dataMunger.getConditions("select city,winner,player_match from ipl.csv group by winner"));
		assertNotEquals(
				"testGetWithGroupByClauseFailure() : Retrieval of Logical Operators failed. AND/OR keyword will exist in the query only if where conditions exists and it contains multiple conditions.The extracted logical operators will be stored in a String array which will be returned by the method. Please note that AND/OR can exist as a substring in the conditions as well. For eg: name='Alexander',color='Red' etc",
				1, dataMunger.getLogicalOperators("select city,winner,player_match from ipl1.csv group by winner"));
		assertNotEquals(
				"testGetWithGroupByClauseFailure() : Check getGroupByFields() method. The query string can contain more than one group by fields. it is also possible thant the query string might not contain group by clause at all. The field names, condition values might contain 'group' as a substring. For eg: newsgroup_name",
				new String[] { "winner1" },
				dataMunger.getGroupByFields("select city,winner1,player_match from ipl1.csv group by winner"));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetColumnsWithMultipleWhereAndOrderByClause() {

		assertEquals(
				"testGetColumnsWithMultipleWhereAndOrderByClause() : Select fields extractions failed. The query string can have multiple fields separated by comma after the 'select' keyword. The extracted fields is supposed to be stored in a String array which is to be returned by the method getFields(). Check getFields() method",
				new String[] { "city", "winner", "player_match" }, dataMunger.getFields(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' order by city"));
		assertEquals(
				"testGetColumnsWithMultipleWhereAndOrderByClause() : Retrieval of Base Query failed. BaseQuery contains from the beginning of the query till the where clause",
				"select city,winner,player_match from ipl.csv", dataMunger.getBaseQuery(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' order by city"));
		assertEquals(
				"testGetColumnsWithMultipleWhereAndOrderByClause() : Retrieval of conditions part failed. The conditions part contains starting from where keyword till the next keyword, which is either group by or order by clause. In case of absence of both group by and order by clause, it will contain till the end of the query string",
				"season > 2014 and city ='bangalore'", dataMunger.getConditionsPartQuery(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' order by city"));
		assertEquals(
				"testGetColumnsWithMultipleWhereAndOrderByClause() : Retrieval of conditions failed. Check getConditions() method. The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords",
				new String[] { "season > 2014", "city ='bangalore'" }, dataMunger.getConditions(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' order by city"));
		assertEquals(
				"testGetColumnsWithMultipleWhereAndOrderByClause() : Retrieval of Logical Operators failed. AND/OR keyword will exist in the query only if where conditions exists and it contains multiple conditions.The extracted logical operators will be stored in a String array which will be returned by the method. Please note that AND/OR can exist as a substring in the conditions as well. For eg: name='Alexander',color='Red' etc",
				new String[] { "and" }, dataMunger.getLogicalOperators(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' order by city"));
		assertEquals(
				"testGetColumnsWithMultipleWhereAndOrderByClause() : Check getGroupByFields() method. The query string can contain more than one group by fields. it is also possible thant the query string might not contain group by clause at all. The field names, condition values might contain 'group' as a substring. For eg: newsgroup_name",
				null, dataMunger.getGroupByFields(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' order by city"));
		assertEquals(
				"testGetColumnsWithMultipleWhereAndOrderByClause() : Check getOrderByFields() method. The query string can contain more than one order by fields.The query string might not contain order by clause at all. The field names, condition values might contain 'order' as a substring. For eg: order_number,job_order ",
				new String[] { "city" }, dataMunger.getOrderByFields(
						"select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' order by city"));
	}

	@Test
	public void testGetColumnsWithMultipleWhereAndOrderByClauseFailure() {

		assertNotNull(
				"testGetColumnsWithMultipleWhereAndOrderByClauseFailure(): Retrieval of Base Query failed. BaseQuery contains from the beginning of the query till the where clause",
				dataMunger.getBaseQuery(
						"select city1,winner,player_match from ipl1.csv where season > 2014 and city ='Bangalore' order by city"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereAndOrderByClauseFailure(): Select fields extractions failed. The query string can have multiple fields separated by comma after the 'select' keyword. The extracted fields is supposed to be stored in a String array which is to be returned by the method getFields(). Check getFields() method",
				new String[] { "city1", "winner", "player_match" }, dataMunger.getFields(
						"select city,winner,player_match from ipl1.csv where season > 2014 and city ='Bangalore' order by city"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereAndOrderByClauseFailure(): Retrieval of Base Query failed. BaseQuery contains from the beginning of the query till the where clause",
				"select city,winner,player_match from ipl1.csv", dataMunger.getBaseQuery(
						"select city1,winner,player_match from ipl1.csv where season > 2014 and city ='Bangalore' order by city"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereAndOrderByClauseFailure(): Retrieval of conditions part failed. The conditions part contains starting from where keyword till the next keyword, which is either group by or order by clause. In case of absence of both group by and order by clause, it will contain till the end of the query string",
				"season < 2014 and city ='bangalore'", dataMunger.getConditionsPartQuery(
						"select city,winner,player_match from ipl1.csv where season1 > 2014 and city ='Bangalore' order by city"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereAndOrderByClauseFailure(): Retrieval of conditions failed. Check getConditions() method. The query can contain one or multiple conditions. In case of multiple conditions, the conditions will be separated by AND/OR keywords",
				new String[] { "season < 2014", "city ='bangalore'" }, dataMunger.getConditions(
						"select city,winner,player_match from ipl1.csv where season1 > 2014 and city ='Bangalore' order by city"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereAndOrderByClauseFailure(): Retrieval of Logical Operators failed. AND/OR keyword will exist in the query only if where conditions exists and it contains multiple conditions.The extracted logical operators will be stored in a String array which will be returned by the method. Please note that AND/OR can exist as a substring in the conditions as well. For eg: name='Alexander',color='Red' etc",
				new String[] { "or" }, dataMunger.getLogicalOperators(
						"select city,winner,player_match from ipl1.csv where season > 2014 and city ='Bangalore' order by city"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereAndOrderByClauseFailure(): Check getGroupByFields() method. The query string can contain more than one group by fields. it is also possible thant the query string might not contain group by clause at all. The field names, condition values might contain 'group' as a substring. For eg: newsgroup_name",
				1, dataMunger.getGroupByFields(
						"select city,winner,player_match from ipl1.csv where season > 2014 and city ='Bangalore' order by city"));
		assertNotEquals(
				"testGetColumnsWithMultipleWhereAndOrderByClauseFailure(): Check getOrderByFields() method. The query string can contain more than one order by fields.The query string might not contain order by clause at all. The field names, condition values might contain 'order' as a substring. For eg: order_number,job_order",
				new String[] { "city1" }, dataMunger.getOrderByFields(
						"select city,winner,player_match from ipl1.csv where season > 2014 and city ='Bangalore' order by city"));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetSplitStrings() {

		assertNotNull("testGetSplitStrings() : Splitting query into tokens returns null value",
				dataMunger.getSplitStrings("select * from ipl.csv"));
		assertNotNull("testGetSplitStrings() : Splitting query into tokens returns null value",
				dataMunger.getSplitStrings(
						"select count(city),sum(win_by_runs),min(win_by_runs),max(win_by_runs),avg(win_by_runs) from data/ipl.csv)"));
		assertEquals("testGetSplitStrings() : Splitting query into tokens does not return the correct values",
				new String[] { "select", "*", "from", "ipl.csv" }, dataMunger.getSplitStrings("select * from ipl.csv"));
		assertEquals("testGetSplitStrings() : Splitting query into tokens does not return the correct values",
				new String[] { "select",
						"count(city),sum(win_by_runs),min(win_by_runs),max(win_by_runs),avg(win_by_runs)", "from",
						"data/ipl.csv" },
				dataMunger.getSplitStrings(
						"select count(city),sum(win_by_runs),min(win_by_runs),max(win_by_runs),avg(win_by_runs) from data/ipl.csv"));
		assertEquals("testGetSplitStrings() : Splitting query into tokens does not return the correct values",
				new String[] { "select", "city,winner,player_match", "from", "ipl1.csv", "where", "season", ">", "2014",
						"and", "city", "=", "'bangalore'", "order", "by", "city" },
				dataMunger.getSplitStrings(
						"select city,winner,player_match from ipl1.csv where season > 2014 and city = 'Bangalore' order by city"));

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetAggregateFunctions() {

		assertNull(
				"testGetAggregateFunctions() : Invalid aggregate columns displayed query into tokens returns null value",
				dataMunger.getAggregateFunctions("select * from ipl.csv"));
		assertNotNull("testGetAggregateFunctions() : Aggregate function returns null", dataMunger.getAggregateFunctions(
				"select count(city),sum(win_by_runs),min(win_by_runs),max(win_by_runs),avg(win_by_runs) from data/ipl.csv"));
		assertEquals("testGetAggregateFunctions() : Aggregate function does not return the correct values",
				new String[] { "count(city)", "sum(win_by_runs)", "min(win_by_runs)", "max(win_by_runs)",
						"avg(win_by_runs)" },
				dataMunger.getAggregateFunctions(
						"select count(city),sum(win_by_runs),min(win_by_runs),max(win_by_runs),avg(win_by_runs) from data/ipl.csv"));
		assertEquals("testGetAggregateFunctions() : Aggregate function does not return the correct values",
				new String[] { "count(city)", "sum(win_by_runs)", "min(win_by_runs)", "max(win_by_runs)",
						"avg(win_by_runs)" },
				dataMunger.getAggregateFunctions(
						"select count(city),sum(win_by_runs),min(win_by_runs),max(win_by_runs),avg(win_by_runs) from data/ipl.csv where season > 2014"));

	}

}