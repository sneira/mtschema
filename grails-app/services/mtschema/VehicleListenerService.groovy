package mtschema

import grails.events.annotation.gorm.Listener
import groovy.transform.CompileStatic
import org.grails.datastore.mapping.core.Datastore
import org.grails.datastore.mapping.engine.event.AbstractPersistenceEvent
import org.grails.datastore.mapping.engine.event.AbstractPersistenceEventListener
import org.grails.datastore.mapping.engine.event.PreInsertEvent
import org.grails.datastore.mapping.engine.event.PreUpdateEvent
import org.springframework.context.ApplicationEvent

@CompileStatic
class VehicleListenerService extends AbstractPersistenceEventListener {

    protected VehicleListenerService(Datastore datastore) {
        super(datastore)
        println " --- Constructor. Datastore parámetro: ${datastore}. Datastore final: ${this.datastore}"
    }

    @Listener(Vehicle)
    void onPreInsertEvent(PreInsertEvent event) {
        println "*** Vehicle preInsert: ${event.entityAccess.getProperty('model')}. Source: ${event.source}. Datastore: ${this.datastore}. Evento ${event}"
        event.entityAccess.setProperty('model', 'preInsert')
    }

    @Listener(Vehicle)
    void onPreUpdateEvent(PreUpdateEvent event) {
        println "*** Vehicle preUpdate: ${event.entityAccess.getProperty('model')}. Source: ${event.source}. Datastore: ${this.datastore}. Evento ${event}"
        event.entityAccess.setProperty('model', 'preUpdate')
    }

    @Override
    protected void onPersistenceEvent(AbstractPersistenceEvent event) {
        println ">>> onPersistenceEvent: ${event.entityAccess.getProperty('model')}. Source: ${event.source}. Datastore: ${this.datastore}. Evento ${event}"
        event.entityAccess.setProperty('model', 'onPersistenceEvent')
    }

    @Override
    boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        boolean supportsEvent = eventType.isAssignableFrom(PreInsertEvent) ||
                eventType.isAssignableFrom(PreUpdateEvent)
        println "??? SupportsEventType ${eventType}: ${supportsEvent}"
        return supportsEvent
    } // Copiado de http://www.tothenew.com/blog/using-hibernate-events-with-persistenceeventlistener/

}
