import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Geometry {
    // --- Dados de entrada --- //
    private double Q;           // Vazão nominal do rotor [m³/s]
    private double Q_min;       // Vazão mínima de operação [m³/s]
    private double H;           // Altura de operação [m]
    private double H_max;       // Altura máxima de operação [m]
    private double h_sum;       // Altura miníma afogada [m]
    private double Z_b;         // Altura em relação ao nível do mar [m]
    private double eta_v;       // Rendimento volumétrico do rotor
    private double eta_m;       // Rendimento mecanico do conjunto
    // --- Dados Calculados --- //
    private double y;           // Salto energético [J/kg]
    private double toma_min;    // Toma minímo
    private double n_qa;        // n_qa Rotação específica em [rpm]
    private double q_r11;       // Vazão biunitária no rotor [m³/s]
    private double n;           // Rotação da máquina [rpm]
    private double z_p;         // Número de pólos
    private double n_qar11;     // Rotação específica do rotor biunitaria [rpm]
    private double q_m;         // Vazão [m³/s]
    private double toma_m;      // Toma m
    private double h_sum1;      // Altura de sucção [mca]
    private double n_qr11;      // [rpm]
    private double eta_i;       // Rendimento interno
    private double q_r;         // Vazão do rotor [m³/s]
    private double n_qar;       // Rotação [rpm]
    private double d_5e;        // Diâmetro de saída d_5e [m]
    private double b_0;         // Largura do distribuidor [m]
    private double d_4e;        // Diâmetro externo de entrada [m]
    private double d_4i;        // Diâmetro interno de entrada [m]
    private double d_4m;        // Diâmetro médio de entrada [m]
    private double u_4m;        // Velocidade média na aresta de entrada [m/s]
    private double d_3e;        // Diâmentro externo da coroa externo [m]
    private double c_m;         // Velocidade no tubo de admissão [m/s]
    private double c_u4m;       // Velocidade tangencial [m/s]
    private double beta_4m;     // Ângulo de direção da pá de entrada do rotor [graus º]
    private double d_3i;        // Diâmetro interno da coroa [m]
    private double d_5i;        // Diametro interno da coroa d_5i [m]
    private double li;          // Altura interna da coroa [m]
    private double le;          // Altura da coroa externa [m]
    private double l_5e;        // Altura do ponto d_3i até d_5e [m]
    private double l_4i;        // Comprimento do arco de d_4i até d_5i [m]
    private double l_4e;        // Comprimento do arco de d_4e até d_5e [m]
    private double p_emax;      // Potência máxima no eixo [kW]
    private double d;           // Diâmetro do eixo [mm]
    private double y_ij[];      // Equação para traçar a coroa externa [m]
    private double x_ij[];      // Pontos para a determinação da equação
    private double y_em;        // Espessura máxima da coroa externa [m]
    private double y_ej[];      // Equação para o traçado da coroa interna [m]
    private double x_ej[];      // Pontos para equação [m]

    private static final String filename = "C:/Users/lucas/OneDrive/Área de Trabalho/ProjectTurbine/planilha.xls";

    public void GerarPlanilha() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet1 = workbook.createSheet("Geometria1");
        HSSFSheet sheet = workbook.createSheet("Geometria");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("x");
        cell = row.createCell(1);
        cell.setCellValue("y");
        cell = row.createCell(2);
        cell.setCellValue("x");
        cell = row.createCell(3);
        cell.setCellValue("y");
        for (int i = 0; i < x_ej.length; i++) {
            row = sheet.createRow(i + 1);
            cell = row.createCell(0);
            cell.setCellValue(x_ej[i]);
            cell = row.createCell(1);
            cell.setCellValue(y_ej[i]);
            cell = row.createCell(2);
            cell.setCellValue(x_ij[i]);
            cell = row.createCell(3);
            cell.setCellValue(y_ij[i]);

        }
        row = sheet1.createRow(0);
        cell = row.createCell(0);
        cell.setCellValue("parameter_Name");
        cell = row.createCell(1);
        cell.setCellValue("Equation");
        cell = row.createCell(2);
        cell.setCellValue("Unit/Type");
        cell = row.createCell(3);
        cell.setCellValue("Comment");
        // Linha 1
        row = sheet1.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("D5e");
        cell = row.createCell(1);
        cell.setCellValue(d_5e);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 2
        row = sheet1.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("b0");
        cell = row.createCell(1);
        cell.setCellValue(b_0);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 3
        row = sheet1.createRow(3);
        cell = row.createCell(0);
        cell.setCellValue("D4e");
        cell = row.createCell(1);
        cell.setCellValue(d_4e);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 4
        row = sheet1.createRow(4);
        cell = row.createCell(0);
        cell.setCellValue("D4i");
        cell = row.createCell(1);
        cell.setCellValue(d_4i);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 5
        row = sheet1.createRow(5);
        cell = row.createCell(0);
        cell.setCellValue("D4m");
        cell = row.createCell(1);
        cell.setCellValue(d_4m);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 6
        row = sheet1.createRow(6);
        cell = row.createCell(0);
        cell.setCellValue("D3e");
        cell = row.createCell(1);
        cell.setCellValue(d_3e);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 7
        row = sheet1.createRow(7);
        cell = row.createCell(0);
        cell.setCellValue("beta_4m");
        cell = row.createCell(1);
        cell.setCellValue(beta_4m);
        cell = row.createCell(2);
        cell.setCellValue("º");
        // Linha 8
        row = sheet1.createRow(8);
        cell = row.createCell(0);
        cell.setCellValue("D3i");
        cell = row.createCell(1);
        cell.setCellValue(d_3i);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 9
        row = sheet1.createRow(9);
        cell = row.createCell(0);
        cell.setCellValue("D5i");
        cell = row.createCell(1);
        cell.setCellValue(d_5i);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 10
        row = sheet1.createRow(10);
        cell = row.createCell(0);
        cell.setCellValue("li");
        cell = row.createCell(1);
        cell.setCellValue(li);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 11
        row = sheet1.createRow(11);
        cell = row.createCell(0);
        cell.setCellValue("le");
        cell = row.createCell(1);
        cell.setCellValue(le);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 12
        row = sheet1.createRow(12);
        cell = row.createCell(0);
        cell.setCellValue("L5e");
        cell = row.createCell(1);
        cell.setCellValue(l_5e);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 13
        row = sheet1.createRow(13);
        cell = row.createCell(0);
        cell.setCellValue("L4i");
        cell = row.createCell(1);
        cell.setCellValue(l_4i);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 14
        row = sheet1.createRow(14);
        cell = row.createCell(0);
        cell.setCellValue("L4e");
        cell = row.createCell(1);
        cell.setCellValue(l_4e);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 15
        row = sheet1.createRow(15);
        cell = row.createCell(0);
        cell.setCellValue("d");
        cell = row.createCell(1);
        cell.setCellValue(d);
        cell = row.createCell(2);
        cell.setCellValue("mm");
        // Linha 16
        row = sheet1.createRow(63);
        cell = row.createCell(0);
        cell.setCellValue("Y_em");
        cell = row.createCell(1);
        cell.setCellValue(y_em);
        cell = row.createCell(2);
        cell.setCellValue("m");

        try {
            FileOutputStream out = new FileOutputStream(new File(Geometry.filename));
            workbook.write(out);
            out.close();
            System.out.println("Arquivo gerado com sucesso!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Arquivo não encontrado!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro na edição de arquivo!");
        }
    }

    public Geometry() {
    }

    public Geometry(double Q, double Q_min, double H, double H_max, double h_sum, double Z_b, double eta_v, double eta_m) {
        this.Q = Q;
        this.Q_min = Q_min;
        this.H = H;
        this.H_max = H_max;
        this.h_sum = h_sum;
        this.Z_b = Z_b;
        this.eta_v = eta_v;
        this.eta_m = eta_m;

        // --- Inicio Cálculos --- //
        Y();
        System.out.println("Salto energético y = " + y + " J/kg");
        Toma_min();
        System.out.println("Toma mínimo = " + toma_min);
        N_qa();
        System.out.println("n_qa = " + n_qa + " rpm");
        Q_r11();
        System.out.println("q_r11 = " + q_r11 + " m³/s");
        N();
        System.out.println("n = " + n + " rpm");
        Z_p();
        System.out.println("z_p = " + z_p);
        System.out.println("Rotação corrigida n = " + n + " rpm");
        N_qar11();
        System.out.println("A rotação específica n_qar11 = " + n_qar11 + " rpm");
        Q_m();
        System.out.println("A vazão = " + q_m + " rpm");
        Toma_m();
        System.out.println("Toma m = " + toma_m);
        H_sum();
        System.out.println("h_sum = " + h_sum1 + " mca ");
        N_qr11();
        System.out.println("n_r11 = " + n_qr11);
        Eta_i();
        System.out.println("Rendimento interno eta_i = " + eta_i);
        Q_r();
        System.out.println("Vazão do rotor q_r = " + q_r + " m³/s");
        N_qar();
        System.out.println("Rotação específica n_qar = " + n_qar + " rpm");
        D_5e();
        System.out.println("Diâmetro de saída d_5e = " + d_5e + " m");
        B_0();
        System.out.println("Largura do distribuidor b_0 = " + b_0 + " m");
        D_4e();
        System.out.println("Diâmetro de entrada externa d_4e = " + d_4e + " m");
        D_4i();
        System.out.println("Diâmentro de entrada interno d_4i = " + d_4i + " m");
        D_m();
        System.out.println("Diâmetro médio de entrada d_m = " + d_4m + " m");
        U_4m();
        System.out.println("Velocidade média na aresta de entrada u_4m = " + u_4m + " m/s");
        D_3e();
        System.out.println("Diâmetro externo da cinta d_3e = " + d_3e + " m");
        C_m();
        System.out.println("Velocidade média entrada de c_m = " + c_m + " m/s");
        C_u4m();
        System.out.println("Velocidade tangencial c_u4m = " + c_u4m + " m/s");
        Beta_4m();
        System.out.println("Ângulo de direção da pá de entrada do rotor beta_4m = " + beta_4m + "º");
        D_3i();
        System.out.println("Diâmetro interno da coroa d_3i = " + d_3i + " m");
        D_5i();
        System.out.println("Diâmentro interno da coroa d_5i = " + d_5i + " m");
        Li();
        System.out.println("Altura interna da coroa li = " + li + " m");
        Le();
        System.out.println("Altura externa da coroa le = " + le + " m");
        L_5e();
        System.out.println("Altura de d_3i até d_5e l_5e = " + l_5e + " m");
        L_4i();
        System.out.println("Comprimento do arco de circunferência l_4i = " + l_4i + " m");
        L_4e();
        System.out.println("Comprimento do arco de circunferência l_4e = " + l_4e + " m");
        P_emax();
        System.out.println("Potência máxima no eixo p_emax = " + p_emax + " kW");
        D();
        System.out.println("Diâmentro do eixo d = " + d + "[mm]");
        Y_ij();
        Y_em();
        System.out.println("Espessura máxima da coroa externa y_em = " + y_em + " m");
        Y_ej();
    }

    private double Y() {
        return y = H * 9.81;
    }

    private double Toma_min() {
        return toma_min = (10 - 0.00122 * Z_b - h_sum) / H;
    }

    private double N_qa() {
        return n_qa = 627.7 * Math.pow((toma_min - 0.0311), 0.5);
    }

    private double Q_r11() {
        return q_r11 = eta_v * Q;
    }

    private double N() {
        //return n = (n_qa * Math.pow(H, 0.75)) / (3 * Math.pow(q_r11, 0.5));
        return n = 450;
    }

    private double Z_p() {
        z_p = 3600 / n;
        int z_pint = (int) z_p;
        if (z_p != z_pint) {
            z_p = z_pint + 1;
            this.n = 3600 / z_p;
        }
        return z_p;
    }

    private double N_qar11() {
        return n_qar11 = 3 * n * Math.pow(Q_r11(), 0.5) / Math.pow(H, 0.75);
    }

    private double Q_m() {
        return q_m = (0.248 + 2.741 * Math.pow(10, -3) * n_qar11 - 3.403 * Math.pow(10, -6) * Math.pow(n_qar11, 2)) * q_r11;
    }

    private double Toma_m() {
        return toma_m = 0.00311 + 2.5381 * Math.pow(10, -6) * Math.pow(n_qar11, 2);
    }

    private double H_sum() {
        return h_sum1 = 10 - 0.00122 * Z_b - toma_m * H;
    }

    private double N_qr11() {
        return n_qr11 = n_qar11 / 3;
    }

    private double Eta_i() {
        return eta_i = Math.pow((0.7183 + 0.005566 * n_qr11 - 6.5417 * Math.pow(10, -5) * Math.pow(n_qr11, 2) + 2.0919 * Math.pow(10, -7) * Math.pow(n_qr11, 3)), 0.5);
    }

    /*Problema na equação*/
    private double Q_r() {
        return q_r = 0.731 * (1 + 0.01 * Math.pow(n_qar11, 0.5)) * q_r11;
        //return  q_r = 12.636;
    }

    private double N_qar() {
        return n_qar = 3 * n * (Math.pow(q_r, 0.5)) / Math.pow(H, 0.75);
    }

    /*Problema na equação*/
    private double D_5e() {
//        return d_5e = 24.786 * Math.pow(H, 0.5) / n + 0.685 * Math.pow(q_r, 0.5) / Math.pow(H, 0.25);
        return d_5e = 1.267;
    }

    private double B_0() {
        return b_0 = (0.168 * Math.pow(10, -2) * n_qar - 0.018 * Math.pow(10, -4) * Math.pow(n_qar, 2)) * d_5e;
    }

    private double D_4e() {
        if (60 < n_qar && n_qar <= 100) {
            return d_4e = (2.32 - 0.975 * Math.pow(10, -2) * n_qar) * d_5e;
        } else {
            if (100 < n_qar && 250 >= n_qar) {
                return d_4e = (0.165 * Math.pow(10, -4) * Math.pow(n_qar, 2) - 0.835 * Math.pow(10, -2) * n_qar + 2.017) * d_5e;
            } else {
                if (250 < n_qar && 350 >= n_qar) {
                    return d_4e = (1.025 - 0.03 * Math.pow(10, -2) * n_qar) * d_5e;
                }
            }
        }
        return d_4e;
    }

    private double D_4i() {
        if (60 < n_qar && n_qar <= 100) {
            d_4i = (2.32 - 0.975 * Math.pow(10, -2) * n_qar) * d_5e;
        } else {
            if (100 < n_qar && n_qar <= 350) {
                d_4i = (0.5 + 84.5 * Math.pow(n_qar, -1)) * d_5e;
            }
        }
        return d_4i;
    }

    private double D_m() {
        return d_4m = 0.5 * (d_4i + d_4e);
    }

    private double U_4m() {
        return u_4m = Math.PI * d_4m * n / 60;
    }

    private double D_3e() {
        if (60 <= n_qar && n_qar <= 100) {
            d_3e = (2.32 - 0.975 * Math.pow(10, -2) * n_qar) * d_5e;
        } else {
            if (100 < n_qar && n_qar <= 350) {
                d_3e = (1.255 - 0.633 * Math.pow(10, -3) * n_qar) * d_5e;
            }
        }
        return d_3e;
    }

    private double C_m() {
        return c_m = (4 * q_r) / (Math.PI * b_0 * d_3e);
    }

    private double C_u4m() {
        return c_u4m = 9.81 * eta_i * H / u_4m;
    }

    private double Beta_4m() {
        beta_4m = Math.atan(c_m / (u_4m - c_u4m)) * 180 / Math.PI;
        return beta_4m;
    }

    private double D_3i() {
        if (60 <= n_qar && n_qar <= 100) {
            d_3i = (2.32 - 0.975 * Math.pow(10, -2) * n_qar) * d_5e;
        } else {
            if (100 < n_qar && n_qar <= 350) {
                d_3i = (0.7 + 0.16 / (2.11 * Math.pow(10, -3) * n_qar + 0.08)) * d_5e;
            }
        }
        return d_3i;
    }

    private double D_5i() {
        return d_5i = (0.86 - 2.18 * Math.pow(10, -3) * n_qa) * d_5e;
    }

    private double Li() {
        return li = (0.4 + 0.675 * Math.pow(10, -2) * n_qar - 0.0177 * Math.pow(10, -4) * Math.pow(n_qar, 2)) * d_5e;
    }

    private double Le() {
        return le = (0.042 * Math.pow(10, -4) * Math.pow(n_qar, 2) - 0.4 * Math.pow(10, -2) * n_qar + 1.2) * d_5e;
    }

    private double L_5e() {
        return l_5e = (0.26 - 0.21 * Math.pow(10, -3) * n_qar) * d_5e;
    }

    private double L_4i() {
        if (50 < n_qar && n_qar <= 210) {
            l_4i = (3.785 * Math.pow(10, -6) * Math.pow(n_qar, 2) - 1.673 * Math.pow(10, -3) * n_qar + 0.436) * d_4e;
        } else {
            if (210 < n_qar && n_qar <= 350) {
                l_4i = (2.353 * Math.pow(10, -6) * Math.pow(n_qar, 2) - 0.8667 * Math.pow(10, -3) * n_qar + 0.328) * d_4e;
            }
        }
        return l_4i;
    }

    private double L_4e() {
        if (50 < n_qar && n_qar <= 210) {
            l_4e = (3.713 * Math.pow(10, -6) * Math.pow(n_qar, 2) - 1.907 * Math.pow(10, -3) * n_qar + 0.358) * d_4e;
        } else {
            if (210 < n_qar && n_qar <= 350) {
                l_4e = (2.222 * Math.pow(10, -4) * n_qar + 0.0833) * d_4e;
            }
        }
        return l_4e;
    }

    private double P_emax() {
        return p_emax = (9.81 * q_r11 * H_max) / (eta_i * eta_m);
    }

    private double D() {
        return d = 118 * Math.cbrt((p_emax / n));
    }

    private void Y_ij() {
        double xmax = li / 4;
        int pontos = 40;
        double delta = xmax / pontos;
        x_ij = new double[pontos];
        y_ij = new double[pontos];
        x_ij[0] = 0;
        y_ij[0] = 1.54 * d_3i * Math.sqrt((x_ij[0] / li) * (Math.pow(1 - (x_ij[0] / li), 3)));
        for (int i = 1; i < pontos; i++) {
            x_ij[i] = x_ij[i - 1] + delta;
            y_ij[i] = 1.54 * d_3i * Math.sqrt((x_ij[i] / li) * (Math.pow(1 - (x_ij[i] / li), 3)));
            System.out.println("x_ij = " + x_ij[i] + " m \t y_ij = " + y_ij[i] + " m");
        }
    }

    private double Y_em() {
        return y_em = (0.162 * (d_3e - d_5e)) / Math.sqrt((l_5e / le) * Math.pow(1 - (l_5e / le), 3));
    }

    private void Y_ej() {
        double xmax = le;
        int pontos = 40;
        double delta = xmax / pontos;

        x_ej = new double[pontos];
        y_ej = new double[pontos];
        x_ej[0] = 0;
        y_ej[0] = 3.08 * y_em * Math.sqrt((x_ej[0] / le) * Math.pow(1 - x_ej[0] / le, 3));

        for (int i = 1; i < pontos; i++) {
            x_ej[i] = delta + x_ej[i - 1];
            y_ej[i] = 3.08 * y_em * Math.sqrt((x_ej[i] / le) * Math.pow(1 - x_ej[i] / le, 3));
            System.out.println("x_ej = " + x_ej[i] * 1000 + " m \t y_ej = " + y_ej[i] * 1000 + " m");
        }

    }
}
