package edu.wpi.teame.navigation;

import edu.wpi.teame.App;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class Utilities {
  public static void navigate(final Screen screen) {
    final String filename = screen.getFilename();

    try {
      final var resource = App.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(resource);

      App.getRootPane().setCenter(loader.load());
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }

  /**
   * adds listeners for entering and exiting a given MFXButton that allow color change on hover (ie
   * the button and text switch colors)
   */
  public static void addButtonHover(MFXButton button) {
    button.setOnMouseEntered(
        event -> {
          button.getStyleClass().remove("buttonNormal");
          button.getStyleClass().add("buttonHover");
        });
    button.setOnMouseExited(
        event -> {
          button.getStyleClass().remove("buttonHover");
          button.getStyleClass().add("buttonNormal");
        });
  }

  // Helpers

  private static String buttonBackground(MFXButton button) {
    String style = button.getStyle();
    int loc = style.indexOf("-fx-background-color: ");
    loc += "-fx-background-color: ".length();
    // System.out.println("location: " + loc);
    // System.out.println("style length: " + style.length());
    return style.substring(loc, loc + 7) + "ff";
  }

  private static String buttonText(MFXButton button) {
    String text = button.getTextFill().toString();
    return "#" + text.substring(2);
  }

  public static String buttonTextAlignment(MFXButton button) {
    String style = button.getStyle();
    int loc = style.indexOf("-fx-alignment: ");
    loc += "-fx-alignment: ".length();

    if (loc == -1) return "center";

    // find the end index of the alignment field
    int end = style.substring(loc).indexOf(";");
    return style.substring(loc, loc + end);
  }
}
