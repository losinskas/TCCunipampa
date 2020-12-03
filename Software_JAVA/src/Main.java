public class Main {
//  Método principal para os inputs de dados iniciais
    public static void main(String[] args) {
        double Q, Q_min, H, H_max, h_sum, Z_b, eta_v, eta_m, n;
/*
        Q = 15;         // Vazão de entrada [m³/s]
        Q_min = 10;     // Vazão mínima [m³/s]
        H = 60;         // Altura de operação [m]
        H_max = 65;     // Altura máxima de operação [m]
        h_sum = 2;      // Altura minima de operação afogada [m]
        Z_b = 200;      // Altura em relação ao nível do mar [m]
        eta_v = 0.99;   // Rendimento volumétrico do rotor
        eta_m = 0.97;   // Remdimento mecânico do conjunto Q = 15;         // Vazão de entrada [m³/s]

        Q = 3.6;
        Q_min = 10;     // Vazão mínima [m³/s]
        H = 21.35;         // Altura de operação [m]
        H_max = 22;     // Altura máxima de operação [m]
        h_sum = 2;      // Altura minima de operação afogada [m]
        Z_b = 500;      // Altura em relação ao nível do mar [m]
        eta_v = 0.96;   // Rendimento volumétrico do rotor
        eta_m = 0.91;   // Remdimento mecânico do conjunto
*/
        Q = 3.8;         // Vazão de entrada [m³/s]
        Q_min = 2;     // Vazão mínima [m³/s]
        H = 21.7;         // Altura de operação [m]
        H_max = 30;     // Altura máxima de operação [m]
        h_sum = 2;      // Altura minima de operação afogada [m]
        Z_b = 200;      // Altura em relação ao nível do mar [m]
        eta_v = 0.95;   // Rendimento volumétrico do rotor
        eta_m = 0.97;   // Remdimento mecânico do conjunto// Cálculos iniciais do rotor
        n = 450;        // Rotação da máquina [rpm]

        Geometry geometry = new Geometry(Q, Q_min, H, H_max, h_sum, Z_b, eta_v, eta_m, n);
        geometry.GerarPlanilha();
        geometry.GerarPontos();
        double eta_i = geometry.getEta_i();
        n = geometry.getN();
        double D5e = geometry.getD_5e();
        double nqar = geometry.getN_qar();
        double D3i = geometry.getD_3i();
        double D3e = geometry.getD_3e();
        double b0 = geometry.getB_0();
        Espiral espiral = new Espiral(H, eta_i, n, Q, nqar, D5e, D3i,D3e,b0);
        espiral.GerarDados();

        double sj[] = {0,0.04298,0.08595,0.1289,0.171918,0.21489,0.25787};
        double D[] = {0.5983,0.59745,0.59853,0.60241,0.61061,0.62686,0.67237};
        double ri = 0.9031;
        double re = 0.05144;
        double fem =0.95;
        double cm = geometry.getC_m();
        double Dm =D[4];
        double Qr = geometry.getQ_r();
        double s = 0.25787;
        double D5m[] = {0.2,0.24,0.31,0.38,0.49,0.59,0.7};
        double rg = 0.550;
        double Lg = 0.330;
        double teta[] = {90, 58.07,81.85,63.99,90,90,90};
        double zeta[] = {45,25.7,61.51,53.67,82.26,74.41,90};

        Geometry1 geometry1 = new Geometry1(sj,D,cm ,fem,Dm,Qr,re,ri, s, D5m, n, eta_i, H, rg, Lg, teta, zeta);
        geometry1.GerarPontos();

         sj = new double[]{0, 0.0456, 0.0912, 0.1368, 0.1824, 0.228, 0.2736};
         D = new double[]{0.60185, 0.604, 0.60736, 0.61241, 0.62013, 0.63298, 0.68044};
         ri = 0.538122;
         re = 0.0705;
         fem =0.95;
         cm = geometry.getC_m();
         Dm =D[4];
         Qr = geometry.getQ_r();
         s = 0.27357;
         D5m = new double[]{0.37862, 0.43202, 0.490322, 0.562778, 0.630244, 0.706283};
         rg = 0.550;
         Lg = 0.330;
         teta = new double[]{90, 58.07, 81.85, 63.99, 90, 90, 90};
         zeta = new double[]{45, 25.7, 61.51, 53.67, 82.26, 74.41, 90};

        Geometry1 geometry2 = new Geometry1(sj,D,cm ,fem,Dm,Qr,re,ri, s, D5m, n, eta_i, H, rg, Lg, teta, zeta);
        geometry1.GerarPontos();

    }
}
