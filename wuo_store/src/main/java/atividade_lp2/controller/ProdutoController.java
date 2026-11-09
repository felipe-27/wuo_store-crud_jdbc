package atividade_lp2.controller;

import atividade_lp2.model.Produto;
import atividade_lp2.model.ProdutoDAO;
import atividade_lp2.view.TelaPrincipal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

public class ProdutoController {
    
    private TelaPrincipal view;
    private ProdutoDAO dao;
    private List<Produto> listaProdutos;

    // O Controller recebe a View para poder manipulá-la
    public ProdutoController(TelaPrincipal view) {
        this.view = view;
        this.dao = new ProdutoDAO();
        this.listaProdutos = new ArrayList<>();
    }

    public boolean salvarProduto(String acao, List<String> produtoString) {        
        String nome = produtoString.get(0);
        String textoPreco = produtoString.get(1);
        String textoQuantidade = produtoString.get(2);
        String avaliacao = produtoString.get(3);
        
        nome = nome.trim();
        avaliacao = avaliacao.trim();
        
        if (avaliacao.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Selecione uma avaliação.");
            return false;
        }
        
        // validação de campos vazios
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Preencha todos os campos do formulário");
            return false; // retorna falso para a tela não fechar
        }
        
        if (textoPreco.isBlank()) textoPreco = "0";
        if (textoQuantidade.isBlank()) textoQuantidade = "0";
        
        BigDecimal precoUnitario;
        int quantidade;
        
        // conversão e tratamento de erro
        try {
            textoPreco = textoPreco.replace(",", ".");
            precoUnitario = new BigDecimal(textoPreco);
            quantidade = Integer.parseInt(textoQuantidade);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Os campos Preço e Quantidade devem conter apenas valores numéricos.",
             "Formato Inválido", JOptionPane.ERROR_MESSAGE);
            return false; // retorna falso para a tela não fechar
        }
        
        try {
            // direcionamento da ação
            if (acao.equals("INSERIR")) {
                Produto produto = new Produto(nome, precoUnitario, quantidade, avaliacao);
                dao.insereProduto(produto);
                
                DefaultTableModel modeloTabela = (DefaultTableModel) view.getTblProdutos().getModel();
                BigDecimal valorAcumulado = precoUnitario.multiply(new BigDecimal(quantidade));
                modeloTabela.addRow(new Object[]{
                    produto.getNomeProduto(), 
                    produto.getPreco().toString(),
                    String.valueOf(produto.getQuantidade()),
                    produto.getAvaliacao(),
                    valorAcumulado.toString()
                });
                listaProdutos.add(produto);
                
                JOptionPane.showMessageDialog(view, "Produto adicionado com sucesso!");
                
            } else if (acao.equals("EDITAR")) {
                int linhaSelecionada = view.getTblProdutos().getSelectedRow();
                int linhaModelo = view.getTblProdutos().convertRowIndexToModel(linhaSelecionada);
                String idDoProduto = listaProdutos.get(linhaModelo).getId();
                
                Produto produto = new Produto(nome, precoUnitario, quantidade, avaliacao);
                produto.setId(idDoProduto); // Coloca o ID no objeto
                
                dao.updateProduto(produto);
                
                DefaultTableModel modeloTabela = (DefaultTableModel) view.getTblProdutos().getModel();
                BigDecimal valorAcumulado = precoUnitario.multiply(new BigDecimal(quantidade));
                modeloTabela.setValueAt(nome, linhaModelo, 0);
                modeloTabela.setValueAt(precoUnitario.toString(), linhaModelo, 1);
                modeloTabela.setValueAt(String.valueOf(quantidade), linhaModelo, 2);
                modeloTabela.setValueAt(avaliacao, linhaModelo, 3);
                modeloTabela.setValueAt(valorAcumulado.toString(), linhaModelo, 4);
                listaProdutos.set(linhaModelo, produto);
                
                JOptionPane.showMessageDialog(view, "Produto atualizado com sucesso!");
            }
            
            return true; // sucesso, a tela pode fechar
            
        } catch (Exception e) {
            e.printStackTrace(); // Imprime no console para debugar
            JOptionPane.showMessageDialog(view,
                "Erro ao salvar no banco:\n" + e.getMessage(), 
                "Erro no Banco de Dados",
                JOptionPane.ERROR_MESSAGE);
            return false; // falhou, não fecha a tela
        }
    }

    public void deletarProduto() {
        JTable tabela = view.getTblProdutos();
        int linhaSelecionada = tabela.getSelectedRow();
        
        if (linhaSelecionada != -1) {
            int linhaModelo = tabela.convertRowIndexToModel(linhaSelecionada);
            Produto produto = listaProdutos.get(linhaModelo);
            DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

            try {
                dao.deletaProduto(produto.getId());

                modelo.removeRow(linhaModelo);
                listaProdutos.remove(linhaModelo);
                JOptionPane.showMessageDialog(view, "Produto deletado com sucesso!");

            } catch (IllegalStateException e) {
                e.printStackTrace(); // Imprime no console para debugar
                JOptionPane.showMessageDialog(view, 
                    "Erro ao tentar deletar o produto no banco:\n" + e.getMessage(), 
                    "Erro de Exclusão", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void preencherTabela() {
        this.listaProdutos = dao.listarProdutos();
        JTable tabela = view.getTblProdutos();
        DefaultTableModel modeloTabela = (DefaultTableModel) tabela.getModel();
        
        // Limpa a tabela para não duplicar dados
        modeloTabela.setNumRows(0);

        try {
            // Percorre a lista que vem do banco e adiciona linha por linha
            for (Produto produto : listaProdutos) {
                
                BigDecimal valorAcumulado = produto.getPreco().multiply(new BigDecimal(produto.getQuantidade()));
                // adiciona na TableModel
                modeloTabela.addRow(new Object[]{
                    produto.getNomeProduto(), 
                    produto.getPreco().toString(),
                    String.valueOf(produto.getQuantidade()),
                    produto.getAvaliacao(),
                    valorAcumulado.toString()
                });
            }
        } catch (Exception e) {
            e.printStackTrace(); // Imprime no console para debugar
            JOptionPane.showMessageDialog(view, "Erro ao carregar a tabela: " + e.getMessage());
        }
    }
}