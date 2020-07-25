package io.github.eroshenkoam.cmd;

import io.github.eroshenkoam.cmd.issue.IssueGetCommand;
import io.github.eroshenkoam.cmd.issue.IssueListCommand;
import io.github.eroshenkoam.cmd.issue.IssueUpdateCommand;
import picocli.CommandLine;

@CommandLine.Command(
        name = "tester",
        subcommands = {
                IssueListCommand.class,
                IssueGetCommand.class,
                IssueUpdateCommand.class
        }
)
public class MainCommand implements Runnable {

    @Override
    public void run() {
        new CommandLine(new MainCommand()).usage(System.out);
    }

}
