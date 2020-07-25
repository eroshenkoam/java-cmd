package io.github.eroshenkoam.cmd.issue;

import io.github.eroshenkoam.cmd.github.GitHubService;
import io.github.eroshenkoam.cmd.github.Issue;
import picocli.CommandLine;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static io.github.eroshenkoam.cmd.RetrofitUtil.createRetrofit;

@CommandLine.Command(
        name = "issue-get", mixinStandardHelpOptions = true,
        description = "Get information about Issue by number"
)
public class IssueGetCommand implements Runnable {

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

    @CommandLine.Option(
            names = {"--number"},
            description = "Issue number"
    )
    protected int number;

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

        final Response<Issue> response = github.getIssue(owner, repo, number).execute();
        if (response.isSuccessful()) {
            final Issue issue = response.body();
            System.out.println(String.format("#%d [%s] %s", issue.getNumber(), issue.getState(), issue.getTitle()));
        } else {
            System.out.println(response.errorBody().string());
        }
    }

}
