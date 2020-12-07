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
        Espiral espiral = new Espiral(H, eta_i, n, Q, nqar, D5e, D3i, D3e, b0);
        espiral.GerarDados();

        double sj[] = {0, 0.04298, 0.08595, 0.1289, 0.171918, 0.21489, 0.25787};
        double D[] = {0.5983, 0.59745, 0.59853, 0.60241, 0.61061, 0.62686, 0.67237};
        //double D[] = {0.67237,0.62686,0.61061,0.60241,0.59853,0.59745,0.5983};
        double ri = 0.9031;
        double re = 0.05144;
        double fem = 0.95;
        double cm = geometry.getC_m();
        double Dm = D[4];
        double Qr = geometry.getQ_r();
        double s = 0.25787;
        double D5m[] = {0.2, 0.24, 0.31, 0.38, 0.49, 0.59, 0.7};
        double rg = 0.46864;
        double Lg = 0.33646;
        double teta[] = {76.82, 65.34, 87.62, 79.26, 87.9, 89.48, 85};
        double zeta[] = {11.5, 33.14, 67.95, 72.71, 83.91, 89.43, 83};

        Geometry1 geometry1 = new Geometry1(sj, D, cm, fem, Dm, Qr, re, ri, s, D5m, n, eta_i, H, rg, Lg, teta, zeta);
        geometry1.GerarPontos();

        //sj = new double[]{0, 0.107, 0.209, 0.322, 0.425, 0.538, 0.644};
        sj = new double[]{0.644, 0.538, 0.425, 0.322, 0.209, 0.107, 0};
        D = new double[]{0.696, 0.589, 0.490, 0.389, 0.310, 0.243, 0.200};
        //D = new double[]{0.2, 0.243, 0.310, 0.389, 0.490, 0.589, 0.696};
        ri = 0.538122;
        re = 0.0705;
        fem = 0.95;
        cm = geometry.getC_m();
        // cm = 17.8;
        Dm = D[4];
        Qr = geometry.getQ_r();
        s = 0.644;
        D5m = new double[]{0.67237, 0.62686, 0.61061, 0.60241, 0.59853, 0.59745, 0.5983};
        rg = 0.550;
        Lg = 0.330;
        teta = new double[]{90, 81.27, 70.5, 75.98, 68.57, 70.61, 83};
        zeta = new double[]{1, 2.8, 10, 20.82, 27.37, 37.59, 57.46};
        nqar = geometry.getN_qar();
        //double rgj[] = {199.73,206.166,218.33,234.32,258.87,298.968,338.182};
        double rgj[] = {0.338182, 0.298968, 0.25887, 0.23432, 0.21833, 0.206166, 0.19973};
        double Lgj[] = {0.419832, 0.406687, 0.37408, 0.336446, 0.283311, 0.228167, 0.19};
        double cu4j[] = geometry1.getCuj();
        D3i = geometry.getD_3i();
        D3e = geometry.getD_3e();

        Geometry2 geometry2 = new Geometry2(sj, D, cm, fem, Dm, Qr, re, ri, s, D5m, n, eta_i, H, rg, Lg, teta, zeta, nqar, rgj, Lgj, cu4j, D3i, D3e);
        geometry2.GerarPontos();

        //sj = new double[]{0, 0.04298, 0.08595, 0.1289, 0.171918, 0.21489, 0.25787};
        sj = new double[]{0.2578, 0.21489, 0.171, 0.1289, 0.08595, 0.04298, 0};
        double []beta_4m = geometry1.getBeta_j();
        double bm = geometry1.getBm();
        PerfilPa perfilPa = new PerfilPa(sj, s,beta_4m, bm,H, Lgj);

    }
}
