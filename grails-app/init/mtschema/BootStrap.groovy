package mtschema

import grails.gorm.multitenancy.Tenants
import org.grails.orm.hibernate.HibernateDatastore

class BootStrap {

    def grailsApplication
    HibernateDatastore hibernateDatastore

    def init = { servletContext ->
        println " -- En bootstrap"
        def ctx = grailsApplication.mainContext
        ['TEST', 'TRES'].each { String name ->
            println " ---- Registrando ${name}"
            HibernateDatastore ds = hibernateDatastore.getDatastoreForConnection(name)
            VehicleListenerService listener = new VehicleListenerService(ds)
            ctx.addApplicationListener(listener)
        }
    }
    def destroy = {
    }
}
