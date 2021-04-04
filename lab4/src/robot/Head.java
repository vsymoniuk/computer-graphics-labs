package robot;

import shapes.Cube;
import shapes.Frustum;
import shapes.HalfSphere;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class Head extends TransformGroup {

    public Head(double size) {
        TransformGroup group = new TransformGroup();

        Shape3D cylinder = new Frustum()
                .setHeight(size / 2)
                .setInnerRadius(size / 4)
                .setOuterRadius(size / 4)
                .compile(getAppearance());
        group.addChild(cylinder);

        TransformGroup halfSphere1Group = new TransformGroup();
        Shape3D halfSphere1 = new HalfSphere()
                .setRadius(size / 4)
                .compile(getAppearance());
        halfSphere1Group.addChild(halfSphere1);
        Transform3D halfSphere1Transform = new Transform3D();
        halfSphere1Transform.setTranslation(new Vector3d(0, 0, size / 4));
        halfSphere1Group.setTransform(halfSphere1Transform);
        group.addChild(halfSphere1Group);

        Eye leftEye = new Eye(3 * (float) size / 16);
        Transform3D leftEyeTransform = new Transform3D();
        leftEyeTransform.setTranslation(new Vector3f(
                9 * (float) size / 50,
                (float) size / 8,
                5 * (float) size / 32
        ));
        leftEye.setTransform(leftEyeTransform);
        group.addChild(leftEye);

        Eye rightEye = new Eye(3 * (float) size / 16);
        Transform3D rightEyeTransform = new Transform3D();
        rightEyeTransform.setTranslation(new Vector3f(
                9 * (float) size / 50,
                (float) -size / 8,
                5 * (float) size / 32
        ));
        rightEye.setTransform(rightEyeTransform);
        group.addChild(rightEye);

        // -------------------MOUTH--------------------------
        float offset = (float)(size / 4);

        TransformGroup topMouthGroup = new TransformGroup();
        Shape3D topMouth = new Cube()
                .setSize(size / 4)
                .compile(getAppearance());
        topMouthGroup.addChild(topMouth);
        Transform3D topMouthTransform = new Transform3D();
        topMouthTransform.setTranslation(new Vector3f( 3 * (float) size / 16, 0, (float) size / 4 - offset));
        topMouthTransform.setScale(new Vector3d(0.75, 1, 0.02));
        topMouthGroup.setTransform(topMouthTransform);
        group.addChild(topMouthGroup);

        TransformGroup bottomMouthGroup = new TransformGroup();
        Shape3D bottomMouth = new Cube()
                .setSize(size / 4)
                .compile(getAppearance());
        bottomMouthGroup.addChild(bottomMouth);
        Transform3D bottomMouthTransform = new Transform3D();
        bottomMouthTransform.setTranslation(new Vector3f( 3 * (float) size / 16, 0, (float) size / 16 - offset));
        bottomMouthTransform.setScale(new Vector3d(0.75, 1, 0.02));
        bottomMouthGroup.setTransform(bottomMouthTransform);
        group.addChild(bottomMouthGroup);

        TransformGroup leftMouthGroup = new TransformGroup();
        Shape3D leftMouth = new Cube()
                .setSize(size / 4)
                .compile(getAppearance());
        leftMouthGroup.addChild(leftMouth);
        Transform3D leftMouthTransform = new Transform3D();
        leftMouthTransform.setTranslation(new Vector3f(
                3 * (float) size / 16,
                (float) size / 4 - 0.02f * (float) size / 4,
                5 * (float) size / 32 - offset));
        leftMouthTransform.setScale(new Vector3d(0.75, 0.02, 0.39));
        leftMouthGroup.setTransform(leftMouthTransform);
        group.addChild(leftMouthGroup);

        TransformGroup rightMouthGroup = new TransformGroup();
        Shape3D rightMouth = new Cube()
                .setSize(size / 4)
                .compile(getAppearance());
        rightMouthGroup.addChild(rightMouth);
        Transform3D rightMouthTransform = new Transform3D();
        rightMouthTransform.setTranslation(new Vector3f(
                3 * (float) size / 16,
                (float) -size / 4 + 0.02f * (float) size / 4,
                5 * (float) size / 32 - offset));
        rightMouthTransform.setScale(new Vector3d(0.75, 0.02, 0.39));
        rightMouthGroup.setTransform(rightMouthTransform);
        group.addChild(rightMouthGroup);

        TransformGroup backMouthGroup = new TransformGroup();
        Shape3D backMouth = new Cube()
                .setSize(size / 4)
                .compile(leftEye.getPupilAppearance());
        backMouthGroup.addChild(backMouth);
        Transform3D backMouthTransform = new Transform3D();
        backMouthTransform.setTranslation(new Vector3f(
                (float) size / 4,
                0,
                5 * (float) size / 32 - offset));
        backMouthTransform.setScale(new Vector3d(0.02, 0.98, 0.39));
        backMouthGroup.setTransform(backMouthTransform);
        group.addChild(backMouthGroup);
        // -------------------MOUTH--------------------------

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
