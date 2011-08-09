import racetrack.Runner
import racetrack.Race
import racetrack.Registration

import grails.util.GrailsUtil

class BootStrap {

	def init = { servletContext ->
		switch(GrailsUtil.environment) {
			case "development":
				def kyle = new Runner(firstName:"Kyle",
				lastName:"Beyer",
				dateOfBirth:new Date(),
				gender:"M",
				address:"123 My Way",
				city:"Manhattan",
				state:"KS",
				zipcode:"66502",
				email:"kyle@theideacenter.org")
				kyle.save()
				if(kyle.hasErrors()) {
					println kyle.errors
				}
				
				def wildRun = new Race(name:"Wild Run", startDate:new Date() + 1, city:"Here", state:"NC", distance:5.0, cost:20.0, maxRunners:20)
				wildRun.save()
				if(wildRun.hasErrors()) {
					println wildRun.errors
				}
				
				def reg = new Registration(paid: false, runner:kyle, race:wildRun)
				reg.save()
				if(reg.hasErrors()) {
					println reg.errors
				}
				
				break;

			case "production":
				break;
		}
	}
	def destroy = {
	}
}
