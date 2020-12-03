import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Aletas {

    private double Ddg;     // Diâmetro de de onde vai as fixações das aletas [m]
    private double zd;      // Número de aletas
    private double td;      // Passo das aletas [m]
    private double L;       // Comprimento total da aleta [m]
    private double Ld;      // Comprimento util da aleta [m]
    private double Lds;     // Comprimento da aleta desde sua ponta ate o centro de giro
    private double Ldi;     // Comprimento desde do seu eixo ate a cauda.
    private double dd;      // espessura da aleta [m]
    private double b0;
    private double H;


    public Aletas(double Ddg, double H, double b0) {
        this.Ddg = Ddg;
        this.H = H;
        this.b0 = b0;

        zd();
        System.out.println("Número de aletas zd = " + zd);
        td();
        System.out.println("O passo das aletas é td = "+ td+ " m");
        L();
        System.out.println("O valor do comprimento da aleta é L = "+ L +" m");
        Ld();
        System.out.println("O valor de comprimento da aleta util Ld = "+ Ld+" m");
        Lds();
        System.out.println("O valor do comprimeto da crista ate o centro de giro Lds ="+Lds+ " m");
        Ldi();
        System.out.println("O valor do comprimento do centro de giro até a a cauda");
        dd();
        System.out.println("dd = "+ dd +" m");
    }

    private void zd() {
        double exp = 0.3333333;
        zd = 13.5 * Math.pow(Ddg, exp);
        int numeroAletas = (int) zd;
        while (numeroAletas % 4 != 0) {
            numeroAletas -= 1;
        }
        zd = Double.parseDouble(String.valueOf(numeroAletas));

    }
    private void dd(){
        dd = 14.9 * Math.pow(H*Ld*Math.pow(b0, 2)/12000000, 0.33333);
    }
    private void td(){
        td = Math.PI*Ddg/zd;
    }
    private void L(){
        L = 1.287*td;
    }
    private void Ld(){
        Ld = 1.167*td;
    }
    private void Lds(){
        Lds = (0.5+0.07)*td;
    }
    private void Ldi(){
        Ldi = (0.667-0.07)*td;
    }

    public double getDdg() {
        return Ddg;
    }

    public void setDdg(double ddg) {
        Ddg = ddg;
    }

    public double getZd() {
        return zd;
    }

    public void setZd(double zd) {
        this.zd = zd;
    }

    public double getTd() {
        return td;
    }

    public void setTd(double td) {
        this.td = td;
    }
    private static final String filename = "C:/Users/lucas/OneDrive/Área de Trabalho/ProjectTurbine/aleta.xls";
    public void GerarPontos(){
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
        cell.setCellValue("zd");
        cell = row.createCell(1);
        cell.setCellValue(zd);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 2
        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("td");
        cell = row.createCell(1);
        cell.setCellValue(td);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 3
        row = sheet.createRow(3);
        cell = row.createCell(0);
        cell.setCellValue("L");
        cell = row.createCell(1);
        cell.setCellValue(L);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 4
        row = sheet.createRow(4);
        cell = row.createCell(0);
        cell.setCellValue("Ld");
        cell = row.createCell(1);
        cell.setCellValue(Ld);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 5
        row = sheet.createRow(5);
        cell = row.createCell(0);
        cell.setCellValue("Lds");
        cell = row.createCell(1);
        cell.setCellValue(Lds);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 6
        row = sheet.createRow(6);
        cell = row.createCell(0);
        cell.setCellValue("Ldi");
        cell = row.createCell(1);
        cell.setCellValue(Ldi);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 7
        row = sheet.createRow(7);
        cell = row.createCell(0);
        cell.setCellValue("dd");
        cell = row.createCell(1);
        cell.setCellValue(dd);
        cell = row.createCell(2);
        cell.setCellValue("m");
        // Linha 8
        row = sheet.createRow(8);
        cell = row.createCell(0);
        cell.setCellValue("b0");
        cell = row.createCell(1);
        cell.setCellValue(b0);
        cell = row.createCell(2);
        cell.setCellValue("m");



        try{
            FileOutputStream out = new FileOutputStream(new File(Aletas.filename));
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
