package mn.log.ip

import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification
import jakarta.inject.Inject

@MicronautTest
class MyControllerSpec extends Specification {

    @Inject
    @Client('/')
    HttpClient client

    void 'contact endpoint and check logs'() {
        when:
        client.toBlocking().exchange('/event-loop')

        then:
        // Printed logs should contain IP
        noExceptionThrown()
    }

    void 'contact endpoint and check logs'() {
        when:
        client.toBlocking().exchange('/thread')

        then:
        // Printed logs should contain IP
        noExceptionThrown()
    }

}
