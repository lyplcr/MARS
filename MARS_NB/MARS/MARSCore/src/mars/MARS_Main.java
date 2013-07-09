/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mars;

import com.jme3.app.FlyCamAppState;
import com.jme3.input.event.JoyAxisEvent;
import com.jme3.input.event.JoyButtonEvent;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.input.event.TouchEvent;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mars.accumulators.Accumulator;
import mars.states.SimState;
import mars.states.StartState;
import com.jme3.font.BitmapFont;
import mars.gui.MARSView;
import mars.xml.XMLConfigReaderWriter;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.ScreenshotAppState;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.ChaseCamera;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.RawInputListener;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.plugins.OBJLoader;
import com.jme3.system.AppSettings;
import com.jme3.system.awt.AwtPanel;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;
import de.lessvoid.nifty.tools.SizeValue;
import de.lessvoid.xml.xpp3.Attributes;
import java.io.File;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import mars.auv.AUV;
import mars.core.MARSLogTopComponent;
import mars.core.MARSMapTopComponent;
import mars.core.MARSTopComponent;
import mars.core.MARSTreeTopComponent;
import mars.states.MapState;
import mars.states.NiftyState;
import mars.xml.ConfigManager;
import mars.xml.XML_JAXB_ConfigReaderWriter;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.modules.InstalledFileLocator;
import org.openide.util.RequestProcessor;


/**
 * This is the MAIN class for JME3.
 * @author Thomas Tosik
 */
public class MARS_Main extends SimpleApplication implements ScreenController,Controller{

    //needed for graphs
    @Deprecated
    private MARSView view;
    private MARSTreeTopComponent TreeTopComp;
    private MARSTopComponent MARSTopComp;
    private MARSMapTopComponent MARSMapComp;
    private MARSLogTopComponent MARSLogComp;
    private boolean view_init = false;
    private boolean startstateinit = false;
    private boolean statsDarken = true;

    StartState startstate;
    MapState mapstate;
    NiftyState niftystate;
    
    ChaseCamera chaseCam;
    Camera map_cam;
    
    ViewPort MapViewPort;
    ViewPort ViewPort2;
    
    AdvancedFlyByCamera advFlyCam;
    
    ConfigManager configManager = new ConfigManager();
    
    //nifty(gui) stuff
    private NiftyJmeDisplay niftyDisplay;
    private Nifty nifty;
    private Element progressBarElement;
    private TextRenderer textRenderer;
    private boolean load = false;
    private Future simStateFuture = null;
    private ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(2);

    private float[] speeds = new float[8];
    private int speedsCount = 3;//default speed
    
    //progress bar (nb)
    private final ProgressHandle progr = ProgressHandleFactory.createHandle("MARS_Main");
    

    /**
     *
     */
    public MARS_Main() {
        super();
        //Logger.getLogger(this.getClass().getName()).setLevel(Level.OFF);
        speeds[0] = 0.25f;
        speeds[1] = 0.5f;
        speeds[2] = 0.75f;
        speeds[3] = 1f;
        speeds[4] = 1.5f;
        speeds[5] = 2.0f;
        speeds[6] = 3.0f;
        speeds[7] = 4.0f;
    }

    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        MARS_Main app = new MARS_Main();
        app.start();
    }

    /**
     *
     */
    @Override
    public void simpleInitApp() {
        initAssetPaths();
        initProgressBar();
        progr.progress( "Starting MARS_MAIN" );
        XML_JAXB_ConfigReaderWriter xml = new XML_JAXB_ConfigReaderWriter();
        MARS_Settings mars_settings = xml.loadMARS_Settings();
        configManager.setConfig(mars_settings.getAutoConfigName());
        //initNifty();
        progr.progress( "Init Map ViewPort" );
        initMapViewPort();
        //initAssetsLoaders();
        progr.progress( "Creating StartState" );
        startstate = new StartState(assetManager);
        startstate.setEnabled(true);
        if(!mars_settings.isAutoEnabled()){
            viewPort.attachScene(startstate.getRootNode());
            //ViewPort2.attachScene(startstate.getRootNode());
            stateManager.attach(startstate);
        }

        progr.progress( "Creating MapState" );
        mapstate = new MapState(assetManager);
        MapViewPort.attachScene(mapstate.getRootNode());
        stateManager.attach(mapstate);
        
        //nifty state
        progr.progress( "Creating NiftyState" );
        niftystate = new NiftyState();
        //viewPort.attachScene(niftystate.getRootNode());
        stateManager.attach(niftystate);
        
        //attach Screenshot AppState
        progr.progress( "Creating ScreenshotAppState" );
        ScreenshotAppState screenShotState = new ScreenshotAppState();
        stateManager.attach(screenShotState);
        
        //deactivate the state, solves maybe wasd problems
        if (stateManager.getState(FlyCamAppState.class) != null) {
            stateManager.getState(FlyCamAppState.class).setEnabled(false); 
        }
        //overrirde standard flybycam      
        flyCam.setEnabled(false);
        flyCam.unregisterInput();
        advFlyCam = new AdvancedFlyByCamera(cam);
        advFlyCam.setDragToRotate(true);
        advFlyCam.setEnabled(false);
        advFlyCam.registerWithInput(inputManager);
        
        if(mars_settings.isAutoEnabled()){
            //SimState simstate = new SimState(view,configManager);
            progr.progress( "Creating SimState" );
            SimState simstate = new SimState(MARSTopComp,TreeTopComp,MARSMapComp,MARSLogComp,configManager);
            simstate.setMapState(mapstate);
            stateManager.attach(simstate);
        }else{
            configManager.setConfig("default");
        }
            
       /* FlyCamAppState flycamState = (FlyCamAppState)stateManager.getState(FlyCamAppState.class);
        if(flycamState != null){
            stateManager.detach(flycamState);
            AdvancedFlyCamAppState flyc = new AdvancedFlyCamAppState();
            AdvancedFlyByCamera advFlyCam = new AdvancedFlyByCamera(cam);
            flyc.setCamera(advFlyCam);
            flyCam = advFlyCam;
            stateManager.attach(flyc);
        }*/
        progr.finish();
    }
    
    private void initAssetPaths(){
        File file = InstalledFileLocator.getDefault().locate("Assets/Images", "mars.core", false);
        String absolutePath = file.getAbsolutePath();
        assetManager.registerLocator(absolutePath, FileLocator.class);
        File file2 = InstalledFileLocator.getDefault().locate("Assets/Interface", "mars.core", false);
        String absolutePath2 = file2.getAbsolutePath();
        assetManager.registerLocator(absolutePath2, FileLocator.class);
        File file3 = InstalledFileLocator.getDefault().locate("Assets/Icons", "mars.core", false);
        String absolutePath3 = file3.getAbsolutePath();       
        assetManager.registerLocator(absolutePath3, FileLocator.class);
        
        File file4 = InstalledFileLocator.getDefault().locate("Assets/Textures", "mars.core", false);
        assetManager.registerLocator(file4.getAbsolutePath(), FileLocator.class);
        File file6 = InstalledFileLocator.getDefault().locate("Assets/Textures/Sky", "mars.core", false);
        assetManager.registerLocator(file6.getAbsolutePath(), FileLocator.class);
        File file7 = InstalledFileLocator.getDefault().locate("Assets/Textures/Terrain", "mars.core", false);
        assetManager.registerLocator(file7.getAbsolutePath(), FileLocator.class);
        File file10 = InstalledFileLocator.getDefault().locate("Assets/Textures/Flow", "mars.core", false);
        assetManager.registerLocator(file10.getAbsolutePath(), FileLocator.class);
        File file20 = InstalledFileLocator.getDefault().locate("Assets/Textures/Water", "mars.core", false);
        assetManager.registerLocator(file20.getAbsolutePath(), FileLocator.class);
        
        File file5 = InstalledFileLocator.getDefault().locate("Assets/Models", "mars.core", false);
        assetManager.registerLocator(file5.getAbsolutePath(), FileLocator.class);
        
        File file8 = InstalledFileLocator.getDefault().locate("Assets/shaderblowlibs", "mars.core", false);
        assetManager.registerLocator(file8.getAbsolutePath(), FileLocator.class);
        
        File file9 = InstalledFileLocator.getDefault().locate("Assets/gridwaves", "mars.core", false);
        assetManager.registerLocator(file9.getAbsolutePath(), FileLocator.class);
        
        File file11 = InstalledFileLocator.getDefault().locate("Assets/FishEye", "mars.core", false);
        assetManager.registerLocator(file11.getAbsolutePath(), FileLocator.class);
        
        File file12 = InstalledFileLocator.getDefault().locate("Assets/Forester", "mars.core", false);
        assetManager.registerLocator(file12.getAbsolutePath(), FileLocator.class);
        
        File file13 = InstalledFileLocator.getDefault().locate("Assets/LensFlare", "mars.core", false);
        assetManager.registerLocator(file13.getAbsolutePath(), FileLocator.class);
        
        File file14 = InstalledFileLocator.getDefault().locate("Assets/MatDefs", "mars.core", false);
        assetManager.registerLocator(file14.getAbsolutePath(), FileLocator.class);
        
        //File file15 = InstalledFileLocator.getDefault().locate("Assets/Materials", "mars.core", false);
        //assetManager.registerLocator(file15.getAbsolutePath(), FileLocator.class);
        
        File file16 = InstalledFileLocator.getDefault().locate("Assets/Rim", "mars.core", false);
        assetManager.registerLocator(file16.getAbsolutePath(), FileLocator.class);
        
        File file17 = InstalledFileLocator.getDefault().locate("Assets/ShaderBlow", "mars.core", false);
        assetManager.registerLocator(file17.getAbsolutePath(), FileLocator.class);
        
        File file18 = InstalledFileLocator.getDefault().locate("Assets/Shaders", "mars.core", false);
        assetManager.registerLocator(file18.getAbsolutePath(), FileLocator.class);
    }
    
    /*
     * We use or own OBJLoader based on the same class here because we need a special
     * material file (for the light blow shader) not the lighting mat.
     */
    private void initAssetsLoaders(){
        assetManager.unregisterLoader(OBJLoader.class);
        assetManager.registerLoader(MyOBJLoader.class,"obj");
    }
    
    private void initMapViewPort(){
        map_cam = cam.clone();
        float aspect = (float) map_cam.getWidth() / map_cam.getHeight();
        float frustumSize = 1f;
        map_cam.setFrustum(-1000, 1000, -aspect * frustumSize, aspect * frustumSize, frustumSize, -frustumSize);
        map_cam.setParallelProjection(true);
        MapViewPort = renderManager.createMainView("MapView", map_cam);
        MapViewPort.setClearFlags(true, true, true);
        MapViewPort.setBackgroundColor(ColorRGBA.Black);
        
        /*Camera cam2 = getCamera().clone();
        cam2.setViewPort(0f, 0.5f, 0f, 1f);
        cam2.setLocation(new Vector3f(-0.10947256f, 1.5760219f, 4.81758f));
        cam2.setRotation(new Quaternion(0.0010108891f, 0.99857414f, -0.04928594f, 0.020481428f));

        ViewPort2 = getRenderManager().createMainView("Bottom Left", cam2);
        ViewPort2.setClearFlags(true, true, true);*/
    }
    
    private void initProgressBar(){
        //setting up progress bar
        progr.start();
        /*Runnable tsk = new Runnable()
        {
           public void run() {
               progr.start();
           }

        };*/
        //RequestProcessor.getDefault().post(tsk);
    }

    /**
     * dont update anything here, the statemanager does that for us
     * @param tpf
     */
    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);
        
        //we have to do it here because of buggy behaviour of statsState
        if(statsDarken){
            this.setStatsStateDark(false);
            statsDarken = false;
        }

        if(startstate.isInitialized() && TreeTopComp!=null && MARSTopComp!=null && startstateinit==false){// little hack to allow the starting of a config only when the startstate was initialized
            MARSTopComp.allowStateInteraction();
            startstateinit = true;
        }
       
        /*if (load) {//we will be loading,switching appstates
            //this.setProgress(0.5f, "dfsdfsdf");
            //System.out.println("we are loading!!!");
            if (simStateFuture != null && simStateFuture.isDone()) {//cleanup
                System.out.println("simStateFuture is done!!!!");
                nifty.gotoScreen("end");
                nifty.exit();
                guiViewPort.removeProcessor(niftyDisplay);
                load = false;
            }
        }*/
    }

    /**
     * we dont render(custom) anything at all. we aren't crazy.
     * @param rm
     */
    @Override
    public void simpleRender(RenderManager rm) {
    }

    @Override
    public void stop() {
        //make sure to release ros connection
        simStateFuture = this.enqueue(new Callable() {
            public Boolean call() throws Exception {
                if(stateManager.getState(SimState.class) != null){
                    SimState simState = (SimState)stateManager.getState(SimState.class);
                    simState.disconnectFromServer();
                    while(simState.getIniter().ServerRunning()){
                        
                    }
                }
                return true;
            }
        });
        /*try {
            //wait till ros killed
            Object obj = simStateFuture.get(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(MARS_Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MARS_Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            Logger.getLogger(MARS_Main.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        super.stop();
    }
    
    /**
     * 
     */
    public void startSimState(){
        endStart();
        /*Element element = nifty.getScreen("loadlevel").findElementByName("loadingtext");
        textRenderer = element.getRenderer(TextRenderer.class);
        progressBarElement = nifty.getScreen("loadlevel").findElementByName("progressbar");
        nifty.gotoScreen("loadlevel");
        load = true;*/
        simStateFuture = this.enqueue(new Callable() {
            public Void call() throws Exception {
                //SimState simstate = new SimState(view,configManager);   
                SimState simstate = new SimState(MARSTopComp,TreeTopComp,MARSMapComp,MARSLogComp,configManager);
                //viewPort.attachScene(simstate.getRootNode());
                //ViewPort2.attachScene(simstate.getRootNode());
                simstate.setMapState(mapstate);
                stateManager.attach(simstate);
                return null;
            }
        });
    }
    
    private void endStart(){
        Future startStateFuture = this.enqueue(new Callable() {
            public Void call() throws Exception {
                stateManager.getState(StartState.class).setEnabled(false);
                return null;
            }
        });
    }
    
    /**
     *
     * @param view
     */
    @Deprecated
    public void setView(MARSView view){
        this.view = view;
    }

     /**
      *
      * @return
      */
    @Deprecated
    public MARSView getView() {
        return view;
    }
    
    /**
     *
     * @param view
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

    public void setMARSMapComp(MARSMapTopComponent MARSMapComp) {
        this.MARSMapComp = MARSMapComp;
    }

    public MARSMapTopComponent getMARSMapComp() {
        return MARSMapComp;
    }

    public MARSLogTopComponent getMARSLogComp() {
        return MARSLogComp;
    }

    public void setMARSLogComp(MARSLogTopComponent MARSLogComp) {
        this.MARSLogComp = MARSLogComp;
    }

    public void setMARSTopComp(MARSTopComponent MARSTopComp) {
        this.MARSTopComp = MARSTopComp;
    }

    public MARSTopComponent getMARSTopComp() {
        return MARSTopComp;
    }

     /**
      * 
      * @return
      */
    public AppSettings getSettings(){
        return settings;
    }

    /**
     *
     * @return
     */
    public ChaseCamera getChaseCam(){
        return stateManager.getState(SimState.class).getChaseCam();
    }
    
    /**
     * 
     * @param guiFont
     */
    public void setGuiFont(BitmapFont guiFont) {
        this.guiFont = guiFont;
    }
    
    /**
     * 
     * @deprecated
     */
    @Deprecated
    public void initNifty(){
        assetManager.registerLocator("Assets/Interface", FileLocator.class);
        assetManager.registerLocator("Assets/Icons", FileLocator.class);
        niftyDisplay = new NiftyJmeDisplay(assetManager,
                inputManager,
                audioRenderer,
                guiViewPort);
        nifty = niftyDisplay.getNifty();
 
        //nifty.fromXml("nifty_loading.xml", "start", this);
        //nifty.fromXml("nifty_loading.xml", "start");
        nifty.fromXml("nifty_energy_popup.xml", "start");
        
        //set logging to less spam
        Logger.getLogger("de.lessvoid.nifty").setLevel(Level.SEVERE); 
        Logger.getLogger("NiftyInputEventHandlingLog").setLevel(Level.SEVERE); 

        guiViewPort.addProcessor(niftyDisplay);
    }
    
    /**
     * 
     */
    @Override
    public void onStartScreen() {
    }
 
    /**
     * 
     */
    @Override
    public void onEndScreen() {
    }
    
    /**
     * 
     * @param nifty
     * @param screen
     */
    @Override
    public void bind(Nifty nifty, Screen screen) {
        //progressBarElement = nifty.getScreen("loadlevel").findElementByName("progressbar");
        this.nifty = nifty;
    }
 
    // methods for Controller
    /**
     * 
     * @param inputEvent
     * @return
     */
    @Override
    public boolean inputEvent(final NiftyInputEvent inputEvent) {
        return false;
    }
 
    /**
     * 
     * @param nifty
     * @param screen
     * @param elmnt
     * @param prprts
     * @param atrbts
     */
    @Override
    public void bind(Nifty nifty, Screen screen, Element elmnt, Properties prprts, Attributes atrbts) {
        //progressBarElement = elmnt.findElementByName("progressbar");
    }
    
    /**
     * 
     * @param prprts
     * @param atrbts
     */
    @Override
    public void init(Properties prprts, Attributes atrbts) {
    }
    
    /**
     * 
     * @param auv
     * @param x
     * @param y
     */
    public void setHoverMenuForAUV(final AUV auv, final int x, final int y){
        simStateFuture = this.enqueue(new Callable() {
            public Void call() throws Exception {
                if(stateManager.getState(NiftyState.class) != null){
                    NiftyState niftyState = (NiftyState)stateManager.getState(NiftyState.class);
                    niftyState.setHoverMenuForAUV(auv, x, y);
                }
                return null;
            }
        });
    }

    /**
     * 
     * @param visible
     */
    public void setHoverMenuForAUV(final boolean visible){
        simStateFuture = this.enqueue(new Callable() {
            public Void call() throws Exception {
                if(stateManager.getState(NiftyState.class) != null){
                    NiftyState niftyState = (NiftyState)stateManager.getState(NiftyState.class);
                    niftyState.setHoverMenuForAUV(visible);
                }
                return null;
            }
        });
    }
    
    public void setSpeedMenu(final boolean visible){
        simStateFuture = this.enqueue(new Callable() {
            public Void call() throws Exception {
                if(stateManager.getState(NiftyState.class) != null){
                    NiftyState niftyState = (NiftyState)stateManager.getState(NiftyState.class);
                    niftyState.setSpeedUp(visible);
                }
                return null;
            }
        });
    }
 
    /**
     * 
     * @param getFocus
     */
    public void onFocus(boolean getFocus) {
    }
    
    /**
     * 
     * @param progress
     * @param loadingText
     */
    public void setProgress(final float progress, final String loadingText) {
        //since this method is called from another thread, we enqueue the changes to the progressbar to the update loop thread
        enqueue(new Callable() {
 
            public Object call() throws Exception {
                final int MIN_WIDTH = 32;
                int pixelWidth = (int) (MIN_WIDTH + (progressBarElement.getParent().getWidth() - MIN_WIDTH) * progress);
                progressBarElement.setConstraintWidth(new SizeValue(pixelWidth + "px"));
                progressBarElement.getParent().layoutElements();
 
                textRenderer.setText(loadingText);
                return null;
            }
        });
 
    }
    
    /**
     * 
     * @param progress
     * @param loadingText
     */
    public void setProgressWithoutEnq(final float progress, String loadingText) {
        final int MIN_WIDTH = 32;
        int pixelWidth = (int) (MIN_WIDTH + (progressBarElement.getParent().getWidth() - MIN_WIDTH) * progress);
        progressBarElement.setConstraintWidth(new SizeValue(pixelWidth + "px"));
        progressBarElement.getParent().layoutElements();
 
        textRenderer.setText(loadingText);
    }
    
    /**
     * 
     * @return
     */
    public Nifty getNifty() {
        return nifty;
    }
    
    /**
     * 
     */
    public void startSimulation(){
        simStateFuture = this.enqueue(new Callable() {
            public Void call() throws Exception {
                if(stateManager.getState(SimState.class) != null){
                    SimState simState = (SimState)stateManager.getState(SimState.class);
                    simState.startSimulation();
                }
                return null;
            }
        });
    }
    
    /**
     * 
     */
    public void speedUpSimulation(){
        if(speedsCount < speeds.length-1){
            speedsCount++;
            speed = speeds[speedsCount];
            simStateFuture = this.enqueue(new Callable() {
            public Void call() throws Exception {
                if(stateManager.getState(SimState.class) != null){
                    SimState simState = (SimState)stateManager.getState(SimState.class);
                    simState.getMARSSettings().setPhysicsSpeed(speed);
                }
                return null;
            }
            });
        }
        setSpeedMenu(true);
    }
    
        /**
     * 
     */
    public void speedDownSimulation(){
        if(speedsCount > 0){
            speedsCount--;
            speed = speeds[speedsCount];
            simStateFuture = this.enqueue(new Callable() {
            public Void call() throws Exception {
                if(stateManager.getState(SimState.class) != null){
                    SimState simState = (SimState)stateManager.getState(SimState.class);
                    simState.getMARSSettings().setPhysicsSpeed(speed);
                }
                return null;
            }
        });
        }
        setSpeedMenu(true);
    }
    
    public void defaultSpeedSimulation(){
        speedsCount = 3;
        speed = speeds[speedsCount];
        simStateFuture = this.enqueue(new Callable() {
            public Void call() throws Exception {
                if(stateManager.getState(SimState.class) != null){
                    SimState simState = (SimState)stateManager.getState(SimState.class);
                    simState.getMARSSettings().setPhysicsSpeed(speed);
                }
                return null;
            }
        });
        setSpeedMenu(true);
    }
    
    public void setSpeed(float speed){
        this.speed = speed;
        setSpeedMenu(true);
    }

    public float getSpeed() {
        return speed;
    }

    /**
     * 
     */
    public void pauseSimulation(){
        simStateFuture = this.enqueue(new Callable() {
            public Void call() throws Exception {
                if(stateManager.getState(SimState.class) != null){
                    SimState simState = (SimState)stateManager.getState(SimState.class);
                    simState.pauseSimulation();
                }
                return null;
            }
        });
    }
        
    /**
     * 
     */
    public void restartSimulation(){
        simStateFuture = this.enqueue(new Callable() {
            public Void call() throws Exception {
                if(stateManager.getState(SimState.class) != null){
                    SimState simState = (SimState)stateManager.getState(SimState.class);
                    simState.restartSimulation();
                }
                return null;
            }
        });
    }
    
    /**
     * 
     * @return
     */
    public ViewPort getMapViewPort(){
        return MapViewPort;
    }
    
    public ViewPort getViewPort2(){
        return ViewPort2;
    }

    /**
     * 
     * @return
     */
    public MapState getMapstate() {
        return mapstate;
    }
    
    @Override
    public FlyByCamera getFlyByCamera(){
        return advFlyCam;
    }
    
    /**
     * 
     * @return
     */
    public Camera getMapCamera(){
        return map_cam;
    }
    
    /**
     * 
     * @param darken
     */
    public void setStatsStateDark(boolean darken){
        //we dont want a dark underlay in the stats
        if(stateManager.getState(StatsAppState.class) != null){
            StatsAppState statsState = (StatsAppState)stateManager.getState(StatsAppState.class);
            statsState.setDarkenBehind(darken);
        }
    }
    
    /**
     * 
     */
    public void restartSimState(){
        simStateFuture = this.enqueue(new Callable() {
            public Void call() throws Exception {
                if(stateManager.getState(BulletAppState.class) != null){
                    BulletAppState bulletAppState = (BulletAppState)stateManager.getState(BulletAppState.class);
                    bulletAppState.setEnabled(false);
                    stateManager.detach(bulletAppState);
                }
                if(stateManager.getState(MapState.class) != null){
                    MapState mapState = (MapState)stateManager.getState(MapState.class);
                    //mapState.setEnabled(false);
                    mapState.clear();
                }
                if(stateManager.getState(SimState.class) != null){
                    SimState simState = (SimState)stateManager.getState(SimState.class);
                    simState.setEnabled(false);
                    stateManager.detach(simState);
                }
                return null;
            }
        });
        startSimState();
    }
    
    public void setConfigName(String configName){
        configManager.setConfig(configName);
    }
    
    public void setProgressHandle(ProgressHandle progr){
        //this.progr = progr;
    }
}