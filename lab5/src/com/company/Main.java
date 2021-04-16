package com.company;

import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import javax.media.j3d.Background;
import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.image.TextureLoader;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import javax.swing.JFrame;

public class Main extends JFrame {
    private static Canvas3D canvas;
    private static SimpleUniverse universe;
    private static BranchGroup root;

    private static TransformGroup bear;
    private static int TextureFlags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;

    public Main() throws IOException {
        configureWindow();
        configureCanvas();
        configureUniverse();
        root = new BranchGroup();
        addImageBackground();
        addDirectionalLightToUniverse();
        addAmbientLightToUniverse();
        bear = getBearGroup();
        root.addChild(bear);
        root.compile();
        universe.addBranchGraph(root);
    }


    private void configureWindow() {
        setTitle("Lab5");
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
        TextureLoader t = new TextureLoader("C:/Users/izogi/IdeaProjects/lab5/src/assets/WOODS.jpg", canvas);
        Background background = new Background(t.getImage());
        background.setImageScaleMode(Background.SCALE_FIT_ALL);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        background.setApplicationBounds(bounds);
        root.addChild(background);
    }

    private void addDirectionalLightToUniverse() {
        BoundingSphere bounds = new BoundingSphere();
        bounds.setRadius(100);
        DirectionalLight light = new DirectionalLight(new Color3f(1, 1, 1), new Vector3f(-1, -1, -1));
        light.setInfluencingBounds(bounds);
        root.addChild(light);
    }

    private void addAmbientLightToUniverse() {
        AmbientLight light = new AmbientLight(new Color3f(1, 1, 1));
        light.setInfluencingBounds(new BoundingSphere());
        root.addChild(light);
    }

    private void addAppearance(Shape3D shape, String path) {
        TextureLoader loader = new TextureLoader(path, "RGP", new Container());
        Texture texture = loader.getTexture();
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));

        TextureAttributes attrs = new TextureAttributes();
        attrs.setTextureMode(TextureAttributes.MODULATE);

        Appearance appearance = new Appearance();
        appearance.setTexture(texture);
        appearance.setTextureAttributes(attrs);

        shape.setAppearance(appearance);
    }

    private TransformGroup getBearGroup() throws IOException {
        Shape3D shape = getModelShape3D("bear", "C:/Users/izogi/IdeaProjects/lab5/src/assets/MODEL.obj");

//        addAppearance(shape, "C:/Users/izogi/IdeaProjects/lab5/src/assets/TEXTURE.jpg");
        Transform3D transform3D = new Transform3D();
        transform3D.setScale(new Vector3d(0.4, 0.4, 0.4));
        Transform3D rotationY = new Transform3D();
        rotationY.rotY(Math.PI / 4);
        transform3D.mul(rotationY);
        TransformGroup group = getModelGroup(shape);
        group.setTransform(transform3D);
        return group;
    }

    private TransformGroup getModelGroup(Shape3D shape) {
        TransformGroup group = new TransformGroup();
        group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        group.addChild(shape);
        return group;
    }

    private Shape3D getModelShape3D(String name, String path) throws IOException {
        Scene scene = getSceneFromFile(path);
        Map<String, Shape3D> map = scene.getNamedObjects();
        Shape3D shape = map.get(name);
        scene.getSceneGroup().removeChild(shape);
        return shape;
    }

    private Scene getSceneFromFile(String path) throws IOException {
        ObjectFile file = new ObjectFile(ObjectFile.RESIZE);
        file.setFlags(ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY);
        return file.load(new FileReader(path));
    }

    public static void main(String[] args) {
        try {
            Main window = new Main();
            Animation bearMovement = new Animation(bear);
            canvas.addKeyListener(bearMovement);
            window.setVisible(true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

