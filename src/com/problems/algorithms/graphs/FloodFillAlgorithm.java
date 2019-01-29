/**
 * @author VIVEK
 * @Date 01/28/2019
 * 
 * 
 */
package com.problems.algorithms.graphs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class FloodFillAlgorithm {

	private int[][] image = null;

	private List<List<Integer>> pixelBoundaries = null;

	private static final Integer INCREASE_ROW_COUNT = 1;

	private static final Integer DECREASE_ROW_COUNT = -1;

	private static final Integer INCREASE_COLUMN_COUNT = 1;

	private static final Integer DECREASE_COLUMN_COUNT = -1;

	private static final Integer STATIC_POSITION = 0;

	/*
	 * Read an actual image into a 2d array.
	 * 
	 */
	private void loadPixelBoundaries() {
		pixelBoundaries = new LinkedList<>();
		pixelBoundaries.add(Arrays.asList(STATIC_POSITION, DECREASE_COLUMN_COUNT));// left
		pixelBoundaries.add(Arrays.asList(DECREASE_ROW_COUNT, DECREASE_COLUMN_COUNT));// left upper diagonal
		pixelBoundaries.add(Arrays.asList(INCREASE_ROW_COUNT, DECREASE_COLUMN_COUNT));// left lower diagonal
		pixelBoundaries.add(Arrays.asList(STATIC_POSITION, INCREASE_COLUMN_COUNT));// right
		pixelBoundaries.add(Arrays.asList(DECREASE_ROW_COUNT, INCREASE_COLUMN_COUNT));// right upper diagonal
		pixelBoundaries.add(Arrays.asList(INCREASE_ROW_COUNT, INCREASE_COLUMN_COUNT));// right lower diagonal
		pixelBoundaries.add(Arrays.asList(DECREASE_ROW_COUNT, STATIC_POSITION));// up
		pixelBoundaries.add(Arrays.asList(INCREASE_ROW_COUNT, STATIC_POSITION));// down
	}

	private void loadImage() {
		image = new int[][] { { 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 }, { 1, 1, 2, 1, 1 }, { 1, 2, 2, 2, 1 },
				{ 2, 2, 2, 2, 2 } };
	}

	private void displayImage() {
		int count = image.length;
		System.out.println("Count:" + count);
		for (int[] row : image) {

			for (int col : row) {
				System.out.print(col + " ");
			}
			System.out.println();
		}
	}

	private Stack<List<Integer>> identifyNeighbors(int rowPixel, int colPixel) {
		// Identify the neighboring coordinates, not the values

		int rowLen = image.length - 1;
		int colLen = image[0].length - 1;
		Stack<List<Integer>> neighbors = new Stack<>();
		for (List<Integer> pixelBoundary : pixelBoundaries) {
			if ((rowPixel + pixelBoundary.get(0)) > rowLen || (rowPixel + pixelBoundary.get(0)) <= 0) {
				continue;
			} else if ((colPixel + pixelBoundary.get(1)) > colLen || (colPixel + pixelBoundary.get(1)) <= 0) {
				continue;
			} else {
				neighbors.push(Arrays.asList(rowPixel + pixelBoundary.get(0), colPixel + pixelBoundary.get(1)));
			}
		}

		System.out.println("Neighbouring coordinates");
		for (List<Integer> neighbor : neighbors) {
			neighbor.forEach(coordinate -> System.out.print(coordinate + " "));
			System.out.print("Value: " + image[neighbor.get(0)][neighbor.get(1)]);
			System.out.println();
		}
		return neighbors;

	}
	
	private void performFillColor(int rowPixel, int colPixel, int currentColor, int colorToBeChanged) {
		
		if(image.length == 0) {
			return;
		}
		Stack<List<Integer>> neighbors = identifyNeighbors(rowPixel, colPixel);
		
		if(!neighbors.isEmpty() && neighbors.peek() != null) {
			List<Integer> neighbor = neighbors.pop();
			if(image[neighbor.get(0)][neighbor.get(1)] == currentColor) {
				image[neighbor.get(0)][neighbor.get(1)] = colorToBeChanged;
				performFillColor(neighbor.get(0),neighbor.get(1),currentColor,colorToBeChanged);
			}else {
				return;
			}
		}
		
	}

	public static void main(String[] args) {
		FloodFillAlgorithm ffA = new FloodFillAlgorithm();
		ffA.loadPixelBoundaries();
		ffA.loadImage();
		ffA.displayImage();
		ffA.performFillColor(2, 2, 2, 3);
		ffA.displayImage();
	}

}
