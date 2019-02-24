package mtschema

import groovy.transform.CompileStatic
import org.grails.datastore.mapping.core.Datastore
import org.grails.datastore.mapping.engine.event.AbstractPersistenceEvent
import org.grails.datastore.mapping.engine.event.AbstractPersistenceEventListener
import org.grails.datastore.mapping.engine.event.EventType
import org.grails.datastore.mapping.engine.event.PreInsertEvent
import org.grails.datastore.mapping.engine.event.PreUpdateEvent
import org.springframework.context.ApplicationEvent

@CompileStatic
class VehicleListenerService extends AbstractPersistenceEventListener {

    protected VehicleListenerService(Datastore datastore) {
        super(datastore)
    }

    @Override
    protected void onPersistenceEvent(AbstractPersistenceEvent event) {
        println ">>> onPersistenceEvent: ${event.entityAccess.getProperty('model')}. Source: ${event.source}. Datastore: ${this.datastore}. Event: ${event}"
        String newModel = event.eventType == EventType.PreInsert ? 'preInsert' : 'preUpdate'
        println ">>>>>> ${newModel}"
        event.entityAccess.setProperty('model', newModel)
    }

    @Override
    boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        boolean supportsEvent = eventType.isAssignableFrom(PreInsertEvent) ||
                eventType.isAssignableFrom(PreUpdateEvent)
        println "??? SupportsEventType ${eventType}: ${supportsEvent}"
        return supportsEvent
    } // Copiado de http://www.tothenew.com/blog/using-hibernate-events-with-persistenceeventlistener/

}
