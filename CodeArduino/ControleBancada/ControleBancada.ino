
/**
 * @author Lucas Losinskas
 * e-mail: llosinskas@gmail.com
 */
/*
 * Software para o controle da bancada dídatica do TCC
 * 
 */

  // Prótotipo das Funçõe Auxiliares
void setDuty_pin03(float value); // Seleciona o duty cycle na saída digital 3
void setDuty_pin11(float value);// Seleciona o duty cycle na saída digital 11
void setFrequency(char option);  // Seleciona a frequência de saída de operação do PWM 

  // Variáveis Globais
int adc = 0x00; // Armazena grandezas analógicas 

  // Configurações Iniciais
void setup() {
 pinMode(3,OUTPUT); // Saída PWM pino 3
 pinMode(11, OUTPUT); // Saída PWM pino 11

 TCCR2A = 0xA3; // Configura a operação em fast PWM
  // Seleciona a frequência de saída opção inical 1 para frequência PWM (aprox 62.5kHz);
 setFrequency(2);
  // Saída 3 com duty inicial de 80%
setDuty_pin03(80);
  // Saída 3 com duty inicial de 20%
 setDuty_pin11(20);
}// end setup

  // Inicia o Loop do arduino 
  void loop() {
  adc = analogRead(A0);
  adc = map(adc, 0, 1023, 1, 100);
  setDuty_pin03(adc);
    
  }//end Loop

  
  void setDuty_pin03(float value){
    int duty;
    value = value/100;
    duty= ( value* 256)-1;
    OCR2B = duty;
  }
  
  void setDuty_pin11(float value){
    int duty;
    value = ( value/100);
    duty = ( value * 256)-1;
    OCR2A = duty;
  }
  
  
  // Função de setFrequency
  void setFrequency(char option){
    /*
     *  Table:
     *  
     *      option  Frequêncy (
     *      
     *      1       62.5    kHz 
     *      2       7.81    kHz
     *      3       1.95    kHz
     *      4       976.56  Hz
     *      5       488.28  Hz
     *      6       244.14  Hz
     *      7       61.03   Hz
     */ 

      TCCR2B = option;

     //end setFrequency
  }




/*
 * Agradecimentos: 
 *  - Professor Fabio Tomm
 *  - WRKits 
 *  - Brincado com ideias 
 */
