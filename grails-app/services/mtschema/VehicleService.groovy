package mtschema

import grails.gorm.multitenancy.CurrentTenant
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic

@CompileStatic
class VehicleService {

    VehicleGormService vehicleGormService

    int countVehicles() {
        vehicleGormService.count()
    }

    List<Vehicle> listarVehiculos() {
        vehicleGormService.list([:])
    }

    Vehicle obtenerVehiculo(Long id) {
        vehicleGormService.find(id)
    }

    Vehicle actualizar(Long id, String model, Integer year) {
        vehicleGormService.update(id, model, year)
    }

    Vehicle crear(String model, Integer year) {
        vehicleGormService.save(model, year)
    }

    Vehicle borrar(Long id) {
        vehicleGormService.delete(id)
    }

}
