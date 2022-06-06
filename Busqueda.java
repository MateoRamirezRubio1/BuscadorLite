import java.io.*;
import java.util.*;
import java.util.Scanner;

public class Busqueda {
    public <T> ArrayList<T> leerDatosArchivo(T nombreArchivoCSV){
        ArrayList<T> palabras = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(nombreArchivoCSV.toString()));
            var line = "";
            T[] parts;

            while ((line = reader.readLine()) != null) {
                parts = (T[]) line.split(";");

                palabras.add(parts[0]);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return palabras;
    }

    public <T> ArrayList<T> masRelevantes(ArrayList<T> arr, T usuario, T opcion){
        var contenedor = new Hashtable<>();
        ArrayList<String> relevantes = new ArrayList<>();
        ArrayList<T> topMasRelevantes = new ArrayList<>();
        var contador = 0;
        var temporal = "";
        T[] parts;

        //Obtener busquedas con el dígito o palabra necesitado
        if(opcion.equals("a")){
            for(var i=0; i< arr.size();i++){
                for(var j=0;j<arr.get(i).toString().length();j++){
                    if(Character.compare(arr.get(i).toString().charAt(j), usuario.toString().charAt(0)) == 0 ){
                        relevantes.add(arr.get(i).toString());
                    }
                }
            }
        }else {
            for(var i=0; i< arr.size();i++){
                parts = (T[]) arr.get(i).toString().split(" ");
                for(var j=0;j< parts.length;j++){
                    if(parts[j].toString().equals(usuario)){
                        relevantes.add(arr.get(i).toString());
                    }
                }
            }
        }

        if(relevantes.size() != 0){
            Collections.sort(relevantes);
            temporal = relevantes.get(0);

            //Obtener las veces que se repite cada busqueda
            for(var i=0;i<relevantes.size();i++){
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

            //Obtener los que se repiten más veces en orden descendente
            for(var i=0;i<5;i++){
                Enumeration e = contenedor.keys();
                var clave="";
                var palabra="";
                while( e.hasMoreElements() ) {
                    clave = e.nextElement().toString();
                    if ((Integer) contenedor.get(clave)>contador){
                        palabra = clave;
                        contador = (Integer) contenedor.get(clave);

                    }
                }
                topMasRelevantes.add((T) palabra);
                contador=0;
                contenedor.remove(palabra);
            }
        }

        return topMasRelevantes;
    }

    public <T> void imprimirTopRelevantes(ArrayList<T> arr, T opcion){
        if (arr.size()==0){
            System.out.println("No sucedió ningún hecho relevante con la opción ingresada");
        }else{
            if(opcion.equals("a")){
                for (var i=0;i<3;i++){
                    System.out.print(i+1);
                    System.out.println(": " + arr.get(i));
                }
            } else {
                for (var i=0;i<5;i++){
                    System.out.print(i+1);
                    System.out.println(": " + arr.get(i));
                }
            }
        }
    }

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        Busqueda datosArchivo = new Busqueda();
        var datos = new ArrayList<>();
        datos = datosArchivo.leerDatosArchivo("C:\\Users\\PC Mateo\\IdeaProjects\\Buscador\\datos.csv");

        System.out.println("Bienvenido:");
        System.out.println("______________________________________________");
        System.out.println("Menú: (Escriba la letra de la opción que desea ejecutar)");
        System.out.println("a.) Mostrar 3 hechos relevantes según un número dado por el Usuario.");
        System.out.println("b.) Mostrar 5 hechos relevantes según una palabra dado por el Usuario.");
        System.out.println("c.) Mostrar 5 hechos relevantes según un texto dado por el Usuario.");

        var opcionUsuario = input.nextLine();

        switch (opcionUsuario){
            case "a":
                System.out.print("Ingrese el número a buscar: ");
                var numUsuario = input.nextLine();
                datosArchivo.imprimirTopRelevantes(datosArchivo.masRelevantes(datos, numUsuario.charAt(0), "a"), "a");
                break;
            case "b":
                System.out.print("Ingrese la palabra a buscar: ");
                var palabraUsuario = input.nextLine();
                datosArchivo.imprimirTopRelevantes(datosArchivo.masRelevantes(datos, palabraUsuario, "b"), "b");
                break;
            case "c":
                break;
            default:
                System.out.println("Error, la opción escogida no es válida. Vuelva a intentarlo.");
        }

    }
}
