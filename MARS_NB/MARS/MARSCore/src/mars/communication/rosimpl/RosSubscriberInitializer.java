/*
 * Copyright (c) 2015, Institute of Computer Engineering, University of Lübeck
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package mars.communication.rosimpl;

import java.util.logging.Level;
import java.util.logging.Logger;
import mars.actuators.Actuator;
import mars.actuators.thruster.Thruster;
import org.ros.message.MessageListener;

/**
 *
 * @author Fabian Busse
 */
public class RosSubscriberInitializer {

    public static void createSubscriberForActuator(Actuator actuator, AUVConnectionNode node, String auvName) {

        if (actuator instanceof Thruster) {
            final Thruster thruster = (Thruster) actuator;
            node.newSubscriber(auvName + "/" + actuator.getName(), hanse_msgs.sollSpeed._TYPE).addMessageListener(
                    new MessageListener<hanse_msgs.sollSpeed>() {
                        @Override
                        public void onNewMessage(hanse_msgs.sollSpeed message) {
                            thruster.set_thruster_speed((int) message.getData());
                        }
                    }, (actuator.getSimState().getMARSSettings().getROSGlobalQueueSize() > 0) ? actuator.getSimState().getMARSSettings().getROSGlobalQueueSize() : actuator.getRos_queue_listener_size());

            return;
        }

        Logger.getLogger(RosSubscriberInitializer.class.getName()).log(Level.WARNING, "Unable to map actuator " + actuator + " to subscriber!", "");
    }
}