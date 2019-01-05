package application;

import java.util.Calendar;
import java.util.GregorianCalendar;

import database.TableManagment;

public class Model
{
	private Calendar calendar;
	private String actualMonth;
	private int yearOfMonthBefore;
	private int monthBefore;
	private int maximumOfMonthBefore;
	private int dayOfWeekOfMaximumOfMonthBefore;
	
	private final String executor1 = "Kasia"; //Gotuje, myje naczynia i robi zakupy 
	//na początku tygodnia
	private final String executor2 = "Ania"; //Gotuje, myje naczynia i robi zakupy 
	//na koniec tygodnia
	
	public String getActualMonth()
	{
		return actualMonth;
	}
	
	public String getExecutor1()
	{
		return executor1;
	}
	
	public String getExecutor2()
	{
		return executor2;
	}
	
	public Calendar getCalendar()
	{
		return calendar;
	}
	
	public int getMonthBefore()
	{
		return monthBefore;
	}
	
	public int getMaximumOfMonthBefore()
	{
		return maximumOfMonthBefore;
	}

	public int getDayOfWeekOfMaximumOfMonthBefore()
	{
		return dayOfWeekOfMaximumOfMonthBefore;
	}
	
	public int getYearOfMonthBefore()
	{
		return yearOfMonthBefore;
	}

	public Model()
	{
		theLastDayAndNameOfMonthBefore();
	}
	
	public String changeIntMonthToStringMonth(int intMonth)
	{
		String stringMonth = "";

		switch (intMonth)
		{
			case 0:
			{
				stringMonth = "JANUARY";
				break;
			}
			case 1:
			{
				stringMonth = "FEBRUARY";
				break;
			}
			case 2:
			{
				stringMonth = "MARCH";
				break;
			}
			case 3:
			{
				stringMonth = "APRIL";
				break;
			}
			case 4:
			{
				stringMonth = "MAY";
				break;
			}
			case 5:
			{
				stringMonth = "JUNE";
				break;
			}
			case 6:
			{
				stringMonth = "JULY";
				break;
			}
			case 7:
			{
				stringMonth = "AUGUST";
				break;
			}
			case 8:
			{
				stringMonth = "SEPTEMBER";
				break;
			}
			case 9:
			{
				stringMonth = "OCTOBER";
				break;
			}
			case 10:
			{
				stringMonth = "NOVEMBER";
				break;
			}
			case 11:
			{
				stringMonth = "DECEMBER";
				break;
			}
		}
		return stringMonth;
	}

	public String whichMonth(Calendar calendar)
	{
		int actualMonth = calendar.get(Calendar.MONTH);
		String actualMonthToString = changeIntMonthToStringMonth(actualMonth);
		return actualMonthToString;
	}

	public void theLastDayAndNameOfMonthBefore()
	{
		calendar = new GregorianCalendar();
		
		monthBefore = calendar.get(Calendar.MONTH)-1;
		
		if(monthBefore == -1)
		{
			monthBefore = 11;
			yearOfMonthBefore = calendar.get(Calendar.YEAR)-1;
		}
		
		else
		{
			yearOfMonthBefore = calendar.get(Calendar.YEAR);
		}
		
		Calendar tempCalendar = new GregorianCalendar();
		tempCalendar.set(Calendar.YEAR, yearOfMonthBefore);
		tempCalendar.set(Calendar.MONTH, monthBefore);
		tempCalendar.set(Calendar.DATE, 1);
		
		maximumOfMonthBefore = tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		tempCalendar.set(Calendar.DATE, maximumOfMonthBefore);
	
		dayOfWeekOfMaximumOfMonthBefore = tempCalendar.get(Calendar.DAY_OF_WEEK);
		
	}
	
	public void insertTableWithRubbish(int actualDayOfWeek, String actualRubbishExecutor)
	{
		for(int i = 1; i<= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++)
		{
			if(actualDayOfWeek > 7)
			{
				if(actualRubbishExecutor == executor1)
				{
					actualRubbishExecutor = executor2;
				}
				else
				{
					actualRubbishExecutor = executor1;
				}
				actualDayOfWeek = 1;
			}
			
			TableManagment.insertResponsibility(actualMonth+calendar.get(Calendar.YEAR),"Śmieci", i, calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR), actualRubbishExecutor, null, null);
			actualDayOfWeek++;
		}	
		
	}
	
	public void calculateRubbish()
	{
		theLastDayAndNameOfMonthBefore();
		
		TableManagment.selectResponsibility("Śmieci", maximumOfMonthBefore, monthBefore, yearOfMonthBefore);
		
		String[] partsOfRubbishQueryResults = TableManagment.getActualListOfQueryResults().get(0).split(" ");
		String previousRubbishExecutor = partsOfRubbishQueryResults[4];
		String actualRubbishExecutor;
		
		Calendar tempCalendar = new GregorianCalendar();
		tempCalendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		int actualDayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK);
		
		// miesiac który skończył się na koniec tygodnia
		if(dayOfWeekOfMaximumOfMonthBefore > 7 && previousRubbishExecutor.equals(executor1))
		{
			actualRubbishExecutor = executor2;
			insertTableWithRubbish(actualDayOfWeek, actualRubbishExecutor);
		}
		
		if(dayOfWeekOfMaximumOfMonthBefore > 7 && previousRubbishExecutor.equals(executor2))
		{
			actualRubbishExecutor = executor1;
			insertTableWithRubbish(actualDayOfWeek, actualRubbishExecutor);
		}
		
		//miesiac ktory skonczyl sie w srodku tygodnia
		if(dayOfWeekOfMaximumOfMonthBefore < 7 && previousRubbishExecutor.equals(executor2))
		{
			actualRubbishExecutor = executor2;
			actualDayOfWeek = dayOfWeekOfMaximumOfMonthBefore;
			insertTableWithRubbish(actualDayOfWeek, actualRubbishExecutor);
		}
		
		if(dayOfWeekOfMaximumOfMonthBefore < 7 && previousRubbishExecutor.equals(executor1))
		{
			actualRubbishExecutor = executor1;
			actualDayOfWeek = dayOfWeekOfMaximumOfMonthBefore;
			insertTableWithRubbish(actualDayOfWeek, actualRubbishExecutor);
		}
	}
	
	public int findLastParticularDayOfWeekLastMonth(int dayOfWeek)
	{
		int dayInLastMonthWithParticularDayOfWeek = 0;
		
		Calendar tempCalendar = new GregorianCalendar();
		tempCalendar.set(Calendar.YEAR, yearOfMonthBefore);
		tempCalendar.set(Calendar.MONTH, monthBefore);
		
		for(int i=maximumOfMonthBefore; i>0; i--)
		{
			tempCalendar.set(Calendar.DATE, i);
			if(tempCalendar.get(Calendar.DAY_OF_WEEK) == dayOfWeek)
			{
				dayInLastMonthWithParticularDayOfWeek = i;
			}		
		}
		return dayInLastMonthWithParticularDayOfWeek;
	}
	
	public String calculateActualShoppingExecutorLastThursday()
	{
		TableManagment.selectResponsibility("Zakupy", findLastParticularDayOfWeekLastMonth(4), monthBefore, yearOfMonthBefore);
		String[] partsOfShoppingDinnerAndWashingQueryResults = TableManagment.getActualListOfQueryResults().get(0).split(" ");
		String actualShoppingExecutorLastThursday = partsOfShoppingDinnerAndWashingQueryResults[4];
		return actualShoppingExecutorLastThursday;
	}
	
	public String calculateActualDinnerExecutorLastThursday()
	{
		TableManagment.selectResponsibility("Obiad", findLastParticularDayOfWeekLastMonth(4), monthBefore, yearOfMonthBefore);
		String[] partsOfShoppingDinnerAndWashingQueryResults = TableManagment.getActualListOfQueryResults().get(0).split(" ");
		String actualDinnerExecutorLastThursday = partsOfShoppingDinnerAndWashingQueryResults[4];
		return actualDinnerExecutorLastThursday;
	}
	
	public String calculateActualWashingTheDishesExecutorLastThursday()
	{
		TableManagment.selectResponsibility("Mycie naczyń", findLastParticularDayOfWeekLastMonth(4), monthBefore, yearOfMonthBefore);
		String[] partsOfShoppingDinnerAndWashingQueryResults = TableManagment.getActualListOfQueryResults().get(0).split(" ");
		String actualWashingTheDishesExecutorLastThursday = partsOfShoppingDinnerAndWashingQueryResults[4];
		return actualWashingTheDishesExecutorLastThursday;
	}
	
	public void calculateShopping()
	{
		String actualShoppingExecutorLastThursday = calculateActualShoppingExecutorLastThursday();
		
		int actualDayOfWeek = dayOfWeekOfMaximumOfMonthBefore;
		
		if(actualDayOfWeek > 7)
		{
			actualDayOfWeek = 1;
		}
		
		for(int i = 1; i<= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++)
		{
			if(actualDayOfWeek > 7)
			{
				if(actualShoppingExecutorLastThursday.equals(executor2))
				{
					actualShoppingExecutorLastThursday = executor1;
				}
				
				else
				{
					actualShoppingExecutorLastThursday = executor2;
				}
				
				actualDayOfWeek = 1;
			}
			
			if(actualDayOfWeek == 1 || actualDayOfWeek == 2 || actualDayOfWeek == 3 ||
			  (actualDayOfWeek == 4 && actualShoppingExecutorLastThursday.equals(executor2)))
			{	
				TableManagment.insertResponsibility(actualMonth+calendar.get(Calendar.YEAR),"Zakupy", i, calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR), executor1, null, null);
			}
			
			if(actualDayOfWeek == 5 || actualDayOfWeek == 6 || actualDayOfWeek == 7 ||
			  (actualDayOfWeek == 4 && actualShoppingExecutorLastThursday.equals(executor1)))
			{
				TableManagment.insertResponsibility(actualMonth+calendar.get(Calendar.YEAR),"Zakupy", i, calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR), executor2, null, null);	
			}
			
			actualDayOfWeek++;
		}
	}
	
	public void calculateDinner()
	{
		String actualDinnerExecutorLastThursday = calculateActualShoppingExecutorLastThursday();
		
		int actualDayOfWeek = dayOfWeekOfMaximumOfMonthBefore;
		
		if(actualDayOfWeek > 7)
		{
			actualDayOfWeek = 1;
		}
		
		for(int i = 1; i<= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++)
		{
			if(actualDayOfWeek > 7)
			{
				if(actualDinnerExecutorLastThursday.equals(executor2))
				{
					actualDinnerExecutorLastThursday = executor1;
				}
				
				else
				{
					actualDinnerExecutorLastThursday = executor2;
				}
				
				actualDayOfWeek = 1;
			}
			
			if(actualDayOfWeek == 1 || actualDayOfWeek == 2 || actualDayOfWeek == 3 ||
			  (actualDayOfWeek == 4 && actualDinnerExecutorLastThursday.equals(executor2)))
			{	
				TableManagment.insertResponsibility(actualMonth+calendar.get(Calendar.YEAR),"Obiad", i, calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR), executor1, null, null);
			}
			
			if(actualDayOfWeek == 5 || actualDayOfWeek == 6 || actualDayOfWeek == 7 ||
			  (actualDayOfWeek == 4 && actualDinnerExecutorLastThursday.equals(executor1)))
			{
				TableManagment.insertResponsibility(actualMonth+calendar.get(Calendar.YEAR),"Obiad", i, calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR), executor2, null, null);	
			}
			
			actualDayOfWeek++;
		}
	}
	
	public void calculateWashingTheDishes()
	{
		String actualWashingTheDishesExecutorLastThursday = calculateActualShoppingExecutorLastThursday();
		
		int actualDayOfWeek = dayOfWeekOfMaximumOfMonthBefore;
		
		if(actualDayOfWeek > 7)
		{
			actualDayOfWeek = 1;
		}
		
		for(int i = 1; i<= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++)
		{
			if(actualDayOfWeek > 7)
			{
				if(actualWashingTheDishesExecutorLastThursday.equals(executor2))
				{
					actualWashingTheDishesExecutorLastThursday = executor1;
				}
				
				else
				{
					actualWashingTheDishesExecutorLastThursday = executor2;
				}
				
				actualDayOfWeek = 1;
			}
			
			if(actualDayOfWeek == 1 || actualDayOfWeek == 2 || actualDayOfWeek == 3 ||
			  (actualDayOfWeek == 4 && actualWashingTheDishesExecutorLastThursday.equals(executor2)))
			{	
				TableManagment.insertResponsibility(actualMonth+calendar.get(Calendar.YEAR),"Mycie naczyń", i, calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR), executor1, null, null);
			}
			
			if(actualDayOfWeek == 5 || actualDayOfWeek == 6 || actualDayOfWeek == 7 ||
			  (actualDayOfWeek == 4 && actualWashingTheDishesExecutorLastThursday.equals(executor1)))
			{
				TableManagment.insertResponsibility(actualMonth+calendar.get(Calendar.YEAR),"Mycie naczyń", i, calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR), executor2, null, null);	
			}
			
			actualDayOfWeek++;
		}
	}
	
	public void getDataFromDatabaseToCalendar()
	{
		calendar = GregorianCalendar.getInstance();
		actualMonth = whichMonth(calendar);
		TableManagment.createNeWTable(actualMonth+calendar.get(Calendar.YEAR));
		calculateRubbish();
		calculateShopping();
		calculateDinner();
		calculateWashingTheDishes();
		
	}
}
