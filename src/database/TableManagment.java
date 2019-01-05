package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.Model;

public class TableManagment
{
	private static ResultSet resultSet;
	private static List<String> actualListOfQueryResults = new ArrayList<String>();
	
	public static List<String> getActualListOfQueryResults()
	{
		return actualListOfQueryResults;
	}

	public static void createNeWTable(String tableName)
	{
		String sql = "CREATE TABLE IF NOT EXISTS " + tableName +
					 " (name_of_responsibility TEXT NOT NULL, " +
					 "day INTEGER NOT NULL, month INTEGER NOT NULL, " +
					 "year INTEGER NOT NULL, executor TEXT NOT NULL, " +
					 "done TEXT, adnotation TEXT)";
		try
		{
			ConnectionDatabase.getStatement().executeUpdate(sql);
		} 
		
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void insertResponsibility(String tableName, String nameOfResponsibility,
											int day, int month, int year, String executor, String done, 
											String adnotation)
	{
		String sql = "INSERT INTO " + tableName + " (name_of_responsibility, day, month,"
					+ " year, executor, done, adnotation) VALUES ('" + nameOfResponsibility +"', " 
					+ day + ", " + month + ", " + year + ", '" + executor + "', '" + done + "', '" + adnotation + "')";
		
		try
		{
			ConnectionDatabase.getStatement().executeUpdate(sql);
		} 
		
		catch (SQLException e)
		{
			e.printStackTrace();
		}	
	}
	
	public static void selectResponsibility(String nameOfResponsibility, int day, int month, 
											int year)
	{
		Model model = new Model();
		
		int monthSql = month + 1;
		
		String sql = "SELECT name_of_responsibility, day, month, year, executor, done, adnotation"
				   + " FROM " + model.changeIntMonthToStringMonth(month).toUpperCase() 
				   + year + " WHERE name_of_responsibility='" + nameOfResponsibility 
				   +"' AND day='" + day +"' " + "AND month='" + monthSql + "' AND year='" 
				   + year + "'";
		try
		{
			actualListOfQueryResults.clear();
			resultSet = ConnectionDatabase.getStatement().executeQuery(sql);
			String actualResult;
			
			while(resultSet.next())
			{
				actualResult = resultSet.getString("name_of_responsibility")
				+ " " + resultSet.getInt("day") + " " + resultSet.getInt("month") 
				+ " " + resultSet.getInt("year") + " " + resultSet.getString("executor") 
				+ " " + resultSet.getString("done") + " " + resultSet.getString("adnotation");
				actualListOfQueryResults.add(actualResult);
			}
			resultSet.close();
		} 
	
		catch (SQLException e)
		{
			e.printStackTrace();
		}	
	}
}
