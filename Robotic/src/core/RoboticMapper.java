package core;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import algorithms.Edge;
import algorithms.Point;
import algorithms.Vertex;
import core.Mapeador.Direcao;

public class RoboticMapper {
	private int[][] world;
	private Point position;
	private Direction direction;
	private ISensorWall sensorWall;
	private List<Edge> edges;
	private List<Vertex> vertexes;
	private Point lastPosition;
	private Vertex lastVertex;

	public RoboticMapper(int[][] world, Point position, Direction direction, ISensorWall sensorWall) {
		this.world = world;
		this.position = position;
		this.direction = direction;
		this.sensorWall = sensorWall;
		this.vertexes = new ArrayList<>();
		this.edges = new ArrayList<>();
	}
	
	public List<Edge> getEdges() {
		return this.edges;
	}
	
	public boolean hasWall(Direction direction) {
		return sensorWall.detect(direction);
	}
	
	public boolean isVertex() {
//		return !this.hasWall(Direction.FRONT) && !this.hasWall(Direction.RIGHT);
		return !this.hasWall(Direction.RIGHT) && !this.hasWall(Direction.BACK);
	}
	
	public void map() {
		if (!isVertex()) {
			return;
		}
		
		Vertex vertexFromCurrentPosition = new Vertex(this.generateIdFromPoint(this.position));
		Point rightPosition = this.getNextPosition(Direction.BACK);
		Vertex vertexFromRightPosition = new Vertex(this.generateIdFromPoint(rightPosition));
		
		Edge edge = new Edge(
			vertexFromCurrentPosition,
			vertexFromRightPosition,
			calculateDistance(this.position, rightPosition, Direction.BACK)
		);
		
		vertexes.add(vertexFromCurrentPosition);
		vertexes.add(vertexFromRightPosition);
		
		if (this.lastVertex != null) {
			edges.add(new Edge(
				lastVertex,
				vertexFromCurrentPosition,
				calculateDistance(this.lastPosition, this.position, direction)
			));
		}
		
		this.lastVertex = vertexFromCurrentPosition;
		this.lastPosition = this.position;
		
		edges.add(edge);
	}
	
	private int calculateDistance(Point source, Point destination, Direction direction) {
		if (direction == Direction.LEFT || direction == Direction.RIGHT) {
			return Math.abs(source.getX() - destination.getX());
		}
		
		return Math.abs(source.getY() - destination.getY());
	}
	
	private String generateIdFromPoint(Point point) {
		return String.valueOf(
			(point.getX() + 1) + (point.getY() * 7)
		);
	}
	
	public void moviment() {
		this.position = this.getNextPosition();
	}
	
	public Point getPosition() {
		return this.position;
	}
	
	public Point getNextPosition() {
		return new Point(nextX(this.direction), nextY(this.direction));
	}
	
	public Point getNextPosition(Direction direction) {
		return new Point(nextX(direction), nextY(direction));
	}
	
	private int nextX(Direction direction) {
		if (direction == Direction.RIGHT) {
			return this.position.getX() + 1;
		}
		
		if (direction == Direction.LEFT) {
			return this.position.getX() - 1;
		}
		
		return this.position.getX();
	}
	
	private int nextY(Direction direction) {
		if (direction == Direction.BACK) {
			return this.position.getY() + 1;
		}
		
		if (direction == Direction.FRONT) {
			return this.position.getY() - 1;
		}
		
		return this.position.getY();
	}
	
	public Edge getLastEdge() {
		return this.edges.get(this.edges.size() - 1);
	}
}
