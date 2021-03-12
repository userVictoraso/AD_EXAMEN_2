import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    static HashMap<Integer, Double> listaDePuntos;
    static ArrayList<String> listaDePartidas = new ArrayList<>();
    static ArrayList<Clasificacion> listaClasificacion = new ArrayList<>();
    static int numPartidasActual = 0;
    static int NUM_PARTIDAS = 190;


    static Session session;
    static Transaction transaction;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        generarHashMap();

        while (numPartidasActual < NUM_PARTIDAS) {
            generarPartida();
        }

        mostrarJugadores();
    }

    private static void generarPartida() {
        int idBlancas = (int) (Math.random() * 20 + 1);
        char gana = ' ';
        double contador = 0.0;

        int idNegras = idBlancas;
        while (idNegras == idBlancas) {
            idNegras = (int) (Math.random() * 20 + 1);
        }

        if (!comprobarPartida(idBlancas, idNegras)) {
            int ganador = (int) (Math.random() * 3 + 1);
            if (ganador == 1) {
                gana = 'B';
                contador = listaDePuntos.get(idBlancas);
                contador++;
                listaDePuntos.put(idBlancas, contador);
            } else if (ganador == 2) {
                gana = 'N';
                contador = listaDePuntos.get(idNegras);
                contador++;
                listaDePuntos.put(idNegras, contador);
            } else {
                gana = 'T';
                contador = listaDePuntos.get(idBlancas);
                contador = contador + 0.5;
                listaDePuntos.put(idBlancas, contador);
                contador = 0;
                contador = listaDePuntos.get(idNegras);
                contador = contador + 0.5;
                listaDePuntos.put(idNegras, contador);
            }
            Partidas p = new Partidas(idBlancas, idNegras, gana, generarFechaInicial(), generarFechaFinal());
            guardarPartidaEnBD(p);
            numPartidasActual++;
            System.out.println("Partida creada");
        } else {
            System.out.println("La partida ya existe.");
        }
    }

    public static void guardarPartidaEnBD(Partidas partida) {
        session = null;
        transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.save(partida);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean comprobarPartida(int idBlancas, int idNegras) {
        String codigoPartida;
        boolean existe = false;

        if (idBlancas < idNegras) {
            codigoPartida = idBlancas + "" + idNegras;
        } else {
            codigoPartida = idNegras + "" + idBlancas;
        }

        for (String s : listaDePartidas) {
            if (codigoPartida.equals(s)) {
                existe = true;
            }
        }
        if (existe == false) {
            listaDePartidas.add(codigoPartida);
        }
        return existe;
    }

    private static void generarHashMap() {
        listaDePuntos = new HashMap<>();

        for (int i = 1; i <= 20; i++) {
            listaDePuntos.put(i, 0.0);
        }
    }

    private static String generarFechaInicial() {
        LocalDateTime inicio = LocalDateTime.now();
        String fechaInicio = inicio.format(formatter);
        return fechaInicio;
    }

    private static String generarFechaFinal() {
        int tiempo = (int) (Math.random() * 472 + 8);
        LocalDateTime fin = LocalDateTime.now().plusMinutes(tiempo);
        String fechaFin = fin.format(formatter);
        return fechaFin;
    }

    private static void mostrarJugadores() {
        session = null;
        transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            Query queryAll = session.createQuery("from Jugadores");
            List<Jugadores> jugadores = queryAll.list();

            for (Jugadores j : jugadores) {
                Clasificacion c = new Clasificacion(j.getId(), j.getNombreJugador(), listaDePuntos.get(j.getId()));
                listaClasificacion.add(c);
            }
            //ORDENAR
            Collections.sort(listaClasificacion);
            for (Clasificacion c : listaClasificacion) {
                System.out.println(c.toString());
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
