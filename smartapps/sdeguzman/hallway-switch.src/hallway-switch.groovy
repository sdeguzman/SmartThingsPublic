/**
 *  Hallway Switch
 *
 *  Author: Salvador DeGuzman
 *
 *  Date: 2013-05-01
 */
definition(
	name: "Hallway Switch",
	namespace: "sdeguzman",
	author: "Salvador DeGuzman",
	description: "Turns on/off a collection of lights based on the state of a specific switch.",
	category: "My Apps",
	iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/light_outlet.png",
	iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/light_outlet@2x.png"
)

preferences {
	section("When this switch is turned on, off or dimmed") {
		input "master", "capability.switch", title: "Where?"
	}
	section("Turn on or off all of these switches as well") {
		input "switches", "capability.switch", multiple: true, required: false
	}
}

def installed()
{   
	log.debug "installed"
	subscribe(master, "switch.on", onHandler)
	subscribe(master, "switch.off", offHandler)
}

def updated()
{
	log.debug "updated"
	unsubscribe()
	subscribe(master, "switch.on", onHandler)
	subscribe(master, "switch.off", offHandler)
}

def logHandler(evt) {
	log.debug evt.value
}

def onHandler(evt) {
	log.debug evt.value
	log.debug onSwitches()
	onSwitches()?.on()
}

def offHandler(evt) {
	log.debug evt.value
	log.debug offSwitches()
	offSwitches()?.off()
}

private onSwitches() {
	if(switches && onSwitches) { switches + onSwitches }
	else if(switches) { switches }
	else { onSwitches }
}

private offSwitches() {
	if(switches && offSwitches) { switches + offSwitches }
	else if(switches) { switches }
	else { offSwitches }
}