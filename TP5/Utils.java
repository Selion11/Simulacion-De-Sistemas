package TP5;
import TP5.Jugador.Jugador;
import TP5.Jugador.JugadorAzul;
import TP5.Jugador.JugadorRojo;
import java.util.List;
import java.util.Optional;

public class Utils {

    public static final double FIELD_HEIGHT = 70.0; // Altura del campo (paredes en y = 0 y y = 70)

    // Parámetros para la función sigmoidal

    // Controla la pendiente de la función
    // Si el jugador hace cambios de dirección abruptos, reducir un poco A.
    private static final double A = 1;

    // Controla el desplazamiento para asegurar s_a(0) = 0.99
    // Si el jugador reacciona demasiado tarde, prueba con valores de B más bajos.
    private static final double B = -0.5;

    public static double calcularSigmoid(double tiempoColision) {
        System.out.println("Valor Sa: " + 1.0 / (1.0 + Math.exp(A * (tiempoColision + B))));
        return 1.0 / (1.0 + Math.exp(A * (tiempoColision + B)));
    }


    // Método para calcular la dirección hacia un objetivo
    public static double[] calcularDireccion(double posX, double posY, double objetivoX, double objetivoY) {
        double dirX = objetivoX - posX;
        double dirY = objetivoY - posY;
        double magnitud = Math.sqrt(dirX * dirX + dirY * dirY);
        if (magnitud > 0) {
            dirX /= magnitud;
            dirY /= magnitud;
        }
        return new double[]{dirX, dirY};
    }

    public static boolean detectarColision(Jugador jugador1, Jugador jugador2) {
        double dx = jugador1.getPosX() - jugador2.getPosX();
        double dy = jugador1.getPosY() - jugador2.getPosY();
        double distancia = Math.sqrt(dx * dx + dy * dy);
        return distancia < (jugador1.getRadio() + jugador2.getRadio());
    }


    /**
     * Calcula la distancia entre dos particulas para despues poder calcular la variable de contacto
     * @param p1: Particula 1
     * @param p2: Particula 2
     * @return: retorna la distancia entre las particulas en formato double
     */
    static private double playerDistance(Jugador p1,Jugador p2){
        double x = Math.pow(p1.getPosX() - p2.getPosX(),2);
        double y = Math.pow(p1.getPosY() - p2.getPosY(),2);
        return Math.sqrt(x + y);
    }

    /**
     * Calcula la fuerza normal desde la particula 1 hacia la particula 2
     * @param p1: Particula desde la cual se calcula la normal
     * @param p2: PArtiucla hacia la cual se calcula la normal
     * @return: Retorna un Double[] con la componente x de la fuerza en [0] y la componente Y de la misma en [1]
     */
    static private Double[] calculateNormal(Jugador p1,Jugador p2){
        Double[] normal = new Double[2];
        double distace = playerDistance(p1,p2);
        double xComponent = (p2.getPosX() - p1.getPosX())/distace;
        double yComponent = (p2.getPosY() - p1.getPosY())/distace;
        normal[0] = xComponent;
        normal[1] = yComponent;
        return normal;
    }

    /**
     * Variable de contacto debe utilizarse en los calculos de la fuerza granular, fuerza del sistema y para saber si dos particulas estan en contacto (caso en el que el valor es <0)
     * @param p1: Jugador 1
     * @param p2: Jugador 2
     * @return: retorna un double que es <0 si estan en contacto las particulas y >0 si no lo estan
     */
    static public double contactVariable(Jugador p1,Jugador p2){
        return playerDistance(p1,p2) -(p1.getRadio()*2);
    }

    /**
     * Calcular la fuerza total del sistema para la particula 1
     * @param p1: Particula para la cual se va a calcular la fuerza del sistema
     * @param inContact:Lista con todas las particulas que estan en contacto con la p1
     * @param kn: k en la direccion normal
     * @param kt: k en la direccion tangencial
     * @return: decuelve un Double[] donde [0] es la componente en x y [1] la componente en y
     */
    public static Double[] calculateAcceleration(Jugador p1,List<JugadorAzul> inContact, double kn, double kt){
        Double[] ans = new Double[2];
        Double[] wish = wishForce(p1.getTargetX(), p1.getTargetY(), p1.getVelocidadMaxima(),p1.getVelX(), p1.getVelY(),p1.getWeight(),p1.getTau());
        Double[] granular = new Double[2];
        granular[0] = 0.0;
        granular[1] = 0.0;
        if(!inContact.isEmpty()){//Si hay particulas en contacto para calcular la fuerza granular de las mismas
            for(Jugador j: inContact){
                //Contact variable siempre va a dar <0 pq es lo que se deberia usar para llenar la lista inContact
                double contactVariable = contactVariable(p1,j);
                Double[] aux1 = granularForce(p1,j,contactVariable,kn,kt);
                granular[0] += aux1[0];
                granular[1] += aux1[1];
            }
        }

        ans[0] = (wish[0]+granular[0])/p1.getWeight();
        ans[1] = (wish[1]+granular[1])/p1.getWeight();

        return ans;
    }

    /**
     * Calcula la fuerza granular de la particula 1 hacia la 2
     * @param p1: Particula desde la cual se calcula la fuerza granular
     * @param p2: Particula hacia la cual se calcula la fuerza granular
     * @param contactVariable: Valor del coeficiente de superposicion de las particualas (deberia ser <0 pues si no lo es no estaria calculando est0
     * @param kn: k en la direcion normal
     * @param kt: k en la direccion tangencial
     * @return: Retorna un Double[] con la componente x de la fuerza en [0] y la componente Y de la misma en [1]
     */
    static private Double[] granularForce(Jugador p1,Jugador p2,double contactVariable,double kn,double kt) {
        Double[] normal = calculateNormal(p1,p2);
        Double[] tanget = new Double[2];
        tanget[0] = -normal[1];
        tanget[1] = normal[0];

        Double[] forceN = new Double[2];
        double ncte = -contactVariable*kn;
        forceN[0] = normal[0] * ncte;
        forceN[1] = normal[1] * ncte;

        Double[] forceT = new Double[2];
        double tcte = p1.getVelX()*tanget[0] + p1.getVelY()*tanget[1];
        tcte *= kt*contactVariable;
        forceT[0] = tcte*tanget[0];
        forceT[1] = tcte*tanget[1];

        Double[] force = new Double[2];
        force[0] = forceT[0]+forceN[0];
        force[1] = forceT[1]+forceN[1];

        return force;
    }

    /**
     * La fuerza de deseo para una particula deberiamos calcular esto siempre para todas las particulas
     * @param tempX: Objetivo temporal de la partiucla (componente x)
     * @param tempY: Objetivo temporal de la partiucla (componente y)
     * @param maxV: Velocidad maxima a la que desea llegar la particula que estamos estudiando
     * @param xVel: Componente x de la velocidad
     * @param yVel: Componente y de la velocidad
     * @param w: Peso de la particula
     * @param tau: Coeficiente del tiempo de reaccion
     * @return: retorna un Double[] con la componente x en [0] y la componente y en [1]
     */
    static private Double[] wishForce(double tempX, double tempY,double maxV, double xVel,double yVel,double w,double tau){
        Double[] force = new Double[2];//0 x component | 1 y component
        double constant = (w/tau);
        double xComponent = constant*(maxV*tempX-xVel );
        double yComponent = constant*(maxV*tempY-yVel);
        force[0] = xComponent;
        force[1] = yComponent;
        return force;
    }
}

