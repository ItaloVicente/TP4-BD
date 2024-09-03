/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.bd;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import model.dao.ConsultaDAO;

/**
 *
 * @author super
 */
public class Medico {
    private String nome;
    private int id;
    private String CRM;
    private String especialidade;
    private String senha;
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private ArrayList<Double> notas = new ArrayList();
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private ArrayList<Consulta> consultas = new ArrayList();
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private ArrayList<Consulta> listaEspera = new ArrayList();
    private int ativo = 1;
    public Medico(String nome, String especialidade, String senha, String CRM) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.senha = senha;
        this.CRM=CRM;
    }
    public void setAtivo(int ativo){
        this.ativo = ativo;
    }

    public int getAtivo() {
        return ativo;
    }
    
    public String getCRM() {
        return CRM;
    }

    public void setCRM(String CRM) {
        this.CRM = CRM;
    }
    
    public String getNome() {
        return nome;
    }
    
        public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public ArrayList<Double> getNotas() {
        return notas;
    }

    public void setNotas(ArrayList<Double> notas) {
        this.notas = notas;
    }

    public ArrayList<Consulta> getConsultas() {
        ConsultaDAO daoc = new ConsultaDAO();
        ArrayList<Consulta> todasConsultas = daoc.getIdMedico(this.id);
        for(Consulta consulta : todasConsultas){
            if(consulta.getStatus().equals("marcada")){
                this.consultas.add(consulta);
            }
        }
        return this.consultas;
    }
    public Consulta getConsultaIndex(int index){
        return this.consultas.get(index);
    }

    public void setConsultas(ArrayList<Consulta> consultas) {
        this.consultas = consultas;
    }
    public void resetConsultas(){
        int size = this.consultas.size();
        for(int i =0; i<size; i++){
            this.consultas.remove(0);
        }
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
    
    public void adicionarNota(double nota){
        notas.add(nota);
    }
    public void resetNotas(){
        int size = this.notas.size();
        for(int i =0; i<size; i++){
            this.notas.remove(0);
        }
    }
    //Periodo no formato dd/mm/aa
    public ArrayList<Consulta> gerarRelatorio(String periodoInicial,String periodoFinal) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date data;
        Date dataInicial = sdf.parse(periodoInicial);
        Date dataFinal = sdf.parse(periodoFinal);
        ArrayList<Consulta> consultasPeriodo = new ArrayList<>();
        ArrayList<Consulta> consultasTotais = this.getConsultas();
        for(Consulta i : consultasTotais){
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
    
}

