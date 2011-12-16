/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.xml;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import mars.KeyConfig;
import mars.MARS_Settings;
import mars.PhysicalEnvironment;
import mars.auv.AUV;
import mars.auv.BasicAUV;
import mars.simobjects.SimObject;

/**
 * This Class is responsible for reading and writing java objects to an config file
 * as an xml file
 * @author Thomas Tosik
 */
public class XML_JAXB_ConfigReaderWriter {
    /**
     * 
     * @return
     */
    public static ArrayList loadSimObjects(){
        ArrayList arrlist = new ArrayList();
        FilenameFilter filter = new FilenameFilter() {
                    public boolean accept(File dir, String s) {
                        return s.toLowerCase().endsWith( ".xml" );
                    }           
        };
        File dir = new File("./xml/simobjects");
        
        if(dir.isDirectory()){
            File[] files = dir.listFiles(filter);
            for (int i=0; i<files.length; i++) {
                //Get filename of file or directory
                System.out.println(files[i].getName());
                arrlist.add(loadSimObject(files[i]));
            }
            return arrlist;
        }else{
            return arrlist;
        }
    }
    
    /**
     * 
     * @param file
     * @return
     */
    public static SimObject loadSimObject(File file){
        try {
            if(file.exists()){
                JAXBContext context = JAXBContext.newInstance( SimObject.class );
                Unmarshaller u = context.createUnmarshaller();
                //u.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                SimObject simob = (SimObject)u.unmarshal( file );
                //System.out.println(simob.getName());
                return simob;
            }else{
                return null;
            }
        } catch (JAXBException ex) {
            Logger.getLogger(XML_JAXB_ConfigReaderWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public static SimObject loadSimObject(String name){
        try {
            File file = new File("./xml/simobjects/" + name + ".xml");
            if(file.exists()){
                JAXBContext context = JAXBContext.newInstance( SimObject.class );
                Unmarshaller u = context.createUnmarshaller();
                //u.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                SimObject simob = (SimObject)u.unmarshal( file );
                //System.out.println(simob.getName());
                return simob;
            }else{
                return null;
            }
        } catch (JAXBException ex) {
            Logger.getLogger(XML_JAXB_ConfigReaderWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * 
     * @param simob
     */
    public static void saveSimObject(SimObject simob){
        try {
            JAXBContext context = JAXBContext.newInstance( SimObject.class );
            Marshaller m = context.createMarshaller();
            m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            File file = new File( "./xml/simobjects/" + simob.getName() + ".xml" );
            m.marshal( simob, file );
        } catch (JAXBException ex) {
            Logger.getLogger(XML_JAXB_ConfigReaderWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param simobs
     */
    public static void saveSimObjects(ArrayList simobs){
        Iterator iter = simobs.iterator();
        while(iter.hasNext() ) {
            SimObject simob = (SimObject)iter.next();
            saveSimObject(simob);
        }
    }
    
    /**
     * 
     * @return
     */
    public static ArrayList loadAUVs(){
        ArrayList arrlist = new ArrayList();
        FilenameFilter filter = new FilenameFilter() {
                    public boolean accept(File dir, String s) {
                        return s.toLowerCase().endsWith( ".xml" );
                    }           
        };
        File dir = new File("./xml/auvs");
        
        if(dir.isDirectory()){
            File[] files = dir.listFiles(filter);
            for (int i=0; i<files.length; i++) {
                //Get filename of file or directory
                System.out.println(files[i].getName());
                arrlist.add(loadAUV(files[i]));
            }
            return arrlist;
        }else{
            return arrlist;
        }
    }
    
    /**
     * 
     * @param file
     * @return
     */
    public static BasicAUV loadAUV(File file){
        try {
            if(file.exists()){
                JAXBContext context = JAXBContext.newInstance( BasicAUV.class );
                Unmarshaller u = context.createUnmarshaller();
                //u.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                BasicAUV auv = (BasicAUV)u.unmarshal( file );
                //System.out.println(simob.getName());
                return auv;
            }else{
                return null;
            }
        } catch (JAXBException ex) {
            Logger.getLogger(XML_JAXB_ConfigReaderWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public static BasicAUV loadAUV(String name){
        try {
            File file = new File("./xml/auvs/" + name + ".xml");
            if(file.exists()){
                JAXBContext context = JAXBContext.newInstance( BasicAUV.class );
                Unmarshaller u = context.createUnmarshaller();
                //u.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                BasicAUV auv = (BasicAUV)u.unmarshal( file );
                //System.out.println(simob.getName());
                return auv;
            }else{
                return null;
            }
        } catch (JAXBException ex) {
            Logger.getLogger(XML_JAXB_ConfigReaderWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * 
     * @param auv
     */
    public static void saveAUV(BasicAUV auv){
        try {
            JAXBContext context = JAXBContext.newInstance( BasicAUV.class );
            Marshaller m = context.createMarshaller();
            m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            File file = new File( "./xml/auvs/" + auv.getName() + ".xml" );
            m.marshal( auv, file );
        } catch (JAXBException ex) {
            Logger.getLogger(XML_JAXB_ConfigReaderWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param auvs
     */
    public static void saveAUVs(ArrayList auvs){
        Iterator iter = auvs.iterator();
        while(iter.hasNext() ) {
            BasicAUV auv = (BasicAUV)iter.next();
            saveAUV(auv);
        }
    }
    
    /**
     * 
     * @return
     */
    public static MARS_Settings loadMARS_Settings(){
        try {
            File file = new File("./xml/" + "Settings" + ".xml");
            if(file.exists()){
                JAXBContext context = JAXBContext.newInstance( MARS_Settings.class );
                Unmarshaller u = context.createUnmarshaller();
                MARS_Settings mars_settings = (MARS_Settings)u.unmarshal( file );
                //System.out.println(simob.getName());
                return mars_settings;
            }else{
                return null;
            }
        } catch (JAXBException ex) {
            Logger.getLogger(XML_JAXB_ConfigReaderWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * 
     * @param mars_settings
     */
    public static void saveMARS_Settings(MARS_Settings mars_settings){
        try {
            JAXBContext context = JAXBContext.newInstance( MARS_Settings.class );
            Marshaller m = context.createMarshaller();
            m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            File file = new File( "./xml/" + "Settings" + ".xml" );
            m.marshal( mars_settings, file );
        } catch (JAXBException ex) {
            Logger.getLogger(XML_JAXB_ConfigReaderWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        /**
     * 
     * @return
     */
    public static PhysicalEnvironment loadPhysicalEnvironment(){
        try {
            File file = new File("./xml/" + "PhysicalEnvironment" + ".xml");
            if(file.exists()){
                JAXBContext context = JAXBContext.newInstance( PhysicalEnvironment.class );
                Unmarshaller u = context.createUnmarshaller();
                PhysicalEnvironment pe = (PhysicalEnvironment)u.unmarshal( file );
                //System.out.println(simob.getName());
                return pe;
            }else{
                return null;
            }
        } catch (JAXBException ex) {
            Logger.getLogger(XML_JAXB_ConfigReaderWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * 
     * @param pe 
     */
    public static void savePhysicalEnvironment(PhysicalEnvironment pe){
        try {
            JAXBContext context = JAXBContext.newInstance( MARS_Settings.class );
            Marshaller m = context.createMarshaller();
            m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            File file = new File( "./xml/" + "PhysicalEnvironment" + ".xml" );
            m.marshal( pe, file );
        } catch (JAXBException ex) {
            Logger.getLogger(XML_JAXB_ConfigReaderWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        /**
     * 
     * @return
     */
    public static KeyConfig loadKeyConfig(){
        try {
            File file = new File("./xml/" + "KeyConfig" + ".xml");
            if(file.exists()){
                JAXBContext context = JAXBContext.newInstance( KeyConfig.class );
                Unmarshaller u = context.createUnmarshaller();
                KeyConfig keyconfig = (KeyConfig)u.unmarshal( file );
                //System.out.println(simob.getName());
                return keyconfig;
            }else{
                return null;
            }
        } catch (JAXBException ex) {
            Logger.getLogger(XML_JAXB_ConfigReaderWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * 
     * @param mars_settings
     */
    public static void saveKeyConfig(KeyConfig keyconfig){
        try {
            JAXBContext context = JAXBContext.newInstance( KeyConfig.class );
            Marshaller m = context.createMarshaller();
            m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            File file = new File( "./xml/" + "KeyConfig" + ".xml" );
            m.marshal( keyconfig, file );
        } catch (JAXBException ex) {
            Logger.getLogger(XML_JAXB_ConfigReaderWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
