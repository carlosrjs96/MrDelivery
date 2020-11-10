package mrdelivery;

import javafx.scene.control.TextArea;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static java.lang.Thread.sleep;

public class JSONReader  extends Thread {
    private JSONParser parser;
    private Object object;
    private String [] abc = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private List<Vertex> grafo = new ArrayList<>();

    Queue<Grafo> colaGrafos;
    TextArea colaDeGrafos;
    boolean estado = true;

    JSONReader(Queue<Grafo> colaGrafos, TextArea colaDeGrafos){
        this.colaGrafos =colaGrafos;
        this.colaDeGrafos = colaDeGrafos;
    }

    JSONReader(){}

    @Override
    public void run() {
        while (estado) {
            try {
                System.out.println("leer archivos");
                ArrayList<String> documentos = null;
                try {
                    documentos = getDocuments(".//Json files");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (String doc : documentos) {
                    System.out.println("Documento:" + doc);
                    Grafo grafo1 = getGrafo(".//Json files//" + doc, doc);
                    if (grafo1 != null) {
                        colaGrafos.add(grafo1);
                        colaDeGrafos.appendText("Grafo: " + doc + "\n");
                        copyFile(".//Json files//" + doc, ".//Leidos//" + doc);
                    } else {
                        System.out.println("Documento Enviado a fallidos");
                    }
                }
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }


    public Grafo getGrafo(String path,String doc) {
        Grafo grafoJSON = new Grafo();
        grafo.clear();
        try {
            parser = new JSONParser();
            FileReader reader = new FileReader(path);
            object = parser.parse(reader);
            reader.close();
            //Lee el el JSON y crea un objeto
            JSONObject grafoJson = (JSONObject) object;

            //Lee los vertices del objeto JSON
            JSONArray vertices = (JSONArray) grafoJson.get("vertices");

            //Crea los vertices y los inserta en el ArrayList
            for (int i=0; i < vertices.size(); ++i) {
                //grafo.add( new Vertex(vertices.get(i).toString()));
                grafo.add( new Vertex(vertices.get(i).toString(), i));
            }

            //Lee las aristas del objeto JSON
            JSONArray aristas = (JSONArray) grafoJson.get("aristas");
            //JSONObject arist = (JSONObject) aristas.get(0);
            JSONObject arista;

            for (int i = 0; i < aristas.size(); i++) {
                arista = (JSONObject) aristas.get(i);

                Vertex origen = findVertex(arista.get("origen").toString());
                Vertex destino = findVertex(arista.get("destino").toString());
                boolean activo = (boolean) arista.get("activo");
                int costo = (int) (long) arista.get("costo");
                double km =  Double.parseDouble(arista.get("km").toString());
                double minutos = Double.parseDouble(arista.get("minutos").toString());

                //Al origen le agrega el edge de sus vecinos
                origen.addNeighbour(new Edge(origen,destino,activo,
                        costo,km,minutos));
            }
        } catch (Exception e) {
            copyFile(path,".//Fallidos//"+doc);
            System.out.println("Error leyendo archivo JSON " + e);
            return null;
        }

        grafoJSON.setGrafo(grafo);
        return grafoJSON;
    }


    //Funcion auxliar
    public Vertex findVertex(String name){
        for (Vertex vertex :
                grafo) {
            if (vertex.getName().equals(name))
                return vertex;
        }
        return null;
    }

    public ArrayList<String> getDocuments(String ruta) throws IOException {

        String sCarpAct = ruta;
        File carpeta = new File(sCarpAct);

        String[] listado = carpeta.list();
        ArrayList<String> listaDocumentos = new ArrayList<String>();
        if (listado == null || listado.length == 0) {
            System.out.println("No hay elementos dentro de la carpeta actual");
            return listaDocumentos;
        }else{
            for (int i=0; i< listado.length; i++) {
                System.out.println(listado[i]);
                listaDocumentos.add(listado[i]);
            }
        }
        //Files.deleteIfExists(carpeta.toPath());
        return listaDocumentos;
    }

    public void copyFile(String  origen, String destino) {
        Path origenPath = FileSystems.getDefault().getPath(origen);
        Path destinoPath = FileSystems.getDefault().getPath(destino);

        try {
            Files.move(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
