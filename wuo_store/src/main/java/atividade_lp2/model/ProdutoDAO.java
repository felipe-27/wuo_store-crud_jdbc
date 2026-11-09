package atividade_lp2.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // CREATE
    public void insereProduto(Produto produto) {
        String sql = "INSERT INTO produto (nomeProduto, preco, quantidade, avaliacao) VALUES (?, ?, ?, ?)";
        
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, produto.getNomeProduto());
            ps.setBigDecimal(2, produto.getPreco());
            ps.setInt(3, produto.getQuantidade());
            ps.setString(4, produto.getAvaliacao());
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao inserir produto", e);
        }
    }

    // READ
    public List<Produto> listarProdutos() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT id, nomeProduto, preco, quantidade, avaliacao FROM produto";
        
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                lista.add(montaProduto(rs));
            }
            
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return lista;
    }

    // READ (busca pelo nome, aceita parte do nome)
    public List<Produto> buscarProdutos(String nome) {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT id, nomeProduto, preco, quantidade, avaliacao FROM produto WHERE nomeProduto LIKE ?";
        
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, "%" + nome + "%");
            
            // result set é instanciado apenas aqui, não no try acima,
            // pois é preciso setar os parâmetros (ps) antes de executar a query
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(montaProduto(rs));
                }
            }
            
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao buscar produto por nome", e);
        }
        return lista;
    }

    // UPDATE
    public void updateProduto(Produto produto) {
        String sql = "UPDATE produto SET nomeProduto = ?, preco = ?, quantidade = ?, avaliacao = ? WHERE id = ?";
        
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, produto.getNomeProduto());
            ps.setBigDecimal(2, produto.getPreco());
            ps.setInt(3, produto.getQuantidade());
            ps.setString(4, produto.getAvaliacao());
            ps.setString(5, produto.getId());
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao atualizar produto", e);
        }
    }

    // DELETE
    public void deletaProduto(String id) {
        String sql = "DELETE FROM produto WHERE id = ?";
        
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, id);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao deletar produto", e);
        }
    }

    // Método auxiliar
    private Produto montaProduto(ResultSet rs) throws SQLException {
        Produto p = new Produto();
        p.setId(rs.getString("id"));
        p.setNomeProduto(rs.getString("nomeProduto"));
        p.setPreco(rs.getBigDecimal("preco"));
        p.setQuantidade(rs.getInt("quantidade"));
        p.setAvaliacao(rs.getString("avaliacao"));
        return p;
    }
}