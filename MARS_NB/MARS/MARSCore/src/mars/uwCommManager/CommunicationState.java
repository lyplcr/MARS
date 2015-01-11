
package mars.uwCommManager;

import mars.uwCommManager.threading.CommunicationMultiPathSimulator;
import mars.uwCommManager.threading.CommunicationExecutorRunnable;
import mars.uwCommManager.helpers.CommunicationComputedDataChunk;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.system.Timer;
import com.jme3.system.lwjgl.LwjglSmoothingTimer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import mars.MARS_Main;
import mars.auv.AUV;
import mars.auv.AUV_Manager;
import mars.core.CentralLookup;
import mars.sensors.CommunicationDevice;
import mars.sensors.CommunicationMessage;
import mars.states.MapState;
import mars.states.SimState;
import mars.uwCommManager.graphics.CommOnMap;
import mars.uwCommManager.helpers.DistanceTrigger;
import static mars.uwCommManager.options.CommOptionsConstants.*;
import mars.uwCommManager.threading.CommunicationDistanceComputationRunnable;

/**
 * Entrypoint of the communications module.
 * @version 0.2
 * @author Jasper Schwinghammer
 */
public class CommunicationState extends AbstractAppState {
    /**
     * Reference to the core class of the project
     */
    private MARS_Main app = null;
    
    /**
     * This queue will be used to store all messages until they are processed
     */
    private ConcurrentLinkedQueue<CommunicationMessage> msgQueue = new ConcurrentLinkedQueue<CommunicationMessage>();

    /**
     * The Runnable that is used to merge all byte arrays back to one messages
     */
    private CommunicationMultiPathSimulator multiPathModule;
    /**
     * The Runnable that is used to check for possible ways from one AUV to another
     */
    private CommunicationDistanceComputationRunnable distanceTraceModule;
    /**
     * Map the AUV to its runnable
     */
    private Map<String,CommunicationExecutorRunnable> auvProcessMap;
    /**
    * The executor for multitasking
    */
    private ScheduledThreadPoolExecutor executor;
    /**
     * Used to determine how many threads the executor should use
     */
    private static int threadCount;
    /**
     * How many ticks per secound should the runnables have
     */
    public static final int RESOLUTION = 30;
    
    /**
     * The visualization class for the minimap
     */
    private CommOnMap commOnMap = null;
    
    /**
     * 
     */
    private boolean commOnMapActive = false;
    

//------------------------------- INIT -----------------------------------------
    /**
     * initialize all basic stuff.
     * at the end the object is added to the CentralLookup
     * @since 0.1
     * @param stateManager the stateManager of MARS
     * @param app the main application
     */
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app); 
        if(app instanceof MARS_Main){
            app = (MARS_Main)app;
        }
        
        //load settings
        if(!loadAndInitPreferenceListeners()) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,"Failed to load communications config");
        }
        //Init the threadpool
        executor = new ScheduledThreadPoolExecutor(threadCount);
        //prepare and start the runnables for multithreading
        auvProcessMap = new HashMap();
        if (!initRunnables(CentralLookup.getDefault().lookup(SimState.class).getAuvManager())) {
            System.out.println("Something went wrong while initializing the communications minimap visualization " + CentralLookup.getDefault().lookup(AUV_Manager.class));
        }
        
        
        commOnMap = new CommOnMap(commOnMapActive);
        if(!commOnMap.init(app.getStateManager().getState(MapState.class), 
                CentralLookup.getDefault().lookup(SimState.class).getAuvManager(), CentralLookup.getDefault().lookup(SimState.class).getMARSSettings())) {
            System.out.println("Something went wrong while initializing the communications minimap visualization" + app.getStateManager().getState(MapState.class) + " "  +CentralLookup.getDefault().lookup(AUV_Manager.class)+ " "+CentralLookup.getDefault().lookup(SimState.class).getMARSSettings());
        }
        
        //Init done, add to centrallookup
        CentralLookup.getDefault().add(this);
    }
    
    
    /**
     * @since 0.2
     * Init all runnables for multithreading
     * @param auvManager the AUV_Manager
     * @return if the initialization was successful
     */
    private boolean initRunnables(final AUV_Manager auvManager) {
        if(auvManager == null) return false;
        
        HashMap<String,AUV> auvs = auvManager.getAUVs();
            for ( AUV auv : auvs.values()){
            //Check if the AUV is enabled and has a modem
                if(auv.getAuv_param().isEnabled() && auv.hasSensorsOfClass(CommunicationDevice.class.getName())) {
                    CommunicationExecutorRunnable runnable = new CommunicationExecutorRunnable(5.6f,RESOLUTION);
                    auvProcessMap.put(auv.getName(), runnable);
                    executor.scheduleAtFixedRate(runnable, 1000000, 1000000/RESOLUTION, TimeUnit.MICROSECONDS);
                }
                
            }
        multiPathModule = new CommunicationMultiPathSimulator();
        multiPathModule.init(auvManager, this);
        executor.scheduleAtFixedRate(multiPathModule, 1500000, 1000000/RESOLUTION, TimeUnit.MICROSECONDS);
        distanceTraceModule = new CommunicationDistanceComputationRunnable();
        distanceTraceModule.init(auvManager);
        executor.scheduleAtFixedRate(distanceTraceModule, 500000, 1000000, TimeUnit.MICROSECONDS);
        return true;
    }
    

    
    /**
     * Load the preferences from the options panel
     * @since 0.1
     * @return whether the preferences could be loaded
     */
    private boolean loadAndInitPreferenceListeners() {
        Preferences pref = Preferences.userNodeForPackage(mars.uwCommManager.options.CommunicationConfigurationOptionsPanelController.class);
        if(pref == null) return false;
        threadCount = pref.getInt(OPTIONS_THREADCOUNT_SLIDER, 5);
        
        
        
        pref.addPreferenceChangeListener(new PreferenceChangeListener() {
            @Override
            public void preferenceChange(PreferenceChangeEvent e) {
                //Distance Checkup Event
                if(e.getKey().equals(OPTIONS_DISTANCE_CHECKUP_CHECKBOX)) {
                    //DO WE STILL USE THIS? NEED TO BE SETUP AGAIN FOR NEW SYSTEM
                }//Distance Checkup Event closed
                
                //Thread Slider Event
                if(e.getKey().equals(OPTIONS_THREADCOUNT_SLIDER)) {
                    threadCount = Integer.parseInt(e.getNewValue());
                    executor.setCorePoolSize(threadCount);
                }//Thread Slider Event closed
                
                //Show Range Event
                if(e.getKey().equals(OPTIONS_SHOW_MINIMAP_RANGE_CHECKBOX)){
                    commOnMapActive = Boolean.parseBoolean(e.getNewValue());
                    if(!(commOnMap == null)) commOnMap.setActive(commOnMapActive);
                }
            }
        });
        return true;
    }

    
//---------------------------END INIT-------------------------------------------    
    
    /**
     * update loop, called by MARS Mainthread only
     * @since 0.1
     * @param tpf time since last frame
     */
    @Override
    public void update(final float tpf) {
        
        commOnMap.update(tpf);

        
        
        //TESTCODE BEGIN
        AUV_Manager auvManager = CentralLookup.getDefault().lookup(SimState.class).getAuvManager();
        
        if(auvManager != null) {
            HashMap<String,AUV> auvs = auvManager.getAUVs();
            
            for ( AUV auv : auvs.values()){
            //Check if the AUV is enabled and has a modem
                if(auv.getAuv_param().isEnabled() && auv.hasSensorsOfClass(CommunicationDevice.class.getName())) {
                    //Get the modem(s)
                    ArrayList uwmo = auv.getSensorsOfClass(CommunicationDevice.class.getName());
                    //Iterate through the modems and send the msg to everyone;
                    Iterator it = uwmo.iterator();
                    while(it.hasNext()){
                        CommunicationDevice mod = (CommunicationDevice)it.next();
                        mod.update(tpf);
                    }
                }
            }
        }
        //TESTCODE END
        
        
        
        //should not take too much time to dispatch all messages, we use
        // this timer to stop in case of too many messages
        Timer timer = new LwjglSmoothingTimer();
        float time = 0f;
        //int counter = 0;
        while(true) {
            time += timer.getTimePerFrame();
            if(time >= 1f/60f) break;
            CommunicationMessage msg = msgQueue.poll();
            if(msg == null) break;

            /*
            PROCESS THE MESSAGE 
            */
            //Check if the AUV allready has a Runnable
            CommunicationExecutorRunnable e1 = auvProcessMap.get(msg.getAuvName());
            if(e1 ==null ) {
                //if not create a new one
                CommunicationExecutorRunnable runnable = new CommunicationExecutorRunnable(5.6f,RESOLUTION);
                auvProcessMap.put(msg.getAuvName(), runnable);
                executor.scheduleAtFixedRate(runnable, 1000000, 1000000/RESOLUTION, TimeUnit.MICROSECONDS);
                e1 = runnable;
            }
            //Check if there are any distanceTriggers for the current AUV and load them
            List<DistanceTrigger> e2 = distanceTraceModule.getDistanceTriggerMap().get(msg.getAuvName());
            if(e2 != null) e1.setDistanceTriggers(e2);
            e1.assignMessage(msg);
            
            //Get the messages from the runnables and merge them in the multiPathModule
            for(Map.Entry<String, CommunicationExecutorRunnable> entr: auvProcessMap.entrySet()) {
                List<CommunicationComputedDataChunk> chunks = entr.getValue().getComputedMessages();
                if(!chunks.isEmpty()) {
                    multiPathModule.enqueueMsges(chunks);
                }
            }
        }
    }
    
    
    /**
     * Add a message to the queue
     * @since 0.1
     * @param msg The Message that should be processed
     */
    public void putMsg(CommunicationMessage msg) {
        this.msgQueue.add(msg);
    }
    
    
    
    /**
     * cleanup and kill the executer
     */
    @Override
    public void cleanup() {
      super.cleanup();
      executor.shutdown();
    }
 
    @Override
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        if(enabled){
        } else {
        }
    }
    
    /**
     * 
     * @return the current value of threadCount
     */
    public static int getThreadCount() {
        return threadCount;
    }
    
    
    

}
