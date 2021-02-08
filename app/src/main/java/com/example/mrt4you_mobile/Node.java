package com.example.mrt4you_mobile;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Node 
{
	private static final int INFINITY = 2000000000;
    private String name;
    private List<String> stationCode = new ArrayList<>();
    private List<Node> shortestPath = new LinkedList<>();
    private Integer distance = INFINITY;
    Map<Node, Integer> adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, int distance) 
    {
        adjacentNodes.put(destination, distance);
    }
 
    public Node(String name)
    {
        this.name = name;
    }

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public List<String> getStationCode() {return stationCode;}

	public void setStationCode(List<String> stationCode) {this.stationCode = stationCode;}

	public List<Node> getShortestPath() 
	{
		return shortestPath;
	}

	public void setShortestPath(List<Node> shortestPath) 
	{
		this.shortestPath = shortestPath;
	}

	public Integer getDistance() 
	{
		return distance;
	}

	public void setDistance(Integer distance) 
	{
		this.distance = distance;
	}

	public Map<Node, Integer> getAdjacentNodes() 
	{
		return adjacentNodes;
	}

	public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) 
	{
		this.adjacentNodes = adjacentNodes;
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		// If the object is compared with itself then return true
		if (obj == this) {
			return true;
		}

        /* Check if o is an instance of Node or not
          "null instanceof [type]" also returns false */
		if (!(obj instanceof Node)) {
			return false;
		}

		// downcast obj to Booking so that we can compare booking class fields
		Node n = (Node) obj;

		// Compare the data members and return accordingly, nodes are uniquely identified by
		// their name
		return this.getName().equals(n.getName());
	}

	public boolean hasStationCodeStartingWith(String charSequence)
	{
		boolean output = false;
		for (String string: stationCode)
		{
			if (string.toLowerCase().startsWith(charSequence.toLowerCase()))
			{
				output = true;
				break;
			}
		}
		return output;
	}

	public boolean isBeforeStationAndOnSameLine(Node node)
	{
		String line = null;
		boolean comparisonDone = false;
		for (String s1: this.getStationCode())
		{
			for (String s2: node.getStationCode())
			{
				if (s1.substring(0, 2).equals(s2.substring(0,2)))
				{
					line = s1.substring(0, 2);
					comparisonDone = true;
					break;
				}
			}
			if (comparisonDone)
				break;
		}
		if (line != null)
		{
			String finalLine = line;
			return Integer.parseInt(this.getStationCode().stream()
					.filter(x -> x.startsWith(finalLine)).map(x -> x.substring(2))
					.collect(Collectors.toList()).get(0)) <
					Integer.parseInt(node.getStationCode().stream()
							.filter(x -> x.startsWith(finalLine)).map(x -> x.substring(2))
							.collect(Collectors.toList()).get(0));
		}
		else
		{
			return false;
		}
	}
}
