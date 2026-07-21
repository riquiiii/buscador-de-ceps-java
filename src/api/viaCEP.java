package api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


public class viaCEP{
    private String cep;
    private String json;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String estado;
    private String complemento;
    private String regiao;
    private int ddd;
    private boolean erro;
    private List<viaCEP> enderecos = new ArrayList<>();


    public void chamarAPI(String cep) throws InterruptedException, IOException{
        try {
            String url = "https://viacep.com.br/ws/"+ cep +"/json";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            json = response.body();
        } catch (Exception e) {
            System.out.println("Erro ao chamar a api: " + e.getMessage());
        }
    }


    public String tratarCEP(String cep) {

        cep = cep.replace(" ", "");

        if (cep.contains("-")) {
            cep = cep.substring(0, cep.indexOf("-"));
        }
        if (!cep.matches("\\d{8}")) {
            System.out.println("Cep invalido");
            return "";
        }
        return cep;
    }
    public void exibirCEP(){ //throws IOException {
        Gson gson = new GsonBuilder()
                .create();
        viaCEP resposta = gson.fromJson(json, viaCEP.class);
        //System.out.println(json);
        if (!resposta.erro) {
            System.out.println(resposta.toString());
            erro = Boolean.parseBoolean(null);
            enderecos.add(resposta);
            try {
                gravarCEP();
            }
            catch (Exception e) {
                System.out.println("Erro ao gravar CEP: " + e.getMessage());
            }
        }
        else{
            System.out.println("Este CEP não existe.");
        }
    }
    public void gravarCEP() throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        FileWriter escrever = new FileWriter("enderecos.json");
        escrever.write(gson.toJson(enderecos));
        escrever.close();
    }

    @Override
    public String toString() {
        return "CEP: " + cep +
                "\nLogradouro: " + logradouro +
                "\nComplemento: " + complemento +
                "\nBairro: " + bairro +
                "\nLocalidade: " + localidade +
                "\nEstado: " + estado +
                "\nRegião: " + regiao +
                "\nDDD: " + ddd;
    }

    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public String getJson() {
        return json;
    }
    public void setJson(String json) {
        this.json = json;
    }
    public String getRegiao() {
        return regiao;
    }
    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }
    public int getDdd() {
        return ddd;
    }
    public void setDdd(int ddd) {
        this.ddd = ddd;
    }
    public String getComplemento() {
        return complemento;
    }
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getLocalidade() {
        return localidade;
    }
    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }
    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public String getLogradouro() {
        return logradouro;
    }
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    public boolean isErro() {
        return erro;
    }
    public void setErro(boolean erro) {
        this.erro = erro;
    }
}






