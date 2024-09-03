/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import com.mycompany.tp4.bd.Consulta;
import com.mycompany.tp4.bd.Paciente;
import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author super
 */
public class PacienteDAO {
    public boolean create(Paciente p){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO paciente (nome, sexo, senha, data_de_nascimento, cpf)VALUES(?,?,?,?, ?)");
            stmt.setString(1,p.getNome());
            stmt.setString(2, p.getSexo());
            stmt.setString(3, p.getSenha());
            stmt.setString(4, p.getData_de_nascimento());
            stmt.setString(5, p.getCPF());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null,"Salvo com sucesso");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Erro ao salvar: Cheque seus dados, como CPF " + "Erro:" + ex);
                return true;
            }finally{
                ConnectionFactory.closeConnection(con, stmt);
            }
       return false;
    }
    public void update(String CPF, String nome, String sexo, String senha, String data_de_nascimento){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("UPDATE paciente SET nome = ? , sexo = ? , senha = ? , data_de_nascimento = ?  WHERE cpf = ?");
            stmt.setString(1, nome);
            stmt.setString(2, sexo);
            stmt.setString(3, senha);
            stmt.setString(4, data_de_nascimento);
            stmt.setString(5, CPF);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null,"Atualizado com sucesso");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Erro ao atualizar: " + ex);
            }finally{
                ConnectionFactory.closeConnection(con, stmt);
            }
    }   
    public void update(Paciente p){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("UPDATE paciente SET nome = ? , sexo = ? , senha = ? , data_de_nascimento = ? , cpf = ?  WHERE id_paciente = ?");
            stmt.setString(1,p.getNome());
            stmt.setString(2, p.getSexo());
            stmt.setString(3, p.getSenha());
            stmt.setString(4, p.getData_de_nascimento());
            stmt.setString(5, p.getCPF());
            stmt.setInt(6, p.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null,"Atualizado com sucesso");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Erro ao atualizar: " + ex);
            }finally{
                ConnectionFactory.closeConnection(con, stmt);
            }
    }
    //funcao sobrecarga para inativar o paciente
    public void update(Paciente p, int ativo){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        ConsultaDAO daoc = new ConsultaDAO();
        List<Consulta> consultas = daoc.getIdPaciente(p.getId());
        for(Consulta consulta : consultas){
            if(consulta.getIdPaciente() == p.getId()){
                if(consulta.getStatus().equals("marcada")==true || consulta.getStatus().equals("espera")){
                    daoc.delete(consulta);
                    }
                }
            }
        try {
            stmt = con.prepareStatement("UPDATE paciente SET nome = ? , sexo = ? , senha = ? , data_de_nascimento = ? , cpf = ?, ativo = ?  WHERE id_paciente = ?");
            stmt.setString(1,p.getNome());
            stmt.setString(2, p.getSexo());
            stmt.setString(3, p.getSenha());
            stmt.setString(4, p.getData_de_nascimento());
            stmt.setString(5, p.getCPF());
            stmt.setInt(6,ativo);
            stmt.setInt(7, p.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null,"Atualizado com sucesso");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Erro ao atualizar: " + ex);
            }finally{
                ConnectionFactory.closeConnection(con, stmt);
            }
    }
    //apenas para mostrar o comando delete
    public void delete(Paciente p){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ConsultaDAO daoc = new ConsultaDAO();
        List<Consulta> consultas = daoc.read();
        for(Consulta consulta : consultas){
            if(consulta.getIdPaciente() == p.getId()){
                //Se o paciente for exluico, excluo todas as consultas dele tambem
                daoc.delete(consulta);
            }
        }
        
        try {
            stmt = con.prepareStatement("DELETE FROM paciente WHERE id_paciente = ?");
            stmt.setInt(1, p.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null,"Excluido com sucesso");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Erro ao excluir: " + ex);
            }finally{
                ConnectionFactory.closeConnection(con, stmt);
            }
    }
    
        public void inativar(Paciente p){
        p.setAtivo(0);
        this.update(p, p.getAtivo());
    }
   public Paciente getByCPF(String CPF){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Paciente paciente = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM paciente WHERE cpf = ?");
            stmt.setString(1, CPF);
            rs = stmt.executeQuery();
            while(rs.next()){
                paciente = new Paciente(rs.getString("nome"),rs.getString("data_de_nascimento"), rs.getString("sexo"), rs.getString("senha"), rs.getString("cpf"));
                paciente.setId(rs.getInt("id_paciente"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        
        
        return paciente;
    }
    public ArrayList<Paciente> read(){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Paciente> pacientes = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM paciente");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Paciente paciente = new Paciente(rs.getString("nome"),rs.getString("data_de_nascimento"), rs.getString("sexo"), rs.getString("senha"), rs.getString("cpf"));
                paciente.setId(rs.getInt("id_paciente"));
                pacientes.add(paciente);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        
        
        return pacientes;
    }
    
    public boolean checkLogin(String cpf, String senha){
        boolean verificador = false;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM paciente WHERE cpf = ? and senha = ?");
            stmt.setString(1, cpf);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();
            
            if(rs.next()){
                if(rs.getBoolean("ativo")){
                    verificador = true;
                }
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        
        
        return verificador;
    }
    
    public Paciente returnkLogin(String cpf, String senha){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Paciente paciente = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM paciente WHERE cpf = ? and senha = ?");
            stmt.setString(1, cpf);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();
            
            if(rs.next()){
                
                paciente = new Paciente(rs.getString("nome"),rs.getString("data_de_nascimento"), rs.getString("sexo"), rs.getString("senha"), rs.getString("cpf"));
                paciente.setId(rs.getInt("id_paciente"));
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        
        
        return paciente;
    }
    public Paciente returnPaciente(int id){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Paciente paciente = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM paciente WHERE id_paciente=?");
            stmt.setString(1, String.valueOf(id));
            rs = stmt.executeQuery();
            
            if(rs.next()){
                
                paciente = new Paciente(rs.getString("nome"),rs.getString("data_de_nascimento"), rs.getString("sexo"), rs.getString("senha"), rs.getString("cpf"));
                paciente.setId(rs.getInt("id_paciente"));
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        
        
        return paciente;
    }
}
