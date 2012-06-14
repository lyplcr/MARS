/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.gui;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import mars.KeyConfig;

/**
 * This is a TreeModel for the JTree
 * @author Thomas Tosik
 */
public class KeyConfigModel implements TreeModel{

    private final KeyConfig keyConfig;
    private ArrayList<TreeModelListener> treeModelListeners = new ArrayList<TreeModelListener>();
            
    public KeyConfigModel(KeyConfig keyConfig) {
        this.keyConfig = keyConfig;
    }

    public void removeTreeModelListener(TreeModelListener l) {
        treeModelListeners.remove(l);
    }

    public boolean isLeaf(Object node) {
        if(node instanceof KeyConfig){
            return false;
        }else if(node instanceof HashMap){
            return false;
        }else if(node instanceof List){
            return false;
        }else if(node instanceof HashMapWrapper){
            HashMapWrapper hashmapwrap = (HashMapWrapper)node;
            //return isLeaf(hashmapwrap.getUserData());
            return false;
        }else if(node instanceof Vector3f){
            return false;
        }else if(node instanceof ColorRGBA){
            return false;
        }else if(node instanceof LeafWrapper){
            return false;
        }else if(node instanceof Float){
            return true;
        }else if(node instanceof Integer){
            return true;
        }else if(node instanceof Boolean){
            return true;
        }else if(node instanceof String){
            return true;
        }else{
            return true;
        }            
    }

    public Object getRoot() {
        return keyConfig;
    }

    public int getIndexOfChild(Object parent, Object child) {
        System.out.println(parent + "/" + child);
        return 1;
    }

    public int getChildCount(Object parent) {
        if(parent instanceof KeyConfig){
            return keyConfig.getKeys().size();
        }else if(parent instanceof HashMap){
            HashMap<String,Object> hashmap = (HashMap<String,Object>)parent;
            return hashmap.size();
        }else if(parent instanceof List){
            List list = (List)parent;
            return list.size();
        }else if(parent instanceof HashMapWrapper){
            HashMapWrapper hashmapwrap = (HashMapWrapper)parent;
            return getChildCount(hashmapwrap.getUserData());
        }else if(parent instanceof Vector3f){
            return 3;
        }else if(parent instanceof ColorRGBA){
            return 4;
        }else if(parent instanceof Float){
            return 1;
        }else if(parent instanceof Boolean){
            return 1;
        }else if(parent instanceof Integer){
            return 1;
        }else if(parent instanceof LeafWrapper){
            return 1;
        }else if(parent instanceof String){
            return 1;
        }else{
            return 0;
        }
    }

    public Object getChild(Object parent, int index) {
        if(parent instanceof KeyConfig){
            SortedSet<String> sortedset= new TreeSet<String>(keyConfig.getKeys().keySet());
            Iterator<String> it = sortedset.iterator();
            int i = 0;
            while (it.hasNext()) {
                String elem = it.next();
                if(i == index){
                    Object obj = (Object)keyConfig.getKeys().get(elem);
                    return new HashMapWrapper(obj,elem);
                }else if(i > index){
                    return "null";
                }
                i++;
            }
            return "null";
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
                    return "null";
                }
                i++;
            }
            return "null";
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
            return "null";
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
            return "null";
        }else if(parent instanceof HashMapWrapper){
            HashMapWrapper hashmapwrap = (HashMapWrapper)parent;
            return getChild(hashmapwrap.getUserData(), index); 
        }else if(parent instanceof LeafWrapper){
            LeafWrapper leafWrapper = (LeafWrapper)parent;
            return getChild(leafWrapper.getUserData(), index); 
        }else if(parent instanceof Float){
            return (Float)parent;
        }else if(parent instanceof Boolean){
            return (Boolean)parent;
        }else if(parent instanceof Integer){
            return (Integer)parent;
        }else if(parent instanceof String){
            return (String)parent;
        }else{
            return "test: " + index;
        }
    }

    public void addTreeModelListener(TreeModelListener l) {
        treeModelListeners.add(l);
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
        System.out.println("*** valueForPathChanged : "
                           + path + " --> " + newValue);
        
        //save the new value
        //saveValue(path, path, newValue);
        
        //dont forget to tell the listeners that something has changed
        Iterator iter = treeModelListeners.iterator();
        while(iter.hasNext() ) {
            TreeModelListener treeModelListener = (TreeModelListener)iter.next();
            treeModelListener.treeNodesChanged(new TreeModelEvent(this, path));
            treeModelListener.treeStructureChanged(new TreeModelEvent(this, path));
        }
    }
    
    private void saveValue(TreePath originalPath, TreePath path, Object value){
        Object obj = path.getLastPathComponent();
        if(!(obj instanceof HashMapWrapper)){
            saveValue(originalPath,path.getParentPath(),value);
        }else{
            HashMapWrapper hasher = (HashMapWrapper)obj;
            if(hasher.getUserData() instanceof LeafWrapper){
                saveValue(originalPath,path.getParentPath(),value);
            }else if(hasher.getUserData() instanceof Float || hasher.getUserData() instanceof Integer || hasher.getUserData() instanceof String || hasher.getUserData() instanceof Boolean){
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
                keyConfig.updateState(path);
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
                keyConfig.updateState(path);
            }else if(hasher.getUserData() instanceof ColorRGBA){
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
                keyConfig.updateState(path);    
            }
        }
    }
   
}