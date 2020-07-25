package io.github.eroshenkoam.cmd.issue;

import io.github.eroshenkoam.cmd.github.GitHubService;
import io.github.eroshenkoam.cmd.github.Issue;
import picocli.CommandLine;
import retrofit2.Response;

import java.util.Optional;

import static io.github.eroshenkoam.cmd.RetrofitUtil.createRetrofit;

@CommandLine.Command(
        name = "issue-update", mixinStandardHelpOptions = true,
        description = "Update an issue by number"
)
public class IssueUpdateCommand implements Runnable {

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

    @CommandLine.Option(
            names = {"--title"},
            description = "Issue title"
    )
    protected String title;

    @CommandLine.Option(
            names = {"--state"},
            description = "Issue state"
    )
    protected String state;

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

        final Response<Issue> issueResponse = github.getIssue(owner, repo, number).execute();
        if (!issueResponse.isSuccessful()) {
            System.out.println(issueResponse.errorBody().string());
        }

        final Issue patch = issueResponse.body();
        Optional.ofNullable(title).ifPresent(patch::setTitle);
        Optional.ofNullable(state).ifPresent(patch::setState);

        final Response<Issue> updateResponse = github.updateIssue(owner, repo, number, patch).execute();
        if (updateResponse.isSuccessful()) {
            final Issue updated = updateResponse.body();
            System.out.println(String.format("#%d [%s] %s", updated.getNumber(), updated.getState(), updated.getTitle()));
        } else {
            System.out.println(updateResponse.errorBody().string());
        }
    }

}
