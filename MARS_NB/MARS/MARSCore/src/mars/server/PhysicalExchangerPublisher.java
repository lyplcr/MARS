/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.server;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import mars.MARS_Main;
import mars.MARS_Settings;
import mars.auv.AUV_Manager;
import mars.states.SimState;

/**
 * The main class responsible for publish/sending data of the sensors/actuators
 * from auvs.
 *
 * @author Thomas Tosik <tosik at iti.uni-luebeck.de>
 */
public class PhysicalExchangerPublisher implements Runnable {

    private static final long sleeptime = 2;

    private MARS_Main mars;
    private AUV_Manager auv_manager;
    private MARS_Settings marsSettings;

    //rosjava stuff
    private boolean running = true;

    /**
     *
     * @param mars
     * @param auv_manager
     * @param marsSettings
     */
    public PhysicalExchangerPublisher(MARS_Main mars, AUV_Manager auv_manager, MARS_Settings marsSettings) {
        //set the logging
        try {

            Logger.getLogger(this.getClass().getName()).setLevel(Level.parse(marsSettings.getLoggingLevel()));

            if(marsSettings.getLoggingFileWrite()){
                // Create an appending file handler
                boolean append = true;
                FileHandler handler = new FileHandler(this.getClass().getName() + ".log", append);
                handler.setLevel(Level.parse(marsSettings.getLoggingLevel()));
                // Add to the desired logger
                Logger logger = Logger.getLogger(this.getClass().getName());
                logger.addHandler(handler);
            }

            if(!marsSettings.getLoggingEnabled()){
                Logger.getLogger(this.getClass().getName()).setLevel(Level.OFF);
            }

        } catch (IOException e) {
        }

        this.mars = mars;
        this.auv_manager = auv_manager;
        this.marsSettings = marsSettings;
    }

    /**
     *
     */
    public void shutdown() {
        running = false;
    }

    @Override
    public void run() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "ROS Server running...", "");
        try {
            while (running) {
                Future<Void> fut = mars.enqueue(new Callable<Void>() {
                    public Void call() throws Exception {
                        //if(marsSettings.isROS_Server_publish()){
                        auv_manager.publishSensorsOfAUVs();
                        auv_manager.publishActuatorsOfAUVs();
                        //}
                        return null;
                    }
                });
                Thread.sleep(sleeptime);
            }
        } catch (Exception e) {
        }
    }
}
