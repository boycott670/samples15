package com.nespresso.exercise.electric_trip.parsers;

import java.util.List;
import java.util.Map;

import com.nespresso.exercise.electric_trip.entities.City;

public interface TripParser
{
	List<City> parseCities (final String trip);
	Map<String, City> connectCities (final String trip, final List<? extends City> cities);
}
