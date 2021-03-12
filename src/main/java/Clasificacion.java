public class Clasificacion implements Comparable<Clasificacion>{
    int id;
    String nombre;
    double puntos;

    public Clasificacion(int id, String nombre, double puntos) {
        this.id = id;
        this.nombre = nombre;
        this.puntos = puntos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPuntos() {
        return puntos;
    }

    public void setPuntos(double puntos) {
        this.puntos = puntos;
    }


    @Override
    public String toString() {
        return "Clasificacion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", puntos=" + puntos +
                '}';
    }

    @Override
    public int compareTo(Clasificacion c) {
        if(c.getPuntos() > puntos){
            return 1;
        } else {
            return -1;
        }
    }
}
