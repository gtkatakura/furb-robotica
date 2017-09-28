package algorithms;

import static org.junit.Assert.*;

import org.junit.Test;

public class WavefrontDetectorTest {
//	int[][] map = new int[][] {
//		{0, 0, 0, 0, 0, 0, 0},
//		{0, 0, 0, 0, 0, 0, 0},
//		{0, 0, 0, 0, 0, 0, 0},
//		{0, 0, 0, 0, 0, 0, 0},
//		{0, 0, 0, 0, 0, 0, 0},
//		{0, 0, 0, 0, 0, 0, 0},
//		{0, 0, 0, 0, 0, 0, 0}
//	};

	@Test
	public void shouldReturnTopPointTest() {
		int[][] map = new int[][] {
			{0, 0, 0},
			{0, 1, 0},
			{0, 0, 0}
		};
		
		WavefrontDetector detector = new WavefrontDetector(map);
		Point source = new Point(map.length / 2, map.length / 2);

		Point nextPoint = detector.findFrom(source, value -> value == 0);
		
		assertEquals(nextPoint.getX(), 1);
		assertEquals(nextPoint.getY(), 0);
	}
	
	@Test
	public void shouldReturnBottomPointTest() {
		int[][] map = new int[][] {
			{0, 1, 0},
			{0, 1, 0},
			{0, 0, 0}
		};
		
		WavefrontDetector detector = new WavefrontDetector(map);
		Point source = new Point(map.length / 2, map.length / 2);

		Point nextPoint = detector.findFrom(source, value -> value == 0);
		
		assertEquals(nextPoint.getX(), 1);
		assertEquals(nextPoint.getY(), 2);
	}
	
	@Test
	public void shouldReturnLeftPointTest() {
		int[][] map = new int[][] {
			{0, 1, 0},
			{0, 1, 0},
			{0, 1, 0}
		};
		
		WavefrontDetector detector = new WavefrontDetector(map);
		Point source = new Point(map.length / 2, map.length / 2);

		Point nextPoint = detector.findFrom(source, value -> value == 0);
		
		assertEquals(nextPoint.getX(), 0);
		assertEquals(nextPoint.getY(), 1);
	}
	
	@Test
	public void shouldReturnRightPointTest() {
		int[][] map = new int[][] {
			{0, 1, 0},
			{1, 1, 0},
			{0, 1, 0}
		};
		
		WavefrontDetector detector = new WavefrontDetector(map);
		Point source = new Point(map.length / 2, map.length / 2);

		Point nextPoint = detector.findFrom(source, value -> value == 0);
		
		assertEquals(nextPoint.getX(), 2);
		assertEquals(nextPoint.getY(), 1);
	}
	
	@Test
	public void shouldReturnTopLeftPointTest() {
		int[][] map = new int[][] {
			{0, 1, 0},
			{1, 1, 1},
			{0, 1, 0}
		};
		
		WavefrontDetector detector = new WavefrontDetector(map);
		Point source = new Point(map.length / 2, map.length / 2);

		Point nextPoint = detector.findFrom(source, value -> value == 0);
		
		assertEquals(nextPoint.getX(), 0);
		assertEquals(nextPoint.getY(), 0);
	}
	
	@Test
	public void shouldReturnTopRightPointTest() {
		int[][] map = new int[][] {
			{1, 1, 0},
			{1, 1, 1},
			{0, 1, 0}
		};
		
		WavefrontDetector detector = new WavefrontDetector(map);
		Point source = new Point(map.length / 2, map.length / 2);

		Point nextPoint = detector.findFrom(source, value -> value == 0);
		
		assertEquals(nextPoint.getX(), 2);
		assertEquals(nextPoint.getY(), 0);
	}
	
	@Test
	public void shouldReturnBottomLeftPointTest() {
		int[][] map = new int[][] {
			{1, 1, 1},
			{1, 1, 1},
			{0, 1, 0}
		};
		
		WavefrontDetector detector = new WavefrontDetector(map);
		Point source = new Point(map.length / 2, map.length / 2);

		Point nextPoint = detector.findFrom(source, value -> value == 0);
		
		assertEquals(nextPoint.getX(), 0);
		assertEquals(nextPoint.getY(), 2);
	}
	
	@Test
	public void shouldReturnBottomRightPointTest() {
		int[][] map = new int[][] {
			{1, 1, 1},
			{1, 1, 1},
			{1, 1, 0}
		};
		
		WavefrontDetector detector = new WavefrontDetector(map);
		Point source = new Point(map.length / 2, map.length / 2);

		Point nextPoint = detector.findFrom(source, value -> value == 0);
		
		assertEquals(nextPoint.getX(), 2);
		assertEquals(nextPoint.getY(), 2);
	}
	
	@Test
	public void shouldReturnBottomPointWhenNotExistsTopCaseTest() {
		int[][] map = new int[][] {
			{0, 0},
			{0, 0}
		};
		
		WavefrontDetector detector = new WavefrontDetector(map);
		Point source = new Point(0, 0);

		Point nextPoint = detector.findFrom(source, value -> value == 0);
		
		assertEquals(nextPoint.getX(), 0);
		assertEquals(nextPoint.getY(), 1);
	}
}
