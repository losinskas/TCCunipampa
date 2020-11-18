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

    public Espiral(double H, double eta_i, double n, double Qp, double nqar, double D5e) {
        // Passar parâmetros de entrada
        this.H = H;
        this.eta_i = eta_i;
        this.n = n;
        this.Qp = Qp;
        this.nqar = nqar;
        this.D5e = D5e;

        AE();
        System.out.println("\nO Valor da área Ae = " + Ae + " °/m");
        Ce();
        System.out.println("Velocidade de entrada Ce = " + Ce + " m/s");
        DE();
        System.out.println("Diâmetro de entrada da turbina DE = " + DE + " m");
        D0();
        System.out.println("Diâmetro da turbina D0 = "+ D0+" m");
        Re0();
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

    private void D0(){
        D0 = (0.16*Math.pow(10, -4)*Math.pow(nqar, 2)- 0.98*Math.pow(10, -2)*nqar+2.9)*D5e;
    }
    private void Re0(){
        Re0 = new double[5];
        // teta = 0
        Re0[0] = 0.5*D0;
        System.out.println("teta = 0 e Re0 = "+ Re0[0]+" m");
        // teta = 90
        Re0[1] = 0.5*D0+2*(90/Ae+Math.sqrt(D0*90/Ae));
        System.out.println("teta = 0 e Re0 = "+ Re0[1]+" m");
        // teta = 180
        Re0[2] = 0.5*D0+2*(180/Ae+Math.sqrt(D0*180/Ae));
        System.out.println("teta = 0 e Re0 = "+ Re0[2]+" m");
        // teta = 270
        Re0[3] = 0.5*D0+2*(270/Ae+Math.sqrt(D0*270/Ae));
        System.out.println("teta = 0 e Re0 = "+ Re0[3]+" m");
        // teta = 360
        Re0[4] = 0.5*D0+2*(360/Ae+Math.sqrt(D0*360/Ae));
        System.out.println("teta = 0 e Re0 = "+ Re0[4]+" m");


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
        // Linha 3
        row = sheet.createRow(3);
        cell = row.createCell(0);
        cell.setCellValue("Re00");
        cell = row.createCell(1);
        cell.setCellValue(Re0[0]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 4
        row = sheet.createRow(4);
        cell = row.createCell(0);
        cell.setCellValue("Re090");
        cell = row.createCell(1);
        cell.setCellValue(Re0[1]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 5
        row = sheet.createRow(5);
        cell = row.createCell(0);
        cell.setCellValue("Re0180");
        cell = row.createCell(1);
        cell.setCellValue(Re0[2]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 6
        row = sheet.createRow(6);
        cell = row.createCell(0);
        cell.setCellValue("Re0270");
        cell = row.createCell(1);
        cell.setCellValue(Re0[3]);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 7
        row = sheet.createRow(7);
        cell = row.createCell(0);
        cell.setCellValue("Re0360");
        cell = row.createCell(1);
        cell.setCellValue(Re0[4]);
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
