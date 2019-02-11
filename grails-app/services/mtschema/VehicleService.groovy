package mtschema

import groovy.transform.CompileStatic

@CompileStatic
class VehicleService {

    VehicleGormService vehicleGormService

    int countVehicles() {
        vehicleGormService.count()
    }

    List<Vehicle> listVehicles() {
        vehicleGormService.list([:])
    }

    Vehicle getVehicle(Long id) {
        vehicleGormService.find(id)
    }

    Vehicle update(Long id, String model, Integer year) {
        vehicleGormService.update(id, model, year)
    }

    Vehicle create(String model, Integer year) {
        vehicleGormService.save(model, year)
    }

    void delete(Long id) {
        vehicleGormService.delete(id)
    }

}
