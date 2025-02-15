package com.example.projetojavafxjdbc.util;


import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ConfirmationDialog {


    public static boolean show(Stage owner, String message) {

        final boolean[] result = new boolean[1];


        Stage stage = new Stage();
        stage.initOwner(owner);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);

        stage.setOnCloseRequest(e -> e.consume());


        Label label = new Label(message);
        label.setStyle("-fx-font-size: 16px; -fx-text-fill: #333;");
        label.setWrapText(true);
        label.setMaxWidth(350);


        Button btnYes = new Button("Sim");
        btnYes.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-size: 16px;");
        btnYes.setMinWidth(80);
        btnYes.setOnAction(e -> {
            result[0] = true;
            stage.close();
        });


        Button btnNo = new Button("Não");
        btnNo.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-size: 16px;");
        btnNo.setMinWidth(80);
        btnNo.setOnAction(e -> {
            result[0] = false;
            stage.close();
        });


        HBox buttonBox = new HBox(15, btnYes, btnNo);
        buttonBox.setAlignment(Pos.CENTER);


        VBox root = new VBox(25, label, buttonBox);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-radius: 12;");


        StackPane container = new StackPane(root);
        container.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0.5, 0, 1);");


        Scene scene = new Scene(container);
        stage.setScene(scene);
        stage.sizeToScene();

        Platform.runLater(() -> {
            double ownerX = owner.getX();
            double ownerY = owner.getY();
            double ownerWidth = owner.getWidth();
            double ownerHeight = owner.getHeight();
            double stageWidth = stage.getWidth();
            double stageHeight = stage.getHeight();
            stage.setX(ownerX + (ownerWidth - stageWidth) / 2);
            stage.setY(ownerY + (ownerHeight - stageHeight) / 2);
        });

        stage.showAndWait();

        return result[0];
    }
}
