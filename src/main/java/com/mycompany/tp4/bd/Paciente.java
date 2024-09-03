/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.bd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import model.dao.ConsultaDAO;

/**
 *
 * @author super
 */
public class Paciente {
    private String nome;
    private int id;
    private String data_de_nascimento;
    private String CPF;
    private String sexo;
    private String senha;
    private int ativo = 1;
    private ArrayList<Consulta> consultas = new ArrayList<>();
    private ArrayList<Consulta> listaEspera = new ArrayList<>();
    private ArrayList<Consulta> consultasRealizadas = new ArrayList<>();
    private ArrayList<Consulta> avaliarConsultas = new ArrayList<>();

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    public void setAvaliarConsultas(ArrayList<Consulta> avaliarConsultas) {
        this.avaliarConsultas = avaliarConsultas;
    }

    public void resetAvaliarConsultas(){
        this.avaliarConsultas = null;
    }

    public String getData_de_nascimento() {
        return data_de_nascimento;
    }

    public void setData_de_nascimento(String data_de_nascimento) {
        this.data_de_nascimento = data_de_nascimento;
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void resetListaEspera(){
        int size = this.listaEspera.size();
        for(int i =0; i<size; i++){
            this.listaEspera.remove(0);
        }
    }
    public void setListaEspera(ArrayList<Consulta> listaEspera) {
        this.listaEspera = listaEspera;
    }

    public ArrayList<Consulta> getConsultasRealizadas() {
        return consultasRealizadas;
    }

    public void setConsultasRealizadas(ArrayList<Consulta> consultasRealizadas) {
        this.consultasRealizadas = consultasRealizadas;
    }

    public ArrayList<Consulta> getConsultas() {
        ConsultaDAO daoc = new ConsultaDAO();
        ArrayList<Consulta> todasConsultas = daoc.getIdPaciente(this.id);
        for(Consulta consulta : todasConsultas){
            if(consulta.getStatus().equals("marcada")){
                this.consultas.add(consulta);
            }
        }
        return this.consultas;
    }
    public void resetConsultas(){
        int size = this.consultas.size();
        for(int i =0; i<size; i++){
            this.consultas.remove(0);
        }
    }
    public int getIdade(){
        // Converter a string para LocalDate
        LocalDate dataNascimento = LocalDate.parse(this.data_de_nascimento);
        
        // Obter a data atual
        LocalDate dataAtual = LocalDate.now();
        
        // Calcular a diferença de anos entre a data atual e a data de nascimento
        Period periodo = Period.between(dataNascimento, dataAtual);
        int idade = periodo.getYears(); // Obtém apenas a parte dos anos
        return idade;
    }
    public void setConsultas(ArrayList<Consulta> consultas) {
        this.consultas = consultas;
    }

    public Paciente(String nome, String data_de_nascimento, String sexo, String senha, String CPF) {
        this.nome = nome;
        this.data_de_nascimento = data_de_nascimento;
        this.sexo = sexo;
        this.senha = senha;
        this.CPF = CPF;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public ArrayList<Consulta> gerarRelatorio(String periodoInicial,String periodoFinal) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date data;
        Date dataInicial = sdf.parse(periodoInicial);
        Date dataFinal = sdf.parse(periodoFinal);
        ArrayList<Consulta> consultasPeriodo = new ArrayList<>();
        ArrayList<Consulta> consultasTotal = this.getConsultas();
        for(Consulta i : consultasTotal){
            SimpleDateFormat formatoOriginal = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd-MM-yyyy");
            data = formatoOriginal.parse(i.getData());
            String dataFormatada = formatoDesejado.format(data);
            Date dataConsulta = sdf.parse(dataFormatada);
            if(dataConsulta.after(dataInicial)&&dataConsulta.before(dataFinal)){
                consultasPeriodo.add(i);
            }
        }
        if(consultasPeriodo.size()>0){
            System.out.println("Voce possui os seguintes agendamentos/consultas no periodo de: " + periodoInicial + " a " + periodoFinal);
            for(Consulta i : consultasPeriodo){
                System.out.println(i.toString());
            }
        }
        else{
            System.out.println("Voce nao possui agendamentos/consultas no periodo informado");
        }
        this.resetConsultas();
        return consultasPeriodo;
    }

/*
    public void adicionarConsulta(Consulta consulta){
        this.consultas.add(consulta);
    }
    public void addListaEspera(Consulta consulta){
        this.listaEspera.add(consulta);
    }
    
    public void verificarListas (Consulta consulta){
        for(int i =0;i<this.listaEspera.size();i++){
            Consulta consultaListaEspera = listaEspera.get(i);
                        if(consultaListaEspera.getData().equals(consulta.getData())&&consultaListaEspera.getHorario().equals(consulta.getHorario())){
                            consultas.add(consultaListaEspera);
                            listaEspera.remove(i);
                        }
        }
    }
    public void removerListaEspera(Consulta consulta){
        int index =0;
        for(int x=0;x<listaEspera.size();x++){
            Consulta i = listaEspera.get(x);
            if(i.getData().equals(consulta.getData())&&i.getHorario().equals(consulta.getHorario())){
                this.listaEspera.remove(index);
            }
            index=index+1;
        }
    }
    public void verificarListaEspera(Consulta consulta){
        int index =0;
        for(int x=0;x<listaEspera.size();x++){
            Consulta i = listaEspera.get(x);
            if(i.getData().equals(consulta.getData())&&i.getHorario().equals(consulta.getHorario())){
                this.listaEspera.remove(index);
                this.consultas.add(i);
            }
            index=index+1;
        }
    }
*/
}
