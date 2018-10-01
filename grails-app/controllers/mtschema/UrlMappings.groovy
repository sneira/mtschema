package mtschema

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'vehicle')
        '/vehicles' (resources: 'vehicle')
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
