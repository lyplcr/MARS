/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.simobjects;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.effect.shapes.EmitterBoxShape;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import mars.Helper.Helper;
import mars.PickHint;

/**
 *
 * @author Thomas Tosik <tosik at iti.uni-luebeck.de>
 */
@XmlRootElement(name="OilBurst")
@XmlAccessorType(XmlAccessType.NONE)
public class OilBurst extends SimObject{

    /**
     *
     */
    public OilBurst() {
    }

    /**
     *
     * @param simob
     */
    public OilBurst(SimObject simob) {
        super(simob);
    }
    
    /**
     *
     * @return
     */
    @Override
    public SimObject copy() {
        OilBurst oilBurst = new OilBurst(this);
        oilBurst.initAfterJAXB();
        return oilBurst;
    }
    
    /**
     *
     */
    @Override
    public void init(){
        initParticles();
        Helper.setNodePickUserData(debugNode,PickHint.NoPick);
        simObNode.attachChild(renderNode);
        simObNode.attachChild(debugNode);
        simObNode.setLocalScale(getScale());
        simObNode.setUserData("simob_name", getName());
        simObNode.setLocalTranslation(getPosition());
        simObNode.rotate(getRotation().x, getRotation().y, getRotation().z);
        simObNode.setName(getName());
        simObNode.updateModelBound();
        simObNode.updateGeometricState();
    }
    
    private void initParticles(){
        //assetManager.registerLocator("Assets/Textures/Water", FileLocator.class);
        ParticleEmitter fire = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 300);
        Material mat_red = new Material(assetManager,"Common/MatDefs/Misc/Particle.j3md");
        mat_red.setTexture("Texture", assetManager.loadTexture("Effects/Smoke/Smoke.png"));
        mat_red.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        fire.setMaterial(mat_red);
        fire.setImagesX(15); 
        fire.setImagesY(1); // 2x2 texture animation
        fire.setEndColor(new ColorRGBA(0f, 0f, 0f, 0.0f));
        fire.setStartColor(new ColorRGBA(0.0f, 0.0f, 0.0f, 1.0f));
        fire.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0.35f, 0));
        fire.getParticleInfluencer().setVelocityVariation(0.25f);
        fire.setStartSize(0.12f);
        fire.setEndSize(0.2f);
        fire.setGravity(0, 0, 0);
        fire.setLowLife(4f);
        fire.setHighLife(5f);
        fire.setParticlesPerSec(25.0f);
        //fire.setShape(new EmitterPointShape(Vector3f.ZERO));
        //fire.setGravity(0f, -0.05f, 0f);
        //fire.setFacingVelocity(true);
        fire.setRandomAngle(false);
        fire.setRotateSpeed(0.0f);
        renderNode.attachChild(fire);
        
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        material.setTexture("Texture", assetManager.loadTexture("Effects/flame-alpha.png"));
        material.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        //material.getAdditionalRenderState().setDepthWrite(true);
        //material.getAdditionalRenderState().setDepthTest(true);
        material.getAdditionalRenderState().setAlphaTest(true);
        //material.getAdditionalRenderState().setAlphaFallOff(1.0f);
        material.setFloat("Softness", 3f); // 
        
        /*ParticleEmitter smoke = new ParticleEmitter("Smoke", ParticleMesh.Type.Triangle, 100);
        smoke.setMaterial(material);
        smoke.setShape(new EmitterSphereShape(Vector3f.ZERO, 1f));
        //smoke.setShape(new EmitterBoxShape(new Vector3f(0f,0f,0f),new Vector3f(10f,1.4f,10f)));
        smoke.setImagesX(1);
        smoke.setImagesY(1); // 2x2 texture animation
        smoke.setStartColor(new ColorRGBA(0.0f, 0.0f, 0.0f,1f)); // dark gray
        smoke.setEndColor(new ColorRGBA(0.0f, 0.0f, 0.0f, 0.3f)); // gray      
        smoke.setStartSize(0.1f);
        smoke.setEndSize(2.5f);
        smoke.setGravity(0f, -0.0001f, 0f);
        smoke.setLowLife(100f);
        smoke.setHighLife(100f);    
        smoke.setEnabled(false);
        //smoke.setLocalTranslation(0, 0.1f, 0); 
        smoke.emitAllParticles();
        smoke.setQueueBucket(RenderQueue.Bucket.Translucent);
        renderNode.attachChild(smoke);*/
        
        /*ParticleEmitter smoke = new ParticleEmitter("Smoke", ParticleMesh.Type.Triangle, 30);
        smoke.setMaterial(material);
        smoke.setShape(new EmitterSphereShape(Vector3f.ZERO, 5));
        smoke.setImagesX(1);
        smoke.setImagesY(1); // 2x2 texture animation
        smoke.setStartColor(new ColorRGBA(0.1f, 0.1f, 0.1f,1f)); // dark gray
        smoke.setEndColor(new ColorRGBA(0.5f, 0.5f, 0.5f, 0.3f)); // gray      
        smoke.setStartSize(3f);
        smoke.setEndSize(5f);
        smoke.setGravity(0, -0.001f, 0);
        smoke.setLowLife(100f);
        smoke.setHighLife(100f);
        smoke.setLocalTranslation(0, 0.1f, 0);        
        smoke.emitAllParticles();*/
        
        ParticleEmitter smoke = new ParticleEmitter("Smoke", ParticleMesh.Type.Triangle, 800);
        smoke.setMaterial(material);
        //smoke.setShape(new EmitterSphereShape(Vector3f.ZERO, 0.5f));
        smoke.setShape(new EmitterBoxShape(new Vector3f(-80f,-0.5f,-80f),new Vector3f(80f,0.1f,80f)));
        //smoke.setShape(new EmitterPointShape(Vector3f.ZERO));
        smoke.setImagesX(2);
        smoke.setImagesY(2); // 2x2 texture animation
        smoke.setStartColor(new ColorRGBA(0.0f, 0.0f, 0.0f,0.5f)); // dark gray//0.125f
        smoke.setEndColor(new ColorRGBA(0.0f, 0.0f, 0.0f, 0.0f)); // gray      
        smoke.setStartSize(0.7f);//0.2f
        smoke.setEndSize(1.5f);
        smoke.setGravity(0f, 0.05f, 0f);
        smoke.setLowLife(6f);
        smoke.setHighLife(8f);    
        
        smoke.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0.2f, 0.0f));
        smoke.getParticleInfluencer().setVelocityVariation(0.05f);
        //smoke.setInWorldSpace(true);
        smoke.setRandomAngle(true);
        smoke.setRotateSpeed(0.0f);
        smoke.setLocalTranslation(0, -0.6f, 0);   
        //smoke.emitAllParticles();
        smoke.setQueueBucket(RenderQueue.Bucket.Transparent);
        
        /*smoke.setGravity(0f, 0.0f, 0f);
        smoke.getParticleInfluencer().setVelocityVariation(0.0f);
        smoke.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0.0f, 0.0f));*/
        
        //smoke.emitAllParticles();
        smoke.setEnabled(true);
        
        renderNode.attachChild(smoke);
    }
    
   /**
     * 
     * @param target
     * @param hashmapname
     */
    @Override
    public void updateState(String target, String hashmapname){
        if(target.equals("position") && hashmapname.equals("")){
            if(simObNode != null ){
                simObNode.setLocalTranslation(getPosition());
            }
        }else if(target.equals("rotation") && hashmapname.equals("")){
            if(simObNode != null ){
                Matrix3f m_rot = new Matrix3f();
                Quaternion q_rot = new Quaternion();
                q_rot.fromAngles(getRotation().x, getRotation().y, getRotation().z);
                m_rot.set(q_rot);
                simObNode.setLocalRotation(m_rot);
            }
        }else if(target.equals("scale") && hashmapname.equals("")){
            simObNode.setLocalScale(getScale());
        }    
    }
}
