/**
 *  Porch Light
 *
 *  Author: Salvador DeGuzman
 *
 *  Date: 2015-02-26
 */
definition(
	name: "Porch Light",
	namespace: "sdeguzman",
	author: "Salvador DeGuzman",
	description: "Turns on light at sunset, dim at midnight, and off at sunrise.",
	category: "My Apps",
	iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/light_outlet.png",
	iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/light_outlet@2x.png"
)

preferences {
	section("Select the porch light:") {
		input "porchLight", "capability.switchLevel", multiple: false
	}
    
    section("What time would you like to dim?"){
		input(name: "lateNightTime", type:"time")
    	input(name: "dimLevel", type:"number", title: "Percent:")
    }
}

def installed()
{   
	initialize()
}

def updated()
{
	unsubscribe()
    unschedule()
    
    initialize()
}

def initialize() {
    subscribe(location, "sunset", sunsetHandler)
    subscribe(location, "sunrise", sunriseHandler)
    schedule(lateNightTime, lateNightHandler)
}

def sunsetHandler(evt) {
	porchLight.on()
    porchLight.setLevel(100)
    
    log.debug "Porch Light turned ON and set to ${porchLight.currentValue("level")}"
}

def sunriseHandler(evt) {
	porchLight.off()
    log.debug "Porch Light turned OFF"
}

def lateNightHandler(evt) {
	def lightLevel = dimLevel
    porchLight.setLevel(lightLevel)
    log.debug "Porch Light set to ${porchLight.currentValue("level")}"
}