package com.company;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;

public class Main extends Application{
    Color windowBg = Color.rgb(85,107,47);
    Color clockOutline = Color.rgb(255,215,0);
    Color clockArrows = Color.rgb(0,0,0);
    Color clockBg = Color.rgb(245,245,245);

    int vw = 700;
    int vh = 400;

    int cx = 350;
    int cy = 200;

    int rOuter = 150;
    int rInner = 140;
    int rHourMark = 15;

    public static void main (String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        Group root = new Group();
        Scene scene = new Scene (root, vw, vh);
        scene.setFill(windowBg);

        DrawClocksBase(root);
        DrawHourMarks(root);
        DrawMinuteArrow(root);
        DrawHourArrow(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void DrawClocksBase(Group root) {
        Circle outline = new Circle(cx, cy, rOuter);
        outline.setFill(clockOutline);

        Circle base = new Circle(cx, cy, rInner);
        base.setFill(clockBg);

        root.getChildren().add(outline);
        root.getChildren().add(base);
    };

    public void DrawHourMarks(Group root) {
        int ro = rInner - rHourMark - 10;

        Circle three = new Circle(cx + ro, cy, rHourMark);
        Circle six = new Circle(cx, cy - ro, rHourMark);
        Circle nine = new Circle(cx, cy + ro, rHourMark);
        Circle twelve = new Circle(cx - ro, cy, rHourMark);

        three.setFill(clockOutline);
        six.setFill(clockOutline);
        nine.setFill(clockOutline);
        twelve.setFill(clockOutline);

        root.getChildren().add(three);
        root.getChildren().add(six);
        root.getChildren().add(nine);
        root.getChildren().add(twelve);
    };

    public void DrawHourArrow(Group root) {
        Polygon arrow = new Polygon(
                cx, cy + 10,
                cx + 10, cy,
                cx, cy - rInner + 10,
                cx - 10, cy
        );

        arrow.setFill(clockArrows);
        root.getChildren().add(arrow);
    };

    public void DrawMinuteArrow(Group root) {
        Polygon outerArrow = new Polygon(
                cx - 20, cy,
                cx, cy - 15,
                cx + rInner - 40, cy,
                cx, cy + 15
        );
        Polygon innerArrow = new Polygon(
                cx - 15, cy,
                cx, cy - 10,
                cx + rInner - 50, cy,
                cx, cy + 10
        );

        outerArrow.setFill(clockArrows);
        innerArrow.setFill(clockBg);
        root.getChildren().add(outerArrow);
        root.getChildren().add(innerArrow);
    };
}