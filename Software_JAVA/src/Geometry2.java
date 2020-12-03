import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Geometry2 {

    private double relacaoCm[];
    private double[] sj;
    private double s;
    private double ri;
    private double re;
    private double kj[];
    private double Dj[];
    private double area;
    private double area1;
    private double cmm;
    private double cm4i;
    private double kc;
    private double cm;
    private double fem;
    private double km;
    private double dm;
    private double Qr;
    private double bm;
    private double u5m[];
    private double D5m[];
    private double n;
    private double beta_5m[];
    private double uj[];
    private double cuj[];
    private double eta_i;
    private double H;
    private double beta_j[];
    private double rg;
    private double Lg;
    private double zr[];
    private double ej[];
    private double tj[];
    private double teta[];
    private double feej[];
    private double cmj[];
    private double cmj1[];
    private double beta_j1[];
    private double beta_hj[];
    private double zeta[];

    public Geometry2(double[] sj, double[] dj, double cm, double fem, double dm, double Qr, double re, double ri,
                     double s, double D5m[], double n, double eta_i, double H, double rg, double Lg, double teta[], double zeta[]) {
        this.sj = sj;
        this.Dj = dj;
        this.cm = cm;
        this.fem = fem;
        this.dm = dm;
        this.Qr = Qr;
        this.re = re;
        this.ri = ri;
        this.s = s;
        this.D5m = D5m;
        this.n = n;
        this.H = H;
        this.eta_i = eta_i;
        this.rg = rg;
        this.Lg = Lg;
        this.teta = teta;
        this.zeta = zeta;
        RelacaoCm();
        Kj();
        integralA();
        cmm();
        kc();
        Bm();
        Km();
        cm4i();
        U5m();
        Beta_5m();
        AjusteDeCurvaClass ajuste = new AjusteDeCurvaClass();
        uj();
        cuj();
        cmj1();
        beta_j();
        zr();
        ej();
        tj();
        feej();
        cmj1();
        cmj();
        beta_j1();
        beta_hj();
    }
    private void beta_hj(){
        beta_hj = new double[Dj.length];
        for (int i = 0; i < Dj.length; i++) {
            beta_hj[i] = Math.atan( Math.tan( Math.toRadians( beta_j1[i])) * Math.sin(Math.toRadians(zeta[i])) );
            System.out.println("beta_hj["+ i + "] = " +Math.toDegrees(beta_hj[i]) );
        }
    }
    private void beta_j1() {
        beta_j1 = new double[Dj.length];
        for (int i = 0; i < Dj.length; i++) {
            beta_j1[i] = Math.atan(cmj[i] / (uj[i] - cuj[i])) * 180 / Math.PI;
            System.out.println("beta_j[" + i + "] = " + beta_j1[i]);
        }
    }

    private void cmj1() {
        cmj1 = new double[Dj.length];
        for (int i = 0; i < Dj.length; i++) {
            cmj1[i] = cm4i*Math.exp(sj[i]/(4*ri)*(sj[i]/(2*s)*(ri/re - 1 )+1) );
            System.out.println("cmj*["+i+"] = "+cmj1[i]);
        }
    }

    private void cmj() {
        cmj = new double[Dj.length];
        for (int i = 0; i < Dj.length; i++) {
            cmj[i] = cmj1[i] / feej[i];
            System.out.println("cmj[" + i + "] = " + cmj[i]);
        }
    }

    private void feej() {
        feej = new double[Dj.length];
        for (int i = 0; i < Dj.length; i++) {
            feej[i] = 1 - ((ej[i] * Math.sqrt(1 + Math.pow(1 / Math.tan(Math.toRadians(teta[i])), 2) * Math.pow(Math.cos(Math.toRadians(beta_j[i])), 2))) / (tj[i] * Math.sin(Math.toRadians(beta_j[i]))));
            System.out.println("feej[" + i + "] = " + feej[i]);
        }
    }

    private void tj() {
        tj = new double[Dj.length];
        for (int i = 0; i < Dj.length; i++) {
            tj[i] = Math.PI * Dj[i] / zr[i];
            System.out.println("tj["+i+"] = " + tj[i]);
        }
    }

    private void ej() {
        ej = new double[Dj.length];
        for (int i = 0; i < Dj.length; i++) {
            ej[i] = 0.007 * bm * Math.sqrt(H) * (1 - 0.7 * sj[i] / s);
            System.out.println("ej[" + i + "] = " + ej[i]);
        }
    }

    private void zr() {
        zr = new double[Dj.length];
        int zrInteiro[] = new int[Dj.length];
        double angleRad;
        for (int i = 0; i < Dj.length - 1; i++) {
            angleRad = ((beta_j[i] + beta_5m[i]) / 2) * Math.PI / 180;
            zr[i] = 10 * rg / Lg * Math.sin(angleRad);
            zrInteiro[i] = (int) zr[i];
            System.out.println("zr[" + i + "] = " + zrInteiro[i]);
        }

    }

    private void beta_j() {
        beta_j = new double[Dj.length];
        for (int i = 0; i < Dj.length; i++) {
            beta_j[i] = Math.atan(cmm / (uj[i] - cuj[i])) * 180 / Math.PI;
            System.out.println("beta_j[" + i + "] = " + beta_j[i]);
        }
    }

    private void cuj() {
        cuj = new double[Dj.length];
        for (int i = 0; i < Dj.length; i++) {
            cuj[i] = 9.81 * eta_i * H / uj[i];
            System.out.println("cu[" + i + "] = " + cuj[i]);
        }
    }

    private void uj() {
        uj = new double[Dj.length];
        for (int i = 0; i < Dj.length; i++) {
            uj[i] = Math.PI * Dj[i] * n / 60;
            System.out.println("uj[" + i + "] = " + uj[i]);
        }
    }

    private void Km() {
        km = bm * dm * cm;

        System.out.println("km = " + km);
    }

    private void RelacaoCm() {
        relacaoCm = new double[sj.length];
        for (int i = 0; i < sj.length; i++) {
            relacaoCm[i] = Math.exp(sj[i] / (ri * 4) * (sj[i] / (2 * s) * (ri / re - 1) + 1));
            System.out.println("Relacao Cm = " + relacaoCm[i]);
        }
    }

    private void Kj() {
        kj = new double[relacaoCm.length];
        for (int i = 0; i < relacaoCm.length; i++) {
            kj[i] = relacaoCm[i] * Dj[i];
            System.out.println("Kj = " + kj[i]);

        }
    }

    private void integralA() {
        area1 = area / 2;
    }

    private void cmm() {
        cmm = fem * cm;
        System.out.println("cmm = " + cmm + " [m/s]");
    }

    private void cm4i() {
        cm4i = (cmm * dm) / km;
        System.out.println("cm4i = " + cm4i + " [m/s]");
    }

    private void kc() {
        kc = Qr / (3 * Math.PI);
        System.out.println("kc = " + kc + " [m/s]");

    }

    private void Bm() {
        bm = kc / (cmm * dm);
        System.out.println("bm = " + bm + "m");
    }

    private void U5m() {
        u5m = new double[D5m.length];
        for (int i = 0; i < D5m.length; i++) {
            u5m[i] = (Math.PI * D5m[i] * n) / 60;
            System.out.println("u5m[" + i + "]  = " + u5m[i] + " [m/s]");
        }
    }

    private void Beta_5m() {
        beta_5m = new double[D5m.length];
        double graus;
        for (int i = 0; i < D5m.length; i++) {
            graus = cmm / u5m[i];
            beta_5m[i] = Math.atan(graus);
            beta_5m[i] = beta_5m[i] * 180 / Math.PI;

            System.out.println("beta_5m[" + i + "] = " + beta_5m[i]);
        }
    }

    private static final String filename = "C:/Users/lucas/OneDrive/Área de Trabalho/ProjectTurbine/geometriaPa.xls";

    public void GerarPontos() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Pontos");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        row = sheet.createRow(0);
        cell = row.createCell(0);
        cell.setCellValue("parameter_Name");
        cell = row.createCell(1);
        cell.setCellValue("Equation");
        cell = row.createCell(2);
        cell.setCellValue("Unit/Type");
        cell = row.createCell(3);
        cell.setCellValue("Comment");
        // Linha 1
        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("bm");
        cell = row.createCell(1);
        cell.setCellValue(bm);
        cell = row.createCell(2);
        cell.setCellValue("m");

        try {
            FileOutputStream out = new FileOutputStream(new File(Geometry2.filename));
            workbook.write(out);
            out.close();
            System.out.printf("Arquivo gerado com sucesso!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Arquivo não encontrado!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro na edição do arquivo \n");
        }
    }
}

