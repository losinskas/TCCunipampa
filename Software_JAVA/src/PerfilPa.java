public class PerfilPa {

    private double s4j[];
    private double s4e;
    private double beta_j[];
    private double lambda_4j[] = {90, 90, 90, 90, 90, 90, 90};
    private double fj[];
    private double e_maxj[];
    private double Lj[];
    private double L1j[];
    private double fpj[];
    private double bm;
    private double h;
    private double emaxj1[];
    private double epi[][];
    private double xj[][];
    private double esi[][];
    private double deltap[][];
    public PerfilPa(double[] s4j, double s4e, double beta_j[], double bm, double h, double[] Lj) {
        this.s4j = s4j;
        this.s4e = s4e;
        this.beta_j = beta_j;
        this.bm = bm;
        this.h = h;
        this.Lj = Lj;

        emaxj();
        fj();
        emaxj1();
        L1j();
        fpj();
        epi();
    }

    private void epi() {
        epi = new double[beta_j.length][10];
        esi = new double[beta_j.length][10];
        xj = new double[beta_j.length][10];
        deltap = new double[beta_j.length][10];
        double x[] = {0,3.3,4.7,6.5,8.05,8.25,7.4,5.25,2.1,0};
        double xporc[] = {0,0.025,0.05,0.1,0.2,0.3,0.5,0.7,0.9,1};
        for (int i = 0; i < beta_j.length; i++) {
            for (int j = 0; j < 10; j++) {
                xj[i][j] = Lj[i]*100*xporc[j];
                //epi[i][j] = -3.7313+4.7028*xj[i][j]-2.3971*Math.pow(xj[i][j], 1.5)+0.0482*Math.pow(xj[i][j], 0.5)+3.7438*Math.exp(-xj[i][j]);
                //esi[i][j] = -0.6944+2.0464*xj[i][j]-2.5203*Math.pow(xj[i][j], 1.5)-1.05545*Math.pow(xj[i][j], 0.5)+0.8176*Math.exp(xj[i][j]);
                deltap[i][j] = x[j]*emaxj1[i]/8.25;
                System.out.print("xi = "+xj[i][j] +"\t");
                System.out.print("x = " + x[j]+"\t");
                System.out.println("deltap = " +deltap[i][j]*1000+"\t");

            }
            System.out.println("");
        }
    }

    private void fpj() {
        fpj = new double[beta_j.length];
        for (int i = 0; i < beta_j.length; i++) {
            fpj[i] = emaxj1[i] / 8.25;
            System.out.println("fpj[" + i + "] = " + fpj[i]);
        }
    }

    private void L1j() {
        L1j = new double[beta_j.length];
        for (int i = 0; i < beta_j.length; i++) {
            L1j[i] = 1.01 * Lj[i];
            System.out.println("L1j[" + i + "] = " + L1j[i]);
        }
    }

    private void emaxj1() {
        emaxj1 = new double[beta_j.length];
        for (int i = 0; i < beta_j.length; i++) {
            emaxj1[i] = fj[i] * e_maxj[i];
            System.out.println("emax*[" + i + "] = " + emaxj1[i]);
        }
    }

    private void fj() {
        fj = new double[beta_j.length];
        for (int i = 0; i < beta_j.length; i++) {
            fj[i] = Math.sqrt(1 + Math.pow(Math.tan(Math.toRadians(beta_j[i])) * Math.sin(Math.toRadians(lambda_4j[i])), -2));
            System.out.println("fj[" + i + "] = " + fj[i]);
        }
    }

    private void emaxj() {
        e_maxj = new double[beta_j.length];
        for (int i = 0; i < beta_j.length; i++) {
            e_maxj[i] = 0.09 * bm * Math.sqrt(h) * (1 - 0.7 * s4j[i] / s4e);
            System.out.println("emaxj[" + i + "] = " + e_maxj[i]);
        }


    }

}
