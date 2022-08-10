package uet.oop.dictionary.utils;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import uet.oop.dictionary.ui.Toast;
import uet.oop.dictionary.ui.ToastUI;

public class QuickToast {
    public static void makeText(Pane root, String message) {
        Toast.ToastBuilder builder = new Toast.ToastBuilder(root.getScene().getWindow());

        builder.withContent(new ToastUI())
                .withPos(root.getLayoutX() + 8, root.getLayoutY()
                        + root.getHeight() - 40)
                .withMessage(message)
                .build()
                .show();
    }
}
