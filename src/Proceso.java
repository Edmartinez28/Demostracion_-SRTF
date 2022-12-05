public class Proceso {
    public String nomProceso;
    private int   timeEjecucion , timeLlegada , prioridad;

    public Proceso(String nomProceso, int timeEjecucion, int timeLlegada, int prioridad) {
        this.nomProceso = nomProceso;
        this.timeEjecucion = timeEjecucion;
        this.timeLlegada = timeLlegada;
        this.prioridad = prioridad;
    }

    public Proceso(String nomProceso, int timeEjecucion, int timeLlegada) {
        this.nomProceso = nomProceso;
        this.timeEjecucion = timeEjecucion;
        this.timeLlegada = timeLlegada;
        this.prioridad = 3;
    }

    public String getNomProceso() {
        return nomProceso;
    }

    public int getTimeEjecucion() {
        return timeEjecucion;
    }

    public int getTimeLlegada() {
        return timeLlegada;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public void setTimeEjecucion(int timeEjecucion) {
        this.timeEjecucion = timeEjecucion;
    }
}
