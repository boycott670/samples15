package com.nespresso.exercise.electric_trip;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ElectricTripTest
{

	@Test
	public void shouldGetAtDestinationWithLowerCharge()
	{
		ElectricTrip trip = new ElectricTrip("PARIS-200-BOURGES");
		int batterySize = 85; // KWh
		int lowSpeedPerformance = 5; // Km per KWh
		int highSpeedPerformance = 5; // Km per KWh
		int participantId = trip.startTripIn("PARIS", batterySize, lowSpeedPerformance, highSpeedPerformance);
		trip.go(participantId);
		assertThat(trip.locationOf(participantId), is("BOURGES"));
		assertThat(trip.chargeOf(participantId), is("53%")); // % is rounded to closest integer
	}

	@Test
	public void shouldGoToFinalDestination()
	{
		ElectricTrip trip = new ElectricTrip("PARIS-250-LIMOGES-100-BORDEAUX");
		int participantId = trip.startTripIn("PARIS", 85, 5, 5);
		trip.go(participantId);
		assertThat(trip.locationOf(participantId), is("BORDEAUX"));
		assertThat(trip.chargeOf(participantId), is("18%"));
	}

}
