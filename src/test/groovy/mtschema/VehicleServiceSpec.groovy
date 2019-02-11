package mtschema

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest
import org.grails.datastore.mapping.config.Settings
import org.grails.datastore.mapping.multitenancy.resolvers.SystemPropertyTenantResolver

class VehicleServiceSpec extends HibernateSpec implements ServiceUnitTest<VehicleService> {

    @Override
    List<Class> getDomainClasses() { [Vehicle] }

    @Override
    Map getConfiguration() {
        [
                (Settings.SETTING_MULTI_TENANT_RESOLVER_CLASS): SystemPropertyTenantResolver,
                'hibernate.flush.mode': 'AUTO'
        ]
    }

    def setup() {
        System.setProperty(SystemPropertyTenantResolver.PROPERTY_NAME, 'PRUEBA')
        service.vehicleGormService = hibernateDatastore.getService(VehicleGormService)
    }

    void "List vehicles"() {
        expect:
        !service.listVehicles()
    }

    void "Get vehicle"() {
        when:
        Vehicle vehicle = service.getVehicle(1)

        then:
        !vehicle

        when:
        vehicle = service.create('Xantia', 1997)

        then:
        vehicle
        service.getVehicle(vehicle.id)
        service.countVehicles() == 1

        when:
        service.delete(vehicle.id)

        then:
        service.countVehicles() == old(service.countVehicles()) - 1
    }
}
