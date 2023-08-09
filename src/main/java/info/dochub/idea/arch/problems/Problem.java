package info.dochub.idea.arch.problems;

import com.intellij.psi.PsiElement;
import info.dochub.idea.arch.utils.Objects;
import org.jetbrains.annotations.NotNull;

public class Problem implements Comparable<Problem> {
    private final PsiElement target;
    private final ProblemLevel problemLevel;
    private final int line;
    private final int column;
    private final String sourceName;
    private final String message;
    private final boolean afterEndOfLine;
    private final boolean suppressErrors;

    public Problem(@NotNull final PsiElement target,
                   @NotNull final String message,
                   @NotNull final ProblemLevel problemLevel,
                   final int line,
                   final int column,
                   final String sourceName,
                   final boolean afterEndOfLine,
                   final boolean suppressErrors) {
        this.target = target;
        this.message = message;
        this.problemLevel = problemLevel;
        this.line = line;
        this.column = column;
        this.sourceName = sourceName;
        this.afterEndOfLine = afterEndOfLine;
        this.suppressErrors = suppressErrors;
    }

    @Override
    public int compareTo(@NotNull Problem other) {
        int lineComparison = Integer.compare(this.line, other.line);
        if (lineComparison == 0) {
            int columnComparison = Integer.compare(this.column, other.column);
            if (columnComparison == 0) {
                int severityComparison = -this.problemLevel.compareTo(other.problemLevel);
                if (severityComparison == 0) {
                    return Objects.compare(this.message, other.message);
                }
                return severityComparison;
            }
            return columnComparison;
        }
        return lineComparison;
    }
}
