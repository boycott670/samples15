package com.nespresso.exercise.electric_trip.entities;

import java.util.NoSuchElementException;

public class City
{
	private final String name;
	private City nextCity;
	private int distanceInKilometersToNextCity;
	
	public City (final String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setNextCity(City nextCity)
	{
		this.nextCity = nextCity;
	}
	
	public void setDistanceInKilometersToNextCity(int distanceInKilometersToNextCity)
	{
		this.distanceInKilometersToNextCity = distanceInKilometersToNextCity;
	}

	public boolean hasNextCity ()
	{
		return nextCity != null;
	}
	
	public City getNextCity ()
	{
		if (!hasNextCity())
		{
			throw new NoSuchElementException();
		}
		
		return nextCity;
	}

	public int getDistanceInKilometersToNextCity()
	{
		if (!hasNextCity())
		{
			throw new NoSuchElementException();
		}
		
		return distanceInKilometersToNextCity;
	}
}
