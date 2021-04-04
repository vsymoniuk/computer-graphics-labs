package robot;

import com.sun.j3d.utils.geometry.Cylinder;
import shapes.HalfSphere;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class Leg extends TransformGroup {
    public Leg(double size) {
        float delta = (float)(size / 8);

        TransformGroup group = new TransformGroup();

        TransformGroup legGroup = new TransformGroup();
        Cylinder leg = new Cylinder((float)(delta * 0.4), (float)(delta * 8));
        leg.setAppearance(getAppearance());
        legGroup.addChild(leg);
        Transform3D legTransform  = new Transform3D();
        legTransform.rotX(Math.PI / 2);
        legGroup.setTransform(legTransform);
        group.addChild(legGroup);

        TransformGroup footGroup = new TransformGroup();
        Shape3D foot = new HalfSphere()
                .setRadius(delta)
                .compile(getAppearance());
        footGroup.addChild(foot);
        Transform3D footTransform = new Transform3D();
        footTransform.setTranslation(new Vector3d(0, 0, -delta * 4));
        footGroup.setTransform(footTransform);
        group.addChild(footGroup);

        addChild(group);
    }

    public Appearance getAppearance() {
        Appearance appearance = new Appearance();
        appearance.setMaterial(
                new Material(
                        new Color3f(0.4453f, 0.4453f, 0.4453f),
                        new Color3f(0f, 0f, 0f),
                        new Color3f(0.4453f, 0.4453f, 0.4453f),
                        new Color3f(1f, 1f, 1f),
                        70f
                )
        );
        return appearance;
    }
}
