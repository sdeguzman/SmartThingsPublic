/**
* Web Services Tutorial
*
* Copyright 2015 SmartThings
*
* Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
* in compliance with the License. You may obtain a copy of the License at:
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
* on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
* for the specific language governing permissions and limitations under the License.
*
*/
definition(
name: "SightHound Motion Alert",
namespace: "sdeguzman",
author: "SalvadorDeGuzman",
description: "Connect SightHound motion alert to smartthings",
category: "",
iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
oauth: [displayName: "SightHound Motion Alert ", displayLink: "http://localhost:4567"])

preferences {
section ("Allow external service to control these things...") {
input "motionSensor", "capability.motionSensor", multiple: false, required: true
}
}

mappings {
	path("/status") {
		action: [
			GET: "status"
		]
	}
	path("/status/:command") {
		action: [
			PUT: "updateStatus"
		]
	}
}

def status() {
	def resp = [name: motionSensor.displayName, value: motionSensor.currentValue("motion")]
	return resp
}

void updateStatus() {
    def command = params.command
    if (command) {
        if (!motionSensor.hasCommand(command)) {
            httpError(501, "$command is not a valid command for all switches specified")
        } 
        motionSensor."$command"()
    }
}
def installed() {}

def updated() {}