package com.mycompany.tp4.bd;
import java.util.ArrayList;
import model.dao.ConsultaDAO;
import model.dao.MedicoDAO;
import model.dao.PacienteDAO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */



/**
 *
 * @author super
 */
public class TP4BD {

    public static void main(String[] args) {
        Medico m = new Medico("VICENTE", "Cardiologia", "12345", "123456");        
        MedicoDAO daom = new MedicoDAO();
        daom.create(m,"12345");
        Paciente p = new Paciente("CRIS","02/02/04","M", "12345", "12345678911");
        PacienteDAO daop = new PacienteDAO();
        daop.create(p);
        Consulta c = new Consulta(1, 1, "24/05/01", "10:00","marcada");
        c.setId(1);
        ConsultaDAO dao = new ConsultaDAO();
        dao.create(c);
        c.setHorario("09:00");
        dao.update(c);
        Consulta pesquisa = dao.getConsultaId(1);
        System.out.println(pesquisa.getData());
        dao.delete(c);
        Consulta c2 = new Consulta(1, 1, "24/05/01", "11:00","marcada");
        ArrayList<Consulta> consultas = new ArrayList<Consulta>();
        consultas.add(c);
        consultas.add(c2);
        dao.create_arrayconsultas(consultas);
        System.out.println(daom.readForNome("VIC").get(0).getNome());

    }
}
