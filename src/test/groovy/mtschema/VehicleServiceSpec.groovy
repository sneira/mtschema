package mtschema

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest
import org.grails.datastore.mapping.config.Settings
import org.grails.datastore.mapping.multitenancy.resolvers.SystemPropertyTenantResolver
import org.springframework.test.annotation.Rollback

class VehicleServiceSpec extends HibernateSpec implements ServiceUnitTest<VehicleService> {

    @Override
    List<Class> getDomainClasses() { [Vehicle] }

    @Override
    Map getConfiguration() {
        [(Settings.SETTING_MULTI_TENANT_RESOLVER_CLASS): SystemPropertyTenantResolver]
    }

    def setup() {
        System.setProperty(SystemPropertyTenantResolver.PROPERTY_NAME, 'PRUEBA')
        service.vehicleGormService = hibernateDatastore.getService(VehicleGormService)
    }

    void "Prueba de lista"() {
        expect:
        !service.listarVehiculos()
    }

    void "Obtener vehículo"() {
        when:
        Vehicle vehiculo = service.obtenerVehiculo(1)

        then:
        !vehiculo

        when:
        vehiculo = service.crear('Xantia', 1997)

        then:
        vehiculo
        service.obtenerVehiculo(vehiculo.id)
        service.countVehicles() == 1

        when:
        service.borrar(vehiculo.id)

        then:
        service.countVehicles() == old(service.countVehicles()) - 1
    }
}
