package com.college.ainav.utils;

import java.util.*;

public class AStarAlgorithm {
    public static class Node {
        public String id;
        public String name;
        public double x, y;
        public double gScore = Double.MAX_VALUE;
        public double fScore = Double.MAX_VALUE;
        public Node parent = null;

        public Node(String id, String name, double x, double y) {
            this.id = id;
            this.name = name;
            this.x = x;
            this.y = y;
        }
    }

    public static class Edge {
        public String startId, endId;
        public double weight;

        public Edge(String startId, String endId, double weight) {
            this.startId = startId;
            this.endId = endId;
            this.weight = weight;
        }
    }

    public static List<Node> findShortestPath(Map<String, Node> allNodes, List<Edge> allEdges, Node start, Node target) {
        if (start == null || target == null) return new ArrayList<>();

        for (Node node : allNodes.values()) {
            node.gScore = Double.MAX_VALUE;
            node.fScore = Double.MAX_VALUE;
            node.parent = null;
        }

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fScore));
        Set<String> closedSet = new HashSet<>();

        start.gScore = 0;
        start.fScore = Math.sqrt(Math.pow(start.x - target.x, 2) + Math.pow(start.y - target.y, 2));
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            if (current.id.equals(target.id)) {
                List<Node> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = current.parent;
                }
                Collections.reverse(path);
                return path;
            }

            closedSet.add(current.id);

            for (Edge edge : allEdges) {
                if (edge.startId.equals(current.id)) {
                    Node neighbor = allNodes.get(edge.endId);
                    if (neighbor == null || closedSet.contains(neighbor.id)) continue;

                    double tentativeGScore = current.gScore + edge.weight;
                    if (tentativeGScore < neighbor.gScore) {
                        neighbor.parent = current;
                        neighbor.gScore = tentativeGScore;
                        neighbor.fScore = neighbor.gScore + Math.sqrt(Math.pow(neighbor.x - target.x, 2) + Math.pow(neighbor.y - target.y, 2));

                        if (!openSet.contains(neighbor)) {
                            openSet.add(neighbor);
                        }
                    }
                }
            }
        }
        return new ArrayList<>();
    }
}
