/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.debug.BulletDebugAppState;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.RawInputListener;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.event.JoyAxisEvent;
import com.jme3.input.event.JoyButtonEvent;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.input.event.TouchEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import mars.Helper.Helper;
import mars.Initializer;
import mars.KeyConfig;
import mars.MARS_Main;
import mars.MARS_Settings;
import mars.PhysicalEnvironment;
import mars.PickHint;
import mars.auv.AUV;
import mars.auv.AUV_Manager;
import mars.auv.BasicAUV;
import mars.auv.example.Hanse;
import mars.auv.example.Monsun2;
import mars.simobjects.SimObject;
import mars.simobjects.SimObjectManager;
import mars.xml.XML_JAXB_ConfigReaderWriter;
import javax.swing.TransferHandler;
import mars.Collider;
import mars.MyDebugAppStateFilter;
import mars.core.CentralLookup;
import mars.core.MARSLogTopComponent;
import mars.core.MARSMapTopComponent;
import mars.core.MARSTopComponent;
import mars.core.MARSTreeTopComponent;
import mars.filter.FishEyeFilter;
import mars.recorder.RecordControl;
import mars.recorder.RecordManager;
import mars.xml.ConfigManager;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.util.Lookup;

/**
 *
 * @author Thomas Tosik
 */
public class SimState extends AbstractAppState implements PhysicsTickListener,AppStateExtension{

    private Node rootNode = new Node("SimState Root Node");
    private AssetManager assetManager;
    private InputManager inputManager;
    private AUV_Manager auvManager;
    private RecordManager recordManager;
    private SimObjectManager simobManager;
    private BulletAppState bulletAppState;
    private MARS_Main mars;
    
    private boolean initial_ready = false;

    //needed for graphs
    private MARSTreeTopComponent TreeTopComp;
    private MARSTopComponent MARSTopComp;
    private MARSMapTopComponent MARSMapComp;
    private MARSLogTopComponent MARSLogComp;
    private boolean view_init = false;
    private boolean server_init = false;
    private boolean debugFilter = false;
    private boolean init = false;
    
    //main settings file
    private MARS_Settings mars_settings;
    private KeyConfig keyconfig;
    private PhysicalEnvironment physical_environment;
    private Initializer initer;
    private ArrayList auvs = new ArrayList();
    private ArrayList simobs = new ArrayList();
    private XML_JAXB_ConfigReaderWriter xml;
    private ConfigManager configManager;
    
    private ChaseCamera chaseCam;
    
    //water
    private Node sceneReflectionNode = new Node("sceneReflectionNode");
    private Collider RayDetectable = new Collider();
    private Node AUVsNode = new Node("AUVNode");
    private Node SimObNode = new Node("SimObNode");
    //warter currents
    private Node currents = new Node("currents");
    
    private Hanse auv_hanse;
    private Monsun2 auv_monsun2;
    
    private Future simStateFuture = null;
        
    //map stuff
    private MapState mapState;
    
    //progress bar (nb)
    private final ProgressHandle progr = ProgressHandleFactory.createHandle("SimState");
    
    /**
     * 
     * @param assetManager
     */
    public SimState(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
    
    /**
     * 
     * @param MARSTopComp
     * @param TreeTopComp
     * @param MARSMapComp
     * @param MARSLogComp
     * @param configManager  
     */
    public SimState(MARSTopComponent MARSTopComp ,MARSTreeTopComponent TreeTopComp, MARSMapTopComponent MARSMapComp, MARSLogTopComponent MARSLogComp,ConfigManager configManager) {
        this.TreeTopComp = TreeTopComp;
        this.MARSTopComp = MARSTopComp;
        this.MARSMapComp = MARSMapComp;
        this.MARSLogComp = MARSLogComp;
        this.configManager = configManager;
    }
        
    /**
     * 
     * @return
     */
    @Override
    public Node getRootNode(){
        return rootNode;
    }

    /**
     *
     * @param cam
     */
    @Override
    public void setCamera(Camera cam) {
        
    }
 
    /**
     *
     */
    @Override
    public void cleanup() {
        super.cleanup();
        
        //cleanup the initer (viewport, filters)
        initer.cleanup();
        
        //clean up all auvs (offscreen view from drag for example)
        auvManager.cleanup();

        //cleanup gui state
        
        //clean the cameras
        chaseCam.setEnabled(false);
        chaseCam = null;
        
        //deattach the state root node from the main 
        mars.getRootNode().detachChild(getRootNode());
        getRootNode().detachAllChildren();
    }

    /**
     *
     * @param stateManager
     * @param app
     */
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        
        //starting progressbar nb
        progr.start();
        progr.progress( "Starting SimState" );
        
        if(!super.isInitialized()){
            if(app instanceof MARS_Main){
                mars = (MARS_Main)app;
                assetManager = mars.getAssetManager();
                inputManager = mars.getInputManager();
                mars.getRootNode().attachChild(getRootNode());
            }else{
                throw new RuntimeException("The passed application is not of type \"MARS_Main\"");
            }
            
            //enable OcullusRift support
            //StereoCamAppState stereoCamAppState = new StereoCamAppState();
            //stateManager.attach(stereoCamAppState);

            
            //mars.getViewPort().setEnabled(false);
    
            
         /*   Matrix4f matrix = new Matrix4f(0.099684946f, 0.003476259f, 0.007129367f, -0.05035142f, -0.0035146326f, 0.099937364f, 4.1346974E-4f, -0.021245062f, -0.0071105273f, -6.6273817E-4f, 0.09974468f, -0.023290642f, 0.0f, 0.0f, 0.0f, 1.0f);
            Vector3f start = new Vector3f(1.9252679f, -49.951576f, -2.914092f); 
            Vector3f dir = new Vector3f(-0.035146322f, 0.9993737f, 0.0041346983f);
            Ray test = new Ray(start, dir);
            Vector3f v1 = new Vector3f(2.154625f, -0.832799f, -2.879551f);
            Vector3f v2 = new Vector3f(2.154625f, -0.832799f, -2.534256f);
            Vector3f v3 =  new Vector3f(-1.67098f, -0.832798f, -2.879551f);
            Vector3f v4 = Vector3f.ZERO;
            Vector3f v5 = Vector3f.ZERO;
            Vector3f v6 = Vector3f.ZERO;
            float t_world = 0f;
            float t = test.intersects(v1, v2, v3);
                if (!Float.isInfinite(t)) {
                        matrix.mult(v1, v4);
                        matrix.mult(v2, v5);
                        matrix.mult(v3, v6);
                        t_world = test.intersects(v4, v5, v6);
                }*/
            
          /*  Vector3f start = new Vector3f(0.2182425f, -5.027495f, 0.12827098f); 
            Vector3f dir = new Vector3f(0f, 1f, 0f);    
            Vector3f v1 = new Vector3f(0.19529787f, -0.10055651f, 0.12947007f);
            Vector3f v2 = new Vector3f(0.22324294f, -0.13183054f, 0.12800966f);
            Vector3f v3 =  new Vector3f(0.21205825f, -0.13176638f, 0.12909873f);
            mars.Ray test = new mars.Ray(start, dir);
            float testt = test.intersects(v1, v2, v3);
            
            Geometry mark5 = new Geometry("VideoCamera_Arrow_2", new Arrow(test.getDirection().mult(10f)));
            Material mark_mat4 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mark_mat4.setColor("Color", ColorRGBA.Green);
            mark5.setMaterial(mark_mat4);
            mark5.setLocalTranslation(test.getOrigin());
            mark5.updateGeometricState();
            rootNode.attachChild(mark5);

            Material mark_mat5 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mark_mat5.setColor("Color", ColorRGBA.Red);
            Geometry mark6 = new Geometry("VideoCamera_Arrow_3", new Arrow(new Vector3f(0.22324294f, -0.13183054f, 0.12800966f).subtract(new Vector3f(0.19529787f, -0.10055651f, 0.12947007f)).mult(1f)));
            mark6.setMaterial(mark_mat5);
            mark6.setLocalTranslation(new Vector3f(0.19529787f, -0.10055651f, 0.12947007f));
            mark6.updateGeometricState();
            rootNode.attachChild(mark6);

            Geometry mark7 = new Geometry("VideoCamera_Arrow_4", new Arrow(new Vector3f(0.21205825f, -0.13176638f, 0.12909873f).subtract(new Vector3f(0.22324294f, -0.13183054f, 0.12800966f)).mult(1f)));
            mark7.setMaterial(mark_mat5);
            mark7.setLocalTranslation(new Vector3f(0.22324294f, -0.13183054f, 0.12800966f));
            mark7.updateGeometricState();
            rootNode.attachChild(mark7);

            Geometry mark8 = new Geometry("VideoCamera_Arrow_5", new Arrow(new Vector3f(0.19529787f, -0.10055651f, 0.12947007f).subtract(new Vector3f(0.21205825f, -0.13176638f, 0.12909873f)).mult(1f)));
            mark8.setMaterial(mark_mat5);
            mark8.setLocalTranslation(new Vector3f(0.21205825f, -0.13176638f, 0.12909873f));
            mark8.updateGeometricState();
            rootNode.attachChild(mark8);*/
        
            progr.progress( "Adding Nodes" );
            sceneReflectionNode.attachChild(AUVsNode);
            sceneReflectionNode.attachChild(SimObNode);
            rootNode.attachChild(sceneReflectionNode);
            rootNode.attachChild(currents);
            
            progr.progress( "Loading Nifty" );
            initNiftyLoading();
            progr.progress( "Starting Nifty" );
            startNiftyState();
            progr.progress( "Loading Config" );
            loadXML(configManager.getConfigName());
            //progr.progress( "Init Keys" );
            //initPrivateKeys();// load custom key mappings
            progr.progress( "Starting Physics" );
            setupPhysics();
            //progr.progress( "Starting GUI" );
            //setupGUI();
            progr.progress( "Starting Cameras" );
            setupCams();
            
            progr.progress( "Creating Managers" );
            recordManager = new RecordManager(xml);
            //recordManager.loadRecordings();
            auvManager = new AUV_Manager(this);
            simobManager = new SimObjectManager(this);
        
            progr.progress( "Creating Initializer" );
            initer = new Initializer(mars,this,auvManager,physical_environment);
            initer.init();
            
            //set camera to look to (0,0,0)
            setupCamPos();
            mars.getCamera().lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
            

            if(mars_settings.getROSEnabled()){
                if(initer.checkROSServer()){//Waiting for ROS Server to be ready
                
                }
            }
                
            progr.progress( "Init Map" );
            initMap();//for mars_settings
            
            progr.progress( "Populate AUVManager" );
            populateAUV_Manager(auvs,physical_environment,mars_settings,recordManager,initer);
            
            Future fut = mars.enqueue(new Callable() {
             public Void call() throws Exception {
                 CentralLookup.getDefault().add(auvManager);
                 return null;
             }
            });
   
            progr.progress( "Populate SimObjectManager" );
            populateSim_Object_Manager(simobs);
            
            //initMap();//for manager
            
            //progr.progress( "Init public Keys" );
            //initPublicKeys();
            
            progr.progress( "Init View" );
            initView();
            
            init = true;
            
            /*JAXBContext context;
            try {*/
                /*context = JAXBContext.newInstance( MARS_Settings.class );
                Marshaller m = context.createMarshaller();
                m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                m.marshal( mars_settings, System.out );*/
                
                //XML_JAXB_ConfigReaderWriter xabl = new XML_JAXB_ConfigReaderWriter();
                //XML_JAXB_ConfigReaderWriter.loadSimObjects(); 
                
                /*File file = new File( "./xml/simobjects/room.xml" );
                context = JAXBContext.newInstance( SimObject.class );
                Marshaller m = context.createMarshaller();
                Unmarshaller u = context.createUnmarshaller();
                m = context.createMarshaller();
                m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                SimObject blo = (SimObject)simobs.get(0);
                //m.marshal( blo, System.out );
                m.marshal( blo, file );
                
                SimObject room2 = (SimObject)u.unmarshal( file );
                System.out.println( room2.getName() );*/
                
                /*SimObject blo = (SimObject)simobs.get(0);
                XML_JAXB_ConfigReaderWriter.saveSimObject(blo);
                
                XML_JAXB_ConfigReaderWriter.saveSimObjects(simobs);
                
                XML_JAXB_ConfigReaderWriter.loadSimObject("testpipe");
                
                XML_JAXB_ConfigReaderWriter.loadSimObjects();*/
            
                /*XML_JAXB_ConfigReaderWriter.saveMARS_Settings(mars_settings);
                
                MARS_Settings stt = XML_JAXB_ConfigReaderWriter.loadMARS_Settings();
                stt.initAfterJAXB();
                
                XML_JAXB_ConfigReaderWriter.savePhysicalEnvironment(physical_environment);
                
                PhysicalEnvironment pee = XML_JAXB_ConfigReaderWriter.loadPhysicalEnvironment();
                pee.initAfterJAXB();
                pee.getFluid_temp();*/
            
                //XML_JAXB_ConfigReaderWriter.saveAUV(auv_monsun2);
                
                //Monsun2 mon = (Monsun2)XML_JAXB_ConfigReaderWriter.loadAUV("monsun");  
            
                /*XML_JAXB_ConfigReaderWriter.saveAUV(auv_hanse);
                Hanse han = (Hanse)XML_JAXB_ConfigReaderWriter.loadAUV("hanse2"); */
                
                /*context = JAXBContext.newInstance( BasicAUV.class );
                Marshaller m = context.createMarshaller();
                m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );

                m.marshal( auv_hanse, System.out );*/
           /* } catch (JAXBException ex) {
                Logger.getLogger(StartState.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            
            //waiting for auvs and sensors/actuators to be ready
            //initer.start_ROS_Server();
            //initer.setupROS_Server();
            /*Thruster tt = (Thruster)auv_hanse.getActuator("thrusterDownFront");
            tt.test();*/
            //XML_JAXB_ConfigReaderWriter.saveAUV(auv_hanse);
            
            progr.progress( "Init GuiState" );
            final GuiState guiState = new GuiState();
            guiState.setAuvManager(auvManager);
            guiState.setSimobManager(simobManager);
            guiState.setIniter(initer);
            guiState.setAUVsNode(AUVsNode);
            guiState.setSimObNode(SimObNode);
            guiState.setMars_settings(mars_settings);
            final AppStateManager stateManagerFin = stateManager;
            Future fut2 = mars.enqueue(new Callable() {
             public Void call() throws Exception {
                    getMARS().getViewPort().attachScene(guiState.getRootNode());
                    stateManagerFin.attach(guiState);
                    return null;
             }
            });
            
            
            
            
            progr.progress( "Init other States" );
            progr.progress( "Init FishSwarm State" );
            Lookup lkp = Lookup.getDefault();
            AbstractAppState state = lkp.lookup(AbstractAppState.class);
            if(state != null){
                stateManager.attach(state);
            }
        }
        progr.progress( "Init Super" );
        super.initialize(stateManager, app);
        
        progr.finish();
    }
    
    private void initView(){
            TreeTopComp.setMarsSettings(mars_settings);
            MARSTopComp.setMarsSettings(mars_settings);
            TreeTopComp.setPenv(physical_environment);
            MARSTopComp.setPenv(physical_environment);
            TreeTopComp.setKeyConfig(keyconfig);
            MARSTopComp.setKeyConfig(keyconfig);
            TreeTopComp.setConfigManager(configManager);
            MARSTopComp.setConfigManager(configManager);
            TreeTopComp.setAuv_manager(auvManager);
            MARSTopComp.setAuv_manager(auvManager);
            TreeTopComp.setSimob_manager(simobManager);
            MARSTopComp.setSimob_manager(simobManager);
            TreeTopComp.initAUVTree(auvManager);
            TreeTopComp.initSimObjectTree(simobManager);
            TreeTopComp.initEnvironmentTree(physical_environment);
            TreeTopComp.initSettingsTree(mars_settings);
            TreeTopComp.initKeysTree(keyconfig);
            TreeTopComp.initPopUpMenues(auvManager);
            TreeTopComp.initDND();
            MARSTopComp.initDND();
            MARSTopComp.allowSimInteraction();
            TreeTopComp.updateTrees();
            MARSMapComp.initDND();

        if(mars_settings.getROSEnabled()){
                /*Logger.getLogger(SimState.class.getName()).log(Level.INFO, "Waiting for ROS Server to be ready...", "");
                while(!initer.isROS_ServerReady()){
                    
                }
                Logger.getLogger(SimState.class.getName()).log(Level.INFO, "ROS Server ready.", "");
                Logger.getLogger(SimState.class.getName()).log(Level.INFO, "Waiting for ROS Server Node to be created...", "");
                while(initer.getROS_Server().getMarsNode() == null){
                    
                }
                Logger.getLogger(SimState.class.getName()).log(Level.INFO, "ROS Server Node created.", "");
                Logger.getLogger(SimState.class.getName()).log(Level.INFO, "Waiting for ROS Server Node to exist...", "");
                while(!initer.getROS_Server().getMarsNode().isExisting()){
                    
                }
                Logger.getLogger(SimState.class.getName()).log(Level.INFO, "ROS Server Node exists.", "");
                Logger.getLogger(SimState.class.getName()).log(Level.INFO, "Waiting for ROS Server Node to be running...", "");
                while(!initer.getROS_Server().getMarsNode().isRunning()){
                    
                }
                Logger.getLogger(SimState.class.getName()).log(Level.INFO, "ROS Server Node running.", "");*/
                //server_init = true;//server running, is needed because view is sometimes null in the beginning(see update)
                //server_init = initer.checkROSServer();
            if(initer.checkROSServer()){
                MARSTopComp.allowServerInteraction(true);
            }else{
                MARSTopComp.allowServerInteraction(false);
            }
        }else{
            MARSTopComp.allowServerInteraction(false);
        }
    }
    
    /**
     * 
     */
    public void connectToServer(){
        mars_settings.setROSEnabled(true);
        initer.setupServer();
        if(initer.checkROSServer()){
                MARSTopComp.allowServerInteraction(true);
        }else{
                MARSTopComp.allowServerInteraction(false);
        }
    }
    
    /**
     * 
     */
    public void disconnectFromServer(){
        mars_settings.setROSEnabled(false);
        MARSTopComp.enableServerInteraction(false);
        initer.killServer();
        MARSTopComp.allowServerInteraction(false);
    }
    
    /**
     *
     * @param enable
     */
    public void enablePublishing(boolean enable){
        mars_settings.setROSPublish(enable);
    }
    
    /**
     *
     * @param enable
     */
    public void enableRecording(boolean enable){
        if(recordManager != null){
            recordManager.setEnabled(enable);
        }
    }
    
    /**
     *
     */
    public void playRecording(){
        recordManager.play();
        AUV auv = auvManager.getAUV("hanse");
        RigidBodyControl control = auv.getAUVNode().getControl(RigidBodyControl.class);
        control.setEnabled(false);
        RecordControl recordControl = new RecordControl(recordManager,auv,MARSLogComp);
        auv.getAUVNode().addControl(recordControl);
    }
    
    /**
     *
     * @param step
     */
    public void setRecord(int step){
        System.out.println("Step set to: " + step);
        recordManager.setRecord(step);
    }
    
    /**
     *
     */
    public void pauseRecording(){
        recordManager.pause();
        recordManager.setEnabled(false);
    }
    
    /**
     *
     * @param file
     */
    public void saveRecording(File file){
        recordManager.saveRecording(file);
    }
    
    /**
     *
     * @param file
     */
    public void loadRecording(File file){
        recordManager.loadRecordings(file);
    }
    
    private void initMap(){
        Future fut = mars.enqueue(new Callable() {
             public Void call() throws Exception {
                mapState.loadMap(mars_settings.getTerrainColorMap());
                mapState.setMars_settings(mars_settings);
                mapState.setAuv_manager(auvManager);
                return null;
            }
        });
    }
    
    /*
     *
     */
    private void setupPhysics(){
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        mars.getStateManager().attach(bulletAppState);
        //set the physis world parameters
        bulletAppState.getPhysicsSpace().setMaxSubSteps(mars_settings.getPhysicsMaxsubsteps());
        
        //setting Filter in the DebugState so we can show specific collision boxes
        /*if (mars.getStateManager().getState(BulletDebugAppState.class) != null) {
            mars.getStateManager().getState(BulletDebugAppState.class).setFilter(new MyDebugAppStateFilter()); 
        }*/ //doesnt work here because DebugAppState suuuuuucks
        
        if(mars_settings.getPhysicsDebug()){
            bulletAppState.setDebugEnabled(true);
        }
            
        bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0.0f, 0.0f, 0.0f));
        bulletAppState.getPhysicsSpace().setAccuracy(1f/mars_settings.getPhysicsFramerate());
        bulletAppState.getPhysicsSpace().addTickListener(this);
        physical_environment.setBulletAppState(bulletAppState);
        
        bulletAppState.setEnabled(false);
    }

    private void setupCams(){
        mars.getFlyByCamera().setMoveSpeed(mars_settings.getCameraFlyCamMoveSpeed());
        mars.getFlyByCamera().setEnabled(true);
        chaseCam = new ChaseCamera(mars.getCamera(),rootNode,inputManager);
        chaseCam.setInvertVerticalAxis(true);
        chaseCam.setZoomSensitivity(mars_settings.getCameraChaseCamZoomSensitivity());
        chaseCam.setEnabled(false);
    }
    
    private void setupCamPos(){
        moveCamera(mars_settings.getCameraDefaultPosition(), false);
        rotateCamera(mars_settings.getCameraDefaultRotation(), false);
    }
    
    /*
     *
     */
    private void loadXML(String config){
        try {
            xml = new XML_JAXB_ConfigReaderWriter(config);
             keyconfig = xml.loadKeyConfig();    
             mars_settings = xml.loadMARS_Settings();
             physical_environment = xml.loadPhysicalEnvironment();
             mars_settings.setPhysical_environment(physical_environment);
                
             /*Dynamixel_AX12PLUS serv = new Dynamixel_AX12PLUS();
                serv.setEnabled(true);
                serv.setNodeVisibility(true);
                serv.setPhysicalExchangerName("servo");
                serv.setServoStartVector(new Vector3f(0.015f, -0.02f,-0.24f));
                serv.setServoDirection(new Vector3f(0f, 0f, -1f));
                JAXBContext context = JAXBContext.newInstance( Dynamixel_AX12PLUS.class );
                Marshaller m = context.createMarshaller();
                m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                m.marshal( serv, System.out );*/
               /* TerrainSender serv = new TerrainSender();
                serv.setEnabled(true);
                serv.setNodeVisibility(true);
                serv.setPhysicalExchangerName("terrainsender");
                JAXBContext context = JAXBContext.newInstance( TerrainSender.class );
                Marshaller m = context.createMarshaller();
                m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                m.marshal( serv, System.out );*/
             
                /*System.out.println("Recording:");
                Recording recs = new Recording();
                recs.addRecord();
                JAXBContext context = JAXBContext.newInstance( Recording.class );
                Marshaller m = context.createMarshaller();
                m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                m.marshal( recs, System.out );*/
             
             
             auvs = xml.loadAUVs();//xmll.getAuvs();
                /*IMU im = new IMU();
                im.setEnabled(true);
                im.setNodeVisibility(false);
                im.setPhysicalExchangerName("imu");
                Hanse hans = (Hanse)auvs.get(1);
                hans.registerPhysicalExchanger(im);*/
             
                /*Dynamixel_AX12PLUS serv = new Dynamixel_AX12PLUS();
                serv.setEnabled(true);
                serv.setNodeVisibility(true);
                serv.setPhysicalExchangerName("servo");
                serv.setServoStartVector(new Vector3f(0.015f, -0.02f,-0.24f));
                serv.setServoDirection(new Vector3f(0f, 0f, -1f));
                JAXBContext context = JAXBContext.newInstance( Dynamixel_AX12PLUS.class );
                Marshaller m = context.createMarshaller();
                m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                m.marshal( serv, System.out );*/
                
                /*Hanse hans = null;
                Iterator iter2 = auvs.iterator();
                while(iter2.hasNext() ) {
                    AUV aaa = (AUV)iter2.next();
                    if(            aaa.getAuv_param().getName().equals("hanse2") ){
                    hans = (Hanse)aaa;
                    }
                 }

                hans.registerPhysicalExchanger(serv);*/
             
             //do stuff after jaxb, see also UnmarshallListener
             Iterator iter = auvs.iterator();
             while(iter.hasNext() ) {
                BasicAUV bas_auv = (BasicAUV)iter.next();
                bas_auv.getAuv_param().setAuv(bas_auv);
                bas_auv.setName(bas_auv.getAuv_param().getName());
                bas_auv.setState(this);
             }
             simobs = xml.loadSimObjects();
        } catch (Exception ex) {
            Logger.getLogger(SimState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     /*
     *
     */
    private void populateAUV_Manager(ArrayList auvs,PhysicalEnvironment pe, MARS_Settings mars_settings, RecordManager recordManager, Initializer initer){
        auvManager.setBulletAppState(bulletAppState);
        auvManager.setPhysical_environment(pe);
        auvManager.setSimauv_settings(mars_settings);
        auvManager.setRecManager(recordManager);
        if(mars_settings.getROSEnabled()){
            auvManager.setMARSNodes(initer.getROS_Server().getMarsNodes());
        }
        auvManager.registerAUVs(auvs);
        //update the view in the next frame
        Future fut = mars.enqueue(new Callable() {
                public Void call() throws Exception {
                    TreeTopComp.updateTrees();
                    TreeTopComp.initPopUpMenues(auvManager);
                    return null;
                }
                });
    }

    /*
     *
     */
    private void populateSim_Object_Manager(ArrayList simobs){
        simobManager.setBulletAppState(bulletAppState);
        simobManager.registerSimObjects(simobs);
        //update the view in the next frame
        Future fut = mars.enqueue(new Callable() {
                public Void call() throws Exception {
                    TreeTopComp.updateTrees();
                    return null;
                }
                });
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isInitialized() {
        return super.isInitialized();
    }

    /**
     *
     */
    @Override
    public void postRender() {
        if (!super.isEnabled()) {
            return;
        }
        super.postRender();
    }

    /**
     *
     * @param rm
     */
    @Override
    public void render(RenderManager rm) {
        if (!super.isEnabled()) {
            return;
        }
        super.render(rm);
    }

    /**
     *
     * @param enabled
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled); 
        if(!enabled){
            rootNode.setCullHint(Spatial.CullHint.Always);
        }else{
            rootNode.setCullHint(Spatial.CullHint.Never);
        }
    }

    /**
     *
     * @param stateManager
     */
    @Override
    public void stateAttached(AppStateManager stateManager) {
        super.stateAttached(stateManager);
    }

    /**
     *
     * @param stateManager
     */
    @Override
    public void stateDetached(AppStateManager stateManager) {
        super.stateDetached(stateManager);
    }

    /**
     *
     * @param tpf
     */
    @Override
    public void update(float tpf) {
        if (!super.isEnabled()) {
            return;
        }
        super.update(tpf);
        
        if(TreeTopComp == null){
            System.out.println("TreeTopComp is NULL");
        }
        if(MARSTopComp == null){
            System.out.println("MARSTopComp is NULL");
        }
        
        if(mars_settings.isWavesWaterEnabled()){
            initer.updateWavesWater(tpf);
        }

        if(mars_settings.isProjectedWavesWaterEnabled()){
            initer.updateProjectedWavesWater(tpf);
        }
        
        if(mars_settings.isGrassEnabled()){
            initer.updateGrass(tpf);
        }
        
        if(initer != null && initer.getSkyControl() != null){
            if(getMARSSettings().getSkyDomeSpeed() != 0f){
                if(initer.getTimeOfDay().isInitialized()){
                    initer.getTimeOfDay().update(tpf);
                    initer.getTimeOfDay().setRate(getMARSSettings().getSkyDomeSpeed() * getMARSSettings().getSkyDomeDirection());
                    initer.getSkyControl().getSunAndStars().setHour(initer.getTimeOfDay().getHour());
                }
            }
        }
        
        //rootNode.updateLogicalState(tpf);
        //rootNode.updateGeometricState();
    }
    
    /**
     * 
     * @return
     */
    public SimObjectManager getSimob_manager() {
        return simobManager;
    }

    /**
     *
     * @return
     */
    public AUV_Manager getAuvManager() {
        return auvManager;
    }
 
    /**
     *
     * @return
     */
    public BulletAppState getBulletAppState(){
        return bulletAppState;
    }

    /**
     *
     * @return
     */
    public Node getSceneReflectionNode() {
        return sceneReflectionNode;
    }

    /**
     * 
     * @return
     */
    public Node getAUVsNode() {
        return AUVsNode;
    }
    
    /**
     *
     * @return
     */
    public Collider getCollider() {
        return RayDetectable;
    }
    
    /**
     *
     * @return
     */
    public Node getSimObNode() {
        return SimObNode;
    }

    /**
     * 
     * @return
     */
    public MARS_Settings getMARSSettings() {
        return mars_settings;
    }

    /**
     *
     * @return
     */
    public ChaseCamera getChaseCam(){
        return chaseCam;
    }

    /**
     *
     * @return
     */
    public Initializer getIniter() {
        return initer;
    }
    
    /**
     * 
     * @return
     */
    public MARS_Main getMARS() {
        return mars;
    }

    /**
     * 
     * @return
     */
    public KeyConfig getKeyconfig() {
        return keyconfig;
    }
    
    /**
     * 
     * @return
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * 
     * @param ps
     * @param tpf
     */
    @Override
    public void prePhysicsTick(PhysicsSpace ps, final float tpf) {
        if (!super.isEnabled()) {
            return;
        }
        //limit velocitys
        /*Collection<PhysicsRigidBody> rigidBodyList = ps.getRigidBodyList();
        //System.out.println("rbody size: " + rigidBodyList.size());
        for (PhysicsRigidBody physicsRigidBody : rigidBodyList) {
            Vector3f angularVelocity = physicsRigidBody.getAngularVelocity();
            System.out.println("angular_drag_torque_vec: " + angularVelocity.length() + " " + angularVelocity);
            float speed = angularVelocity.length();
            if(Helper.infinityCheck(angularVelocity)){
                System.out.println("INF!!!!!");
                physicsRigidBody.setAngularVelocity(Vector3f.ZERO);
            }
            if(speed > 1f) {
                angularVelocity.mult(1f/speed);
                physicsRigidBody.setAngularVelocity(angularVelocity);
            }
        }*/
        
        /*AUV auv = auvManager.getAUV("hanse");
        if(auv != null){
            GhostControl ghostControl = auv.getGhostControl();
            if(ghostControl != null){
                    List<PhysicsCollisionObject> overlappingObjects = ghostControl.getOverlappingObjects();
                    for (PhysicsCollisionObject physicsCollisionObject : overlappingObjects) {
                        System.out.println(physicsCollisionObject.toString());
                    }
                    System.out.println("ghostControl.getOverlappingCount(): " + ghostControl.getOverlappingCount());
            }
        }*/
    
        if(recordManager != null){
            recordManager.update(tpf);
        }
        
        //setting Filter in the DebugState so we can show specific collision boxes
        if (mars.getStateManager().getState(BulletDebugAppState.class) != null) {
            if(!debugFilter && init){
                Logger.getLogger(SimState.class.getName()).log(Level.INFO, "Initialiazing DebugAppStateFilter...", "");
                mars.getStateManager().getState(BulletDebugAppState.class).setFilter(new MyDebugAppStateFilter(mars_settings,auvManager)); 
                debugFilter = true;
            }
        }

        //only update physics when simulation is started and auv_manager/comManager are both ready and instantied.
        if(auvManager != null && initial_ready){
            auvManager.updateAllAUVs(tpf);
        }            
    }

    /**
     * 
     * @param ps
     * @param tpf
     */
    @Override
    public void physicsTick(PhysicsSpace ps, float tpf) {
        if (!super.isEnabled()) {
            return;
        }
         /*//limit velocitys
        Collection<PhysicsRigidBody> rigidBodyList = ps.getRigidBodyList();
        //System.out.println("rbody size: " + rigidBodyList.size());
        for (PhysicsRigidBody physicsRigidBody : rigidBodyList) {
            Vector3f angularVelocity = physicsRigidBody.getAngularVelocity();
            float speed = angularVelocity.length();
                if(speed > 1f) {
                    angularVelocity.mult(1f/speed);
                    physicsRigidBody.setAngularVelocity(angularVelocity);
                }
        }*/
        //System.out.println("PHYSICS: " + tpf);
    }

    private void initNiftyLoading(){
        //Element element = nifty_load.getScreen("loadlevel").findElementByName("loadingtext");
        //textRenderer = element.getRenderer(TextRenderer.class);
        //mars.setProgressWithoutEnq(0.5f, "dfsdfsdf");
        //System.out.println("setting loading!!!");
        //mars.setProgress(0.5f, "dfsdfsdf");
        /*try {
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SimState.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    
    /**
     * 
     */
    public void startSimulation(){
        simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                bulletAppState.getPhysicsSpace().setGravity(physical_environment.getGravitational_acceleration_vector());
                initial_ready = true;
                MARSTopComp.allowPhysicsInteraction(true);
                bulletAppState.setEnabled(true);
                System.out.println("Simulation started...");
                return null;
            }
        }); 
    }
            
    /**
     * 
     */
    public void pauseSimulation(){
        simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                bulletAppState.setEnabled(false);
                bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0.0f, 0.0f, 0.0f));
                auvManager.clearForcesOfAUVs();
                initial_ready = false;
                MARSTopComp.allowPhysicsInteraction(false);
                System.out.println("Simulation stopped...");    
                return null;
            }
        });         
    }
    
    /**
     * 
     */
    public void restartSimulation(){
        simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                System.out.println("RESET!!!");
                auvManager.resetAllAUVs();
                return null;
            }
        });  
    }
    
    /**
     * 
     * @param mapState
     */
    public void setMapState(MapState mapState) {
        this.mapState = mapState;
    }
    
    /**
     * 
     * @param new_position
     * @param relative
     */
    public void moveCamera(Vector3f new_position, boolean relative){
        if(!relative){
            mars.getCamera().setLocation(new_position);
        }else{
            mars.getCamera().setLocation(mars.getCamera().getLocation().add(new_position));
        }
    }
    
    /**
     * 
     * @param new_rotation
     * @param relative
     */
    public void rotateCamera(Vector3f new_rotation, boolean relative){
        //System.out.println("rotateCamera" + new_rotation);
        if(!relative){
            Quaternion quat = new Quaternion();
            quat.fromAngles(new_rotation.getX(), new_rotation.getY(), new_rotation.getZ());
            mars.getCamera().setRotation(quat);
        }
    }
    
    /**
     * 
     * @param auv
     */
    public void chaseAUV(AUV auv){
        if(auv != null){
            mars.getFlyByCamera().setEnabled(false);
            mars.getChaseCam().setSpatial(auv.getAUVNode());
            mars.getChaseCam().setEnabled(true);
        }
    }
    
    /**
     * Enables an AUV and sets it to the position. If already enabled then position change. The position is computed from he screen position.
     * @param auvName
     * @param pos
     * @param dropAction 
     * @param name  
     */
    public void enableAUV(String auvName, Point pos, int dropAction, String name){
        AUV auv = auvManager.getAUV(auvName);
        if(auv != null){
            Vector3f click3d = mars.getCamera().getWorldCoordinates(new Vector2f(pos.x, mars.getCamera().getHeight()-pos.y), 0f).clone();
            Vector3f dir = mars.getCamera().getWorldCoordinates(new Vector2f(pos.x, mars.getCamera().getHeight()-pos.y), 1f).subtractLocal(click3d);
            Vector3f intersection = Helper.getIntersectionWithPlaneCorrect(new Vector3f(0f, initer.getCurrentWaterHeight(pos.x, mars.getCamera().getHeight()-pos.y), 0f),Vector3f.UNIT_Y,click3d, dir);
            if(dropAction == TransferHandler.COPY){
                AUV auvCopy = new BasicAUV(auv);
                auvCopy.getAuv_param().setAuv(auvCopy);
                auvCopy.setName(name);
                auvCopy.getAuv_param().setPosition(intersection);
                auvCopy.setState(this);
                auvManager.registerAUV(auvCopy);
                //we have to update the view AFTER the AUV register
                Future simStateFutureView = mars.enqueue(new Callable() {
                    public Void call() throws Exception {
                        if(TreeTopComp != null){
                            TreeTopComp.updateTrees(); 
                        }
                        return null;
                    }
                });           
            }else{
                if( auv.getAuv_param().isEnabled()){//check if auf auv already enabled, then only new position
                    auv.getAuv_param().setPosition(intersection);
                    auv.getPhysicsControl().setPhysicsLocation(intersection);
                }else{
                    auv.getAuv_param().setPosition(intersection);
                    auv.getAuv_param().setEnabled(true);
                    auvManager.enableAUV(auv, true);
                    auv.getPhysicsControl().setPhysicsLocation(intersection);
                }
            }
        }
    }
    
    /**
     * Enables an AUV and sets it to the position. If already enabled then position change.
     * @param auvName
     * @param pos
     * @param dropAction 
     * @param name  
     */
    public void enableAUV(String auvName, Vector3f pos, int dropAction, String name){
        AUV auv = auvManager.getAUV(auvName);
        pos.y = initer.getCurrentWaterHeight(pos.x, mars.getCamera().getHeight()-pos.y);
        if(auv != null){
            if(dropAction == TransferHandler.COPY){
                AUV auvCopy = new BasicAUV(auv);
                auvCopy.getAuv_param().setAuv(auvCopy);
                auvCopy.setName(name);
                auvCopy.getAuv_param().setPosition(pos);
                auvCopy.setState(this);
                auvManager.registerAUV(auvCopy);
                /*view.updateTrees();
                Future simStateFutureView = mars.enqueue(new Callable() {
                    public Void call() throws Exception {
                        if(view != null){
                            view.updateTrees(); 
                        }
                        return null;
                    }
                }); */
            }else{
                if( auv.getAuv_param().isEnabled()){//check if auf auv already enabled, then only new position
                    auv.getAuv_param().setPosition(pos);
                    auv.getPhysicsControl().setPhysicsLocation(pos);
                }else{
                    auv.getAuv_param().setPosition(pos);
                    auv.getAuv_param().setEnabled(true);
                    auvManager.enableAUV(auv, true);
                    auv.getPhysicsControl().setPhysicsLocation(pos);
                }
            }
        }
    }
    
    /**
     * Enables an SimObject and sets it to the position. If already enabled then position change.
     * @param simobName
     * @param pos
     * @param dropAction 
     * @param name  
     */
    public void enableSIMOB(String simobName, Vector3f pos, int dropAction, String name){
        SimObject simob = simobManager.getSimObject(simobName);
        pos.y = initer.getCurrentWaterHeight(pos.x, mars.getCamera().getHeight()-pos.y);
        if(simob != null){
            if(dropAction == TransferHandler.COPY){
                SimObject simobCopy = simob.copy();
                simobCopy.setName(name);
                simobCopy.setPosition(pos);
                simobManager.registerSimObject(simobCopy);
                TreeTopComp.updateTrees();
                Future simStateFutureView = mars.enqueue(new Callable() {
                    public Void call() throws Exception {
                        if(TreeTopComp != null){
                            TreeTopComp.updateTrees(); 
                        }
                        return null;
                    }
                }); 
            }else{
                if( simob.isEnabled()){//check if auf simob already enabled, then only new position
                    simob.setPosition(pos);
                    simob.getPhysicsControl().setPhysicsLocation(pos);
                }else{
                    simob.setPosition(pos);
                    simob.setEnabled(true);
                    simobManager.enableSimObject(simob, true);
                    simob.getPhysicsControl().setPhysicsLocation(pos);
                }
            }
        }
    }
    
    /**
     * Enables an AUV and sets it to the position. If already enabled then position change.  The position is computed from he screen position.
     * @param simobName
     * @param pos
     * @param dropAction
     * @param name  
     */
    public void enableSIMOB(String simobName, Point pos, int dropAction, String name){
        SimObject simob = simobManager.getSimObject(simobName);
        if(simob != null){
            Vector3f click3d = mars.getCamera().getWorldCoordinates(new Vector2f(pos.x, mars.getCamera().getHeight()-pos.y), 0f).clone();
            Vector3f dir = mars.getCamera().getWorldCoordinates(new Vector2f(pos.x, mars.getCamera().getHeight()-pos.y), 1f).subtractLocal(click3d);
            Vector3f intersection = Helper.getIntersectionWithPlaneCorrect(new Vector3f(0f, initer.getCurrentWaterHeight(pos.x, mars.getCamera().getHeight()-pos.y), 0f),Vector3f.UNIT_Y,click3d, dir);
            if(dropAction == TransferHandler.COPY){
                SimObject simobCopy = simob.copy();
                simobCopy.setName(name);
                simobCopy.setPosition(intersection);
                simobManager.registerSimObject(simobCopy);
                /*view.updateTrees();
                Future simStateFutureView = mars.enqueue(new Callable() {
                    public Void call() throws Exception {
                        if(view != null){
                            view.updateTrees(); 
                        }
                        return null;
                    }
                }); */
            }else{
                if( simob.isEnabled()){//check if auf simob already enabled, then only new position
                    simob.setPosition(intersection);
                    simob.getPhysicsControl().setPhysicsLocation(intersection);
                }else{
                    simob.setPosition(intersection);
                    simob.setEnabled(true);
                    simobManager.enableSimObject(simob, true);
                    simob.getPhysicsControl().setPhysicsLocation(intersection);
                }
            }
        }
    }
    
    /**
     *
     */
    public void startNiftyState(){
        if (mars.getStateManager().getState(NiftyState.class) != null) {
            mars.getStateManager().getState(NiftyState.class).show(); 
        }
    }
    
    /**
     *
     * @param TreeTopComp 
     */
    public void setTreeTopComp(MARSTreeTopComponent TreeTopComp){
        this.TreeTopComp = TreeTopComp;
    }

     /**
      *
      * @return
      */
    public MARSTreeTopComponent getTreeTopComp() {
        return TreeTopComp;
    }

    /**
     *
     * @return
     */
    public MARSTopComponent getMARSTopComp() {
        return MARSTopComp;
    }

    /**
     *
     * @param MARSTopComp
     */
    public void setMARSTopComp(MARSTopComponent MARSTopComp) {
        this.MARSTopComp = MARSTopComp;
    }
}
