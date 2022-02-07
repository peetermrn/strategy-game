package com.gamefromscratch.path;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class Graph<T extends GraphNode> {
    private final Set<T> nodes;

    private final Map<String, Set<String>> connections;

    public Graph(Set<T> nodes, Map<String, Set<String>> connections) {
        this.nodes = nodes;
        this.connections = connections;
    }

    public T getNode(String id) throws IllegalArgumentException {
        return nodes.stream()
                .filter(node -> node.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No node found with ID: " + id));
    }

    public Set<T> getConnections(T node) throws IllegalArgumentException {
        return connections.get(node.getId()).stream()
                .map(this::getNode)
                .collect(Collectors.toSet());
    }
}
