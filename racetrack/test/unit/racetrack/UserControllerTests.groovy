package racetrack

import grails.test.*
import org.codehaus.groovy.grails.plugins.codecs.*

class UserControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
		String.metaClass.encodeAsBase64 = {-> Base64Codec.encode(delegate)}
		String.metaClass.encodeAsSHA = {-> SHACodec.encode(delegate)}
    }

    protected void tearDown() {
        super.tearDown()
    }

	/*
	 * Test to ensure that the index redirects to the user list
	 */
    void testIndex() {
		controller.index()
		assertEquals "list", controller.redirectArgs["action"]
    }
	
	/*
	 * Test the show action to ensure that it shows the 2nd user
	 */
	void testShow(){
		def jdoe = new User(login:"jdoe")
		def suziq = new User(login:"suziq")
		mockDomain(User, [jdoe, suziq])
		controller.params.id = 2
		def map = controller.show()
		assertEquals "suziq", map.userInstance.login
	}
	
	/*
	 * Test the authentication to ensure it checks the password
	 */
	void testAuthenticate(){
		def jdoe = new User(login:"jdoe", password:"password".encodeAsSHA())
		mockDomain(User, [jdoe])
		
		controller.params.login = "jdoe"
		controller.params.password = "password"
		controller.authenticate()
		
		assertNotNull controller.session.user
		assertEquals "jdoe", controller.session.user.login
		
		controller.params.password = "foo"
		controller.authenticate()
		assertTrue controller.flash.message.startsWith("Sorry, jdoe")
	}
}
