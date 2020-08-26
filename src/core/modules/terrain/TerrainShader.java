package core.modules.terrain;

import core.kernel.Camera;
import core.scene.GameObject;
import core.shaders.Shader;
import core.utils.ResourceLoader;

public class TerrainShader extends Shader {
    private static TerrainShader instance;

    public static TerrainShader getInstance(){
        if (instance == null){
            instance = new TerrainShader();
        }
        return instance;
    }

    protected TerrainShader() {
        super();

        addVertexShader(ResourceLoader.loadShader("shaders/terrain/terrain_VS.glsl"));
        addTessellationControlShader(ResourceLoader.loadShader("shaders/terrain/terrain_TC.glsl"));
        addTessellationEvaluationShader(ResourceLoader.loadShader("shaders/terrain/terrain_TE.glsl"));
        addFragmentShader(ResourceLoader.loadShader("shaders/terrain/terrain_FS.glsl"));

        compileShader();

        addUniform("localMatrix");
        addUniform("worldMatrix");
        addUniform("m_ViewProjection");
    }

    public void updateUniforms(GameObject object){
        setUniform("m_ViewProjection", Camera.getInstance().getViewProjectionMatrix());
        setUniform("localMatrix", object.getLocalTransform().getWorldMatrix());
        setUniform("worldMatrix", object.getWorldTransform().getWorldMatrix());
    }
}
