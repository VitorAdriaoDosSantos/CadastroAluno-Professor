package com.example.projetojavafxjdbc.controller;

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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.example.projetojavafxjdbc.util.ConfirmationDialog;
import com.example.projetojavafxjdbc.util.Toast;


/**
 * Classe controladora para a tela de produtos.
 * Gerencia a exibição, adição, atualização e remoção de produtos.
 */
public class CrudProfessorController {

    @FXML
    private TableView<Professor> tabelaProfessores; // Tabela para exibir os produtos
    @FXML
    private TableColumn<Professor, String> colunaNome; // Coluna para exibir o nome do produto
    @FXML
    private TableColumn<Professor, String> colunaMatricula; // Coluna para exibir a categoria do produto
    @FXML
    private TableColumn<Professor, Image> colunaFoto; // Coluna para exibir o preço do produto


    @FXML
    private TextField textNome; // Campo de texto para o nome do produto
    @FXML
    private TextField textMatricula; // Campo de texto para a categoria do produto
    @FXML
    private TextField textFoto; // Campo de texto para o preço do produto


    private Stage stage; // Referência para o palco principal

    /**
     * Inicializa a classe controladora.
     * Carrega a tabela de produtos e define a referência do palco.
     */
    @FXML
    public void initialize() {
        carregarTabela();
        Platform.runLater(() -> stage = (Stage) tabelaProfessores.getScene().getWindow());

    }


    /**
     * Lida com o clique do mouse na tabela de produtos.
     * Carrega os detalhes do produto selecionado nos campos de texto.
     *
     * @param event Evento de clique do mouse.
     */
    @FXML
    public void handleMouseClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            Professor professorSelecionado = tabelaProfessores.getSelectionModel().getSelectedItem();
            if (professorSelecionado != null) {
                carregarDetalhesProduto(professorSelecionado);
            }
        }
    }

    /**
     * Carrega os detalhes do produto nos campos de texto.
     *
     * @param professor Produto a ser exibido nos campos de texto.
     */
    private void carregarDetalhesProduto(Professor professor) {

        textNome.setText(professor.getNome());
        textMatricula.setText(String.valueOf(professor.getMatricula()));
        textFoto.setText(String.valueOf(professor.getFoto()));
    }

    /**
     * Carrega a tabela de produtos com os dados do banco de dados.
     */
    private void carregarTabela() {
        try {
            ObservableList<Professor> observableList = FXCollections.observableArrayList(
                    DaoFactory.createProfessorDao().findAll()
            );

            colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colunaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
            //foto para carregar
            colunaFoto.setCellValueFactory(new PropertyValueFactory<>("foto"));

            tabelaProfessores.setItems(observableList);

            tabelaProfessores.refresh(); // Força atualização visual

        } catch (Exception e) {
            System.out.println("Erro ao carregar a tabela de professores:");
            e.printStackTrace();
        }
    }

    /**
     * Atualiza um produto existente no banco de dados.
     * Exibe uma confirmação antes de atualizar o produto.
     */
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

    /**
     * Adiciona um novo produto ao banco de dados.
     * Exibe uma confirmação antes de adicionar o produto.
     */
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

    /**
     * Remove um produto existente do banco de dados.
     * Exibe uma confirmação antes de remover o produto.
     */
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
}
