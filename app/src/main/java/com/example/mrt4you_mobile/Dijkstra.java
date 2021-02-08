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
	public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
		source.setDistance(0);

		Set<Node> settledNodes = new HashSet<>();
		Set<Node> unsettledNodes = new HashSet<>();

		unsettledNodes.add(source);

		while (unsettledNodes.size() != 0) {
			Node currentNode = getLowestDistanceNode(unsettledNodes);
			unsettledNodes.remove(currentNode);
			for (Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
				Node adjacentNode = adjacencyPair.getKey();
				Integer edgeWeight = adjacencyPair.getValue();
				if (!settledNodes.contains(adjacentNode)) {
					calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
					unsettledNodes.add(adjacentNode);
				}
			}
			settledNodes.add(currentNode);
		}
		return graph;
	}

	private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
		Node lowestDistanceNode = null;
		int lowestDistance = Integer.MAX_VALUE;
		for (Node node : unsettledNodes) {
			int nodeDistance = node.getDistance();
			if (nodeDistance <= lowestDistance) {
				lowestDistance = nodeDistance;
				lowestDistanceNode = node;
			}
		}
		return lowestDistanceNode;
	}

	private static void calculateMinimumDistance(Node evaluationNode, Integer edgeWeight, Node sourceNode) {
		Integer sourceDistance = sourceNode.getDistance();
		if (sourceDistance + edgeWeight < evaluationNode.getDistance()) {
			evaluationNode.setDistance(sourceDistance + edgeWeight);
			LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
			shortestPath.add(sourceNode);
			evaluationNode.setShortestPath(shortestPath);
		}
	}

	public static Route shortestPathAndDistanceFromSourceToDestination(String sourceName,
																	   String destinationName, Graph graph) {
		Node source = graph.findNode(sourceName);
		Node destination = graph.findNode(destinationName);
		Map<String, String> finalStations = new HashMap<>();
		finalStations.put("NS1", "Jurong East");
		finalStations.put("NS28", "Marina South Pier");
		finalStations.put("EW1", "Pasir Ris");
		finalStations.put("EW33", "Tuas Link");
		finalStations.put("CC1", "Dhoby Ghaut");
		finalStations.put("CC29", "HarbourFront");
		finalStations.put("CE2", "Marina Bay");

		if (source != null && destination != null) {
			Map<String, Integer> output = new HashMap<>();
			String path = "";

			Graph optimizedGraph = calculateShortestPathFromSource(graph, source);

			if (destination.getDistance() >= Integer.MAX_VALUE)
				return null;
			else {
				List<Node> shortestPath = optimizedGraph.getNodes().stream()
						.filter(x -> x.getName().equals(destination.getName()))
						.collect(Collectors.toList()).get(0).getShortestPath();
				shortestPath.add(destination);
				Route shortestRoute = new Route(destination.getDistance());
				for (Node node: shortestPath)
				{
					path += node.getName() + " ";
				}
				shortestRoute.setPath(path);
				shortestRoute.setStart(source.getName());
				shortestRoute.setEnd(destination.getName());

				int counter = 0;

				while (counter < shortestPath.size()) {
					String line, direction;
					if (counter+1 == shortestPath.size()) {
						line = shortestPath.get(counter).getStationCode().get(0).substring(0,2);
					} else {
						line = findLineFromPath(shortestPath.get(counter), shortestPath.get(counter+1));
					}
					if (counter!=0) {
						direction = finalStations.get(findDirection(line, shortestPath.get(counter-1), shortestPath.get(counter)));
					} else {
						direction = finalStations.get(findDirection(line, shortestPath.get(counter), shortestPath.get(counter+1)));
					}

					Subroute subroute = new Subroute(line, direction);
					int noOfStations = 0;
					boolean endOfSubroute = false;
					while (!endOfSubroute) {
						subroute.getStations().add(shortestPath.get(counter));
						//if not starting point
						if (counter!=0) {
							noOfStations++;
						}

						//if end of path OR
						//if current is an interchange and next station indicates a change in line
						if ((counter+1 == shortestPath.size()) ||
								(shortestPath.get(counter).getAdjacentNodes().size() > 2
								&& !line.equals(
								shortestPath.get(counter + 1).getStationCode().get(0).substring(0, 2)))) {
							if (counter+1!=shortestPath.size()) {
								shortestRoute.getInterchanges().add(shortestPath.get(counter));
							}
							endOfSubroute = true;
							subroute.setNoOfStations(noOfStations);
							int noOfMins = findTimeForSubroute(shortestPath.get(counter).getDistance(), shortestRoute);
							subroute.setNoOfMins(noOfMins);
							shortestRoute.getSubroutes().add(subroute);
						}
						counter++;
					}
				}

				int totalTime = 0;
				for (Subroute sr : shortestRoute.getSubroutes()) {
					totalTime+=sr.getNoOfMins();
				}
				shortestRoute.setTotalTime(totalTime);
				return shortestRoute;

			}
		}

		else
			return null;
	}



	private static String findLineFromPath(Node firstStn, Node secondStn) {
		if (firstStn.getStationCode().size() == 1) {
			return firstStn.getStationCode().get(0).substring(0, 2);
		} else {
			for (String l : firstStn.getStationCode()) {
				for (String j : secondStn.getStationCode())
					if (l.substring(0, 2).equals(j.substring(0, 2))) {
						return l.substring(0, 2);
					}
			}
		}
		return null;
	}

	private static String findDirection(String line, Node prevStn, Node currentStn) {
		String prevStnCodeOnLine = "", currentStnCodeOnLine = "";
		for (String sc: prevStn.getStationCode()) {
			if (sc.substring(0, 2).equals(line)) {
				prevStnCodeOnLine = sc;
				break;
			}
		}

		for (String sc: currentStn.getStationCode()) {
			if (sc.substring(0, 2).equals(line)) {
				currentStnCodeOnLine = sc;
				break;
			}
		}

		switch(line) {
			case "NS":
				if (prevStnCodeOnLine.compareTo(currentStnCodeOnLine)<0) {
					return "NS28";
				}
				else {
					return "NS1";
				}
			case "EW":
				if (prevStnCodeOnLine.compareTo(currentStnCodeOnLine)<0) {
					return "EW33";
				}
				else {
					return "EW1";
				}
			case "CC":
				if (prevStnCodeOnLine.compareTo(currentStnCodeOnLine)<0) {
					return "CC29";
				}
				else {
					return "CC1";
				}
			case "CE":
				if (prevStnCodeOnLine.compareTo(currentStnCodeOnLine)<0) {
					return "CE2";
				}
				else {
					return "CC1";
				}

		}

		return null;

	}

	private static int findTimeForSubroute(int x, Route currentRoute) {
		if (currentRoute.getSubroutes()==null) {
			return x;
		} else {
			int result = x;
			for (int i = 0; i < currentRoute.getSubroutes().size(); i++) {
				result -= currentRoute.getSubroutes().get(i).getNoOfMins();
			}
			return result;
		}
	}
}
