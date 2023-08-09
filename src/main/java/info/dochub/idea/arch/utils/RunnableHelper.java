package info.dochub.idea.arch.utils;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.project.Project;

public class RunnableHelper {
    public static void runRead(Project project, Runnable runnable, String name) {
        CommandProcessor.getInstance().executeCommand(
                project,
                new ReadAction(runnable),
                name,
                "DocHub"
        );
    }

    public static void runWrite(Project project, Runnable runnable, String name)
    {
        CommandProcessor.getInstance().executeCommand(
                project, new WriteAction(runnable),
                name,
                "DocHub"
        );
    }

    static class ReadAction implements Runnable {
        Runnable runnable;
        ReadAction(Runnable runnable) {
            this.runnable = runnable;
        }

        public void run() {
            ApplicationManager.getApplication().runReadAction(runnable);
        }

    }

    static class WriteAction implements Runnable {
        Runnable runnable;
        WriteAction(Runnable runnable) {
            this.runnable = runnable;
        }

        public void run() {
            ApplicationManager.getApplication().runWriteAction(runnable);
        }
    }
}
