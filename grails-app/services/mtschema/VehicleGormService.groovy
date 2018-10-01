package mtschema

import grails.gorm.multitenancy.CurrentTenant
import grails.gorm.services.Service
import grails.gorm.transactions.Transactional

@Service(Vehicle)
@CurrentTenant
abstract class VehicleGormService {

    abstract List<Vehicle> list(Map args)

    abstract Integer count()

    abstract Vehicle find(Serializable id)

    abstract Vehicle save(String model, Integer year)

    abstract Vehicle delete(Serializable id)

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
