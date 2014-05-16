/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mars;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.post.SceneProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.texture.FrameBuffer;

/**
 * We want to see some WireFrames.
 * @author Thomas Tosik
 */
public class WireProcessor implements SceneProcessor {

    RenderManager renderManager;
    Material wireMaterial;

    /**
     * 
     * @param manager
     * @param color
     */
    public WireProcessor(AssetManager manager, ColorRGBA color) {
        wireMaterial = new Material(manager, "/Common/MatDefs/Misc/Unshaded.j3md");
        wireMaterial.setColor("Color", color);
    }

    /**
     *
     * @param rm
     * @param vp
     */
    public void initialize(RenderManager rm, ViewPort vp) {
        renderManager = rm;
    }

    /**
     *
     * @param vp
     * @param w
     * @param h
     */
    public void reshape(ViewPort vp, int w, int h) {
    }

    /**
     *
     * @return
     */
    public boolean isInitialized() {
        return renderManager != null;
    }

    /**
     *
     * @param tpf
     */
    public void preFrame(float tpf) {
    }

    /**
     *
     * @param rq
     */
    public void postQueue(RenderQueue rq) {
        renderManager.setForcedMaterial(wireMaterial);
    }

    /**
     *
     * @param out
     */
    public void postFrame(FrameBuffer out) {
        renderManager.setForcedMaterial(null);
    }

    /**
     *
     */
    public void cleanup() {
        renderManager.setForcedMaterial(null);
    }
}
