
/**
 * @author Lucas Losinskas
 * e-mail: llosinskas@gmail.com
 */
/*
 * Software para o controle da bancada dídatica do TCC
 * 
 */

#define sCorrente A0
#define pwm 3
  // Prótotipo das Funçõe Auxiliares
void setDuty_pinPWM(float value); // Seleciona o duty cycle na saída digital 3
void setDuty_pin11(float value);// Seleciona o duty cycle na saída digital 11
void setFrequency(char option);  // Seleciona a frequência de saída de operação do PWM 
int calc_mA(float value);
  // Variáveis Globais
int adc = 0x00; // Armazena grandezas analógicas 
int corrente;
  // Configurações Iniciais
void setup() {
 pinMode(pwm,OUTPUT); // Saída PWM pino 3
 pinMode(11, OUTPUT); // Saída PWM pino 11

 TCCR2A = 0xA3; // Configura a operação em fast PWM
  // Seleciona a frequência de saída opção inical 1 para frequência PWM (aprox 62.5kHz);
 setFrequency(1);
  // Saída 3 com duty inicial de 80%
setDuty_pinPWM(80);
  // Saída 3 com duty inicial de 20%
 setDuty_pin11(20);
}// end setup

  // Inicia o Loop do arduino 
  void loop() {
  adc = analogRead(sCorrente);
  adc = map(adc, 0, 1023, 1, 100);
  setDuty_pinPWM(adc);
  corrente = calc_mA(0.185);
    
  }//end Loop

  
  void setDuty_pinPWM(float value){
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
  int calc_mA(float value){
  //Sensor de corrente 5A
  adc = analogRead(sCorrente);  
  int tensao = (adc/1024.0)*5000;
  int mAmps = (tensao - 2500)/value;
  return mAmps;
 
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
