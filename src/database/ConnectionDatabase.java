package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDatabase
{
	private static Connection connection;
	private static Statement statement;

	public static Connection getConnection()
	{
		return connection;
	}

	public static Statement getStatement()
	{
		return statement;
	}

	public static void connectDatabase()
	{
		try
		{
			connection = DriverManager.getConnection("jdbc:sqlite:responsibilities.db");
			statement = connection.createStatement();
			System.out.println("Database connected");
		}

		catch (SQLException e)
		{
			e.printStackTrace();
			return;
		}
	}

	public static void disconnectDatabase()
	{
		try
		{
			connection.close();
			statement.close();
			System.out.println("Database connection closed");
		}

		catch (SQLException e)
		{
			e.printStackTrace();
			return;
		}
	}

}
