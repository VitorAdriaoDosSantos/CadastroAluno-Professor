package com.example.projetojavafxjdbc.controller;

import com.example.projetojavafxjdbc.Application;
import com.example.projetojavafxjdbc.model.dao.DaoFactory;
import com.example.projetojavafxjdbc.model.entities.Aluno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class BuscarAlunoController implements Initializable {
    @FXML
    private ComboBox matricula;
    @FXML
    private TextField nome;
    @FXML
    private TextField cpf;
    @FXML
    private DatePicker data;
    @FXML
    private ImageView foto;
    File file;
    @FXML

    public void onFotoClicked(){
        FileChooser fc = new FileChooser();
        file = fc.showOpenDialog(Application.getScene().getWindow());
        if(file!=null){
            foto.setImage(new Image(file.getAbsolutePath()));
        }
    }
    @FXML
    public void onAtualizarClicked(){}

    @FXML
    public void onBuscarClicked(){

    }

    @FXML
    public void onDeletarClicked(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Aluno> alunos = DaoFactory.createAlunoDao().procurarTodos();
        List<Integer> matriculas = new ArrayList<>();
        for(Aluno aluno:alunos){
            matriculas.add(aluno.getMatricula());
        }
        ObservableList<Integer> obs =
                FXCollections.observableArrayList(matriculas);

        matricula.setItems(obs);


    }
}