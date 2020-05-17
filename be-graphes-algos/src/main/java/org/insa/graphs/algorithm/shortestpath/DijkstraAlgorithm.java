package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Graph graph= data.getGraph();
        boolean fini = false;

        // Création d'une liste de Labels
        Label[] tabLabels= new Label[graph.size()];
        
        // Création d'un tas de labels
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        
        // Ajout du sommet d'origine et de ses attributs
        Label origine = new Label(data.getOrigin(), 0, null, null);
        tabLabels[origine.getSommet().getId()]=origine;
        // Le cout du point d'origine à lui même est de 0
        tas.insert(origine);
        
        
        
        while(!tas.isEmpty() && !fini) {
        	
        	// On trouve le noeud qui a le cout le plus faible du tas, on le retire et on le garde en mémoire dans la variable labelX
        	Label labelX=tas.deleteMin();

        	if (labelX.isMarque()) {
        		continue;
        	}
        	// Quand on sort un noeud du tas, on le marque, et on prévient les observateurs
        	labelX.setMarque();
        	notifyNodeMarked(labelX.getSommet());
        	
        	if (labelX.getSommet() == data.getDestination()) {
        		fini = true;
        		break;
        	}
        	
        	for (Arc arc : labelX.getSommet().getSuccessors()) {
        		
        		// On vérifie qu'on peut bien prendre cet arc
        		if (!data.isAllowed(arc)) {
                     continue;
                }
        		// On récupère le label dans le tableau
        		Label successeurLabel = tabLabels[arc.getDestination().getId()];
        		// Si la case correspondant à ce noeud est vide
        		if (successeurLabel == null) {
        			// On prévient les obervateurs qu'on a atteint un nouveau noeud
        			notifyNodeReached(arc.getDestination());
        			// On lui crée un label 
        			successeurLabel= new Label(arc.getDestination(),tabLabels[arc.getOrigin().getId()].getCout()+ arc.getMinimumTravelTime(), arc.getOrigin(), arc);
        			tabLabels[arc.getDestination().getId()]= successeurLabel;
        			tas.insert(successeurLabel);
        		}
        		else {
        			if (successeurLabel.getCout() > tabLabels[arc.getOrigin().getId()].getCout()+ arc.getMinimumTravelTime()) {
        				tabLabels[arc.getDestination().getId()]=new Label(arc.getDestination(),tabLabels[arc.getOrigin().getId()].getCout()+ arc.getMinimumTravelTime(), arc.getOrigin(), arc);
        			// Si le label est déjà dans le tas on met à jour sa position, sinon on l'y ajoute
        				tas.remove(successeurLabel);
        				tas.insert(tabLabels[arc.getDestination().getId()]);
        			}
        		}
        	}
        }
        // On genère la solution
        
        // Création d'une liste d'arcs
        
        ArrayList<Arc> listeArcsSolution = new ArrayList<Arc>();
        //Arc [] tabArcsSolution = new Arc [graph.size()];
        
        Label labelX = tabLabels[data.getDestination().getId()];
        
        if (labelX.getPere()==null) {
        	solution= new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {
        	 // The destination has been found, notify the observers.
        	notifyDestinationReached(data.getDestination());
        	
        	// On remplit la liste des arcs solution
        	while (labelX.getArc() != null) {
        		listeArcsSolution.add(0,labelX.getArc());
        		labelX= tabLabels[labelX.getArc().getOrigin().getId()];
        	}
        	
        	// On crée le Path solution
        	Path pathSolution = new Path(graph, listeArcsSolution);
        	
        	// On crée la solution
        	solution= new ShortestPathSolution(data, Status.OPTIMAL, pathSolution);
        }
        
        
        
        
        return solution;
    }

}
