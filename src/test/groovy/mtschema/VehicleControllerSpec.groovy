package mtschema

import grails.test.hibernate.HibernateSpec
import grails.testing.web.controllers.ControllerUnitTest
import org.grails.datastore.mapping.config.Settings
import org.grails.datastore.mapping.multitenancy.exceptions.TenantNotFoundException
import org.grails.datastore.mapping.multitenancy.resolvers.SystemPropertyTenantResolver
import spock.lang.Stepwise

@Stepwise
class VehicleControllerSpec extends HibernateSpec implements ControllerUnitTest<VehicleController> {

    @Override
    Map getConfiguration() {
        [(Settings.SETTING_MULTI_TENANT_RESOLVER_CLASS): SystemPropertyTenantResolver]
    }

    VehicleGormService vehicleGormService

    def setup() {
        System.setProperty(SystemPropertyTenantResolver.PROPERTY_NAME, 'PRUEBA')
        vehicleGormService = hibernateDatastore.getService(VehicleGormService)
        controller.vehicleGormService = vehicleGormService
        println "----- Setup: ${vehicleGormService.count()} -----"
    }

    def cleanup() {
        println "----- Cleanup -----"
        System.setProperty(SystemPropertyTenantResolver.PROPERTY_NAME, '')
    }

    void 'Test the index action with no tenant id'() {
        when: 'there is no tenant id'
        System.setProperty(SystemPropertyTenantResolver.PROPERTY_NAME, '')
        controller.index()

        then:
        thrown(TenantNotFoundException)
    }

    void 'Test the index action returns the correct model'() {

        when: 'The index action is executed'
        controller.index()

        then: 'The model is correct'
        !model.vehicleList
        model.vehicleCount == 0
    }

    void 'Test the create action returns the correct model'() {
        when: 'The create action is executed'
        controller.create()

        then: 'The model is correctly created'
        model.vehicle != null
    }

    void 'Test the save action correctly persists an instance'() {

        when: 'The save action is executed with an invalid instance'
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        controller.save('', 1900)

        then: 'The create view is rendered again with the correct model'
        model.vehicle != null
        view == 'create'

        when: 'The save action is executed with a valid instance'
        response.reset()
        controller.save('A5', 2011)

        then: 'A redirect is issued to the show action'
        response.redirectedUrl == '/vehicles/1'
        controller.flash.message != null
        vehicleGormService.count() == 1
    }

    void 'Prueba'() {
        when:
        controller.save('Xantia', 1996)

        then:
        vehicleGormService.count() == 1
    }

    void 'Test that the show action returns 404 for an invalid id'() {
        when: 'The show action is executed with a null domain'
        controller.show(null)

        then: 'A 404 error is returned'
        response.status == 404
    }

    void 'Test the update action performs an update on a valid domain instance'() {
        setup:
        vehicleGormService.save('A5', 2011)
        Vehicle existingVehicle = vehicleGormService.list().first()

        expect:
        vehicleGormService.count() == 1

        when: 'Update is called for a domain instance that doesn\'t exist'
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'PUT'
        controller.update(999, 'A5', 2011)

        then: 'A 404 error is returned'
        response.redirectedUrl == '/vehicles'
        flash.message != null

        when: 'An invalid domain instance is passed to the update action'
        response.reset()
        controller.update(existingVehicle.id, 'A5', 1900)

        then: 'The edit view is rendered again with the invalid instance'
        view == 'edit'
        model.vehicle instanceof Vehicle

        when: 'A valid domain instance is passed to the update action'
        response.reset()
        controller.update(existingVehicle.id, 'A5', 2012)

        then: 'A redirect is issued to the show action'
        response.redirectedUrl == "/vehicles/${existingVehicle.id}"
        flash.message != null
    }

    void 'Test that the delete action deletes an instance if it exists'() {
        when: 'The delete action is called for a null instance'
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'DELETE'
        controller.delete(null)

        then: 'A 404 is returned'
        response.redirectedUrl == '/vehicles'
        flash.message != null

        when: 'A domain instance is created'
        response.reset()
        vehicleGormService.save('A5', 2011)
        Vehicle existingVehicle = vehicleGormService.list().first()

        then:
        vehicleGormService.count() == 1

        when:
        controller.delete(existingVehicle.id)

        then: 'The instance is deleted'
        response.redirectedUrl == '/vehicles'
        flash.message != null
        vehicleGormService.count() == old(vehicleGormService.count()) - 1
    }

}
