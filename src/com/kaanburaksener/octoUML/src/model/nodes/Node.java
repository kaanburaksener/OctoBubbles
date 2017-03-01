package com.kaanburaksener.octoUML.src.model.nodes;

import com.kaanburaksener.octoUML.src.model.GraphElement;
import javafx.geometry.Rectangle2D;

/**
 * Interface used by all Node-classes. Represents a UML-class.
 */
public interface Node extends GraphElement {

    String getId();

    double getX();

    double getY();

    double getWidth();

    double getHeight();

    void setWidth(double width);

    void setHeight(double height);

    Rectangle2D getBounds();

    String getTitle();

    void setTitle(String title);

    String getType();
}
