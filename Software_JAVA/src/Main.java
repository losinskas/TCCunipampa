public class Main {
//  Método principal para os inputs de dados iniciais
    public static void main(String[] args) {
        double Q, Q_min, H, H_max, h_sum, Z_b, eta_v, eta_m;
/*
        Q = 15;         // Vazão de entrada [m³/s]
        Q_min = 10;     // Vazão mínima [m³/s]
        H = 60;         // Altura de operação [m]
        H_max = 65;     // Altura máxima de operação [m]
        h_sum = 2;      // Altura minima de operação afogada [m]
        Z_b = 200;      // Altura em relação ao nível do mar [m]
        eta_v = 0.99;   // Rendimento volumétrico do rotor
        eta_m = 0.97;   // Remdimento mecânico do conjunto Q = 15;         // Vazão de entrada [m³/s]
        */
        Q = 3.6;
        Q_min = 10;     // Vazão mínima [m³/s]
        H = 21.35;         // Altura de operação [m]
        H_max = 22;     // Altura máxima de operação [m]
        h_sum = 2;      // Altura minima de operação afogada [m]
        Z_b = 500;      // Altura em relação ao nível do mar [m]
        eta_v = 0.96;   // Rendimento volumétrico do rotor
        eta_m = 0.91;   // Remdimento mecânico do conjunto
        /*
        Q = 3.8;         // Vazão de entrada [m³/s]
        Q_min = 2;     // Vazão mínima [m³/s]
        H = 21.7;         // Altura de operação [m]
        H_max = 30;     // Altura máxima de operação [m]
        h_sum = 2;      // Altura minima de operação afogada [m]
        Z_b = 200;      // Altura em relação ao nível do mar [m]
        eta_v = 0.99;   // Rendimento volumétrico do rotor
        eta_m = 0.97;   // Remdimento mecânico do conjunto// Cálculos iniciais do rotor
*/
        Geometry geometry = new Geometry(Q, Q_min, H, H_max, h_sum, Z_b, eta_v, eta_m);
        geometry.GerarPlanilha();
        geometry.GerarPontos();
        double eta_i = geometry.getEta_i();
        double n = geometry.getN();
        double D5e = geometry.getD_5e();
        double nqar = geometry.getN_qar();

        Espiral espiral = new Espiral(H, eta_i, n, Q, nqar, D5e);
        espiral.GerarDados();
    }
}
