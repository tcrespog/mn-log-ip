package mn.log.ip

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import jakarta.inject.Singleton

@CompileStatic
@Singleton
@Slf4j
class MyService {

    void log() {
        log.info("Printing log in service method")
    }

}
