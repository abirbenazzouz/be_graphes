package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {
    ShortestPathData data;
    
	public AStarAlgorithm(ShortestPathData data) {
        super(data);
        this.data=data;

    }
	// On crée des labelstar au lieux de créer des label, et on reprend le code de Dijkstra dont AStar hérite
	protected Label newLabel(Node pNoeud, double pCout, Node pPere, Arc pArc){      
        return new LabelStar(pNoeud, pCout , pPere, pArc, data);
    }
	
	
}