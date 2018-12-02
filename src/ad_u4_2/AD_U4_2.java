/*
 * Ahora vamos a cargar el archivo XML creado en la práctica 1 en una lista de objetos jugadores, y luego esta lista la guardaremos en un fichero XML utilizando serialización, por último practicaremos la deserialización.
 * 1. Crea primero las clases necesarias para que luego podamos almacenar los datos del archivo xml de la práctica 1 en objetos de estas clases.
 * 2. Utilizando DOM carga el contenido del archivo en una una colección de objetos.
 * 3. Añade al programa anterior una función para añadir un nuevo jugador a la lista de jugadores, añade dos jugadores y guarda el resultado final en el archivo xml utilizando serialización. Fíjate en las etiquetas generadas y en sus nombres y practica la utilización de alias.
 * 4. Deserializa el archivo XML creado en el punto anterior.
 */
package ad_u4_2;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author David Riquelme Tejero
 */
public class AD_U4_2 {

    //Ruta absoluta donde se encuentra el archivo xml
    private final String RUTA = "C:\\Users\\mdfda\\Documents\\NetBeansProjects\\AD_U4_1_1-master\\src\\ad_u4_2\\documento\\";
    private Jugadores listaJugadores;
    private Document documento;
    private File fichero;
    private Scanner sc = new Scanner(System.in);

    public AD_U4_2() {
        int opcion;
        boolean cambios = false;
        listaJugadores = new Jugadores();
        //Rellenar listaJugadores
        cargarXML();

        do {
            System.out.println("\nSelecciona opción (número): ");
            System.out.println("1. Listar jugadores");
            System.out.println("2. Añadir nuevo jugador");
            System.out.println("0. Salir");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    listarJugadores();
                    break;
                case 2:
                    cambios = true;
                    addJugador();
                    break;
                case 0:
                    if (cambios) {
                        System.out.println("Antes de irte, ¿como quieres serializar los cambios?");
                        System.out.println("1. Con alias");
                        System.out.println("2. Sin alias");
                        int serializar = sc.nextInt();
                        if (serializar == 2) {
                            guardarCambios(false);
                        } else {
                            guardarCambios(true);
                        }
                    } else {
                        System.out.println("Bye, bye");
                    }
                    sc.close();
                    break;
                default:
                    System.err.println("Tu elección esta equivocada. Vuelvela a hacer pero bien");
                    ;
            }
        } while (opcion != 0);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new AD_U4_2();
    }

    /**
     * Función para listar a los jugadores
     */
    public void listarJugadores() {
        int i;
        for (i = 0; i < listaJugadores.getJugadores().size(); i++) {
            System.out.println("************************************");
            System.out.println("***** CODIGO JUGADOR: " + i + " *****");
            System.out.println("Nombre: " + listaJugadores.getJugadores().get(i).getNombre());
            System.out.println("Nacionalidad: " + listaJugadores.getJugadores().get(i).getNacionalidad());
            System.out.println("Puntuación: " + listaJugadores.getJugadores().get(i).getPuntuacion());
            System.out.println("Nivel: " + listaJugadores.getJugadores().get(i).getNivel());
            System.out.println("Horas: " + listaJugadores.getJugadores().get(i).getHoras() + "\n");
            System.out.println("************************************");
        }
    }

    /**
     * Método para añadir un nuevo jugador
     */
    public void addJugador() {
        sc.nextLine();
        Jugador jugador = new Jugador();
        System.out.println("Introduce la nacionalidad del nuevo jugador: ");
        System.out.println("--> ES = España");
        System.out.println("--> US = Estadounidense");
        System.out.println("--> CO = Colombiana");
        System.out.println("--> FR = Francesa");
        System.out.println("--> IT = Italiana");
        jugador.setNacionalidad(sc.nextLine());
        System.out.println("Introduce el nombre del nuevo jugador: ");
        jugador.setNombre(sc.nextLine());
        System.out.println("Introduce las horas jugadas del jugador " + jugador.getNombre() + ": ");
        jugador.setHoras(sc.nextInt());
        sc.nextLine();
        System.out.println("Introduce el nivel del jugador " + jugador.getNombre() + ": ");
        jugador.setNivel(sc.nextInt());
        sc.nextLine();
        System.out.println("Introduce la puntuación del jugador " + jugador.getNombre() + ": ");
        jugador.setPuntuacion(sc.nextInt());
        sc.nextLine();
        listaJugadores.addJugador(jugador);
        System.out.println("Acabas de añadir un nuevo jugador");
    }

    /**
     * Método que carga, desde un xml, los datos que se usarán por la aplicación
     */
    public void cargarXML() {
        int respuesta;
        System.out.println("Selecciona como cargar los datos: ");
        System.out.println("1. Cargar xml por defecto (Jugadores.xml)");
        System.out.println("2. Cargar a partir de archivo serializado sin alias (JugadoresSerializados.xml, solo en caso de que exista)");
        System.out.println("3. Cargar a partir de archivo serializado con alias (JugadoresSerializadosConAlias.xml, solo en caso de que exista)");
        respuesta = sc.nextInt();
        while (respuesta > 3 || respuesta < 1) {
            System.err.println("Selecciona una opcion válida");
            System.out.println("Selecciona como cargar los datos: ");
            System.out.println("1. Cargar archivo xml (Jugadores.xml)");
            System.out.println("2. Cargar a partir de archivo serializado sin alias (JugadoresSerializados.xml, solo en caso de que exista)");
            System.out.println("3. Cargar a partir de archivo serializado con alias (JugadoresSerializadosConAlias.xml, solo en caso de que exista)");
            respuesta = sc.nextInt();
        }
        switch (respuesta) {
            case 1:
                fichero = new File(RUTA + "Jugadores.xml");
                XMLNormal();
                break;
            case 2:
                fichero = new File(RUTA + "JugadoresSerializados.xml");
                break;
            case 3:
                fichero = new File(RUTA + "JugadoresSerializadosConAlias.xml");
                break;
        }
        //Dado que el archivo por defecto SIEMPRE existirá solo si se decide cargar desde un archivo serializado podrá no existir el fichero
        if (respuesta != 1) {
            //En caso de que los archivos serializados no existan el xml se cargará de la manera tradicional
            if (!fichero.exists()) {
                System.err.println("El fichero serializado no existe");
                System.out.println("Se procede a cargar el fichero por defecto (Jugadores.xml)");
                fichero = new File(RUTA + "Jugadores.xml");
                XMLNormal();
            } else {
                XStream flujox = new XStream();
                //Este código es para evitar el error: Security framework of XStream not initialized, XStream is probably vulnerable.
                Class<?>[] classes = new Class[] { Jugador.class, Jugadores.class };
                XStream.setupDefaultSecurity(flujox);
                flujox.allowTypes(classes);
                //Linea para obtener el atributo nacionalidad
                flujox.aliasAttribute(Jugador.class, "nacionalidad", "nacionalidad");
                //En caso de que se haya usado alias para el proceso de serialización
                if (respuesta == 3) {
                    flujox.alias("Jugador", Jugador.class);
                    flujox.alias("Jugadores", Jugadores.class);
                    flujox.addImplicitCollection(Jugadores.class, "jugadores");
                }
                listaJugadores = (Jugadores) flujox.fromXML(fichero);
            }
        }
    }

    /**
     * Método para serializar los datos con los que se ha estado trabajando
     * @param alias
     */
    public void guardarCambios(boolean alias) {
        try {
            String nombreFichero = "JugadoresSerializados.xml";
            XStream flujox = new XStream();
            //Esta linea es para que sepa que la nacionalidad la tiene que mapear como un atributo de Jugador, no como un nodo hijo
            flujox.aliasAttribute(Jugador.class, "nacionalidad", "nacionalidad");
            if (alias) {
                flujox.alias("Jugador", Jugador.class);
                flujox.alias("Jugadores", Jugadores.class);
                flujox.addImplicitCollection(Jugadores.class, "jugadores");
                nombreFichero = "JugadoresSerializadosConAlias.xml";
            }
            //Guardar en fichero .xml (es importante poner la RUTA, sino no crea ningún archivo)
            flujox.toXML(listaJugadores, new FileOutputStream(RUTA+nombreFichero));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AD_U4_2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para realizar la carga del xml de manera tradicional
     */
    public void XMLNormal() {
        try {
            //Cargamos el xml de la manera tradicional
            DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factoria.newDocumentBuilder();
            documento = builder.parse(fichero);
            //A partir del documento xml convertir los nodos en objetos de Jugador y guardarlos en listaJugadores
            rellenarJugadores();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(AD_U4_2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para, a partir del archivo xml, crear objetos de tipo Jugador y
     * almacenarlos en un ArrayList
     */
    public void rellenarJugadores() {
        int i, j;
        Jugador jugador;
        NodeList jugadores = documento.getElementsByTagName("Jugador"), propiedades;

        for (i = 0; i < jugadores.getLength(); i++) {
            propiedades = jugadores.item(i).getChildNodes();
            jugador = new Jugador();
            //Bucle para recorrer los hijos del nodo jugador y setear las propiedades del objeto jugador
            for (j = 0; j < propiedades.getLength(); j++) {
                switch (propiedades.item(j).getNodeName()) {
                    case "nombre":
                        jugador.setNombre(propiedades.item(j).getTextContent());
                        break;
                    case "horas_jugadas":
                        jugador.setHoras(Integer.parseInt(propiedades.item(j).getTextContent()));
                        break;
                    case "nivel":
                        jugador.setNivel(Integer.parseInt(propiedades.item(j).getTextContent()));
                        break;
                    case "puntuacion":
                        jugador.setPuntuacion(Integer.parseInt(propiedades.item(j).getTextContent()));
                        break;
                }
            }
            //Accedo a los atributos del jugador, en concreto a la nacionalidad, y la seteo
            jugador.setNacionalidad(jugadores.item(i).getAttributes().getNamedItem("nacionalidad").getTextContent());
            listaJugadores.getJugadores().add(jugador);
        }
    }
}
