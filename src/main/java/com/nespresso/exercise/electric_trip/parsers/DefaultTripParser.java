package com.nespresso.exercise.electric_trip.parsers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.nespresso.exercise.electric_trip.entities.ChargingCity;
import com.nespresso.exercise.electric_trip.entities.City;

public class DefaultTripParser implements TripParser
{

	@Override
	public List<City> parseCities(String trip)
	{
		return Arrays.stream(trip.split("-\\d+-"))
			.map(city ->
			{
				final String[] cityTokens = city.split(":");
				
				if (cityTokens.length == 1)
				{
					return new City(cityTokens[0]);
				}
				
				return new ChargingCity(cityTokens[0], Integer.valueOf(cityTokens[1]));
			})
			.collect(Collectors.toList());
	}

	@Override
	public Map<String, City> connectCities(String trip, List<? extends City> cities)
	{
		final Matcher matcher = Pattern.compile("-(\\d+)-").matcher(trip);
		
		for (int cityIndex = 0 ; cityIndex < cities.size() - 1 ; cityIndex ++)
		{
			matcher.find();
			
			cities.get(cityIndex).setNextCity(cities.get(cityIndex + 1));
			cities.get(cityIndex).setDistanceInKilometersToNextCity(Integer.valueOf(matcher.group(1)));
		}
		
		return cities.stream().collect(Collectors.toMap(City::getName, Function.identity()));
	}	
	
}
