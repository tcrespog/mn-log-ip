package mn.log.ip

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import jakarta.inject.Inject

@Controller('/')
@Slf4j
@CompileStatic
class MyController {

    @Inject MyService myService

    @Get('/event-loop')
    void eventLoop() {
        log.info("Printing log in controller method")
        myService.log()
    }

    @ExecuteOn(TaskExecutors.IO)
    @Get('/thread')
    void thread() {
        log.info("Printing log in controller method")
        myService.log()
    }

}
