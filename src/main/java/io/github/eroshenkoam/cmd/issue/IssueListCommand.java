package io.github.eroshenkoam.cmd.issue;

import io.github.eroshenkoam.cmd.github.GitHubService;
import io.github.eroshenkoam.cmd.github.Issue;
import picocli.CommandLine;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.List;

import static io.github.eroshenkoam.cmd.RetrofitUtil.createRetrofit;

@CommandLine.Command(
        name = "issue-list", mixinStandardHelpOptions = true,
        description = "Get information about issues in repository"
)
public class IssueListCommand implements Runnable {

    @CommandLine.Option(
            names = {"--owner"},
            description = "Repository owner"
    )
    protected String owner;

    @CommandLine.Option(
            names = {"--repo"},
            description = "Repository name"
    )
    protected String repo;

    @Override
    public void run() {
        try {
            runUnsafe();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void runUnsafe() throws Exception {
        final GitHubService github = createRetrofit().create(GitHubService.class);

        final Response<List<Issue>> response = github.getIssues(owner, repo).execute();
        if (response.isSuccessful()) {
            response.body().forEach(issue -> {
                System.out.println(String.format("#%d [%s] %s", issue.getNumber(), issue.getState(), issue.getTitle()));
            });
        } else {
            System.out.println(response.errorBody().string());
        }
    }

}
