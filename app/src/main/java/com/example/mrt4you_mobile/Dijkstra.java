package com.example.mrt4you_mobile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Set;

public class Dijkstra 
{
	public static Graph calculateShortestPathFromSource(Graph graph, Node source) 
	{
	    source.setDistance(0);

	    Set<Node> settledNodes = new HashSet<>();
	    Set<Node> unsettledNodes = new HashSet<>();

	    unsettledNodes.add(source);

	    while (unsettledNodes.size() != 0) 
	    {
	        Node currentNode = getLowestDistanceNode(unsettledNodes);
	        unsettledNodes.remove(currentNode);
	        for (Entry<Node, Integer> adjacencyPair: currentNode.getAdjacentNodes().entrySet()) 
	        {
	            Node adjacentNode = adjacencyPair.getKey();
	            Integer edgeWeight = adjacencyPair.getValue();
	            if (!settledNodes.contains(adjacentNode)) 
	            {
	                calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
	                unsettledNodes.add(adjacentNode);
	            }
	        }
	        settledNodes.add(currentNode);
	    }
	    return graph;
	}
	
	private static Node getLowestDistanceNode(Set<Node> unsettledNodes) 
	{
	    Node lowestDistanceNode = null;
	    int lowestDistance = Integer.MAX_VALUE;
	    for (Node node: unsettledNodes) {
	        int nodeDistance = node.getDistance();
	        if (nodeDistance <= lowestDistance) 
	        {
	            lowestDistance = nodeDistance;
	            lowestDistanceNode = node;
	        }
	    }
	    return lowestDistanceNode;
	}
	
	private static void calculateMinimumDistance(Node evaluationNode, Integer edgeWeight, Node sourceNode) 
	{
		Integer sourceDistance = sourceNode.getDistance();
		if (sourceDistance + edgeWeight < evaluationNode.getDistance()) 
		{
			evaluationNode.setDistance(sourceDistance + edgeWeight);
			LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
			shortestPath.add(sourceNode);
			evaluationNode.setShortestPath(shortestPath);
		}
	}
	
	public static Map<String, Integer> shortestPathAndDistanceFromSourceToDestination(String sourceName,
			String destinationName, Graph graph)
	{
		Node source = graph.findNode(sourceName);
		Node destination = graph.findNode(destinationName);

		if (source != null && destination != null)
		{
			Map<String, Integer> output = new HashMap<String, Integer>();
			String path = "";
			
			Graph optimizedGraph = calculateShortestPathFromSource(graph, source);
			
			if (destination.getDistance() >= Integer.MAX_VALUE)
				return null;
			else
			{
				List<Node> shortestPathFromSourceToDestination = optimizedGraph.getNodes().stream()
						.filter(x -> x.getName().equals(destination.getName()))
						.collect(Collectors.toList()).get(0).getShortestPath();

				for (Node node: shortestPathFromSourceToDestination)
				{
					path = path + node.getName() + " ";
				}
				path = path + destination.getName();

				output.put(path, destination.getDistance());
				return output;
				
			}
		}
		else
			return null;
		
	}
}
