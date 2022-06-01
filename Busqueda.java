import java.io.*;
import java.util.*;
import java.util.Scanner;

public class Busqueda {
    public ArrayList<String> leerDatosArchivo(String nombreArchivoCSV){
        ArrayList<String> palabras = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(nombreArchivoCSV));
            String line = null;
            String[] parts={};

            while ((line = reader.readLine()) != null) {
                parts = line.split(";");

                palabras.add(parts[0]);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return palabras;
    }

    public ArrayList<Object> masRelevantes(ArrayList<String> arr, Character num){
        Hashtable<String, Integer> contenedor = new Hashtable<String, Integer>();
        ArrayList<String> relevantes = new ArrayList<String>();
        ArrayList<Object> topMasRelevantes = new ArrayList<Object>();
        int contador = 0;
        String temporal = "";

        for(int i=0; i< arr.size();i++){
            for(int j=0;j<arr.get(i).length();j++){
                if(Character.compare(arr.get(i).charAt(j), num) == 0 ){
                    relevantes.add(arr.get(i));
                }
            }
        }


        if(relevantes.size() != 0){
            Collections.sort(relevantes);
            temporal = relevantes.get(0);

            for(int i=0;i<relevantes.size();i++){
                contador+=1;
                if(temporal.equals(relevantes.get(i))){
                    contenedor.put(relevantes.get(i), contador);
                }else{
                    temporal=relevantes.get(i);
                    contador=1;
                    contenedor.put(relevantes.get(i), contador);
                }
            }

            contador = 0;
            relevantes.clear();

            for(int i=0;i<3;i++){
                Enumeration e = contenedor.keys();
                Object clave="";
                Object palabra="";
                while( e.hasMoreElements() ) {
                    clave = e.nextElement();
                    if (contenedor.get(clave)>contador){
                        palabra = clave;
                        contador = contenedor.get(clave);

                    }
                }
                topMasRelevantes.add(palabra);
                contador=0;
                contenedor.remove(palabra);
            }
        }

        return topMasRelevantes;
    }

    public void imprimirTopRelevantes(ArrayList<Object> arr){
        if (arr.size()==0){
            System.out.println("No sucedió ningún hecho relevante con la opción ingresada");
        }else{
            for (int i=0;i<3;i++){
                System.out.print(i+1);
                System.out.println(": " + arr.get(i));
            }
        }
    }

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        Busqueda datosArchivo = new Busqueda();
        ArrayList<String> datos = new ArrayList<String>();
        datos = datosArchivo.leerDatosArchivo("C:\\Users\\PC Mateo\\IdeaProjects\\Buscador\\datos.csv");

        System.out.println("Bienvenido:");
        System.out.println("______________________________________________");
        System.out.println("Menú: (Escriba la letra de la opción que desea ejecutar)");
        System.out.println("a.) Mostrar 3 hechos relevantes según un número dado por el Usuario.");
        System.out.println("b.) Mostrar 5 hechos relevantes según una palabra dado por el Usuario.");
        System.out.println("c.) Mostrar 5 hechos relevantes según un texto dado por el Usuario.");

        String opcionUsuario = input.nextLine();

        switch (opcionUsuario){
            case "a":
                System.out.print("Ingrese el número a buscar: ");
                String numUsuario1 = input.nextLine();
                datosArchivo.imprimirTopRelevantes(datosArchivo.masRelevantes(datos, numUsuario1.charAt(0)));
                break;
            case "b":
                break;
            case "c":
                break;
            default:
                System.out.println("Error, la opción escogida no es válida. Vuelva a intentarlo.");
        }

    }
}
