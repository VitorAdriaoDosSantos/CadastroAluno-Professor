package com.example.projetojavafxjdbc.controller;

import com.example.projetojavafxjdbc.Application;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;


public class CadastrarAlunoController {
    @FXML
    private TextField nome;
    @FXML
    private TextField cpf;
    @FXML
    private DatePicker dataNascimento;
    @FXML
    private ImageView foto;
    private File file;

    @FXML
    public void fotoOnClicked(){
        FileChooser fc = new FileChooser();
        file = fc.showOpenDialog(Application.getScene().getWindow());
        if(file!=null){
            foto.setImage(new Image(file.getAbsolutePath()));
        }
    }

    @FXML
    public void salvarOnClicked(){
    }

}
