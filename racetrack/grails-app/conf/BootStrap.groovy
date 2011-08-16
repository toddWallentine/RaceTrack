import racetrack.Runner
import racetrack.Race
import racetrack.Registration
import racetrack.User

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
				
				def burner = new Race(name:"Barn Burner", startDate:(new Date() + 120), city:"Cary", state:"NC", distance:10.0, cost:15.0, maxRunners:350)
				burner.save()
				if(burner.hasErrors()){
					println burner.errors
				}
					
				def chase = new Race(name:"Race for the Chase",	startDate:(new Date() + 150), city:"Duck", state:"NC", distance:5.0, cost:25.0, maxRunners:350)
				chase.save()
				if(chase.hasErrors()){
					println chase.errors
				}
				
				def reg = new Registration(paid: false, runner:kyle, race:wildRun)
				reg.save()
				if(reg.hasErrors()) {
					println reg.errors
				}
				
				def admin = new User(login:"admin", password:"wordpass", role:"admin")
				admin.save()
				if(admin.hasErrors()) {
					println admin.errors
				}
				
				def todd = new User(login:"todd", password:"tool13", role:"user")
				todd.save()
				if(todd.hasErrors()) {
					println todd.errors
				}
				
				break;

			case "production":
				break;
		}
	}
	def destroy = {
	}
}
