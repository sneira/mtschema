package mtschema

import grails.gorm.multitenancy.CurrentTenant
import grails.validation.ValidationException
import groovy.transform.CompileStatic

import static org.springframework.http.HttpStatus.NOT_FOUND

@CompileStatic
class VehicleController {

    VehicleGormService vehicleGormService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond vehicleGormService.list(params), model: [vehicleCount: vehicleGormService.count()]
    }

    def show(Long id) {
        Vehicle vehicle = id ? vehicleGormService.find(id) : null
        respond vehicle
    }

    @CurrentTenant
    def create() {
        respond new Vehicle(params)
    }

    @CurrentTenant
    def edit(Long id) {
        show id
    }

    def save(String model, Integer year) {
        try {
            Vehicle vehicle = vehicleGormService.save(model, year)
            flash.message = 'Vehicle created'
            redirect vehicle
        } catch (ValidationException e) {
            respond e.errors, view: 'create'
        }
    }

    def update(Long id, String model, Integer year) {
        try {
            Vehicle vehicle = vehicleGormService.update(id, model, year)
            if (vehicle == null) {
                notFound()
            }
            else {
                flash.message = 'Vehicle updated'
                redirect vehicle
            }
        } catch (ValidationException e) {
            respond e.errors, view: 'edit'
        }
    }

    protected void notFound() {
        flash.message = 'Vehicle not found'
        redirect uri: '/vehicles', status: NOT_FOUND
    }

    def delete(Long id) {
        Vehicle vehicle = vehicleGormService.delete(id)
        if (vehicle == null) {
            notFound()
        }
        else {
            flash.message = 'Vehicle Deleted'
            redirect action: 'index', method: 'GET'
        }
    }
}
