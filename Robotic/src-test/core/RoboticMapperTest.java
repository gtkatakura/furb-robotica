package core;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import algorithms.*;

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
		add(createEdge(4, 11));
		add(createEdge(5, 12));

		add(createEdge(13, 14));
		add(createEdge(20, 21));
		add(createEdge(27, 28));
		add(createEdge(34, 35));

		add(createEdge(37, 44));
		add(createEdge(38, 45));
		add(createEdge(39, 46));
		add(createEdge(40, 47));
		add(createEdge(41, 48));

		add(createEdge(8, 9));
		add(createEdge(15, 16));
		add(createEdge(22, 23));
		add(createEdge(29, 30));
		
		add(createEdge(10, 11));
		add(createEdge(17, 18));
		add(createEdge(24, 25));
		add(createEdge(31, 32));
		
		add(createEdge(32, 39));
		add(createEdge(33, 40));
	}};
	
	static Edge createEdge(int sourceId, int destinationId) {
		return new Edge(createVertex(sourceId), createVertex(destinationId), 1);
	}
	
	static Edge findEdge(Edge edge2) {
		Edge invertEdge = new Edge(
			edge2.getDestination(),
			edge2.getSource(),
			edge2.getWeight()
		);
		
		return walls.stream()
			.filter(edge -> edge.equals(edge2) || edge.equals(invertEdge))
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
			Direction nextDirection = this.robotic.getDirection();
			
			if (direction == Direction.RIGHT) {
				nextDirection = robotic.nextDirection();
			} else if (direction == Direction.LEFT) {
				nextDirection = robotic.nextDirectionLeft();
			}
			
			Edge edge = new Edge(
				createVertex(generateIdFromPoint(robotic.getPosition())),
				createVertex(generateIdFromPoint(robotic.getNextPosition(nextDirection))),
				1
			);
			
			return findEdge(edge) != null;
		}
		
		private int generateIdFromPoint(Point point) {
			return (point.getX() + 1) + (point.getY() * 7);
		}
	}
	
	public class RoboticMock implements IRobotic {
		@Override
		public void moviment() {
		}

		@Override
		public void rotate(Direction direction) {
		}

	}


	@Test
	public void test2() {
		SensorWallMock sensorWall = new SensorWallMock();
		List<Edge> edges = new ArrayList<>();
		
		for (int i = 0; i < world.length; i++) {
			for (int j = 0; j < world.length; j++) {
				world[i][j] = 0;
			}
		}

		RoboticMapper robotic = new RoboticMapper(
			world,
			new Point(0, 0),
			Direction.RIGHT,
			sensorWall,
			new RoboticMock()
		);
		
		sensorWall.setRobotic(robotic);
		
		Edge[][] edgesExpecteds = new Edge[][] {
			{ createEdge(1, 2), createEdge(1, 8) },
			{ createEdge(2, 3), createEdge(2, 9) },
			{ createEdge(3, 4), createEdge(3, 10) },
			{ createEdge(4, 5) },
			{ createEdge(5, 6) },
			{ createEdge(6, 7), createEdge(6, 13) },
			{ createEdge(7, 14) },
			{ createEdge(14, 21) },
			{ createEdge(21, 28) },
			{ createEdge(28, 35) },
			{ createEdge(35, 42) },
			{ createEdge(42, 49), createEdge(42, 41) },
			{ createEdge(49, 48) },
			{ createEdge(48, 47) },
			{ createEdge(47, 46) },
			{ createEdge(46, 45) },
			{ createEdge(45, 44) },
			{ createEdge(44, 43) },
			{ createEdge(43, 36) },
			{ createEdge(36, 29), createEdge(36, 37) },
			{ createEdge(29, 22) },
			{ createEdge(22, 15) },
			{ createEdge(15, 8) },
			{ },
			{ createEdge(9, 16), createEdge(9, 10) },
			{ createEdge(16, 23), createEdge(16, 17) },
			{ createEdge(23, 30), createEdge(23, 24) },
			{ createEdge(30, 37), createEdge(30, 31) },
			{ createEdge(37, 38) },
			{ createEdge(38, 39), createEdge(38, 31) },
			{ createEdge(39, 40) },
			{ createEdge(40, 41) },
			{ createEdge(41, 34) },
			{ createEdge(34, 27), createEdge(34, 33) },
			{ createEdge(27, 20), createEdge(27, 26) },
			{ createEdge(20, 13), createEdge(20, 19) },
			{ createEdge(13, 12) },
			{ createEdge(12, 11), createEdge(12, 19) },
			{ createEdge(11, 18) },
			{ createEdge(18, 25), createEdge(18, 19) },
			{ createEdge(25, 32), createEdge(25, 26) },
			{ createEdge(32, 33) },
			{ createEdge(33, 26) },
			{ createEdge(26, 19) },
			{ },
			{ createEdge(10, 17) },
			{ createEdge(17, 24) },
			{ createEdge(24, 31) },
		};
		
		Point[] pointsExpecteds = new Point[] {
			new Point(1, 0),
			new Point(2, 0),
			new Point(3, 0),
			new Point(4, 0),
			new Point(5, 0),
			new Point(6, 0),
			new Point(6, 1),
			new Point(6, 2),
			new Point(6, 3),
			new Point(6, 4),
			new Point(6, 5),
			new Point(6, 6),
			new Point(5, 6),
			new Point(4, 6),
			new Point(3, 6),
			new Point(2, 6),
			new Point(1, 6),
			new Point(0, 6),
			new Point(0, 5),
			new Point(0, 4),
			new Point(0, 3),
			new Point(0, 2),
			new Point(0, 1),
			new Point(1, 1),
			new Point(1, 2),
			new Point(1, 3),
			new Point(1, 4),
			new Point(1, 5),
			new Point(2, 5),
			new Point(3, 5),
			new Point(4, 5),
			new Point(5, 5),
			new Point(5, 4),
			new Point(5, 3),
			new Point(5, 2),
			new Point(5, 1),
			new Point(4, 1),
			new Point(3, 1),
			new Point(3, 2),
			new Point(3, 3),
			new Point(3, 4),
			new Point(4, 4),
			new Point(4, 3),
			new Point(4, 2),
			new Point(2, 1),
			new Point(2, 2),
			new Point(2, 3),
			new Point(2, 4),
		};

		for (int i = 0; i < edgesExpecteds.length; i++) {
			Edge[] edgesExpected = edgesExpecteds[i];

			robotic.discover();
			
			for (Edge edgeExpected : edgesExpected) {
				edges.add(edgeExpected);
			}
			
			assertEquals(edges, robotic.getEdges());
			assertEquals(pointsExpecteds[i], robotic.getPosition());
		}
	}
	
	public static <T> List<T> copyList(List<T> source) {
	    List<T> dest = new ArrayList<T>();
	    for (T item : source) { dest.add(item); }
	    return dest;
	}
}
