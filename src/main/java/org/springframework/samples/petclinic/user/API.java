
package org.springframework.samples.petclinic.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import org.json.JSONObject;


public class API {
    String codigopostal;
    String municipio;
    String estado;

    public String getCodigopostal() {
        return codigopostal;
    }

    public void setCodigopostal(String codigopostal) {
        this.codigopostal = codigopostal;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public boolean WS (String cp,String mun){
        API api = new API();
        try {
            URL url = new URL("https://api-codigos-postales.herokuapp.com/v2/codigo_postal/"+cp);//your url i.e fetch data from .
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept","application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;            
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                JSONObject obj= new JSONObject(output);
                municipio = obj.getString("municipio");
                codigopostal = obj.getString("codigo_postal");
                estado = obj.getString("estado");
                System.out.println(municipio);
                System.out.println(estado);
                System.out.println(codigopostal);                
            }           
            
            conn.disconnect();

        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
        
      
        byte[] ptext = municipio.getBytes(ISO_8859_1); 
        String value = new String(ptext, UTF_8); 
        //System.out.println("Value"+value);
        
      if( mun.equals(value)){
          return true;
      }
      else{
          return false;
      }
            
    }
    
    public void WS (String cp){
        try {
            URL url = new URL("https://api-codigos-postales.herokuapp.com/v2/codigo_postal/"+cp);//your url i.e fetch data from .
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept","application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;            
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                JSONObject obj= new JSONObject(output);
                municipio = obj.getString("municipio");
                codigopostal = obj.getString("codigo_postal");
                estado = obj.getString("estado");
                System.out.println(municipio);
                System.out.println(estado);
                System.out.println(codigopostal);
                
            }
            conn.disconnect();

        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
    }
    
//    public String validacion(String cp,String municipio,String estado){
//        if()
//    }
    
    public static void main(String[] args) {
        API api = new API();
        boolean response;
        response=api.WS("96700","Minatitlán");
        System.out.println(response);
    }
    
    
}
