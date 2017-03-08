package com.kaanburaksener.octoUML.src.view.edges;

import com.kaanburaksener.octoUML.src.model.edges.AbstractEdge;
import com.kaanburaksener.octoUML.src.util.Constants;
import com.kaanburaksener.octoUML.src.view.nodes.AbstractNodeView;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by kaanburaksener on 08/03/17.
 *
 * Visual representation of RealizationEdge class.
 */
public class RealizationEdgeView extends AbstractEdgeView {
    private AbstractEdge refEdge;
    private ArrayList<Line> arrowHeadLines = new ArrayList<>();

    public RealizationEdgeView(AbstractEdge edge, AbstractNodeView startNode, AbstractNodeView endNode) {
        super(edge, startNode, endNode);
        refEdge = edge;
        this.setStrokeWidth(super.STROKE_WIDTH);
        this.setStroke(Color.BLACK);
        setPosition();
        draw();
    }

    protected void draw() {
        AbstractEdge.Direction direction = refEdge.getDirection();
        Line sl = getStartLine();
        Line ml = getMiddleLine();
        Line el = getEndLine();

        sl.getStrokeDashArray().addAll(20d, 10d);
        ml.getStrokeDashArray().addAll(20d, 10d);
        el.getStrokeDashArray().addAll(20d, 10d);

        getChildren().clear();
        getChildren().add(sl);
        getChildren().add(ml);
        getChildren().add(el);

        super.draw();
        this.getChildren().add(super.getEndMultiplicity());
        this.getChildren().add(super.getStartMultiplicity());

        //Draw arrows.
        switch(direction) {
            case NO_DIRECTION:
                //Do nothing.
                break;
            case START_TO_END:
                this.getChildren().add(drawArrowHead(getEndLine().getEndX(), getEndLine().getEndY(), getEndLine().getStartX(), getEndLine().getStartY()));
                break;
            case END_TO_START:
                this.getChildren().add(drawArrowHead(getStartLine().getStartX(), getStartLine().getStartY(), getStartLine().getEndX(), getStartLine().getEndY()));
                break;
            case BIDIRECTIONAL:
                this.getChildren().add(drawArrowHead(getStartLine().getStartX(), getStartLine().getStartY(), getStartLine().getEndX(), getStartLine().getEndY()));
                this.getChildren().add(drawArrowHead(getEndLine().getEndX(), getEndLine().getEndY(), getEndLine().getStartX(), getEndLine().getStartY()));
                break;
        }
    }

    public void setSelected(boolean selected){
        super.setSelected(selected);
        if(selected){
            for(Line l : arrowHeadLines){
                l.setStroke(Constants.selected_color);
            }
        } else {
            for (Line l : arrowHeadLines) {
                l.setStroke(Color.BLACK);
            }
        }
    }

    /**
     * Draws an ArrowHead and returns it in a group.
     * Based on code from http://www.coderanch.com/t/340443/GUI/java/Draw-arrow-head-line
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @return Group.
     */
    private Group drawArrowHead(double startX, double startY, double endX, double endY) {
        Group group = new Group();

        double phi = Math.toRadians(40);
        int barb = 20;
        double dy = startY - endY;
        double dx = startX - endX;
        double theta = Math.atan2(dy, dx);
        double x, y, rho = theta + phi;

        double[] xs = new double[2];
        double[] ys = new double[2];

        for (int j = 0; j < 2; j++) {
            x = startX - barb * Math.cos(rho);
            y = startY - barb * Math.sin(rho);
            xs[j] = x;
            ys[j] = y;
            rho = theta - phi;
        }

        Polygon background = new Polygon();
        background.getPoints().addAll(startX, startY, xs[0], ys[0], xs[1], ys[1]);
        background.setFill(Color.WHITE);
        background.toBack();
        Line line1 = new Line(startX, startY, xs[0], ys[0]);
        Line line2 = new Line(startX, startY, xs[1], ys[1]);
        Line line3 = new Line(xs[0], ys[0], xs[1], ys[1]);
        line1.setStrokeWidth(super.STROKE_WIDTH);
        line2.setStrokeWidth(super.STROKE_WIDTH);
        line3.setStrokeWidth(super.STROKE_WIDTH);
        group.getChildren().add(background);
        group.getChildren().add(line1);
        group.getChildren().add(line2);
        group.getChildren().add(line3);
        arrowHeadLines.addAll(Arrays.asList(line1, line2, line3));
        if(super.isSelected()){
            for(Line l : arrowHeadLines){
                l.setStroke(Constants.selected_color);
            }
        }
        return group;
    }

    public void propertyChange(PropertyChangeEvent evt){
        super.propertyChange(evt);
        if(evt.getPropertyName().equals(Constants.changeNodeTranslateX) || evt.getPropertyName().equals(Constants.changeNodeTranslateY) ||
                evt.getPropertyName().equals(Constants.changeEdgeDirection)) {
            draw();
        }
    }
}