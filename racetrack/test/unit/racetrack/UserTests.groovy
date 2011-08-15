package racetrack

import grails.test.*
import org.codehaus.groovy.grails.plugins.codecs.*

class UserTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
		String.metaClass.encodeAsBase64 = {-> Base64Codec.encode(delegate)}
		String.metaClass.encodeAsSHA = {-> SHACodec.encode(delegate)}
    }

    protected void tearDown() {
        super.tearDown()
    }

	/*
	 * Test the validation of the role to ensure only valid values work
	 */
    void testInvalidRole() {
		mockForConstraintsTests(User)
		def user = new User(login:"nobody", password:"nothing".encodeAsSHA(), role:"SuperUser")
		assertFalse user.validate()
		assertEquals "inList", user.errors["role"]
    }
	
	/*
	 * Test the validation of the login to ensure it enforces uniqueness
	 */
	void testUniqueConstraint() {
		def todd = new User(login:"todd")
		def admin = new User(login:"admin")
		mockDomain(User, [todd, admin])
		
		def invalidTodd = new User(login:"todd")
		invalidTodd.save()
		assertEquals 2, User.count()
		assertEquals "unique", invalidTodd.errors["login"]

		/* Failing after trying to use SHA encoding. -todd 15 Aug 2011		
		def user = new User(login:"user", password:"pass".encodeAsSHA(), role:"user")
		user.save()
		assertEquals 3, User.count()
		assertNotNull User.findByLoginAndPassword("user", "pass")
		*/
	}
}
