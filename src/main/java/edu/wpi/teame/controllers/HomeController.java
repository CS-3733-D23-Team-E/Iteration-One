package edu.wpi.teame.controllers;

import edu.wpi.teame.navigation.Screen;
import edu.wpi.teame.navigation.Utilities;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class HomeController {
  @FXML MFXButton signageButton;
  @FXML MFXButton flowersButton;
  @FXML MFXButton mealRequestButton;
  @FXML MFXButton exitButton;

  @FXML MFXButton databaseViewButton;

  @FXML ImageView logoImage;

  @FXML
  public void initialize() {
    logoImage.setOnMouseEntered(
        event -> {
          // change to wong.jpg
        });
    logoImage.setOnMouseExited(
        event -> {
          // change to teamlogo.jpg
        });

    Utilities.addButtonHover(exitButton);
    exitButton.setOnMouseClicked(event -> Platform.exit());

    Utilities.addButtonHover(flowersButton);
    flowersButton.setOnMouseClicked(event -> Utilities.navigate(Screen.FLOWER_REQUEST));

    Utilities.addButtonHover(signageButton);
    signageButton.setOnMouseClicked(event -> Utilities.navigate(Screen.SIGNAGE_TEXT));

    databaseViewButton.setOnMouseClicked(event -> Utilities.navigate(Screen.DATABASE_VIEW));

    Utilities.addButtonHover(mealRequestButton);
    mealRequestButton.setOnMouseClicked(event -> Utilities.navigate(Screen.MEAL_REQUEST));
  }
}
