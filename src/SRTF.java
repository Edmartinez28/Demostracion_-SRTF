import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SRTF {

    static List<Proceso> procesos = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   INGRESO DE LOS PROCESOS    <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        /*
        System.out.println(" Ingrese los procesos a Ejecutar\n--------------------------------- ");
        boolean x = true;
        do{
            System.out.println("Ingrese proceso:______________________");
            System.out.print("Ingrese el nombre del proceso: ");
            String nombre = sc.next();
            System.out.print("Ingrese el tiempo de llegada del proceso: ");
            int llegada = sc.nextInt();
            System.out.print("Ingrese el tiempo de ejecución del proceso: ");
            int ejecucion = sc.nextInt();
            System.out.print("Ingrese la prioridad del proceso: ");
            int prioridad = sc.nextInt();

            procesos.add(new Proceso(nombre,ejecucion,llegada,prioridad));

            System.out.println("\nDesea Ingresar un nuevo proceso (s/n):");
            String c = sc.next();
            x = (c.equals("S") || c.equals("s") )?true:false;
            System.out.println(" ");
        }while(x == true);*/

        procesos.add(new Proceso(" A ",3,2,2));
        procesos.add(new Proceso(" B ",2,1,3));
        procesos.add(new Proceso(" C ",1,3,1));
        procesos.add(new Proceso(" D ",3,4,3));
        procesos.add(new Proceso(" E ",6,0,4));
        procesos.add(new Proceso(" F ",4,3,5));

        // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>   PRESENTACIÓN DE LOS PROCESOS    <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

        System.out.println("+--------------------+-----+-----+-----+\n| Nombre Proceso     | tLl | tEj | Pri |\n+--------------------+-----+-----+-----+");
        for (var p: procesos) {
            System.out.printf("| %18s | %3d | %3d | %3d |\n",p.getNomProceso(),p.getTimeLlegada(),p.getTimeEjecucion(),p.getPrioridad());
        }
        System.out.println("+--------------------+-----+-----+-----+\n");

        // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>   ORDENACIÓN DE LOS PROCESOS    <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

        String[] resultado = srtf(procesos);
        for (int z=0 ; z<resultado.length ; z++){
            System.out.print("+-------");
        }
        System.out.println("+");
        for (var y: resultado ) {
            System.out.printf("| %5s ",y.substring(0,3));
        }
        System.out.println("|");
        for (int z=0 ; z<resultado.length ; z++){
            System.out.print("+-------");
        }
        System.out.println("+");

        presentarResultados(porOrdenLlegada(procesos),resultado);

    }

    private static String[] srtf(List<Proceso> pro){

        int tiempoProcesos = 0;
        for (var p:pro) {
            tiempoProcesos += p.getTimeEjecucion();
        }
        /*
        String[] vector = new String[tiempoProcesos];

        int aux = 0;
        for (var t: porOrdenLlegada(pro) ) {
            for(int r=0 ; r<t.getTimeEjecucion() ; r++){
                vector[aux] = t.getNomProceso();
                aux++;
            }
        }*/
        return ejecutar(porOrdenLlegada(pro));
    }

    private static List<Proceso> porOrdenLlegada(List<Proceso> list) {

        List<Proceso> ordenada = new ArrayList<>();
        List<Integer> llegadas = new ArrayList<>();
        llegadas.add(list.get(0).getTimeLlegada());

        for (var o : list) {
            int aux = o.getTimeLlegada();
            if (!llegadas.contains(aux)) {
                llegadas.add(aux);
            }
        }

        Collections.sort(llegadas);

        for(int aux=0 ; aux < llegadas.size() ; aux++){
            for (var te: list) {
                if(te.getTimeLlegada() == llegadas.get(aux)){
                    ordenada.add(te);
                }
            }
        }

        return ordenada;
    }

    private static List<Proceso> porOrdenEjecucion(List<Proceso> list) {

        List<Proceso> ordenada = new ArrayList<>();
        List<Integer> ejecucion = new ArrayList<>();
        ejecucion.add(list.get(0).getTimeEjecucion());

        for (var o : list) {
            int aux = o.getTimeEjecucion();
            if (!ejecucion.contains(aux)) {
                ejecucion.add(aux);
            }
        }

        Collections.sort(ejecucion);

        for(int aux=0 ; aux < ejecucion.size() ; aux++){
            for (var te: list) {
                if(te.getTimeEjecucion() == ejecucion.get(aux)){
                    ordenada.add(te);
                }
            }
        }

        System.out.println("Los procesos ordenados son:");
        for (var t: ordenada) {
            System.out.print(t.nomProceso+">"+t.getTimeEjecucion()+" ");
        }
        System.out.println(" ");
        return ordenada;
    }

    private static String[] ejecutar(List<Proceso> list) {
        List<Proceso> enEspera = new ArrayList<>();
        int tiempoProcesos = 0;
        for (var p : list) {
            tiempoProcesos += p.getTimeEjecucion();
        }
        String[] vector = new String[tiempoProcesos];

        for (int y = 0; y < vector.length; y++) {
            for (var p : list) {
                if (p.getTimeLlegada() == y) {
                    enEspera.add(p);
                }
            }

            if (enEspera.size() >=2){
                enEspera = porOrdenEjecucion(enEspera);
            }

            if(enEspera.get(0).getTimeEjecucion() <= 0){
                enEspera.remove(0);
            }
            vector[y] = enEspera.get(0).getNomProceso();

            enEspera.get(0).setTimeEjecucion(enEspera.get(0).getTimeEjecucion() -1);
        }
        return vector;
    }

    private static void presentarResultados(List<Proceso> list, String[] vector){

        int contadorEspera = 0;
        int contadorRetorno = 0;

        System.out.println("\n---  RESULTADOS  ----------------------------------------");
        System.out.println("\n+-----------------------------+-------------------+");
        System.out.printf("|%27s  | %17s |","Tiempo de Espera","Tiempo de Retorno");
        System.out.println("\n+-----------------------------+-------------------+");
        for (var l:list) {
            int pos = 0;
            for (int g=vector.length-1 ; g>=0 ; g--){
                if(l.getNomProceso().equals(vector[g])){
                    pos = g;
                }
            }
            System.out.printf("| %10s: (%2d - %2d) = %2d  |  %10s = %2d  |\n",l.getNomProceso(),pos,l.getTimeLlegada(),(pos-l.getTimeLlegada()),l.getNomProceso(),(pos+l.getTimeEjecucion()));
            contadorEspera += (pos-l.getTimeLlegada());
            contadorRetorno += (pos+l.getTimeEjecucion());
        }

        DecimalFormat format2 = new DecimalFormat("#.##");
        System.out.println("+-----------------------------+-------------------+");
        System.out.printf("|%27s  | %17s |","Promedio de Espera","Promedio Retorno");
        System.out.println("\n+-----------------------------+-------------------+");
        System.out.printf("| %10s:(%2d / %2d) = %s | Med:(%2d / %2d) = %s|","Media",contadorEspera , list.size() ,
                format2.format(contadorEspera/(float)list.size()) , contadorRetorno , list.size() , format2.format(contadorRetorno/(float)list.size()));
        System.out.println("\n+-----------------------------+-------------------+");
    }
}
