import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class MainPanel implements ToolWindowFactory  {
    private JPanel panel;
    private JLabel srcLabel;
    private JLabel testLabel;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        if (project.getBasePath() != null) {
            File src = new File(project.getBasePath() + File.separator + "src");
            List<Integer> srcResult = FileTraverser.go(src);
            File test = new File(project.getBasePath() + File.separator + "src"
                    + File.separator + "test");
            List<Integer> testResult = FileTraverser.go(test);
            showResults(srcLabel, srcResult, "Source");
            showResults(testLabel, testResult, "Test source");
        }

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(panel, "", false);
        toolWindow.getContentManager().addContent(content);
    }

    private void showResults(JLabel label, List<Integer> result, String text) {
        int a = result.get(0);
        int b = result.get(1);
        text += " files: " + a + "/" + b;
        if (b != 0) {
            double p = 100 * (double) (a) / (double) (b);
            label.setText(text + " " + p + "%");
        } else {
            label.setText(text);
        }
    }
}
