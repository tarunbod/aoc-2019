import java.io.*;
import java.util.*;

class Node {
	String name;
	List<String> neighbors = new ArrayList<>();
	String parent = null;

	@Override
	public String toString() {
		return "{" + parent + "; " + neighbors + "}";
	}
}

class Parentifier {
	String name;
	String parent;
}

public class Main {
	public static void main(String[] args) throws Exception {
		Map<String, Node> graph = new HashMap<>();
		Scanner sc = new Scanner(new File(args[0]));
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] parts = line.split("\\)");
			if (graph.containsKey(parts[0])) {
				graph.get(parts[0]).neighbors.add(parts[1]);
			} else {
				Node n = new Node();
				n.name = parts[0];
				n.neighbors.add(parts[1]);
				graph.put(parts[0], n);
			}
		}
		sc.close();
		Map<String, String> leaves = new HashMap<>();
		for (String key : graph.keySet()) {
			Node n = graph.get(key);
			for (String neighbor : n.neighbors) {
				Node neighborNode = graph.get(neighbor);
				if (neighborNode == null) {
					leaves.put(neighbor, key);
				} else {
					neighborNode.parent = key;
				}
			}
		}
		for (String leaf : leaves.keySet()) {
			Node n = new Node();
			n.name = leaf;
			n.parent = leaves.get(leaf);
			graph.put(leaf, n);
		}
		// System.out.println(graph);

		int n = numberOfOrbits(graph, "COM");
		System.out.println(n + " total direct + indirect orbits");

		String parent = graph.get("YOU").parent;
		String sanParent = graph.get("SAN").parent;
		// shortestPath(graph, parent, sanParent);
		n = shortestPath(graph, parent, sanParent);
		System.out.println((n - 1) + " orbital transfers");
	}

	public static int numberOfOrbits(Map<String, Node> graph, String root) {
		List<String> stack = new ArrayList<>();
		List<String> visited = new ArrayList<>();
		stack.add(root);

		List<String> currentPath = new ArrayList<>();
		List<String> parentsStack = new ArrayList<>();
		parentsStack.add(root);
		List<List<String>> paths = new ArrayList<>();

		while (!stack.isEmpty()) {
			String v = stack.remove(stack.size() - 1);
			if (!visited.contains(v)) {
				visited.add(v);
				currentPath.add(v);
				List<String> neighbors = graph.get(v).neighbors;
				if (neighbors.isEmpty()) {
					paths.add(new ArrayList<>(currentPath));
					currentPath = currentPath.subList(
						0,
						currentPath.indexOf(
							parentsStack.remove(parentsStack.size() - 1)
						) + 1
					);
					continue;
				}
				if (neighbors.size() > 1) {
					parentsStack.add(v);
				}
				for (String w : neighbors) {
					stack.add(w);
				}
			}
		}

		// System.out.println(paths);

		int sum = 0;
		for (int i = 0; i < paths.size(); i++) {
			List<String> path = paths.get(i);
			int n = path.size();
			int adding = n * (n - 1) / 2;
			int remaining = -1;
			for (int j = 0; j < i; j++) {
				List<String> copy = new ArrayList<>(path);
				List<String> prevPath = paths.get(j);
				copy.removeAll(prevPath);
				// System.out.println(copy);
				if (remaining == -1 || path.size() - copy.size() > remaining) {
					remaining = path.size() - copy.size();
				}
			}
			// System.out.println(remaining);
			int subtracting = remaining < 0 ? 0 : remaining * (remaining - 1) / 2;
			// System.out.println("Adding " + adding + ", Subtracting " + subtracting);
			sum += adding - subtracting;
			// System.out.println("Running sum = " + sum);
		}
		return sum;
	}

	public static int shortestPath(Map<String, Node> graph, String start, String dest) {
		Map<String, String> parents = new HashMap<>();
		List<String> q = new ArrayList<>();
		List<String> visited = new ArrayList<>();
		visited.add(start);
		q.add(start);
		while (!q.isEmpty()) {
			String v = q.remove(0);
			if (v.equals(dest)) {
				int count = 0;
				String current = v;
				while (current != null) {
					System.out.print(current + " ");
					current = parents.get(current);
					count++;
				}
				System.out.println();
				return count;
			}
			List<String> neighbors = new ArrayList<>();
			neighbors.addAll(graph.get(v).neighbors);
			if (graph.get(v).parent != null) {
				neighbors.add(graph.get(v).parent);
			}
			for (String w : neighbors) {
				if (!visited.contains(w)) {
					visited.add(w);
					parents.put(w, v);
					q.add(w);
				}
			}
		}
		return -1;
	}
}
