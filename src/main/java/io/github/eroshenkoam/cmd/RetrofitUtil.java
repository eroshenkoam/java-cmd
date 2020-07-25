package io.github.eroshenkoam.cmd;

import io.github.eroshenkoam.cmd.github.AuthInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class RetrofitUtil {

    public static Retrofit createRetrofit() throws IOException {
        final Path tokenFile = Paths.get(System.getProperty("user.home")).resolve("token");
        final String token = Files.readAllLines(tokenFile).get(0).trim();
        return createRetrofit("https://api.github.com/", token);
    }

    public static Retrofit createRetrofit(final String endpoint, final String token) {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Optional.ofNullable(token)
                .map(AuthInterceptor::new)
                .ifPresent(builder::addInterceptor);
        return new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .client(builder.build())
                .baseUrl(endpoint)
                .build();
    }

}
