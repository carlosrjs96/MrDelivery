package mrdelivery;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.*;

import static java.lang.Thread.sleep;

public class Controller {

    @FXML
    Button leerGrafoBTN = new Button();

    @FXML
    Button dijkstraBTN = new Button();

    @FXML
    Button caminoOptimoBTN = new Button();

    @FXML
    Button verGrafo = new Button();

    @FXML
    Button allPaths = new Button();

    @FXML
    Button avanzar = new Button();

    @FXML
    TextField getVertice = new TextField();

    @FXML
    TextField caminoOptimoTxt = new TextField();

    @FXML
    TextField allPathsTxt = new TextField();

    @FXML
    TextField camino = new TextField();

    @FXML
    TextArea colaDeGrafos = new TextArea();

    @FXML
    TextArea algoritmos = new TextArea();

    @FXML
    TextArea verGrafoText = new TextArea();

    @FXML
    TextArea caminoTxtArea = new TextArea();

    @FXML
    CheckBox scroll = new CheckBox();

    //Queue<Grafo> colaGrafos = new ArrayDeque<>();
    LinkedList<Grafo> colaGrafos = new LinkedList<>();

    Grafo grafoActual;

    @FXML
    final ToggleGroup group = new ToggleGroup();

    @FXML
    RadioButton cost = new RadioButton();
    @FXML
    RadioButton km = new RadioButton();
    @FXML
    RadioButton minuts = new RadioButton();

    @FXML
    Button recorridoProfundidadBtn = new Button();

    @FXML
    Button recorridoAnchuraBtn = new Button();

    @FXML
    TextField RecorridoTxt1 = new TextField();

    @FXML
    RadioButton costA = new RadioButton();
    @FXML
    RadioButton kmA = new RadioButton();
    @FXML
    RadioButton minutosA = new RadioButton();

    @FXML
    public void recorridoProfundidadBtn(ActionEvent e){
        String verticeSeleccionado = RecorridoTxt1.getText().toUpperCase();
        algoritmos.setText("Recorrido en profundidad \n"+grafoActual.resultDFS(grafoActual.findVertice(verticeSeleccionado).getId()));
    }
    @FXML
    public void recorridoAnchuraBtn(ActionEvent e){
        String verticeSeleccionado = RecorridoTxt1.getText().toUpperCase();
        algoritmos.setText("Recorrido en anchura \n"+grafoActual.BFS(grafoActual.findVertice(verticeSeleccionado).getId()));
    }
    @FXML
    public void ArbolDeExpansionBtn(ActionEvent e){
        String criterio = "";//"costo" "km" "minutos"
        if (costA.isSelected()){
            criterio = "costo";
        }else if(kmA.isSelected()){
            criterio = "km";
        }else if(minutosA.isSelected()){
            criterio = "minutos";
        }else{
            System.out.println("Seleccione un criterio");
            return;
        }
        algoritmos.setText("Árbol expansión mínima (PRIM) \n"+grafoActual.Prim(grafoActual.getAdjMatrix(criterio),criterio));
    }


    @FXML
    public void setLeerGrafoBTN(ActionEvent e) throws IOException {
        JSONReader jsonReader = new JSONReader();
        ArrayList<String> documentos =  jsonReader.getDocuments(".//Json files");
        for(String doc : documentos){
            System.out.println("Documento:"+doc);
            Grafo grafo1 = jsonReader.getGrafo(".//Json files//"+doc,doc);
            if(grafo1!=null) {
                colaGrafos.add(grafo1);
                colaDeGrafos.appendText("Grafo: " + doc + "\n");
                jsonReader.copyFile(".//Json files//" + doc, ".//Leidos//" + doc);
            }else{
                System.out.println("Documento Enviado a fallidos");
            }
        }
        //=============AQUI EL HILO=================
        /*JSONReader jsonReader1 = new JSONReader(colaGrafos, colaDeGrafos);
        jsonReader1.run();*/
    }

    @FXML
    public void verGrafo(ActionEvent e){
        //Desencola el grafo y lo guarda en una variable global para trabajarlo
        grafoActual = colaGrafos.poll();

        //assert grafoActual != null;
        //verGrafoText.setText(grafoActual.verGrafo());
        verGrafoText.setText(grafoActual != null ? grafoActual.verGrafo() : "No hay grafos en la cola");

        //Actualiza la visualizacion de cola de grafos
        colaDeGrafos.setText("");
        for (int i = 1; i <= colaGrafos.size(); ++i)
            colaDeGrafos.appendText("Grafo " + i + "\n");

    }

    //Dijkstra
    @FXML
    public void dijkstraBTN(ActionEvent e){
        if (!scroll.isSelected())
            algoritmos.clear();
        String verticeSeleccionado = getVertice.getText().toUpperCase();
        if (verticeSeleccionado.isEmpty()) {
            System.out.println("Debe ingresar un vertice");
            /*Popup popup = new Popup();
            Label label = new Label("Debe ingresar un vertice");
            popup.getContent().add(label);
            popup.show(new Stage());*/
            return;
        }

        System.out.println("Aplicando Dijkstra...");
        //Dijkstra dijkstra = new Dijkstra();
        //assert colaGrafos.peek() != null;
        //Vertex cp = colaGrafos.peek().findVertice("A");
        //Vertex cp = grafoActual.findVertice("A");
        if (cost.isSelected()){
            Dijkstra dijkstra = new Dijkstra();
            Vertex cp = grafoActual.findVertice(verticeSeleccionado);
            dijkstra.computePath(cp, "costo");

            double sum = 0;
            List<Vertex> vertices = grafoActual.getVertices();
            for (Vertex vertice : vertices){
                //algoritmos.appendText(dijkstra.getShortestPathTo(vertice).toString() + "\n");
                List<Vertex> path = dijkstra.getShortestPathTo(vertice);
                for (Vertex vertex : path) {
                    sum = vertex.getMinDistance();
                }
                System.out.println(sum);
                System.out.println("--------------\n");
                algoritmos.appendText(path.toString() + " ---- total costo: " + sum + "\n");
                sum = 0;

            }

            grafoActual.resetGrafo();

        } else if (km.isSelected()){
            Dijkstra dijkstra = new Dijkstra();
            Vertex cp = grafoActual.findVertice(verticeSeleccionado);
            dijkstra.computePath(cp, "km");

            double sum = 0;
            List<Vertex> vertices = grafoActual.getVertices();
            for (Vertex vertice : vertices){
                //algoritmos.appendText(dijkstra.getShortestPathTo(vertice).toString() + "\n");
                List<Vertex> path = dijkstra.getShortestPathTo(vertice);
                for (Vertex vertex : path) {
                    sum = vertex.getMinDistance();
                }
                System.out.println(sum);
                System.out.println("--------------\n");
                algoritmos.appendText(path.toString() + " ---- total km: " + sum + "\n");
                sum = 0;
            }
            grafoActual.resetGrafo();
        }
        else {
            Dijkstra dijkstra = new Dijkstra();
            Vertex cp = grafoActual.findVertice(verticeSeleccionado);
            dijkstra.computePath(cp, "minutos");

            double sum = 0;
            List<Vertex> vertices = grafoActual.getVertices();
            for (Vertex vertice : vertices){
                //algoritmos.appendText(dijkstra.getShortestPathTo(vertice).toString() + "\n");
                List<Vertex> path = dijkstra.getShortestPathTo(vertice);
                for (Vertex vertex : path) {
                    sum = vertex.getMinDistance();
                }
                System.out.println(sum);
                System.out.println("--------------\n");
                algoritmos.appendText(path.toString() + " ---- total minutos: " + sum + "\n");
                sum = 0;
            }
            grafoActual.resetGrafo();
        }


        //assert colaGrafos.peek() != null;
        //Vertex source = colaGrafos.peek().findVertice(verticeSeleccionado);
        //Vertex source = grafoActual.findVertice(verticeSeleccionado);
        //System.out.println(dijkstra.getShortestPathTo(source));

        //algoritmos.setText(dijkstra.getShortestPathTo(source).toString());


    }

    //Camino optimo
    @FXML
    public void caminoOptimoBTN(ActionEvent e){
        String caminoOptimo = caminoOptimoTxt.getText().toUpperCase();
        if (cost.isSelected()) {
            Dijkstra dijkstra = new Dijkstra();

            Vertex compPath = grafoActual.findVertice(caminoOptimo.split("-")[0]);
            Vertex target = grafoActual.findVertice(caminoOptimo.split("-")[1]);
            dijkstra.computePath(compPath, "costo");
            double sum = 0;
            List<Vertex> path = dijkstra.getShortestPathTo(target);
            for (Vertex vertex : path)
                sum = vertex.getMinDistance();
            algoritmos.appendText("Camino optimo por costo: " + sum + " "+path.toString() + "\n");

            grafoActual.resetGrafo();

        } else if (km.isSelected()){
            Dijkstra dijkstra = new Dijkstra();

            Vertex compPath = grafoActual.findVertice(caminoOptimo.split("-")[0]);
            Vertex target = grafoActual.findVertice(caminoOptimo.split("-")[1]);
            dijkstra.computePath(compPath, "km");
            double sum = 0;
            List<Vertex> path = dijkstra.getShortestPathTo(target);
            for (Vertex vertex : path)
                sum = vertex.getMinDistance();
            algoritmos.appendText("Camino optimo por km: " + sum + " "+path.toString() + "\n");

            grafoActual.resetGrafo();
        } else {
            Dijkstra dijkstra = new Dijkstra();

            Vertex compPath = grafoActual.findVertice(caminoOptimo.split("-")[0]);
            Vertex target = grafoActual.findVertice(caminoOptimo.split("-")[1]);
            dijkstra.computePath(compPath, "minutos");
            double sum = 0;
            List<Vertex> path = dijkstra.getShortestPathTo(target);
            for (Vertex vertex : path)
                sum = vertex.getMinDistance();
            algoritmos.appendText("Camino optimo por minutos: " + sum + " "+path.toString() + "\n");

            grafoActual.resetGrafo();
        }

    }

    @FXML
    public void getAllPaths(ActionEvent e){
        Vertex origen = grafoActual.findVertice(allPathsTxt.getText().split("-")[0]);
        Vertex destino = grafoActual.findVertice(allPathsTxt.getText().split("-")[1]);
        //grafoActual.printAllPaths(origen.getId(), destino.getId());

        grafoActual.printAllPaths(origen.getId(), destino.getId());
        System.out.println(grafoActual.getPaths());
        algoritmos.appendText("Caminos desde " + origen.getName() + " a " + destino.getName() + ": \n");

        String [] appendText = new String[grafoActual.getPaths().size()];
        //double sum = 0;
        for (int i = 0; i < grafoActual.getPaths().size(); ++i) {
            //algoritmos.appendText(grafoActual.getPaths().get(i).toString() + "\n");
            appendText[i] = grafoActual.getPaths().get(i).toString() + " ";

            /*for (List<Vertex> path : grafoActual.getPaths()){
                for (int j = 0; j < path.size()-1; j++) {
                    sum += path.get(j).finEdge(path.get(j), path.get(j+1)).getCosto();
                }
                System.out.println("Suma del camino: " + sum);
                sum = 0;
            }*/

        }


        //Suma el costo de los caminos disponibles al destino seleccionado
        double sum2 = 0;
        int k =0;
        for (List<Vertex> path : grafoActual.getPaths()) {
            for (int i = 0; i < path.size() - 1; i++) {
                if (cost.isSelected()) {
                    sum2 += path.get(i).finEdge(path.get(i), path.get(i + 1)).getCosto();
                } else if (km.isSelected()) {
                    sum2 += path.get(i).finEdge(path.get(i), path.get(i + 1)).getKm();
                } else {
                    sum2 += path.get(i).finEdge(path.get(i), path.get(i + 1)).getMinutos();
                }
            }
            System.out.println("Suma del camino: " + sum2);
            //appendText[0] += String.valueOf(sum);
            if (cost.isSelected()) {
                algoritmos.appendText(k+1 + ": " + appendText[k] + " total por costo: "+sum2 + "\n");
            } else if (km.isSelected()) {
                algoritmos.appendText(k+1 + ": " + appendText[k] + " total por km: "+sum2 + "\n");
            } else {
                algoritmos.appendText(k+1 + ": " + appendText[k] + " total por minutos: "+sum2 + "\n");
            }
            appendText[k] = "";
            sum2 = 0;
            k++;
        }
        //grafoActual.resetGrafo();
    }

    @FXML
    public void avanzarBTN(ActionEvent e){
        if (camino.getText().isEmpty()) {
            caminoTxtArea.setText("Debe seleccionar un camino!");
        }

        int caminoSelected = Integer.parseInt(camino.getText());
        caminoTxtArea.setText(grafoActual.getPaths().get(caminoSelected-1).toString() + " ");


    }

}
