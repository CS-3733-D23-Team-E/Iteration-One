package edu.wpi.teame.controllers;

import static javafx.scene.paint.Color.WHITE;

import edu.wpi.teame.entities.LoginData;
import edu.wpi.teame.utilities.Navigation;
import edu.wpi.teame.utilities.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class HomePageController {
  @FXML MFXButton serviceRequestButton;
  @FXML MFXButton signageButton;
  @FXML MFXButton databaseViewButton;
  @FXML MFXButton menuButton;
  @FXML MFXButton menuBarSignage;
  @FXML MFXButton menuBarServices;
  @FXML MFXButton menuBarHome;
  @FXML MFXButton menuBarMap;
  @FXML MFXButton menuBarExit;
  @FXML MFXButton mapsButton;
  @FXML MFXButton loginButton;
  @FXML TextField username;
  @FXML TextField password;
  @FXML MFXButton menuBarDatabase;

  Boolean loggedIn;

  public void initialize() {
    menuDropDownVisibility(false);

    showMenuButtonsWhenHovered(menuButton);
    showMenuButtonsWhenHovered(menuBarSignage);
    showMenuButtonsWhenHovered(menuBarServices);
    showMenuButtonsWhenHovered(menuBarHome);
    showMenuButtonsWhenHovered(menuBarMap);
    showMenuButtonsWhenHovered(menuBarDatabase);
    showMenuButtonsWhenHovered(menuBarExit);

    mouseSetup(serviceRequestButton);
    mouseSetup(signageButton);
    mouseSetup(mapsButton);
    mouseSetup(databaseViewButton);
    serviceRequestButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUESTS));
    signageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_TEXT));
    databaseViewButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP_DATA_EDITOR));
    mapsButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP));

    menuBarSignage.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_TEXT));
    menuBarServices.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUESTS));
    menuBarHome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    menuBarMap.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP));
    menuBarDatabase.setOnMouseClicked(event -> Navigation.navigate((Screen.MAP_DATA_EDITOR)));
    menuBarExit.setOnMouseClicked(event -> Platform.exit()); // Uncomment when we
    // know where exit goes

    loggedIn = false;
    loginButton.setOnMouseClicked(event -> attemptLogin());
  }

  public void menuDropDownVisibility(boolean bool) {
    menuBarSignage.setVisible(bool);
    menuBarServices.setVisible(bool);
    menuBarHome.setVisible(bool);
    menuBarMap.setVisible(bool);
    menuBarDatabase.setVisible(bool);
    menuBarExit.setVisible(bool);
  }

  public void showMenuButtonsWhenHovered(MFXButton button) {
    button.setOnMouseEntered(
        event -> {
          menuDropDownVisibility(true);
        });
    button.setOnMouseExited(
        event -> {
          menuDropDownVisibility(false);
        });
  }

  private void mouseSetup(MFXButton btn) {
    btn.setOnMouseEntered(
        event -> {
          btn.setStyle(
              "-fx-background-color: #ffffff; -fx-alignment: center; -fx-border-color: #192d5a; -fx-border-width: 2;");
          btn.setTextFill(Color.web("#192d5aff", 1.0));
        });
    btn.setOnMouseExited(
        event -> {
          btn.setStyle("-fx-background-color: #192d5aff; -fx-alignment: center;");
          btn.setTextFill(WHITE);
        });
  }

  public void attemptLogin() {
    // Get the input login info
    LoginData login = new LoginData(username.getText(), password.getText());

    // If the login was successful
    if (login.attemptLogin()) {
      // Hide text fields and button
      password.setVisible(false);
      username.setVisible(false);
      loginButton.setVisible(false);
      // Set loggedIn as true
      loggedIn = true;

    } else {
      // Clear the fields
      password.clear();
      username.clear();
    }
  }
}
