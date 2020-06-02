package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertEquals;

import java.io.*;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.io.*;
import org.junit.Before;
import org.junit.Test;

public class ShortestPathTest {

	protected ShortestPathSolution solutionBellman[] = new ShortestPathSolution[4];
	protected ShortestPathSolution solutionDijkstra[] = new ShortestPathSolution[4];
	protected ShortestPathSolution solutionAStar[] = new ShortestPathSolution[4];
	
	@Before
	public void Construction() throws Exception{
		//final String belgique ="/Users/abirbenazzouz/Documents/Travail/3 MIC/Maps_graphes/belgium.mapgr";
		final String hauteGaronne="/Users/abirbenazzouz/Documents/Travail/Maps_graphes/haute-garonne.mapgr";
		//final String insa="/Users/abirbenazzouz/Documents/Travail/3 MIC/Maps_graphes/insa.mapgr";
		
		final GraphReader readerHauteGaronne = new BinaryGraphReader(new DataInputStream
				(new BufferedInputStream(new FileInputStream(hauteGaronne))));
		
		/*final GraphReader readerBelgique = new BinaryGraphReader(new DataInputStream
				(new BufferedInputStream(new FileInputStream(belgique))));*/
			
		 /*final GraphReader readerInsa = new BinaryGraphReader(new DataInputStream
				(new BufferedInputStream(new FileInputStream(insa))));*/
		 
		 Graph graphHauteGaronne = readerHauteGaronne.read();
		 //Graph graphBelgique = readerBelgique.read();
		 //Graph graphInsa = readerInsa.read();
		
		 
		 final ArcInspector allRoads = ArcInspectorFactory.getAllFilters().get(0);
		 final ArcInspector carAndLength = ArcInspectorFactory.getAllFilters().get(1);
		 final ArcInspector carsAndTime = ArcInspectorFactory.getAllFilters().get(2);
		 final ArcInspector pedestrian = ArcInspectorFactory.getAllFilters().get(4);
		 
		 ShortestPathData data[] = new ShortestPathData[4];
		 
		 // D'un point à lui-même
		 data[0] = new ShortestPathData(graphHauteGaronne, graphHauteGaronne.get(63104), graphHauteGaronne.get(63104), allRoads);
		 
		 
		 // Bikini insa en voiture (temps)
		 data[1] = new ShortestPathData(graphHauteGaronne, graphHauteGaronne.get(10991), graphHauteGaronne.get(63104), carsAndTime);
		 
		 // Bikini Insa en voiture (temps)
		 data[2] = new ShortestPathData(graphHauteGaronne, graphHauteGaronne.get(10991), graphHauteGaronne.get(63104), carAndLength);
		 
		 // Bikini insa à pieds
		 data[3] = new ShortestPathData(graphHauteGaronne, graphHauteGaronne.get(10991), graphHauteGaronne.get(63104), pedestrian);
		 
		
		 BellmanFordAlgorithm bellman[]= new BellmanFordAlgorithm[4];
		 DijkstraAlgorithm dijkstra[]= new DijkstraAlgorithm[4];
		 AStarAlgorithm astar[]= new AStarAlgorithm[4];
		 
		 // On remplit les tableaux
		 for(int i=0; i<4;i++) {
	    	  bellman[i] = new BellmanFordAlgorithm(data[i]);
	    	  dijkstra[i] = new DijkstraAlgorithm(data[i]);
	    	  astar[i] = new AStarAlgorithm(data[i]);
	      }
		 
		 for(int i=0; i<4;i++) {
	    	  solutionBellman[i] = bellman[i].run();
	    	  solutionDijkstra[i] = dijkstra[i].run();
	    	  solutionAStar[i] = astar[i].run();
	      }
		 
		 
		 
		 
		 
		 
		
	}
	
	// On compare les solutions au résultat de l'algorithme de Bellman Ford, qu'on prend comme référence
	
	//Dijkstra :
	@Test
	 public void testDijkstra() throws IOException  {
		 assertEquals(solutionBellman[0].getPath(),solutionDijkstra[0].getPath());     
		 }
		
	@Test
	public void testDijkstra1() throws IOException  {
		assertEquals(solutionBellman[1].getPath(),solutionDijkstra[1].getPath());  
		 }
		
	@Test
	public void testDijkstra2() throws IOException  {
		assertEquals(solutionBellman[2].getPath(),solutionDijkstra[2].getPath());
		}
		
	@Test
	public void testDijkstra3() throws IOException  {
		assertEquals(solutionBellman[3].getPath(),solutionDijkstra[3].getPath());
		}
	
	
	// A* :
	@Test
	 public void testAstar() throws IOException  {
		 assertEquals(solutionBellman[0].getPath(),solutionAStar[0].getPath());     
		 }
		
	@Test
	public void testAstar1() throws IOException  {
		assertEquals(solutionBellman[1].getPath(),solutionAStar[1].getPath());  
		 }
		
	@Test
	public void testAstar2() throws IOException  {
		assertEquals(solutionBellman[2].getPath(),solutionAStar[2].getPath());
		}
		
	@Test
	public void testAstar3() throws IOException  {
		assertEquals(solutionBellman[3].getPath(),solutionAStar[3].getPath());
		}
	
	
}
