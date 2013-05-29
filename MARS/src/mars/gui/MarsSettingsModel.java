/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.gui;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.tree.TreePath;
import mars.MARS_Settings;

/**
 * This is a TreeModel for the JTree
 * @author Thomas Tosik
 */
public class MarsSettingsModel extends GenericTreeModel{

    private final MARS_Settings settings;
            
    /**
     * 
     * @param settings
     */
    public MarsSettingsModel(MARS_Settings settings) {
        this.settings = settings;
    }

    @Override
    public Object getRoot() {
        return settings;
    }

    @Override
    public int getChildCount(Object parent) {
        int childCount = super.getChildCount(parent);
        if(childCount == 0){
            if(parent instanceof MARS_Settings){
                return settings.getSettings().size();
            }else{
                return childCount;
            }
        }else{
            return childCount;
        }
    }

    @Override
    public Object getChild(Object parent, int index) {
        if(parent instanceof MARS_Settings){
            SortedSet<String> sortedset= new TreeSet<String>(settings.getSettings().keySet());
            Iterator<String> it = sortedset.iterator();
            int i = 0;
            while (it.hasNext()) {
                String elem = it.next();
                if(i == index){
                    Object obj = (Object)settings.getSettings().get(elem);
                    return new HashMapWrapper(obj,elem);
                }else if(i > index){
                    return null;
                }
                i++;
            }
            return null;
        }else if(parent instanceof HashMap){
            HashMap<String,Object> hashmap = (HashMap<String,Object>)parent;
            SortedSet<String> sortedset= new TreeSet<String>(hashmap.keySet());
            Iterator<String> it = sortedset.iterator();
            int i = 0;
            while (it.hasNext()) {
                String elem = it.next();
                if(i == index){
                    Object obj = (Object)hashmap.get(elem);
                    return new HashMapWrapper(obj,elem);
                }else if(i > index){
                    return null;
                }
                i++;
            }
            return null;
        }else if(parent instanceof List){
            List list = (List)parent;
            Collections.sort(list);
            Object object = list.get(index);
            return object.toString();
        }else if(parent instanceof Vector3f){
            Vector3f vec = (Vector3f)parent;
            if(index == 0){
                return new HashMapWrapper(new LeafWrapper(vec.getX()),"X");
            }else if (index == 1){
                return new HashMapWrapper(new LeafWrapper(vec.getY()),"Y");
            }else if (index == 2){
                return new HashMapWrapper(new LeafWrapper(vec.getZ()),"Z");
            }
            return null;
        }else if(parent instanceof ColorRGBA){
            ColorRGBA color = (ColorRGBA)parent;
            if(index == 0){
                return new HashMapWrapper(new LeafWrapper(color.getRed()),"R");
            }else if (index == 1){
                return new HashMapWrapper(new LeafWrapper(color.getGreen()),"G");
            }else if (index == 2){
                return new HashMapWrapper(new LeafWrapper(color.getBlue()),"B");
            }else if (index == 3){
                return new HashMapWrapper(new LeafWrapper(color.getAlpha()),"A");
            }
            return null;
        }else if(parent instanceof HashMapWrapper){
            HashMapWrapper hashmapwrap = (HashMapWrapper)parent;
            return getChild(hashmapwrap.getUserData(), index); 
        }else if(parent instanceof LeafWrapper){
            LeafWrapper leafWrapper = (LeafWrapper)parent;
            return getChild(leafWrapper.getUserData(), index); 
        }else if(parent instanceof Float){
            return (Float)parent;
        }else if(parent instanceof Double){
            return (Double)parent;
        }else if(parent instanceof Boolean){
            return (Boolean)parent;
        }else if(parent instanceof Integer){
            return (Integer)parent;
        }else if(parent instanceof String){
            return (String)parent;
        }else{
            return null;
        }
    }
    
    @Override
    protected void saveValue(TreePath originalPath, TreePath path, Object value){
        Object obj = path.getLastPathComponent();
        if(!(obj instanceof HashMapWrapper)){
            saveValue(originalPath,path.getParentPath(),value);
        }else{
            HashMapWrapper hasher = (HashMapWrapper)obj;
            if(hasher.getUserData() instanceof LeafWrapper){
                saveValue(originalPath,path.getParentPath(),value);
            }else if(hasher.getUserData() instanceof Float || hasher.getUserData() instanceof Double || hasher.getUserData() instanceof Integer || hasher.getUserData() instanceof String || hasher.getUserData() instanceof Boolean){
                Object preObj = (Object)path.getParentPath().getLastPathComponent();
                if(preObj instanceof HashMapWrapper){
                    HashMapWrapper hashwrap = (HashMapWrapper)preObj;
                    if(hashwrap.getUserData() instanceof HashMap){
                        HashMap<String,Object> hashmap = (HashMap<String,Object>)hashwrap.getUserData();
                        hashmap.put(hasher.getName(), value);
                    }/*if(hashwrap.getUserData() instanceof PhysicalExchanger){
                        PhysicalExchanger pe = (PhysicalExchanger)hashwrap.getUserData();
                        pe.getAllVariables().put(hasher.getName(), value);
                    }*///only nessecary when direct variables in PE. But now we have only hashmaps
                    hasher.setUserData(value);
                }
                settings.updateState(path);
            }else if(hasher.getUserData() instanceof Vector3f){
                Vector3f vec = (Vector3f)hasher.getUserData();
                HashMapWrapper preObj = (HashMapWrapper)originalPath.getParentPath().getLastPathComponent();
                LeafWrapper leaf = (LeafWrapper)preObj.getUserData();
                leaf.setUserData((Float)value);
                if(preObj.getName().equals("X")){
                    vec.setX((Float)value);
                }else if(preObj.getName().equals("Y")){
                    vec.setY((Float)value);
                }else if(preObj.getName().equals("Z")){
                    vec.setZ((Float)value);
                }
                settings.updateState(path);
            }else if(hasher.getUserData() instanceof ColorRGBA){
                if(!(originalPath.getLastPathComponent() instanceof Float)){//direct color or leaf?
                    ColorRGBA color = (ColorRGBA)hasher.getUserData();
                    ColorRGBA newColor = (ColorRGBA)value;
                    color.r = (newColor.getRed());
                    color.g = (newColor.getGreen());
                    color.b = (newColor.getBlue());
                    color.a = (newColor.getAlpha());
                    settings.updateState(path);  
                }else{
                    ColorRGBA color = (ColorRGBA)hasher.getUserData();
                    HashMapWrapper preObj = (HashMapWrapper)originalPath.getParentPath().getLastPathComponent();
                    LeafWrapper leaf = (LeafWrapper)preObj.getUserData();
                    leaf.setUserData((Float)value);
                    if(preObj.getName().equals("R")){
                        color.r = ((Float)value);
                    }else if(preObj.getName().equals("G")){
                        color.g = ((Float)value);
                    }else if(preObj.getName().equals("B")){
                        color.b = ((Float)value);
                    }else if(preObj.getName().equals("A")){
                        color.a = ((Float)value);
                    }
                    settings.updateState(path);  
                } 
            }
        }
    }
   
}
