package mtschema

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest
import org.grails.datastore.mapping.config.Settings
import org.grails.datastore.mapping.multitenancy.resolvers.SystemPropertyTenantResolver

class VehicleServiceSpec extends HibernateSpec implements ServiceUnitTest<VehicleService> {

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
        service.listarVehiculos().size() == 1

        when:
        service.borrar(vehiculo.id)

        then:
        service.listarVehiculos().size() == old(service.listarVehiculos().size()) - 1
    }
}
