package com.nespresso.exercise.electric_trip.entities;

public class Participant
{
	private City currentCity;
	private final int batterySize;
	private final int lowSpeedPerformance;
	private final int highSpeedPerformance;
	
	private double currentBatteryLevel;
	
	public Participant(City currentCity, int batterySize, int lowSpeedPerformance, int highSpeedPerformance)
	{
		this.currentCity = currentCity;
		this.batterySize = batterySize;
		this.lowSpeedPerformance = lowSpeedPerformance;
		this.highSpeedPerformance = highSpeedPerformance;
		
		currentBatteryLevel = this.batterySize;
	}
	
	private void advanceByConsuming (final int kmsPerKwh)
	{
		while (currentCity.hasNextCity() && currentBatteryLevel >= Integer.valueOf(currentCity.getDistanceInKilometersToNextCity()).doubleValue() / kmsPerKwh)
		{
			currentBatteryLevel -= Integer.valueOf(currentCity.getDistanceInKilometersToNextCity()).doubleValue() / kmsPerKwh;
			currentCity = currentCity.getNextCity();
		}
	}
	
	public void go ()
	{
		advanceByConsuming(lowSpeedPerformance);
	}
	
	public void sprint ()
	{
		advanceByConsuming(highSpeedPerformance);
	}
	
	public City locationOf ()
	{
		return currentCity;
	}
	
	public double chargeOf ()
	{
		return currentBatteryLevel / batterySize;
	}
}
