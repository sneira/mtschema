package mtschema

import grails.gorm.MultiTenant

class Vehicle implements MultiTenant<Vehicle> {

    String model
    Integer year

    static constraints = {
        model blank: false
        year min: 1950
    }
}
