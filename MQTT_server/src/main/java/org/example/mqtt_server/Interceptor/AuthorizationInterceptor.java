package org.example.mqtt_server.Interceptor;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

@GRpcGlobalInterceptor
public class AuthorizationInterceptor implements ServerInterceptor {
    static class NoOperationListener<ReqT> extends ServerCall.Listener<ReqT> {
    }

    String BEARER_TYPE = "Bearer";
    String ACCESS_TOKEN_KEY = "C20F2F601BAA9B88945249CC9D499948C069B517A2278DB6C3307E596F125660";
    String REFRESH_TOKEN_KEY = "249CC9D4B51601BAA9B9C20F2F32278DB6C6F125660307E59948C0698894597A";
    Metadata.Key<String> AUTHORIZATION_METADATA_KEY = Metadata.Key.of("Authorization", ASCII_STRING_MARSHALLER);
    Context.Key<String> CLIENT_ID_CONTEXT_KEY = Context.key("clientId");

    private final JwtParser accessTokenParser = Jwts.parserBuilder().setSigningKey(ACCESS_TOKEN_KEY).build();
    private final JwtParser refreshTokenParser = Jwts.parserBuilder().setSigningKey(REFRESH_TOKEN_KEY).build();

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {
        String key = headers.get(AUTHORIZATION_METADATA_KEY);
        Status status;
        if (key.startsWith(BEARER_TYPE)) {
            try {
                String token = key.substring(BEARER_TYPE.length()).trim();
                Jws<Claims> claims = accessTokenParser.parseClaimsJws(token);
                Context ctx = Context.current().withValue(CLIENT_ID_CONTEXT_KEY, claims.getSignature());
                return Contexts.interceptCall(ctx, call, headers, next);
            } catch (Exception e) {
                status = Status.UNAUTHENTICATED.withDescription(e.getMessage()).withCause(e);
            }
        } else status = Status.UNAUTHENTICATED.withDescription("Unknown authorization type");
        Status.UNAUTHENTICATED.withDescription("Authorization token is missing");
        call.close(status, headers);
        // no op
        return new NoOperationListener();
    }

    public String getACCESS_TOKEN_KEY() {
        return ACCESS_TOKEN_KEY;
    }

    public String getREFRESH_TOKEN_KEY() {
        return REFRESH_TOKEN_KEY;
    }
}
