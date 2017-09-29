package core;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import algorithms.Edge;
import algorithms.Point;
import algorithms.Vertex;

public class RoboticMapperTest {
	static int[][] world = new int[][] {
		{  1,  2,  3,  4,  5,  6,  7 },
		{  8,  9, 10, 11, 12, 13, 14 },
		{ 15, 16, 17, 18, 19, 20, 21 },
		{ 22, 23, 24, 25, 26, 27, 28 },
		{ 29, 30, 31, 32, 33, 34, 35 },
		{ 36, 37, 38, 39, 40, 41, 42 },
		{ 43, 44, 45, 46, 47, 48, 49 }
	};
	
	static List<Vertex> vertexes = new ArrayList<>();
	
	static List<Edge> walls = new ArrayList<Edge>() {{
		add(createEdge(4, 11, 1));
		add(createEdge(5, 12, 1));
		
//		add(createEdge(8, 9, 1));
//		add(createEdge(15, 16, 1));
//		add(createEdge(22, 23, 1));
//		add(createEdge(29, 30, 1));
//		add(createEdge(22, 23, 1));
	}};
	
//	static List<Edge> edges = new ArrayList<Edge>() {{
//		add(createEdge(1, 8, 1));
//		add(createEdge(1, 2, 1));
//		add(createEdge(2, 9, 1));
//		add(createEdge(2, 3, 1));
//		add(createEdge(3, 10, 1));
//		add(createEdge(3, 6, 3));
//		add(createEdge(6, 13, 3));
//		add(createEdge(6, 7, 1));
//		add(createEdge(7, 42, 5));
//		add(createEdge(42, 41, 1));
//		add(createEdge(42, 49, 1));
//		add(createEdge(49, 43, 6));
//		add(createEdge(43, 36, 1));
//		add(createEdge(36, 37, 1));
//	}};
	
	static Edge createEdge(int sourceId, int destinationId, int weight) {
		return new Edge(createVertex(sourceId), createVertex(destinationId), weight);
	}
	
	static Edge findEdge(String id) {
		return walls.stream()
			.filter(edge -> edge.getId().equals(id))
			.findFirst()
			.orElse(null);
	}
	
	static Vertex createVertex(String id) {
		Vertex newVertex = vertexes.stream()
			.filter(vertex -> vertex.getId() == id)
			.findAny()
			.orElse(null);
		
		if (newVertex == null) {
			newVertex = new Vertex(id);
			vertexes.add(newVertex);
		}
		
		return newVertex;
	}
	
	static Vertex createVertex(int id) {
		return createVertex(new Integer(id).toString());
	}
	
	static class SensorWallMock implements ISensorWall {
		private RoboticMapper robotic;

		public void setRobotic(RoboticMapper robotic) {
			this.robotic = robotic;
		}
		
		public boolean detect(Direction direction) {
			Edge edge = findEdge(
				"Edge_" + generateIdFromPoint(robotic.getPosition()) +
				"_to_" + generateIdFromPoint(robotic.getNextPosition(direction))
			);
			
			return edge != null;
		}
		
		private int generateIdFromPoint(Point point) {
			return (point.getX() + 1) + (point.getY() * 7);
		}
	}

	@Test
	public void test() {
		SensorWallMock sensorWall = new SensorWallMock();
		List<Edge> edges = new ArrayList<>();

		RoboticMapper robotic = new RoboticMapper(
			world,
			new Point(0, 0),
			Direction.RIGHT,
			sensorWall
		);
		
		sensorWall.setRobotic(robotic);
		
		// Action 1
		assertFalse(robotic.hasWall(Direction.FRONT));
		assertFalse(robotic.hasWall(Direction.RIGHT));
		assertTrue(robotic.isVertex());
		
		robotic.map();
		
		edges.add(new Edge(createVertex(1), createVertex(8), 1));
		assertEquals(edges, robotic.getEdges());
		
		robotic.moviment();
		assertEquals(robotic.getPosition(), new Point(1, 0));
		
		
		// Action 2
		assertFalse(robotic.hasWall(Direction.FRONT));
		assertFalse(robotic.hasWall(Direction.RIGHT));
		assertTrue(robotic.isVertex());
		
		robotic.map();
		edges.add(new Edge(createVertex(1), createVertex(2), 1));
		edges.add(new Edge(createVertex(2), createVertex(9), 1));
		assertEquals(edges, robotic.getEdges());
		
		robotic.moviment();
		assertEquals(robotic.getPosition(), new Point(2, 0));
		
		
		// Action 3
		assertFalse(robotic.hasWall(Direction.FRONT));
		assertFalse(robotic.hasWall(Direction.RIGHT));
		assertTrue(robotic.isVertex());
		
		robotic.map();
		edges.add(new Edge(createVertex(2), createVertex(3), 1));
		edges.add(new Edge(createVertex(3), createVertex(10), 1));
		assertEquals(edges, robotic.getEdges());
		
		robotic.moviment();
		assertEquals(robotic.getPosition(), new Point(3, 0));

		// Action 4
		assertFalse(robotic.isVertex());
		
		robotic.map();
		assertEquals(edges, robotic.getEdges());
		
		robotic.moviment();
		assertEquals(robotic.getPosition(), new Point(4, 0));

		// Action 5
		assertFalse(robotic.isVertex());
		
		robotic.map();
		assertEquals(edges, robotic.getEdges());
		
		robotic.moviment();
		assertEquals(robotic.getPosition(), new Point(5, 0));

		// Action 6
		assertTrue(robotic.isVertex());
		
		robotic.map();
		edges.add(new Edge(createVertex(3), createVertex(6), 3));
		edges.add(new Edge(createVertex(6), createVertex(13), 1));
		assertEquals(edges, robotic.getEdges());
		
		robotic.moviment();
		assertEquals(robotic.getPosition(), new Point(6, 0));
		

//		add(createEdge(3, 6, 3));
//		add(createEdge(6, 13, 3));
//		add(createEdge(6, 7, 1));
//		add(createEdge(7, 42, 5));
//		add(createEdge(42, 41, 1));
//		add(createEdge(42, 49, 1));
//		add(createEdge(49, 43, 6));
//		add(createEdge(43, 36, 1));
//		add(createEdge(36, 37, 1));
	}
}
