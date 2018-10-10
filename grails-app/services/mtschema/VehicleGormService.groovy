package mtschema

import grails.gorm.multitenancy.CurrentTenant
import grails.gorm.services.Service
import grails.gorm.transactions.Transactional

interface IVehicleGormService {
    List<Vehicle> list(Map args)

    Integer count()

    Vehicle find(Serializable id)

    Vehicle save(String model, Integer year)

    void delete(Serializable id)
}

@Service(Vehicle)
@CurrentTenant
abstract class VehicleGormService implements IVehicleGormService {

    @Transactional
    Vehicle update( Serializable id, String model, Integer year) {
        Vehicle vehicle = find(id)
        if (vehicle != null) {
            vehicle.model = model
            vehicle.year = year
            vehicle.save(failOnError:true)
        }
        vehicle
    }

}
