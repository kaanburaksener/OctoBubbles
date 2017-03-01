package com.kaanburaksener.octoUML.src.util.commands;

import com.kaanburaksener.octoUML.src.model.edges.AbstractEdge;

/**
 * Command for changing the direction of an edge.
 */
public class DirectionChangeEdgeCommand implements Command {

    AbstractEdge aEdge;
    AbstractEdge.Direction aOldDirection;
    AbstractEdge.Direction aNewDirection;

    public DirectionChangeEdgeCommand(AbstractEdge pEdge, AbstractEdge.Direction pOldDirection,
                                      AbstractEdge.Direction pNewDirection){
        aEdge = pEdge;
        aOldDirection = pOldDirection;
        aNewDirection = pNewDirection;
    }

    public void undo(){
        aEdge.setDirection(aOldDirection);
    }

    public void execute(){
        aEdge.setDirection(aNewDirection);
    }
}
