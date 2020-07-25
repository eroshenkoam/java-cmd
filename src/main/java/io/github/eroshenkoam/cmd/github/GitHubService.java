package io.github.eroshenkoam.cmd.github;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

import java.util.List;

public interface GitHubService {

    @GET("repos/{owner}/{repo}/issues")
    Call<List<Issue>> getIssues(@Path("owner") String user,
                                @Path("repo") String repo);

    @GET("repos/{owner}/{repo}/issues/{number}")
    Call<Issue> getIssue(@Path("owner") String user,
                         @Path("repo") String repo,
                         @Path("number") int number);

    @PATCH("repos/{owner}/{repo}/issues/{number}")
    Call<Issue> updateIssue(@Path("owner") String user,
                            @Path("repo") String repo,
                            @Path("number") Integer number,
                            @Body Issue issue);

}
