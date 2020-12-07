import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Geometry1 {

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

    public Geometry1(double[] sj, double[] dj, double cm, double fem, double dm, double Qr, double re, double ri,
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
            System.out.println("beta_j*[" + i + "] = " + beta_j[i]);
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
            FileOutputStream out = new FileOutputStream(new File(Geometry1.filename));
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

    public double[] getRelacaoCm() {
        return relacaoCm;
    }

    public void setRelacaoCm(double[] relacaoCm) {
        this.relacaoCm = relacaoCm;
    }

    public double[] getSj() {
        return sj;
    }

    public void setSj(double[] sj) {
        this.sj = sj;
    }

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
    }

    public double getRi() {
        return ri;
    }

    public void setRi(double ri) {
        this.ri = ri;
    }

    public double getRe() {
        return re;
    }

    public void setRe(double re) {
        this.re = re;
    }

    public double[] getKj() {
        return kj;
    }

    public void setKj(double[] kj) {
        this.kj = kj;
    }

    public double[] getDj() {
        return Dj;
    }

    public void setDj(double[] dj) {
        Dj = dj;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getArea1() {
        return area1;
    }

    public void setArea1(double area1) {
        this.area1 = area1;
    }

    public double getCmm() {
        return cmm;
    }

    public void setCmm(double cmm) {
        this.cmm = cmm;
    }

    public double getCm4i() {
        return cm4i;
    }

    public void setCm4i(double cm4i) {
        this.cm4i = cm4i;
    }

    public double getKc() {
        return kc;
    }

    public void setKc(double kc) {
        this.kc = kc;
    }

    public double getCm() {
        return cm;
    }

    public void setCm(double cm) {
        this.cm = cm;
    }

    public double getFem() {
        return fem;
    }

    public void setFem(double fem) {
        this.fem = fem;
    }

    public double getKm() {
        return km;
    }

    public void setKm(double km) {
        this.km = km;
    }

    public double getDm() {
        return dm;
    }

    public void setDm(double dm) {
        this.dm = dm;
    }

    public double getQr() {
        return Qr;
    }

    public void setQr(double qr) {
        Qr = qr;
    }

    public double getBm() {
        return bm;
    }

    public void setBm(double bm) {
        this.bm = bm;
    }

    public double[] getU5m() {
        return u5m;
    }

    public void setU5m(double[] u5m) {
        this.u5m = u5m;
    }

    public double[] getD5m() {
        return D5m;
    }

    public void setD5m(double[] d5m) {
        D5m = d5m;
    }

    public double getN() {
        return n;
    }

    public void setN(double n) {
        this.n = n;
    }

    public double[] getBeta_5m() {
        return beta_5m;
    }

    public void setBeta_5m(double[] beta_5m) {
        this.beta_5m = beta_5m;
    }

    public double[] getUj() {
        return uj;
    }

    public void setUj(double[] uj) {
        this.uj = uj;
    }

    public double[] getCuj() {
        return cuj;
    }

    public void setCuj(double[] cuj) {
        this.cuj = cuj;
    }

    public double getEta_i() {
        return eta_i;
    }

    public void setEta_i(double eta_i) {
        this.eta_i = eta_i;
    }

    public double getH() {
        return H;
    }

    public void setH(double h) {
        H = h;
    }

    public double[] getBeta_j() {
        return beta_j;
    }

    public void setBeta_j(double[] beta_j) {
        this.beta_j = beta_j;
    }

    public double getRg() {
        return rg;
    }

    public void setRg(double rg) {
        this.rg = rg;
    }

    public double getLg() {
        return Lg;
    }

    public void setLg(double lg) {
        Lg = lg;
    }

    public double[] getZr() {
        return zr;
    }

    public void setZr(double[] zr) {
        this.zr = zr;
    }

    public double[] getEj() {
        return ej;
    }

    public void setEj(double[] ej) {
        this.ej = ej;
    }

    public double[] getTj() {
        return tj;
    }

    public void setTj(double[] tj) {
        this.tj = tj;
    }

    public double[] getTeta() {
        return teta;
    }

    public void setTeta(double[] teta) {
        this.teta = teta;
    }

    public double[] getFeej() {
        return feej;
    }

    public void setFeej(double[] feej) {
        this.feej = feej;
    }

    public double[] getCmj() {
        return cmj;
    }

    public void setCmj(double[] cmj) {
        this.cmj = cmj;
    }

    public double[] getCmj1() {
        return cmj1;
    }

    public void setCmj1(double[] cmj1) {
        this.cmj1 = cmj1;
    }

    public double[] getBeta_j1() {
        return beta_j1;
    }

    public void setBeta_j1(double[] beta_j1) {
        this.beta_j1 = beta_j1;
    }

    public double[] getBeta_hj() {
        return beta_hj;
    }

    public void setBeta_hj(double[] beta_hj) {
        this.beta_hj = beta_hj;
    }

    public double[] getZeta() {
        return zeta;
    }

    public void setZeta(double[] zeta) {
        this.zeta = zeta;
    }

    public static String getFilename() {
        return filename;
    }
}

