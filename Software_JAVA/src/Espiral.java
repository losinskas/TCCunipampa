import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.jcp.xml.dsig.internal.dom.DOMX509Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Espiral {

    private double Ae;          // Área seccional [°/m]
    private double H;           // Altura de projeto [m]
    private double eta_i;       // Rendimento interno
    private double n;           // Rotação do rotor [rpm]
    private double Qp;          // Vazão de projeto [m³/s]
    private double Ce;          // Velocidade de entrada [m/s]
    private double DE;          // Diâmtro de entrada [m]
    private double nqar;        // Rotação
    private double D5e;         // Diâmetro [m]
    private double D0;          // Diâmetro [m]
    private double Re0[];       // Raio da expiral
    private double Res0[];      // Raio do tubo
    private double De;          // Diâmetro de entrada da espiral [m]
    private double D3i;         // Diâmetro de entrada  da coroa interna [m]
    private double D3e;         // Diâmetro de entrada da coroa externa [m]
    private double b0;          // Largura do distribuidor [m]
    private double Ddg;         // Diâmetro da fixação das aletas [m]
    private double zd;          // Número de aletas
    public Espiral(double H, double eta_i, double n, double Qp, double nqar, double D5e, double D3i, double D3e, double b0) {
        // Passar parâmetros de entrada
        this.H = H;
        this.eta_i = eta_i;
        this.n = n;
        this.Qp = Qp;
        this.nqar = nqar;
        this.D5e = D5e;
        this.D3i = D3i;
        this.D3e = D3e;
        this.b0 = b0;
        AE();
        System.out.println("\nO Valor da área Ae = " + Ae + " °/m");
        Ce();
        System.out.println("Velocidade de entrada Ce = " + Ce + " m/s");
        DE();
        System.out.println("Diâmetro de entrada da turbina DE = " + DE + " m");
        D0();
        System.out.println("Diâmetro da turbina D0 = "+ D0+" m");
        Re0();
        De();
        System.out.println("Diâmetro de entrada da espiral De = "+ De +" m");
        Ddg();
        System.out.println("Diâmetro de fixação das aletas Ddg = " + Ddg + " m" );
        Aletas aletas = new Aletas(Ddg, H, b0);
        aletas.GerarPontos();
        zd = aletas.getZd();
    }

    private void AE() {
        Ae = 211896 * H * eta_i / (Qp * n);
    }

    private void Ce() {
        Ce = 0.2 * Math.pow((2 * 8.91 * H), 0.5);
    }

    private void DE() {
        DE = 1.437 * Qp / Math.sqrt(H);
    }
    private void De(){De = 2*Res0[16];}

    private void D0(){
        D0 = (0.16*Math.pow(10, -4)*Math.pow(nqar, 2)- 0.98*Math.pow(10, -2)*nqar+2.9)*D5e;
    }

    private void Ddg(){Ddg = (D0-D3e)/2 +D3e;}

    private double Res0(double re0){
        return  0.5 * (re0-0.5*D0);
    }
    private void Re0(){
        Re0 = new double[17];
        Res0 = new double[17];

        // teta = 0
        Re0[0] = 0.5*D0;
        Res0[0] = Res0(Re0[0]);
        System.out.println("teta = 0 e Re0 = "+ Re0[0]+" m");
        System.out.println("Res0 = "+ Res0[0] +" m");
        //  teta = 22.5
        Re0[1] = 0.5*D0+2*(22.5/Ae+Math.sqrt(D0*22.5/Ae));
        Res0[1] = Res0(Re0[1]);
        System.out.println("teta = 22.5 e Re0 = "+ Re0[1]+" m");
        System.out.println("Res0 = "+ Res0[1] +" m");
        // teta = 45
        Re0[2] = 0.5*D0+2*(45/Ae+Math.sqrt(D0*45/Ae));
        Res0[2] = Res0(Re0[2]);
        System.out.println("teta = 45 e Re0 = "+ Re0[2]+" m");
        System.out.println("Res0 = "+ Res0[2] +" m");
        // teta = 67.5
        Re0[3] = 0.5*D0+2*(67.5/Ae+Math.sqrt(D0*67.5/Ae));
        Res0[3] = Res0(Re0[3]);
        System.out.println("teta = 67.5 e Re0 = "+ Re0[3]+" m");
        System.out.println("Res0 = "+ Res0[3] +" m");
        // teta = 90
        Re0[4] = 0.5*D0+2*(90/Ae+Math.sqrt(D0*90/Ae));
        Res0[4] = Res0(Re0[4]);
        System.out.println("teta = 90 e Re0 = "+ Re0[4]+" m");
        System.out.println("Res0 = "+ Res0[4] +" m");
        // teta = 112.5
        Re0[5] = 0.5*D0+2*(112.5/Ae+Math.sqrt(D0*112.5/Ae));
        Res0[5] = Res0(Re0[5]);
        System.out.println("teta = 112.5 e Re0 = "+ Re0[5]+" m");
        System.out.println("Res0 = "+ Res0[5] +" m");
        // teta = 135
        Re0[6] = 0.5*D0+2*(135/Ae+Math.sqrt(D0*135/Ae));
        Res0[6] = Res0(Re0[6]);
        System.out.println("teta = 135 e Re0 = "+ Re0[6]+" m");
        System.out.println("Res0 = "+ Res0[6] +" m");
        // teta = 157.5
        Re0[7] = 0.5*D0+2*(157.5/Ae+Math.sqrt(D0*157.5/Ae));
        Res0[7] = Res0(Re0[7]);
        System.out.println("teta = 157.5 e Re0 = "+ Re0[7]+" m");
        System.out.println("Res0 = "+ Res0[7] +" m");
        // teta = 180
        Re0[8] = 0.5*D0+2*(180/Ae+Math.sqrt(D0*180/Ae));
        Res0[8] = Res0(Re0[8]);
        System.out.println("teta = 180 e Re0 = "+ Re0[8]+" m");
        System.out.println("Res0 = "+ Res0[8] +" m");
        // teta = 202.5
        Re0[9] = 0.5*D0+2*(202.5/Ae+Math.sqrt(D0*202.5/Ae));
        Res0[9] = Res0(Re0[9]);
        System.out.println("teta = 202.5 e Re0 = "+ Re0[9]+" m");
        System.out.println("Res0 = "+ Res0[9] +" m");
        // teta = 225
        Re0[10] = 0.5*D0+2*(225/Ae+Math.sqrt(D0*225/Ae));
        Res0[10] = Res0(Re0[10]);
        System.out.println("teta = 225 e Re0 = "+ Re0[10]+" m");
        System.out.println("Res0 = "+ Res0[10] +" m");
        // teta = 247.5
        Re0[11] = 0.5*D0+2*(247.5/Ae+Math.sqrt(D0*247.5/Ae));
        Res0[11] = Res0(Re0[11]);
        System.out.println("teta = 247.5 e Re0 = "+ Re0[11]+" m");
        System.out.println("Res0 = "+ Res0[11] +" m");
        // teta = 270
        Re0[12] = 0.5*D0+2*(270/Ae+Math.sqrt(D0*270/Ae));
        Res0[12] = Res0(Re0[12]);
        System.out.println("teta = 270 e Re0 = "+ Re0[12]+" m");
        System.out.println("Res0 = "+ Res0[12] +" m");
        // teta = 292.5
        Re0[13] = 0.5*D0+2*(292.5/Ae+Math.sqrt(D0*292.5/Ae));
        Res0[13] = Res0(Re0[13]);
        System.out.println("teta = 292.5 e Re0 = "+ Re0[13]+" m");
        System.out.println("Res0 = "+ Res0[13] +" m");
        // teta = 315
        Re0[14] = 0.5*D0+2*(315/Ae+Math.sqrt(D0*315/Ae));
        Res0[14] = Res0(Re0[14]);
        System.out.println("teta = 315 e Re0 = "+ Re0[14]+" m");
        System.out.println("Res0 = "+ Res0[14] +" m");
        // teta = 337.5
        Re0[15] = 0.5*D0+2*(337.5/Ae+Math.sqrt(D0*337.5/Ae));
        Res0[15] = Res0(Re0[15]);
        System.out.println("teta = 337.5 e Re0 = "+ Re0[15]+" m");
        System.out.println("Res0 = "+ Res0[15] +" m");
        // teta = 360
        Re0[16] = 0.5*D0+2*(360/Ae+Math.sqrt(D0*360/Ae));
        Res0[16] = Res0(Re0[16]);
        System.out.println("teta = 360 e Re0 = "+ Re0[16]+" m");
        System.out.println("Res0 = "+ Res0[16] +" m");

    }
    private static final String filenameEspiral = "C:/Users/lucas/OneDrive/Área de Trabalho/ProjectTurbine/espiral.xls";
    public void GerarDados(){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("DadosEspiral");
        Row row;
        Cell cell;
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
        cell.setCellValue("DE");
        cell = row.createCell(1);
        cell.setCellValue(DE);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 2
        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("D0");
        cell = row.createCell(1);
        cell.setCellValue(D0);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 3  0
        row = sheet.createRow(3);
        cell = row.createCell(0);
        cell.setCellValue("Re00");
        cell = row.createCell(1);
        cell.setCellValue(Re0[0]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 4 22,5
        row = sheet.createRow(4);
        cell = row.createCell(0);
        cell.setCellValue("Re00225");
        cell = row.createCell(1);
        cell.setCellValue(Re0[1]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 5 45
        row = sheet.createRow(5);
        cell = row.createCell(0);
        cell.setCellValue("Re045");
        cell = row.createCell(1);
        cell.setCellValue(Re0[2]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 6 67,5
        row = sheet.createRow(6);
        cell = row.createCell(0);
        cell.setCellValue("Re0675");
        cell = row.createCell(1);
        cell.setCellValue(Re0[3]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 7 90
        row = sheet.createRow(7);
        cell = row.createCell(0);
        cell.setCellValue("Re090");
        cell = row.createCell(1);
        cell.setCellValue(Re0[4]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 8 112,5
        row = sheet.createRow(8);
        cell = row.createCell(0);
        cell.setCellValue("Re01125");
        cell = row.createCell(1);
        cell.setCellValue(Re0[5]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 9 135
        row = sheet.createRow(9);
        cell = row.createCell(0);
        cell.setCellValue("Re0135");
        cell = row.createCell(1);
        cell.setCellValue(Re0[6]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 10 157,5
        row = sheet.createRow(10);
        cell = row.createCell(0);
        cell.setCellValue("Re01575");
        cell = row.createCell(1);
        cell.setCellValue(Re0[7]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 11 180
        row = sheet.createRow(11);
        cell = row.createCell(0);
        cell.setCellValue("Re0180");
        cell = row.createCell(1);
        cell.setCellValue(Re0[8]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 12 202,5
        row = sheet.createRow(12);
        cell = row.createCell(0);
        cell.setCellValue("Re02025");
        cell = row.createCell(1);
        cell.setCellValue(Re0[9]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 13 225
        row = sheet.createRow(13);
        cell = row.createCell(0);
        cell.setCellValue("Re0225");
        cell = row.createCell(1);
        cell.setCellValue(Re0[10]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 14 247,5
        row = sheet.createRow(14);
        cell = row.createCell(0);
        cell.setCellValue("Re02475");
        cell = row.createCell(1);
        cell.setCellValue(Re0[11]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 15 270
        row = sheet.createRow(15);
        cell = row.createCell(0);
        cell.setCellValue("Re0270");
        cell = row.createCell(1);
        cell.setCellValue(Re0[12]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 16 292,5
        row = sheet.createRow(16);
        cell = row.createCell(0);
        cell.setCellValue("Re02925");
        cell = row.createCell(1);
        cell.setCellValue(Re0[13]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 17 315
        row = sheet.createRow(17);
        cell = row.createCell(0);
        cell.setCellValue("Re0315");
        cell = row.createCell(1);
        cell.setCellValue(Re0[14]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 18 337,5
        row = sheet.createRow(18);
        cell = row.createCell(0);
        cell.setCellValue("Re03375");
        cell = row.createCell(1);
        cell.setCellValue(Re0[15]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 19 360
        row = sheet.createRow(19);
        cell = row.createCell(0);
        cell.setCellValue("Re0360");
        cell = row.createCell(1);
        cell.setCellValue(Re0[16]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 20
        row = sheet.createRow(20);
        cell = row.createCell(0);
        cell.setCellValue("Ae");
        cell = row.createCell(1);
        cell.setCellValue(Ae);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 21 0
        row = sheet.createRow(21);
        cell = row.createCell(0);
        cell.setCellValue("Res0");
        cell = row.createCell(1);
        cell.setCellValue(Res0[0]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 22 22,5
        row = sheet.createRow(22);
        cell = row.createCell(0);
        cell.setCellValue("Res0225");
        cell = row.createCell(1);
        cell.setCellValue(Res0[1]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 23 45
        row = sheet.createRow(23);
        cell = row.createCell(0);
        cell.setCellValue("Res045");
        cell = row.createCell(1);
        cell.setCellValue(Res0[2]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 24 67,5
        row = sheet.createRow(24);
        cell = row.createCell(0);
        cell.setCellValue("Res0675");
        cell = row.createCell(1);
        cell.setCellValue(Res0[3]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 25 90
        row = sheet.createRow(25);
        cell = row.createCell(0);
        cell.setCellValue("Res090");
        cell = row.createCell(1);
        cell.setCellValue(Res0[4]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 26 112,5
        row = sheet.createRow(26);
        cell = row.createCell(0);
        cell.setCellValue("Res1125");
        cell = row.createCell(1);
        cell.setCellValue(Res0[5]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 27 135
        row = sheet.createRow(27);
        cell = row.createCell(0);
        cell.setCellValue("Res135");
        cell = row.createCell(1);
        cell.setCellValue(Res0[6]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 28 157,5
        row = sheet.createRow(28);
        cell = row.createCell(0);
        cell.setCellValue("Res1575");
        cell = row.createCell(1);
        cell.setCellValue(Res0[7]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 29 180
        row = sheet.createRow(29);
        cell = row.createCell(0);
        cell.setCellValue("Res180");
        cell = row.createCell(1);
        cell.setCellValue(Res0[8]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 30 202,5
        row = sheet.createRow(30);
        cell = row.createCell(0);
        cell.setCellValue("Res2025");
        cell = row.createCell(1);
        cell.setCellValue(Res0[9]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 31 225
        row = sheet.createRow(31);
        cell = row.createCell(0);
        cell.setCellValue("Res225");
        cell = row.createCell(1);
        cell.setCellValue(Res0[10]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 32 247,5
        row = sheet.createRow(32);
        cell = row.createCell(0);
        cell.setCellValue("Res2475");
        cell = row.createCell(1);
        cell.setCellValue(Res0[11]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 33 270
        row = sheet.createRow(33);
        cell = row.createCell(0);
        cell.setCellValue("Res270");
        cell = row.createCell(1);
        cell.setCellValue(Res0[12]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 34 2925
        row = sheet.createRow(34);
        cell = row.createCell(0);
        cell.setCellValue("Res2925");
        cell = row.createCell(1);
        cell.setCellValue(Res0[13]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 35 315
        row = sheet.createRow(35);
        cell = row.createCell(0);
        cell.setCellValue("Res315");
        cell = row.createCell(1);
        cell.setCellValue(Res0[14]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 36 3375
        row = sheet.createRow(36);
        cell = row.createCell(0);
        cell.setCellValue("Res3375");
        cell = row.createCell(1);
        cell.setCellValue(Res0[15]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 37 360
        row = sheet.createRow(37);
        cell = row.createCell(0);
        cell.setCellValue("Res360");
        cell = row.createCell(1);
        cell.setCellValue(Res0[16]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 38
        row = sheet.createRow(38);
        cell = row.createCell(0);
        cell.setCellValue("D3e");
        cell = row.createCell(1);
        cell.setCellValue(D3e);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 39
        row = sheet.createRow(39);
        cell = row.createCell(0);
        cell.setCellValue("D3i");
        cell = row.createCell(1);
        cell.setCellValue(D3i);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 40
        row = sheet.createRow(40);
        cell = row.createCell(0);
        cell.setCellValue("b0");
        cell = row.createCell(1);
        cell.setCellValue(b0);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 41
        row = sheet.createRow(41);
        cell = row.createCell(0);
        cell.setCellValue("Ddg");
        cell = row.createCell(1);
        cell.setCellValue(Ddg);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 42
        row = sheet.createRow(42);
        cell = row.createCell(0);
        cell.setCellValue("Ddg");
        cell = row.createCell(1);
        cell.setCellValue(Ddg);
        cell = row.createCell(2);
        cell.setCellValue("m");
        try{
            FileOutputStream out = new FileOutputStream(new File(Espiral.filenameEspiral));
            workbook.write(out);
            out.close();
            System.out.printf("Arquivo gerado com sucesso!");
        }catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println("Arquivo não encontrado!");
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Erro na edição do arquivo \n");
        }
    }
}
