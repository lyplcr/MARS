/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.ros;

/**
 *
 * @author Thomas Tosik
 */
public interface ROS {
    
    public String ros_msg_type = "";
    @Deprecated
    public org.ros.node.Node ros_node = null;
    public MARSNodeMain mars_node = null;
    /*
     * 
     */
    public void initROS();
    /*
     * 
     */
    @Deprecated
    public void initROS(org.ros.node.Node ros_node, String auv_name);
    /*
     * 
     */
    public void initROS(MARSNodeMain ros_node, String auv_name);
    /*
     * 
     */
    public void setROS_MSG_Type(String ros_msg_type);
    /*
     * 
     */
    public String getROS_MSG_Type();
    /*
     * 
     */
    @Deprecated
    public void setROS_Node(org.ros.node.Node ros_node);
    /*
     * 
     */
    public void setROS_Node(MARSNodeMain ros_node);
    /*
     * 
     */
    @Deprecated
    public org.ros.node.Node getROS_Node();
    /*
     * 
     */
    public MARSNodeMain getMARS_Node();
}
