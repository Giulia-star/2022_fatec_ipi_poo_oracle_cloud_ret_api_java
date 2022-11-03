import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class PessoaService {
  private HttpClient client = HttpClient.newHttpClient();
  private final String url;

  public List <Pessoa> listar(){
      //generic (desde a vesao 5)
      List <Pessoa> Pessoa = new ArrayList <> (); //diamante: versao 7
    try{
        //design pattern:builder
      HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(url)).build();
      var response = client.send(request,BodyHandlers.ofString());
     //System.out.println(response.body());
     //response.body()-abstracao
     JSONObject raiz = new JSONObject(response.body());
     JSONArray items = raiz.getJSONArray("items");

     for(int i =0; i < items.length(); i++ ){
       JSONObject pessoaJSON = items.getJSONObject(i);
       String nome = pessoaJSON.getString("nome");
       int idade = pessoaJSON.getInt("idade");
       String hobby = pessoaJSON.getString("hobby");
       Pessoa p = new Pessoa();
       p.setNome(nome);
       p.setIdade(idade);
       p.setHobby(hobby);
      Pessoa.add(p);
     }
    }
    catch(Exception e){
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
    return Pessoa;
  }
}
