package com.example.projetojavafxjdbc.controller;

import com.example.projetojavafxjdbc.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationController {
    @FXML
    private MenuItem cadastrar;
    @FXML
    private MenuItem buscar;
    @FXML
    private MenuItem Crud;

    private Stage stage;

    @FXML
    public void menuItemCadastrarOnClicked() throws IOException {
        Application.newStage("cadastrar-aluno-view.fxml");
    }

    @FXML
    public void menuItemBuscarOnClicked() throws IOException {
       Application.newStage("buscar-aluno-view.fxml");
    }
    @FXML
    public void menuItemCrudOnClicked() throws IOException {
        Application.newStage("crud-professor-view.fxml");
    }

}