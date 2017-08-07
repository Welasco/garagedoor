/**
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *  Garage Door Push Button
 *
 *  Author: Andre Hass
 *
 *  Date: 2017-04-30
 */

preferences {
    input("hostaddress", "text", title: "IP Address for Server:", description: "Ex: 10.0.0.12 or 192.168.0.4 (no http://)")
    input("hostport", "number", title: "Port of Server", description: "port")
}

metadata {
	definition (name: "GarageDoorButton", namespace: "GarageDoor", author: "Victor Santana") {
		capability "Actuator"
		capability "Switch"
		capability "Momentary"
		capability "Sensor"
	}

	// simulator metadata
	simulator {
	}

	// UI tile definitions
	tiles {
		standardTile("switch", "device.switch", width: 2, height: 2, canChangeIcon: true) {
			state "off", label: 'off', action: "on", icon: "st.Transportation.transportation14", backgroundColor: "#ffffff", nextState: "Turning on"
			state "on", label: 'on', action: "off", icon: "st.Transportation.transportation14", backgroundColor: "#00A0DC"
		}
		main "switch"
		details "switch"
	}
}

def parse(String description) {
}

def physicalgraph.device.HubAction push() {
	sendEvent(name: "switch", value: "on", isStateChange: true, displayed: false)
    sendEvent(name: "switch", value: "off", isStateChange: true, displayed: false)
    return sendRaspberryCommand("garagedoor")
	
	//sendEvent(name: "momentary", value: "pushed", isStateChange: true)
}


def on() {
	push()
}

def off() {
	push()

}

def physicalgraph.device.HubAction sendRaspberryCommand(String command) {

    log.debug "GarageDoor - command: $command"
    if(settings.hostaddress && settings.hostport){
		
        def host = settings.hostaddress
		def port = settings.hostport

		def path = "/api/$command"

		def headers = [:] 
		headers.put("HOST", "$host:$port")
		headers.put("Content-Type", "application/json")

		log.debug "GarageDoor - The Header is $headers"

		def method = "GET"

		try {
			def hubAction = new physicalgraph.device.HubAction(
				method: method,
				path: path,
				//body: json,
				headers: headers,
			)

			log.debug hubAction
			hubAction
            return hubAction
		}
		catch (Exception e) {
			log.debug "GarageDoor - Hit Exception $e on $hubAction"
		}
	}
	else{
		
		log.debug "GarageDoor - RespberryPI IP address and port not set!"
	}
}