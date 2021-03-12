import javax.persistence.*;
@Entity
@Table(name = "JUGADORES")
public class Jugadores {
    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getNacionalidadJugador() {
        return nacionalidadJugador;
    }

    public void setNacionalidadJugador(String nacionalidadJugador) {
        this.nacionalidadJugador = nacionalidadJugador;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name="ID")
    private int id;
    @Column(name="NOMBRE")
    private String nombreJugador;
    @Column(name="NACIONALIDAD")
    private String nacionalidadJugador;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nombreJugador='" + nombreJugador;
    }

}
