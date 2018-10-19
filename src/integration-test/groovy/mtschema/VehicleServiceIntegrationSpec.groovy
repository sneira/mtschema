package mtschema

import grails.testing.mixin.integration.Integration
import org.grails.datastore.mapping.multitenancy.resolvers.SystemPropertyTenantResolver
import spock.lang.Specification

@Integration
class VehicleServiceIntegrationSpec extends Specification {

    VehicleService vehicleService

    def setupSpec() {
        System.setProperty(SystemPropertyTenantResolver.PROPERTY_NAME, 'PRUEBA')
    }

    void "Prueba de lista"() {
        expect:
        !vehicleService.listarVehiculos()
    }

    void "Obtener vehículo"() {
        when:
        Vehicle vehiculo = Vehicle.withNewSession {
            vehicleService.obtenerVehiculo(1)
        }

        then:
        !vehiculo

        when:
        vehiculo = vehicleService.crear('Xantia', 1997)

        then:
        vehiculo
        vehicleService.obtenerVehiculo(vehiculo.id)
        vehicleService.listarVehiculos().size() == 1

        when:
        vehicleService.borrar(vehiculo.id)

        then:
        vehicleService.listarVehiculos().size() == 0
    }

}
