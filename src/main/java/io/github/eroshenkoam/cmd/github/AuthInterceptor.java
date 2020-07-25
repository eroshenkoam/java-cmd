package io.github.eroshenkoam.cmd.github;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AuthInterceptor implements Interceptor {

    private final String token;

    public AuthInterceptor(final String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request request = chain.request().newBuilder()
                .addHeader("Authorization", String.format("token %s", token)).build();
        return chain.proceed(request);
    }
}
