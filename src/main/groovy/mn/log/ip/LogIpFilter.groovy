package mn.log.ip

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.core.order.Ordered
import io.micronaut.core.propagation.PropagatedContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Filter
import io.micronaut.http.context.ServerRequestContext
import io.micronaut.http.filter.HttpServerFilter
import io.micronaut.http.filter.ServerFilterChain
import io.micronaut.http.server.util.HttpClientAddressResolver
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import io.micronaut.context.propagation.slf4j.MdcPropagationContext
@Slf4j
@Singleton
@CompileStatic
@Filter("/**")
class LogIpFilter implements HttpServerFilter, Ordered {

    @Inject HttpClientAddressResolver addressResolver

    /**
     * Set the current IP to the logger.
     * @see <a href="http://logback.qos.ch/manual/mdc.html"/>
     */
    @Override
    Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        MDC.put('ip', getClientIp())
        try (PropagatedContext.Scope ignore = PropagatedContext.getOrEmpty().plus(new MdcPropagationContext()).propagate()) {
            return chain.proceed(request)
        }
    }

    @Override
    int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE
    }

    private String getClientIp() {
        final current = ServerRequestContext.currentRequest()
        return current.isPresent() ? getClientIp(current.get()) : null
    }

    private String getClientIp(HttpRequest request) {
        try {
            return addressResolver.resolve(request)
        }
        catch (Throwable t) {
            log.debug "Unable to resolve client address for $request - cause: ${t.message ?: t}"
            return null
        }
    }

}
