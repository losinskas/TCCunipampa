import java.io.Serializable;

public class EliminacaoClass implements Serializable {

    private double matriz[][];
    private double X[], R[];
    private String texto;
    private String latex;

    public void Eliminacao(double m[][], int n, double r[]) {

        double maior, pivo, menor;
        int p;
        latex = "Método de Eliminação de Gauss";
        String latexAuxiliar;

        //==========================================================================================
        // Ajeitar o código latex
        latexAuxiliar = "\\left[ \\begin{array}{";
        for (int i = 0; i < n; i++) {
            latexAuxiliar += "c";
        }
        latexAuxiliar += "}";

        //==========================================================================================
        // Arrumando a matriz e o método
        for (int k = 0; k < n - 1; k++) {
            p = k;
            maior = Math.abs(m[k][k]);
            for (int i = k + 1; i < n; i++) {
                menor = Math.abs(m[i][k]);
                if (menor > maior) {
                    maior = menor;
                    p = i;
                }
            }
            if (p != k) {
                for (int i = k; i < n; i++) {
                    menor = m[p][i];
                    m[p][i] = m[k][i];
                    m[k][i] = menor;
                }
                menor = r[p];
                r[p] = r[k];
                r[k] = menor;
            }
            //======================================================================================
            // Método de Eliminaçao Gauss
            for (int i = k + 1; i < n; i++) {
                texto += "M = " + m[i][k];
                pivo = m[i][k] / m[k][k];
                texto += "Pivo = " + pivo + "\n" + " Matriz  \n\n";
                //   latex += "Pivo = " + pivo + latexAuxiliar;
                for (int j = k; j < n; j++) {


                    m[i][j] = m[i][j] - pivo * m[k][j];
                    texto += "\n" + m[i][j] + "\t";
                }
                texto += "\n Pivo =  \n" + " Matriz  \n\n";
                r[i] = r[i] - pivo * r[k];
                texto += r[i] + "\n\n";
            } // end for i
        } //end for k


        //==========================================================================================
        // Começa o método escrever a matriz
        latex += "$$ " + latexAuxiliar;
        for (int i = 0; i < n; i++) {

            texto += " | \t ";

            for (int j = 0; j < n; j++) {
                if (j == (n - 1)) {
                    if (i == (n - 1)) {
                        latex += m[i][j];
                    } else {
                        latex += m[i][j] + "\\\\";
                    }

                } else {
                    latex += m[i][j] + "&";
                }
                texto += m[i][j] + " \t\t ";

            }
            texto += "| = \t| " + r[i] + "|\n";

        }
        latex += "\\end{array}\\right]";

        texto += " \nCalculo para achar os valores das variáveis" + "\n\n";

        double x[] = new double[n];
        double soma;
        x[n - 1] = r[n - 1] / m[n - 1][n - 1];
        texto += "Valor de x_" + (n - 1) + " = " + x[n - 1] + "\n\n";
        for (int i = n - 2; i >= 0; i--) {
            soma = r[i];
            for (int j = i; j < n; j++) {
                soma = soma - m[i][j] * x[j];
            }
            x[i] = soma / m[i][i];
            texto += "Valor de x_" + (i) + " = " + x[i] + "\n\n";
        }
        latex += " \\cdot \\left[ \\begin{array}{c}";
        for (int i = 0; i < n; i++) {
            if (i == (n - 1)) {
                latex += x[i];
            } else {
                latex += x[i] + "\\\\";
            }
        }
        latex += "\\end{array} \\right] = " +
                "\\left[ \\begin{array}{c}";
        for (int i = 0; i < n; i++) {
            if (i == (n - 1)) {
                latex += r[i];
            } else {
                latex += r[i] + "\\\\";
            }
        }
        latex += "\\end{array} \\right]$$";


        latex += "Matriz Escalonada";
        latex += "$$A = \\begin{bmatrix}";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == n - 1) {
                    latex += m[i][j];
                } else {
                    latex += m[i][j] + "&";
                }

            }
            if (i != n - 1) {
                latex += "\\\\";
            }

        }
        latex += "\\end{bmatrix}$$";

        latex+="Solução";
        latex += "$$x = \\begin{bmatrix}";
        for (int i = 0; i < n; i++) {
            if (i == n - 1) {
                latex += "x_{" + i + "}=" + x[i];
            } else {
                latex += "x_{" + i + "} = " + x[i] + "\\\\";
            }
        }
        latex += "\\end{bmatrix}$$";


        latex += "$$f(x) = \\begin{bmatrix}";
        for (int i = 0; i < n; i++) {
            if (i == n - 1) {
                latex += r[i];
            } else {
                latex += r[i] + "\\\\";
            }
        }
        latex += "\\end{bmatrix}$$";


        this.X = x;
        this.R = r;
        this.matriz = m;
        this.texto = texto;
    }

    //==============================================================================================
    // Método de pivotamento
    public void eliminacao1(double m[][], int n, double b[]) {
        double pivo;
        String texto;
        texto = "";
        latex = "Método de Pivotamneto";
        String latexAuxiliar;

        //==========================================================================================
        // Ajeitar o código latex
        latexAuxiliar = "\\left[ \\begin{array}{";
        for (int i = 0; i < n; i++) {
            latexAuxiliar += "c";
        }
        latexAuxiliar += "}";


        for (int k = 0; k < n - 1; k++) {

            texto += "Passo " + (k + 1) + "\n";
            for (int i = k + 1; i < n; i++) {
                if (m[k][k] != 0) {
                    pivo = m[i][k] / m[k][k];
                    texto += " pivo = " + pivo + "\n" + "L_" + (i + 1) + " - pivo * L_" + (k + 1) + " M = " + m[i][k] + " " + m[k][k] + "\n";
                } else {
                    pivo = 1;

                }
                for (int j = k; j < n; j++) {
                    m[i][j] = m[i][j] - pivo * m[k][j];
                }
                b[i] = b[i] - pivo * b[k];
            }
        }

        double x[] = new double[n];
        double soma = 0;
        x[n - 1] = b[n - 1] / m[n - 1][n - 1];
        for (int i = n - 2; i >= 0; i--) {
            soma = b[i];
            for (int j = i; j < n; j++) {
                soma = soma - m[i][j] * x[j];
            }
            x[i] = soma / m[i][i];


        }
        for (int i = 0; i < n; i++) {

            texto += " | \t ";

            for (int j = 0; j < n; j++) {
                texto += m[i][j] + " \t\t ";
            }
            texto += "| \t  |" + b[i] + "|\n";
        }

        texto += "Resposta \n";
        for (int i = 0; i < n; i++) {
            texto += "O valor de x_" + (i + 1) + " = \t " + x[i] + "\n";
        }

        latex += "$$$$Matriz Escalonada";
        latex += "$$A = \\begin{bmatrix}";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == n - 1) {
                    latex += m[i][j];
                } else {
                    latex += m[i][j] + "&";
                }

            }
            if (i != n - 1) {
                latex += "\\\\";
            }

        }
        latex += "\\end{bmatrix}$$";

        latex+="Solução";
        latex += "$$x = \\begin{bmatrix}";
        for (int i = 0; i < n; i++) {
            if (i == n - 1) {
                latex += "x_{" + i + "}=" + x[i];
            } else {
                latex += "x_{" + i + "} = " + x[i] + "\\\\";
            }
        }
        latex += "\\end{bmatrix}$$";


        latex += "$$f(x) = \\begin{bmatrix}";
        for (int i = 0; i < n; i++) {
            if (i == n - 1) {
                latex += b[i];
            } else {
                latex += b[i] + "\\\\";
            }
        }
        latex += "\\end{bmatrix}$$";



        this.X = x;
        this.R = b;
        this.matriz = m;
        this.texto = texto;
    }

    public void jordan(double[][] m, int n, double[] b) {

        this.texto = "";
        this.matriz = null;
        this.R = null;
        this.X = null;
        int passo;
        String txt = "";
        n = b.length;
        double pivo, divisor;
        latex = "Método de Jordan $$$$";
        for (int k = 0; k < n - 1; k++) {

            for (int i = k + 1; i < n; i++) {
                passo = k + 1;
                if (m[k][k] != 0) {
                    txt += "Passo " + passo + "\n";
                    pivo = m[i][k] / m[k][k];
                } else {
                    txt += " O Pivo deu zero";
                    break;
                }

                for (int j = 0; j < n; j++) {
                    m[i][j] = m[i][j] - pivo * m[k][j];
                    txt += " Pivo = " + pivo + "\n" + " L_" + (i + 1) + " = L_" + (i + 1) + " - Pivo* L_" + (i) + "\n";

                }
                b[i] = b[i] - pivo * b[k];

            }
            divisor = m[k][k];
            for (int i = k; i < n; i++) {
                txt += "L_" + (k + 1) + " / " + divisor + "\n";
                m[k][i] = m[k][i] / divisor;
            }
            b[k] = b[k] / divisor;
            if (k == n - 2) {
                divisor = m[k + 1][k + 1];
                txt += "L_" + (k + 1) + " / " + divisor + "\n\n";
                for (int i = k + 1; i < n; i++) {

                    m[k + 1][i] = m[k + 1][i] / divisor;

                }
                b[k + 1] = b[k + 1] / divisor;
            }
        }
        for (int i = 0; i < n; i++) {
            txt += "| \t";
            for (int j = 0; j < n; j++) {
                txt += m[i][j] + "\t\t";
            }
            txt += "|\t |" + b[i] + "|\n";
        }
        txt += "\n\n";

        for (int k = n - 1; k > 0; k--) {

            for (int i = k - 1; i >= 0; i--) {

                pivo = m[i][k] / m[k][k];

                for (int j = n - 1; j >= 0; j--) {
                    m[i][j] = m[i][j] - pivo * m[k][j];
                }
                b[i] = b[i] - pivo * b[k];
                txt += " O Valor de x_" + i + " = \t " + b[i] + "\n";
            }
        }
        latex += "Matriz Escalonada";
        latex += "$$A = \\begin{bmatrix}";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == n - 1) {
                    latex += m[i][j];
                } else {
                    latex += m[i][j] + "&";
                }

            }
            if (i != n - 1) {
                latex += "\\\\";
            }

        }
        latex += "\\end{bmatrix}$$";

        latex+="Solução";
        latex += "$$x = \\begin{bmatrix}";
        for (int i = 0; i < n; i++) {
            if (i == n - 1) {
                latex += "x_{" + i + "}=" + b[i];
            } else {
                latex += "x_{" + i + "} = " + b[i] + "\\\\";
            }
        }
        latex += "\\end{bmatrix}$$";


        latex += "$$f(x) = \\begin{bmatrix}";
        for (int i = 0; i < n; i++) {
            if (i == n - 1) {
                latex += b[i];
            } else {
                latex += b[i] + "\\\\";
            }
        }
        latex += "\\end{bmatrix}$$";


        this.matriz = m;
        this.R = b;
        this.X = b;
        this.texto = txt;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String Texto() {
        return this.texto;
    }

    public double[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(double[][] matriz) {
        this.matriz = matriz;
    }

    public double[] getX() {
        return X;
    }

    public void setX(double[] X) {
        this.X = X;
    }

    public double[] getR() {
        return R;
    }

    public void setR(double[] R) {
        this.R = R;
    }

    public String getLatex() {
        return latex;
    }

    public void setLatex(String latex) {
        this.latex = latex;
    }

}
