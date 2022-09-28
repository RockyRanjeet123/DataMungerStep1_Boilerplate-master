### Problem Statement

String Parsing  (Query string)


## Our task 1 involves three steps, given below:

1) Write a program to read the query string as input and split them into words. Print the output on the console as given below:

    Input String : 	select * from ipl.csv where season > 2014 and city = 'Bangalore'
    
    Output String: select
    			    	 * 
    			     from 
    			     ipl.csv  
    			     where  
    			     season
    			     > 
    			     2014
    			     and 
    			     city
    			     =
    			     'bangalore'

2) Further enhance your program to extract certain parts of the same query:

	i. Get only file name from the query string.
	
		Input String : select * from ipl.csv where season > 2014 and city = 'Bangalore'
		Output String : ipl.csv
	
3) Get only base part(before `where` word) of the query from the given query string. 

		Input String : select * from ipl.csv where season > 2014 and city = 'Bangalore'
		Output String : select * from ipl.csv 

After all the logic is written for these methods run DataMungerTestTask1.java file
-----------------------------------------------------------------------------------

## Our task II involves three steps, given below:

1)Extract the selected fields/information from the given query.

     Input String : select city,winner,player_match from ipl.csv where season > 2014 
                    and city ='Bangalore'
	Output String :	city
            			winner
            			player_match


2)Get only filter part(after `where` word before'group by or order by' if they exist 
  in the query) of the query from the given query string. 
	
		Input String : select * from ipl.csv where season > 2014 and city ='Bangalore'
		Output String : season > 2014 and city ='bangalore'
		
		Input String : select city from ipl.csv where season > 2014 or city ='Bangalore'
		               order by city
		Output String : season > 2014 or city ='bangalore


3) As there will be multiple conditions, separate each condition and display in different line.
	    
	    Input String : select * from ipl.csv where season > 2014 and city ='Bangalore'
		Output String : season > 2014 
                         city ='bangalore'
                         
After all the logic is written for these methods run DataMungerTestTask2.java file.   
----------------------------------------------------------------------------------- 

##Our task III involves two steps, given below:            
		               
1)Extract the logical operators in sequence from the given query string. 
  Note: Logical operators are "and, or, not"
	    
	 Input String : select season,winner,player_match from ipl.csv where season > 2014 and    		                 city ='Bangalore' or date > '31-12-2014'
	    Output String : 
		               and
		               or
		        
2)Extract the order by field from the given string.
  Note : The user may need the information in sorted order of a particular field.
        
     Input String : select * from ipl.csv where season > 2016 and city='Bangalore' order by    					   win_by_runs
	Output String : win_by_runs
 
After all the logic is written for these methods run DataMungerTestTask3.java file. 
----------------------------------------------------------------------

##Our task IV involves two steps, given below:    
    
 1)Extract the group by field from the given string.
   Note : user may need the related information grouped together.
   For Example they may require to see the information department wise.
        
        Input String : select team1, city from ipl.csv where season > 2016 and 	                     city ='Bangalore' group by team1
		Output String : team1
	
2) User may require the information like who is getting maximum salary or minimum age etc.. these are called aggregate functions (minimum, maximum, count, average, sum)
	Input String : select avg(win_by_wickets),min(win_by_runs) from ipl.csv 
	Output String : 
		            avg(win_by_wickets)
                     min(win_by_runs)
	            
	   	Note:  Other parts like where clause, order by, group by may be present in the query.
	   	
After all the logic is written for these methods run DataMungerTestTask4.java file.
------------------------------------------------------------------------------

At the end of all the methods Implementation done run DataMungertest.java file.  	   	
-------------------------------------------------------------------------------