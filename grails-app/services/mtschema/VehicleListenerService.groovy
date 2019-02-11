package mtschema

import grails.events.annotation.gorm.Listener
import groovy.transform.CompileStatic
import org.grails.datastore.mapping.engine.event.PreInsertEvent
import org.grails.datastore.mapping.engine.event.PreUpdateEvent

@CompileStatic
class VehicleListenerService {

    @Listener(Vehicle)
    void onPreInsertEvent(PreInsertEvent event) {
        println "*** Vehicle preInsert"
        event.entityAccess.setProperty('model', 'preInsert')
    }

    @Listener(Vehicle)
    void onPreUpdateEvent(PreUpdateEvent event) {
        println "*** Vehicle preUpdate"
        event.entityAccess.setProperty('model', 'preUpdate')
    }

}
