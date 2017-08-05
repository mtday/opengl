package opengl.game.model;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.asList;
import static opengl.game.model.AttributeType.NORMAL_ATTRIBUTE;
import static opengl.game.model.AttributeType.TEXTURE_ATTRIBUTE;
import static opengl.game.model.AttributeType.VERTEX_ATTRIBUTE;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

public class Obj {
    private static final Logger LOGGER = LoggerFactory.getLogger(Obj.class);

    @Nonnull
    private final IndexBuffer indexBuffer;
    @Nonnull
    private final Map<AttributeType, AttributeBuffer> attributeBuffers;

    public Obj(@Nonnull final ModelType modelType) {
        final List<Vector3f> vertexVectors = new ArrayList<>();
        final List<Vector2f> textureVectors = new ArrayList<>();
        final List<Vector3f> normalVectors = new ArrayList<>();
        final List<Vector3i> faceVectors = new ArrayList<>();

        try (final InputStream inputStream = Obj.class.getClassLoader().getResourceAsStream(modelType.getResource());
             final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, UTF_8);
             final BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String trimmed = line.trim();
                if (trimmed.startsWith("v ")) {
                    vertexVectors.add(parse3f(trimmed));
                } else if (trimmed.startsWith("vt ")) {
                    textureVectors.add(parse2f(trimmed));
                } else if (trimmed.startsWith("vn ")) {
                    normalVectors.add(parse3f(trimmed));
                } else if (trimmed.startsWith("f ")) {
                    faceVectors.addAll(parseTriple3i(trimmed));
                } else if (trimmed.startsWith("o ")) {
                    // Not sure what this is.
                } else if (trimmed.startsWith("s ")) {
                    // Not sure what this is.
                } else if (trimmed.startsWith("# ")) {
                    // Ignore comments.
                } else if (trimmed.length() == 0) {
                    // Ignore blank lines.
                } else {
                    LOGGER.warn("Unrecognized object file line: {}", trimmed);
                }
            }
        } catch (final IOException ioException) {
            throw new RuntimeException("Failed to parse object file resource: " + modelType.getResource(), ioException);
        }

        final float[] vertices = new float[vertexVectors.size() * 3];
        final float[] textures = new float[vertexVectors.size() * 2];
        final float[] normals = new float[vertexVectors.size() * 3];
        final List<Integer> indices = new ArrayList<>();

        for (final Vector3i face : faceVectors) {
            indices.add(face.x);
            final Vector3f vertex = vertexVectors.get(face.x);
            vertices[face.x * 3] = vertex.x;
            vertices[face.x * 3 + 1] = vertex.y;
            vertices[face.x * 3 + 2] = vertex.z;
            final Vector2f texture = textureVectors.get(face.y);
            textures[face.x * 2] = texture.x;
            textures[face.x * 2 + 1] = 1 - texture.y;
            final Vector3f normal = normalVectors.get(face.z);
            normals[face.x * 3] = normal.x;
            normals[face.x * 3 + 1] = normal.y;
            normals[face.x * 3 + 2] = normal.z;
        }

        indexBuffer = new IndexBuffer(indices.stream().mapToInt(i -> i).toArray());
        attributeBuffers = new HashMap<>(3);
        attributeBuffers.put(VERTEX_ATTRIBUTE, new AttributeBuffer(VERTEX_ATTRIBUTE, vertices));
        attributeBuffers.put(TEXTURE_ATTRIBUTE, new AttributeBuffer(TEXTURE_ATTRIBUTE, textures));
        attributeBuffers.put(NORMAL_ATTRIBUTE, new AttributeBuffer(NORMAL_ATTRIBUTE, normals));
    }

    @Nonnull
    public IndexBuffer getIndexBuffer() {
        return indexBuffer;
    }

    @Nonnull
    public Map<AttributeType, AttributeBuffer> getAttributeBuffers() {
        return attributeBuffers;
    }

    @Nonnull
    private Vector3f parse3f(@Nonnull final String line) {
        final String[] parts = line.split(" ");
        return new Vector3f(parseFloat(parts[1]), parseFloat(parts[2]), parseFloat(parts[3]));
    }

    @Nonnull
    private Vector2f parse2f(@Nonnull final String line) {
        final String[] parts = line.split(" ");
        return new Vector2f(parseFloat(parts[1]), parseFloat(parts[2]));
    }

    @Nonnull
    private List<Vector3i> parseTriple3i(@Nonnull final String line) {
        final String[] parts = line.split(" ");
        return asList(parse3i(parts[1]), parse3i(parts[2]), parse3i(parts[3]));
    }

    @Nonnull
    private Vector3i parse3i(@Nonnull final String part) {
        final String[] parts = part.split("/");
        return new Vector3i(parseInt(parts[0]) - 1, parseInt(parts[1]) - 1, parseInt(parts[2]) - 1);
    }
}
