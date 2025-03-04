package com.example.projetojavafxjdbc.controller;
import com.example.projetojavafxjdbc.Application;
import com.example.projetojavafxjdbc.model.dao.DaoFactory;
import com.example.projetojavafxjdbc.model.entities.Professor;
import com.example.projetojavafxjdbc.util.ImageConverter;
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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;


public class CrudProfessorController {

    @FXML
    private TableView<Professor> tabelaProfessores;
    @FXML
    private TableColumn<Professor, String> colunaNome;
    @FXML
    private TableColumn<Professor, Integer> colunaMatricula;

    @FXML
    private TextField textNome;
    @FXML
    private TextField textMatricula;
    @FXML
    private ImageView imageFoto;

    private Stage stage;
    private File file;

    @FXML
    public void initialize() {
        carregarTabela();
        Platform.runLater(() -> stage = (Stage) tabelaProfessores.getScene().getWindow());

    }
    private int matriculaAntiga;

    @FXML
    public void handleMouseClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            Professor professorSelecionado = tabelaProfessores.getSelectionModel().getSelectedItem();
            if (professorSelecionado != null) {
                matriculaAntiga = professorSelecionado.getMatricula();
                carregarDetalhesProfessor(professorSelecionado);
            }
        }
    }

    private void carregarDetalhesProfessor(Professor professor) {
        textNome.setText(professor.getNome());
        textMatricula.setText(String.valueOf(professor.getMatricula()));
        if (professor.getFoto() != null) {
            imageFoto.setImage(new Image(new ByteArrayInputStream(professor.getFoto())));
        } else {
            imageFoto.setImage(new Image(getClass().getResource("/img/profile.png").toExternalForm()));
        }
    }

    private void carregarTabela() {
        try {
            ObservableList<Professor> observableList = FXCollections.observableArrayList(
                    DaoFactory.createProfessorDao().findAll()
            );

            colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colunaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));

            tabelaProfessores.setItems(observableList);
            tabelaProfessores.refresh();

        } catch (Exception e) {
            System.out.println("Erro ao carregar a tabela de professores:");
            e.printStackTrace();
        }
    }

    @FXML
    private void atualizarProfessor() {
        boolean confirmacao = ConfirmationDialog.show(stage, "Tem certeza que deseja atualizar o professor?");
        if (confirmacao) {

            try {

                String nome = textNome.getText();
                String matriculaTexto = textMatricula.getText();

                if (nome == null || nome.trim().isEmpty()) {
                    Toast.show(stage, "O campo Nome não pode estar vazio.");
                    return;
                }
                if (matriculaTexto == null || matriculaTexto.trim().isEmpty()) {
                    Toast.show(stage, "O campo Matrícula não pode estar vazio.");
                    return;
                }

                if (!nome.matches("[a-zA-ZÀ-ÿ\\s]+")) {
                    Toast.show(stage, "O Nome deve conter apenas letras.");
                    return;
                }

                if (!matriculaTexto.matches("\\d+")) {
                    Toast.show(stage, "A Matrícula deve conter apenas números.");
                    return;
                }

                Professor professor = new Professor();
                professor.setNome(textNome.getText());
                professor.setMatricula(Integer.parseInt(textMatricula.getText()));
                if (imageFoto.getImage() != null) {
                    byte[] foto = ImageConverter.imageViewToByteArray(imageFoto, "png");
                    professor.setFoto(foto);
                }
                DaoFactory.createProfessorDao().update(professor, matriculaAntiga);
            } catch (Exception e) {
                Toast.show(stage, "Erro ao atualizar o professor");
            }
            carregarTabela();
        }
    }

    @FXML
    private void adicionarProfessor() {
        boolean confirmacao = ConfirmationDialog.show(stage, "Tem certeza que deseja adicionar o professor?");

        if (confirmacao) {
            try {
                String nome = textNome.getText();
                String matriculaTexto = textMatricula.getText();


                if (nome == null || nome.trim().isEmpty()) {
                    Toast.show(stage, "O campo Nome não pode estar vazio.");
                    return;
                }
                if (matriculaTexto == null || matriculaTexto.trim().isEmpty()) {
                    Toast.show(stage, "O campo Matrícula não pode estar vazio.");
                    return;
                }

                if (!nome.matches("[a-zA-ZÀ-ÿ\\s]+")) {
                    Toast.show(stage, "O Nome deve conter apenas letras.");
                    return;
                }

                if (!matriculaTexto.matches("\\d+")) {
                    Toast.show(stage, "A Matrícula deve conter apenas números.");
                    return;
                }

                Professor professor = new Professor();
                professor.setNome(textNome.getText());
                professor.setMatricula(Integer.parseInt(textMatricula.getText()));
                if (imageFoto.getImage() != null) {
                    byte[] foto = ImageConverter.imageViewToByteArray(imageFoto, "png");
                    professor.setFoto(foto);
                }


                DaoFactory.createProfessorDao().insert(professor);
            } catch (NullPointerException e) {
                Toast.show(stage, "Preencha todos os campos");
            } catch (NumberFormatException e) {
                Toast.show(stage, "Preencha os campos corretamente");
            } catch (Exception e) {
                Toast.show(stage, "Erro ao adicionar o professor");
                e.printStackTrace();
            } finally {
                carregarTabela();
            }
        }
    }

    @FXML
    private void removerProfessor() {
        boolean confirmacao = ConfirmationDialog.show(stage, "Tem certeza que deseja remover o professor?");
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
        imageFoto.setImage(new Image(getClass().getResource("/img/profile.png").toExternalForm()));
    }

    @FXML
    public void fotoOnClicked(){
        FileChooser fc = new FileChooser();
        file = fc.showOpenDialog(stage);
        if(file!=null){
            imageFoto.setImage(new Image(file.toURI().toString()));
        }
    }
}