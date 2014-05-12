package org.mars.auvtree;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.NAME;
import javax.swing.GrayFilter;
import javax.swing.JOptionPane;
import mars.ChartValue;
import mars.Manipulating;
import mars.PhysicalExchanger;
import mars.PropertyChangeListenerSupport;
import mars.accumulators.Accumulator;
import mars.actuators.Actuator;
import mars.actuators.Lamp;
import mars.actuators.Teleporter;
import mars.actuators.Thruster;
import mars.actuators.servos.Servo;
import mars.actuators.visualizer.VectorVisualizer;
import mars.auv.AUV_Parameters;
import mars.sensors.AmpereMeter;
import mars.sensors.Compass;
import mars.sensors.FlowMeter;
import mars.sensors.GPSReceiver;
import mars.sensors.Gyroscope;
import mars.sensors.IMU;
import mars.sensors.PingDetector;
import mars.sensors.PollutionMeter;
import mars.sensors.PressureSensor;
import mars.sensors.RayBasedSensor;
import mars.sensors.Sensor;
import mars.sensors.TemperatureSensor;
import mars.sensors.TerrainSender;
import mars.sensors.UnderwaterModem;
import mars.sensors.VideoCamera;
import mars.sensors.VoltageMeter;
import mars.sensors.WiFi;
import mars.sensors.sonar.Sonar;
import mars.states.SimState;
import org.openide.ErrorManager;
import org.openide.actions.DeleteAction;
import org.openide.modules.InstalledFileLocator;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.Lookups;

/**
 * This class is the presentation for actuators, accumulators and sensors of an
 * auv.
 *
 * @author Christian Friedrich
 * @author Thomas Tosik
 */
public class PhysicalExchangerNode extends AbstractNode implements PropertyChangeListener {

    /**
     * Object which is representated by the node
     */
    private Object obj;
    
    /**
     * Hashmap with paramaeters of object.
     */
    private HashMap params;
    
    /**
     * Hashmap with noise paramaeters of object.
     */
    private HashMap noise;
    
    /**
     * 
     */
    private ArrayList slavesNames;
            
    /**
     * Name of the image file on the harddisk.
     */
    private String icon;
    
    /**
     * Displayname of the node.
     */
    private final String nodeName;

    /**
     * This is constructor is called to create a node for an attachement.
     *
     * @param obj This can be an accumulator, actuator or a sensor
     * @param nodeName
     */
    public PhysicalExchangerNode(Object obj, String nodeName) {
        // initially this node is asumed to be a leaf
        super(Children.LEAF, Lookups.singleton(obj));
        this.nodeName = nodeName;
        this.obj = obj;

        // depending on type of object cast it and get its variables
        if (obj instanceof Accumulator) {
            params = ((Accumulator) (obj)).getAllVariables();
            icon = "battery_charge.png";
        } else if (obj instanceof Actuator) {
            params = ((Actuator) (obj)).getAllVariables();
            noise = ((Actuator) (obj)).getAllNoiseVariables();
            if(obj instanceof Manipulating){
                slavesNames = ((Manipulating) (obj)).getSlavesNames();
            }
            icon = ((Actuator) (obj)).getIcon();
        } else if (obj instanceof Sensor) {
            params = ((Sensor) (obj)).getAllVariables();
            noise = ((Sensor) (obj)).getAllNoiseVariables();
            if(obj instanceof AmpereMeter){
                
            }
            if(obj instanceof Manipulating){
                slavesNames = ((Manipulating) (obj)).getSlavesNames();
            }
            icon = ((Sensor) (obj)).getIcon();
        } else if(obj instanceof AUV_Parameters){
           params = ((AUV_Parameters) (obj)).getAllVariables();
        }
        
        //no icon set, use default one
        if(icon == null){
            if(obj instanceof Sonar){
                icon = "radar.png";
            }else if(obj instanceof Compass){
                icon = "compass.png";
            }else if(obj instanceof VideoCamera){
                icon = "cctv_camera.png";
            }else if(obj instanceof PingDetector){
                icon = "microphone.png";
            }else if(obj instanceof IMU){
                icon = "compass.png";
            }else if(obj instanceof TemperatureSensor){
                icon = "thermometer.png";
            }else if(obj instanceof Gyroscope){
                icon = "transform_rotate.png";
            }else if(obj instanceof PressureSensor){
                icon = "transform_perspective.png";
            }else if(obj instanceof TerrainSender){
                icon = "soil_layers.png";
            }else if(obj instanceof Thruster){
                icon = "thruster_seabotix.png";
            }else if(obj instanceof VectorVisualizer){
                icon = "arrow_up.png";
            }else if(obj instanceof Servo){
                icon = "AX-12.png";
            }else if(obj instanceof UnderwaterModem){
                icon = "speaker-volume.png";
            }else if(obj instanceof WiFi){
                icon = "radio_2.png";
            }else if(obj instanceof Lamp){
                icon = "flashlight-shine.png";
            }else if(obj instanceof GPSReceiver){
                icon = "satellite.png";
            }else if(obj instanceof Lamp){
                icon = "flashlight-shine.png";
            }else if(obj instanceof VoltageMeter){
                icon = "battery_charge.png";
            }else if(obj instanceof AmpereMeter){
                icon = "battery_charge.png";
            }else if(obj instanceof FlowMeter){
                icon = "breeze_small.png";
            }else if(obj instanceof Teleporter){
                icon = "transform_move.png";
            }else if(obj instanceof PollutionMeter){
                icon = "oil-barrel.png";
            }else{//last resort
                icon = "question-white.png";
            }
        }
        
        // create subchilds
        // don't show them currently, because one has to use the property window
        //when you want to activate it you need addtional code:
        //https://blogs.oracle.com/geertjan/entry/no_expansion_key_when_no
        //http://netbeans.dzone.com/nb-dynamic-icons-for-explorer-trees
        /*if (params != null && !params.isEmpty()) {
            setChildren(Children.create(new ParamChildNodeFactory(params), true));
        }*/

        setDisplayName(nodeName);
    }

    /**
     * This method returns the image icon.
     *
     * @param type
     * @return Icon which will be displayed.
     */
    @Override
    public Image getIcon(int type) {
        //if (auv.getAuv_param().isEnabled()) {
            return TreeUtil.getImage(icon);
        //}else{
        //    return GrayFilter.createDisabledImage(TreeUtil.getImage(icon));
        //}
    }

    /**
     * Loads image which is displayed next to a opened node.
     *
     * @param type
     * @return Returns image which is loaded with getImage()
     * @see also TreeUtil.getImage()
     */
    @Override
    public Image getOpenedIcon(int type) {
        return TreeUtil.getImage(icon);
    }

    /**
     * Returns the string which is displayed in the tree. Node name is used
     * here.
     *
     * @return Returns node name.
     */
    @Override
    public String getDisplayName() {
        return nodeName;
    }

    /**
     * This method generates the properties for the property sheet. It adds an
     * property change listener for each displayed property. This is used to
     * update the property sheet when values in an external editor are adjusted.
     *
     * @return Returns instance of sheet.
     */
    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        obj = getLookup().lookup(PhysicalExchanger.class);
        if(obj == null){//i know, its hacky at the moment. should use multiple lookup. or an common interface for all interesting objects
            obj = getLookup().lookup(Accumulator.class);
        }
        if(obj == null){//i know, its hacky at the moment. should use multiple lookup. or an common interface for all interesting objects
            obj = getLookup().lookup(Manipulating.class);
        }
        if(obj == null){//i know, its hacky at the moment. should use multiple lookup. or an common interface for all interesting objects
            obj = getLookup().lookup(AUV_Parameters.class);
        }
        if(obj == null){//i know, its hacky at the moment. should use multiple lookup. or an common interface for all interesting objects
            obj = getLookup().lookup(HashMap.class);
        }
        
        if (params != null) {
            createPropertiesSet(obj,params,"Properties",false,sheet);
        }
        if (noise != null) {
            createPropertiesSet(obj,noise,"Noise",true,sheet);
        }
        if (slavesNames != null) {
            sheet.put(createPropertiesSet(obj,slavesNames,"Slaves",true));
        }
        // add listener to react of changes from external editors (AUVEditor)
        if(params != null){
            ((PropertyChangeListenerSupport) (obj)).addPropertyChangeListener(this);
        }
        return sheet;
    }
    
    private void createPropertiesSet(Object obj, HashMap params, String displayName, boolean expert, Sheet sheet){
        Sheet.Set set;
        if(expert){
            set = Sheet.createExpertSet();
        }else{
            set = Sheet.createPropertiesSet();
        }
        
        set.setDisplayName(displayName);
        set.setName(displayName);
        sheet.put(set);
        
        Iterator<Map.Entry<String, Object>> i = params.entrySet().iterator();
        Property prop;
        String name;
        for (; i.hasNext();) {
            Map.Entry<String, Object> mE = i.next();

            if(mE.getValue() instanceof HashMap){//make a new set 
                Sheet.Set setHM = Sheet.createExpertSet();
                HashMap hasher = (HashMap)mE.getValue();
                Iterator<Map.Entry<String, Object>> ih = hasher.entrySet().iterator();
                for (; ih.hasNext();) {
                    Map.Entry<String, Object> mE2 = ih.next();
                    String namehm = mE.getKey() + mE2.getKey().substring(0, 1).toUpperCase() + mE2.getKey().substring(1);
                    try {
                        Property prophm = new PropertySupport.Reflection(obj, mE2.getValue().getClass(), namehm);
                        // set custom property editor for position and rotation params
                        if (mE2.getValue() instanceof Vector3f) {
                            ((PropertySupport.Reflection) (prophm)).setPropertyEditorClass(Vector3fPropertyEditor.class);
                        } else if (mE2.getValue() instanceof ColorRGBA) {
                            ((PropertySupport.Reflection) (prophm)).setPropertyEditorClass(ColorPropertyEditor.class);
                        }

                        prophm.setName(mE2.getKey());
                        setHM.put(prophm);
                    } catch (NoSuchMethodException ex) {
                        ErrorManager.getDefault();
                    }
                }
                setHM.setDisplayName(mE.getKey());
                setHM.setName(mE.getKey());
                sheet.put(setHM);
            }else if (!mE.getKey().isEmpty()) {
                name = mE.getKey().substring(0, 1).toUpperCase() + mE.getKey().substring(1);
                try {
                    prop = new PropertySupport.Reflection(obj, mE.getValue().getClass(), name);
                    // set custom property editor for position and rotation params
                    if (mE.getValue() instanceof Vector3f) {
                        ((PropertySupport.Reflection) (prop)).setPropertyEditorClass(Vector3fPropertyEditor.class);
                    } else if (mE.getValue() instanceof ColorRGBA) {
                        ((PropertySupport.Reflection) (prop)).setPropertyEditorClass(ColorPropertyEditor.class);
                    }

                    prop.setName(name);
                    set.put(prop);
                } catch (NoSuchMethodException ex) {
                    ErrorManager.getDefault();
                }
            }
        }
        
    }

    private Sheet.Set createPropertiesSet(Object obj, ArrayList params, String displayName, boolean expert){
        Sheet.Set set;
        if(expert){
            set = Sheet.createExpertSet();
        }else{
            set = Sheet.createPropertiesSet();
        }
        
        Property prop;
        String name;
        for (Iterator it = params.iterator(); it.hasNext();) {
            String slaveName = (String)it.next();
            try {
                prop = new PropertySupport.Reflection(obj, String.class, "SlavesNames");
                prop.setName(slaveName);
                set.put(prop);
            } catch (NoSuchMethodException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        /*Iterator<Map.Entry<String, Object>> i = params.entrySet().iterator();
        
        for (; i.hasNext();) {
            Map.Entry<String, Object> mE = i.next();

            if (!mE.getKey().isEmpty()) {
                name = mE.getKey().substring(0, 1).toUpperCase() + mE.getKey().substring(1);
                try {
                    prop = new PropertySupport.Reflection(obj, mE.getValue().getClass(), "getSlavesNames");
                    // set custom property editor for position and rotation params
                    if (mE.getValue() instanceof Vector3f) {
                        ((PropertySupport.Reflection) (prop)).setPropertyEditorClass(Vector3fPropertyEditor.class);
                    } else if (mE.getValue() instanceof ColorRGBA) {
                        ((PropertySupport.Reflection) (prop)).setPropertyEditorClass(ColorPropertyEditor.class);
                    }

                    prop.setName(name);
                    set.put(prop);
                } catch (NoSuchMethodException ex) {
                    ErrorManager.getDefault();
                }
            }
        }*/
        set.setDisplayName(displayName);
        return set;
    }
        
    /**
     * This listerner is called on property changes. It updates the Property
     * Sheet to display adjusted values.
     *
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.fireDisplayNameChange(null, getDisplayName());
        this.fireIconChange();
        if ("Position".equals(evt.getPropertyName()) || "Rotation".equals(evt.getPropertyName())) {
            setSheet(getSheet());
        }
    }
    
    /**
     * This one is overridden to define left click actions.
     *
     * @param popup
     *
     * @return Returns array of Actions.
     */
    @Override
    public Action[] getActions(boolean popup) {
        if(obj instanceof Compass){
            return new Action[]{new DataChartAction(), new ViewCompassAction(),new EnableAction(),SystemAction.get(DeleteAction.class)};
        }else if(obj instanceof VideoCamera){
            return new Action[]{new ViewCameraAction(),new EnableAction(),SystemAction.get(DeleteAction.class)};
        }else if(obj instanceof RayBasedSensor){
            return new Action[]{new SonarPlanarAction(), new SonarPolarAction(),new EnableAction(),SystemAction.get(DeleteAction.class)};
        }else if(obj instanceof UnderwaterModem){
            return new Action[]{new ViewCommunicationAction(),new EnableAction(),SystemAction.get(DeleteAction.class)};
        }else if(obj instanceof ChartValue){
            return new Action[]{new DataChartAction(),new EnableAction(),SystemAction.get(DeleteAction.class)};
        }else{
            return new Action[]{new EnableAction(),SystemAction.get(DeleteAction.class)};
        }
   }
    
    /**
     * Inner class for the actions on right click. Provides action to enable and
     * disable an auv.
     */
    private class EnableAction extends AbstractAction {

        public EnableAction() {
            if(obj instanceof PhysicalExchanger){
                PhysicalExchanger objpe = (PhysicalExchanger) obj;
                if (objpe.getEnabled()) {
                    putValue(NAME, "Disable");
                } else {
                    putValue(NAME, "Enable");
                }
            }  
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //boolean auvEnabled = auv.getAuv_param().isEnabled();
            //auv.getAuv_param().setEnabled(!auvEnabled);
            //propertyChange(new PropertyChangeEvent(this, "enabled", !auvEnabled, auvEnabled));
            JOptionPane.showMessageDialog(null, "Done!");
        }

    }
    
   /**
     * Inner class for the actions on right click. Provides action to enable and
     * disable an auv.
     */
    private class SonarPolarAction extends AbstractAction {

        public SonarPolarAction() {
            putValue(NAME, "View Sonar Data [Polar]");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //propertyChange(new PropertyChangeEvent(this, "enabled", !auvEnabled, auvEnabled));
            /*Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    simState.chaseAUV(auv);
                }
                return null;
            }
            });*/
            JOptionPane.showMessageDialog(null, "Done!");
        }

    }
    
       /**
     * Inner class for the actions on right click. Provides action to enable and
     * disable an auv.
     */
    private class SonarPlanarAction extends AbstractAction {

        public SonarPlanarAction() {
            putValue(NAME, "View Sonar Data [Planar]");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //propertyChange(new PropertyChangeEvent(this, "enabled", !auvEnabled, auvEnabled));
            /*Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    simState.chaseAUV(auv);
                }
                return null;
            }
            });*/
            JOptionPane.showMessageDialog(null, "Done!");
        }

    }
    
           /**
     * Inner class for the actions on right click. Provides action to enable and
     * disable an auv.
     */
    private class DataChartAction extends AbstractAction {

        public DataChartAction() {
            putValue(NAME, "Add Data to Chart");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //propertyChange(new PropertyChangeEvent(this, "enabled", !auvEnabled, auvEnabled));
            /*Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    simState.chaseAUV(auv);
                }
                return null;
            }
            });*/
            JOptionPane.showMessageDialog(null, "Done!");
        }

    }
    
               /**
     * Inner class for the actions on right click. Provides action to enable and
     * disable an auv.
     */
    private class ViewCameraAction extends AbstractAction {

        public ViewCameraAction() {
            putValue(NAME, "View Camera Data");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //propertyChange(new PropertyChangeEvent(this, "enabled", !auvEnabled, auvEnabled));
            /*Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    simState.chaseAUV(auv);
                }
                return null;
            }
            });*/
            JOptionPane.showMessageDialog(null, "Done!");
        }

    }
    
                   /**
     * Inner class for the actions on right click. Provides action to enable and
     * disable an auv.
     */
    private class ViewCompassAction extends AbstractAction {

        public ViewCompassAction() {
            putValue(NAME, "View Compass Data");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //propertyChange(new PropertyChangeEvent(this, "enabled", !auvEnabled, auvEnabled));
            /*Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    simState.chaseAUV(auv);
                }
                return null;
            }
            });*/
            JOptionPane.showMessageDialog(null, "Done!");
        }

    }
    
                       /**
     * Inner class for the actions on right click. Provides action to enable and
     * disable an auv.
     */
    private class ViewCommunicationAction extends AbstractAction {

        public ViewCommunicationAction() {
            putValue(NAME, "View Communication Device");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //propertyChange(new PropertyChangeEvent(this, "enabled", !auvEnabled, auvEnabled));
            /*Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    simState.chaseAUV(auv);
                }
                return null;
            }
            });*/
            JOptionPane.showMessageDialog(null, "Done!");
        }

    }
}
