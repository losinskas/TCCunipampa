package GerarExcel;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class gerarPlanilha {

    private static final String filename = "./Planilha.xls";

    public gerarPlanilha() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Geometria");

        try{
            FileOutputStream out = new FileOutputStream(new File(gerarPlanilha.filename));
            workbook.write(out);
            out.close();
            System.out.println("Arquivo gerado com sucesso!");
        }catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println("Arquivo não encontrado!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro na edição do arquivo!");
        }

    }

}
