import javax.persistence.*;
@Entity
@Table(name = "PARTIDAS")
public class Partidas {

    public Partidas(int blancas, int negras, char ganador, String inicio, String fin) {
        this.blancas = blancas;
        this.negras = negras;
        this.ganador = ganador;
        this.inicio = inicio;
        this.fin = fin;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name="ID")
    private int id;
    @Column(name="BLANCAS")
    private int blancas;
    @Column(name="NEGRAS")
    private int negras;
    @Column(name="GANAN")
    private char ganador;
    @Column(name="INICIO")
    private String inicio;
    @Column(name="FIN")
    private String fin;

    public Partidas() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
