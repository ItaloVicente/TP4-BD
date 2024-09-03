/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.bd;

/**
 *
 * @author super
 */
public class Consulta {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private int idMedico;
    private int idPaciente;
    private String data;
    private String horario;
    private String descricao;
    private double nota;
    private String status;
    private int prioridade;

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }
 
    
    
    public double getNota() {
        return this.nota;
    }

    public String getDescricao() {
        return this.descricao;
    }
    
    public void setNota(double nota) {
        this.nota = nota;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Consulta(int idMedico, int idPaciente, String data, String horario, String status) {
        this.idMedico = idMedico;
        this.idPaciente = idPaciente;
        this.data = data;
        this.horario=horario;
        this.nota = 0.0;
        this.status = status;
    }
    
    public Consulta(int idMedico, int idPaciente, String data, String horario, double nota, String status) {
        this.idMedico = idMedico;
        this.idPaciente = idPaciente;
        this.data = data;
        this.horario=horario;
        this.nota = nota;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }
}
