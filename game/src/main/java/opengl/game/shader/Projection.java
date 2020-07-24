package opengl.game.shader;

import org.joml.Matrix4f;

public class Projection {
    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000f;

    private final Matrix4f projection;

    public Projection(float width, float height) {
        float aspectRatio = width / height;
        float yScale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float xScale = yScale / aspectRatio;
        float frustumLength = FAR_PLANE - NEAR_PLANE;

        projection = new Matrix4f();
        projection.m00(xScale);
        projection.m11(yScale);
        projection.m22(-((FAR_PLANE + NEAR_PLANE) / frustumLength));
        projection.m23(-1);
        projection.m32(-((2 * NEAR_PLANE * FAR_PLANE) / frustumLength));
        projection.m33(0);
    }

    public Matrix4f getProjection() {
        return projection;
    }
}
