package com.company;

import javax.vecmath.*;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.swing.JFrame;
import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.*;

import java.awt.*;
import java.util.Enumeration;
import java.util.Hashtable;

public class Main extends JFrame{
    private static Canvas3D canvas;
    private static SimpleUniverse universe;
    private static BranchGroup root;
    private static TransformGroup trainerTransformGroup;
    private static Scene trainerScene;

    // moves
    private static int movesCount = 7;
    private static int movesDuration = 700;
    private static int startTime = 0;

    public Main(){
        configureWindow();
        configureCanvas();
        configureUniverse();
        root = new BranchGroup();
        addImageBackground();
        addLight();
        trainerTransformGroup = getTrainerGroup();
        root.addChild(trainerTransformGroup);
        root.compile();
        universe.addBranchGraph(root);
    }

    private void configureWindow() {
        setTitle("Lab6");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void configureCanvas() {
        canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.setDoubleBufferEnable(true);
        getContentPane().add(canvas, BorderLayout.CENTER);
    }

    private void configureUniverse() {
        universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();
    }

    private void addImageBackground() {
        TextureLoader t = new TextureLoader("C:/Users/izogi/IdeaProjects/lab6/assets/bg.png", canvas);
        Background background = new Background(t.getImage());
        background.setImageScaleMode(Background.SCALE_FIT_ALL);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        background.setApplicationBounds(bounds);
        root.addChild(background);
    }

    public void addLight(){
        BoundingSphere bounds = new BoundingSphere();
        bounds.setRadius(100);
        DirectionalLight directionalLight = new DirectionalLight(new Color3f(1, 1, 1), new Vector3f(-1, -1, -1));
        directionalLight.setInfluencingBounds(bounds);
        root.addChild(directionalLight);

        AmbientLight ambientLight = new AmbientLight(new Color3f(1, 1, 1));
        ambientLight.setInfluencingBounds(new BoundingSphere());
        root.addChild(ambientLight);
    }

    public TransformGroup getTrainerGroup(){
        BoundingSphere boundingSphere = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
        ObjectFile loader = new ObjectFile(ObjectFile.RESIZE);
        try {
            trainerScene = loader.load("C:/Users/izogi/IdeaProjects/lab6/assets/trainer.obj");
        }
        catch (Exception e){
            System.out.println("File loading failed:" + e);
        }

        TransformGroup bodyTG = new TransformGroup();
        TransformGroup ballTG = new TransformGroup();
        ballTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        TransformGroup backpackTG = new TransformGroup();
        TransformGroup headTG = new TransformGroup();
        headTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        Appearance bodyAppearance = new Appearance();
        setToMyDefaultAppearance(bodyAppearance, new Color3f(.23f, .50f, .65f));
        Appearance ballAppearance = new Appearance();
        setToMyDefaultAppearance(ballAppearance, new Color3f(.72f, .13f, .23f));
        Appearance headAppearance = new Appearance();
        setToMyDefaultAppearance(headAppearance, new Color3f(.23f, .50f, .65f));
        Appearance backpackAppearance = new Appearance();
        setToMyDefaultAppearance(backpackAppearance, new Color3f(.43f, .29f, .12f));

        Hashtable trainer = trainerScene.getNamedObjects();

        // trainer body
        Shape3D body = (Shape3D) trainer.get("body");
        body.setAppearance(bodyAppearance);
        bodyTG.addChild(body.cloneTree());

        // trainer ball
        Alpha ballAlpha = new Alpha(movesCount, Alpha.INCREASING_ENABLE, startTime, 0, movesDuration,0,0,0,0,0);
        RotationInterpolator ballInterpolator = new RotationInterpolator(ballAlpha, ballTG, new Transform3D(), 0.0f, (float) Math.PI * 2);
        ballInterpolator.setSchedulingBounds(boundingSphere);

        Shape3D ball = (Shape3D) trainer.get("left_ball");
        ball.setAppearance(ballAppearance);
        ballTG.addChild(ball.cloneTree());
        ballTG.addChild(ballInterpolator);

        // trainer head
        Alpha headAlpha = new Alpha(movesCount, Alpha.INCREASING_ENABLE, startTime, 0, movesDuration,0,0,0,0,0);
        RotationInterpolator headInterpolator = new RotationInterpolator(headAlpha, headTG, new Transform3D(), 0.0f, (float) Math.PI*2);
        headInterpolator.setSchedulingBounds(boundingSphere);

        Shape3D head = (Shape3D) trainer.get("head");
        head.setAppearance(headAppearance);
        headTG.addChild(head.cloneTree());
        headTG.addChild(headInterpolator);

        // trainer backpack
        Shape3D backpack = (Shape3D) trainer.get("backpack");
        backpack.setAppearance(backpackAppearance);
        backpackTG.addChild(backpack.cloneTree());

        Transform3D startTransformation = new Transform3D();
        startTransformation.setScale(0.3);

        TransformGroup transformGroup = new TransformGroup(startTransformation);
        TransformGroup sceneGroup = new TransformGroup();

        sceneGroup.addChild(backpackTG);
        sceneGroup.addChild(bodyTG);
        sceneGroup.addChild(ballTG);
        sceneGroup.addChild(headTG);

        transformGroup.addChild(sceneGroup);
        return rotate(
                translate(transformGroup, new Vector3f(0.0f,0.0f,-0.5f)),
                new Alpha(3,3000)
        );
    }

    private TransformGroup translate(Node node, Vector3f vector){
        Transform3D transform3D = new Transform3D();
        transform3D.setTranslation(vector);
        TransformGroup transformGroup = new TransformGroup();
        transformGroup.setTransform(transform3D);
        transformGroup.addChild(node);
        return transformGroup;
    }

    private TransformGroup rotate(Node node, Alpha alpha){
        TransformGroup xformGroup = new TransformGroup();
        xformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        RotationInterpolator interpolator = new RotationInterpolator(alpha,xformGroup);
        interpolator.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0),1.0));
        xformGroup.addChild(interpolator);
        xformGroup.addChild(node);
        return xformGroup;
    }

    public static void setToMyDefaultAppearance(Appearance app, Color3f col) {
        app.setMaterial(new Material(col, col, col, col, 150.0f));
    }

    public static void main(String[] args) {
        Main window = new Main();
        window.setVisible(true);
    }

}
