package com.nespresso.exercise.electric_trip.entities;

public class ChargingCity extends City
{

	private final int kwhPerHour;

	public ChargingCity(String name, int kwhPerHour)
	{
		super(name);
		this.kwhPerHour = kwhPerHour;
	}

	@Override
	public int charge(int hoursOfCharge)
	{
		return kwhPerHour * hoursOfCharge;
	}
	
}
