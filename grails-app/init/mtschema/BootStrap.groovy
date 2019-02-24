package mtschema

import org.grails.orm.hibernate.HibernateDatastore

class BootStrap {

    def grailsApplication
    HibernateDatastore hibernateDatastore

    def init = { servletContext ->
        println " -- Bootstrap init"
        def ctx = grailsApplication.mainContext
        ['TEST', 'TEST2'].each { String name ->
            println " ---- Registering ${name}"
            HibernateDatastore ds = hibernateDatastore.getDatastoreForConnection(name)
            VehicleListenerService listener = new VehicleListenerService(ds)
            ctx.addApplicationListener(listener)
        }
    }
    def destroy = {
    }
}
