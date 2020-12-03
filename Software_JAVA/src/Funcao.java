import org.mariuszgromada.math.mxparser.Function;


/**
 * @author Lucas Losinskas
 */

/*
 *
 * classe para tratar a função simbólicamente criada para gerar a malha de dados dos gráficos
 */
public class Funcao {
    String Expressao, Var;

    public void funcao1(String var, String expressao) {

        this.Expressao = expressao;
        this.Var = var;

    }

    // Cria o array de x
    public double[] vetorX(double x0, double xn) {

        // Precisão de pontos
        double d = 0.1;
        // cria os valores de x incrementando a precisão
        int n = (int) Math.abs((xn - x0) / d);
        double r[] = new double[n];
        for (int i = 0; i < n; i++) {
            r[i] = x0;
            x0 += d;

        }
        //retorna o array de x
        return r;
    }

    // Cria o array de y
    public double[] eval(double[] x) throws Exception {
        //
        double r[] = new double[x.length];
        Function f = new Function("f", Expressao, Var);


        for (int i = 0; i < r.length; i++) {

            r[i] = f.calculate(x[i]);

        }
        return r;
    }


}
