public class Geometry1 {

    private double relacaoCm[];
    private double []sj;
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

    public Geometry1(double []sj, double []dj, double cm, double fem, double km, double dm, double Qr) {
    this.sj = sj;
    this.Dj = dj;
    this.cm = cm;
    this.fem = fem;
    this.km = km;
    this.dm = dm;
    this.Qr = Qr;
    RelacaoCm();
    Kj();
    integralA();
    cmm();


    }

    private void RelacaoCm(){
        relacaoCm = new double[sj.length];
        for (int i=0; i<sj.length;i++) {
            relacaoCm[i] = Math.exp( sj[i]/(ri*4) * (sj[i]/(2*s))*(ri/re-1)+1);
        }
    }
    private void Kj(){
        for(int i=0; i<relacaoCm.length; i++){
            kj[i] = relacaoCm[i]*Dj[i];
        }
    }
    private void integralA(){
        area1 = area/2;
    }
    private void cmm(){
        cmm = fem*cm;
    }
    private  void cm4i(){
        cm4i = (cmm*dm)/km;
    }
    private void kc(){
        kc = Qr/(3*Math.PI);
    }
}
