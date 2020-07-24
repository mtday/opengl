package opengl.game.model;

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

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;
import static opengl.game.model.AttributeType.*;

public class ObjLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjLoader.class);

    public static Model load(ModelType modelType) {
        List<Vector3f> vertexVectors = new ArrayList<>();
        List<Vector2f> textureVectors = new ArrayList<>();
        List<Vector3f> normalVectors = new ArrayList<>();
        List<Vector3i> faceVectors = new ArrayList<>();

        try (InputStream inputStream = ObjLoader.class.getClassLoader().getResourceAsStream(modelType.getResource())) {
            if (inputStream == null) {
                throw new IOException("Model type " + modelType + " resource not found: " + modelType.getResource());
            }
            try (InputStreamReader inputStreamReader = new InputStreamReader(requireNonNull(inputStream), UTF_8);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String trimmed = line.trim();
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
            }
        } catch (IOException ioException) {
            throw new RuntimeException("Failed to parse object file resource: " + modelType.getResource(), ioException);
        }

        float[] vertices = new float[vertexVectors.size() * 3];
        float[] textures = new float[vertexVectors.size() * 2];
        float[] normals = new float[vertexVectors.size() * 3];
        List<Integer> indices = new ArrayList<>();

        for (Vector3i face : faceVectors) {
            indices.add(face.x);
            Vector3f vertex = vertexVectors.get(face.x);
            vertices[face.x * 3] = vertex.x;
            vertices[face.x * 3 + 1] = vertex.y;
            vertices[face.x * 3 + 2] = vertex.z;
            Vector2f texture = textureVectors.get(face.y);
            textures[face.x * 2] = texture.x;
            textures[face.x * 2 + 1] = 1 - texture.y;
            Vector3f normal = normalVectors.get(face.z);
            normals[face.x * 3] = normal.x;
            normals[face.x * 3 + 1] = normal.y;
            normals[face.x * 3 + 2] = normal.z;
        }

        IndexBuffer indexBuffer = new IndexBuffer(indices.stream().mapToInt(i -> i).toArray());
        Map<AttributeType, AttributeBuffer> attributeBuffers = new HashMap<>(3);
        attributeBuffers.put(VERTEX_ATTRIBUTE, new AttributeBuffer(VERTEX_ATTRIBUTE, vertices));
        attributeBuffers.put(TEXTURE_ATTRIBUTE, new AttributeBuffer(TEXTURE_ATTRIBUTE, textures));
        attributeBuffers.put(NORMAL_ATTRIBUTE, new AttributeBuffer(NORMAL_ATTRIBUTE, normals));
        return new Model(indexBuffer, attributeBuffers);
    }

    private static Vector3f parse3f(String line) {
        String[] parts = line.split(" ");
        return new Vector3f(parseFloat(parts[1]), parseFloat(parts[2]), parseFloat(parts[3]));
    }

    private static Vector2f parse2f(String line) {
        String[] parts = line.split(" ");
        return new Vector2f(parseFloat(parts[1]), parseFloat(parts[2]));
    }

    private static List<Vector3i> parseTriple3i(String line) {
        String[] parts = line.split(" ");
        return asList(parse3i(parts[1]), parse3i(parts[2]), parse3i(parts[3]));
    }

    private static Vector3i parse3i(String part) {
        String[] parts = part.split("/");
        return new Vector3i(parseInt(parts[0]) - 1, parseInt(parts[1]) - 1, parseInt(parts[2]) - 1);
    }
}
