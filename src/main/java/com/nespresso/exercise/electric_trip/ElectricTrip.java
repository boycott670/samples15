package com.nespresso.exercise.electric_trip;

import java.util.HashMap;
import java.util.Map;

import com.nespresso.exercise.electric_trip.entities.City;
import com.nespresso.exercise.electric_trip.entities.Participant;
import com.nespresso.exercise.electric_trip.parsers.DefaultTripParser;
import com.nespresso.exercise.electric_trip.parsers.TripParser;

public class ElectricTrip
{
	private final TripParser tripParser = new DefaultTripParser();
	
	private final Map<String, City> cities;
	
	private int lastGivenParticipantId = 0;
	private Map<Integer, Participant> participants = new HashMap<>();
	
	public ElectricTrip (final String trip)
	{
		cities = tripParser.connectCities(trip, tripParser.parseCities(trip));
	}
	
	public synchronized int startTripIn (final String city, final int batterySize, final int lowSpeedPerformance, final int highSpeedPerformance)
	{
		participants.put(++ lastGivenParticipantId, new Participant(cities.get(city), batterySize, lowSpeedPerformance, highSpeedPerformance));
		return lastGivenParticipantId;
	}
	
	public void go (int participantId)
	{
		participants.get(participantId).go();
	}
	
	public void sprint (int participantId)
	{
		participants.get(participantId).sprint();
	}
	
	public String locationOf (int participantId)
	{
		return participants.get(participantId).locationOf().getName();
	}
	
	public String chargeOf (int participantId)
	{
		return String.format("%.0f%%", participants.get(participantId).chargeOf() * 100);
	}
	
	public void charge (int participantId, int hoursOfCharge)
	{
		participants.get(participantId).charge(hoursOfCharge);
	}
}
