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

	@Test
	public void shouldStayAtIntermediateStopIfNotEnoughCharge()
	{
		ElectricTrip trip = new ElectricTrip("PARIS-250-LIMOGES-500-MARSEILLES");
		int participantId = trip.startTripIn("PARIS", 85, 5, 5);
		trip.go(participantId);
		assertThat(trip.locationOf(participantId), is("LIMOGES"));
		assertThat(trip.chargeOf(participantId), is("41%"));
		trip.go(participantId);
		assertThat(trip.locationOf(participantId), is("LIMOGES"));
		assertThat(trip.chargeOf(participantId), is("41%"));
	}

	@Test
	public void shouldConsumeMoreWhenSprinting()
	{
		ElectricTrip trip = new ElectricTrip("PARIS-250-LIMOGES-100-BORDEAUX");
		int participantId = trip.startTripIn("PARIS", 85, 5, 3);
		trip.sprint(participantId);
		assertThat(trip.locationOf(participantId), is("LIMOGES"));
		assertThat(trip.chargeOf(participantId), is("2%"));
	}

	@Test
	public void shouldMoveMultipleParticipantsAccordingly()
	{
		ElectricTrip trip = new ElectricTrip("PARIS-250-LIMOGES-100-BORDEAUX");
		int id1 = trip.startTripIn("PARIS", 85, 5, 3);
		int id2 = trip.startTripIn("LIMOGES", 70, 5, 3);
		trip.sprint(id1);
		trip.go(id2);
		assertThat(trip.locationOf(id1), is("LIMOGES"));
		assertThat(trip.chargeOf(id1), is("2%"));
		assertThat(trip.locationOf(id2), is("BORDEAUX"));
		assertThat(trip.chargeOf(id2), is("71%"));
	}

	@Test
	public void shouldAllowCharging()
	{
		ElectricTrip trip = new ElectricTrip("PARIS-250-LIMOGES:25-100-BORDEAUX"); // 25 is the KWh charged per hour of
																					// charge time at Limoges
		int id = trip.startTripIn("PARIS", 85, 5, 3);
		trip.sprint(id);
		assertThat(trip.locationOf(id), is("LIMOGES"));
		assertThat(trip.chargeOf(id), is("2%"));
		int hoursOfCharge = 3;
		trip.charge(id, hoursOfCharge);
		trip.sprint(id);
		assertThat(trip.locationOf(id), is("BORDEAUX"));
		assertThat(trip.chargeOf(id), is("51%"));
	}

}
