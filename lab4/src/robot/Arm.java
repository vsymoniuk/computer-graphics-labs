package robot;

import com.sun.j3d.utils.geometry.Cylinder;
import shapes.Frustum;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class Arm extends TransformGroup {
    public Arm(double size) {
        float delta = (float)(size / 8);

        TransformGroup group = new TransformGroup();

        TransformGroup armGroup = new TransformGroup();
        Cylinder arm = new Cylinder((float)(delta * 0.4), delta * 5);
        arm.setAppearance(getAppearance());
        armGroup.addChild(arm);
        Transform3D armTransform  = new Transform3D();
        armTransform.rotZ(Math.PI / 2);
        armGroup.setTransform(armTransform);
        group.addChild(armGroup);


        TransformGroup handGroup = new TransformGroup();
        Shape3D hand = new Frustum()
                .setHeight(3 * delta / 4)
                .setInnerRadius(3 * delta / 8)
                .setOuterRadius(9 * delta / 16)
                .compile(getAppearance());
        handGroup.addChild(hand);
        Transform3D handTransform = new Transform3D();
        handTransform.rotY(Math.PI / 2);
        handTransform.setTranslation(new Vector3f(23 * (float) delta / 8, 0, 0));
        handGroup.setTransform(handTransform);
        group.addChild(handGroup);

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
