/**
 *  3-Way Hallway Switch
 *
 *  Author: Salvador DeGuzman
 *
 *  Date: 2013-05-01
 */
definition(
	name: "3-Way Hallway Switch",
	namespace: "sdeguzman",
	author: "Salvador DeGuzman",
	description: "Turns on/off switches",
	category: "My Apps",
	iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/light_outlet.png",
	iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/light_outlet@2x.png"
)

preferences {
	section("These are the switches") {
		input "switches", "capability.switch", multiple: true
	}
}

def installed()
{   
	log.debug "HALLWAYSWITCH - installed"
	subscribe(switches, "switch", switchHandler)
}

def updated()
{
	log.debug "HALLWAYSWITCH - updated"
	unsubscribe()
	subscribe(switches, "switch", switchHandler)
}

def switchHandler(evt) {
	log.debug "HALLWAYSWITCH - ${evt.displayName} turned ${evt.stringValue}"
    if (evt.value == "on") {
        switches.on()
    } else if (evt.value == "off") {
        switches.off()
    }
}