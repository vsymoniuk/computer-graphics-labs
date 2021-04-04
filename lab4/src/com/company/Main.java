package com.company;

import robot.Robot;
import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Main extends JFrame implements KeyListener {
    private double movingDelta = 0.2d;
    private double angleDelta = Math.PI / 50;

    private SimpleUniverse universe;
    private Point3d watcher = new Point3d(3d, 0d, 0d);
    private Vector3d xAxis = new Vector3d(-1d, 0d, 0d);
    private Vector3d zAxis = new Vector3d(0d, 0d, 1d);

    public Main() {
        super("Lab 4");
        setLayout(new BorderLayout());
        setSize(1200, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.addKeyListener(this);

        universe = new SimpleUniverse(canvas);
        universe.addBranchGraph(createSceneGraph());
        updateViewPosition();

        add(BorderLayout.CENTER, canvas);

        setVisible(true);
    }

    public BranchGroup createSceneGraph() {
        BranchGroup group = new BranchGroup();

        group.addChild(new Robot(1));

        Color3f lightColor = new Color3f(1, 1, 1);
        BoundingSphere lightArea = new BoundingSphere(new Point3d(0, 0, 0), 100);

        Vector3f lightDirection1 = new Vector3f(-1, 1, -1);
        DirectionalLight light1 = new DirectionalLight(lightColor, lightDirection1);
        light1.setInfluencingBounds(lightArea);
        group.addChild(light1);

        Vector3f lightDirection2 = new Vector3f(-1, 0, 0);
        DirectionalLight light2 = new DirectionalLight(lightColor, lightDirection2);
        light2.setInfluencingBounds(lightArea);
        group.addChild(light2);

        return group;
    }

    private void updateViewPosition() {
        Transform3D viewingTransform = new Transform3D();

        Point3d lookAtPosition = new Point3d(
                watcher.x + xAxis.x,
                watcher.y + xAxis.y,
                watcher.z + xAxis.z
        );

        viewingTransform.lookAt(watcher, lookAtPosition, zAxis);
        viewingTransform.invert();

        universe.getViewingPlatform().getViewPlatformTransform().setTransform(viewingTransform);
    }

    private void updateEyePosition(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W: {
                rotateViewY(-angleDelta);
                break;
            }
            case KeyEvent.VK_A: {
                rotateViewX(-angleDelta);
                break;
            }
            case KeyEvent.VK_S: {
                rotateViewY(angleDelta);
                break;
            }
            case KeyEvent.VK_D: {
                rotateViewX(angleDelta);
                break;
            }

            case KeyEvent.VK_Z: {
                moveViewBySightDirection(movingDelta);
                break;
            }
            case KeyEvent.VK_X: {
                moveViewBySightDirection(-movingDelta);
                break;
            }

            case KeyEvent.VK_LEFT: {
                moveWatcherAroundScene(-movingDelta);
                rotateViewX(-angleDelta);
                break;
            }
            case KeyEvent.VK_RIGHT: {
                moveWatcherAroundScene(movingDelta);
                rotateViewX(angleDelta);
                break;
            }
        }
        updateViewPosition();
    }

    public void moveWatcherAroundScene(double delta) {
        Vector3d n = new Vector3d();
        n.cross(xAxis, zAxis);
        n.normalize();
        n.scale(delta);
        watcher.add(n);
    }

    public void rotateViewX(double angle) {
        xAxis = VecmathHelper.rotateVector(xAxis, zAxis, angle);
    }

    public void rotateViewY(double angle) {
        Vector3d horizon = new Vector3d();
        horizon.cross(xAxis, zAxis);
        xAxis = VecmathHelper.rotateVector(xAxis, horizon, angle);
    }

    public void moveViewBySightDirection(double delta) {
        Vector3d deltaVector = new Vector3d(xAxis);
        deltaVector.scale(delta);
        watcher.add(deltaVector);
    }

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        updateEyePosition(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) { }
}