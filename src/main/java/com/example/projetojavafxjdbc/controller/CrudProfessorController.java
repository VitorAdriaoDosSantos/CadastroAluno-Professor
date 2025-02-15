package com.example.projetojavafxjdbc.controller;

import com.example.projetojavafxjdbc.Application;
import com.example.projetojavafxjdbc.model.dao.DaoFactory;
import com.example.projetojavafxjdbc.model.entities.Professor;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.example.projetojavafxjdbc.util.ConfirmationDialog;
import com.example.projetojavafxjdbc.util.Toast;
import javafx.scene.image.ImageView;
import java.io.File;

public class CrudProfessorController {

    @FXML
    private TableView<Professor> tabelaProfessores; // Tabela para exibir os produtos
    @FXML
    private TableColumn<Professor, String> colunaNome; // Coluna para exibir o nome do produto
    @FXML
    private TableColumn<Professor, Integer> colunaMatricula; // Coluna para exibir a categoria do produto
    @FXML
    private TableColumn<Professor, Image> colunaFoto; // Coluna para exibir o preço do produto


    @FXML
    private TextField textNome; // Campo de texto para o nome do produto
    @FXML
    private TextField textMatricula; // Campo de texto para a categoria do produto
    @FXML
    private TextField textFoto; // Campo de texto para o preço do produto


    private Stage stage; // Referência para o palco principal
    @FXML
    private ImageView foto;
    private File file;


    @FXML
    public void initialize() {

        carregarTabela();
        Platform.runLater(() -> stage = (Stage) tabelaProfessores.getScene().getWindow());
    }
    @FXML
    public void handleMouseClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            Professor professorSelecionado = tabelaProfessores.getSelectionModel().getSelectedItem();
            if (professorSelecionado != null) {
                carregarDetalhesProfessor(professorSelecionado);
            }
        }
    }

    private void carregarDetalhesProfessor(Professor professor) {

        textNome.setText(professor.getNome());
        textMatricula.setText(String.valueOf(professor.getMatricula()));
        textFoto.setText(String.valueOf(professor.getFoto()));
    }

    private void carregarTabela() {
        try {
            ObservableList<Professor> observableList = FXCollections.observableArrayList(
                    DaoFactory.createProfessorDao().findAll()
            );

            colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colunaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
            colunaFoto.setCellValueFactory(new PropertyValueFactory<>("foto"));

            tabelaProfessores.setItems(observableList);

            tabelaProfessores.refresh(); // Força atualização visual

        } catch (Exception e) {
            System.out.println("Erro ao carregar a tabela de professores:");
            e.printStackTrace();
        }
    }

    @FXML
    private void atualizarProfessor() {
        boolean confirmacao = ConfirmationDialog.show(stage, "Tem certeza que deseja atualizar o produto?");
        if (confirmacao) {

            try {
                Professor professor = new Professor();


                professor.setNome(textNome.getText());
                professor.setMatricula(Integer.parseInt(textMatricula.getText()));
                //foto errada
                //professor.setFoto(Double.parseDouble(textFoto.getText()));

                DaoFactory.createProfessorDao().update(professor);
            } catch (Exception e) {
                Toast.show(stage, "Erro ao atualizar o produto");
            }
            carregarTabela();
        }
    }

    @FXML
    private void adicionarProfessor() {
        boolean confirmacao = ConfirmationDialog.show(stage, "Tem certeza que deseja adicionar o produto?");
        if (confirmacao) {
            try {
                Professor professor = new Professor();
                String nome = textNome.getText();
                if (nome == null || nome.isEmpty()) {
                    Toast.show(stage, "O campo nome é obrigatório");
                    return;
                }
                professor.setNome(textNome.getText());
                professor.setMatricula(Integer.parseInt(textMatricula.getText()));
                //foto ta errada
                //professor.setFoto(getFoto.getValue());

                DaoFactory.createProfessorDao().insert(professor);
            } catch (NullPointerException e) {
                Toast.show(stage, "Preencha todos os campos");
            } catch (NumberFormatException e) {
                Toast.show(stage, "Preencha os campos corretamente");
            } catch (Exception e) {
                Toast.show(stage, "Erro ao adicionar o produto");
                e.printStackTrace();
            } finally {
                carregarTabela();
            }
        }
    }

    @FXML
    private void removerProfessor() {
        boolean confirmacao = ConfirmationDialog.show(stage, "Tem certeza que deseja remover o produto?");
        if (confirmacao) {
            try {
                int matricula;
                try {
                    matricula = Integer.parseInt(textMatricula.getText());
                } catch (NumberFormatException e) {
                    System.out.println("Erro ao converter matricula para número: " + e.getMessage());
                    return;
                }
                DaoFactory.createProfessorDao().deleteByMatricula(matricula);
                carregarTabela();
            } catch (Exception e) {
                System.out.println("Erro ao remover o professor: " + e.getMessage());
            }
        } else {
            System.out.println("Remoção cancelada pelo usuário.");
        }
    }

    @FXML
    private void limparCampos() {
        textNome.clear();
        textMatricula.clear();
        textFoto.clear();

    }

    @FXML
    public void fotoOnClicked(){
        FileChooser fc = new FileChooser();
        file = fc.showOpenDialog(Application.getScene().getWindow());
        if(file!=null){
            foto.setImage(new Image(file.getAbsolutePath()));
        }
    }
}
