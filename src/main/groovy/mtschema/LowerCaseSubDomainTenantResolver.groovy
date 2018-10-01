package mtschema

import groovy.transform.CompileStatic
import org.grails.datastore.mapping.core.connections.ConnectionSource
import org.grails.datastore.mapping.multitenancy.TenantResolver
import org.grails.datastore.mapping.multitenancy.exceptions.TenantNotFoundException
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletWebRequest

import javax.servlet.http.HttpServletRequest

/**
 * Created by sofia on 17/09/18
 */

@CompileStatic
class LowerCaseSubDomainTenantResolver implements TenantResolver {

    @Override
    Serializable resolveTenantIdentifier() {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes()
        if(requestAttributes instanceof ServletWebRequest) {

            HttpServletRequest httpServletRequest = ((ServletWebRequest) requestAttributes).getRequest()
            String subdomain = httpServletRequest.getRequestURL().toString()
            String requestURI = httpServletRequest.getRequestURI()
            def i = requestURI.length()
            if(i > 0) {
                subdomain = subdomain.substring(0, subdomain.length()-i)
            }
            subdomain = subdomain.substring(subdomain.indexOf("/") + 2);
            if( subdomain.indexOf(".") > -1 ) {
                return subdomain.substring(0, subdomain.indexOf(".")).toUpperCase()
            }
            else {
                return ConnectionSource.DEFAULT
            }
        }
        throw new TenantNotFoundException("Tenant could not be resolved outside a web request")
    }

}
