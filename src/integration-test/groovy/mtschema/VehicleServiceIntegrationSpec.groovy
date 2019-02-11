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

    void "List vehicles"() {
        expect:
        !vehicleService.listVehicles()
    }

    void "Get vehicle"() {
        when:
        Vehicle vehicle = Vehicle.withNewSession {
            vehicleService.getVehicle(1)
        }

        then:
        !vehicle

        when:
        vehicle = vehicleService.create('Xantia', 1997)

        then:
        vehicle
        vehicleService.getVehicle(vehicle.id)
        vehicleService.listVehicles().size() == 1

        when:
        vehicleService.delete(vehicle.id)

        then:
        vehicleService.listVehicles().size() == 0
    }

}
