package io.github.sever0x.apigateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * A global filter that extracts user information from the JWT and adds it as headers to the request.
 * This allows downstream services to access user information without needing to parse the JWT again.
 */
@Component
public class JwtFilter implements GlobalFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		return ReactiveSecurityContextHolder.getContext()
				.mapNotNull(SecurityContext::getAuthentication)
				.filter(authentication -> authentication.getPrincipal() instanceof Jwt)
				.mapNotNull(authentication -> (Jwt) authentication.getPrincipal())
				.map(jwt -> exchange.mutate()
						.request(r -> r
								.header("X-User-Id", jwt.getClaimAsString("userId"))
								.header("X-User-Role", jwt.getClaimAsString("role"))
						).build())
				// If there's no authentication, or it's not a JWT, just pass the original exchange through
				.defaultIfEmpty(exchange)
				// Continue the filter chain with the modified or original exchange
				.flatMap(chain::filter);
	}
}
