/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.xml;

import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import mars.actuators.Actuator;

/**
 *
 * @author Thomas Tosik
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"unit", "key", "value"})
public class MyHashMapEntryTypeActuators extends MyHashMapEntryType{
    @XmlAttribute
    public String key; 
    
    @XmlAttribute
    public String unit; 
    
    @XmlElement
    //@XmlJavaTypeAdapter(ActuatorAdapter.class)
    public Actuator value;
    
    public MyHashMapEntryTypeActuators() {}
    
    public MyHashMapEntryTypeActuators(Map.Entry<String,Object> e) {
       key = e.getKey();

       if(e.getValue() instanceof HashMapEntry){
            if(((HashMapEntry)e.getValue()).getValue() instanceof Actuator){
                value = (Actuator)((HashMapEntry)e.getValue()).getValue();
            }
            unit = ((HashMapEntry)e.getValue()).getUnit(); 
       }else{
            if(e.getValue() instanceof Actuator){
                value = (Actuator)e.getValue();
            }
       }
    }
    
    public String getKey() {
        return key;
    }
    
    public String getUnit() {
        return unit;
    }

    public Actuator getValue() {
        return value;
    }
    
    public Object getObject() {
        return value;
    }
}